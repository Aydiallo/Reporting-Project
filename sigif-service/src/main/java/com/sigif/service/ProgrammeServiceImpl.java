package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.ProgrammeDAO;
import com.sigif.dto.ProgrammeDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Programme;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation du service d'accès aux programmes
 * @author Mamadou Ndir 
 * @since 22 mai 201717:03:29
 */
@Service("programmeService")
@Transactional
public class ProgrammeServiceImpl extends AbstractServiceImpl<Programme, ProgrammeDTO> implements ProgrammeService {
    /**
     * Loggueur.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ProgrammeServiceImpl.class);

    @Override
    protected ProgrammeDAO getDao() {
        return (ProgrammeDAO) super.getDao();
    }

    @Override
    public Map<String, String> getAllProgrammOfUserByLoginRoleAndMinistery(String login, String ministery, String role)
            throws ApplicationException, TechnicalException {
        ministery = FunctionsUtils.checkNotNullNotEmptyAndTrim("ministery", ministery, LOG);
        try {
            List<Programme> programListQuery =
                    getDao().getAllProgrammOfMinistery(ministery);
            Map<String, String> programList = new HashMap<String, String>();
            if (programListQuery.size() > 0) {
                for (Programme p : programListQuery) {
                    programList.put(p.getCode(), p.getDescription());
                }
            }

            return FunctionsUtils.sortByValue(programList);
        } catch (Exception e) {
            String msg = String.format(
                    "Impossible de récupérer les programmes pour le ministère '%s', le login '%s' et le rôle '%s' : %s",
                    ministery, login, role, e.getMessage());
            LOG.error(msg);
            throw new TechnicalException(msg);
        }
    }
}
