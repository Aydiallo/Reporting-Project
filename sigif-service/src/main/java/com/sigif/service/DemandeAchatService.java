package com.sigif.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sigif.dto.DemandeAchatDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PosteCommandeAchat;

/**
 * service d'accès aux demandes d'achat.
 * 
 * @author Mickael Beaupoil
 *
 */
public interface DemandeAchatService extends AbstractService<DemandeAchatDTO> {

    /**
     * Ajoute une demande d'achat simple en base.
     * 
     * @param login
     *            login de l'utilisateur connecté
     * @param ministeryCode
     *            code du ministère sélectionné dans la démarche par le
     *            demandeur
     * @param spendingServiceCode
     *            code du service dépensier sélectionné dans la démarche par le
     *            demandeur
     * @param programCode
     *            code du programme sélectionné dans la démarche par le
     *            demandeur
     * @param creationDate
     *            date de création de la demande
     * @param requesterLogin
     *            login du demandeur
     * @param purchasingCategoriesCode
     *            code de la catégorie d'achat sélectionné dans la démarche par
     *            le demandeur
     * @param demandTitle
     *            titre de la demande saisie par le demandeur
     * @param objectDemand
     *            objet de la demande saisie par le demandeur
     * @param numTeledossier
     *            le numéro de télédossier généré
     * @param statutAvancement
     *            le statut d'avancement de la DA
     * @throws ApplicationException
     *             Si le login ou le login du demandeur sont vides ou null
     * @throws TechnicalException
     *             Si l'ajout échoue pour une raison technique
     * @return true/false selon que l'enregistrement a réussi ou pas
     * @author Meissa
     * @since 22-05-2017
     */
    boolean saveDA(String login, String ministeryCode, String spendingServiceCode, String programCode,
            Date creationDate, String requesterLogin, String purchasingCategoriesCode, String demandTitle,
            String objectDemand, String numTeledossier, String statutAvancement)
            throws ApplicationException, TechnicalException;

    /**
     * Ajoute une demande d'achat en base avec la pièce jointe associée.
     * 
     * @param login
     *            login du créateur de la DA
     * @param ministeryCode
     *            Code du ministère
     * @param spendingServiceCode
     *            Code du service dépensier
     * @param programCode
     *            code du programme
     * @param creationDate
     *            Date de création
     * @param requesterLogin
     *            login du demandeur
     * @param purchasingCategoriesCode
     *            code de la catégorie d'achat
     * @param demandTitle
     *            Titre de la demande
     * @param objectDemand
     *            objet de la demande saisie par le demandeur
     * @param uploadStringPJ
     *            Chaîne renvoyée par le getString SMG sur le champ upload
     * @param intitulePJ
     *            intitulé de la PJ
     * @param listPostesDAmap
     *            liste des postes de DA sous forme de map dont les clés sont :
     *            - NumeroPosteDA, - Type, - Categorie, - Designation, -
     *            Quantite, - Unite, - DateLivraisonSouhaite, - Commentaire, -
     *            UploadStringPJ, contient l'emplacement de la PJ - IntitulePJ
     *            nom de la PJ - ServiceBeneficiaire, - Programme, - Action, -
     *            Activite, - Fond, - Civilite, - Nom, - Rue, - Numero, -
     *            CodePostal, - Ville, - Region, - Telephone, - Portable, -
     *            Email
     * @param numTeledossier
     *            le numéro de télédossier généré
     * @param statutAvancement
     *            le statut d'avancement de la DA
     * @throws ApplicationException
     *             Si le login est vide ou null ou si la PJ n'est pas
     *             trouvée
     * @throws TechnicalException
     *             Si l'ajout échoue pour une raison technique
     * @return true/false selon que l'enregistrement a réussi ou pas
     * @author Meissa
     * @since 22-05-2017
     */
    boolean saveDAwithPJ(String login, String ministeryCode, String spendingServiceCode, String programCode,
            Date creationDate, String requesterLogin, String purchasingCategoriesCode, String demandTitle,
            String objectDemand, String uploadStringPJ, String intitulePJ, List<Map<String, Object>> listPostesDAmap,
            String numTeledossier, String statutAvancement) throws ApplicationException, TechnicalException;

