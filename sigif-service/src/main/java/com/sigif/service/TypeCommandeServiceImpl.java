package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.TypeCommandeDAO;
import com.sigif.dto.TypeCommandeDTO;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeCommande;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux types de commande.
 * 
 */
@Service("typeCommandeService")
@Transactional
public class TypeCommandeServiceImpl extends AbstractServiceImpl<TypeCommande, TypeCommandeDTO>
        implements TypeCommandeService {
    /**
     * DAO pour TypeCommande.
     */
    @Autowired
    private TypeCommandeDAO tcDao;

	@Override
	public Map<String, String> getAllOrderTypes() throws TechnicalException {
		
		List<TypeCommande> typeCommandeActifs = tcDao.getTypeCommandeActifs();
		
		Map<String, String> resultat = new HashMap<String, String>();
		
		for (TypeCommande tc : typeCommandeActifs) {
			resultat.put(tc.getCode(), tc.getDesignation());
		}
		return FunctionsUtils.sortByValue(resultat);
	}
}
