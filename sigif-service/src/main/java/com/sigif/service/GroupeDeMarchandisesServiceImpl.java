package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.GroupeDeMarchandisesDAO;
import com.sigif.dto.GroupeDeMarchandisesDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.GroupeDeMarchandises;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux groupes de marchandises.
 * 
 * @author Meissa Beye
 *
 */
@Service("groupeDeMarchandisesService")
@Transactional
public class GroupeDeMarchandisesServiceImpl extends AbstractServiceImpl<GroupeDeMarchandises, GroupeDeMarchandisesDTO>
        implements GroupeDeMarchandisesService {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupeDeMarchandisesServiceImpl.class);

    /**
     * DAO pour GroupeDeMarchandises.
     */
    @Autowired
    private GroupeDeMarchandisesDAO grpMarchandisesDAO;

	@Override
	public Map<String, String> getAllMerchandisesGroup(String codeTypeAchat) throws ApplicationException, TechnicalException {
	    codeTypeAchat = FunctionsUtils.checkNotNullNotEmptyAndTrim("codeTypeAchat", codeTypeAchat, LOGGER);
		List<GroupeDeMarchandises> grpMarchandises = grpMarchandisesDAO.getGroupeDeMarchandisesByTypeAchat(codeTypeAchat);
		Map<String, String> resultat = new HashMap<String, String>();
		if (grpMarchandises != null) {	
			for (GroupeDeMarchandises grpM : grpMarchandises) {
				resultat.put(grpM.getCode(), grpM.getDesignation());
			}
		}
		return FunctionsUtils.sortByValue(resultat);
	}
	
	@Override
	public Map<String, String> getAllActifMerchandisesGroup() throws TechnicalException {
		
		List<GroupeDeMarchandises> grpMarchandises = grpMarchandisesDAO.getAllGroupeDeMarchandisesActifs();
		Map<String, String> resultat = new HashMap<String, String>();
		if (grpMarchandises != null) {	
			for (GroupeDeMarchandises grpM : grpMarchandises) {
				resultat.put(grpM.getCode(), grpM.getDesignation());
			}
		}
		return FunctionsUtils.sortByValue(resultat);
	}

}
