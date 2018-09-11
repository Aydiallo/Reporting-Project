package com.sigif.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.StatutEchanges;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.EchangesSAP;

/**
 * Implémentation de la classe d'accès à la table des échanges avec SAP.
 * 
 */
@Repository("echangesSapDao")
public class EchangesSapDaoImpl extends AbstractDAOImpl<EchangesSAP> implements EchangesSapDao {
    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(EchangesSapDaoImpl.class);

    @Override
    public EchangesSAP getEchangeEnCours() throws TechnicalException {
        try {
            EchangesSAP echange = (EchangesSAP) this.getSession()
                    .createQuery(" FROM EchangesSAP where statut = :statut ORDER BY dateLancement DESC")
                    .setParameter("statut", StatutEchanges.EnCours).setMaxResults(1).uniqueResult();

            return echange;
        } catch (Exception e) {
            LOGGER.error(
                    "Impossible de récuperer les informations du dernier import/export en cours : " + e.getMessage());
            throw new TechnicalException("Impossible de récuperer les informations du dernier import/export en cours",
                    e);
        }
    }
}
