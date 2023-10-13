package com.nearu.nearu.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nearu.nearu.entity.types.StudentApplicationPk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@IdClass(StudentApplicationPk.class)
@Table(name = "student_application")
public class StudApplication {

    @Id
    @Column(name = "user_no")
    private Integer userNo;

    @ManyToOne
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private User user;

    @Id
    @Column(name = "application_no")
    private Integer applicationNo;

    @ManyToOne
    @JoinColumn(name = "application_no", insertable = false, updatable = false)
    private Application application;
}

