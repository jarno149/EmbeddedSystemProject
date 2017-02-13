/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.applicationmodels;

/**
 *
 * @author jarno
 */
public class SensorMeasurementDay
{
    private String sensorName;
    private String measurementDate;
    private double averageTemperature;

    public SensorMeasurementDay(String sensorName, String measurementDate, double averageTemperature)
    {
        this.sensorName = sensorName;
        this.measurementDate = measurementDate;
        this.averageTemperature = averageTemperature;
    }

    public String getSensorName()
    {
        return sensorName;
    }

    public void setSensorName(String sensorName)
    {
        this.sensorName = sensorName;
    }

    public String getMeasurementDate()
    {
        return measurementDate;
    }

    public void setMeasurementDate(String measurementDate)
    {
        this.measurementDate = measurementDate;
    }

    public double getAverageTemperature()
    {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature)
    {
        this.averageTemperature = averageTemperature;
    }
    
    
}
