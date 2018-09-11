package com.sigif.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sigif.exception.ApplicationException;

/**
 * Convertit un string au format dd/mm/yyyy en date java.
 * 
 * @author Malick
 */
public final class StringToDate {
    /**
     * Constructeur privé vide pour classe utilitaire.
     */
    private StringToDate() {
    }

    /**
     * Convertit une string en Date.
     * 
     * @param stringDate
     *            au format ("dd/MM/yyyy HH:mm:ss")
     * @return Date correspondante
     * @throws ApplicationException
     *             Si la date n'est pas au bon format
     */
    public static Date convertStringToDate(String stringDate) throws ApplicationException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = formatter.parse(stringDate);
            return date;
        } catch (ParseException e) {
            throw new ApplicationException(
                    String.format("La date '%s' n'est pas au format 'dd/MM/yyyy HH:mm:ss'", stringDate), e);
        }
    }
    
    /**
     * Convertit une string en Date.
     * 
     * @param stringDate
     *            au format ("dd/MM/yyyy")
     * @return Date correspondante
     * @throws ApplicationException
     *             Si la date n'est pas au bon format
     */
    public static Date convertStringToSimpleDate(String stringDate) throws ApplicationException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(stringDate);
            return date;
        } catch (ParseException e) {
            throw new ApplicationException(String.format("La date '%s' n'est pas au format 'dd/MM/yyyy'", stringDate), e);
        }
    }

    /**
     * Convertit une date en string au format ("dd/MM/yyyy").
     * 
     * @param dateParam
     *            date à convertir
     * @return String au format ("dd/MM/yyyy")
     */
    public static String convertDateToString(Date dateParam) {

        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        return df.format(dateParam);
    }

    /**
     * Convertit une date en string au format ("dd/MM/yyyy HH:mm:ss").
     * 
     * @param dateParam
     *            date à convertir
     * @return String au format ("dd/MM/yyyy HH:mm:ss")
     */
    public static String convertDateTimeToString(Date dateParam) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return df.format(dateParam);
    }

}
