package com.sigif.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sigif.enumeration.StatutApprobationPosteDA;

/**
 * Convertisseur de StatutApprobationPosteDA.
 * 
 * @author Mickael Beaupoil
 *
 */
@Converter
public class StatutApprobationPosteDAConverter implements AttributeConverter<StatutApprobationPosteDA, String> {

    @Override
    public String convertToDatabaseColumn(StatutApprobationPosteDA value) {
        if (value == null) {
            return null;
        }
        return value.displayName();
    }

    @Override
    public StatutApprobationPosteDA convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return StatutApprobationPosteDA.fromValue(value);
    }
}