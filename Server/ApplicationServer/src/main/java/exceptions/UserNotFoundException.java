/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import static java.lang.String.format;

/**
 *
 * @author fakero
 */

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(String username)
    {
        super(format("User %s does not exist", username));
    }
}