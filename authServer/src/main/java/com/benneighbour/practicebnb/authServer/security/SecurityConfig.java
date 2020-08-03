package com.benneighbour.practicebnb.authServer.security;

import com.benneighbour.practicebnb.authServer.service.SecurityUserServiceImpl;
import com.benneighbour.practicebnb.authServer.common.security.JwtAuthenticationConfig;
import com.benneighbour.practicebnb.authServer.common.security.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private JwtAuthenticationConfig config;

  @Autowired private SecurityUserServiceImpl securityUserService;

  @Bean
  public JwtAuthenticationConfig jwtConfig() {
    return new JwtAuthenticationConfig();
  }

//  @Autowired
//  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    auth.inMemoryAuthentication()
//        .withUser("benneighbour")
//        .password(encoder.encode("benneighbour"))
//        .roles("USER");
//  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(securityUserService).passwordEncoder(passwordEncoder());
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .csrf().disable()
            .logout().disable()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .anonymous()
            .and()
            .exceptionHandling().authenticationEntryPoint(
            (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager()),
                    UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated();
  }
}
