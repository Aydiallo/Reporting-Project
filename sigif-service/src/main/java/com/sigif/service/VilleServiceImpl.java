package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.VilleDAO;
import com.sigif.dto.VilleDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Ville;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux villes.
 */
@Service("villeService")
@Transactional
public class VilleServiceImpl extends AbstractServiceImpl<Ville, VilleDTO> implements VilleService {
    /**
     * Loggueur.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(VilleServiceImpl.class);

    /**
     * DAO pour Ville.
     */
    @Autowired
    private VilleDAO villeDAO;

    @Override
    public Map<String, String> getAllTownsByArea(String areaCode) throws ApplicationException, TechnicalException {
        areaCode = FunctionsUtils.checkNotNullNotEmptyAndTrim("areaCode", areaCode, LOGGER);
        List<Ville> villes = villeDAO.getVilleActifsByDepartement(areaCode);
        Map<String, String> resultat = new HashMap<String, String>();
        if (villes != null) {
            for (Ville ville : villes) {
                resultat.put(ville.getCode(), ville.getDesignation());
            }
        }
        return FunctionsUtils.sortByValue(resultat);
    }

	
}
