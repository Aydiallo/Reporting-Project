package com.sigif.dao;

import java.util.Date;
import java.util.List;

import com.sigif.enumeration.StatutReceptionCA;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PosteCommandeAchat;

/**
 * Classe d'accès aux données des commandes d'achat.
 * 
 * @author Malick Diagne
 *
 */
public  interface CommandeAchatDAO extends AbstractDAO<CommandeAchat> {

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
     *            : numero de la CA (peut être null)
     * @param orderType
     *            : code du type de commande (peut être null)
     * @param purchasingCategory
     *            : code de la catégorie d'achat (peut être null)
     * @param status
     *            : statut de la commande (peut être null)
     * @param dateFrom
     *            : date de création minimale (peut être null)
     * @param dateTo
     *            : date de création maximale (peut être null)
     * @return le nombre de résultats
     * @throws TechnicalException
     *             Si la recherche échoue
     * @author Malick Diagne
     */
    int searchCANbResults(String spendingService, String noCA, String orderType, String purchasingCategory,
            StatutReceptionCA status, Date dateFrom, Date dateTo) throws TechnicalException;

    /**
     * Recherche les nbMaxResults plus récentes commandes d'achat
     * réceptionnables (statut « Non réceptionnée » ou « Partiellement
     * réceptionnée ») et dont au moins un poste réceptionnable (statut « Non
     * réceptionné » ou « Partiellement réceptionné ») est relatif au service
     * dépensier sélectionné par le Réceptionnaire et répondant aux critères
     * sélectionnés.
     * 
     * @param spendingService
     *            : code du service dépensier
     * @param noCA
     *            : numero de la CA (peut être null)
     * @param orderType
     *            : code du type de commande (peut être null)
     * @param purchasingCategory
     *            : code de la catégorie d'achat (peut être null)
     * @param status
     *            : statut de la commande (peut être null)
     * @param dateFrom
     *            : date de création minimale (peut être null)
     * @param dateTo
     *            : date de création maximale (peut être null)
     * @param nbMaxResults
     *            : nombre max de résultats remontés
     * @return Les nbMaxResults dernieres commandes d'achat correspondant aux
     *         critères
     * 
     * @throws TechnicalException
     *             Si la recherche échoue
     * @author Malick Diagne
     */
    List<CommandeAchat> searchCA(String spendingService, String noCA, String orderType, String purchasingCategory,
            StatutReceptionCA status, Date dateFrom, Date dateTo, int nbMaxResults) throws TechnicalException;

    /**
     * Retourne la commande d'achat correspondant à un idSap.
     * 
     * @param idSapCA
     *            : identifiant de la CA (idSAP)
     * @return : les informations de la CA sous forme de Map
     * @throws TechnicalException
     *             si la recherche échoue
     * @author Mamadou Ndir
     */
    CommandeAchat getCAInformation(String idSapCA) throws TechnicalException;

    /**
     * Retourne une liste ordonnée des CA liées à une DA ou à un posteDA.
     * 
     * @param numDossierDA
     *            le numéro de dossier de la DA
     * @param noPosteDA
     *            le numéro de poste de la DA
     * @param orderBy
     *            parametre de trie
     * @return liste de CommandeAchat
     * @throws TechnicalException
     *             si la recherche échoue
     * @author Meissa Beye
     * @since 08/06/2017
     */
    List<CommandeAchat> getAllCommandeAchatOfDA(String numDossierDA, String noPosteDA, String orderBy)
            throws TechnicalException;
    
    /**
     * Calcule et affecte le statut de la CA en fonction des statuts de ses postes.
     * @param ca la commande d'achat
     * @return le statut calculé après affectation
     * @throws TechnicalException si l'accès BD échoue
     */
    StatutReceptionCA calculateCaStatus(CommandeAchat ca) throws TechnicalException; 
    
    //* Alpha ajout *//
     List<CommandeAchat> listNotDeletableCA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal) ;
	 List<CommandeAchat> listCommandeAchats() ;
	
	 void addlistNotDeletableCA(List<CommandeAchat> liste) ;
	 List<DemandeAchat> listDAfromCA(List<CommandeAchat> liste) ;
	
	 List<CommandeAchat> listDeletableCA(List<CommandeAchat> listNotDeletableCA) ;
	
	 List<PosteCommandeAchat> getPosteCommandeAchats(CommandeAchat da) ;
	
	 void delete(List<CommandeAchat> liste) ;
}
