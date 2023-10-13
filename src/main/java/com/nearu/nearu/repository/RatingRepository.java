package com.nearu.nearu.repository;
import com.nearu.nearu.entity.Qa;
import com.nearu.nearu.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    ArrayList<Rating> findAllByUserNo(Integer userNo);
    ArrayList<Rating> findAllByApplication_AdminNo(Integer adminNo);
    void deleteByRatingNo(Integer ratingNo);

}
