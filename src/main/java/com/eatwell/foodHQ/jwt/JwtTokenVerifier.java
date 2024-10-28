package com.eatwell.foodHQ.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//token generated in the successfulAuthentication() is verified in the JwtTokenVerifier, when
//it is sent with each request coming from the header
public class JwtTokenVerifier extends OncePerRequestFilter {
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    @Autowired
    public JwtTokenVerifier(SecretKey secretKey,
                            JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    //filter executes once per request.i.e invoke filter just once for each request coming from the client
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //String authorizationHeader = request.getHeader("Authorization");
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        //com.common.google.base
        //if(Strings.isNullOrEmpty(authorizationHeader) || authorizationHeader.startsWith("Bearer ")) {
        if(Strings.isNullOrEmpty(authorizationHeader) || authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request,response);
            return;
        }

        //String token = authorizationHeader.replace("Bearer ","");
        String token = authorizationHeader.replace(jwtConfig.getAuthorizationHeader(),"");
        try {
            //String key = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";
            Jws<Claims> claimsJws = Jwts.parser()
                    //.setSigningKey(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();
            //check claim() in the SuccessfulAuthentication()
            var authorities = (List<Map<String,String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = authorities.stream()
                    .map((m)->new SimpleGrantedAuthority(m.get("authority")))
                            .collect(Collectors.toSet()); //"authority should be same as that in payload data
            //after getting authorities above, we tell spring security that the user can be authenticated:
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthoritySet
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (JwtException e){
            throw new IllegalStateException("Token cannot be trusted " + token);
        }
        //To pass request and response from a this filter to the next filter in the filterchain:
        filterChain.doFilter(request,response);

    }
}
