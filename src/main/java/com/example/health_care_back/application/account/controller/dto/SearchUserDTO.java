package com.example.health_care_back.application.account.controller.dto;

import com.example.health_care_back.application.account.domain.code.AuthorityType;
import com.example.health_care_back.application.account.domain.code.UserStatus;
import com.example.health_care_back.util.dto.PagingDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SearchUserDTO extends PagingDTO {
    private String email;
    private String nickname;
    private String mobile;
    private String name;
    private List<UserStatus> userStatuses;
    private List<AuthorityType> authorities;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate signUpDateFrom;
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate signUpDateTo;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate recentSignInDateFrom;
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate recentSignInDateTo;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate recentChangeStatusDateFrom;
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate recentChangeStatusDateTo;
}