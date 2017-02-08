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
public class MinimalUser
{
    private final String username;
    private final String firstname;
    
    public MinimalUser(User user)
    {
        username = user.getUsername();
        firstname = user.getFirstname();
    }
}
