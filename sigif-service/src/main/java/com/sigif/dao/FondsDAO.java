package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Fonds;

/**
 * Classe d'accès aux données des fonds.
 * 
 * @author Meissa Beye
 *
 */
public interface FondsDAO extends AbstractDAO<Fonds> {

	/**
	 * Retourne la liste des fonds au statut actif.
	 * 
	 * @return tous les fonds actifs
	 * @author Meissa Beye
	 * @since 02-06-2017
	 */
	List<Fonds> getAllFondsActifs();

	/**
	 * Retourne la liste des fonds au statut actif par type de fonds.
	 * 
	 * @param idTypeFonds id du type de fonds
	 * @return tous les fonds actifs du type
	 * @throws TechnicalException si la recherche échoue pour une raison technique
	 * @author Meissa Beye
	 * @since 02-06-2017
	 */
	List<Fonds> getFondsActifsByTypeFonds(int idTypeFonds) throws TechnicalException;
}
