package com.nearu.nearu.repository;

import com.nearu.nearu.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
    void deleteByUserNo(Integer userNo);
    UserInfo findByUserNo(Integer userNo);

}
