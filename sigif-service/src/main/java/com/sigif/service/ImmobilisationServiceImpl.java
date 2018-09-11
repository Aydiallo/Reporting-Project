package com.sigif.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.ImmobilisationDAO;
import com.sigif.dto.ImmobilisationDTO;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CategorieImmobilisation;
import com.sigif.modele.Immobilisation;
import com.sigif.modele.TypeImmobilisation;
import com.sigif.modele.Unite;
import com.sigif.util.Constantes;

/**
 * Implémentation de la classe d'accès aux immobilisations.
 * 
 * @author Catherine Alardo
 * @since 16/06/2017
 */
@Service("immobilisationService")
@Transactional
public class ImmobilisationServiceImpl extends AbstractServiceImpl<Immobilisation, ImmobilisationDTO>
		implements ImmobilisationService {

	/**
	 * DAO pour Immobilisation.
	 */
	@Autowired
	private ImmobilisationDAO immobilisationDAO;

	@Override
	public Map<String, String> getImmobilisationByRef(String refImmo, String statut) throws TechnicalException {

		Map<String, String> resultat = new HashMap<String, String>();
		Immobilisation immo = immobilisationDAO.getImmobilisationByRef(refImmo, statut);
		if (immo != null) {
			// Immobilisation
			resultat.put(Constantes.KEY_MAP_ID, String.valueOf(immo.getId()));
			resultat.put(Constantes.KEY_MAP_CODE, immo.getCode());
			resultat.put(Constantes.KEY_MAP_DESIGNATION, immo.getDesignation());

			// CategorieImmobilisation
			CategorieImmobilisation catImmo = immo.getCategorieImmobilisation();
			resultat.put(Constantes.KEY_MAP_DESIGNATION_CATEGORIEIMMO, catImmo.getDesignation());
			resultat.put(Constantes.KEY_MAP_CODE_CATEGORIEIMMO, catImmo.getCode());

			// TypeImmobilisation
			TypeImmobilisation typeImmo = immo.getCategorieImmobilisation().getTypeImmobilisation();
			resultat.put(Constantes.KEY_MAP_DESIGNATION_TYPEIMMO, typeImmo.getDesignation());
			resultat.put(Constantes.KEY_MAP_CODE_TYPEIMMO, typeImmo.getCode());

			// Unité
			Unite unite = immo.getUnite();
			resultat.put(Constantes.KEY_MAP_DESIGNATION_UNITE, unite.getNom());
			resultat.put(Constantes.KEY_MAP_CODE_UNITE, unite.getCode());

		} else {
			resultat = null;
		}
		return resultat;
	}

}
