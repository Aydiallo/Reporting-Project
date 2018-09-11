package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.CategorieImmobilisationDAO;
import com.sigif.dto.CategorieImmobilisationDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CategorieImmobilisation;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux categories d'immobilisations.
 * 
 * @author Meissa Beye
 * @since 14/06/2017
 */
@Service("categorieImmobilisationService")
@Transactional
public class CategorieImmobilisationServiceImpl
		extends AbstractServiceImpl<CategorieImmobilisation, CategorieImmobilisationDTO>
		implements CategorieImmobilisationService {
    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategorieImmobilisationServiceImpl.class);
    
	/**
	 * DAO pour CategorieImmobilisation.
	 */
	@Autowired
	private CategorieImmobilisationDAO categorieImmoDAO;

	@Override
	public Map<String, String> getCategorieImmoByTypeImmo(String typeImmoCode)
			throws ApplicationException, TechnicalException {
	    typeImmoCode = FunctionsUtils.checkNotNullNotEmptyAndTrim("typeImmoCode", typeImmoCode, LOGGER);
		List<CategorieImmobilisation> categorieImmos = categorieImmoDAO.getCategorieImmoByTypeImmo(typeImmoCode);
		Map<String, String> resultat = new HashMap<String, String>();
		if (categorieImmos != null) {
			for (CategorieImmobilisation categorieImmo : categorieImmos) {
				resultat.put(categorieImmo.getCode(), categorieImmo.getDesignation());
			}
		}
		return FunctionsUtils.sortByValue(resultat);
	}

}
