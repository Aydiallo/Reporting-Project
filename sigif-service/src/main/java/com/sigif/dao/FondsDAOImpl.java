package com.sigif.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Fonds;

/**
 * Implémentation de la classe d'accès aux données des fonds.
 * 
 * @author Meissa Beye
 * @since 02/06/2017
 */
@Repository("fondsDAO")
public class FondsDAOImpl extends AbstractDAOImpl<Fonds> implements FondsDAO {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FondsDAOImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<Fonds> getAllFondsActifs() {
        List<Fonds> fonds = this.getSession().createQuery(" FROM Fonds where statut = '" + Statut.actif + "'").list();
        return fonds;
    }

    @Override
    public List<Fonds> getFondsActifsByTypeFonds(int idTypeFonds) throws TechnicalException {
        try {
            List<Fonds> fonds = null;
            Map<String, Object> criteres = new HashMap<String, Object>();
            criteres.put("typeFonds.id", idTypeFonds);
            criteres.put("statut", Statut.actif);
            fonds = this.getByParams(criteres);
            return fonds;
        } catch (Exception e) {
            LOGGER.error("Impossible de récuperer les fonds actifs de type id = " + idTypeFonds);
            throw new TechnicalException("Impossible de récuperer les fonds actifs de type id = " + idTypeFonds, e);
        }
    }
}
