package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Departement;
import com.sigif.modele.Ville;

/**
 * Classe d'accès aux données des departements.
 * 
 * @author Meissa Beye
 *
 */
public interface DepartementDAO extends AbstractDAO<Departement> {

	/**
	 * Retourne la liste des departements au statut actif.
	 * 
	 * @return Toutes les departements actives
	 * @throws TechnicalException Si la lecture des données en base échoue
	 * @author Meissa Beye
	 * @since 02-06-2017
	 */
	List<Departement> getAllDepartementActifs() throws TechnicalException;

	/**
	 * Retourne la liste des departements d'une région donnée au statut actif.
	 * 
	 * @param codeRegion Code de la région
	 * @return Toutes les departements actives de la région
	 * @throws TechnicalException Si la lecture des données en base échoue
	 * @author Meissa Beye
	 * @since 02-06-2017
	 */
	List<Departement> getDepartementActifsByRegion(String codeRegion) throws TechnicalException;

}
