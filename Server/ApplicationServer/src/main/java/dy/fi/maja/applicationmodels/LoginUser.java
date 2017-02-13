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
public class LoginUser
{
    private final String username;
    private final String firstname;
    private final String token;
    
    public LoginUser(MinimalUser user, String jwt)
    {
        username = user.getUsername();
        firstname = user.getFirstname();
        token = jwt;
    }
}
