package com.example.health_care_back.application.account.controller;

import com.example.health_care_back.application.account.controller.dto.ConfirmPasswordUserDTO;
import com.example.health_care_back.application.account.service.UserCmService;
import com.example.health_care_back.application.common.response.CommonResponse;
import com.example.health_care_back.application.vo.UserVO;
import com.example.health_care_back.infra.config.security.user.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cm/users")
public class CmUserController {

  private final UserCmService userCmService;

  // 회원 정보 조회
  @GetMapping("/{user-id}")
  public CommonResponse<UserVO> getUser(@PathVariable(value = "user-id") Long userId,
    @AuthenticationPrincipal LoginUser loginUser) {
    return CommonResponse.success(userCmService.getUser(loginUser, userId));
  }

  @PostMapping(value = "/{user-id}/confirm:password", consumes = MediaType.APPLICATION_JSON_VALUE)
  public CommonResponse<Boolean> confirmPassword(@PathVariable(value = "user-id") Long userId,
                                                 @Valid @RequestBody ConfirmPasswordUserDTO dto,
                                                 @AuthenticationPrincipal LoginUser loginUser) {
    return CommonResponse.success(userCmService.confirmPassword(loginUser, userId, dto));
  }
}
