package com.sigif.dao;

import java.util.List;

import com.sigif.modele.PosteDemandeAchat;

/**
 * Classe d'accès aux données des PosteDemandeAchats.
 * 
 * @author Meissa Beye
 *
 */
public interface PosteDemandeAchatDAO extends AbstractDAO<PosteDemandeAchat> {
	/**
	 * Retourne le poste correspondant au numéro de dossier et au numéro de poste.
	 * 
	 * @param numDa
	 *            : identifiant de la DA (numéroDossier)
	 * @param noPoste
	 *            : numéro du poste la DA
	 * @return : le poste DA
	 * @author Mamadou Ndir
	 */
	PosteDemandeAchat getPosteDAInformation(String numDa, String noPoste);

	/**
     * Récupère la liste des postes pour une demande d'achat donnée.
     * 
     * @param numDA
     *            numéro de la demande d'achat (numeroDA)
     * @return Les postes de la DA 
	 * @author Mamadou Ndir
	 */
	List<PosteDemandeAchat> getItemsByDA(String numDA);

}
