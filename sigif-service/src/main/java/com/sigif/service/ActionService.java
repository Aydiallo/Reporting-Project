package com.sigif.service;

import java.util.Map;

import com.sigif.dto.ActionDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux actions.
 * @author Meissa Beye
 *
 */
public interface ActionService extends AbstractService<ActionDTO> {

	/**
	 * Retourne la liste des actions au statut actif pour un programme donné. 
	 * @author Meissa
	 * @param codeProgram code du programme recherché
	 * @return la liste des actions au statut actif pour un programme donné (code, désignation), retourne null si pas d'action
	 * @throws ApplicationException si le codeProgram est vide ou null
	 * @throws TechnicalException si la lecture en base échoue
	 */
	Map<String, String> getAllActionsByProgram(String codeProgram) throws ApplicationException, TechnicalException;

}
