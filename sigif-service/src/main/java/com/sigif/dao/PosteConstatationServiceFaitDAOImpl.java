package com.sigif.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.ConstatationServiceFait;
import com.sigif.modele.PosteConstatationServiceFait;

/**
 * Implémentation Classe d'accès aux données des postes des CSF.
 * 
 */
@Repository("posteConstatationServiceFaitDAO")
public class PosteConstatationServiceFaitDAOImpl extends AbstractDAOImpl<PosteConstatationServiceFait>
        implements PosteConstatationServiceFaitDAO {
    /**
     * Loggueur.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PosteConstatationServiceFaitDAOImpl.class);

    @Override
    public int getNbPostesCsfLinkedToPosteCa(int idPosteCa) throws TechnicalException {
        try {
            Query query = this.getSession()
                    .createQuery(
                            "SELECT COUNT(1) FROM PosteConstatationServiceFait WHERE posteCommandeAchat.id =:idPosteCa")
                    .setParameter("idPosteCa", idPosteCa);
            Long valeur = (Long) query.iterate().next();
            return valeur.intValue();
        } catch (Exception e) {
            LOGGER.error(String.format("Echec de la récupération du nombre de postes de CSF liés à au poste de CA '%d'",
                    idPosteCa));

            throw new TechnicalException(
                    String.format("Echec de la récupération du nombre de postes de CSF liés à au poste de CA '%d'",
                            idPosteCa),
                    e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PosteConstatationServiceFait> getPostesCsfByPosteCa(String numCa, String numPoste, String numTeledossier)
            throws TechnicalException {

        try {
            StringBuilder queryHql = new StringBuilder(" FROM PosteConstatationServiceFait p");
            queryHql.append(" WHERE p.constatationServiceFait.commandeAchat.idSap = :numCAParam");
            queryHql.append(" AND p.posteCommandeAchat.idSap = :numPosteParam");
            if (numTeledossier != null) {
                queryHql.append(" AND p.constatationServiceFait.numeroDossier <> :numTeledossier");
            }
            Query query = this.getSession().createQuery(queryHql.toString());
            query.setParameter("numCAParam", numCa);
            query.setParameter("numPosteParam", numPoste);
            if (numTeledossier != null) {
                query.setParameter("numTeledossier", numTeledossier);
            }

            return query.list();
        } catch (Exception e) {
            LOGGER.error(
                    String.format("Echec de la récupération des postes de CSF liés à la CA '%s' et au poste de CA '%s'",
                            numCa, numPoste));

            throw new TechnicalException(
                    String.format("Echec de la récupération des postes de CSF liés à la CA '%s' et au poste de CA '%s'",
                            numCa, numPoste),
                    e);
        }
    }

    @Override
    public PosteConstatationServiceFait getPosteCsfByCsfAndIdCsfPoste(String numDossierCSF, String idCSFPoste)
            throws TechnicalException {
        try {
            StringBuilder queryHql = new StringBuilder(" FROM PosteConstatationServiceFait p");
            queryHql.append(" WHERE p.constatationServiceFait.numeroDossier = :numDossierCSF");
            queryHql.append(" AND p.idCsfposte = :idCSFPoste");
            Query query = this.getSession().createQuery(queryHql.toString());
            query.setParameter("numDossierCSF", numDossierCSF);
            query.setParameter("idCSFPoste", idCSFPoste);

            return (PosteConstatationServiceFait) query.uniqueResult();
        } catch (Exception e) {
            LOGGER.error(
                    String.format("Echec de la récupération du poste CSF lié à la CSF '%s' et de numéro de poste '%s'",
                            numDossierCSF, idCSFPoste));

            throw new TechnicalException(
                    String.format("Echec de la récupération du poste CSF lié à la CSF '%s' et de numéro de poste '%s'",
                            numDossierCSF, idCSFPoste),
                    e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PosteConstatationServiceFait> getItemsCSF(String numCsf) throws TechnicalException {
        try {
            String queryHql =
                    "FROM PosteConstatationServiceFait p WHERE p.constatationServiceFait.numeroDossier = :numCsfParam";
            Query query = this.getSession().createQuery(queryHql);
            query.setParameter("numCsfParam", numCsf);

            return query.list();
        } catch (Exception e) {
            LOGGER.error(String.format("Echec de la récupération des postes de la CSF '%s'", numCsf));

            throw new TechnicalException(String.format("Echec de la récupération des postes de la CSF '%s'", numCsf),
                    e);
        }
    }

    @Override
    public PosteConstatationServiceFait getPosteCSFInfo(String numCsf, String numPosteCsf) throws TechnicalException {
        try {
            StringBuilder queryHql = new StringBuilder(" FROM PosteConstatationServiceFait p");
            queryHql.append(" WHERE p.constatationServiceFait.numeroDossier = :numCsfParam");
            queryHql.append(" AND p.idCsfposte = :paramPosteCsf");
            Query query = this.getSession().createQuery(queryHql.toString());
            query.setParameter("numCsfParam", numCsf);
            query.setParameter("paramPosteCsf", numPosteCsf);

            return (PosteConstatationServiceFait) query.uniqueResult();
        } catch (Exception e) {
            LOGGER.error(String.format("Echec de la récupération du poste de la CSF '%s' et de numéro '%s'", numCsf,
                    numPosteCsf));

            throw new TechnicalException(String.format(
                    "Echec de la récupération du poste de la CSF '%s' et de numéro '%s'", numCsf, numPosteCsf), e);

        }
    }

    /* alpha ajout*/
    
	@Override
	public void initialise(Set<PosteConstatationServiceFait> postesASupprimer) {
		Session session = this.getSession();
        session.createQuery("from PosteConstatationServiceFait pcsf "
        		+ "left join fetch pcsf.PosteCommandeAchat"
        		+ " where pcsf  in :liste")
        		.setParameterList("liste", postesASupprimer).list();
        
        session.createQuery("from PosteConstatationServiceFait pcsf "
        		+ "left join fetch pcsf.PieceJointe"
        		+ " where pcsf  in :liste")
        		.setParameterList("liste", postesASupprimer).list();
		
	}

}
