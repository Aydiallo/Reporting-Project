package com.sigif.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sigif.dto.ConstatationServiceFaitDTO;
import com.sigif.enumeration.CsfUpdatableStatus;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.ConstatationServiceFait;

/**
 * Service d'accès aux CSF.
 *
 */
public interface ConstatationServiceFaitService extends AbstractService<ConstatationServiceFaitDTO> {

    /**
     * Ajoute une constatation de service fait en base avec les pièces jointes
     * associées (Les PJ peuvent être vides).
     * 
     * @param login
     *             Login du créateur
     * @param numTeledossier
     *             Numéro de télédossier généré
     * @param receptionDate
     *             Date de réception
     * @param spendingServiceCode
     *             Code du service dépensier
     * @param codeCa
     *             Code de la CA liée à la CSF
     * @param comment
     *             Commentaire
     * @param progressStatus
     *             Statut d'avancement
     * @param certificationStatus
     *             Statut de certification
     * @param listAttachments
     *             Liste des pièces jointes de la CSF. Chaque pièce jointe est
     *            représentée par une Map contenant les clés suivantes : <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_NATURE} - Nature de
     *            la PJ ("Autre", "PV", "BL", "Facture") <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_INTITULE} -
     *            Intitulé de la PJ (référence) <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_UPLOAD_STRING_PJ} -
     *            Chaîne getString u champ upload de la PJ <BR>
     * @param listPostesCsfMap
     *            Liste des postes de la CSF. Chaque poste est représentée par
     *            une Map contenant les clés suivantes : <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_POSTE_CSF} -
     *            Id du poste (IdCSFPoste) <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_CA_POSTE} -
     *            Numéro du poste CA (idSAP du poste) <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_QTE_RECUE} -
     *            Quantité reçue <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} -
     *            Quantité acceptée <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_COMMENTAIRE} -
     *            Commentaire<BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_AVANCEMENT}
     *            - Statut d'avancement <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_LIEU_STOCKAGE} - Id
     *            du lieu de stockage <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_NATURE} - Nature de
     *            pièce jointe ("Autre", "PV", "BL", "Facture") <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_UPLOAD_STRING_PJ} -
     *            Chaîne getString du champ upload de la PJ <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_INTITULE} -
     *            Intitulé de la PJ (Référence)<BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_MOTIF_REJET} -
     *            Motif de rejet du poste<BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_ID} -
     *            Id SAP<BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_DATE_MODIFICATION} -
     *            Date de modification SAP<BR>
     * @throws ApplicationException
     *             Si : <BR>
     *             - le n° de dossier est vide ou null <BR>
     *             - le login est vide ou null <BR>
     *             - le code du service dépensier est vide ou null <BR>
     *             - la date de réception est null <BR>
     *             - le numéro de CA est vide ou null <BR>
     *             - une des pièces jointes indiquées n'existe pas<BR>
     *             - la quantité restante d'un poste CA n'est pas suffisante par
     *             rapport à la quantité acceptée
     * @throws TechnicalException
     *             Si l'insertion échoue pour une raison technique
     * 
     * @return false si la CSF est verrouillée, true si la CSF a été
     *         correctement créée, une exception dans les autres cas
     * @since 29-05-2017
     */
    boolean saveCSF(String login, String numTeledossier, Date receptionDate, String spendingServiceCode, String codeCa,
            String comment, String progressStatus, String certificationStatus,
            List<Map<String, String>> listAttachments, List<Map<String, String>> listPostesCsfMap)
            throws ApplicationException, TechnicalException;

    /**
     * Verrouille une CSF pour signaler qu'elle est en cours de modification
     * (remplissage des colonnes loginVerrou et dateVerrou).<BR>
     * Note : colonnes non utilisées pour l'instant (Une CSF est modifiable
     * seulement si elle a été créé par l'utilisateur courant et qu'elle
     * contient des postes au statut différent de Certifié ou En attente de
     * certification.
     * 
     * @param login
     *            login de l'utilisateur qui verrouille
     * @param numeroCSF
     *            numéro de dossier de la CSF
     * @return true si la CSF a pu être verrouillée, false sinon
     * @throws ApplicationException
     *             si l'un des paramètres est null ou vide
     * @author Meissa
     * @since 21/06/2017
     */
    boolean lockCSF(String login, String numeroCSF) throws ApplicationException;

    /**
     * Déverrouille une CSF pour signaler qu'elle n'est plus en cours de
     * modification (mise à NULL des colonnes loginVerrou et dateVerrou). <BR>
     * Note : colonnes non utilisées pour l'instant (Une CSF est modifiable
     * seulement si elle a été créé par l'utilisateur courant et qu'elle
     * contient des postes au statut différent de Certifié ou En attente de
     * certification.
     * 
     * @param numeroCSF
     *            numéro de dossier de la CSF à déverrouiller
     * @throws ApplicationException
     *             Si l'un des paramètres est null ou vide
     * @author Meissa
     * @since 21/06/2017
     */
    void unlockCSF(String numeroCSF) throws ApplicationException;

