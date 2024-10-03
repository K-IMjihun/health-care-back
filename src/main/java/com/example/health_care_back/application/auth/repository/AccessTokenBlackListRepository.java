package com.example.health_care_back.application.auth.repository;

import com.example.health_care_back.application.auth.domain.AccessTokenBlackList;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenBlackListRepository extends CrudRepository<AccessTokenBlackList, String> {
}
