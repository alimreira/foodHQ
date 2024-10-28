package com.eatwell.foodHQ.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDate;
import java.util.Date;

//verifies credentials sent by a user
public class JwtUserNamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Autowired
    public JwtUserNamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                                                throws AuthenticationException {
        try {
            UserNamePasswordAuthenticationRequest authRequest = new ObjectMapper().readValue(request.getInputStream(),
                                                                                UserNamePasswordAuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authRequest.getUserName(), //userName is the principal
                    authRequest.getPassword()   //password is the credential.
            );
            Authentication authenticate = authenticationManager.authenticate(authentication);//authenticationManager checks if username exist and if the password is correct/validate password
            return authenticate;
        }catch (RuntimeException | IOException ex) {
            throw new RuntimeException(ex);
        }
        //return super.attemptAuthentication(request,response);
    }

    //generates token and sends back to the client (AbstractAuthenticationProcessingFilter) cmd + o
    @Override
    //This method is invoked only if attemptAuthentication() is executed successfully. But if the attemptAuthentication() fails, the
    //successfulAuthentication() will never be invoked
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
                                                throws IOException, ServletException {

       // String key = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities",authResult.getAuthorities())
                .setIssuedAt(new Date())
                //.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                //.signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                .signWith(secretKey)
                .compact();              //the hmacShaKeyFor should be long enough in order to generate secure keys

            //to send the above generated token to the client, we have to add it to the response header
        //response.addHeader("Authorization","Bearer " + token);
        response.addHeader(jwtConfig.getAuthorizationHeader(),jwtConfig.getTokenPrefix() + token);

    }
//request filters are classes that perform some form of validation on request before it reaches its destination api.
    //you can have as much filter as you want. The sequence of filters is not necessarily guaranteed.
}


