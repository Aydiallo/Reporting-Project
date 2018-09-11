package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Unite;

/**
 * Implémentation de la classe d'accès aux données des unités.
 * @author Meissa Beye
 * @since 01/06/2017
 */
@Repository("uniteDAO")
public class UniteDAOImpl extends AbstractDAOImpl<Unite> implements UniteDAO {
	
	/** LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UniteDAOImpl.class);
		
	@SuppressWarnings("unchecked")
	@Override
    public List<Unite> getAllUniteActifs() throws TechnicalException {
		try {
			List<Unite> unites = this.getSession()
						 .createQuery(" FROM Unite where statut = '" + Statut.actif + "'").list();
			return unites;
			
		} catch (Exception e) {
		    LOGGER.error("Impossible de récuperer les unités actives ");
		    throw new TechnicalException("Impossible de récuperer les unités actives", e);
		}	
	}
}
