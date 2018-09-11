package com.sigif.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sigif.enumeration.StatutCertificationCSF;

/**
 * Convertisseur de StatutCertificationCSF.
 * 
 * @author Mickael Beaupoil
 *
 */
@Converter
public class StatutCertificationCSFConverter implements AttributeConverter<StatutCertificationCSF, String> {

    @Override
    public String convertToDatabaseColumn(StatutCertificationCSF value) {
        if (value == null) {
            return null;
        }
        return value.displayName();
    }

    @Override
    public StatutCertificationCSF convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return StatutCertificationCSF.fromValue(value);
    }
}