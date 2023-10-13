package com.nearu.nearu.object.request;

import com.nearu.nearu.entity.Comment;
import com.nearu.nearu.entity.Qa;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Getter
@Setter
public class QaReadResponse {
    private Qa question;
    // 여러개 만들기 위해선 arraylist 사용
    private ArrayList<Comment> comments;
    private String name;


}

