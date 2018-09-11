package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeImmobilisation;

/**
 * Implémentation de la classe d'accès aux données des types d'immobilisation
 * 
 * @author MeissaBeye
 * @since 14/06/2017
 */
@Repository("TypeImmobilisationDAO")
public class TypeImmobilisationDAOImpl extends AbstractDAOImpl<TypeImmobilisation> implements TypeImmobilisationDAO {

	/** LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TypeImmobilisationDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<TypeImmobilisation> getAllTypeImmobilisationActifs() throws TechnicalException {
		try {
			List<TypeImmobilisation> typeImmos = this.getSession()
					.createQuery(" FROM TypeImmobilisation where statut = '" + Statut.actif + "'").list();

			return typeImmos;

		} catch (Exception e) {
			LOGGER.error("Impossible de récuperer les typeImmos actifs ");
			throw new TechnicalException("Impossible de récuperer les typeImmos actifs", e);
		}
	}
}
