/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationModels;

/**
 *
 * @author Jarno
 */
public class Temperature
{
    private String sensorname;
    private double value;
    private long timestamp;
    private TemperatureUnit unit;

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }
    
    
    
    public static class TemperatureUnit
    {
        private String suffix;
        private String unit;
    }
}
