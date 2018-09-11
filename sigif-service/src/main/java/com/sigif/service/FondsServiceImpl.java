package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.FondsDAO;
import com.sigif.dto.FondsDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Fonds;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux fonds.
 * 
 * @author Meissa Beye
 * @since 02/06/2017
 */
@Service("fondsService")
@Transactional
public class FondsServiceImpl extends AbstractServiceImpl<Fonds, FondsDTO> implements FondsService {
    /** Loggueur. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FondsServiceImpl.class);
    
	/**
	 * DAO pour Fonds.
	 */
	@Autowired
	private FondsDAO fondsDAO;

	@Override
	public Map<String, String> getAllFundsByType(String idFundType) throws ApplicationException, TechnicalException {
	    idFundType = FunctionsUtils.checkNotNullNotEmptyAndTrim("idFundType", idFundType, LOGGER);
	    int idType = 0;
	    final int radixBase10 = 10;
	    try {
	        idType = Integer.parseInt(idFundType, radixBase10);
	    } catch (NumberFormatException nfe) {
	        throw new ApplicationException("Le paramètre passé à getAllFundsByType (" + idFundType + ") n'est pas un entier.");
	    }
		List<Fonds> fonds = fondsDAO.getFondsActifsByTypeFonds(idType);
		Map<String, String> resultat = new HashMap<String, String>();
		if (fonds != null) {
			for (Fonds fond : fonds) {
				resultat.put(fond.getCode(), fond.getDesignation());
			}
			resultat = FunctionsUtils.sortByValue(resultat);
		}
		return resultat;
	}

}
