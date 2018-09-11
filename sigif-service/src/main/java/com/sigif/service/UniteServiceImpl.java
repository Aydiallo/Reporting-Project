package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.UniteDAO;
import com.sigif.dto.UniteDTO;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Unite;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux unités.
 * 
 * @author Meissa Beye
 *
 */
@Service("uniteService")
@Transactional
public class UniteServiceImpl extends AbstractServiceImpl<Unite, UniteDTO> implements UniteService {

    /**
     * DAO pour Unité.
     */
    @Autowired
    private UniteDAO uniteDAO;

    @Override
    public Map<String, String> getAllActifUnits() throws TechnicalException {
        List<Unite> unites = uniteDAO.getAllUniteActifs();
        Map<String, String> resultat = new HashMap<String, String>();
        if (unites != null) {
            for (Unite unite : unites) {
                resultat.put(unite.getCode(), unite.getNom());
            }
        }
        return FunctionsUtils.sortByValue(resultat);
    }
}
