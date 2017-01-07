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

/**
 *
 * @author Jarno
 */
public class InquiryPairer implements Runnable
{
    private LocalDevice device;
    private boolean started;
    private static Device[] devices;
    
    public InquiryPairer(LocalDevice device, Device[] devices)
    {
        this.device = device;
        this.devices = devices;
    }

    @Override
    public void run()
    {
        DiscoveryListener listener = new DiscoveryListener()
        {
            @Override
            public void deviceDiscovered(RemoteDevice rd, DeviceClass dc)
            {
                ColorPrint.nextLine();
                try
                {
                    ColorPrint.printCyan("Found device: " + rd.getFriendlyName(true) + "\t@" + rd.getBluetoothAddress());
                }
                catch (Exception e)
                {
                    ColorPrint.printCyan("Found device: \t@" + rd.getBluetoothAddress());
                }
                
                boolean configured = false;
                for(Device dev : devices)
                {
                    if(dev.getMAC().replace(":", "").equals(rd.getBluetoothAddress()))
                    {
                        dev.setRemoteDevice(rd);
                        ColorPrint.printGreen("\tDevice is configured");
                        configured = true;
                        authenticateDevice(rd, dev.getPIN());
                    }
                }
                
                if(!configured)
                {
                    ColorPrint.printRed("\tCannot find configured device");
                }
            }

            @Override
            public void servicesDiscovered(int i, ServiceRecord[] srs)
            {
                
            }

            @Override
            public void serviceSearchCompleted(int i, int i1)
            {
                
            }

            @Override
            public void inquiryCompleted(int i)
            {
                ColorPrint.nextLine();
                ColorPrint.printCyan("Scan completed");
                started = false;
            }
            
            private void authenticateDevice(RemoteDevice dev, int pin)
            {
                if(dev.isAuthenticated())
                    return;
                try
                {
                    RemoteDeviceHelper.authenticate(dev, String.valueOf(pin));
                    ColorPrint.printGreen("tDevice is paired");
                }
                catch (Exception e)
                {
                    ColorPrint.printRed("Cannot authenticate device");
                }
            }
        };
        
        try
        {
            ColorPrint.nextLine();
            ColorPrint.printPurple("Start scanning devices...");
            this.device.getDiscoveryAgent().startInquiry(DiscoveryAgent.LIAC, listener);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        // Keep thread alive
        while(started)
        {
            try
            {
                Thread.sleep(500);
            } catch (Exception e){}
        }
        
        try
        {
            Thread.sleep(1000);
        } catch (Exception e){}
        
        this.device.getDiscoveryAgent().cancelInquiry(listener);
    }
    
}
