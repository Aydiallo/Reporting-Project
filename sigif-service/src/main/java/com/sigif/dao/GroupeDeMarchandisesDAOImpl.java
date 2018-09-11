package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.GroupeDeMarchandises;

/**
 * Implémentation de la classe d'accès aux données des groupes De Marchandises.
 * 
 * @author Meissa Beye
 * @since 31/05/2017
 */
@Repository("groupeDeMarchandisesDAO")
public class GroupeDeMarchandisesDAOImpl extends AbstractDAOImpl<GroupeDeMarchandises>
		implements GroupeDeMarchandisesDAO {

	/** LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupeDeMarchandisesDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupeDeMarchandises> getGroupeDeMarchandisesByTypeAchat(String codeTypeAchat)
			throws TechnicalException {
		try {
			List<GroupeDeMarchandises> grpMarchandises = null;
			if ("F".equals(codeTypeAchat)) { // sans article
				grpMarchandises = this.getSession()
						.createQuery(" SELECT g FROM GroupeDeMarchandises as g where g.statut = '" + Statut.actif + "'"
								+ " and g not in (SELECT a.groupeDeMarchandises FROM Article as a where a.statut = '"
								+ Statut.actif + "' )")
						.list();

			} else if ("S".equals(codeTypeAchat)) { // Avec article
				grpMarchandises = this.getSession()
						.createQuery(" SELECT g FROM GroupeDeMarchandises as g where g.statut = '" + Statut.actif + "'"
								+ " and g in (SELECT a.groupeDeMarchandises FROM Article as a where a.statut = '"
								+ Statut.actif + "' )")
						.list();
			}

			return grpMarchandises;
		} catch (Exception e) {
			LOGGER.error("Impossible de récuperer les groupes de marchandises actifs en fonction du type d'achat "
					+ codeTypeAchat);
			throw new TechnicalException("Impossible de récuperer les groupes de marchandises actifs"
					+ " en fonction du type d'achat " + codeTypeAchat, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupeDeMarchandises> getAllGroupeDeMarchandisesActifs() throws TechnicalException {
		try {
			List<GroupeDeMarchandises> grpMarchandises = this.getSession()
					.createQuery(" SELECT g FROM GroupeDeMarchandises as g where g.statut = '" + Statut.actif + "'")
					.list();

			return grpMarchandises;

		} catch (Exception e) {
			LOGGER.error("Impossible de récuperer les groupes de marchandises actifs ");
			throw new TechnicalException("Impossible de récuperer les groupes de marchandises actifs", e);
		}
	}
}
