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
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_no")
    private Integer applicationNo;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "due_date", columnDefinition = "DATETIME")
    private LocalDateTime dueDate;

    @Column(name = "duration_hours")
    private Integer durationHours;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Column(name = "location", columnDefinition = "MEDIUMTEXT")
    private String location;

    @Column(name = "conditions", columnDefinition = "MEDIUMTEXT")
    private String conditions;

    @Column(name = "d_day", columnDefinition = "DATETIME")
    private LocalDateTime dDay;

    @Column(name = "admin_no")
    private Integer adminNo;

    @ManyToOne
    @JoinColumn(name = "admin_no", insertable = false, updatable = false)
    private User admin;

    public Application() {
    }

    public Application(LocalDateTime dueDate, String location, String conditions, LocalDateTime dDay, Integer adminNo, Integer durationHours) {
        this.dueDate = dueDate;
        this.location = location;
        this.conditions = conditions;
        this.dDay = dDay;
        this.adminNo = adminNo;
        this.durationHours = durationHours;
    }

}
