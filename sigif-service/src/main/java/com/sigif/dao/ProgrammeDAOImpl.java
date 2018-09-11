package com.sigif.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Programme;

/**
 * Implémentation de la classe d'accès aux données des programmes.
 * 
 * @author Mamadou Ndir 
 * @since 22 mai 201717:27:13
 */
@Repository("programmeDAO")
public class ProgrammeDAOImpl extends AbstractDAOImpl<Programme> implements ProgrammeDAO {
    /** Loggeur. */
    private static final Logger LOG = LoggerFactory.getLogger(ProgrammeDAOImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<Programme> getAllProgrammOfMinistery(String ministery)
            throws TechnicalException {
        try {
            String queryString =
                    "select p from Programme p where p.statut = :paramStatut and p.ministere.code =:codeMinistery";
            Query query = this.getSession().createQuery(queryString).setParameter("paramStatut", Statut.actif)
                    .setParameter("codeMinistery", ministery);
            List<Programme> programListQuery = query.list();
            return programListQuery;

        } catch (Exception e) {
            LOG.error("Impossible de recupérer les programme liés au code ministere " + ministery);
            throw new TechnicalException("Impossible de recupérer les programme liés au code ministere " + ministery, e);
        }
    }

    @Override
    public Programme getProgrammeByCode(String codePrg) throws TechnicalException {
        try {
            Query query = this.getSession()
                    .createQuery(" From Programme where code = :codePrg and statut = '" + Statut.actif + "'")
                    .setParameter("codePrg", codePrg);
            Programme programme = (Programme) query.uniqueResult();
            return programme;

        } catch (Exception e) {
            throw new TechnicalException(
                    String.format("Erreur lors de la recherche du programme de code '%s'", codePrg), e);
        }
    }

}
