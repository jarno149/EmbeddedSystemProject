/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

import com.intel.bluetooth.RemoteDeviceHelper;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import sun.util.resources.cldr.zh.LocaleNames_zh_Hant_HK;

/**
 *
 * @author Jarno
 */
public class Inquiry implements Runnable
{
    private LocalDevice device;
    private boolean started;
    public Inquiry(LocalDevice dev)
    {
        this.device = dev;
        started = true;
    }
    
    @Override
    public void run()
    {
        DiscoveryListener listener = new DiscoveryListener()
        {
            @Override
            public void deviceDiscovered(RemoteDevice rd, DeviceClass dc)
            {
                ANSI.nextLine();
                try
                {
                    ANSI.printCyan("Found device: " + rd.getFriendlyName(true) + "\t@ " + rd.getBluetoothAddress());
                } catch (Exception e)
                {
                    ANSI.printCyan("Found device: \t@ " + rd.getBluetoothAddress());
                }
                
                boolean configured = false;
                for (Device dev : Settings.Devices)
                {
                    if(dev.MAC.replace(":", "").equals(rd.getBluetoothAddress()))
                    {
                        dev.RemoteDevice = rd;
                        ANSI.printGreen("\tDevice is configured.");
                        configured = true;
                        authenticateDevice(rd, dev.PIN);
                    }
                }
                if(!configured)
                {
                    ANSI.printRed("\tDevice  is not configured.");
                }
            }

            @Override
            public void servicesDiscovered(int i, ServiceRecord[] srs)
            {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void serviceSearchCompleted(int i, int i1)
            {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void inquiryCompleted(int i)
            {
                ANSI.nextLine();
                ANSI.printCyan("Scan completed.");
                started = false;
            }
        };
        try
        {
            ANSI.nextLine();
            ANSI.printPurple("Start scanning...");
            this.device.getDiscoveryAgent().startInquiry(DiscoveryAgent.LIAC, listener);
        }catch (Exception e) { e.printStackTrace(); }
        
        // Keep thread alive
        while(started)
        {
            try
            {
                Thread.sleep(500);
            } catch (Exception e)
            {
            }
        }
        this.device.getDiscoveryAgent().cancelInquiry(listener);
    }
    
    private static void authenticateDevice(RemoteDevice dev, String pin)
    {
        if(dev.isAuthenticated())
            return;
        try
        {
            RemoteDeviceHelper.authenticate(dev, pin);
            ANSI.printGreen("\tDevice is authenticated.");
        } catch (Exception e){ e.printStackTrace(); }
    }
}
