package com.nearu.nearu.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_no")
    private Integer ratingNo;

    @Column(name = "user_no")
    private Integer userNo;

    @ManyToOne
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private User user;

    @Column(name = "application_no")
    private Integer applicationNo;

    @OneToOne
    @JoinColumn(name = "application_no", insertable = false, updatable = false)
    private Application application;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment", columnDefinition = "MEDIUMTEXT")
    private String comment;

    public Rating () {

    }

    public Rating(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
}


