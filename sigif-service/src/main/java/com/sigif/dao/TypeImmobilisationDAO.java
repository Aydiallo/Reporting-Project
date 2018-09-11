package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeImmobilisation;

/**
 * Classe d'accès aux données des types d'immobilisation
 * 
 * @author Meissa Beye
 *
 */
public interface TypeImmobilisationDAO extends AbstractDAO<TypeImmobilisation> {

	/**
	 * Retourne la liste des types d'immobilisations au statut actif.	
	 * @return tous les types d'immobilisations actifs
	 * @throws TechnicalException Si l'accès aux données de la BD a échoué
	 * @author Meissa Beye
	 * @since 14-06-2017
	 */
	List<TypeImmobilisation> getAllTypeImmobilisationActifs() throws TechnicalException;
	
}
