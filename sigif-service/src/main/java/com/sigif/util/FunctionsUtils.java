package com.sigif.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.sigif.exception.ApplicationException;

/**
 * Classe des fonctions utilitaires.
 *
 */
public final class FunctionsUtils {

    /**
     * Constructeur vide et privé car la classe est utilitaire.
     */
    private FunctionsUtils() {
    }

    /**
     * Fonction de cryptage d'un mot de passe.
     * 
     * @author Meissa / 10-05-2017
     * @param clearPassword
     *            Mot de passe en clair
     * @return cryptedPassword Mot de passe crypté
     */
    public static String getCryptedPassword(String clearPassword) {
        String cryptedPassword = "";
        final int radixHexa = 16;
        final int cleHash = 0xff;
        final int addForCrypt = 0x100;

        try {
            // Hachage en md5
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(clearPassword.getBytes());
            byte[] hached = mDigest.digest();

            // conversion au format héxadécimal
            StringBuilder hexaFormat = new StringBuilder();
            for (int i = 0; i < hached.length; i++) {
                hexaFormat.append(Integer.toString((hached[i] & cleHash) + addForCrypt, radixHexa).substring(1));
            }
            cryptedPassword = hexaFormat.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return cryptedPassword;
    }

    /**
     * Vérifie qu'une chaîne donnée n'est ni null, ni vide puis renvoie la
     * chaîne sans les espaces de début et de fin.
     * 
     * @param paramName
     *            Nom du paramètre (pour log)
     * @param paramValue
     *            Valeur du paramètre
     * @param logger
     *            Loggueur
     * @return La chaîne sans les espaces de début et de fin ou une exception si
     *         la chaîne est null ou vide
     * @throws ApplicationException
     *             si la valeur à vérifier est null ou vide
     */
    public static String checkNotNullNotEmptyAndTrim(String paramName, String paramValue, Logger logger)
            throws ApplicationException {
        String resultTrimmed = null;
        if (!StringUtils.isNotBlank(paramValue)) {
            String msgInvalidParam =
                    String.format("Paramètre incorrect : le paramètre '%s' est null ou vide.", paramName);
            logger.error(msgInvalidParam);
            throw new ApplicationException(msgInvalidParam);
        } else {
            resultTrimmed = paramValue.trim();
        }
        return resultTrimmed;
    }

    /**
     * Cette fonction permet de trier par ordre ASC de n'importe quelle map.
     * 
     * @author Mamadou Ndir
     * @param map
     *            map à trier
     * @param <K>  type de la clé
     * @param <V>  type de la valeur
     * @return la map triée par valeur
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                return (e1.getValue()).compareTo(e2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

   /**
    * Remplace une chaîne vide ou null par null.
    * @param value Valeur à vérifier et transformer.
    * @return la valeur trimmé ou null si elle était vide ou null.
    */
    public static String trimOrNullifyString(String value) {
        String resultTrimmed = null;
        if (StringUtils.isNotBlank(value)) {            
            resultTrimmed = value.trim();
        }
        return resultTrimmed;
    }
}
