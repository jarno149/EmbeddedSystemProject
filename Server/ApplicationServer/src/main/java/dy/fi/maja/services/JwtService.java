/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.services;

import authentication.SecretKeyProvider;
import dy.fi.maja.applicationmodels.MinimalUser;
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
    private SecretKeyProvider secretKeyProvider;

    @SuppressWarnings("unused")
    public JwtService()
    {
        this(null);
    }

    public JwtService(SecretKeyProvider secretKeyProvider)
    {
        this.secretKeyProvider = secretKeyProvider;
    }

    public String tokenFor(MinimalUser minimalUser) throws IOException, URISyntaxException
    {
        byte[] secretKey = secretKeyProvider.getKey();
        Date expiration = Date.from(LocalDateTime.now().plusHours(2).toInstant(UTC));
        return Jwts.builder()
                .setSubject(minimalUser.getUsername())
                .setExpiration(expiration)
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
