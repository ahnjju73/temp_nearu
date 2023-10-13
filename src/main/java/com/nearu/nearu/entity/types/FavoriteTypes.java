package com.nearu.nearu.entity.types;

import lombok.Getter;

@Getter
public enum FavoriteTypes {
    HOME("0", "home"), HOSPITAL("1", "hospital");

    private String typeCode;
    private String type;

    FavoriteTypes(String typeCode, String type) {
        this.type = type;
        this.typeCode = typeCode;
    }

    public static FavoriteTypes getTypeCode(String type){
        if(type == null)
            return null;
        for (FavoriteTypes ft : FavoriteTypes.values()) {
            if(ft.getTypeCode().equals(type))
                return ft;
        }
        return null;
    }

    public static FavoriteTypes getType(String type){
        if(type == null)
            return null;
        for (FavoriteTypes ft : FavoriteTypes.values()) {
            if(ft.getType().equals(type))
                return ft;
        }
        return null;
    }


}
