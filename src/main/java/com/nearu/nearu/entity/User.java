package com.nearu.nearu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nearu.nearu.entity.types.UserType;
import com.nearu.nearu.entity.types.converter.UserTypeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Integer userNo;

    @Column(name = "user_type", columnDefinition = "ENUM", insertable = false, updatable = false)
    private String userTypeCode;

    @Column(name = "user_type", columnDefinition = "ENUM")
    @Convert(converter = UserTypeConverter.class)
    private UserType userType;

    @Column(name = "user_id", unique = true)
    private String userId;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private UserInfo userInfo;

    public User() {
    }
    public User(String userId) {
        this.userId = userId;
    }
    public User(UserType userType, String userId) {
        this.userType = userType;
        this.userId = userId;
    }


}