/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.repositories;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import dy.fi.maja.applicationmodels.User;
import java.util.Optional;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

/**
 *
 * @author Jarno
 */
public class UserRepository
{
    private JacksonDBCollection<User, String> collection;

    public UserRepository(MongoClient client)
    {
        DBCollection dBCollection = client.getDB("Users").getCollection("Users");
        collection = JacksonDBCollection.wrap(dBCollection, User.class, String.class);
    }
    
    private User[] cursorToArray(DBCursor<User> t)
    {
        User[] users = new User[t.count()];
        int counter = 0;
        while(t.hasNext())
        {
            users[counter] = t.next();
            counter++;
        }
        return users;
    }
    
    public User getById(String id)
    {
        return this.collection.findOneById(id);
    }
    
    public User[] getByRole(String role)
    {
        DBCursor<User> cursor = this.collection.find().in("roles", role);
        return cursorToArray(cursor);
    }
    
    public void insert(User u)
    {
        this.collection.insert(u);
    }
    
    public User getByUsername(String username)
    {
        return this.collection.findOne(DBQuery.is("username", username));
    }    
}
