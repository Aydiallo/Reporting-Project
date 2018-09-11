package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Unite;

/**
 * Classe d'accès aux données des unités.
 * 
 * @author Meissa Beye
 *
 */
public interface UniteDAO extends AbstractDAO<Unite> {

	
	/**
	 * Retourne la liste des unités au statut actif.	
	 * @return toutes les unités actives
	 * @throws TechnicalException Si l'accès aux données de la BD a échoué
	 * @author Meissa Beye
	 * @since 01-06-2017
	 */
	List<Unite> getAllUniteActifs() throws TechnicalException;
	
}
