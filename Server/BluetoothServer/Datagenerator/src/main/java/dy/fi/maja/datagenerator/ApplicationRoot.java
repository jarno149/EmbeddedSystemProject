/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.datagenerator;

import com.google.gson.Gson;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Jarno
 */
public class ApplicationRoot
{
    private static MqttClient client;
    private static Gson gson;
    
    public static void main(String[] args) throws InterruptedException
    {
        if(args.length < 1)
        {
            System.out.println("Give sensorname!");
            System.exit(0);
        }
        
        String sensorname = args[0];
        
        // Read settings from file
        Settings.readSettings();
        
        initializeMqttClient();
        
        gson = new Gson();
        
        double val = 20;
        
        while (true)
        {            
            if(client.isConnected())
            {
                double t = Math.random();
                if(t < 0.5 && val < 30)
                    val += 0.1;
                else
                    val -= 0.1;
                
                val = Math.round(val * 100) / 100.0;
                TemperatureObject.TemperatureUnit unit = new TemperatureObject.TemperatureUnit(" C", TemperatureObject.Unit.Celsius);
                TemperatureObject o = new TemperatureObject(sensorname, val, unit, System.currentTimeMillis());
                
                String jsonString = gson.toJson(o);
                System.out.println("Publishing: " + jsonString);
                
                MqttMessage message = new MqttMessage(jsonString.getBytes());
                message.setQos(0);
                try
                {
                    client.publish(Settings.BrokerTopic, message);
                }
                catch (Exception e)
                {
                    System.out.println("Cannot publish to topic: " + Settings.BrokerTopic);
                }
            }
            
            Thread.sleep(Settings.Interval);
        }
    }
    
    private static void initializeMqttClient()
    {
        try
        {
            String brokerAddress = Settings.BrokerUrl;
            brokerAddress += Settings.BrokerUrl != null ? ":" + Settings.BrokerPort : "";
            if(!brokerAddress.startsWith("tcp://"))
                brokerAddress = "tcp://" + brokerAddress;
            
            client = new MqttClient(brokerAddress, "DataGenerator:startTime=" + String.valueOf(System.currentTimeMillis()));
            MqttConnectOptions connOpts = new MqttConnectOptions();
            
            if(Settings.BrokerPassword != null && Settings.BrokerUsername != null)
            {
                connOpts.setUserName(Settings.BrokerUsername);
                connOpts.setPassword(Settings.BrokerPassword.toCharArray());
            }
            
            connOpts.setCleanSession(true);
            System.out.println("Connection to broker: " + Settings.BrokerUrl);
            client.connect(connOpts);
        }
        catch (Exception e)
        {
            System.out.println("Cannot connect to broker.");
            e.printStackTrace();
        }
    }
    
}
