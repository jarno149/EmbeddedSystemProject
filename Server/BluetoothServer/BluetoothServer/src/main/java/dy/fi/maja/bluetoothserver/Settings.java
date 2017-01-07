/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 *
 * @author Jarno
 */
public class Settings
{
    private String BrokerUrl;
    private int BrokerPort;
    private String BrokerUsername;
    private String BrokerPassword;
    private String LogPath;
    private boolean PairDevices;
    private Device[] Devices;
    
    public static Settings readSettings(String fileName)
    {
        Gson gson = new Gson();
        try
        {
            Settings settings = gson.fromJson(new FileReader(fileName), Settings.class);
            return settings;
        }
        catch (FileNotFoundException e)
        {
            ColorPrint.printRed("Cannot " + fileName + " -file");
            System.exit(0);
        }
        catch(Exception e)
        {
            ColorPrint.printRed("Cannot read settings.json -file");
            ColorPrint.printRed(e.getLocalizedMessage());
            System.exit(0);
        }
        return null;
    }

    public String getBrokerUrl()
    {
        return BrokerUrl;
    }

    public int getBrokerPort()
    {
        return BrokerPort;
    }

    public String getBrokerUsername()
    {
        return BrokerUsername;
    }

    public String getBrokerPassword()
    {
        return BrokerPassword;
    }

    public String getLogPath()
    {
        return LogPath;
    }
    
    public boolean getPairDevices()
    {
        return PairDevices;
    }

    public Device[] getDevices()
    {
        return Devices;
    }
}
