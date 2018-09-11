package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.TypeAchatDAO;
import com.sigif.dto.TypeAchatDTO;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeAchat;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux Types d'achat.
 */
@Service("typeAchatService")
@Transactional
public class TypeAchatServiceImpl extends AbstractServiceImpl<TypeAchat, TypeAchatDTO> implements TypeAchatService {

	/**
	 * DAO pour TypeAchat.
	 */
	@Autowired
	private TypeAchatDAO typeAchatDAO;

	@Override
	public Map<String, String> getAllTypeAchat() throws TechnicalException {
		List<TypeAchat> typeAchats = typeAchatDAO.getAllTypeAchatActifs();
		Map<String, String> resultat = new HashMap<String, String>();
		if (typeAchats != null) {
			for (TypeAchat typeA : typeAchats) {
				resultat.put(typeA.getCode(), typeA.getDesignation());
			}
		}
		return FunctionsUtils.sortByValue(resultat);
	}

}
