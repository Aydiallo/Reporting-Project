package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Activite;

/**
 * Classe d'accès aux données des activités.
 * @author Meissa Beye
 * 
 */
public interface ActiviteDAO extends AbstractDAO<Activite> {

	/**
	 * Retourne la liste des activités au statut actif en fonction de l'action.
	 * @param codeAction code de l'action	
	 * @return toutes les activités actives
	 * @throws TechnicalException  Si la connexion à la BD échoue
	 * @author Meissa
	 * @since 01-06-2017
	 */
	List<Activite> getActiviteActifsByAction(String codeAction) throws TechnicalException;
}
