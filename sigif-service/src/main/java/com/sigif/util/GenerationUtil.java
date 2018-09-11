package com.sigif.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe utilitaire de génération des numéros de télédossiers.
 *
 * @author Gaël Weil-Jourdan / Christophe Robine
 */
public final class GenerationUtil {

    /**
     * Loggueur.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerationUtil.class);

    // -------------------- Attributs --------------------

    /**
     * Format de date.
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yy");

    /**
     * Liste de digits utilisés pour la génération aléatoire.
     */
    private static final char[] UID_DIGITS = { 'Y', 'N', 'Q', '8', '6', '7', 'D', 'V', 'B', 'M', 'R', 'W', 'I', 'C',
            'A', 'G', '9', 'T', 'O', '3', 'X', 'E', '5', 'P', 'S', '2' };

    /**
     * UID utilisé pour la génération aléatoire de numéro.
     */
    private static final String UID_THE_STRING = "842TPZPLDO";

    /**
     * Longueur de UID_THE_STRING.
     */
    private static final int NBCHAR_UID = UID_THE_STRING.length();

    /**
     * Générateur de numéro aléatoire.
     */
    private static Random random;

    // -------------------- Constructeur --------------------

    /**
     * Constructeur vide car classe utilitaire.
     */
    private GenerationUtil() {
        // Classe statique
    }

    static {
        try {
            // on utilise directement le constructeur plutôt que
            // SecureRandom.getInstanceStrong() qui peut bloquer le thread si la
            // réserve d'aléatoire est insuffisante
            random = new SecureRandom();
        } catch (Exception e) {
            LOGGER.warn("Utilisation du générateur de nombre aléatoire simple suite à une erreur avec le générateur "
                    + "sécurisé", e);
            random = new Random();
        }
    }

    // -------------------- Methodes --------------------

    /**
     * Génère un numéro de télédossier de 17 caractères sans sequence.
     * 
     * @return un numéro de télédossier
     * @throws Exception
     *             Une exception technique s'est produite lors de la génération
     *             de numéro aléatoire.
     */
    public static String generateTeledossierNumberWithoutSequence() throws Exception {
        String numeroTeledossier;
        synchronized (DATE_FORMAT) {
            numeroTeledossier = DATE_FORMAT.format(new Date()).substring(1);
        }
        numeroTeledossier += "-";
        Long timestamp = System.currentTimeMillis();

        // Algo TOF
        numeroTeledossier += textGenerator(timestamp);

        // Algo Gael (plus simple mais plus long et potentiellement moins
        // 'unique')
        // numeroTeledossier += StringUtils.right(timestamp.toString(), 3);
        // numeroTeledossier += RandomStringUtils.random(8, 0,
        // UID_DIGITS.length, false, false,
        // UID_DIGITS, random);        

        LOGGER.info("Numéro de télédossier généré : {%s}", numeroTeledossier);
        return numeroTeledossier;
    }

    /**
     * Génération de la partie texte.
     *
     * @param sequence
     *            le numéro de sequence
     * @return la chaine pour le teledossier
     */
    public static String textGenerator(long sequence) {
        final int randomizerBound = 100;
        final int randomizerTooSmallLimit = 10;
        
        int trouble = random.nextInt(randomizerBound);
        while (trouble < randomizerTooSmallLimit) {
            trouble = random.nextInt(randomizerBound);
        }
        String toto = trouble + "" + sequence;
        long theLong = Long.parseLong(toto);
        String reponse = toUidString26(theLong);
        StringBuffer sb = new StringBuffer(reponse);
        int insert = random.nextInt(UID_THE_STRING.length());
        int digit = random.nextInt(sb.length());
        sb.insert(digit, UID_THE_STRING.charAt(insert));
        insert = random.nextInt(UID_THE_STRING.length());
        digit = random.nextInt(sb.length());
        sb.insert(digit, UID_THE_STRING.charAt(insert));
        return sb.toString();
    }

    /**
     * méthode de génération d'un String encodée base 26.
     *
     * @param entier
     *            le nombre
     * @return la string encodée.
     */
    private static String toUidString26(long entier) {
        long number = entier;
        int radix = UID_DIGITS.length;
        char[] buf = new char[NBCHAR_UID + 1];
        int charPos = NBCHAR_UID;
        while (number >= radix) {
            buf[charPos--] = UID_DIGITS[(int) (number % radix)];
            number = number / radix;
        }
        buf[charPos] = UID_DIGITS[(int) number];
        return new String(buf, charPos, NBCHAR_UID + 1 - charPos);
    }

    /**
     * méthode de génération d'un mot de passe aléatoire. 32 est la base de
     * representation et 40 est un multiple de 5 (ce qui donnera un mot de passe
     * de 8 caractere) qui représente le nombre de bits sur lequel on code un
     * chiffre de la base 32 (2^5)
     * 
     * @return generedRandomPassword.
     */
    public static String genereRandomPassword() {
        final int shortPasswordLength = 8;
        final int longPasswordLength = 9;
        String symboles = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String generedRandomPassword = "";
        Random r = new Random();
        while (true) {
            char[] password = new char[r.nextBoolean() ? shortPasswordLength : longPasswordLength];
            boolean hasUpper = false, hasLower = false, hasDigit = false;
            for (int i = 0; i < password.length; i++) {
                char ch = symboles.charAt(r.nextInt(symboles.length()));
                if (Character.isUpperCase(ch)) {
                    hasUpper = true;
                } else if (Character.isLowerCase(ch)) {
                    hasLower = true;
                } else if (Character.isDigit(ch)) {
                    hasDigit = true;
                }

                password[i] = ch;
            }
            if (hasUpper && hasLower && hasDigit) {
                generedRandomPassword = new String(password);
                return generedRandomPassword;
            }
        }
    }
}
