/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.applicationmodels;

import lombok.Data;

/**
 *
 * @author fakero
 */
@Data
public class DetailedUser
{
    private final String username;
    private final String firstname;
    private final String lastname;
    
    public DetailedUser(User user){
        username = user.getUsername();
        firstname = user.getFirstname();
        lastname = user.getLastname();
    }
}
