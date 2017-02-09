/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.bluetoothserver;

import javax.bluetooth.RemoteDevice;

/**
 *
 * @author Jarno
 */
public class Device
{
    private String Name;
    private String MAC;
    private int PIN;
    private String[] Topics;
    private transient RemoteDevice RemoteDevice;
    
    public String getName()
    {
        return Name;
    }

    public String getMAC()
    {
        return MAC;
    }

    public int getPIN()
    {
        return PIN;
    }

    public String[] getTopics()
    {
        return Topics;
    }

    public RemoteDevice getRemoteDevice()
    {
        return RemoteDevice;
    }

    public void setRemoteDevice(RemoteDevice RemoteDevice)
    {
        this.RemoteDevice = RemoteDevice;
    }
}
