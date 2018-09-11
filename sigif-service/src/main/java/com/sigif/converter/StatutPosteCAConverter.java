package com.sigif.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sigif.enumeration.StatutPosteCA;

/**
 * Convertisseur de StatutPosteCA.
 * 
 * @author Mickael Beaupoil
 *
 */
@Converter
public class StatutPosteCAConverter implements AttributeConverter<StatutPosteCA, String> {

    @Override
    public String convertToDatabaseColumn(StatutPosteCA value) {
        if (value == null) {
            return null;
        }
        return value.displayName();
    }

    @Override
    public StatutPosteCA convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return StatutPosteCA.fromValue(value);
    }
}