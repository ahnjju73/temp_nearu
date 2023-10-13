package com.nearu.nearu.repository;

import com.nearu.nearu.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsRepository extends JpaRepository<Notifications, Integer>{
    Notifications findByUserNo(Integer userNo);
    void deleteByUserNo(Integer userNo);

}
