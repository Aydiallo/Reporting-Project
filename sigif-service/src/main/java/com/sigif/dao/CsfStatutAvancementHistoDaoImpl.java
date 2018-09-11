package com.sigif.dao;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.StatutAvancementCSF;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.ConstatationServiceFait;
import com.sigif.modele.CsfStatutAvancementHistorique;

/**
 * Implémentation de la classe d'accès à la table d'historisation des statuts
 * d'avancement des CSF.
 * 
 */
@Repository("csfStatutAvancementHistoDao")
public class CsfStatutAvancementHistoDaoImpl extends AbstractDAOImpl<CsfStatutAvancementHistorique>
        implements CsfStatutAvancementHistoDao {
    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CsfStatutAvancementHistoDaoImpl.class);

    @Override
    public void addNewStatusChange(ConstatationServiceFait csf, Date dateModification,
            StatutAvancementCSF statutAvancement) throws TechnicalException {
        try {
            CsfStatutAvancementHistorique lastHisto = (CsfStatutAvancementHistorique) this.getSession()
                    .createQuery(" FROM CsfStatutAvancementHistorique where idCSF = :idCSF ORDER BY date DESC")
                    .setParameter("idCSF", csf.getId()).setMaxResults(1).uniqueResult();
            // Insertion uniquement s'il n'y a pas d'histo pour cette CSF ou que
            // la dernière entrée concerne un autre statut
            if (lastHisto == null || !lastHisto.getStatutAvancement().equals(statutAvancement)) {
                CsfStatutAvancementHistorique newHisto =
                        new CsfStatutAvancementHistorique(csf, dateModification, statutAvancement);
                this.save(newHisto);
            } else {
                LOGGER.debug(
                        "Historisation non effectuée car la dernière entrée pour la CSf '%s' concerne déjà le statut '%s'",
                        csf.getNumeroDossier(), statutAvancement.displayName());
            }

        } catch (Exception e) {
            String msg =
                    String.format("Impossible d'ajouter une nouvelle entrée d'historisation du statut de la CSF '%s'",
                            csf.getNumeroDossier());
            LOGGER.error(msg);
            throw new TechnicalException(msg, e);
        }
    }

    @Override
    public void deleteByCsf(int idCsf) throws TechnicalException {
        try {
            this.getSession().createQuery("DELETE FROM CsfStatutAvancementHistorique where idCSF = :idCsf")
                    .setParameter("idCsf", idCsf).executeUpdate();
        } catch (Exception e) {
            String msg = String.format(
                    "Impossible de supprimer les entrées de la table d'historisation du statut pour la CSF d'id '%d'",
                    idCsf);
            LOGGER.error(msg);
            throw new TechnicalException(msg, e);
        }
    }
}
