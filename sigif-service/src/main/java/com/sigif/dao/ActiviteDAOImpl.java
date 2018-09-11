package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Activite;

/**
 * Implémentation de la classe d'accès aux données des activites.
 * 
 * @author Meissa Beye
 * @since 01/06/2017
 */
@Repository("activiteDAO")
public class ActiviteDAOImpl extends AbstractDAOImpl<Activite> implements ActiviteDAO {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActiviteDAOImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<Activite> getActiviteActifsByAction(String codeAction) throws TechnicalException {
        try {
            List<Activite> activites = null;
            activites = this.getSession()
                    .createQuery(" FROM Activite where action.code = :codeAction and statut = :statut ORDER BY description")
                    .setParameter("codeAction", codeAction).setParameter("statut", Statut.actif).list();

            return activites;
        } catch (Exception e) {
            LOGGER.error("Impossible de récuperer les activites actives de l'action " + codeAction);
            throw new TechnicalException("Impossible de récuperer les activites actives de l'action " + codeAction, e);
        }
    }
}
