/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author Jarno
 */
public class SensorConnection implements Runnable
{
    private RemoteDevice remoteDevice;
    private String deviceName;
    private boolean hasbeenConnected = false;
    
    public SensorConnection(RemoteDevice device)
    {
        this.remoteDevice = device;
        try
        {
            this.deviceName = device.getFriendlyName(true);
        } catch (Exception e)
        {
            this.deviceName = device.getBluetoothAddress();
        }
    }
    
    @Override
    public void run()
    {
        while (true)
        {         
            try
            {
                StreamConnection connection = (StreamConnection)Connector.open(String.format("btspp://%s:1;master=true;encrypt=true;", remoteDevice.getBluetoothAddress()));
                ANSI.printGreen(deviceName + " is connected...");
                hasbeenConnected = true;
                DataInputStream input = connection.openDataInputStream();
                DataOutputStream output = connection.openDataOutputStream();
                InputStreamReader reader = new InputStreamReader(input);
                StringBuilder sb = new StringBuilder();
                
                int failCounter = 100;
                while(true)
                {
                    output.write(0);
                    if(reader.ready())
                    {
                        char[] b = new char[400];
                        reader.read(b);
                        String inString = new String(b);
                        if(inString.contains("#"))
                        {
                            System.out.println(sb.toString() + inString.split("#", 0)[0]);
                            sb.setLength(0);
                            sb.append(inString.split("#", 0)[1]);
                            output.write(0);
                            output.flush();
                        }
                        else if(!inString.trim().equals(""))
                        {
                            sb.append(inString);
                        }
                    }
                    Sleep(100);
                }
            }
            catch (Exception e)
            {
                if(hasbeenConnected)
                {
                    ANSI.printRed(this.deviceName + " Connection lost. Reconnecting...");
                    this.hasbeenConnected = false;
                }  
                else
                    ANSI.printRed("Cannot connect to " + this.deviceName);
                Sleep(1000);
            }
        }
    }
    
    private void Sleep(int ms)
    {
        try
        {
            Thread.sleep(ms);
        } catch (Exception e)
        {
        }
    }
}
