package com.sigif.service;

import java.util.List;
import java.util.Map;

import com.sigif.dto.CommandeAchatDTO;
import com.sigif.enumeration.StatutReceptionCA;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PosteCommandeAchat;

/**
 * Service d'accès aux Commandes d'achat.
 * 
 * @author Mickael Beaupoil
 *
 */
public interface CommandeAchatService extends AbstractService<CommandeAchatDTO> {

    /**
     * Calcule le nb total de commandes réceptionnables (statut « Non
     * réceptionnée » ou « Partiellement réceptionnée ») et dont au moins un
     * poste réceptionnable (statut « Non réceptionné » ou « Partiellement
     * réceptionné ») est relatif au service dépensier sélectionné par le
     * Réceptionnaire et répondant aux critères sélectionnés.
     * 
     * @param spendingService
     *            : code du service dépensier
     * @param noCA
     *            : numero de la CA (peut être vide)
     * @param orderType
     *            : code du type de commande (peut être vide)
     * @param purchasingCategory
     *            : code de la catégorie d'achat (peut être vide)
     * @param status
     *            : statut de la commande (peut être null)
     * @param dateFrom
     *            : date de création au format JJ/MM/AAAA (peut être vide)
     * @param dateTo
     *            : date de création au format JJ/MM/AAAA (peut être vide)
     * @return le nombre de résultats
     * @author malick
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique
     * @throws ApplicationException
     *             Si le service dépensier n'est pas renseigné
     * @since 23-05-2017
     */

    int searchCANbResults(String spendingService, String noCA, String orderType, String purchasingCategory,
            String status, String dateFrom, String dateTo) throws TechnicalException, ApplicationException;

    /**
     * Recherche les XX plus récentes commandes d'achat réceptionnables (statut
     * « Non réceptionnée » ou « Partiellement réceptionnée ») et dont au moins
     * un poste réceptionnable (statut « Non réceptionné » ou « Partiellement
     * réceptionné ») est relatif au service dépensier sélectionné par le
     * Réceptionnaire et répondant aux critères sélectionnés.
     * 
     * @param spendingService
     *            : code du service dépensier
     * @param noCA
     *            : numero de la CA (peut être null ou vide)
     * @param orderType
     *            : code du type de commande (peut être null ou vide)
     * @param purchasingCategory
     *            : code de la catégorie d'achat (peut être null ou vide)
     * @param status
     *            : statut de la commande (peut être null ou vide)
     * @param dateFrom
     *            : date de création au format JJ/MM/AAAA (peut être null ou
     *            vide)
     * @param dateTo
     *            : date de création au format JJ/MM/AAAA (peut être null ou
     *            vide)
     * @param nbMaxResults
     *            : nombre maximum de commandes d'achat remontées
     * @return Une map de tableaux, dont les clés sont les suivantes : <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_ID} : Valeur = id de
     *         CA <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} : Valeur =
     *         numéro de CA <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_COMMANDE} :
     *         Valeur = type de commande (désignation)<BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_COMMENTAIRE} : Valeur
     *         = commentaire de la commande <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_CATEGORIE_ACHAT} :
     *         Valeur = catégorie d'achat (désignation)<BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_DATE_CREATION} :
     *         Valeur = catégorie d'achat (désignation)<BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_NB_CSF} : Valeur =
     *         nombre de CSF liées <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} : Valeur =
     *         statut de la CA <BR>
     * @author malick
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique
     * @throws ApplicationException
     *             Si le service dépensier n'est pas renseigné
     */
    Map<String, Object[]> searchCA(String spendingService, String noCA, String orderType, String purchasingCategory,
            String status, String dateFrom, String dateTo, int nbMaxResults)
            throws TechnicalException, ApplicationException;

