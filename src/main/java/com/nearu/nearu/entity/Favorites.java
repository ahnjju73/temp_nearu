package com.nearu.nearu.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nearu.nearu.entity.types.FavoriteTypes;
import com.nearu.nearu.entity.types.converter.FavoriteTypeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Table(name = "favorites")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_no")
    private Integer favoriteNo;

    @Column(name = "user_no")
    private Integer userNo;

    @ManyToOne
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private User user;

    @Column(name = "address")
    private String address;

    @Column(name = "favorite_type", columnDefinition = "ENUM")
    @Convert(converter = FavoriteTypeConverter.class)
    private FavoriteTypes favoriteTypes;

    @Column(name = "favorite_type", columnDefinition = "ENUM", insertable = false, updatable = false)
    private String favoriteTypeCode;

    public Favorites() {
    }

    public Favorites(String address) {
        this.address = address;
    }
}
