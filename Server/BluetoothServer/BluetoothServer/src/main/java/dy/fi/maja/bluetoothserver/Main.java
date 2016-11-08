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
    
    public static void main(String[] args)
    {
        startTime = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        System.out.println(ANSI.GREEN + "BluetoothService started\t\t" + dateFormat.format(startTime) + ANSI.RESET);
        
        // Read settings first
        Settings.readSettings();
        
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
                System.out.println("Cannot find localdevice...");
            }

            try
            {
                Thread.sleep(retryTimeOut);
            } catch (InterruptedException e)
            {
                System.out.println("Something went wrong... " + e.getLocalizedMessage());
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
