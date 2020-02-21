package org.jaws.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JawsTokenAuthFilter extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
    {
        String token = httpServletRequest.getHeader("Authorisation");
        if (token != null && !token.isEmpty()){
            Key key = Keys.hmacShaKeyFor("bla".getBytes());
            Claims body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

            String principal =body.getIssuer();
//            List<Map<String, String >> roles = body.get("roles");
//            List<String > role = roles.stream().map(r -> roles.get("name")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal,null, role ));

            filterChain.doFilter(httpServletRequest,httpServletResponse);

        }
    }
}
