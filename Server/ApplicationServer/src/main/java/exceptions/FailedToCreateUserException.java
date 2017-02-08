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
public class FailedToCreateUserException extends RuntimeException
{
    public FailedToCreateUserException()
    {
        super(format("Failed to create a new user"));
    }
}