    /**
     * Vérifie que la CSF est modifiable par l'utilisateur. Une CSF est
     * modifiable seulement si : <BR>
     * - Son statut d’avancement est : <BR>
     * ¤¤¤ « Brouillon » ou « En attente d'envoi » <BR>
     * ¤¤¤ OU « en cours de traitement » ET au moins un des postes n'est pas «
     * certifié » (correspond au cas où la CSF est rectifiable)<BR>
     * - Sa commande (CA) de rattachement n’est pas au statut « Non validé »<BR>
     * - L’utilisateur connecté est habilité à réceptionner pour le service
     * dépensier de cette CSF<BR>
     * - Si elle a été créée par l’utilisateur connecté
     * 
     * @param login
     *            login de l'utilisateur
     * @param numeroCSF
     *            NumeroDossier
     * @return - {@link com.sigif.enumeration.CsfUpdatableStatus#NO} si la CSF
     *         n'est pas modifiable<BR>
     *         - {@link com.sigif.enumeration.CsfUpdatableStatus#FULL} si la CSF
     *         est complètement modifiable<BR>
     *         -
     *         {@link com.sigif.enumeration.CsfUpdatableStatus#CORRECTION_ALLOWED}
     *         si la CSF a déjà été envoyée et peut être corrigée<BR>
     * @throws ApplicationException
     *             Si le login ou le no CSF est vide ou null
     * @author Meissa
     * @since 21/06/2017
     */
    CsfUpdatableStatus isCSFUpdatable(String login, String numeroCSF) throws ApplicationException;

    /**
     * Vérifie que la CSF est supprimable par l'utilisateur. Une CSF est
     * modifiable seulement si : <BR>
     * - Son statut d'avancement est « Brouillon » ou « En attente d'envoi »
     * <BR>
     * - aucun de ses postes n'est à un autre statut que NULL (correspond au cas
     * où la CSF est rectifiable)<BR>
     * - Au moins un service dépensier des postes de la CA correspond à un
     * service dépensier de l’utilisateur connecté <BR>
     * - Si elle a été créée par l’utilisateur connecté
     * 
     * @param login
     *            login de l'utilisateur
     * @param numeroCSF
     *            NumeroDossier
     * @return true si elle est supprimable
     * @throws ApplicationException
     *             Si le login ou le no CSF est vide ou null
     */
    boolean isCSFDeletable(String login, String numeroCSF) throws ApplicationException;

    /**
     * Retourne le Statut d'acceptation d'une CSF. <BR>
     * - {@link com.sigif.util.Constantes#STATUT_ACCEPTATION_ACCEPTEE} si le
     * total des quantités acceptées et celui des quantités reçues sont égaux
     * <BR>
     * - {@link com.sigif.util.Constantes#STATUT_ACCEPTATION_ACCEPTEE_PART} si
     * le total des quantités acceptées est inférieur à celui des quantités
     * reçues mais différent de 0 <BR>
     * - {@link com.sigif.util.Constantes#STATUT_ACCEPTATION_ACCEPTEE} si le
     * total des quantités acceptées est égal à 0.
     * 
     * @param numDossierCSF
     *            numéro de dossier de la CSF
     * @return le statut d'acceptation de la CSF (Acceptée, Acceptée
     *         partiellement ou refusée)
     * @author Meissa
     * @throws TechnicalException
     *             si l'accès BD échoue
     * @since 22/06/2017
     */
    String getStatutAcceptationOfCSF(String numDossierCSF) throws TechnicalException;

    /**
     * Retourne les informations d'une CSF sous forme d'une Map :<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_CSF} : Valeur = Numéro
     * de CSF (numéro dossier) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_RECEPTION} : Valeur =
     * date de réception (JJ/MM/AAAA) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_RECEPTIONNAIRE} : Valeur =
     * réceptionnaire (prénom nom) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_AVANCEMENT} : Valeur =
     * statut d'avancement <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_VALIDATION_CSF} :
     * Valeur = statut de certification <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_ACCEPTATION_CSF} :
     * Valeur = statut d'acceptation. <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_COMMENTAIRE} :
     * Valeur = commentaire. <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_SERVICE_DEPENSIER} :
     * Valeur = code du service dépensier <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_SERVICE_DEPENSIER} :
     * Valeur = label du service dépensier. <BR>
     * 
     * @param numDossierCSF
     *            numéro de dossier de la CSF
     * @return une Map contenant les infos de la CSF 
     * @throws ApplicationException
     *             si le numéro de dossier est vide ou null
     * @throws TechnicalException
     *             si l'accès BD échoue
     */
    Map<String, String> getInfosCSF(String numDossierCSF) throws ApplicationException, TechnicalException;

