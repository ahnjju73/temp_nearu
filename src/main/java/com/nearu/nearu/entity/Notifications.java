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
@Table(name = "notifications")
public class Notifications {

    @Id
    @Column(name = "user_no")
    private Integer userNo;

    @ManyToOne
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private User user;

    @Column(name = "email_notif")
    private Boolean emailNotif;

    @Column(name = "msg_notif")
    private Boolean msgNotif;

    @Column(name = "phone_notif")
    private Boolean phoneNotif;

    public Notifications() {
    }

    public Notifications(Boolean emailNotif, Boolean msgNotif, Boolean phoneNotif) {
        this.emailNotif = emailNotif;
        this.msgNotif = msgNotif;
        this.phoneNotif = phoneNotif;
    }
}

