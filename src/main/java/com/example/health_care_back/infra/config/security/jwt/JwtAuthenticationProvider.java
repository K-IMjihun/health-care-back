package com.example.health_care_back.infra.config.security.jwt;

import com.example.health_care_back.application.account.domain.User;
import com.example.health_care_back.application.auth.repository.AccessTokenBlackListRepository;
import com.example.health_care_back.application.common.exception.AuthException;
import com.example.health_care_back.application.common.exception.AuthException.AuthExceptionCode;
import com.example.health_care_back.infra.config.security.user.LoginUser;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

  private final JwtProperties jwtProperties;
  private final AccessTokenBlackListRepository accessTokenBlackListRepository;

  public Authentication getAuthentication(String accessToken) {
    Claims claims = getParseClaims(accessToken);
    validateToken(accessToken);
    return new UsernamePasswordAuthenticationToken(
            new LoginUser(new User(claims)), "", getAuthorities(claims));
  }

  public boolean validateToken(String token) {
    try {
      getParseClaims(token);
      if (accessTokenBlackListRepository.existsById(token)) {
        throw new AuthException(AuthExceptionCode.ALREADY_LOGOUT);
      }
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.warn("Invalid JWT signature");
      throw new JwtException("Invalid JWT signature.");
    } catch (ExpiredJwtException e) {
      log.warn("Expired JWT token.");
      throw new JwtException("Expired JWT token.");
    } catch (UnsupportedJwtException e) {
      log.warn("Unsupported JWT token.");
      throw new JwtException("Unsupported JWT token.");
    } catch (IllegalArgumentException e) {
      log.warn("JWT claims is empty.");
      throw new JwtException("JWT claims is empty.");
    }
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
    String[] authorities = claims.get("authorityType").toString().split(",");
    return Arrays.stream(authorities)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
  }

  private Claims getParseClaims(String accessToken) {
    try {
      return getJwtParseSignedClaims(accessToken).getPayload();
    } catch (Exception e) {
      return ((ExpiredJwtException) e).getClaims();
    }
  }

  private Jws<Claims> getJwtParseSignedClaims(String accessToken) {
    return Jwts.parser()
            .verifyWith(jwtProperties.getSecretKey())
            .build()
            .parseSignedClaims(accessToken);
  }


}
