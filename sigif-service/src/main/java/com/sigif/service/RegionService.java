package com.sigif.service;

import java.util.Map;

import com.sigif.dto.RegionDTO;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux régions.
 * 
 * @author Meissa Beye
 * 
 */
public interface RegionService extends AbstractService<RegionDTO> {

	/**
	 * Retourne la liste des régions au statut actif.
	 * 
	 * @author Meissa Beye
	 * @return la liste des régions au statut actif (code, désignation)
	 * @throws TechnicalException si l'accès BD échoue
	 */
	Map<String, String> getAllAreas() throws TechnicalException;

}
