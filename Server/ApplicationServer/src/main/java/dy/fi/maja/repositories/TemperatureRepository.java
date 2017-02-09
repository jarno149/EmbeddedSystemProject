/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.repositories;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import dy.fi.maja.applicationmodels.Temperature;
import java.util.List;
import org.mongojack.Aggregation;
import org.mongojack.DBCursor;
import static org.mongojack.DBQuery.*;
import org.mongojack.DBSort;
import org.mongojack.JacksonDBCollection;

/**
 * @author Jarno
 */
public class TemperatureRepository
{
    private JacksonDBCollection<Temperature, String> collection;
    
    public TemperatureRepository(MongoClient client)
    {
        DBCollection dBCollection = client.getDB("Sensors").getCollection("Temperatures");
        this.collection = JacksonDBCollection.wrap(dBCollection, Temperature.class, String.class);
    }
    
    private Temperature[] cursorToArray(DBCursor<Temperature> t)
    {
        Temperature[] temps = new Temperature[t.count()];
        int counter = 0;
        while(t.hasNext())
        {
            temps[counter] = t.next();
            counter++;
        }
        return temps;
    }
    
    public Temperature[] getBySensorname(String sensorName)
    {
        DBCursor<Temperature> cursor = this.collection.find().is("sensorname", sensorName);
        return cursorToArray(cursor);
    }
    
    public Temperature[] getAll()
    {
        DBCursor<Temperature> cursor = this.collection.find();
        return cursorToArray(cursor);
    }
    
    public List getSensorNames()
    {
        return this.collection.distinct("sensorname");
    }
    
    public Temperature[] getAll(int count)
    {
        DBCursor<Temperature> cursor = this.collection.find().sort(DBSort.asc("timestamp"));
        Temperature[] allItems = cursorToArray(cursor);
        Temperature[] temps = new Temperature[count];
        int counter = 0;
        for(int i = allItems.length-1; i > allItems.length - count-1 && i >= 0; i--)
        {
            temps[counter] = allItems[i];
            counter++;
        }
        return temps;
    }
    
    public Temperature[] getBySensorname(String sensorName, int count)
    {
        DBCursor<Temperature> cursor = this.collection.find().is("sensorname", sensorName).sort(DBSort.asc("timestamp"));
        Temperature[] allItems = cursorToArray(cursor);
        Temperature[] temps = new Temperature[count];
        int counter = 0;
        for(int i = allItems.length-1; i > allItems.length - count-1 && i >= 0; i--)
        {
            temps[counter] = allItems[i];
            counter++;
        }
        return temps;
    }
    
    public Temperature[] getBetweenTimestamps(long from, long to)
    {
        DBCursor<Temperature> cursor = this.collection.find().lessThan("timestamp", to).and(greaterThan("timestamp", from));
        return cursorToArray(cursor);
    }
    
    public Temperature getById(String id)
    {
        return this.collection.findOneById(id);
    }
    
    public void insert(Temperature t)
    {
        this.collection.insert(t);
    }
    
    public void delete(Temperature t)
    {
        this.collection.removeById(t.getId());
    }
    
    public void delete(String id)
    {
        this.collection.removeById(id);
    }
}
