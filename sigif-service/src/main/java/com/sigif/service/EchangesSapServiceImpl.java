package com.sigif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.EchangesSapDao;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.EchangesSAP;

/**
 * Implémentation de la classe de gestion des échanges entre Sigif-formulaires
 * et SAP.
 * 
 */
@Service("echangesSapService")
@Transactional
public class EchangesSapServiceImpl implements EchangesSapService {
    /**
     * Classe d'accès aux données de la table des échanges.
     */
    @Autowired
    private EchangesSapDao echangesDao;

    @Override
    public boolean isSapProcessRunning() throws TechnicalException {
        boolean result;
        EchangesSAP echange = echangesDao.getEchangeEnCours();
        if (echange != null) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
