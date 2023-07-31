package com.br.compassuol.sp.challenge.msauth.repository;

import com.br.compassuol.sp.challenge.msauth.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
