package com.sigif.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sigif.enumeration.StatutApprobationDA;

/**
 * Convertisseur de StatutApprobationDA.
 * 
 * @author Mickael Beaupoil
 *
 */
@Converter
public class StatutApprobationDAConverter implements AttributeConverter<StatutApprobationDA, String> {

    @Override
    public String convertToDatabaseColumn(StatutApprobationDA value) {
        if (value == null) {
            return null;
        }
        return value.displayName();
    }

    @Override
    public StatutApprobationDA convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return StatutApprobationDA.fromValue(value);
    }
}