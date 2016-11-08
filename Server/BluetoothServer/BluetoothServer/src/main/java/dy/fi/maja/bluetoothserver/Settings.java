/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author Jarno
 */
public class Settings
{
    public static String LogPath;
    public static String BrokerUrl;
    public static String BrokerPort;
    public static String BrokerUsername;
    public static String BrokerPassword;
    public static ArrayList<Device> Devices;
    
    public static void readSettings()
    {
        Devices = new ArrayList<Device>();
        JSONParser parser = new JSONParser();
        
        try
        {
            Object o = parser.parse(new FileReader("config.json"));
            JSONObject jsonObject = (JSONObject)o;
            
            LogPath = (String)jsonObject.get("LogPath");
            BrokerUrl = (String)jsonObject.get("BrokerUrl");
            BrokerPort = (String)jsonObject.get("BrokerPort");
            BrokerUsername = (String)jsonObject.get("BrokerUsername");
            BrokerPassword = (String)jsonObject.get("BrokerPassword");
            
            
            JSONArray devs = (JSONArray)jsonObject.get("Devices");
            for (Object dev : devs)
            {
                JSONObject obj = (JSONObject)dev;
                String[] topics = JsonArrayToStringArray((JSONArray)obj.get("Topics"));
                if(topics == null)
                    topics = new String[0];
                Devices.add(new Device((String)obj.get("Name"), (String)obj.get("MAC"), (String)obj.get("PIN"), topics));
            }
        }
        catch (FileNotFoundException e)
        {
            ANSI.printRed("Config file not found");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch(ParseException e)
        {
            ANSI.printRed("Invalid JSON in config file");
        }
        catch (Exception e)
        {
            ANSI.printRed(e.getLocalizedMessage());
        }
    }
    
    public static Device getDeviceByAddress(String address)
    {
        for (Device dev : Devices)
        {
            if(dev.MAC.equals(address) || dev.MAC.replace(":","").equals(address))
                return dev;
        }
        return null;
    }
    
    private static String[] JsonArrayToStringArray(JSONArray array)
    {
        ArrayList<String> strings = new ArrayList<String>();
        for (int i = 0; i < array.size(); i++)
        {
            strings.add(array.get(i).toString());
        }
        return strings.toArray(new String[0]);
    }
}
