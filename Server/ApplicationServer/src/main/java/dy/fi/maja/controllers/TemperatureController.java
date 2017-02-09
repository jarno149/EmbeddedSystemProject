/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.controllers;

import dy.fi.maja.applicationmodels.SensorMeasurementDay;
import dy.fi.maja.applicationmodels.Temperature;
import dy.fi.maja.repositories.TemperatureRepository;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiParams;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(name = "Temperature service", description = "Methods for querying temperatures")
@RestController
@RequestMapping("/temperatures")
public class TemperatureController
{
    private static TemperatureRepository repo;
    
    public static void initRepository(TemperatureRepository repository)
    {
        repo = repository;
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public Temperature[] getAllTemperatures(@ApiQueryParam(description = "Count of results", name = "count", required = false)
    @RequestParam Optional<Integer> count)
    {
        int c = count.orElse(100);
        return repo.getAll(c);
    }
    
    @RequestMapping(value = "/lastday", method = RequestMethod.GET, produces = "application/json")
    public Temperature[] getLastDayTemperatures()
    {
        long currTimestamp = System.currentTimeMillis();
        long dayTimestamp = 86400000;
        return repo.getBetweenTimestamps(currTimestamp - dayTimestamp, currTimestamp);
    }
    
    @RequestMapping(value = "/byname", method = RequestMethod.GET, produces = "application/json")
    public Temperature[] getSensorsTemperatures(@ApiQueryParam(description = "Sensor name", name = "sensorname", required = true)
    @RequestParam String sensorname,
    @ApiQueryParam(description = "Count of results", name="count", required = false)
    @RequestParam Optional<Integer> count)
    {
        int c = count.orElse(100);
        return repo.getBySensorname(sensorname, c);
    }
    
    @RequestMapping(value = "/sensornames", method = RequestMethod.GET, produces = "application/json")
    public List getSensorNames()
    {
        return repo.getSensorNames();
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Temperature insertTemperature(@ApiPathParam(description = "Insert new temperature", name = "temperature")
    @RequestBody Temperature temperature)
    {
        if(temperature != null)
        {
            repo.insert(temperature);
        }
        return temperature;
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Temperature[]> getTemperatures(@ApiQueryParam(description = "Measurement date", name = "date")
    @RequestParam String date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date dateObject = formatter.parse(date);
            long dateMillis = dateObject.getTime();
            long dayLengthMillis = 86400000;
            Temperature[] values = repo.getBetweenTimestamps(dateMillis, dateMillis + dayLengthMillis);
            return new ResponseEntity(values, HttpStatus.OK);
        }
        catch(Exception e) {e.printStackTrace();}
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/measurements")
    public SensorMeasurementDay[] getMeasurementDates(@ApiQueryParam(description = "Sensor name", name = "sensorname")
        @RequestParam Optional<String> sensorname)
    {
        ArrayList<SensorMeasurementDay> measurementDays = new ArrayList<SensorMeasurementDay>();
        if(sensorname.isPresent())
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            HashMap<String, ArrayList<Temperature>> map = new HashMap<String, ArrayList<Temperature>>();
            Temperature[] sensorTemperatures = repo.getBySensorname(sensorname.get());
            for(Temperature temp : sensorTemperatures)
            {
                String dateString = formatter.format(new Date(temp.getTimestamp()));
                if(map.containsKey(dateString))
                {
                    map.get(dateString).add(temp);
                }
                else
                {
                    ArrayList<Temperature> temps = new ArrayList<Temperature>();
                    temps.add(temp);
                    map.put(dateString, temps);
                }
            }
            for(ArrayList<Temperature> temps : map.values())
            {
                double values = 0;
                int divider = 0;
                String dateString = null;
                for(Temperature temp : temps)
                {
                    values += temp.getValue();
                    divider++;
                    if(dateString == null)
                    {
                        dateString = formatter.format(new Date(temp.getTimestamp()));
                    }
                }
                SensorMeasurementDay measurementDay = new SensorMeasurementDay(sensorname.get(), dateString, values / divider);
                measurementDays.add(measurementDay);
            }
            return measurementDays.toArray(new SensorMeasurementDay[0]);
        }
        else
        {
            List<String> sensorNames = getSensorNames();
            for(String sensorName : sensorNames)
            {
                SensorMeasurementDay[] meas = getMeasurementDates(Optional.of(sensorName));
                for(SensorMeasurementDay measDate : meas)
                {
                    measurementDays.add(measDate);
                }
            }
            return measurementDays.toArray(new SensorMeasurementDay[0]);
        }
    }
}
