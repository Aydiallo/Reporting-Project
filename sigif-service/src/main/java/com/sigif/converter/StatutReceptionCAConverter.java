package com.sigif.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sigif.enumeration.StatutReceptionCA;

/**
 * Convertisseur de StatutReceptionCA.
 * @author Mickael Beaupoil
 *
 */
@Converter
public class StatutReceptionCAConverter implements AttributeConverter<StatutReceptionCA, String> {

    @Override
    public String convertToDatabaseColumn(StatutReceptionCA value) {
        if (value == null) {
            return null;
        }        
        return value.displayName();
    }

    @Override
    public StatutReceptionCA convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        
        return StatutReceptionCA.fromValue(value);
    }
}