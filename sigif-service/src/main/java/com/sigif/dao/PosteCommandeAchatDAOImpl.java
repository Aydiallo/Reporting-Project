package com.sigif.dao;

import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.StatutPosteCA;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PosteCommandeAchat;

/**
 * Implémentation de la classe d'accès aux postes de commandes d'achat.
 * 
 * @author Mickael Beaupoil
 *
 */
@Repository("posteCommandeAchatDAO")
public class PosteCommandeAchatDAOImpl extends AbstractDAOImpl<PosteCommandeAchat> implements PosteCommandeAchatDAO {
	/**
	 * Loggueur.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PosteCommandeAchatDAOImpl.class);

	@Override
	public PosteCommandeAchat getPosteCaInformation(String numCA, String numPoste)
			throws TechnicalException, NonUniqueResultException {
		try {
			Query query = this.getSession()
					.createQuery("FROM PosteCommandeAchat WHERE commandeAchat.idSap = :numCA AND idSap = :numPoste")
					.setParameter("numCA", numCA).setParameter("numPoste", numPoste);
			PosteCommandeAchat poste = (PosteCommandeAchat) query.uniqueResult();

			return poste;
		} catch (NonUniqueResultException nonUniqeEx) {
			throw nonUniqeEx;
		} catch (Exception e) {
			LOGGER.error(String.format(
					"Echec de la récupération du poste pour la commande d'achat '%s' et le numéro de poste '%s'", numCA,
					numPoste));

			throw new TechnicalException(String.format(
					"Echec de la récupération du poste pour la commande d'achat '%s' et le numéro de poste '%s'",
					numCA, numPoste), e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PosteCommandeAchat> getClosedItemsByNumCA(String numCA, String orderBy) throws TechnicalException {
		try {
			String queryString = "FROM PosteCommandeAchat WHERE commandeAchat.idSap = :numCA AND statut = :statut";

			if (orderBy != null) {
				queryString += String.format(" ORDER BY %s ASC", orderBy);
			}

			Query query = this.getSession().createQuery(queryString).setParameter("numCA", numCA).setParameter("statut",
					StatutPosteCA.Cloture);
			List<PosteCommandeAchat> listePostes = query.list();

			return listePostes;
		} catch (Exception e) {
			LOGGER.error(String.format("Echec de la récupération des postes réceptionnés de la commande d'achat '%s'",
					numCA));

			throw new TechnicalException(
					String.format("Echec de la récupération des postes réceptionnés de la commande d'achat '%s' : %s",
							numCA, e.getMessage()),
					e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PosteCommandeAchat> getReceivableItemsByCaAndSpendingService(String numCA, String codeSpendingService,
			String orderBy) throws TechnicalException {
		try {
			String queryString = "FROM PosteCommandeAchat WHERE commandeAchat.idSap =:numCA "
					+ "AND serviceDepensierByIdServiceDepensier.code =:codeSD AND statut <> :statut";
			if (orderBy != null) {
				queryString += String.format(" ORDER BY %s ASC", orderBy);
			}

			Query query = this.getSession().createQuery(queryString).setParameter("numCA", numCA)
					.setParameter("codeSD", codeSpendingService).setParameter("statut", StatutPosteCA.Cloture);
			List<PosteCommandeAchat> listePostes = query.list();

			return listePostes;
		} catch (Exception e) {
			String msgErreur = String.format("Echec de la récupération des postes réceptionnables "
					+ "de la commande d'achat '%s' pour le service dépensier '%s'", numCA, codeSpendingService);
			LOGGER.error(msgErreur);

			throw new TechnicalException(msgErreur, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PosteCommandeAchat> getReceivableItemsByCaAndSpendingServiceAndCsf(String numCA, String codeSpendingService,
			String orderBy, String numCSF) throws TechnicalException {
		try {
			String queryString = "FROM PosteCommandeAchat pca WHERE commandeAchat.idSap =:numCA "
					+ "AND serviceDepensierByIdServiceDepensier.code =:codeSD AND statut <> :statut "
					+ "AND not exists (SELECT id FROM PosteConstatationServiceFait WHERE idCAPoste= pca.id " 
					+ "AND constatationServiceFait.numeroDossier =:numCSF)";
			if (orderBy != null) {
				queryString += String.format(" ORDER BY %s ASC", orderBy);
			}

			Query query = this.getSession().createQuery(queryString).setParameter("numCA", numCA)
					.setParameter("codeSD", codeSpendingService).setParameter("statut", StatutPosteCA.Cloture)
					.setParameter("numCSF", numCSF);
			List<PosteCommandeAchat> listePostes = query.list();

			return listePostes;
		} catch (Exception e) {
			String msgErreur = String.format("Echec de la récupération des postes réceptionnables "
					+ "de la commande d'achat '%s' pour le service dépensier '%s'", numCA, codeSpendingService);
			LOGGER.error(msgErreur);

			throw new TechnicalException(msgErreur, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PosteCommandeAchat> getNotReceivableItemsByCaAndSpendingService(String numCA,
			String codeSpendingService, String orderBy) throws TechnicalException {
		try {
			String queryString = "FROM PosteCommandeAchat WHERE commandeAchat.idSap = :numCA "
					+ "AND serviceDepensierByIdServiceDepensier.code <> :codeSD " + "AND statut <> :statut";
			if (orderBy != null) {
				queryString += String.format(" ORDER BY %s ASC", orderBy);
			}
			Query query = this.getSession().createQuery(queryString).setParameter("numCA", numCA)
					.setParameter("codeSD", codeSpendingService).setParameter("statut", StatutPosteCA.Cloture);
			List<PosteCommandeAchat> listePostes = query.list();

			return listePostes;
		} catch (Exception e) {
			String msgErreur = String.format("Echec de la récupération des postes non réceptionnables "
					+ "de la commande d'achat '%s' pour le service dépensier '%s'", numCA, codeSpendingService);
			LOGGER.error(msgErreur);

			throw new TechnicalException(msgErreur, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PosteCommandeAchat> getAllPosteOfCA(String idSapOfCA, String orderBy) throws TechnicalException {
		try {
			String queryString = "FROM PosteCommandeAchat WHERE commandeAchat.idSap = :idSapOfCA ";
			if (orderBy != null) {
				queryString += String.format(" ORDER BY %s ASC", orderBy);
			}
			Query query = this.getSession().createQuery(queryString).setParameter("idSapOfCA", idSapOfCA);
			List<PosteCommandeAchat> listePostes = query.list();

			return listePostes;
		} catch (Exception e) {
			String msgErreur = String.format("Echec de la récupération des postes de la CA d'ID SAP : '%s'", idSapOfCA);
			LOGGER.error(msgErreur);

			throw new TechnicalException(msgErreur, e);
		}
	}

    @Override
    public boolean updateQuantityAndStatus(PosteCommandeAchat pca, Long acceptedQty, boolean csfCreated) {
        if (!csfCreated) {
            pca.setQuantiteRestante(pca.getQuantiteRestante() + acceptedQty); 
        } else {
            if (pca.getQuantiteRestante() < acceptedQty) {
                // la quantité restante est inférieure à la quantité acceptée
                return false;
            } else {
                pca.setQuantiteRestante(pca.getQuantiteRestante() - acceptedQty);
            }
        }
        
        if (pca.getQuantiteRestante() == 0) {
            pca.setStatut(StatutPosteCA.Cloture);
        } else if (pca.getQuantiteCommandee() == pca.getQuantiteRestante()) {
            pca.setStatut(StatutPosteCA.NonReceptionne);
        } else {
            pca.setStatut(StatutPosteCA.PartiellementReceptionne);
        }
        this.merge(pca);
        return true;        
    }
    
    //Alpha ajout//
    
	@Override
	public void delete(List<PosteCommandeAchat> liste) {
		for(int i=0;i<liste.size();i++)
		{
			System.out.println("**************"+liste.get(i).getId()+"***************");
			this.getSession().clear();
			this.delete(liste.get(i));
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DemandeAchat> daLieesCa(List<CommandeAchat> liste) {
		System.out.println("/*************************************************"
				+ "1.1)public List<DemandeAchat> DaLieesCa(List<CommandeAchat> liste)  ****************************/");
		try {
			List<DemandeAchat> poste =this.getSession()
					.createQuery(" select p.demandeAchat FROM PosteCommandeAchat p  WHERE p.commandeAchat in :liste")
					.setParameterList("liste", liste).list();
			System.out.println("1.2)/*************************************************"+poste.size()+" ****************************/");
			
       
			return poste;
		} catch (Exception e) {
			System.out.println("1.3)/*************************************************erreur DAlieees****************************/");
	}
		return null;
}
}
