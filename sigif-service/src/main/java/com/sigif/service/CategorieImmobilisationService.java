package com.sigif.service;

import java.util.Map;

import com.sigif.dto.CategorieImmobilisationDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux categories d'immobilisations.
 * 

 */
public interface CategorieImmobilisationService extends AbstractService<CategorieImmobilisationDTO> {

	/**
	 * Retourne la liste des categorieImmos au statut actif pour un
	 * typeImmobilisation donné.
	 * 
	 * @author Meissa
	 * @param typeImmoCode Code du type d'immobilisation
	 * @return la liste des categorieImmos pour un typeImmobilisation donné
	 *         (code, désignation)
	 * @throws ApplicationException si le code du type d'immo est vide ou null
	 * @throws TechnicalException si la recherche échoue pour une raison technique
	 */
	Map<String, String> getCategorieImmoByTypeImmo(String typeImmoCode) throws ApplicationException, TechnicalException;
}
