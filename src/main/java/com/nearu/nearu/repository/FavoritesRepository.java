package com.nearu.nearu.repository;

import com.nearu.nearu.entity.Favorites;
import com.nearu.nearu.entity.Qa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {
    ArrayList<Favorites> findAllByUserNo(Integer userNo);
    Favorites findByFavoriteNo (Integer favoriteNo);

    void deleteByFavoriteNo (Integer favoriteNo);

    void deleteAllByUserNo(Integer userNo);
}
