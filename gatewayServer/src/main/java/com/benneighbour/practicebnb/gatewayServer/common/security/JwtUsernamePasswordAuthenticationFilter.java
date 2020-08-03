package com.benneighbour.practicebnb.gatewayServer.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/*
 * @created 01/08/2020 - 11
 * @project PremKnockout
 * @author  Ben Neighbour
 */
public class JwtUsernamePasswordAuthenticationFilter
    extends AbstractAuthenticationProcessingFilter {

  private final JwtAuthenticationConfig config;

  private final ObjectMapper mapper;

  public JwtUsernamePasswordAuthenticationFilter(
      JwtAuthenticationConfig config, AuthenticationManager authenticationManager) {
    super(new AntPathRequestMatcher("/login", "POST"));
    setAuthenticationManager(authenticationManager);

    this.config = config;
    this.mapper = new ObjectMapper();
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    // Create a new token, and make sure to sign it with the right hashing algorithm
    Instant now = Instant.now();
    String token =
        Jwts.builder()
            .setSubject(authResult.getName())
            .claim(
                "authorities",
                authResult.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusSeconds(config.getExpiry())))
            .signWith(SignatureAlgorithm.HS256, config.getSecret().getBytes())
            .compact();

    // Create a cookie with the JWT in it
//    response.flushBuffer();

    response.addHeader(
        "Set-Cookie", "session=" + token + "; Path=/; HttpOnly; Domain=127.0.0.1; Max-Age=3600;");
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
      throws AuthenticationException, IOException, ServletException {
    // Read the login request values (username, password)
    LoginObj loginRequest = mapper.readValue(httpServletRequest.getInputStream(), LoginObj.class);
    return getAuthenticationManager()
        .authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword(), Collections.emptyList()));
  }
}
