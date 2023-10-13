package com.nearu.nearu.repository;

import com.nearu.nearu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUserId(String userId);
    User findByUserNo(Integer userNo);
    void deleteByUserNo(Integer userNo);
    User findByUserInfo_Email(String email);
}
