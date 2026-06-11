package com.example.jpademo.Security;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {


private final JwtUtils jwtUtils;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("AuthenticationFilter Executed");
        try{

            String jwt = parseJWT(request);

            if (jwt !=null && jwtUtils.validateJWT(jwt)){
                System.out.println("Token : " + jwt);

              String userId = jwtUtils.getUserIdFromToken(jwt);

              Claims claims = jwtUtils.getAllClaims(jwt);

              List<String> roles = claims.get("roles",List.class);

              List<GrantedAuthority> authorities = List.of();
                System.out.println("Roles : " + roles) ;

              if(roles != null){
                  authorities = roles.stream()
                          .map(role -> (GrantedAuthority)new SimpleGrantedAuthority(role))
                          .toList();


              }
              UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                      userId,null,authorities
              );

              authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

              SecurityContextHolder.getContext().setAuthentication(authentication);


             }


        }
        catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request,response);
    }

    private String parseJWT(HttpServletRequest request) {
return  jwtUtils.getJwtFromHeader(request);

    }
}
