/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import dy.fi.maja.applicationmodels.MinimalUser;
import dy.fi.maja.services.JwtService;
import exceptions.JwtAuthenticationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author fakero
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtService jwtService;

    @SuppressWarnings("unused")
    public JwtAuthenticationProvider() {
        this(null);
    }

    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        try 
        {
            MinimalUser possibleUser = jwtService.verify((String) authentication.getCredentials());
            return new JwtAuthenticatedUser(possibleUser);
            
        }
        catch(Exception e)
        {
            throw new JwtAuthenticationException("Failed to verify token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthToken.class.equals(authentication);
    }
}
