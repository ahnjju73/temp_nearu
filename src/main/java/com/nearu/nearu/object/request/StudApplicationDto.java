package com.nearu.nearu.object.request;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudApplicationDto {
    private Integer userNo;
    private Integer applicationNo;
    private Boolean isConfirmed;
    private String userId;
}
