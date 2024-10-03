package com.example.health_care_back.application.auth.repository;

import com.example.health_care_back.application.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
