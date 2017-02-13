/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import authentication.JwtAuthFilter;
import authentication.JwtAuthenticationEntryPoint;
import authentication.JwtAuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author fakero
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private static JwtAuthFilter jwtAuthFilter;
    
    private static JwtAuthenticationProvider jwtAuthenticationProvider;
    
    private static JwtAuthenticationEntryPoint jwtAuthEndPoint;
    
    public static void init(JwtAuthFilter authFilter, JwtAuthenticationProvider authenticationProvider, JwtAuthenticationEntryPoint authEndPoint)
    {
        jwtAuthFilter = authFilter;
        jwtAuthenticationProvider = authenticationProvider;
        jwtAuthEndPoint = authEndPoint;
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth)  throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/login");
        http.csrf().ignoringAntMatchers("/user/create");

        http.authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/**/*")
                //.permitAll();
                .hasAuthority("ROLE_USER")
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEndPoint);
    }
}
