/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.services;

import dy.fi.maja.applicationmodels.MinimalUser;
import dy.fi.maja.applicationmodels.User;
import dy.fi.maja.repositories.UserRepository;
import org.springframework.stereotype.Component;
import dy.fi.maja.applicationmodels.DetailedUser;

/**
 *
 * @author fakero
 */
@Component
public class UserService
{
    private static UserRepository userRepo;
    
    public void ProfileService(UserRepository userRepository) {
        userRepo = userRepository;
    }
    
    protected User get(String username){
        return userRepo.getByUsername(username);
    }
    
    public MinimalUser minimal(String username) {
        return new MinimalUser(get(username));
    }
    
    public DetailedUser detailed(String username) {
        return new DetailedUser(get(username));
    }
    
}
