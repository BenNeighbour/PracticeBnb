package com.benneighbour.practicebnb.gatewayServer.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtAuthenticationConfig config;

  public JwtAuthenticationFilter(JwtAuthenticationConfig config) {
    this.config = config;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain)
      throws ServletException, IOException {
    try {
      if (!httpServletRequest.getServletPath().equals("/login")) {
        String jwt = getJwtToken(httpServletRequest, true);

        // Validate and create the token, while adding it to the user's authentication session
        Claims claims =
            Jwts.parser()
                .setSigningKey(config.getSecret().getBytes())
                .parseClaimsJws(jwt)
                .getBody();

        String username = claims.getSubject();

        List<String> userAuthorities = claims.get("authorities", List.class);
        if (username != null) {
          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(
                  username,
                  null,
                  userAuthorities.stream()
                      .map(SimpleGrantedAuthority::new)
                      .collect(Collectors.toList()));

          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    } catch (Exception ex) {
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      String accessToken = bearerToken.substring(7);
      if (accessToken == null) return null;

      return accessToken;
    }
    return getJwtFromCookie(request);
  }

  private String getJwtFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("access_token".equals(cookie.getName())) {
          String accessToken = cookie.getValue();
          if (accessToken == null) return null;

          return accessToken;
        }
      }
    }

    return null;
  }

  private String getJwtToken(HttpServletRequest request, boolean fromCookie) {
    if (fromCookie) return getJwtFromCookie(request);

    return getJwtFromRequest(request);
  }
}
