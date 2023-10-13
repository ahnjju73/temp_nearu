package com.nearu.nearu.entity.types.converter;

import com.nearu.nearu.entity.types.UserType;

import javax.persistence.AttributeConverter;

public class UserTypeConverter implements AttributeConverter<UserType, String> {
    @Override
    public String convertToDatabaseColumn(UserType attribute) {
        return attribute == null ? null : attribute.getTypeCode();
    }

    @Override
    public UserType convertToEntityAttribute(String dbData) {
        return UserType.getTypeCode(dbData);
    }
}
