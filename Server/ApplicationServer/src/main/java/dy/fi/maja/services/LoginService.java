/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.services;

import authentication.LoginCredentials;
import dy.fi.maja.applicationmodels.MinimalUser;
import dy.fi.maja.applicationmodels.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author fakero
 */
public class LoginService
{
    private UserService userService;
    
    @SuppressWarnings("unused")
    public LoginService()
    {
        this(null);
    }
    
    public LoginService(UserService userService)
    {
        this.userService = userService;
    }
    
    public MinimalUser login(LoginCredentials credentials)
    {
        User user = userService.get(credentials.getUsername());
        if(BCrypt.checkpw(credentials.getPassword(), user.getPassword()))
        {
            return new MinimalUser(user);
        }
        else
        {
            return null;
        }
    }
}
