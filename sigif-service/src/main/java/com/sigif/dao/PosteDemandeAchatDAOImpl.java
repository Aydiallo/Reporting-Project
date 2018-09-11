package com.sigif.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sigif.modele.PosteDemandeAchat;

/**
 * Implémentation Classe d'accès aux données des Postes de Demande d'Achats.
 * 
 * @author Meissa Beye
 * @since 07/06/2017
 */
@Repository("posteDemandeAchatDAO")
public class PosteDemandeAchatDAOImpl extends AbstractDAOImpl<PosteDemandeAchat> implements PosteDemandeAchatDAO {
	@Override
	public PosteDemandeAchat getPosteDAInformation(String numDa, String noPoste) {	    
	    StringBuilder hqlQuery = new StringBuilder(" FROM PosteDemandeAchat pda"
                + " WHERE pda.demandeAchat.numeroDossier = :paraNumDA AND pda.idDaposte = :paramNumPoste");

        Query query = this.getSession().createQuery(hqlQuery.toString());
        query.setParameter("paraNumDA", numDa);
        query.setParameter("paramNumPoste", noPoste);
        return (PosteDemandeAchat) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PosteDemandeAchat> getItemsByDA(String numDA) {
		if (numDA != null && !numDA.isEmpty()) {
			StringBuilder hqlQuery = new StringBuilder(
					" FROM PosteDemandeAchat pda" + " WHERE pda.demandeAchat.numeroDossier = :paraNumDA");
			Query query = this.getSession().createQuery(hqlQuery.toString());
			query.setParameter("paraNumDA", numDA);
			return query.list();			
		}

		return null;
	}
}
