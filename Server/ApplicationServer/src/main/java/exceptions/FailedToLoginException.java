/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author fakero
 */
@ResponseStatus(UNAUTHORIZED)
public class FailedToLoginException extends RuntimeException
{
    public FailedToLoginException(String username)
    {
        super(format("Failed to login with username %s", username));
    }
}
