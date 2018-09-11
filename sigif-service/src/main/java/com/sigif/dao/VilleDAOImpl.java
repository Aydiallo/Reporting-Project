package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Ville;

/**
 * Implémentation de la classe d'accès aux données des villes.
 * 
 * @author Meissa Beye
 * @since 02/06/2017
 */
@Repository("villeDAO")
public class VilleDAOImpl extends AbstractDAOImpl<Ville> implements VilleDAO {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(VilleDAOImpl.class);

    /** DepartementDAO. */
    @Autowired
    DepartementDAO regionDAO;

    @SuppressWarnings("unchecked")
    @Override
    public List<Ville> getAllVilleActifs() throws TechnicalException {
        try {
            List<Ville> villes =
                    this.getSession().createQuery(" FROM Ville where statut = '" + Statut.actif + "'").list();

            return villes;

        } catch (Exception e) {
            LOGGER.error("Impossible de récuperer les villes actives ");
            throw new TechnicalException("Impossible de récuperer les villes actives", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ville> getVilleActifsByDepartement(String codeDepartement) throws TechnicalException {
        try {
            List<Ville> villes = null;
            villes = this.getSession()
                    .createQuery(" FROM Ville where Departement.code = :codeDepartement and statut = '" + Statut.actif + "'")
                    .setParameter("codeDepartement", codeDepartement).list();

            return villes;

        } catch (Exception e) {
            LOGGER.error("Impossible de récuperer les villes actives du departement " + codeDepartement);
            throw new TechnicalException("Impossible de récuperer les villes actives du departement " + codeDepartement, e);
        }
    }
}
