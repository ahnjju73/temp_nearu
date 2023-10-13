package com.nearu.nearu.object.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {
    private String userId;
    private String password;
    private String type;
    private String address;
    private String name;
    private Boolean gender;
    private String email;
    private String phoneNum;
    private String emergencyNum;
    private String presentation;
    private String condition;
    private String experience;
    private Boolean purpose;
    private Boolean emailNotification;
    private Boolean msgNotification;
    private Boolean phoneNotification;
    private Double rating;
}
