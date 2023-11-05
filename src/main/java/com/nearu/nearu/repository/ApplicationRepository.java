package com.nearu.nearu.repository;

import com.nearu.nearu.entity.Application;
import com.nearu.nearu.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Application findByApplicationNo(Integer applicationNo);
    void deleteByApplicationNo(Integer applicationNo);
    ArrayList<Application> findAllByAdminNo(Integer adminNo);

//    ArrayList findAllByOrderByDDayAsc();
    @Query("SELECT a FROM Application a where a.dDay >= now() ORDER BY a.dDay")
    ArrayList<Application> findAllByDDayBeforeOrderByDDayAsc();
}
