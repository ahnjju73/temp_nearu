package com.nearu.nearu.entity.types;

import lombok.Getter;
@Getter
public enum UserType {
    VOLUNTEER("0", "volunteer"), PATIENT("1", "patient");

    private String typeCode;
    private String type;
    UserType(String typeCode, String type) {
        this.type = type;
        this.typeCode = typeCode;
    }

    public static UserType getType(String type){
        if(type == null)
            return null;
        for(UserType ut : UserType.values()){
            if(ut.getType().equals(type))
                return ut;
        }
        return null;
    }

    public static UserType getTypeCode(String type){
        if(type == null)
            return null;
        for(UserType ut : UserType.values()){
            if(ut.getTypeCode().equals(type))
                return ut;
        }
        return null;
    }
}
