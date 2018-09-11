package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Departement;
import com.sigif.modele.Ville;

/**
 * Classe d'accès aux données des villes.
 * 
 * @author Meissa Beye
 *
 */
public interface VilleDAO extends AbstractDAO<Ville> {

	/**
	 * Retourne la liste des villes au statut actif.
	 * 
	 * @return Toutes les villes actives
	 * @throws TechnicalException Si la lecture des données en base échoue
	 * @author Meissa Beye
	 * @since 02-06-2017
	 */
	List<Ville> getAllVilleActifs() throws TechnicalException;

	/**
	 * Retourne la liste des villes d'une région donnée au statut actif.
	 * 
	 * @param codeRegion Code de la région
	 * @return Toutes les villes actives de la région
	 * @throws TechnicalException Si la lecture des données en base échoue
	 * @author Meissa Beye
	 * @since 02-06-2017
	 */
	List<Ville> getVilleActifsByDepartement(String codeDepartement) throws TechnicalException;

}
