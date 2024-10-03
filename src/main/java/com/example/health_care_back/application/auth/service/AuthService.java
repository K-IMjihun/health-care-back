package com.example.health_care_back.application.auth.service;

import com.example.health_care_back.application.account.domain.code.UserStatus;
import com.example.health_care_back.application.auth.controller.dto.LoginDTO;
import com.example.health_care_back.application.auth.controller.dto.LogoutDTO;
import com.example.health_care_back.application.auth.controller.dto.ReissueTokenDTO;
import com.example.health_care_back.application.auth.domain.AccessTokenBlackList;
import com.example.health_care_back.application.auth.domain.RefreshToken;
import com.example.health_care_back.application.auth.repository.AccessTokenBlackListRepository;
import com.example.health_care_back.application.auth.repository.RefreshTokenRepository;
import com.example.health_care_back.application.common.exception.AuthException;
import com.example.health_care_back.application.common.exception.AuthException.AuthExceptionCode;
import com.example.health_care_back.application.vo.TokenVO;
import com.example.health_care_back.event.user.UserEventPublisher;
import com.example.health_care_back.infra.config.security.jwt.JwtAuthenticationProvider;
import com.example.health_care_back.infra.config.security.jwt.JwtUtil;
import com.example.health_care_back.infra.config.security.user.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtUtil jwtUtil;
  private final JwtAuthenticationProvider jwtAuthenticationProvider;

  private final RefreshTokenRepository refreshTokenRepository;
  private final AccessTokenBlackListRepository accessTokenBlackListRepository;

  private final UserEventPublisher userEventPublisher;

  @Transactional
  public TokenVO login(LoginDTO dto) {
    Authentication authentication = authenticationManagerBuilder.getObject()
      .authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    if (!UserStatus.ACTIVATED.equals(loginUser.getUserStatus())) {
      return TokenVO.valueOf(loginUser.getEmail(), loginUser.getUserStatus());
    }

    TokenVO vo = jwtUtil.createToken(loginUser);

    RefreshToken refreshToken = RefreshToken.createRefreshToken()
      .email(loginUser.getEmail())
      .refreshToken(vo.refreshToken())
      .build();
    refreshTokenRepository.save(refreshToken);

    // 유저 로그인 이벤트 객체 게시
    userEventPublisher.publishEventUserLogin(loginUser.getEmail());
    return vo;
  }

  @Transactional
  public void logout(LogoutDTO dto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.isNull(authentication)) {
      throw new AuthException(AuthExceptionCode.ALREADY_LOGOUT);
    }

    // 현재 로그인된 유저
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();

    // accessToken to LoginUser
    LoginUser loginUserFromToken = (LoginUser) jwtAuthenticationProvider.getAuthentication(
      dto.accessToken()).getPrincipal();

    if (!loginUser.getEmail().equals(loginUserFromToken.getEmail())) {
      throw new AuthException(AuthExceptionCode.NOT_AUTHORIZED);
    }

    refreshTokenRepository.deleteById(loginUser.getEmail());

    AccessTokenBlackList blackList = AccessTokenBlackList.createAccessTokenBlacklist()
      .accessToken(dto.accessToken())
      .build();
    accessTokenBlackListRepository.save(blackList);
    SecurityContextHolder.clearContext();
  }

  public TokenVO reissueToken(ReissueTokenDTO dto) {
    // refresh token validation
    jwtAuthenticationProvider.validateToken(dto.refreshToken());

    LoginUser loginUser = (LoginUser) jwtAuthenticationProvider.getAuthentication(
      dto.accessToken()).getPrincipal();

    // get refreshToken in redis and validation
    RefreshToken refreshToken = refreshTokenRepository
      .findById(loginUser.getEmail())
      .filter(token -> token.validate(dto.refreshToken()))
      .orElseThrow(() -> new AuthException(AuthExceptionCode.JWT_REFRESH_TOKEN_VERIFICATION_FAIL));

    String newAccessToken = jwtUtil.createAccessToken(loginUser, new Date());
    return TokenVO.valueOf(loginUser, newAccessToken, refreshToken.getRefreshToken());
  }
}
