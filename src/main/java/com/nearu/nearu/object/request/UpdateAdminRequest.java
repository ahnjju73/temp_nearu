package com.nearu.nearu.object.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateAdminRequest {
    private List<FavoritesDto> favorites;
    private Integer userNo;
    private String userId;
    private Boolean emailNotification;
    private Boolean msgNotification;
    private Boolean phoneNotification;
}
