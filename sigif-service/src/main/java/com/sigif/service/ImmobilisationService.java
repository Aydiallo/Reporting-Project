package com.sigif.service;

import java.util.Map;

import com.sigif.dto.ImmobilisationDTO;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux immobilisations.
 * 
 * @author Catherine Alardo
 *
 */
public interface ImmobilisationService extends AbstractService<ImmobilisationDTO> {

	/**
	 * Retourne la map des données d'une immobilisation pour une reference et un statut
	 * donnés (null si aucune immobilisation ne correspond) : 
	 * - {@link com.sigif.util.Constantes#KEY_MAP_ID} - Id <br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE} - code<br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} - désignation<br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION_CATEGORIEIMMO} - Designation de la catégorie d'immobilisation liée
	 * <br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_CATEGORIEIMMO}  - Code de la catégorie d'immobilisation liée <br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION_UNITE}  - Designation de l'unité liée <br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_UNITE}  - Code de l'unité d'immobilisation liée <br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION_TYPEIMMO}  - Designation du type d'immobilisation liée <br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_TYPEIMMO}  - Code du type d'immobilisation liée. <br>
	 * 
	 * @param refImmo référence de l'immobilisation
	 * @param statut statut de l'immobilisation
	 * @author Catherine Alardo
	 * @return la map des données d'une immobilisation, retourne null si pas
	 *         d'immobilisation trouvée
	 * @throws TechnicalException si la recherche échoue pour une raison technique
	 */
	Map<String, String> getImmobilisationByRef(String refImmo, String statut) throws TechnicalException;

}
