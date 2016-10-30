/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    public static String LoggingUrl;
    public static ArrayList<Device> Devices;
    
    public static void readSettings()
    {
        Devices = new ArrayList<Device>();
        JSONParser parser = new JSONParser();
        
        try
        {
            Object o = parser.parse(new FileReader("config.json"));
            JSONObject jsonObject = (JSONObject)o;
            
            LoggingUrl = (String)jsonObject.get("LoggingUrl");
            JSONArray devs = (JSONArray)jsonObject.get("Devices");
            for (Object dev : devs)
            {
                JSONObject obj = (JSONObject)dev;
                Devices.add(new Device((String)obj.get("Name"), (String)obj.get("MAC"), (String)obj.get("PIN")));
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Config file not found");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch(ParseException e)
        {
            System.err.println("Invalid JSON in config file");
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
}
