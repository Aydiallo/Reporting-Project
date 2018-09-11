package com.sigif.dao;

import java.util.Date;
import java.util.List;

import com.sigif.enumeration.StatutApprobationDA;
import com.sigif.enumeration.StatutAvancementDA;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PosteCommandeAchat;

/**
 * Classe d'accès aux données des demandes d'achat.
 * @author Malick Diagne
 */
public interface DemandeAchatDAO extends AbstractDAO<DemandeAchat> {

	// Les méthodes crud de base sont déja implementées dans AbstractDAO
	// mettre ici des méthodes spécifiques relatives à la DA(non gérer dans
	// l'abstract)
	/**
	 * Calcule le nb total de DA répondant aux critères donnés.
	 * 
	 * @param login
	 *            login de l'utilisateur connecté (N'est jamais null).
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
	 * @throws TechnicalException
	 *             Si la recherche échoue pour une raison technique
	 * @author Mamadou Ndir
	 */
	int countDANbResults(String login, String ministry, String spendingService, String requesterLogin,
			String purchasingCategory, Date dateFrom, Date dateTo, StatutAvancementDA progessStatus,
			StatutApprobationDA approvalStatus, String numDA) throws TechnicalException;

	/**
	 * Retourne les XX demandes d'achat les plus récentes répondant aux critères
	 * donnés.
	 *  
	 * @param login
	 *            login de l'utilisateur connecté (N'est jamais null).
	 * @param ministry
	 *            Code du ministère (peut être null)
	 * @param spendingService
	 *            Code du service dépensier (peut être vide ou null)
	 * @param requesterLogin
	 *            Login du demandeur (peut être null)
	 * @param purchasingCategory
	 *            Code de la catégorie d'achat (peut être null)
	 * @param dateFrom
	 *            Date minimale de création de la DA (au format JJ/MM/AAAA)
	 *            (peut être vide ou null)
	 * @param dateTo
	 *            Date maximale de création de la DA (au format JJ/MM/AAAA)
	 *            (peut être vide ou null)
	 * @param progessStatus
	 *            Statut d'avancement de la DA (peut être null)
	 * @param approvalStatus
	 *            Statut d'approbation de la DA (peut être null)
	 * @param numDA
	 *            Numéro de DA (peut être vide ou null)
	 * @param nbMaxResults
	 *            Nombre maximum de résultats rémontés (les plus récents par
	 *            date de création)
	 * @return les XX demandes d'achat les plus récentes répondant aux critères
	 *         donnés
	 */
	List<DemandeAchat> searchDA(String login, String ministry, String spendingService, String requesterLogin,
			String purchasingCategory, Date dateFrom, Date dateTo, StatutAvancementDA progessStatus,
			StatutApprobationDA approvalStatus, String numDA, int nbMaxResults);

	/**
	 * Retourne la DA indiquée par le numéro de dossier.
	 * 
	 * @param idDA
	 *            : identifiant de la DA (numeroDossier)
	 * @return : la DA indiquée par le numéro de dossier
	 * @throws TechnicalException
	 *             Si la lecture des informations de la DA échoue
	 */
	DemandeAchat getDAInformation(String idDA) throws TechnicalException;
	
    /**
     * Déverrouille une DA.
     * @param da la DA à déverrouiller
     */
    void unlockDaByDa(DemandeAchat da);
    
    /* Alpha ajout */
    
     List<DemandeAchat> listNotDeletableDA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal);
	  List<DemandeAchat> listDemandeAchats() ;
	 
	  List<CommandeAchat> listCAfromDA(List<DemandeAchat> liste) ;
	  void addlistNotDeletableDA(List<DemandeAchat> liste) ;
	 
     List<DemandeAchat> listDeletableDA(List<DemandeAchat> da);
    
     List<PosteCommandeAchat> getPosteCommandeAchats(DemandeAchat da) ;
   
    /*** suppression de la liste des DA perimes ***/
     void delete(List<DemandeAchat> liste) ;
     
     /**
      * 
      * 
      * 
      */
     List<Object[]> listeDAforPSdM(String codeProgramme, String codeServiceDepensier, String codeMinister,Date dateFrom, Date dateTo);
}
