package com.sigif.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sigif.enumeration.StatutAvancementDA;

/**
 * Convertisseur de StatutAvancementDA.
 * 
 * @author Mickael Beaupoil
 *
 */
@Converter
public class StatutAvancementDAConverter implements AttributeConverter<StatutAvancementDA, String> {

    @Override
    public String convertToDatabaseColumn(StatutAvancementDA value) {
        if (value == null) {
            return null;
        }
        return value.displayName();
    }

    @Override
    public StatutAvancementDA convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return StatutAvancementDA.fromValue(value);
    }
}