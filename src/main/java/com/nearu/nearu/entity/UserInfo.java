package com.nearu.nearu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Table(name = "user_info")
public class UserInfo {

    @Id
    @Column(name = "user_no")
    private Integer userNo;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "email")
    private String email;

    @Column(name = "address", columnDefinition = "MEDIUMTEXT")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "emergency_number")
    private String emerPhoneNumber;

    @Column(name = "presentation", columnDefinition = "MEDIUMTEXT")
    private String presentation;

    @Column(name = "conditions", columnDefinition = "MEDIUMTEXT")
    private String condition;

    @Column(name = "similar_exp")
    private String similarExp;

    @Column(name = "purpose")
    private Boolean purpose;

    @Column(name = "tot_rate")
    private Integer totRate = 0;

    @Column(name = "num_rate")
    private Integer numRate = 0;

    public UserInfo() {

    }

    public UserInfo(String name, Boolean gender, String email, String phoneNumber, String emerPhoneNumber, String presentation, String condition, String similarExp, Boolean purpose) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.emerPhoneNumber = emerPhoneNumber;
        this.presentation = presentation;
        this.condition = condition;
        this.similarExp = similarExp;
        this.purpose = purpose;
    }

    public void addRating(Integer rating){
        this.totRate += rating;
        this.numRate++;
    }

    public void removeRating(Integer rating){
        this.totRate -= rating;
        this.numRate--;
    }
}
