package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Region;

/**
 * Classe d'accès aux données des régions.
 * 
 * @author Meissa Beye
 *
 */
public interface RegionDAO extends AbstractDAO<Region> {

	
	/**
	 * Retourne la liste des régions au statut actif.	
	 * @return Toutes les régions actives
	 * @throws TechnicalException Si l'accès aux données de la BD a échoué
	 * @author Meissa
	 * @since 02-06-2017
	 */
	List<Region> getAllRegionActifs() throws TechnicalException;
	
}
