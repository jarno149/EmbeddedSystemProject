/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.bluetooth.LocalDevice;

/**
 *
 * @author Jarno
 */
public class ApplicationRoot
{
    public static Settings applicationSettings;
    private static Date startTime;
    private static DateFormat dateFormat;
    
    public static void main(String[] args)
    {
        ColorPrint.initPrinter();
        applicationSettings = Settings.readSettings("config.json");
        
        startTime = new Date();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        ColorPrint.printGreen("BluetoothServer started\t\t" + dateFormat.format(startTime));
        
        LocalDevice localDevice = getLocalDevice();
        
        
        // Check if devices needs to be paired
        if(applicationSettings.getPairDevices())
        {
            // Try to pair devices
        }
        else
        {
            // Create device threads
            for(Device dev : applicationSettings.getDevices())
            {
                Thread t = new Thread(new SensorConnection(dev));
                t.run();
            }
        }
    }
    
    private static boolean allDevicesConfigured()
    {
        for(Device dev : applicationSettings.getDevices())
        {
            if(dev.getRemoteDevice() == null)
                return false;
        }
        return true;
    }
    
    private static LocalDevice getLocalDevice()
    {
        int retryTimeout = 2000;
        
        while(true)
        {
            try
            {
                LocalDevice localDevice = LocalDevice.getLocalDevice();
                return  localDevice;
            }
            catch (Exception e)
            {
                ColorPrint.printRed("Cannot find local bluetooth device. Trying again...");
            }
            
            try
            {
                Thread.sleep(retryTimeout);
            } catch (Exception e){}
        }
    }
}
