package com.example.jpademo.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;


@Component
public class JwtUtils {

    private static final String JWTToken ="mySecretKeymySecretKeymySecretKey12345";
    private static final int jwtExpireMs =172800000;


    public String getJwtFromHeader (HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {

            return token.substring( 7);


        }
        return null;

    }


    public String generateToken( String userId , String role){

        return  Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpireMs))
                .claim("roles" , List.of(role))
                .signWith(key())
                .compact();



    }

    public boolean validateJWT (String jwtToken){

try{

Jwts.parser().verifyWith((SecretKey) key()).build()
        .parseSignedClaims(jwtToken);
return true;

}

catch (Exception e){
    e.printStackTrace();
}
return false;
    }


    public Key key (){
return Keys.hmacShaKeyFor(JWTToken.getBytes(StandardCharsets.UTF_8));
    }


    public String getUserIdFromToken(String jwt){

       return Jwts.parser().verifyWith((SecretKey) key()).build()
               .parseSignedClaims(jwt).getPayload().getSubject();


    }

    public Claims getAllClaims (String jwt) {
        return  Jwts.parser().verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(jwt).getPayload();
    }
}
