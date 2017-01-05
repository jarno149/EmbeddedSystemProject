/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.controllers;

import dy.fi.maja.applicationmodels.Temperature;
import dy.fi.maja.repositories.TemperatureRepository;
import java.util.Optional;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Temperatures")
public class TemperatureController
{
    private static TemperatureRepository repo;
    
    public static void initRepository(TemperatureRepository repository)
    {
        repo = repository;
    }
    
    @RequestMapping(value = "/lastDay", method = RequestMethod.GET, produces = "application/json")
    public Temperature[] getLastDayTemperatures()
    {
        long currTimestamp = System.currentTimeMillis();
        long dayTimestamp = 86400000;
        return repo.getBetweenTimestamps(currTimestamp - dayTimestamp, dayTimestamp);
    }
    
    @RequestMapping(value = "/sensorName", method = RequestMethod.GET, produces = "application/json")
    public Temperature[] getSensorsTemperatures(@RequestParam String sensorName, @RequestParam Optional<String> count)
    {
        Temperature[] temps;
        int c = Integer.parseInt(count.orElse("-1"));
        if(c == -1)
            return repo.getBySensorname(sensorName);
        else
            return repo.getBySensorname(sensorName, c);
    }
}
