package com.sigif.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Immobilisation;

/**
 * Implémentation de la classe d'accès aux données d'immobilisations.
 * 
 * @author Catherine Alardo
 * @since 16/06/2017
 */
@Repository("immobilisationDAO")
public class ImmobilisationDAOImpl extends AbstractDAOImpl<Immobilisation> implements ImmobilisationDAO {

	/** LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ImmobilisationDAOImpl.class);

	@Override
	public Immobilisation getImmobilisationByRef(String refImmo, String statut) throws TechnicalException {
		try {
			Statut statutImmo = Statut.actif;
			if ("inactif".equalsIgnoreCase(statut)) {
				statutImmo = Statut.inactif;
			}
			Immobilisation resultat = (Immobilisation) this.getSession()
					.createQuery(" FROM Immobilisation where code = :refImmo and statut = :statutImmo ")
					.setParameter("refImmo", refImmo).setParameter("statutImmo", statutImmo).uniqueResult();

			return resultat;
		} catch (Exception e) {
			LOGGER.error("Impossible de récuperer l'immobilisation avec la référence " + refImmo);
			throw new TechnicalException("Impossible de récuperer l'immobilisation avec la référence " + refImmo, e);
		}
	}
}
