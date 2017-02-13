/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.controllers;

import dy.fi.maja.applicationmodels.DetailedUser;
import dy.fi.maja.applicationmodels.MinimalUser;
import dy.fi.maja.applicationmodels.User;
import dy.fi.maja.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dy.fi.maja.services.UserService;
import exceptions.FailedToCreateUserException;
import exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.RequestBody;
/**
 *
 * @author fakero
 */
@RestController
@RequestMapping("/user")
public class UserController
{
    private static UserService userService;
    
    public static void initController(UserService userSer)
    {
        userService = userSer;
    }
    
    @RequestMapping(path = "/create", method = POST)
    public MinimalUser createUser(@RequestBody User user)
    {
        MinimalUser min = userService.create(user);
        if(min != null)
            return min;
        else
            throw new FailedToCreateUserException();
         
    }
    
    @RequestMapping(path = "/{username}", method = GET, produces = APPLICATION_JSON_VALUE)
    public MinimalUser minimal(@PathVariable String username)
    {
        try
        {
            return userService.minimal(username);
        }
        catch(Exception e)
        {
            throw new UserNotFoundException(username);
        }        
    }
    
    @RequestMapping(path = "/details/{username}", method = GET, produces = APPLICATION_JSON_VALUE)
    public DetailedUser details(@PathVariable String username)
    {
        
        try
        {
            return userService.detailed(username);
        }
        catch(Exception e)
        {
            throw new UserNotFoundException(username);
        } 
    }
    
    
}
