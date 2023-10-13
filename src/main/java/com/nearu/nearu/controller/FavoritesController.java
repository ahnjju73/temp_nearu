package com.nearu.nearu.controller;
import com.nearu.nearu.OriginObject;
import com.nearu.nearu.SessionRequest;
import com.nearu.nearu.config.flows.SessionMapper;
import com.nearu.nearu.entity.Favorites;
import com.nearu.nearu.object.request.FavoritesDto;
import com.nearu.nearu.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class FavoritesController extends OriginObject {
    private final UserService userService;

    @SessionMapper
    @Transactional
    @PostMapping("/favorites")
    public void addAddress(SessionRequest request){
        FavoritesDto map = map(request.getParam(), FavoritesDto.class);
        userService.saveFavorites(map, request.getSession());
    }

    @SessionMapper
    @GetMapping("/favorites")
    public ArrayList<Favorites> readAll(SessionRequest request){
        FavoritesDto map = map(request.getParam(), FavoritesDto.class);
        return userService.fetchAllFavorites(request.getSession());
    }

    @SessionMapper
    @Transactional
    @DeleteMapping("/favorites")
    public void delete(SessionRequest request){
        FavoritesDto map = map(request.getParam(), FavoritesDto.class);
        userService.deleteFavorites(map.getFavoriteNo());
    }
}
