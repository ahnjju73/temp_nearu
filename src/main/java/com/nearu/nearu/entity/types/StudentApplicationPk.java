package com.nearu.nearu.entity.types;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
public class StudentApplicationPk implements Serializable {

    @Column(name = "user_no")
    private Integer userNo;
    @Column(name = "application_no")
    private Integer applicationNo;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof StudentApplicationPk))
            return false;
        else
            return userNo == ((StudentApplicationPk)obj).userNo && applicationNo == ((StudentApplicationPk)obj).applicationNo;
    }
}
