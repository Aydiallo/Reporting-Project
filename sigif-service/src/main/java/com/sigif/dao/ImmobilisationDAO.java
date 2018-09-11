package com.sigif.dao;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Immobilisation;

/**
 * Classe d'accès aux données des immobilisations.
 * 
 * @author Catherine Alardo
 *
 */
public interface ImmobilisationDAO extends AbstractDAO<Immobilisation> {

	/**
	 * Retourne une immobilisation pour reference et un statut donnés.
	 * 
	 * @param refImmo la référence de l'immobilisation
	 * @param statut le statut de l'immobilisation
	 * @author Meissa Beye
	 * @since 15/06/2017
	 * @return Une immobilisation, ou null si pas d'immobilisation trouvée
	 * @throws TechnicalException si la recherche échoue pour une raison technique
	 */
	Immobilisation getImmobilisationByRef(String refImmo, String statut) throws TechnicalException;

}
