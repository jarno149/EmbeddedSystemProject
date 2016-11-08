/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author Jarno
 */
public class SensorConnection implements Runnable
{
    private RemoteDevice remoteDevice;
    private Device configuration;
    private String deviceName;
    private boolean hasbeenConnected = false;
    MqttClient client;
    
    public SensorConnection(Device dev)
    {
        this.remoteDevice = dev.RemoteDevice;
        try
        {
            this.deviceName = this.remoteDevice.getFriendlyName(true);
        } catch (Exception e)
        {
            this.deviceName = this.remoteDevice.getBluetoothAddress();
        }
        this.configuration = dev;
        
        InitializeMqttClient();
    }
    
    private void InitializeMqttClient()
    {
        MemoryPersistence persistence = new MemoryPersistence();
        try
        {
            String brokerAddress = Settings.BrokerUrl;
            brokerAddress += Settings.BrokerPort != null ? ":" + Settings.BrokerPort : "";
            if(!brokerAddress.startsWith("tcp://"))
                brokerAddress = "tcp://" + brokerAddress;
            
            client = new MqttClient(brokerAddress, configuration.Name, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            
            if(Settings.BrokerPassword != null && Settings.BrokerUsername != null)
            {
                connOpts.setPassword(Settings.BrokerPassword.toCharArray());
                connOpts.setUserName(Settings.BrokerUsername);
            }
            
            connOpts.setCleanSession(true);
            ANSI.printGreen("Connecting to broker: " + Settings.BrokerUrl);
            client.connect(connOpts);
            ANSI.printGreen("Connected to broker: " + Settings.BrokerUrl);
            
        } catch (MqttException exception)
        {
            ANSI.printRed(exception.getLocalizedMessage());
        }
    }
    
    @Override
    public void run()
    {
        while (true)
        {         
            try
            {
                StreamConnection connection = (StreamConnection)Connector.open(String.format("btspp://%s:1;master=true;encrypt=true;", remoteDevice.getBluetoothAddress()));
                ANSI.printGreen(deviceName + " is connected...");
                hasbeenConnected = true;
                DataInputStream input = connection.openDataInputStream();
                DataOutputStream output = connection.openDataOutputStream();
                InputStreamReader reader = new InputStreamReader(input);
                StringBuilder sb = new StringBuilder();
                
                int failCounter = 100;
                while(true)
                {
                    output.write(0);
                    if(reader.ready())
                    {
                        char[] b = new char[400];
                        reader.read(b);
                        String inString = new String(b);
                        if(inString.contains("#"))
                        {
                            String message = sb.toString() + inString.split("#", 0)[0];
                            System.out.println(message);
                            ReceivedMessage(message);
                            sb.setLength(0);
                            sb.append(inString.split("#", 0)[1]);
                            output.write(0);
                            output.flush();
                        }
                        else if(!inString.trim().equals(""))
                        {
                            sb.append(inString);
                        }
                    }
                    Sleep(100);
                }
            }
            catch (Exception e)
            {
                if(hasbeenConnected)
                {
                    ANSI.printRed(this.deviceName + " Connection lost. Reconnecting...");
                    this.hasbeenConnected = false;
                }  
                else
                    ANSI.printRed("Cannot connect to " + this.deviceName);
                Sleep(1000);
            }
        }
    }
    
    private void ReceivedMessage(String message)
    {
        if(client.isConnected())
        {
            for (String Topic : configuration.Topics)
            {
                MqttMessage msg = new MqttMessage(message.getBytes());
                msg.setQos(0);
                try
                {
                    client.publish(Topic, msg);
                    
                } catch (MqttException e)
                {
                    ANSI.printRed(e.getLocalizedMessage());
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
