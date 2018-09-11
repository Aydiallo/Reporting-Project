package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.TypeFondsDAO;
import com.sigif.dto.TypeFondsDTO;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeFonds;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux types de fonds.
 * 
 * @author Meissa Beye
 *
 */
@Service("typeFondsService")
@Transactional
public class TypeFondsServiceImpl extends AbstractServiceImpl<TypeFonds, TypeFondsDTO> implements TypeFondsService {

	/**
	 * DAO pour TypeFonds.
	 */
	@Autowired
	private TypeFondsDAO typeFondsDAO;

	@Override
	public Map<String, String> getAllFundTypes() throws TechnicalException {
		List<TypeFonds> typeFonds = typeFondsDAO.getAllTypeFondsActifs();
		Map<String, String> resultat = new HashMap<String, String>();
		if (typeFonds != null) {
			for (TypeFonds typeF : typeFonds) {
				String valeur = typeF.getDesignation() + " (" + typeF.getPerimetreFinancier() + ")";

				resultat.put(String.valueOf(typeF.getId()), valeur);
			}
		}
		return FunctionsUtils.sortByValue(resultat);
	}
}
