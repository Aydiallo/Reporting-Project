package com.sigif.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.CsfStatutAvancementHistoDao;
import com.sigif.enumeration.StatutAvancementCSF;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.ConstatationServiceFait;

/**
 * Implémentation de la classe de gestion de l'historisation des statuts des
 * CSF.
 * 
 */
@Service("csfStatutAvancementHistoService")
@Transactional
public class CsfStatutAvancementHistoServiceImpl implements CsfStatutAvancementHistoService {
    /**
     * Classe d'accès aux données de la table d'historisation.
     */
    @Autowired
    private CsfStatutAvancementHistoDao statutDao;

    @Override
    public void addNewStatusChange(ConstatationServiceFait csf, Date dateModification,
            StatutAvancementCSF statutAvancement) throws TechnicalException {
        this.statutDao.addNewStatusChange(csf, dateModification, statutAvancement);
    }

    @Override
    public void cleanHistory(int idCsf) throws TechnicalException {
        this.statutDao.deleteByCsf(idCsf);
    }
}
