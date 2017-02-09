/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.services;

import authentication.SecretKeyProvider;
import dy.fi.maja.applicationmodels.MinimalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import static java.time.ZoneOffset.UTC;
import java.util.Date;

/**
 *
 * @author fakero
 */
public class JwtService
{
    private static final String ISSUER = "dy.fi.maja.jwt";
    private static final String USERNAME = "username";
    private final SecretKeyProvider secretKeyProvider;
    private final UserService userService;

    @SuppressWarnings("unused")
    public JwtService()
    {
        this(null, null);
    }

    public JwtService(SecretKeyProvider secretKeyProvider, UserService userService)
    {
        this.secretKeyProvider = secretKeyProvider;
        this.userService = userService;
    }

    public String tokenFor(MinimalUser minimalUser) throws IOException, URISyntaxException
    {
        byte[] secretKey = secretKeyProvider.getKey();
        Date expiration = Date.from(LocalDateTime.now().toInstant(UTC));
        return Jwts.builder()
                .setSubject(minimalUser.getUsername())
                .setExpiration(expiration)
                .setIssuer(ISSUER)
                .claim(USERNAME, minimalUser.getUsername())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
    
    public MinimalUser verify(String token) throws IOException, URISyntaxException
    {
        byte[] secretKey = secretKeyProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return userService.minimal(claims.getBody().get(USERNAME).toString());
    }
}
