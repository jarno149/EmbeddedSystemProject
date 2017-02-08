/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advices;

import exceptions.FailedToCreateUserException;
import exceptions.FailedToLoginException;
import exceptions.UserNotFoundException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author fakero
 */
@ControllerAdvice
public class GlobalExceptionHandler
{
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public void profileNotFound() {}

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(FailedToLoginException.class)
    public void failedToLogin() {}
    
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(FailedToCreateUserException.class)
    public void failedToCreateUser() {}
}
