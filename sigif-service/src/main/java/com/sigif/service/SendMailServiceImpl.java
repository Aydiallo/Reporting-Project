package com.sigif.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.sigif.app.SigifProprietes;
import com.sigif.modele.Utilisateur;

//import fr.demarche.en.ligne.communs.mail.EnvoyerMail;
//import fr.demarche.en.ligne.communs.mail.MailException;

/**
 * Implémentation de la classe d'envoi de mail.
 *
 */
@Service("sendMailService")
public class SendMailServiceImpl implements SendMailService {

    /**
     * Environnement Spring (pour la récupération des propriétés).
     */
	/*
    @Autowired
    Environment environnement;

    @Override
    public void sendEmail(String destinataire, String objet, String message) throws MailException {
        List<String> listDestinataire = new ArrayList<String>();
        listDestinataire.add(destinataire);
        EnvoyerMail.sendMail(environnement.getRequiredProperty(SigifProprietes.PROP_PASSERELLE_SMTP),
                environnement.getRequiredProperty(SigifProprietes.PROP_EMETTEUR_MAIL_SIGIF), listDestinataire, objet,
                message, null);
    }

    @Override
    public void sendNewPasswordEmail(Utilisateur user, String newPassword) throws MailException {
        List<String> listDestinataire = new ArrayList<String>();
        listDestinataire.add(user.getCourriel());

        String objet = "[Sigif-Formulaires] Réinitialisation du mot de passe";
        String message = "Bonjour " + user.getPrenom() + " " + user.getNom() + ","
                + "\n Votre nouveau mot de passe est : " + newPassword
                + "\n \n Vous devrez changer ce mot de passe lors de votre prochaine connexion à SIGIF-Formulaires."
                + " \n Merci de cliquer sur le lien ci-dessous ou de copier ce lien dans la barre d'adresse de votre "
                + " \n navigateur pour accéder à l’application SIGIF-Formulaires : " 
                + " \n \n " + environnement.getRequiredProperty(SigifProprietes.PROP_URL_SIGIF_PLATEFORM) 
                + " \n \n Si vous n'avez pas demandé à réinitialiser votre mot de passe, nous vous recommandons de"
                + " \n contacter l’administrateur de SIGIF." 
                + " \n \n Cordialement," + " \n l’application SIGIF" 
                + " \n \n PS : Ce message a été envoyé automatiquement. Merci de ne pas y répondre.";

        EnvoyerMail.sendMail(environnement.getRequiredProperty(SigifProprietes.PROP_PASSERELLE_SMTP),
                environnement.getRequiredProperty(SigifProprietes.PROP_EMETTEUR_MAIL_SIGIF), listDestinataire, objet,
                message, null);
    }
*/
}
