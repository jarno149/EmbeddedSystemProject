/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.services;

import com.google.gson.Gson;
import dy.fi.maja.applicationmodels.Temperature;
import dy.fi.maja.repositories.TemperatureRepository;
import dy.fi.maja.utils.Settings.MqttSettings;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author jarno
 */
public class MqttService implements Runnable
{
    private MqttSettings settings;
    private static TemperatureRepository temperatureRepo;
    private MqttClient client;

    public MqttService(MqttSettings settings, TemperatureRepository temperatureRepository)
    {
        this.settings = settings;
        temperatureRepo = temperatureRepository;
    }
    
    private void start()
    {
        Gson gson = new Gson();
        try
        {
            client = new MqttClient(settings.getUrl(), "AppServer:startTime=" + String.valueOf(System.currentTimeMillis()));
            MqttConnectOptions options =  new MqttConnectOptions();
            if(settings.getUserName() != null || settings.getPassWord() != null)
            {
                options.setUserName(settings.getUserName());
                options.setPassword(settings.getPassWord().toCharArray());
            }
            options.setAutomaticReconnect(true);
            
            client.connect(options);
            System.out.println("MqttService up and running: " + settings.getUrl());
            client.subscribe(settings.getTopic(), new IMqttMessageListener() {
                @Override
                public void messageArrived(String string, MqttMessage mm) throws Exception
                {
                    String payload = new String(mm.getPayload());
                    try
                    {
                        Temperature t = gson.fromJson(payload, Temperature.class);
                        temperatureRepo.insert(t);
                    }
                    catch(Exception e)
                    {
                        System.out.println("Cannot parse json from topic: " + settings.getTopic() + " data: " + payload);
                    }
                }
            });
        }
        catch(MqttException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        start();
        
        sleep(10000);
        
        while(true)
        {
            if(client == null || !client.isConnected())
            {
                start();
            }
            sleep(5000);
        }
    }
    
    private void sleep(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException e){}
    }
}
