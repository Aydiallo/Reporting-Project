package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.GroupeDeMarchandises;

/**
 * Classe d'accès aux données des groupes de marchandises.
 * 
 * @author Meissa Beye
 *
 */
public interface GroupeDeMarchandisesDAO extends AbstractDAO<GroupeDeMarchandises> {

	/**
	 * Retourne la liste des groupes de marchandises au statut actif pour un type d'achat.
	 * Si le type est F, les groupes sans articles sont remontés, si le type est S, ce sont les groupes avec articles.	
	 * @param codeTypeAchat (F/S)
	 * @return liste les groupes de marchandises en fonction du type d'achat
	 * @throws TechnicalException Si la recherche échoue pour une raison technique
	 * @author Meissa Beye
	 * @since 31-05-2017
	 */
	List<GroupeDeMarchandises> getGroupeDeMarchandisesByTypeAchat(String codeTypeAchat) throws TechnicalException;

	/**
	 * Retourne la liste des groupes de marchandises au statut actif.	
	 * @return tous les groupes de marchandises
	 * @throws TechnicalException Si la recherche échoue pour une raison technique
	 * @author Meissa Beye
	 * @since 31-05-2017
	 */
	List<GroupeDeMarchandises> getAllGroupeDeMarchandisesActifs() throws TechnicalException;
	
}
