/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author fakero
 */
@ResponseStatus(NOT_FOUND)
public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(String username)
    {
        super(format("User %s does not exist", username));
    }
}