package com.example.health_care_back.application.account.service;

import com.example.health_care_back.application.account.controller.dto.ConfirmPasswordUserDTO;
import com.example.health_care_back.application.account.domain.User;
import com.example.health_care_back.application.account.domain.code.UserStatus;
import com.example.health_care_back.application.account.repository.UserRepository;
import com.example.health_care_back.application.common.exception.AuthException;
import com.example.health_care_back.application.common.exception.AuthException.AuthExceptionCode;
import com.example.health_care_back.application.common.exception.ResourceException;
import com.example.health_care_back.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.health_care_back.application.vo.UserVO;
import com.example.health_care_back.infra.config.security.user.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserCmService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserVO getUser(LoginUser loginUser, Long userId) {
        User user = userRepository.findByIdAndUserStatusIs(userId, UserStatus.ACTIVATED)
                .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

        if (!user.getId().equals(loginUser.getId())) {
            throw new AuthException(AuthExceptionCode.NOT_AUTHORIZED);
        }

        return UserVO.valueOf(user);
    }

    public boolean confirmPassword(LoginUser loginUser, Long userId, ConfirmPasswordUserDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

        if (!user.getId().equals(loginUser.getId())) {
            throw new AuthException(AuthExceptionCode.NOT_AUTHORIZED);
        }

        return passwordEncoder.matches(dto.password(), user.getPassword());
    }
}