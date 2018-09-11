package com.sigif.service;

import com.sigif.modele.Utilisateur;

//import fr.demarche.en.ligne.communs.mail.MailException;

/**
 * Service d'envoi de mail.
 *
 */
public interface SendMailService {

    /**
     * Envoi un mail.
     * 
     * @param destinataire
     *            Adresse mail du destinataire
     * @param objet
     *            Objet du mail
     * @param message
     *            Message du mail
     * @throws MailException
     *             Si l'envoi du mail échoue
     */
   // void sendEmail(String destinataire, String objet, String message) throws MailException;

    /**
     * Envoi un mail indiquant le nouveau mot de passe à l'utilisateur donné.
     * 
     * @param user
     *            Utilisateur auquel envoyer le mail
     * @param newPassword
     *            Nouveau password à transmettre à l'utilisateur
     * @throws MailException
     *             Si l'envoi de mail échoue
     */
   // void sendNewPasswordEmail(Utilisateur user, String newPassword) throws MailException;
}
