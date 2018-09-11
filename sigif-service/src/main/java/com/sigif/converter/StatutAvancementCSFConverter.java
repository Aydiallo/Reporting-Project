package com.sigif.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sigif.enumeration.StatutAvancementCSF;

/**
 * Convertisseur de StatutAvancementCSF.
 * 
 * @author Mickael Beaupoil
 *
 */
@Converter
public class StatutAvancementCSFConverter implements AttributeConverter<StatutAvancementCSF, String> {

    @Override
    public String convertToDatabaseColumn(StatutAvancementCSF value) {
        if (value == null) {
            return null;
        }
        return value.displayName();
    }

    @Override
    public StatutAvancementCSF convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return StatutAvancementCSF.fromValue(value);
    }
}