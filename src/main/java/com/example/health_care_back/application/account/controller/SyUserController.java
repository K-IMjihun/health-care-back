package com.example.health_care_back.application.account.controller;

import com.example.health_care_back.application.account.controller.dto.ChangeAuthorityDTO;
import com.example.health_care_back.application.account.controller.dto.SearchUserDTO;
import com.example.health_care_back.application.account.service.UserSyService;
import com.example.health_care_back.application.common.response.CommonResponse;
import com.example.health_care_back.application.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sy/users")
public class SyUserController {

  private final UserSyService userSyService;

  // 권한 수정
  @PutMapping(value = "/{user-id}/authority", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void changeAuthority(
    @PathVariable(value = "user-id") long userId,
    @Valid @RequestBody ChangeAuthorityDTO dto) {
    userSyService.changeAuthority(userId, dto);
  }

  // 유저 조회
  @GetMapping
  public CommonResponse<Page<UserVO>> getUsers(
          @Valid SearchUserDTO dto) {
    return CommonResponse.success(userSyService.getUsers(dto));
  }
}
