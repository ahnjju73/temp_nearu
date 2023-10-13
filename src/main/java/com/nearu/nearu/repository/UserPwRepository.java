package com.nearu.nearu.repository;

import com.nearu.nearu.entity.UserPw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPwRepository extends JpaRepository<UserPw, Integer> {
    void deleteByUserNo(Integer userNo);
    UserPw findByUserNo(Integer userNo);

    UserPw findByUser_UserId(String userNo);
}
