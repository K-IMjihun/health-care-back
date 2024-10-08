package com.example.health_care_back.application.account.service;

import com.example.health_care_back.application.account.controller.dto.AuthorityUpdateDTO;
import com.example.health_care_back.application.account.controller.dto.CheckExistenceUserDTO;
import com.example.health_care_back.application.account.controller.dto.CreateUserDTO;
import com.example.health_care_back.application.account.domain.User;
import com.example.health_care_back.application.account.domain.code.AuthorityType;
import com.example.health_care_back.application.account.repository.UserRepository;
import com.example.health_care_back.application.common.exception.DuplicateException;
import com.example.health_care_back.application.common.exception.DuplicateException.DuplicateExceptionCode;
import com.example.health_care_back.application.common.exception.ModifyPermissionException;
import com.example.health_care_back.application.common.exception.ModifyPermissionException.ModifyPermissionExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAnService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${system.key}")
    private String systemKey;

    @Transactional
    public void signUp(CreateUserDTO dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new DuplicateException(DuplicateExceptionCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(dto.newPassword());
        User user = new User(dto, encodedPassword);
        userRepository.save(user);
    }

    public Boolean getExistence(CheckExistenceUserDTO dto) {
        return userRepository.findByEmail(dto.email()).isPresent();
    }

    @Transactional
    public Boolean authorityUpdate(AuthorityUpdateDTO dto) {

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(NullPointerException::new);

        if(!dto.systemKey().equals(systemKey)){
            throw new ModifyPermissionException(ModifyPermissionExceptionCode.SYSTEM_KEY_MISMATCH_EXCEPTION);
        }

        user.updateAuthority(AuthorityType.SYSTEM);
        userRepository.save(user);
        return null;
    }
}