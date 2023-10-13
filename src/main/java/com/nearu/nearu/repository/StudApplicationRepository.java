package com.nearu.nearu.repository;
import com.nearu.nearu.entity.Rating;
import com.nearu.nearu.entity.StudApplication;
import com.nearu.nearu.entity.types.StudentApplicationPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface StudApplicationRepository extends JpaRepository<StudApplication, StudentApplicationPk> {
    StudApplication findByApplicationNoAndUserNo(Integer applicationNo, Integer userNo);
    void deleteAllByApplicationNo(Integer applicationNo);
    void deleteByApplicationNoAndUserNo(Integer applicationNo, Integer userNo);
    Integer countAllByApplicationNo(Integer applicationNo);
    ArrayList<StudApplication> findAllByApplicationNo(Integer applicationNo);
}
