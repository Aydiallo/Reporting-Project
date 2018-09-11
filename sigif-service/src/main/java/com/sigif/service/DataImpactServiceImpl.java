package com.sigif.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.DADataImpactDAO;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.DADataImpact;

/**
 * Implémentation de la classe d'accès aux alertes.
 * 
 * @author Mickael Beaupoil
 *
 */
@Service("dataImpactService")
@Transactional
public class DataImpactServiceImpl implements DataImpactService {
    /**
     * DAO pour DataImpact.
     */
    @Autowired
    private DADataImpactDAO dataImpactDAO;

    @Override
    public void cleanDataImpact(int idDA, int idPosteDa) throws TechnicalException {
        dataImpactDAO.cleanDataImpact(idDA, idPosteDa);
    }

    @Override
    public List<DADataImpact> searchAlertDA(String numDA) throws TechnicalException {
        return dataImpactDAO.searchAlertDA(numDA);
    }

    @Override
    public List<DADataImpact> searchAlertPosteDA(String numDA, String numPosteDA) throws TechnicalException {
        return dataImpactDAO.searchAlertPosteDA(numDA, numPosteDA);
    }
}
