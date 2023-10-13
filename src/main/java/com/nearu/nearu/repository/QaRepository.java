package com.nearu.nearu.repository;

import com.nearu.nearu.entity.Qa;
import com.nearu.nearu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QaRepository extends JpaRepository<Qa, Integer> {
    Qa findByQaNo(Integer qaNo);
    void deleteByQaNo (Integer qaNo);
}
