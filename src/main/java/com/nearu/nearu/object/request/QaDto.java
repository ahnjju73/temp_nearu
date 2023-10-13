package com.nearu.nearu.object.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QaDto {
    private Integer qaNo;
    private Integer userNo;
    private Boolean anonymous;
    private String title;
    private String question;
    private String userId;
}
