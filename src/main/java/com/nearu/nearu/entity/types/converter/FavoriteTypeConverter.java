package com.nearu.nearu.entity.types.converter;

import com.nearu.nearu.entity.types.FavoriteTypes;

import javax.persistence.AttributeConverter;

public class FavoriteTypeConverter implements AttributeConverter<FavoriteTypes, String> {
    @Override
    public String convertToDatabaseColumn(FavoriteTypes attribute) {
        return attribute == null ? null : attribute.getTypeCode();
    }

    @Override
    public FavoriteTypes convertToEntityAttribute(String dbData) {
        return FavoriteTypes.getTypeCode(dbData);
    }
}
