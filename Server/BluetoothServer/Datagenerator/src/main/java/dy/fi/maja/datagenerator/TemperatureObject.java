/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.datagenerator;

import java.text.DecimalFormat;
import java.util.Date;

/**
 *
 * @author Jarno
 */
public class TemperatureObject
{
    private String id;
    private double value;
    private TemperatureUnit unit;
    private long timeStamp;

    public TemperatureObject(String id, double value, TemperatureUnit unit, long timeStamp)
    {
        this.id = id;
        this.value = value;
        this.unit = unit;
        this.timeStamp = timeStamp;
    }

    public String getId()
    {
        return id;
    }

    public double getValue()
    {
        return value;
    }

    public TemperatureUnit getUnit()
    {
        return unit;
    }

    public long getTimeStamp()
    {
        return timeStamp;
    }
    
    
    
    public static class TemperatureUnit
    {
        private String suffix;
        private Unit unit;

        public TemperatureUnit(String suffix, Unit unit)
        {
            this.suffix = suffix;
            this.unit = unit;
        }
        
        
    }
    
    public enum Unit
    {
        Celsius,
        Farenheit
    }
}
