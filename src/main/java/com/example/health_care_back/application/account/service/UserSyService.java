package com.example.health_care_back.application.account.service;

import com.example.health_care_back.application.account.controller.dto.ChangeAuthorityDTO;
import com.example.health_care_back.application.account.controller.dto.SearchUserDTO;
import com.example.health_care_back.application.account.domain.User;
import com.example.health_care_back.application.account.repository.UserRepository;
import com.example.health_care_back.application.account.repository.param.SearchUserParam;
import com.example.health_care_back.application.common.exception.ModifyPermissionException.ModifyPermissionExceptionCode;
import com.example.health_care_back.application.common.exception.ResourceException;
import com.example.health_care_back.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.health_care_back.application.common.exception.ModifyPermissionException;
import com.example.health_care_back.application.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.health_care_back.application.account.domain.code.AuthorityType.SYSTEM;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSyService {

    private final UserRepository userRepository;

    @Transactional
    public void changeAuthority(Long userId, ChangeAuthorityDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

        if (!user.getAuthorityType().equals(SYSTEM)) {
            throw new ModifyPermissionException(ModifyPermissionExceptionCode.UNAUTHORIZED_ACCESS_EXCEPTION);
        }

        if (user.getAuthorityType().equals(dto.authorityType())) {
            throw new ModifyPermissionException(ModifyPermissionExceptionCode.CANNOT_MODIFY_SAME_AUTHORITY_EXCEPTION);
        }

        user.updateAuthority(dto.authorityType());
        userRepository.save(user);
    }

    public Page<UserVO> getUsers(SearchUserDTO dto) {
        return userRepository.findAll(SearchUserParam.valueOf(dto), dto.toPageRequest());
    }
}
