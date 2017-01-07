/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

import ApplicationModels.Temperature;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Jarno
 */
public class SensorConnection implements Runnable
{
    private Device configurationDevice;
    private boolean hasbeenConnected = false;
    private MqttClient client;

    public SensorConnection(Device configurationDevice)
    {
        this.configurationDevice = configurationDevice;
        
        initializeMqttClient();
    }
    
    private void initializeMqttClient()
    {
        try
        {
            String brokerAddress = ApplicationRoot.applicationSettings.getBrokerUrl() + ":" + String.valueOf(ApplicationRoot.applicationSettings.getBrokerPort());
            if(!brokerAddress.startsWith("tcp://"))
                brokerAddress = "tcp://" + brokerAddress;
            
            client = new MqttClient(brokerAddress, String.valueOf(Math.random()));
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            
            if(ApplicationRoot.applicationSettings.getBrokerPassword() != null && ApplicationRoot.applicationSettings.getBrokerUsername() != null)
            {
                connectOptions.setPassword(ApplicationRoot.applicationSettings.getBrokerPassword().toCharArray());
                connectOptions.setUserName(ApplicationRoot.applicationSettings.getBrokerUsername());
            }
            
            connectOptions.setCleanSession(true);
            ColorPrint.printGreen("Connection to broker: " + ApplicationRoot.applicationSettings.getBrokerUrl());
            client.connect(connectOptions);
            ColorPrint.printGreen("Connected to broker!");
            
        } catch (Exception e)
        {
            ColorPrint.printRed(e.getLocalizedMessage());
        }
    }

    @Override
    public void run()
    {
        StreamConnection connection;
        
        while (true)
        {         
            try
            {
                String btAddress = new String();
                if(configurationDevice.getRemoteDevice() == null)
                    btAddress = configurationDevice.getMAC().replace(":", "");
                else
                    btAddress = configurationDevice.getRemoteDevice().getBluetoothAddress();
                connection = (StreamConnection)Connector.open(String.format("btspp://%s:1;master=true;encrypt=true;", btAddress));
                ColorPrint.printGreen(configurationDevice.getName() + " is connected...");
                hasbeenConnected = true;
                DataInputStream input = connection.openDataInputStream();
                DataOutputStream output = connection.openDataOutputStream();
                InputStreamReader reader = new InputStreamReader(input);
                StringBuilder sb = new StringBuilder();
                
                Gson gson = new Gson();
                
                while(true)
                {
                    output.write(0);
                    output.flush();
                    if(reader.ready())
                    {
                        char[] b = new char[500];
                        reader.read(b);
                        String inputString = new String(b);
                        sb.append(inputString);
                        if(sb.toString().contains("#"))
                        {
                            String firstPart = sb.toString().split("#", 2)[0];
                            String tempString = sb.toString().split("#", 2)[1];
                            sb.setLength(0);
                            sb.append(tempString);
                            
                                String incomingMessage = String.format(firstPart, configurationDevice.getName()).replace("\u0000", "").trim();
                                
                                Temperature t = null;
                                
                                int failCounter = 0;
                                while(t == null && failCounter < 50)
                                {
                                    try
                                    {
                                        t = gson.fromJson(incomingMessage, Temperature.class);
                                    } catch (Exception e)
                                    {
                                        failCounter++;
                                    }
                                }
                                
                                if(t != null)
                                {
                                    t.setTimestamp(System.currentTimeMillis());
                                    String jsonString = gson.toJson(t);
                                    ReceivedMessage(jsonString);
                                }
                        }
                        
                        /*
                        char[] b = new char[800];
                        reader.read(b);
                        String inString = new String(b);
                        if(inString.contains("#"))
                        {
                            String message = sb.toString() + inString.split("#", 0)[0];
                            try
                            {
                                message = String.format(message, configurationDevice.getName()).trim();
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                                continue;
                            }
                            System.out.println(message);
                            try
                            {
                                Temperature t = gson.fromJson(message, Temperature.class);
                                t.setTimestamp(System.currentTimeMillis());
                                String jsonString = gson.toJson(t);
                                ReceivedMessage(jsonString);
                                sb.setLength(0);
                                sb.append(inString.split("#", 0)[1]);
                                
                            } catch (Exception e)
                            {
                                e.printStackTrace(); 
                            }
                            
                        }
                        else if(!inString.trim().equals(""))
                        {
                            sb.append(inString);
                        }
*/
                    }
                    Sleep(500);
                }
            }
            catch (Exception e)
            {
                connection = null;
                if(hasbeenConnected)
                {
                    ColorPrint.printRed(this.configurationDevice.getName() + " Connection lost. Reconnecting...");
                    this.hasbeenConnected = false;
                }  
                else
                    ColorPrint.printRed("Cannot connect to " + this.configurationDevice.getName());
                Sleep(1000);
            }
        }
    }
    
    private void ReceivedMessage(String message)
    {
        if(client.isConnected())
        {
            for (String Topic : configurationDevice.getTopics())
            {
                MqttMessage msg = new MqttMessage(message.getBytes());
                msg.setQos(0);
                try
                {
                    client.publish(Topic, msg);
                } catch (MqttException e)
                {
                    ColorPrint.printRed(e.getLocalizedMessage());
                }
            }
        }
    }
    
    private void Sleep(int ms)
    {
        try
        {
            Thread.sleep(ms);
        } catch (Exception e)
        {
            
        }
    }
}
