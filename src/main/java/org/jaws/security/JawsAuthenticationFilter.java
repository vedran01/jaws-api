package org.jaws.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.jaws.model.UserDO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JawsAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    @Data
    public static class AuthenticationRequest{
        private String userName;
        private String password;
    }

    public JawsAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper)
    {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        try
        {
            AuthenticationRequest authenticationRequest = objectMapper.readValue(request.getInputStream(), AuthenticationRequest.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));

        } catch (IOException e)
        {
            throw new BadCredentialsException("Invalid request");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException
    {
        UserDO userDO = ((JawsUserDetails)authResult.getPrincipal()).getUserDO();

        Key key = Keys.hmacShaKeyFor("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad".getBytes());
        Map<String, Object> roles = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("ROLE_ADMIN");
        roles.put("roles", list);
        String token = Jwts.builder().setIssuer(userDO.getUserName()).addClaims(roles).signWith(key).compact();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        response.getWriter().write(token);


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException
    {
      response.sendError(HttpStatus.UNAUTHORIZED.value(), "MRŠ");
    }


}
