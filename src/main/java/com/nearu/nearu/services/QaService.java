package com.nearu.nearu.services;
import com.nearu.nearu.entity.Comment;
import com.nearu.nearu.entity.Qa;
import com.nearu.nearu.entity.User;
import com.nearu.nearu.repository.CommentRepository;
import com.nearu.nearu.repository.QaRepository;
import com.nearu.nearu.repository.UserInfoRepository;
import com.nearu.nearu.repository.UserRepository;
import com.nearu.nearu.object.request.CommentDto;
import com.nearu.nearu.object.request.QaCountsResponse;
import com.nearu.nearu.object.request.QaDto;
import com.nearu.nearu.object.request.QaReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QaService {

    private final QaRepository qaRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    // comment update, comment delete
    @Transactional
    public void post (QaDto q, User session){
        Qa qa = new Qa();
        Integer userNo = session.getUserNo();
        qa.setAnonymous(q.getAnonymous());
        qa.setUserNo(userNo);
        qa.setTitle(q.getTitle());
        qa.setQuestion(q.getQuestion());
        qa.setCreatedDt(LocalDateTime.now());
        qaRepository.save(qa);
    }

    public ArrayList<QaCountsResponse> fetchAll (){
        ArrayList<Qa> list = (ArrayList<Qa>) qaRepository.findAll();
        ArrayList<QaCountsResponse> arrList = new ArrayList<QaCountsResponse>();

        for(int i=0; i<list.size(); i++) {
            QaCountsResponse res = new QaCountsResponse();
            res.setQuestion(list.get(i));
            res.setCountComments(commentRepository.countAllByQaNo(list.get(i).getQaNo()));
            Integer userNo = list.get(i).getUserNo();
            res.setName(userInfoRepository.findByUserNo(userNo).getName());
            arrList.add(res);
        }
        return arrList;
    }

    public QaReadResponse fetchDetails (Integer qaNo) {
        QaReadResponse readResponse = new QaReadResponse();
        Qa qa = qaRepository.findByQaNo(qaNo);
        readResponse.setQuestion(qa);
        readResponse.setComments(commentRepository.findAllByQaNo(qaNo));
        readResponse.setName(userInfoRepository.findByUserNo(qa.getUserNo()).getName());
        return readResponse;
    }

    @Transactional
    public void update(QaDto q) {
        Qa qa = qaRepository.findByQaNo(q.getQaNo());
        qa.setAnonymous(q.getAnonymous());
        qa.setUpdatedDt(LocalDateTime.now());
        qa.setTitle(q.getTitle());
        qa.setQuestion(q.getQuestion());

        qaRepository.save(qa);
    }

    @Transactional
    public void delete(Integer qaNo){
        commentRepository.deleteAllByQaNo(qaNo);
        qaRepository.deleteByQaNo(qaNo);
    }

    @Transactional
    public void commentUpdate(CommentDto c) {
        Comment com = commentRepository.findByCommentNo(c.getCommentNo());
        com.setContent(c.getContent());
        com.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(com);
    }

    @Transactional
    public void commentDelete (Integer commentNo) {
        commentRepository.deleteByCommentNo(commentNo);
    }

    @Transactional
    public void commentPost (CommentDto c, User session) {
        Comment com = new Comment();
        Integer userNo = session.getUserNo();
        com.setUserNo(userNo);
        com.setQaNo(c.getQaNo());
        com.setContent(c.getContent());
        com.setCreatedAt(LocalDateTime.now());
        commentRepository.save(com);
    }



}
