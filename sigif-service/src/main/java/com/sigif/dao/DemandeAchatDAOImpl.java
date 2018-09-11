package com.sigif.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.enumeration.StatutApprobationDA;
import com.sigif.enumeration.StatutAvancementDA;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PosteCommandeAchat;
import com.sigif.util.Constantes;

/**
 * Implémentation de la classe d'accès aux données des demandes d'achat.
 * 
 * @author Mamadou Ndir 
 * @since 6 juin 2017 09:26:04
 */
@Repository("demandeAchatDAO")
public class DemandeAchatDAOImpl extends AbstractDAOImpl<DemandeAchat> implements DemandeAchatDAO {
    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DemandeAchatDAOImpl.class);

  
    
    /*
     * DaStatutAvancementHistoDao
     */
    @Autowired
    DaStatutAvancementHistoDao dasaHDAO;
    
    @Override
    public int countDANbResults(String login, String ministry, String spendingService, String requesterLogin,
            String purchasingCategory, Date dateFrom, Date dateTo, StatutAvancementDA progessStatus,
            StatutApprobationDA approvalStatus, String numDA) throws TechnicalException {

        try {
            StringBuilder hqlQuery = new StringBuilder("Select COUNT(*) FROM DemandeAchat da WHERE 1=1 ");

            if (ministry != null) {
                hqlQuery.append(" AND da.ministere.code = :paramMinistry");
            }
            if (spendingService != null) {
                hqlQuery.append(" AND da.serviceDepensier.code = :paramSpendingService");
            } else {
                hqlQuery.append(" AND da.serviceDepensier IN (Select s FROM Profil p join p.serviceDepensier s where"
                        + " p.utilisateur.login =:userLogin and s.statut = '" + Statut.actif
                        + "' and p.role.code = :roleDemandeur) ");
            }
            if (requesterLogin != null) {
                hqlQuery.append(" AND da.demandeur.login = :paramRequesterLogin");
            }
            if (purchasingCategory != null) {
                hqlQuery.append(" AND da.categorieAchat.code = :paramPurchasingCategory");
            }
            if (dateFrom != null) {
                hqlQuery.append(" AND da.dateCreation >= :paramDateFrom");
            }
            if (dateTo != null) {
                hqlQuery.append(" AND da.dateCreation <= :paramDateTo");
            }
            if (progessStatus != null) {
                hqlQuery.append(" AND da.statutAvancement = :paramStatutAvancement");
            }
            if (approvalStatus != null) {
                hqlQuery.append(" AND da.statutApprobation = :paramStatutApprobation");
            }
            if (numDA != null) {
                hqlQuery.append(" AND da.numeroDossier = :paramNumDA");
            }

            Query query = this.getSession().createQuery(hqlQuery.toString());

            if (ministry != null) {
                query.setParameter("paramMinistry", ministry);
            }
            if (spendingService != null) {
                query.setParameter("paramSpendingService", spendingService);
            } else {
                query.setParameter("userLogin", login).setParameter("roleDemandeur", Constantes.ROLE_DEMANDEUR);
            }
            if (requesterLogin != null) {
                query.setParameter("paramRequesterLogin", requesterLogin);
            }
            if (purchasingCategory != null) {
                query.setParameter("paramPurchasingCategory", purchasingCategory);
            }
            if (dateFrom != null) {
                query.setParameter("paramDateFrom", dateFrom);
            }
            if (dateTo != null) {
                query.setParameter("paramDateTo", dateTo);
            }
            if (progessStatus != null) {
                query.setParameter("paramStatutAvancement", progessStatus);
            }
            if (approvalStatus != null) {
                query.setParameter("paramStatutApprobation", approvalStatus);
            }
            if (numDA != null) {
                query.setParameter("paramNumDA", numDA);
            }
            Long nbDA = (Long) query.uniqueResult();
            return Math.toIntExact(nbDA);
        } catch (Exception e) {
            String msg = String.format("Erreur lors du calcul du nombre de DA correspondants aux critères entrés : %s",
                    e.getMessage());
            LOGGER.error(msg);
            throw new TechnicalException(msg, e);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DemandeAchat> searchDA(String login, String ministry, String spendingService, String requesterLogin,
            String purchasingCategory, Date dateFrom, Date dateTo, StatutAvancementDA progessStatus,
            StatutApprobationDA approvalStatus, String numDA, int nbMaxResults) {
        StringBuilder hqlQuery = new StringBuilder(" FROM DemandeAchat da WHERE 1=1 ");
        if (ministry != null) {
            hqlQuery.append(" AND da.ministere.code = :paramMinistry");
        }
        if (spendingService != null) {
            hqlQuery.append(" AND da.serviceDepensier.code = :paramSpendingService");
        } else {
            hqlQuery.append(" AND da.serviceDepensier IN (Select s FROM Profil p join p.serviceDepensier s where"
                    + " p.utilisateur.login =:userLogin and s.statut = '" + Statut.actif
                    + "' and p.role.code = :roleDemandeur) ");
        }
        if (requesterLogin != null) {
            hqlQuery.append(" AND da.demandeur.login = :paramRequesterLogin");
        }
        if (purchasingCategory != null) {
            hqlQuery.append(" AND da.categorieAchat.code = :paramPurchasingCategory");
        }
        if (dateFrom != null) {
            hqlQuery.append(" AND da.dateCreation >= :paramDateFrom");
        }
        if (dateTo != null) {
            hqlQuery.append(" AND da.dateCreation <= :paramDateTo");
        }
        if (progessStatus != null) {
            hqlQuery.append(" AND da.statutAvancement = :paramStatutAvancement");
        }
        if (approvalStatus != null) {
            hqlQuery.append(" AND da.statutApprobation = :paramStatutApprobation");
        }
        if (numDA != null) {
            hqlQuery.append(" AND da.numeroDossier = :paramNumDA");
        }

        hqlQuery.append(" ORDER BY da.dateCreation DESC");
        Query query = this.getSession().createQuery(hqlQuery.toString());

        if (ministry != null) {
            query.setParameter("paramMinistry", ministry);
        }
        if (spendingService != null) {
            query.setParameter("paramSpendingService", spendingService);
        } else {
            query.setParameter("userLogin", login).setParameter("roleDemandeur", Constantes.ROLE_DEMANDEUR);
        }
        if (requesterLogin != null) {
            query.setParameter("paramRequesterLogin", requesterLogin);
        }
        if (purchasingCategory != null) {
            query.setParameter("paramPurchasingCategory", purchasingCategory);
        }
        if (dateFrom != null) {
            query.setParameter("paramDateFrom", dateFrom);
        }
        if (dateTo != null) {
            query.setParameter("paramDateTo", dateTo);
        }
        if (progessStatus != null) {
            query.setParameter("paramStatutAvancement", progessStatus);
        }
        if (approvalStatus != null) {
            query.setParameter("paramStatutApprobation", approvalStatus);
        }
        if (numDA != null) {
            query.setParameter("paramNumDA", numDA);
        }
        query.setMaxResults(nbMaxResults);
        ArrayList<DemandeAchat> daList = (ArrayList<DemandeAchat>) query.list();
        return daList;
    }

    @Override
    public DemandeAchat getDAInformation(String idDA) throws TechnicalException {
        try {
            DemandeAchat da = this.getUniqueByParam("numeroDossier", idDA);
            return da;
        } catch (Exception e) {
            String msg =
                    String.format("Erreur lors de la lecture des informations de la DA dont le numéro est '%s' : %s",
                            idDA, e.getMessage());
            throw new TechnicalException(msg, e);
        }
    }

    @Override
    public void unlockDaByDa(DemandeAchat da) {
        da.setLoginVerrou(null);
        da.setDateVerrou(null);
        this.merge(da);
    }
/// Alpha ajout//
    
    @SuppressWarnings("unchecked")
	@Override
	public List<DemandeAchat> listNotDeletableDA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		List<DemandeAchat> da = session.createQuery("from DemandeAchat where  not ((statutAvancement = :status AND "
        		+ " dateMiseAjourSAP is not NULL AND :nbJoursDepuisModifStatutTerminal < DATEDIFF(current_date(),dateMiseAjourSAP))"
        		+ " OR :nbJoursDepuisModif < DATEDIFF(current_date(),dateModification))").setParameter("status", StatutAvancementDA.TraitementTermine)
        		.setParameter("nbJoursDepuisModifStatutTerminal",nbJoursDepuisModifStatutTerminal)
        		.setParameter("nbJoursDepuisModif",nbJoursDepuisModif).list();
		this.listNotDeletableDA = da ;
		return da;
	}
    private List<DemandeAchat> listNotDeletableDA ;
    
    public void setListNotDeletableDA(List<DemandeAchat> listNotDeletableDA) {
		this.listNotDeletableDA = listNotDeletableDA;
	}


	public List<DemandeAchat> getListNotDeletableDA() {
		return listNotDeletableDA;
	}


    
    
    @Override
    public List<DemandeAchat> listDemandeAchats() {
    	System.out.println("***** service  List<DemandeAchat> listDemandeAchats() ***");
    	return this.getAll();
    }


	@Override
	@SuppressWarnings("unchecked")

	 public List<CommandeAchat> listCAfromDA(List<DemandeAchat> liste){
		Session session = this.getSession();
        List<CommandeAchat> da = session.createQuery("select pca.commandeAchat from PosteCommandeAchat pca where pca.demandeAchat IN :listda ").
        		setParameterList("listda", liste).list();
                return da;
	}


	@Override
	public void addlistNotDeletableDA(List<DemandeAchat> liste) {
		// TODO Auto-generated method stub
		for(int i=0;i<liste.size();i++)
		{
			this.delete(liste.get(i));
			if(!this.listNotDeletableDA.contains(liste.get(i)))
    		{
    			this.listNotDeletableDA.add(liste.get(i)) ;
    		}
		}
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<DemandeAchat> listDeletableDA(List<DemandeAchat> dac) {
		System.out.println("*tqille de lq liste *"+dac.size());
		//Logger.this.info("*tqille de lq liste *"+dac.size());
		Session session =this.getSession();
        List<DemandeAchat> da = session.createQuery("select distinct da FROM DemandeAchat da "
        		+ "left join fetch da.pieceJointe "
        		+ "left join fetch da.postesDemandeAchat pda "
        		+"left join fetch pda.pieceJointe "
        		+ " where da NOT IN (:liste)")
        		.setParameterList("liste", dac).list();
        
        return da;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PosteCommandeAchat> getPosteCommandeAchats(DemandeAchat demandeAchat) {
		Session session = this.getSession();
        List<PosteCommandeAchat> da = session.createQuery("from PosteCommandeAchat pca where pca.demandeAchat= :daenter")
        		.setParameter("daenter", demandeAchat).list();
       // System.out.println("da.size()"+da.size());
        return da;
	}


	@Override
	public void delete(List<DemandeAchat> liste) {
		for(int i=0;i<liste.size();i++)
		{
			System.out.println("********************"+liste.size()+"******************");
			try {
				System.out.println("********************"+liste.get(i).getId()+"******************");
				this.dasaHDAO.deleteByDa(liste.get(i).getId());
			} catch (TechnicalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.delete(liste.get(i));
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> listeDAforPSdM(String codeProgramme, String codeServiceDepensier, String codeMinister,Date dateFrom, Date dateTo) {
		// TODO Auto-generated method stub
		 StringBuilder hqlQuery = new StringBuilder(" Select month(da.dateModification), year(da.dateModification), count(*) FROM DemandeAchat da WHERE 1=1 ");
		 if (codeMinister != null) {
	            hqlQuery.append(" AND da.ministere.code = :paramMinistry");
	        }
		 if (codeProgramme != null) {
	            hqlQuery.append(" AND da.programme.code = :paramProgramme");
	        }
		 if (codeServiceDepensier != null) {
	            hqlQuery.append(" AND da.serviceDepensier.code = :paramServiceDepensier");
	        }
		 if(dateFrom.compareTo(dateTo)>0) return null ;
		 if (dateFrom != null) {
	            hqlQuery.append(" AND da.dateCreation >= :paramDateFrom");
	        }
	        if (dateTo != null) {
	            hqlQuery.append(" AND da.dateCreation <= :paramDateTo");
	        }
		 hqlQuery.append("Goup by GROUP BY YEAR(record_date), MONTH(record_date) ");
		 hqlQuery.append("ORDER BY da.dateCreation DESC");
	        Query query = this.getSession().createQuery(hqlQuery.toString());

	        if (codeMinister != null) {
	            query.setParameter("paramMinistry", codeMinister);
	        }
	        if (codeProgramme != null) {
	            query.setParameter("paramProgramme", codeProgramme);
	        }
	        if (codeServiceDepensier != null) {
	            query.setParameter("paramServiceDepensier", codeServiceDepensier);
	        }
	        if (dateFrom != null) {
	            query.setParameter("paramDateFrom", dateFrom);
	        }
	        if (dateTo != null) {
	            query.setParameter("paramDateTo", dateTo);
	        }
	        return query.list();
	}

}
