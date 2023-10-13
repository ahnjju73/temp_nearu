package com.nearu.nearu.repository;

import com.nearu.nearu.entity.Application;
import com.nearu.nearu.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Application findByApplicationNo(Integer applicationNo);
    void deleteByApplicationNo(Integer applicationNo);
    ArrayList<Application> findAllByAdminNo(Integer adminNo);
}
