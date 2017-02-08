/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.applicationserver;

import authentication.SecretKeyProvider;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import dy.fi.maja.applicationmodels.Temperature;
import dy.fi.maja.applicationmodels.User;
import dy.fi.maja.controllers.LoginController;
import dy.fi.maja.controllers.TemperatureController;
import dy.fi.maja.controllers.UserController;
import dy.fi.maja.repositories.TemperatureRepository;
import dy.fi.maja.repositories.UserRepository;
import dy.fi.maja.services.JwtService;
import dy.fi.maja.services.LoginService;
import dy.fi.maja.services.MqttTemperatureService;
import dy.fi.maja.services.UserService;
import dy.fi.maja.utils.Settings;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author Jarno
 */

@SpringBootApplication
@ComponentScan(basePackages = {"dy.fi.maja.controllers", "configuration"})
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class ApplicationRoot
{
    public static Settings applicationSettings;
    public static TemperatureRepository temperatureRepository;
    public static UserRepository userRepository;
    
    public static MqttTemperatureService mqttService;
    public static SecretKeyProvider keyProvider;
    public static LoginService loginService;
    public static JwtService jwtService;
    public static UserService userService;
    
    public static void main(String[] args)
    {
        // Get settings from file
        applicationSettings = initializeSettings();
        initializeDatabaseConnections();
        
        TemperatureController.initRepository(temperatureRepository);
        UserController.initController(userRepository);

                
        keyProvider = new SecretKeyProvider();        
        jwtService = new JwtService(keyProvider);
        userService = new UserService(userRepository);
        loginService = new LoginService(userService);

        LoginController.initController(loginService, jwtService);
                
        System.getProperties().put("server.port", applicationSettings.getServerSettings().getPort());
        SpringApplication.run(ApplicationRoot.class, args);
        
        mqttService = new MqttTemperatureService(applicationSettings.getTemperatureMqttSettings(), temperatureRepository);
        Thread mqttServiceThread = new Thread(mqttService);
        mqttServiceThread.start();
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
