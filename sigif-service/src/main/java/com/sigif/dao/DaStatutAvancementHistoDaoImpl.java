package com.sigif.dao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.StatutAvancementDA;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.DaStatutAvancementHistorique;
import com.sigif.modele.DemandeAchat;

/**
 * Implémentation de la classe d'accès à la table d'historisation des statuts
 * d'avancement des DA.
 * 
 */
@Repository("daStatutAvancementHistoDao")
public class DaStatutAvancementHistoDaoImpl extends AbstractDAOImpl<DaStatutAvancementHistorique>
        implements DaStatutAvancementHistoDao {
    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DaStatutAvancementHistoDaoImpl.class);

    @Override
    public void addNewStatusChange(DemandeAchat da, Date dateModification, StatutAvancementDA statutAvancement)
            throws TechnicalException {
        try {
            DaStatutAvancementHistorique lastHisto = (DaStatutAvancementHistorique) this.getSession()
                    .createQuery(" FROM DaStatutAvancementHistorique where idDA = :idDA ORDER BY date DESC")
                    .setParameter("idDA", da.getId()).setMaxResults(1).uniqueResult();
            // Insertion uniquement s'il n'y a pas d'histo pour cette DA ou que
            // la dernière entrée concerne un autre statut
            if (lastHisto == null || !lastHisto.getStatutAvancement().equals(statutAvancement)) {
                DaStatutAvancementHistorique newHisto =
                        new DaStatutAvancementHistorique(da, dateModification, statutAvancement);
                this.save(newHisto);
            } else {
                LOGGER.debug(
                        "Historisation non effectuée car la dernière entrée pour la DA '%s' concerne déjà le statut '%s'",
                        da.getNumeroDossier(), statutAvancement.displayName());
            }

        } catch (Exception e) {
            String msg =
                    String.format("Impossible d'ajouter une nouvelle entrée d'historisation du statut de la DA '%s'",
                            da.getNumeroDossier());
            LOGGER.error(msg);
            throw new TechnicalException(msg, e);
        }
    }

    @Override
    public void deleteByDa(int idDa) throws TechnicalException {
        try {
            this.getSession().createQuery("DELETE FROM DaStatutAvancementHistorique where idDA = :idDA")
                    .setParameter("idDA", idDa).executeUpdate();
        } catch (Exception e) {
            String msg = String.format(
                    "Impossible de supprimer les entrées de la table d'historisation du statut pour la DA d'id '%d'",
                    idDa);
            LOGGER.error(msg);
            throw new TechnicalException(msg, e);
        }
    }

	
}
