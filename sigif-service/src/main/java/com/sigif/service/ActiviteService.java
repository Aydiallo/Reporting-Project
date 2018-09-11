package com.sigif.service;

import java.util.Map;

import com.sigif.dto.ActiviteDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux activités.
 *  
 *
 */
public interface ActiviteService extends AbstractService<ActiviteDTO> {

	/**
	 * Retourne la liste des activités au statut actif pour une action donnée.
	 * @param codeAction Code de l'action
	 * @author Meissa
	 * @return la liste des activités au statut actif pour une action donnée (code, désignation), retourne null si pas d'activité
	 * @throws ApplicationException si le code activité est vide ou null
     * @throws TechnicalException si la lecture échoue
	 */
	Map<String, String> getAllActivitiesByAction(String codeAction) throws ApplicationException, TechnicalException;

}
