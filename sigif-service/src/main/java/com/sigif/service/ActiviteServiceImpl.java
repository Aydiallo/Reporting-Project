package com.sigif.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.ActiviteDAO;
import com.sigif.dto.ActiviteDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Activite;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux activités.
 * 
 * @author Meissa Beye
 * @since 01/06/2017
 */
@Service("activiteService")
@Transactional
public class ActiviteServiceImpl extends AbstractServiceImpl<Activite, ActiviteDTO>
        implements ActiviteService {
    /** Loggueur. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActiviteServiceImpl.class);


    /**
     * DAO pour Activite.
     */
    @Autowired
    private ActiviteDAO activityDAO;

	@Override
	public 	Map<String, String> getAllActivitiesByAction(String codeAction) throws ApplicationException, TechnicalException {
	    codeAction = FunctionsUtils.checkNotNullNotEmptyAndTrim("codeAction", codeAction, LOGGER);
	
		List<Activite> activites = activityDAO.getActiviteActifsByAction(codeAction);
		Map<String, String> resultat = new LinkedHashMap<String, String>();
		if (activites != null) {
			for (Activite activite : activites) {
				resultat.put(activite.getCode(), activite.getDescription());
			}
		}
		return resultat;
	}
}
