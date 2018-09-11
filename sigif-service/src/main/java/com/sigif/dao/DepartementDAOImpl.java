package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Departement;

/**
 * Implémentation de la classe d'accès aux données des departements.
 * 
 * @author Meissa Beye
 * @since 02/06/2017
 */
@Repository("departementDAO")
public class DepartementDAOImpl extends AbstractDAOImpl<Departement> implements DepartementDAO {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartementDAOImpl.class);

    /** RegionDAO. */
    @Autowired
    RegionDAO regionDAO;

    @SuppressWarnings("unchecked")
    @Override
    public List<Departement> getAllDepartementActifs() throws TechnicalException {
        try {
            List<Departement> departements =
                    this.getSession().createQuery(" FROM departement where statut = '" + Statut.actif + "'").list();

            return departements;

        } catch (Exception e) {
            LOGGER.error("Impossible de récuperer les departements actives ");
            throw new TechnicalException("Impossible de récuperer les departements actives", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
	public
    List<Departement> getDepartementActifsByRegion(String codeRegion)throws TechnicalException {
        try {
            List<Departement> departements = null;
            departements = this.getSession()
                    .createQuery(" FROM departement where region.code = :codeRegion and statut = '" + Statut.actif + "'")
                    .setParameter("codeRegion", codeRegion).list();

            return departements;

        } catch (Exception e) {
            LOGGER.error("Impossible de récuperer les departements actives de la région " + codeRegion);
            throw new TechnicalException("Impossible de récuperer les departements actives de la région " + codeRegion, e);
        }
    }
}
