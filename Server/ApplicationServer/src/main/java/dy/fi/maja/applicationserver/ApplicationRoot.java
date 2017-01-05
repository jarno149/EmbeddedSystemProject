/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.applicationserver;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import dy.fi.maja.applicationmodels.Temperature;
import dy.fi.maja.applicationmodels.User;
import dy.fi.maja.repositories.TemperatureRepository;
import dy.fi.maja.repositories.UserRepository;
import dy.fi.maja.utils.Settings;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jarno
 */
public class ApplicationRoot
{
    public static Settings applicationSettings;
    public static TemperatureRepository temperatureRepository;
    public static UserRepository userRepository;
    
    public static void main(String[] args)
    {
        // Get settings from file
        applicationSettings = initializeSettings();
        
        initializeDatabaseConnections();
    }
    
    private static void initializeDatabaseConnections()
    {
        MongoClient client = new MongoClient(applicationSettings.getDatabaseSettings().getUrl());
        temperatureRepository = new TemperatureRepository(client);
        userRepository = new UserRepository(client);
    }
    
    private static Settings initializeSettings()
    {
        Gson g = new Gson();
        Settings s = null;
        try
        {
            s = g.fromJson(new FileReader("settings.json"), Settings.class);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Cannot find settings-json -file");
            System.exit(0);
        }
        
        return s;
    }
}
