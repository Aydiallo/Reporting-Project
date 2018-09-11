package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Region;

/**
 * Implémentation de la classe d'accès aux donées des régions.
 * @author Meissa Beye
 * @since 02/06/2017
 */
@Repository("regionDAO")
public class RegionDAOImpl extends AbstractDAOImpl<Region> implements RegionDAO {
	
	/** LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RegionDAOImpl.class);
		
	@SuppressWarnings("unchecked")
	@Override
    public List<Region> getAllRegionActifs() throws TechnicalException {
		try {
			List<Region> regions = this.getSession()
						 .createQuery(" FROM Region where statut = '" + Statut.actif + "'").list();
			
			return regions;
			
		} catch (Exception e) {
		    LOGGER.error("Impossible de récuperer les régions actives ");
		    throw new TechnicalException("Impossible de récuperer les régions actives", e);
		}	
	}
}
