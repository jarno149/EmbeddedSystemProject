/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.datagenerator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.omg.CORBA.Environment;


/**
 *
 * @author Jarno
 */
public class Settings
{
    public static String BrokerUrl;
    public static String BrokerPort;
    public static String BrokerUsername;
    public static String BrokerPassword;
    public static String BrokerTopic;
    public static int Interval;
    
    public static void readSettings()
    {
        JSONParser parser = new JSONParser();
        
        try
        {
            Object o = parser.parse(new FileReader("config.json"));
            JSONObject jsonObject = (JSONObject)o;
            
            BrokerUrl = (String)jsonObject.get("BrokerUrl");
            BrokerPort = (String)jsonObject.get("BrokerPort");
            BrokerPassword = (String)jsonObject.get("BrokerPassword");
            BrokerUsername = (String)jsonObject.get("BrokerUsername");
            BrokerTopic = (String)jsonObject.get("BrokerTopic");
            String interval = (String)jsonObject.get("Interval");
            try
            {
                Interval = Integer.parseInt(interval);
            }
            catch (Exception e)
            {
                System.out.println("Invalid value in Interval -field.");
                System.exit(0);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("config.json not found.");
            System.exit(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
