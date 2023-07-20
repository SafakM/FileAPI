package com.safak.filemanagement.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value(("${app.jwt-expiration-miliseconds}"))
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication){
        String username=authentication.getName();
        String rolename=authentication.getAuthorities().toString();
        Date currentDate=new Date();
        Date expireDate=new Date(currentDate.getTime()+jwtExpirationDate);

        String token= Jwts.builder()
                .claim("Roles",rolename)
                .claim("username",username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    public String getUsername(String token){
        Claims claims=Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username=claims.get("username").toString();
        return username;
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
