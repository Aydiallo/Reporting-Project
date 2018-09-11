package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeAchat;

/**
 * Implémentation de la classe d'accès aux données des types d'achat.
 * 
 * @author Meissa Beye
 * @since 06/06/2017
 */
@Repository("typeAchatDAO")
public class TypeAchatDAOImpl extends AbstractDAOImpl<TypeAchat> implements TypeAchatDAO {

	/** LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TypeAchatDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<TypeAchat> getAllTypeAchatActifs() throws TechnicalException {
		try {
			List<TypeAchat> typeAchats = this.getSession()
					.createQuery(" FROM TypeAchat where statut = '" + Statut.actif + "'").list();
			return typeAchats;

		} catch (Exception e) {
			LOGGER.error("Impossible de récuperer les typeAchats actifs ");
			throw new TechnicalException("Impossible de récuperer les typeAchats actifs", e);
		}
	}
}
