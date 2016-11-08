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
    public String Name;
    public String MAC;
    public String PIN;
    public String[] Topics;
    
    public RemoteDevice RemoteDevice;

    public Device(String Name, String MAC, String PIN, String[] topics)
    {
        this.Name = Name;
        this.MAC = MAC;
        this.PIN = PIN;
        this.Topics = topics;
    }
    
    
}
