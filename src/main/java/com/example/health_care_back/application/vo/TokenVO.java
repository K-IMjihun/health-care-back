package com.example.health_care_back.application.vo;

import com.example.health_care_back.application.account.domain.code.UserStatus;
import com.example.health_care_back.infra.config.security.user.LoginUser;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TokenVO(
  String email,
  UserStatus userStatus,
  String accessToken,
  String refreshToken)
{

  public static TokenVO valueOf(String email, UserStatus userStatus) {
    return builder()
      .email(email)
      .userStatus(userStatus)
      .build();
  }

  public static TokenVO valueOf(LoginUser loginUser,
    String accessToken, String refreshToken) {
    return builder()
      .email(loginUser.getEmail())
      .userStatus(loginUser.getUserStatus())
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .build();
  }

}
