package com.nearu.nearu.repository;

import com.nearu.nearu.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
    void deleteAllByUserNo(Integer userNo);
    UserSession findByUser_UserId(String userId);
}