    /**
     * Calcule le nb total de DA dont le service dépensier correspond aux
     * services dépensiers autorisés pour le role DDA de l'utilisateur connecté
     * et répondant aux critères donnés.
     * 
     * @param login
     *            login de la personne connectée
     * @param ministry
     *            Code du ministère (peut être vide ou null)
     * @param spendingService
     *            Code du service dépensier (peut être vide ou null)
     * @param requesterLogin
     *            Login du demandeur (peut être vide ou null)
     * @param purchasingCategory
     *            Code de la catégorie d'achat (peut être vide ou null)
     * @param dateFrom
     *            Date minimale de création de la DA (au format JJ/MM/AAAA)
     *            (peut être vide ou null)
     * @param dateTo
     *            Date maximale de création de la DA (au format JJ/MM/AAAA)
     *            (peut être vide ou null)
     * @param progessStatus
     *            Statut d'avancement de la DA (peut être vide ou null)
     * @param approvalStatus
     *            Statut d'approbation de la DA (peut être vide ou null)
     * @param numDA
     *            Numéro de DA (peut être vide ou null)
     * @return le nombre de résultats
     * @throws ApplicationException
     *             Si les dates de début et de fin ne sont pas au format attendu
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique
     * @author Mamadou Ndir
     * 
     */
    int countDANbResults(String login, String ministry, String spendingService, String requesterLogin,
            String purchasingCategory, String dateFrom, String dateTo, String progessStatus, String approvalStatus,
            String numDA) throws ApplicationException, TechnicalException;

    /**
     * Retourne les XX demandes d'achat les plus récentes dont le service
     * dépensier correspond aux services dépensiers autorisés pour le role DDA
     * de l'utilisateur connecté et répondant aux critères donnés.
     * 
     * Le résultat est sous forme d'une Map dont chaque entrée représente une
     * colonne du tableau résultat (clé = titre de la colonne, valeur = tableau
     * de valeurs de la colonne). Les clés suivantes sont disponibles : <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} : Numéro de
     * télédossier de la DA <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_CREATION} : Date de
     * création de la DA (au format JJ/MM/AAAA) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DEMANDEUR} : Demandeur (Prénom
     * Nom) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_MINISTERE} : Désignation du
     * ministère<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_SERVICE_DEPENSIER} : Service
     * dépensier de la DA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CATEGORIE_ACHAT} : Catégorie
     * d'achat (désignation)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_APPROBATION} : Statut
     * d'approbation de la DA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_AVANCEMENT} : Statut
     * d'avancement de la DA <BR>
     * 
     * @param login
     *            login de la personne connectée
     * @param ministry
     *            Code du ministère (peut être vide ou null)
     * @param spendingService
     *            Code du service dépensier (peut être vide ou null)
     * @param requesterLogin
     *            Login du demandeur (peut être vide ou null)
     * @param purchasingCategory
     *            Code de la catégorie d'achat (peut être vide ou null)
     * @param dateFrom
     *            Date minimale de création de la DA (au format JJ/MM/AAAA)
     *            (peut être vide ou null)
     * @param dateTo
     *            Date maximale de création de la DA (au format JJ/MM/AAAA)
     *            (peut être vide ou null)
     * @param progessStatus
     *            Statut d'avancement de la DA (peut être vide ou null)
     * @param approvalStatus
     *            Statut d'approbation de la DA (peut être vide ou null)
     * @param numDA
     *            Numéro de DA (peut être vide ou null)
     * @param nbMaxResults
     *            Nombre maximum de résultats rémontés (les plus récents par
     *            date de création)
     * @return les XX demandes d'achat les plus récentes répondant aux critères
     *         donnés
     * @throws ApplicationException
     *             Si les dates de début et de fin ne sont pas au format attendu
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique
     * @author Mamadou Ndir
     */
    Map<String, Object[]> searchDA(String login, String ministry, String spendingService, String requesterLogin,
            String purchasingCategory, String dateFrom, String dateTo, String progessStatus, String approvalStatus,
            String numDA, int nbMaxResults) throws ApplicationException, TechnicalException;

    /**
     * Retourne sous forme de map (key = nom du champ) les informations de la DA
     * : <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CREATEUR} : Valeur = créateur
     * (prénom nom) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_LOGIN_CREATEUR} : Valeur =
     * login du créateur <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_MODIFICATEUR} : Valeur =
     * modificateur (prénom nom) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_LOGIN_MODIFICATEUR} : Valeur =
     * login du modificateur <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_CREATION} : Valeur = date
     * de création (JJ/MM/AAAA) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_MODIFICATION} : Valeur =
     * date de modification (JJ/MM/AAAA) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DEMANDEUR} : Valeur = demandeur
     * (prénom nom) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_LOGIN} : Valeur = login du demandeur <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} : Valeur = statut
     * d'avancement de la DA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_APPROBATION} : Valeur =
     * statut d'approbation de la DA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_MINISTERE} : Valeur =
     * ministere (désignation)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_MINISTERE} : Valeur =
     * ministere (code)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_PROGRAMME} : Valeur =
     * programme (désignation)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_PROGRAMME} : Valeur =
     * programme (code)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_SERVICE_DEPENSIER} : Valeur =
     * service dépensier (désignation)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_SERVICE_DEPENSIER} : Valeur =
     * service dépensier (code)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CATEGORIE_ACHAT} : Valeur =
     * categorie d'achat (désignation)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_CATEGORIE_ACHAT} : Valeur =
     * categorie d'achat (code)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TITRE} : Valeur = titre<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_OBJET} : Valeur = objet<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_INTITULE} : Valeur =
     * intitulé de la pj de la DA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_PJ_PATH} : Valeur = chemin de
     * la pj de la DA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_UPLOAD_STRING_PJ} : Valeur =
     * string à affecter au champ upload affichant la PJ.<BR>
     * 
     * @param numDA
     *            : identifiant de la DA (numeroDossier)
     * @return : les informations de la DA sous forme de Map
     * @throws TechnicalException
     *             Si la lecture des informations de la DA échoue
     * @throws ApplicationException
     *             Si l'un des paramètres est vide ou null
     * @author Mamadou Ndir
     */
    Map<String, String> getDAInformation(String numDA) throws ApplicationException, TechnicalException;

