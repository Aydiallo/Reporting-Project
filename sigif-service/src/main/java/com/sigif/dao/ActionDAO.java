package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Action;

/**
 * Classe d'accès aux données des actions.
 * @author Meissa Beye
 * 
 */
public interface ActionDAO extends AbstractDAO<Action> {

	/**
	 * Retourne la liste des unités au statut actif.
	 * @param codeProgram code du programme
	 * @return toutes les unités actives
	 * @throws TechnicalException Si la connexion à la BD échoue
	 */
	List<Action> getActionsByProgramme(String codeProgram) throws TechnicalException;
}