    /**
     * Retourne sous forme de map (key = nom du champ) les informations de la CA
     * : <BR>
     * {@link com.sigif.util.Constantes#KEY_MAP_TYPE_COMMANDE} : Valeur = type
     * de commande (désignation) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CATEGORIE_ACHAT} : Valeur =
     * categorie d'achat (désignation)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_CREATION} : Valeur = date
     * de création (JJ/MM/AAAA) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_COMMENTAIRE} : Valeur =
     * commentaire<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} : Valeur = statut de
     * la CA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_DEVISE} : Valeur = code
     * de la devise <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DEVISE} : Valeur = désignation
     * de la devise. <BR>
     * 
     * @param idCA
     *            : identifiant de la CA (idSAP)
     * @return : les informations de la CA sous forme de Map
     * @throws TechnicalException
     *             Si la lecture des informations de la CA échoue
     * @throws ApplicationException
     *             si le paramètre idCA est vide ou null
     */
    Map<String, String> getCAInformation(String idCA) throws TechnicalException, ApplicationException;

    /**
     * Retourne toutes les CA d'une DA ou d'un poste DA. La map de retour
     * contient des listes ordonnées par id SAP de CA. Les clés sont les
     * suivantes : <BR>
     * {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} : Valeur = id SAP<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESCRIPTION} : Valeur =
     * description<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_CREATION} : Valeur = date
     * de création SAP (JJ/MM/AAAA) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} : Valeur = statut.<BR>
     * 
     * @param numDossierDA
     *            numéro de la demande d'achat (numeroDossier)
     * @param noPosteDA
     *            numéro du poste de la demande d'achat (peut être vide ou null)
     * @return un MAP contenant les infos des CA de la DA
     * 
     * @throws ApplicationException
     *             Si le numéro de la demande d'achat est vide ou null
     * @throws TechnicalException
     *             Si l'accès aux données de la BD a échoué
     * @author Meissa
     * @since 08/06/2017
     */
    Map<String, Object[]> getAllCAOfDA(String numDossierDA, String noPosteDA)
            throws ApplicationException, TechnicalException;

    /**
     * Retourne la MAP d'infos de la CA liée à une CSF donnée. Les infos
     * remontées sont : - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_CA} :
     * Valeur = Numéro de CA (IdSAP) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_COMMANDE} : Valeur = type
     * de commande (désignation)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CATEGORIE_ACHAT} : Valeur =
     * categorie d'achat (désignation)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_CREATION} : Valeur = date
     * de création (JJ/MM/AAAA)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} : Valeur = statut de
     * la CA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESCRIPTION} : Valeur =
     * description de la CA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_DEVISE} : Valeur = code
     * de la devise <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DEVISE} : Valeur = désignation
     * de la devise. <BR>
     * 
     * @param numDossierCSF
     *            le numéro de dossier de la CSF
     * @return une map d'info de la CommandeAchat(NUMERO_CA, TYPE_COMMANDE,
     *         CATEGORIE_ACHAT, DATE_CREATION, STATUT, DESCRIPTION, DEVISE)
     * @throws TechnicalException
     *             si la recherche échoue
     * @throws ApplicationException
     *             si le numéro de dossier est vide
     * @author Meissa
     * @since 21/06/2017
     */
    Map<String, Object[]> getCAInfoOfCSF(String numDossierCSF) throws TechnicalException, ApplicationException;
    
    /**
     * Calcule et affecte le statut de la CA en fonction des statuts de ses postes.
     * @param ca la commande d'achat
     * @return le statut calculé après affectation
     * @throws TechnicalException si l'accès BD échoue
     */
    StatutReceptionCA calculateCaStatus(CommandeAchat ca) throws TechnicalException;  
    /*alpha ajout*/
  //* Alpha ajout *//
    List<CommandeAchat> listNotDeletableCA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal) ;
	 List<CommandeAchat> listCommandeAchats() ;
	
	 void addlistNotDeletableCA(List<CommandeAchat> liste) ;
	 List<DemandeAchat> listDAfromCA(List<CommandeAchat> liste) ;
	
	 List<CommandeAchat> listDeletableCA(List<CommandeAchat> listNotDeletableCA) ;
	
	 List<PosteCommandeAchat> getPosteCommandeAchats(CommandeAchat da) ;
	
	 void delete(List<CommandeAchat> liste) ;
}
