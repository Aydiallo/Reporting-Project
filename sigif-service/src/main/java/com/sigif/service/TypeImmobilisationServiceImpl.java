package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.TypeImmobilisationDAO;
import com.sigif.dto.TypeImmobilisationDTO;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeImmobilisation;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux types d'immobilisation.
 * 
 * @author Meissa Beye
 *
 */
@Service("typeImmobilisationService")
@Transactional
public class TypeImmobilisationServiceImpl extends AbstractServiceImpl<TypeImmobilisation, TypeImmobilisationDTO>
		implements TypeImmobilisationService {

	/**
	 * DAO pour TypeImmobilisation.
	 */
	@Autowired
	private TypeImmobilisationDAO typeImmoDAO;

	@Override
	public Map<String, String> getAllActifTypeImmobilisation() throws TechnicalException {
		List<TypeImmobilisation> typeImmos = typeImmoDAO.getAllTypeImmobilisationActifs();
		Map<String, String> resultat = new HashMap<String, String>();
		if (typeImmos != null) {
			for (TypeImmobilisation typeImmo : typeImmos) {
				resultat.put(typeImmo.getCode(), typeImmo.getDesignation());
			}
		}
		return FunctionsUtils.sortByValue(resultat);
	}
}
