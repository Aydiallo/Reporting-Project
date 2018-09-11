package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Action;

/**
 * Implémentation de la classe d'accès aux données des actions.
 * 
 * @author Meissa Beye
 * @since 01/06/2017
 */
@Repository("actionDAO")
public class ActionDAOImpl extends AbstractDAOImpl<Action> implements ActionDAO {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionDAOImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<Action> getActionsByProgramme(String codeProgram) throws TechnicalException {
        try {
            List<Action> actions = null;
            actions = this.getSession()
                    .createQuery(" FROM Action where programme.code = :prg and statut = :statut ORDER BY description")
                    .setParameter("prg", codeProgram).setParameter("statut", Statut.actif).list();

            return actions;
        } catch (Exception e) {
            LOGGER.error("Impossible de récuperer les actions actives du programme " + codeProgram);
            throw new TechnicalException("Impossible de récuperer les actions actives du programme " + codeProgram, e);
        }
    }
}
