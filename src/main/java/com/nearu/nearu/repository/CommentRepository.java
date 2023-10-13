package com.nearu.nearu.repository;

import com.nearu.nearu.entity.Comment;
import com.nearu.nearu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CommentRepository extends JpaRepository<Comment, Integer>
{
    Integer countAllByQaNo(Integer qaNo);
    ArrayList<Comment> findAllByQaNo (Integer qaNo);
    void deleteAllByQaNo (Integer qaNo);

    Comment findByCommentNo (Integer commentNo);
    void deleteByCommentNo (Integer commentNo);

}