    /**
     * Calcule le nb total de CSF répondant aux critères donnés.
     * 
     * @param login
     *            login de l'utilisateur courant (n'est jamais null)
     * @param loginReceptionnaire
     *            login du réceptionnaire (peut être null ou vide)
     * @param dateFrom
     *            Date minimale de création de la CSF (au format JJ/MM/AAAA)
     *            (peut être vide ou null)
     * @param dateTo
     *            Date maximale de création de la CSF (au format JJ/MM/AAAA)
     *            (peut être vide ou null)
     * @param statutAvancement
     *            Statut d'avancement de la CSF (peut être vide ou null)
     * @param statutCertification
     *            Statut de certification de la CSF (peut être vide ou null)
     * @param statutAcceptation
     *            est calculé en fonction des quantités reçues et acceptées
     *            (peut être vide ou null)
     * @param numCSF
     *            Numéro de CSF (peut être vide ou null)
     * @param numCa
     *            Numéro de CA (peut être vide ou null)
     * 
     * @return le nombre de résultats
     * @throws ApplicationException
     *             Si les dates de début et de fin ne sont pas au format attendu
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique
     * @author Mamadou Ndir
     */
    int countNbCSF(String login, String loginReceptionnaire, String dateFrom, String dateTo, String statutAvancement,
            String statutCertification, String statutAcceptation, String numCSF, String numCa)
            throws TechnicalException, ApplicationException;

    /**
     * Retourne une Map de pièces jointes d'une CSF.
     * 
     * @param numDossierCSF
     *            numéro de dossier de la CSF
     * @return une Map des PJ d'une CSF<BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_NATURE} : Valeur =
     *         Nature de la PJ<BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_INTITULE_PJ} : Valeur
     *         = Intitulé de la PJ <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_UPLOAD_STRING_PJ} :
     *         Valeur = getString de la PJ (replacée dans le dossier temp) <BR>
     * @throws ApplicationException
     *             si le numéro de dossier est vide ou null
     * @author Meissa
     * @since 22/06/2017
     */
    Map<String, String[]> getPiecesJointesOfCSF(String numDossierCSF) throws ApplicationException;

    /**
     * Retourne sous forme de map (key = nom du champ) les informations des CSF
     * répondant au critères donnés.
     * 
     * Le résultat est sous forme d'une Map dont chaque entrée représente une
     * colonne du tableau résultat (clé = titre de la colonne, valeur = tableau
     * de valeurs de la colonne). Les clés suivantes sont disponibles : <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_CSF} : Numéro de
     * télédossier de la DA <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_RECEPTIONNAIRE} :
     * Réceptionneur (Prénom Nom)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_RECEPTION} : Date de
     * réception (au format JJ/MM/AAAA) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_AVANCEMENT} : Statut
     * d'avancement<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_VALIDATION_CSF} :
     * Statut de certification<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_ACCEPTATION_CSF} :
     * Statut d'acceptation<BR>
     * 
     * @param login
     *            login de l'utilisateur courant (n'est jamais null)
     * @param loginReceptionnaire
     *            login du réceptionnaire (peut être null ou vide)
     * @param dateFrom
     *            Date minimale de création de la CSF (au format JJ/MM/AAAA)
     *            (peut être vide ou null)
     * @param dateTo
     *            Date maximale de création de la CSF (au format JJ/MM/AAAA)
     *            (peut être vide ou null)
     * @param statutAvancement
     *            Statut d'avancement de la CSF (peut être vide ou null)
     * @param statutCertification
     *            Statut de certification de la CSF (peut être vide ou null)
     * @param statutAcceptation
     *            est calculé en fonction des quantités reçues et acceptées
     *            (peut être vide ou null)
     * @param numCSF
     *            Numéro de CSF (peut être vide ou null)
     * @param numCa
     *            Numéro de CA (peut être vide ou null)
     * @param nbMaxResults
     *            Nombre maximum de résultats remontés
     * 
     * @return les nbMaxResults premières CSFs répondant aux critères donnés.
     * @throws ApplicationException
     *             Si les dates de début et de fin ne sont pas au format attendu
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique
     * @author Mamadou Ndir
     */
    Map<String, Object[]> searchCSFByCriteria(String login, String loginReceptionnaire, String dateFrom, String dateTo,
            String statutAvancement, String statutCertification, String statutAcceptation, String numCSF, String numCa,
            int nbMaxResults) throws TechnicalException, ApplicationException;

    /**
     * Supprime une CSF ainsi que ses pièces jointes et les postes CSF liés (et
     * leur PJ).
     * 
     * @param numDossierCSF
     *            numéro de dossier de la CSF
     * @param login
     *            login de l'utilisateur qui souhaite supprimer la CSF
     * @return true ou false selon que la suppression est reussie ou pas.
     * @throws ApplicationException
     *             si le numéro de dossier est vide ou null
     * @author Meissa
     * @since 23/06/2017
     */
    boolean deleteCSF(String numDossierCSF, String login) throws ApplicationException;

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
