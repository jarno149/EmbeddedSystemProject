/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.services;

import dy.fi.maja.applicationmodels.MinimalUser;
import dy.fi.maja.applicationmodels.User;
import dy.fi.maja.repositories.UserRepository;
import dy.fi.maja.applicationmodels.DetailedUser;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author fakero
 */
public class UserService
{
    private static UserRepository userRepository;
    
    public UserService(UserRepository userRepo)
    {
        userRepository = userRepo;
    }
    
    protected User get(String username)
    {
        return userRepository.getByUsername(username);        
    }
    
    public MinimalUser minimal(String username)
    {
        return new MinimalUser(get(username));
    }
    
    public DetailedUser detailed(String username)
    {
        return new DetailedUser(get(username));
    }
    
    public MinimalUser create(User user)
    {
        if(user.getUsername() != null && user.getPassword() != null && user.getFirstname() != null)
        {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            userRepository.insert(user);
            return new MinimalUser(user);
        }
        else
        {
            return null;
        }
    }
}
