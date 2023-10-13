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
@Table(name = "qa")

public class Qa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qa_no")
    private Integer qaNo;

    @Column(name = "user_no")
    private Integer userNo;

    @ManyToOne
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private User user;

    @Column(name = "anonymous")
    private Boolean anonymous;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdDt;

    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private LocalDateTime updatedDt;

    @Column(name = "title")
    private String title;

    @Column(name = "question", columnDefinition = "MEDIUMTEXT")
    private String question;


    public Qa() {
    }

    public Qa(Boolean anonymous, LocalDateTime date, String title, String question) {
        this.anonymous = anonymous;
        this.createdDt = date;
        this.title = title;
        this.question = question;
    }
}
