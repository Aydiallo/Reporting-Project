package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeFonds;

/**
 * Implémentation de la classe d'accès aux données des types de Fonds.
 * @author Meissa Beye
 * @since 02/06/2017
 */
@Repository("typeFondsDAO")
public class TypeFondsDAOImpl extends AbstractDAOImpl<TypeFonds> implements TypeFondsDAO {
	
	/** LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TypeFondsDAOImpl.class);
		
	@SuppressWarnings("unchecked")
	@Override
    public List<TypeFonds> getAllTypeFondsActifs() throws TechnicalException {
		try {
			List<TypeFonds> typeFondss = this.getSession()
						 .createQuery(" FROM TypeFonds where statut = '" + Statut.actif + "'").list();
			
			return typeFondss;
			
		} catch (Exception e) {
		    LOGGER.error("Impossible de récuperer les TypeFonds actifs ");
			throw new TechnicalException("Impossible de récuperer les TypeFonds actifs", e);
		}	
	}
}
