package com.example.health_care_back.application.account.service;

import com.example.health_care_back.application.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAnService {

//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public boolean getExistence(CheckExistenceUserDTO dto) {
//        return userRepository.findByEmail(dto.email()).isPresent();
//    }
//
//    @Transactional
//    public void signUp(CreateUserDTO dto) {
//        if (userRepository.findByEmail(dto.email()).isPresent()) {
//            throw new DuplicateException(DuplicateExceptionCode.DUPLICATE_EMAIL);
//        }
//
//        String encodedPassword = passwordEncoder.encode(dto.newPassword());
//        User user = new User(dto, encodedPassword);
//        userRepository.save(user);
//    }
}