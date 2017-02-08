/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.controllers;

import authentication.LoginCredentials;
import dy.fi.maja.applicationmodels.MinimalUser;
import dy.fi.maja.services.JwtService;
import dy.fi.maja.services.LoginService;
import exceptions.FailedToLoginException;
import javax.servlet.http.HttpServletResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fakero
 */
@RestController
@RequestMapping(path = "/login")
public class LoginController
{
    private static LoginService loginService;
    private static JwtService jwtService;
    
    public static void initController(LoginService loginSer, JwtService jwtSer)
    {
        loginService = loginSer;
        jwtService = jwtSer;
    }

    @RequestMapping(path = "", method = POST, produces = APPLICATION_JSON_VALUE)
    public MinimalUser login(@RequestBody LoginCredentials credentials, HttpServletResponse response)
    {
        MinimalUser user = loginService.login(credentials);
        if(user != null)
        {
            try
            {
                response.setHeader("Token", jwtService.tokenFor(user));
                return user;
            }
            catch(Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            throw new FailedToLoginException(credentials.getUsername());
        }
    }
}
