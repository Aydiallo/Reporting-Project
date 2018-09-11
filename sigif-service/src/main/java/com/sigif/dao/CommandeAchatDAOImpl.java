package com.sigif.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.StatutPosteCA;
import com.sigif.enumeration.StatutReceptionCA;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PosteCommandeAchat;
import com.sigif.modele.ServiceDepensier;

/**
 * Implémentation de la classe d'accès aux données des commandes d'achats.
 * 
 * @author Malick Diagne
 */
@Repository("commandeAchatDAO")
public class CommandeAchatDAOImpl extends AbstractDAOImpl<CommandeAchat> implements CommandeAchatDAO {
    /**
     * Loggueur.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandeAchatDAOImpl.class);

    /**
     * Dao des services dépensiers.
     */
    @Autowired
    ServiceDepensierDAO serviceDepensierDAO;

    @Override
    public int searchCANbResults(String spendingService, String noCA, String orderType, String purchasingCategory,
            StatutReceptionCA status, Date dateFrom, Date dateTo) throws TechnicalException {

        try {
            ServiceDepensier serviceDepensier = serviceDepensierDAO.getUniqueByParam("code", spendingService);

            Criteria c = getSession().createCriteria(CommandeAchat.class, "ca");
            // remplace le count(distinct(id))
            c.setProjection(Projections.countDistinct("id"));
            // inner join par défaut
            c.createAlias("ca.posteCommandeAchats", "pca");
            c.createAlias("ca.typeCommande", "caTypeCommande");
            c.createAlias("ca.categorieAchat", "caCategorieAchat");
            if (status != null) {
                c.add(Restrictions.eq("ca.statut", status));
            } else {
                c.add(Restrictions.or(Restrictions.eq("ca.statut", StatutReceptionCA.PartiellementReceptionnee),
                        Restrictions.eq("ca.statut", StatutReceptionCA.NonReceptionnee)));
            }
            c.add(Restrictions.or(Restrictions.eq("pca.statut", StatutPosteCA.NonReceptionne),
                    Restrictions.eq("pca.statut", StatutPosteCA.PartiellementReceptionne)));
            c.add(Restrictions.eq("pca.serviceDepensierByIdServiceDepensier.id", serviceDepensier.getId()));

            if ((noCA != null)) {
                c.add(Restrictions.eq("ca.idSap", noCA));
            }

            if ((orderType != null)) {
                c.add(Restrictions.eq("caTypeCommande.code", orderType));
            }

            if ((purchasingCategory != null)) {
                c.add(Restrictions.eq("caCategorieAchat.code", purchasingCategory));
            }

            if ((dateFrom != null)) {
                c.add(Restrictions.ge("ca.dateCreationSap", dateFrom));
            }

            if ((dateTo != null)) {
                c.add(Restrictions.le("ca.dateCreationSap", dateTo));
            }
            Long nbCA = (Long) c.uniqueResult();
            return Math.toIntExact(nbCA);

        } catch (Exception e) {
            String msgErr = String.format("Erreur lors du search des commandes d'achats : %s", e.getMessage());
            LOGGER.error(msgErr);
            throw new TechnicalException(msgErr, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CommandeAchat> searchCA(String spendingService, String noCA, String orderType,
            String purchasingCategory, StatutReceptionCA status, Date dateFrom, Date dateTo, int nbMaxResults)
            throws TechnicalException {
        try {

            ServiceDepensier serviceDepensier = serviceDepensierDAO.getUniqueByParam("code", spendingService);
            // On ne peut pas utiliser Criteria sur ce cas là car on ne pourrait
            // pas faire la projection pour éviter les doublons avant
            // d'appliquer la limitation à 100
            StringBuilder sqlQuery = new StringBuilder("SELECT ca.* FROM commande_achat ca");
            sqlQuery.append(" INNER JOIN poste_commande_achat pca on ca.id=pca.idCA");

            if ((orderType != null)) {
                sqlQuery.append(" INNER JOIN type_commande typComm on ca.idTypeCommande=typComm.id");
            }

            if ((purchasingCategory != null)) {
                sqlQuery.append(" INNER JOIN categorie_achat catAch on ca.idCategorieAchat=catAch.id");
            }

            sqlQuery.append(" WHERE pca.idServiceDepensier = :idServiceDep");
            sqlQuery.append(" AND (pca.statut = :statutPosteNonRecep OR pca.statut = :statutPosteRecepPartiel)");

            if (status != null) {
                sqlQuery.append(" AND ca.statut = :statutCaParam");
            } else {
                sqlQuery.append(" AND (ca.statut = :statutCaNonRecep OR ca.statut = :statutCaRecepPartiel)");
            }

            if (dateFrom != null) {
                sqlQuery.append(" AND ca.dateCreation_SAP >= :dateFrom");
            }

            if (dateTo != null) {
                sqlQuery.append(" AND ca.dateCreation_SAP <= :dateTo");
            }

            if ((noCA != null)) {
                sqlQuery.append(" AND ca.idSap = :noCA");
            }

            if ((orderType != null)) {
                sqlQuery.append(" AND typComm.code = :orderType");
            }

            if ((purchasingCategory != null)) {
                sqlQuery.append(" AND catAch.code = :purchasingCategory");
            }

            // Pas besoin de grouper sur toutes les colonnes de CA car mysql
            // est permissif
            sqlQuery.append(" GROUP BY ca.id");
            sqlQuery.append(" ORDER BY ca.dateCreation_SAP DESC");
            sqlQuery.append(" LIMIT :nbMaxResults");

            SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());
            query.addEntity(CommandeAchat.class);
            query.setParameter("nbMaxResults", nbMaxResults);
            query.setParameter("idServiceDep", serviceDepensier.getId());
            query.setParameter("statutPosteNonRecep", StatutPosteCA.NonReceptionne.displayName());
            query.setParameter("statutPosteRecepPartiel", StatutPosteCA.PartiellementReceptionne.displayName());

            if (status != null) {
                query.setParameter("statutCaParam", status.displayName().replaceAll("'", "\'"));
            } else {
                query.setParameter("statutCaNonRecep", StatutReceptionCA.NonReceptionnee.displayName());
                query.setParameter("statutCaRecepPartiel", StatutReceptionCA.PartiellementReceptionnee.displayName());
            }

            if (dateFrom != null) {
                query.setParameter("dateFrom", dateFrom);
            }

            if (dateTo != null) {
                query.setParameter("dateTo", dateTo);
            }

            if ((noCA != null)) {
                query.setParameter("noCA", noCA);
            }

            if ((orderType != null)) {
                query.setParameter("orderType", orderType);
            }

            if ((purchasingCategory != null)) {
                query.setParameter("purchasingCategory", purchasingCategory);
            }

            List<CommandeAchat> results = query.list();
            return results;
        } catch (Exception e) {

            LOGGER.error("Erreur lors du search des commandes d'achats : " + e.getMessage());
            throw new TechnicalException("Erreur lors du search des commandes d'achats", e);
        }
    }

    @Override
    public CommandeAchat getCAInformation(String idSapCA) throws TechnicalException {
        try {
            CommandeAchat ca = this.getUniqueByParam("idSap", idSapCA);
            return ca;
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la lecture de la CA '" + idSapCA + "' : " + e.getMessage());
            throw new TechnicalException("Erreur lors de la lecture de la CA '" + idSapCA, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CommandeAchat> getAllCommandeAchatOfDA(String numDossierDA, String noPosteDA, String orderBy)
            throws TechnicalException {
        try {
            String queryString = " Select DISTINCT pca.commandeAchat FROM PosteCommandeAchat as pca "
                    + " WHERE pca.demandeAchat.numeroDossier = :numDossierDA ";
            if (StringUtils.isNotBlank(noPosteDA)) {
                queryString += " AND pca.posteDemandeAchat.idDaposte = :noPosteDA";
            }
            if (orderBy != null) {
                queryString += String.format(" ORDER BY pca.%s ASC", orderBy);
            }
            Query query = this.getSession().createQuery(queryString).setParameter("numDossierDA", numDossierDA);
            if (StringUtils.isNotBlank(noPosteDA)) {
                query.setParameter("noPosteDA", noPosteDA);
            }
            List<CommandeAchat> listeCA = query.list();

            return listeCA;
        } catch (Exception e) {
            String msgErreur =
                    String.format("Echec de la récupération des commandes d'achat de la DA de numéro de dossier : '%s'",
                            numDossierDA);
            LOGGER.error(msgErreur);

            throw new TechnicalException(msgErreur, e);
        }
    }

    @Override
    public StatutReceptionCA calculateCaStatus(CommandeAchat ca) throws TechnicalException {
        StatutReceptionCA result = StatutReceptionCA.NonValidee;
        boolean allClosed = true;
        boolean allNotReceived = true;
        if (!ca.getStatut().equals(StatutReceptionCA.NonValidee)) {
            try {
                loopPosteCa: for (PosteCommandeAchat pca : ca.getPosteCommandeAchats()) {
                    switch (pca.getStatut()) {
                    case PartiellementReceptionne:
                        allClosed = false;
                        allNotReceived = false;
                        break loopPosteCa;
                    case Cloture:
                        allNotReceived = false;
                        break;
                    case NonReceptionne:
                        allClosed = false;
                        break;
                    default: 
                        allClosed = false;
                        allNotReceived = false;
                        break;
                    }
                }
                if (allNotReceived) {
                    result = StatutReceptionCA.NonReceptionnee;
                } else if (allClosed) {
                    result = StatutReceptionCA.Receptionnee;
                } else {
                    result = StatutReceptionCA.PartiellementReceptionnee;
                }
                ca.setStatut(result);
                this.merge(ca);
            } catch (Exception e) {
                throw new TechnicalException("Erreur lors du calcul du staut de la CA : " + e.getMessage(), e);
            }
        }
        return result;
    }

    // Alpha ajout//
    
private List<CommandeAchat> listNotDeletableCA ;
    
    public List<CommandeAchat> getListNotDeletableCA() {
		return listNotDeletableCA;
	}

	public void addlistNotDeletableCA(List<CommandeAchat> liste)
    {
    	for(CommandeAchat ca : liste)
    	{
    		if(!this.listNotDeletableCA.contains(ca))
    		{
    			this.listNotDeletableCA.add(ca) ;
    		}
    	}
    }
	@SuppressWarnings("unchecked")
    
    public List<CommandeAchat> listNotDeletableCA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal) 
    {
    	Session session =  this.getSession();
        List<CommandeAchat> ca = session.createQuery("from CommandeAchat where  not ((statut = :status1 OR statut = :status2) AND "
        		+ " dateMiseAjourSap is not NULL AND :nbJoursDepuisModifStatutTerminal < DATEDIFF(current_date(),dateMiseAjourSap)"
        		+ " OR (dateMiseAjourSap is not NULL AND :nbJoursDepuisModif < DATEDIFF(current_date(),dateMiseAjourSap)))").setParameter("status1",StatutReceptionCA.Receptionnee)
        		.setParameter("status2",StatutReceptionCA.NonValidee)
        		.setParameter("nbJoursDepuisModifStatutTerminal",nbJoursDepuisModifStatutTerminal)
        		.setParameter("nbJoursDepuisModif", nbJoursDepuisModif).list();
		this.listNotDeletableCA =ca ;
        return ca;
    }
    
    @SuppressWarnings("unchecked")
    @Override
   public  List<CommandeAchat> listCommandeAchats(){
        return this.getAll();
    }
    @SuppressWarnings("unchecked")
	@Override
	public List<DemandeAchat> listDAfromCA(List<CommandeAchat> liste) {
		Session session =  this.getSession();
        List<DemandeAchat> da = session.createQuery("select pca.demandeAchat from PosteCommandeAchat pca where pca.commandeAchat IN :listca ").
        		setParameterList("listca", liste).list();
                return da;
	}
    @SuppressWarnings("unchecked")
	@Override
	public List<CommandeAchat> listDeletableCA(List<CommandeAchat> lnca) {
		Session session =  this.getSession();
        List<CommandeAchat> ca = session.createQuery("select distinct ca from CommandeAchat ca "
        		+ "left join fetch ca.posteCommandeAchats "
        		+ "where ca not in :liste")
        		.setParameterList("liste", lnca).list();
        
        return ca;
	}
    @SuppressWarnings("unchecked")
	@Override
	public List<PosteCommandeAchat> getPosteCommandeAchats(CommandeAchat ca) {
		Session session = this.getSession();
        List<PosteCommandeAchat> lpca = session.createQuery("from PosteCommandeAchat pca where pca.commandeAchat = :daenter")
        		.setParameter("daenter", ca).list();
        //System.out.println("da.size()"+da.size());
        return lpca;
	}

	@Override
	public void delete(List<CommandeAchat> liste) {
		for(int i=0;i<liste.size();i++)
		{
			this.delete(liste.get(i));
		}
	}
}
