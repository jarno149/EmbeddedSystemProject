/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.applicationmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongojack.*;

/**
 *
 * @author Jarno
 */
public class Temperature
{
    @Id
    private String id;
    @JsonProperty
    private String sensorname;
    @JsonProperty
    private double value;
    @JsonProperty
    private long timestamp;
    @JsonProperty
    private TemperatureUnit unit;

    public Temperature()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getSensorname()
    {
        return sensorname;
    }

    public void setSensorname(String sensorname)
    {
        this.sensorname = sensorname;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }
    
    
    
    public static class TemperatureUnit
    {
        @JsonProperty
        private String suffix;
        @JsonProperty
        private Unit unit;

        public TemperatureUnit()
        {
        }

        public String getSuffix()
        {
            return suffix;
        }

        public void setSuffix(String suffix)
        {
            this.suffix = suffix;
        }

        public Unit getUnit()
        {
            return unit;
        }

        public void setUnit(Unit unit)
        {
            this.unit = unit;
        }
        
        
        
        public static enum Unit
        {
            Celsius,
            Farenheit
        }
    }
}
