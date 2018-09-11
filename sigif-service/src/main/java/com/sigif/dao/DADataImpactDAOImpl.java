package com.sigif.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.DADataImpact;

/**
 *Implémentation de la classe d'accès aux données des impacts.
 * 
 */
@Repository("dADataImpactDAO")
public class DADataImpactDAOImpl extends AbstractDAOImpl<DADataImpact> implements DADataImpactDAO {
    /** Loggueur. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DADataImpactDAOImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<DADataImpact> searchAlertDA(String numDA) throws TechnicalException {
        try {
            StringBuilder hqlQuery = new StringBuilder(" FROM DADataImpact dai WHERE dai.posteDA IS NULL ");

            if (numDA != null && !numDA.isEmpty()) {
                hqlQuery.append(" AND dai.demandeAchat.numeroDossier = :paramNumDA");
            }
            Query query = this.getSession().createQuery(hqlQuery.toString());

            if (numDA != null && !numDA.isEmpty()) {
                query.setParameter("paramNumDA", numDA);
            }
            List<DADataImpact> daiList = query.list();
            return daiList;
        } catch (Exception e) {
            String msg = String.format("Erreur lors de la recherche des alertes pour la DA '%s'.", numDA);
            LOGGER.error(msg);
            throw new TechnicalException(msg, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DADataImpact> searchAlertPosteDA(String numDA, String numPosteDA) throws TechnicalException {
        try {
            StringBuilder hqlQuery = new StringBuilder(" FROM DADataImpact dai");

            hqlQuery.append(" WHERE dai.demandeAchat.numeroDossier = :paramNumDA");
            hqlQuery.append(" AND dai.posteDA.idDaposte = :paramPosteDA");

            Query query = this.getSession().createQuery(hqlQuery.toString());

            query.setParameter("paramNumDA", numDA);
            query.setParameter("paramPosteDA", numPosteDA);

            List<DADataImpact> daiList = query.list();
            return daiList;
        } catch (Exception e) {
            String msg = String.format("Erreur lors de la recherche des alertes pour la DA '%s' et son poste '%s'.",
                    numDA, numPosteDA);
            LOGGER.error(msg);
            throw new TechnicalException(msg, e);
        }
    }

    @Override
    public void cleanDataImpact(int idDA, int idPosteDa) throws TechnicalException {
        try {
            List<DADataImpact> dataImpactASupprimer;
            if (idPosteDa > 0) {
                dataImpactASupprimer = this.getByParam("posteDA.id", idPosteDa);
            } else {
                dataImpactASupprimer = this.getByParam("demandeAchat.id", idDA);
            }
            for (DADataImpact impact : dataImpactASupprimer) {
                this.delete(impact);
            }
        } catch (Exception e) {
            String msg = String.format(
                    "Erreur lors de la suppression des alertes pour la DA d'id '%d' ou le poste d'id '%d'.", idDA,
                    idPosteDa);
            LOGGER.error(msg);
            throw new TechnicalException(msg, e);
        }
    }

}
