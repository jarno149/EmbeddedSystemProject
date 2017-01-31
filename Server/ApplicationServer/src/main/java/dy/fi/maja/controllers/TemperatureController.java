/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.controllers;

import dy.fi.maja.applicationmodels.Temperature;
import dy.fi.maja.repositories.TemperatureRepository;
import java.util.List;
import java.util.Optional;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiParams;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.springframework.context.annotation.ComponentScan;
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
}
