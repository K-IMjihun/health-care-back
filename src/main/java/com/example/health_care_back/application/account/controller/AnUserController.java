package com.example.health_care_back.application.account.controller;

import com.example.health_care_back.application.account.controller.dto.CreateUserDTO;
import com.example.health_care_back.application.account.service.UserAnService;
import com.example.health_care_back.application.common.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/an/users")
public class AnUserController {

    private final UserAnService userAnService;

    // 회원가입
    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<Void> signUp(@Valid @RequestBody CreateUserDTO dto) {
        userAnService.signUp(dto);
        return CommonResponse.success();
    }

}