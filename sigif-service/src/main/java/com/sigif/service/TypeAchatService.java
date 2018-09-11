package com.sigif.service;

import java.util.Map;

import com.sigif.dto.TypeAchatDTO;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux types d'achats.
 * 
 * @author Meissa Beye
 *
 */
public interface TypeAchatService extends AbstractService<TypeAchatDTO> {

	/**
	 * Retourne la liste des types de fonds au statut actif.
	 * 
	 * @author Meissa Beye
	 * @return la liste des types achat au statut actif (code, désignation) triée par désignation
	 * @throws TechnicalException si l'accès BD échoue
	 */
	Map<String, String> getAllTypeAchat() throws TechnicalException;

}
