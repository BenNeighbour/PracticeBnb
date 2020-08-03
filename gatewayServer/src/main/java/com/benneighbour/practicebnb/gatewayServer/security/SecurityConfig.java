package com.benneighbour.practicebnb.gatewayServer.security;

import com.benneighbour.practicebnb.gatewayServer.common.security.JwtAuthenticationConfig;
import com.benneighbour.practicebnb.gatewayServer.common.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private JwtAuthenticationConfig config;

  @Bean
  public JwtAuthenticationConfig jwtConfig() {
    return new JwtAuthenticationConfig();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf()
        .disable()
        .logout()
        .disable()
        .formLogin()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .anonymous()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(
            (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
        .and()
        .addFilterAfter(
            new JwtAuthenticationFilter(config), UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/**")
        .permitAll()
        .anyRequest()
        .authenticated();
  }
}
