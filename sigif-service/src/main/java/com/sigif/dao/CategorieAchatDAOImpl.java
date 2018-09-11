package com.sigif.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CategorieAchat;

/**
 * Implémentation de la classe d'accès aux données des catégories d'achat.
 * @author Mamadou Ndir 
 * @since 23 mai 2017 00:11:27
 */
@Repository("categorieAchatDAO")
public class CategorieAchatDAOImpl extends AbstractDAOImpl<CategorieAchat> implements CategorieAchatDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<CategorieAchat> getAllPurchasingCategories(String status) throws TechnicalException {
		try {
			List<CategorieAchat> categDachatList;
			if (status.equalsIgnoreCase("actif")) {
				categDachatList = this.getSession().createQuery(" From CategorieAchat c where c.statut = :paramStatut")
						.setParameter("paramStatut", Statut.actif).list();
			} else if (status.equalsIgnoreCase("inactif")) {
				categDachatList = this.getSession().createQuery(" From CategorieAchat c where c.statut = :paramStatut")
						.setParameter("paramStatut", Statut.inactif).list();
			} else {
				categDachatList = this.getSession().createQuery(" From CategorieAchat c").list();
			}

			return categDachatList;
		} catch (Exception e) {
			throw new TechnicalException("Erreur lors de la recherche des categories d'achat.", e);
		}
	}

	@Override
	public CategorieAchat getCategorieAchatByCode(String codeCA) throws TechnicalException {
		try {
			Query query = this.getSession()
					.createQuery(" From CategorieAchat where code = :codeCA and statut = :paramStatut")
					.setParameter("codeCA", codeCA).setParameter("paramStatut", Statut.actif);
			CategorieAchat categorieAchat = (CategorieAchat) query.uniqueResult();
			return categorieAchat;

		} catch (Exception e) {
			throw new TechnicalException(
					String.format("Erreur lors de la recherche de la categorie d'achat de code %s ", codeCA), e);
		}
	}
}
