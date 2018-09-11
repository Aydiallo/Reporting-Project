package com.sigif.service;

import java.util.Map;

import com.sigif.dto.TypeCommandeDTO;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux types de commande.
 *
 */
public interface TypeCommandeService extends AbstractService<TypeCommandeDTO> {

	/**
	 * Retourne la liste des types de commande au statut actif. 
	 * @author Meissa Beye
	 * @return la liste des type de commande au statut actif (code, désignation) triée par désignation
	 * @throws TechnicalException si l'accès BD échoue
	 */
	Map<String, String> getAllOrderTypes() throws TechnicalException;

	

}
