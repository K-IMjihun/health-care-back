package com.example.health_care_back.application.account.repository;

import com.example.health_care_back.application.account.repository.param.SearchUserParam;
import com.example.health_care_back.application.vo.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    Page<UserVO> findAll(SearchUserParam param, Pageable pageable);
}
