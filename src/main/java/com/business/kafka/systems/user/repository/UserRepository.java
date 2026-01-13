package com.business.kafka.systems.user.repository;

import com.business.kafka.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
