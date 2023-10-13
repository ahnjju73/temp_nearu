package com.nearu.nearu.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Integer commentNo;

    @Column(name = "user_no")
    private Integer userNo;

    @ManyToOne
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private  User  user;

    @Column(name = "qa_no")
    private Integer qaNo;

    @ManyToOne
    @JoinColumn(name = "admin_no", insertable = false, updatable = false)
    private Qa qa;

    @Column(name = "content", columnDefinition = "MEDIUMTEXT")
    private String content;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;

    public Comment() {
    }

    public Comment(Integer userNo, Integer qaNo, String content, LocalDateTime createdAt) {
        this.userNo = userNo;
        this.qaNo = qaNo;
        this.content = content;
        this.createdAt = createdAt;
    }
}
