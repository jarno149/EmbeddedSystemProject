/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.bluetooth.*;

/**
 *
 * @author Jarno
 */
public class Main
{
    public static Date startTime;
    public static DateFormat dateFormat;
    
    public static void main(String[] args)
    {
        if(System.getProperty("os.name").toLowerCase().startsWith("windows"))
            ANSI.useColors = false;
        
        startTime = new Date();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        ANSI.printGreen("BluetoothService started\t\t" + dateFormat.format(startTime));
        
        // Read settings first
        Settings.readSettings();
        
        if(Settings.LogPath != null)
            Logger.Init(Settings.LogPath);
        
        LocalDevice device = getLocalDevice();
        
        Thread scanThread = new Thread(new Inquiry(device));
        
        while (!allDevicesConfigured())
        {
            try
            {
                scanThread.run();
                scanThread.join();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            
            if(!allDevicesConfigured())
            {
                try
                {
                    Thread.sleep(5000);
                    scanThread.interrupt();
                } catch (Exception e) { e.printStackTrace(); }
                ANSI.printRed("Scanning again. Cannot find all configured devices...");
            }
        }
        
        ANSI.printGreen("All devices configured...");
        
        for (Device dev : Settings.Devices)
        {
            if(dev.RemoteDevice != null)
            {
                Thread t = new Thread(new SensorConnection(dev));
                t.run();
            }
        }
        
        // Keeping program alive
        while (true){ }
    }
    
    // Try to get localDevice in while-loop
    public static LocalDevice getLocalDevice()
    {
        int retryTimeOut = 2000;
        
        while (true)
        {
            try
            {
                LocalDevice locDev = LocalDevice.getLocalDevice();
                return locDev;
            } catch (BluetoothStateException e)
            {
                ANSI.printRed("Cannot find localdevice");
            }

            try
            {
                Thread.sleep(retryTimeOut);
            } catch (InterruptedException e)
            {
                ANSI.printRed("Something went wrong... " + e.getLocalizedMessage());
            }
        }
    }
    
    private static boolean allDevicesConfigured()
    {
        for (Device dev : Settings.Devices)
        {
            if(dev.RemoteDevice == null)
                return false;
        }
        return true;
    }
}
