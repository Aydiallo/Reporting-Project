package com.sigif.exception;

/**
 * Exception de l'application Sigif-formulaires signalant un problème technique
 * (lors d'un appel à des fonctions basiques du framework).
 * 
 * @author Mickael Beaupoil
 *
 */
public class TechnicalException extends Exception {
    /**
     * UID utilisé pour la sérialisation de l'exception.
     */
    private static final long serialVersionUID = 5517586290745598157L;

    /**
     * Crée une nouvelle instance de TechnicalException.
     */
    public TechnicalException() {
    }

    /**
     * Crée une nouvelle instance de TechnicalException.
     * 
     * @param message
     *            Le message détaillant l'exception
     */
    public TechnicalException(String message) {
        super(message);
    }

    /**
     * Crée une nouvelle instance de TechnicalException.
     * 
     * @param cause
     *            L'exception à l'origine de cette exception
     */
    public TechnicalException(Throwable cause) {
        super(cause);
    }

    /**
     * Crée une nouvelle instance de TechnicalException.
     * 
     * @param message
     *            Le message détaillant l'exception
     * @param cause
     *            L'exception à l'origine de cette exception
     */
    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
