/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author fakero
 */
@ResponseStatus(BAD_REQUEST)
public class FailedToCreateUserException extends RuntimeException
{
    public FailedToCreateUserException()
    {
        super(format("Failed to create a new user"));
    }
}
