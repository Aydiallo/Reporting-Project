package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.MinistereDAO;
import com.sigif.dto.MinistereDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Ministere;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation du service d'accès aux ministères.
 * @author Mamadou Ndir
 * @since 17 mai 2017
 */
@Service("ministereService")
@Transactional
public class MinistereServiceImpl extends AbstractServiceImpl<Ministere, MinistereDTO> implements MinistereService {
    /**
     * LOGGER.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MinistereServiceImpl.class);

    @Override
    protected MinistereDAO getDao() {
        return (MinistereDAO) super.getDao();
    }

    @Override
    public Map<String, String> getAllMinisteryOfUserByLoginAndRole(String login, String role, String status)
            throws TechnicalException, ApplicationException {
        login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOG);
        role = FunctionsUtils.checkNotNullNotEmptyAndTrim("role", role, LOG);
        status = FunctionsUtils.checkNotNullNotEmptyAndTrim("status", status, LOG);
        List<Ministere> userMinisteresQuery = this.getDao().getAllMinisteryOfUserByLoginAndRole(login, role, status);
        Map<String, String> minsteres = new HashMap<String, String>();
        for (Ministere m : userMinisteresQuery) {
            minsteres.put(m.getCode(), m.getDesignation());
           
        }
        return FunctionsUtils.sortByValue(minsteres);
    }

	
}
