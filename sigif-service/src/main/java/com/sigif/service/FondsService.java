package com.sigif.service;

import java.util.Map;

import com.sigif.dto.FondsDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux fonds.
 * 
 * @author Meissa Beye
 *
 */
public interface FondsService extends AbstractService<FondsDTO> {

	/**
	 * Retourne la liste des fonds pour un type de fonds donné au statut actif.
	 * 
	 * @author Meissa
	 * @param idFundType Id du type de fonds
	 * @return la liste des fonds pour un type de fonds donné au statut actif
	 *         (code, désignation), retourne null si pas de fonds
	 * @throws ApplicationException si l'id du type de fonds est null, vide ou n'est pas un entier
	 * @throws TechnicalException si la recherche échoue pour une raison technique
	 */
	Map<String, String> getAllFundsByType(String idFundType) throws ApplicationException, TechnicalException;

}
