package com.sigif.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sigif.enumeration.StatutCertificationPosteCSF;

/**
 * Convertisseur de StatutCertificationPosteCSF.
 * 
 * @author Mickael Beaupoil
 *
 */
@Converter
public class StatutCertificationPosteCSFConverter implements AttributeConverter<StatutCertificationPosteCSF, String> {

    @Override
    public String convertToDatabaseColumn(StatutCertificationPosteCSF value) {
        if (value == null) {
            return null;
        }
        return value.displayName();
    }

    @Override
    public StatutCertificationPosteCSF convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return StatutCertificationPosteCSF.fromValue(value);
    }
}