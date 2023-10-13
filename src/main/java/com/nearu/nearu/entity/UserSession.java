package com.nearu.nearu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nearu.nearu.OriginObject;
import com.nearu.nearu.util.Crypt;
import com.nearu.nearu.util.SESSION;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name = "user_sessions")
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserSession extends OriginObject {

    @Id
    @Column(name = "user_no")
    private Integer userNo;

    @OneToOne(cascade = CascadeType.MERGE ,fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private User user;

    @Column(name = "session_key", length = 256)
    private String sessionKey;

    @Column(name = "salt", length = 256)
    private String salt;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private ZonedDateTime createdAt = ZonedDateTime.now();

    public void setUser(User user) {
        this.user = user;
        this.userNo = user.getUserNo();
    }

    @JsonIgnore
    public void makeSessionKey(){
        User users = this.getUser();
        this.salt = Crypt.newCrypt().getSalt(8);
        try {
            this.sessionKey = Jwts.builder()
                    .setIssuer(SESSION.TOKEN_ISSURE)
                    .setSubject(SESSION.TOKEN_NAME)
                    .claim("user_id", users.getUserId())
                    .claim("sess_now", LocalDateTime.now().toString())
                    .setIssuedAt(new Date())
                    .signWith(
                            SignatureAlgorithm.HS256,
                            this.salt.getBytes("UTF-8")
                    ).compact();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            withException("120-002");
        }
    }

}
