/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.controllers;

import dy.fi.maja.applicationmodels.User;
import dy.fi.maja.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import dy.fi.maja.services.UserService;

/**
 *
 * @author fakero
 */
@RestController
@RequestMapping("/user")
public class UserController
{
    private static UserRepository userRepository;
    private static UserService userService;
    
    public static void initController(UserRepository userRepository, UserService userService) {
        UserController.userRepository = userRepository;
        UserController.userService = userService;
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public void createUser(String id, String firstname, String lastname, String username, String password, String[] roles){
        User u = new User();
        u.setId(id);
        u.setFirstname(firstname);
        u.setLastname(lastname);
        u.setUsername(username);
        u.setPassword(password);
        u.setRoles(roles);
        if(u.getUsername() != null && u.getPassword() != null && u.getId() != null){
            userRepository.insert(u);
        }
    }
    
    
    
}
