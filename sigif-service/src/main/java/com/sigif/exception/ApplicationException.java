package com.sigif.exception;

/**
 * Exception de l'application Sigif-formulaires signalant un problème
 * fonctionnel (données incorrectes, état d'une demande incohérent, ...).
 *
 */
public class ApplicationException extends Exception {
    /**
     * UID utilisé pour la sérialisation de l'exception.
     */
    private static final long serialVersionUID = -7611804645670022801L;

    /**
     * Crée une nouvelle instance de ApplicationException.
     */
    public ApplicationException() {
    }

    /**
     * Crée une nouvelle instance de ApplicationException.
     * 
     * @param message
     *            Le message détaillant l'exception
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Crée une nouvelle instance de ApplicationException.
     * 
     * @param cause
     *            L'exception à l'origine de cette exception
     */
    public ApplicationException(Throwable cause) {
        super(cause);
    }

    /**
     * Crée une nouvelle instance de ApplicationException.
     * 
     * @param message
     *            Le message détaillant l'exception
     * @param cause
     *            L'exception à l'origine de cette exception
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
