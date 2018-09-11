package com.sigif.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.DaStatutAvancementHistoDao;
import com.sigif.enumeration.StatutAvancementDA;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.DemandeAchat;

/**
 * Implémentation de la classe de gestion de l'historisation des statuts des DA.
 * 
 */
@Service("daStatutAvancementHistoService")
@Transactional
public class DaStatutAvancementHistoServiceImpl implements DaStatutAvancementHistoService {
    /**
     * Classe d'accès aux données de la table d'historisation.
     */
    @Autowired
    private DaStatutAvancementHistoDao statutDao;

    @Override
    public void addNewStatusChange(DemandeAchat da, Date dateModification, StatutAvancementDA statutAvancement)
            throws TechnicalException {
        this.statutDao.addNewStatusChange(da, dateModification, statutAvancement);
    }

    @Override
    public void cleanHistory(int idDa) throws TechnicalException {
        this.statutDao.deleteByDa(idDa);
    }
}
