package com.nearu.nearu.object.request;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApplicationDto {
    private Integer applicationNo;
    private Boolean status;
    private LocalDateTime dueDate;
    private Integer durationHours;
    private String location;
    private String conditions; // html에서 "reasons"
    private LocalDateTime dDay;
    private Integer adminNo;
    private String userId;

    public void setDueDate(String dueDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dueDate = LocalDateTime.parse(dueDate, formatter);
    }

    public void setdDay(String dDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dDay = LocalDateTime.parse(dDay, formatter);
    }


}
