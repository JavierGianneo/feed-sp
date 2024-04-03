package com.feed.feedsp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.feed.feedsp.security.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    com.feed.feedsp.entity.User user;
    String userName;
    String password;

    try {
      user = new ObjectMapper().readValue(request.getInputStream(), com.feed.feedsp.entity.User.class);
      userName = user.getUserName();
      password = user.getPassword();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);

    return authenticationManager.authenticate(authenticationToken);
  }


  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    User user = (User) authResult.getPrincipal();
    String userName = user.getUsername();
    Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

    Claims claims = Jwts.claims()
            .add("authorities", new ObjectMapper().writeValueAsString(roles))
            .add("username", userName)
            .build();

    String token = Jwts.builder()
            .subject(userName)
            .expiration(new Date(System.currentTimeMillis() + 3600000))
            .issuedAt(new Date())
            .claims(claims)
            .signWith(SECRET_KEY)
            .compact();

    response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

    Map<String, String> body = new HashMap<>();
    body.put("token", token);
    body.put("username", userName);
    body.put("message", String.format("%s ha iniciado sesion con exito", userName));

    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    response.setContentType(CONTENT_TYPE);
    response.setStatus(200);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    Map<String, String> body = new HashMap<>();
    body.put("message", "Nombre de usuario o contrasena incorrectos");
    body.put("error", failed.getMessage());

    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    response.setContentType(CONTENT_TYPE);
    response.setStatus(401);
  }
}
