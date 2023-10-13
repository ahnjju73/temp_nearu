package com.nearu.nearu.controller;

import com.nearu.nearu.OriginObject;
import com.nearu.nearu.SessionRequest;
import com.nearu.nearu.config.flows.SessionMapper;
import com.nearu.nearu.object.request.CommentDto;
import com.nearu.nearu.object.request.QaCountsResponse;
import com.nearu.nearu.object.request.QaDto;
import com.nearu.nearu.services.QaService;
import com.nearu.nearu.object.request.QaReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class QaController extends OriginObject {
    private final QaService qaService;

    @SessionMapper
    @Transactional
    @PostMapping("/qa")
    public void post(SessionRequest request){
        QaDto map = map(request.getParam(), QaDto.class);
        qaService.post(map, request.getSession());
    }

    @SessionMapper
    @Transactional
    @PutMapping("/qa")
    public void edit(SessionRequest request){
        QaDto map = map(request.getParam(), QaDto.class);
        qaService.update(map);
    }

    @SessionMapper
    @Transactional
    @DeleteMapping("/qa")
    public void delete(SessionRequest request){
        QaDto map = map(request.getParam(), QaDto.class);
        qaService.delete(map.getQaNo());
    }

    @SessionMapper
    @Transactional
    @PostMapping("/comment")
    public void commentPost(SessionRequest request) {
        CommentDto map = map(request.getParam(), CommentDto.class);
        qaService.commentPost(map, request.getSession());
    }

    @SessionMapper
    @Transactional
    @PutMapping("/comment")
    public void commentEdit(SessionRequest request) {
        CommentDto map = map(request.getParam(), CommentDto.class);
        qaService.commentUpdate(map);
    }

    @SessionMapper
    @Transactional
    @DeleteMapping("/comment")
    public void commentDelete(SessionRequest request) {
        CommentDto map = map(request.getParam(), CommentDto.class);
        qaService.commentDelete(map.getCommentNo());
    }

    @SessionMapper
    @GetMapping("/qa")
    public QaReadResponse read (SessionRequest request) {
        QaDto map = map(request.getParam(), QaDto.class);
        return qaService.fetchDetails(map.getQaNo());
    }

    @SessionMapper
    @GetMapping("/qa-all")
    public ArrayList<QaCountsResponse> readAll (SessionRequest request) {
        return qaService.fetchAll();
    }
}