package com.sigif.dao;

import java.util.Date;
import java.util.List;

import com.sigif.enumeration.StatutAvancementCSF;
import com.sigif.enumeration.StatutCertificationCSF;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.ConstatationServiceFait;

/**
 * Classe d'accès aux données des CSF.
 * 
 * @author Mickael Beaupoil
 *
 */
 public interface ConstatationServiceFaitDAO extends AbstractDAO<ConstatationServiceFait> {
    /**
     * 
     * Retourne la Commande d'achat liée à une CSF donnée.
     * 
     * @param numDossierCSF
     *            numéro de dossier de la CSF
     * @return la CA liée à la CSF
     * @throws TechnicalException
     *             si l'accès BD échoue
     * @author Meissa Beye
     * @since 21/06/2017
     */
    CommandeAchat getCAofCSF(String numDossierCSF) throws TechnicalException;

    /**
     * Retourne le statut d'acceptation d'une CSF donnée.
     * 
     * @param numDossierCSF
     *            numéro de dossier de la CSF
     * @return statut d'acceptation
     * @throws TechnicalException
     *             si l'accès BD échoue
     * @author Meissa Beye
     * @since 22/06/2017
     */
    String getAcceptationStatus(String numDossierCSF) throws TechnicalException;

    /**
     * Indique si l'utilisateur est habilité à réceptionner sur l'un des
     * services déepnsiers de l'un des postes CA réceptionnés par cette CSF.
     * 
     * @param login
     *            login de l'utilisateur
     * @param csf
     *            Constatation de service fait
     * @return true si l'utilisateur est habilité à réceptionner sur l'un des
     *         services déepnsiers de l'un des postes CA réceptionnés par cette
     *         CSF
     */
    boolean isAuthorizedOnCsf(String login, ConstatationServiceFait csf);

    /**
     * Calcule le nb total de CSF répondant aux critères donnés.
     * 
     * @param login
     *            login de l'utilisateur courant (n'est jamais null)
     * @param loginReceptionnaire
     *            login de l'utilisateur receptionneur (peut être null)
     * @param dateFrom
     *            Date minimale de création de la CSF (au format JJ/MM/AAAA)
     *            (peut être null)
     * @param dateTo
     *            Date maximale de création de la CSF (au format JJ/MM/AAAA)
     *            (peut être null)
     * @param statutAvancement
     *            Statut d'avancement de la CSF (peut être null)
     * @param statutCertification
     *            Statut de certification de la CSF (peut être null)
     * @param statutAcceptation
     *            est calculé en fonction des quantités reçues et acceptées
     *            (peut être vide ou null)
     * @param numCSF
     *            Numéro de CSF (peut être null)
     * @param numCa
     *            Numéro de Ca (peut être null).
     * 
     * @return le nombre de résultats
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique
     * @author Mamadou Ndir
     */
    int countNbCSF(String login, String loginReceptionnaire, Date dateFrom, Date dateTo,
            StatutAvancementCSF statutAvancement, StatutCertificationCSF statutCertification, String statutAcceptation,
            String numCSF, String numCa) throws TechnicalException;

    /**
     * Retour les nbMaxResults plus récentes (par date de réception) CSFs
     * répondant aux critères donnés.
     * 
     * @param login
     *            login de l'utilisateur courant (n'est jamais null)
     * @param loginReceptionnaire
     *            login de l'utilisateur receptionneur (peut être null)
     * @param dateFrom
     *            Date minimale de création de la CSF (au format JJ/MM/AAAA)
     *            (peut être null)
     * @param dateTo
     *            Date maximale de création de la CSF (au format JJ/MM/AAAA)
     *            (peut être null)
     * @param statutAvancement
     *            Statut d'avancement de la CSF (peut être null)
     * @param statutCertification
     *            Statut de certification de la CSF (peut être null)
     * @param statutAcceptation
     *            est calculé en fonction des quantités reçues et acceptées
     *            (peut être vide ou null)
     * @param numCSF
     *            Numéro de CSF (peut être null)
     * @param numCa
     *            Numéro de Ca (peut être null).
     * @param nbMaxResults
     *            Nombre maximum de résultats remontés
     * 
     * @return les nbMaxResults plus récentes (par date de réception) CSFs
     *         répondant aux critères donnés.
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique
     * @author Mamadou Ndir
     */
    List<ConstatationServiceFait> searchCSFByCriteria(String login, String loginReceptionnaire, Date dateFrom,
            Date dateTo, StatutAvancementCSF statutAvancement, StatutCertificationCSF statutCertification,
            String statutAcceptation, String numCSF, String numCa, int nbMaxResults) throws TechnicalException;
    
    //Alpha ajout//
     List<ConstatationServiceFait> listNotDeletableCSF(int nbJoursDepuisModif,
            int nbJoursDepuisModifStatutTerminal) ;
	 List<ConstatationServiceFait> listConstatationServiceFaits() ;
	 List<CommandeAchat> listCAfromCSF() ;
	
	 List<ConstatationServiceFait> listDeletableCSF(List<ConstatationServiceFait> csf) ;
	
	 /* Les csf liees o CA supprimables **/
     List<ConstatationServiceFait> CSFLieesCA(List<CommandeAchat> liste) ;
    
    /*** suppressimer les CSF perimes **/
     void delete(List<ConstatationServiceFait> liste) ;
}
