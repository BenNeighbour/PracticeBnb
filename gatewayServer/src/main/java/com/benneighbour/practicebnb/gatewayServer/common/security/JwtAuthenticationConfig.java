package com.benneighbour.practicebnb.gatewayServer.common.security;

import org.springframework.beans.factory.annotation.Value;

public class JwtAuthenticationConfig {

  private TokenType tokenType;

  private String tokenValue;

  @Value("${practicebnb.security.jwt.secret}")
  private String secret;

  private Long duration;

  private int expiry;

  public JwtAuthenticationConfig() {}

  public JwtAuthenticationConfig(
      TokenType tokenType, String tokenValue, Long duration, int expiry) {
    this.tokenType = tokenType;
    this.tokenValue = tokenValue;
    this.duration = duration;
    this.expiry = expiry;
  }

  public enum TokenType {
    ACCESS,
    REFRESH
  }

  public TokenType getTokenType() {
    return tokenType;
  }

  public void setTokenType(TokenType tokenType) {
    this.tokenType = tokenType;
  }

  public String getTokenValue() {
    return tokenValue;
  }

  public void setTokenValue(String tokenValue) {
    this.tokenValue = tokenValue;
  }

  public Long getDuration() {
    return duration;
  }

  public void setDuration(Long duration) {
    this.duration = duration;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public int getExpiry() {
    return expiry;
  }

  public void setExpiry(int expiry) {
    this.expiry = expiry;
  }
}