    /**
     * Vérifie que la DA est modifiable par l'utilisateur.
     * 
     * @param login
     *            login de l'utilisateur
     * @param noDa
     *            NumeroDossier
     * @return true si la DA est modifiable par l'utilisateur <BR>
     *         (vérifie que la DA n'est pas en cours de modification et le
     *         statut de la DA)
     * @throws ApplicationException
     *             Si le login ou le no DA est vide ou null
     * @author Catherine Alardo / 12-06-2017
     */
    boolean isDAUpdatable(String login, String noDa) throws ApplicationException;

    /**
     * Verrouille une Da pour signaler qu'elle est en cours de modification.
     * 
     * @param login
     *            login de l'utilisateur qui verrouille
     * @param noDa
     *            numéro de dossier de la DA
     * @return true si la DA a pu être verrouillée, false sinon
     * @throws ApplicationException
     *             si l'un des paramètres est null ou vide
     */
    boolean lockDa(String login, String noDa) throws ApplicationException;

    /**
     * Déverrouille une Da pour signaler qu'elle n'est plus en cours de
     * modification.
     * 
     * @param noDa
     *            numéro de dossier de la DA à déverrouiller
     * @throws ApplicationException
     *             Si le paramètre est null ou vide
     */
    void unlockDa(String noDa) throws ApplicationException;

    /**
     * Permet de retourner les alertes qui concernent l'entete d'une DA.
     * 
     * @param idDA
     *            numero da. numero de la DA
     * @return Returne un message comprenant l'ensemble des champs impactés
     * @throws TechnicalException
     *             Probleme liés à l'execution de la requete
     * @throws ApplicationException
     *             Si idDA est vide ou null
     * @author Mamadou Ndir
     */
    String getDataImpact(String idDA) throws ApplicationException, TechnicalException;

    /**
     * Supprime une DA ainsi que toutes les enregistrements liés (PJ, PosteDA).
     * 
     * @param numDossierDA
     *            numero de la DA.
     * @param login
     *            login de l'user qui effectue la suppression
     * @return Returne true/false selon que la suppression est réussie ou pas
     * @author Meissa
     * @since 16/06/2017
     */
    boolean deleteDA(String numDossierDA, String login);

    /**
     * Duplique une DA ainsi que tous les enregistrements liés (PJ, PosteDA).
     * 
     * @param login
     *            login de l'utilisateur courant (qui sera le créateur de la DA
     *            dupliquée)
     * @param oldNumDossierDA
     *            numero de la DA à dupliquer.
     * @param newNumDossierDA
     *            Numéro de la DA dupliquée
     * @return un message indiquant les erreurs non bloquantes rencontrées
     *         (postes non repris) (vide si duplication OK)
     * @throws TechnicalException
     *             si la duplication échoue pour une raison technique
     * @throws ApplicationException
     *             si la duplication échoue car une donnée de l'entête n'existe
     *             plus et que la DA ne peut être reprise ou si l'un des
     *             paramètres est vide
     * @author Meissa
     * @since 20/06/2017
     */
    String duplicateDA(String login, String oldNumDossierDA, String newNumDossierDA)
            throws TechnicalException, ApplicationException;
    /* Alpha ajout */
    
    List<DemandeAchat> listNotDeletableDA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal);
	  List<DemandeAchat> listDemandeAchats() ;
	 
	  List<CommandeAchat> listCAfromDA(List<DemandeAchat> liste) ;
	  void addlistNotDeletableDA(List<DemandeAchat> liste) ;
	 
    List<DemandeAchat> listDeletableDA(List<DemandeAchat> da);
   
    List<PosteCommandeAchat> getPosteCommandeAchats(DemandeAchat da) ;
  
   /*** suppression de la liste des DA perimes ***/
    void delete(List<DemandeAchat> liste) ;
    
    List<Object[]> listeDAforPSdM(String codeProgramme, String codeServiceDepensier, String codeMinister,Date dateFrom, Date dateTo);
}
