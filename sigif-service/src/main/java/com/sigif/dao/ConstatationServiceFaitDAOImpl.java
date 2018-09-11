package com.sigif.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.StatutAvancementCSF;
import com.sigif.enumeration.StatutCertificationCSF;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.ConstatationServiceFait;
import com.sigif.util.Constantes;

/**
 * Implémentation de la classe d'accès aux données des  CSF.
 * 
 * @author Mickael Beaupoil
 *
 */
@Repository("constatationServiceFaitDAO")
public class ConstatationServiceFaitDAOImpl extends AbstractDAOImpl<ConstatationServiceFait>
        implements ConstatationServiceFaitDAO {
    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstatationServiceFaitDAOImpl.class);

    /**
     * PieceJointeDAO.
     */
    @Autowired
    PieceJointeDAO pieceJointeDAO;

    /**
     * UtilisateurDAO.
     */
    @Autowired
    UtilisateurDAO utilisateurDAO;

    /**
     * CommandeAchatDAO.
     */
    @Autowired
    CommandeAchatDAO commandeAchatDAO;
    
    /**
     *  ConstatationServiceFait
     */
    @Autowired
    ConstatationServiceFaitDAO constatationServiceFaitDAO;
    
    /**
     *  CsfStatutAvancementHistoDao
     */
    @Autowired
    CsfStatutAvancementHistoDao csfsAHDAO;
    

    @Override
    public CommandeAchat getCAofCSF(String numDossierCSF) throws TechnicalException {
        String queryString =
                " Select commandeAchat FROM  ConstatationServiceFait WHERE numeroDossier = :numDossierCSF ";
        try {
            Query query = this.getSession().createQuery(queryString).setParameter("numDossierCSF", numDossierCSF);
            CommandeAchat commande = (CommandeAchat) query.uniqueResult();
            return commande;
        } catch (Exception e) {
            String msgErreur = String.format(
                    "Echec de la récupération de la commande d'achat de la CSF de numéro de dossier : '%s'",
                    numDossierCSF);
            LOGGER.error(msgErreur);
            throw new TechnicalException(msgErreur, e);
        }
    }

    @Override
    public String getAcceptationStatus(String numDossierCSF) throws TechnicalException {
        String queryString = "SELECT SUM(COALESCE(pcsf.quantiteAcceptee, 0)), "
                + "SUM(COALESCE(pcsf.quantiteRecue, 0)) FROM PosteConstatationServiceFait pcsf "
                + " WHERE pcsf.constatationServiceFait.numeroDossier = :numDossierCSF ";
        try {
            Query query = this.getSession().createQuery(queryString).setParameter("numDossierCSF", numDossierCSF);
            Object[] result = (Object[]) query.uniqueResult();
            String statut = null;
            if (result[0] != null && result[1] != null) {
                long qteAcceptee = (long) result[0];
                long qteRecue = (long) result[1];
                if (qteAcceptee > 0 && qteAcceptee >= qteRecue) {
                    statut = Constantes.STATUT_ACCEPTATION_ACCEPTEE;
                } else if (qteAcceptee > 0 && qteAcceptee < qteRecue) {
                    statut = Constantes.STATUT_ACCEPTATION_ACCEPTEE_PART;
                } else if (qteAcceptee == 0 && qteRecue != 0) {
                    statut = Constantes.STATUT_ACCEPTATION_REFUSEE;
                }
            }
            return statut;
        } catch (Exception e) {
            String msgErreur = String.format(
                    "Echec de la récupération du statut d'acceptation de la CSF de numéro de dossier '%s' : %s",
                    numDossierCSF, e.getMessage());
            LOGGER.error(msgErreur);
            throw new TechnicalException(msgErreur, e);
        }
    }

    @Override
    public int countNbCSF(String login, String loginReceptionnaire, Date dateFrom, Date dateTo,
            StatutAvancementCSF statutAvancement, StatutCertificationCSF statutCertification, String statutAcceptation,
            String numCSF, String numCa) throws TechnicalException {
        try {
            StringBuilder sqlQuery =
                    new StringBuilder("SELECT COUNT(1) FROM (SELECT csf.id FROM constatation_service_fait csf");

            sqlQuery.append(" INNER JOIN profil profil ON profil.idServiceDepensier = csf.idServiceDepensier");
            sqlQuery.append(" INNER JOIN role role ON role.id = profil.idRole AND role.code = :role");
            sqlQuery.append(" INNER JOIN utilisateur currentUser ON currentUser.id = profil.idUtilisateur");
            sqlQuery.append(" AND currentUser.login = :login");

            if (loginReceptionnaire != null) {
                sqlQuery.append(" INNER JOIN utilisateur recepUser ON recepUser.id = csf.createur");
                sqlQuery.append(" AND recepUser.login = :loginReceptionnaire");
            }

            if (numCa != null) {
                sqlQuery.append(" INNER JOIN commande_achat ca ON ca.id = csf.idCA AND ca.idSAP = :numCa");
            }

            if (statutAcceptation != null && (Constantes.STATUT_ACCEPTATION_ACCEPTEE.equals(statutAcceptation)
                    || Constantes.STATUT_ACCEPTATION_ACCEPTEE_PART.equals(statutAcceptation)
                    || Constantes.STATUT_ACCEPTATION_REFUSEE.equals(statutAcceptation))) {
                sqlQuery.append(" INNER JOIN poste_constatation_service_fait pcsf ON csf.id = pcsf.idCSF");
            }

            sqlQuery.append(" WHERE 1=1");
            if (dateFrom != null) {
                sqlQuery.append(" AND csf.dateReception >= :dateFrom");
            }

            if (dateTo != null) {
                sqlQuery.append(" AND csf.dateReception <= :dateTo");
            }

            if (statutAvancement != null) {
                sqlQuery.append(" AND csf.statutAvancement = :statutAvancement");
            }

            if (statutCertification != null) {
                sqlQuery.append(" AND csf.statutCertification = :statutCertification");
            }

            if (numCSF != null) {
                sqlQuery.append(" AND csf.numeroDossier = :numCSF");
            }

            sqlQuery.append(" GROUP BY csf.id");

            if (statutAcceptation != null) {
                switch (statutAcceptation) {
                case Constantes.STATUT_ACCEPTATION_ACCEPTEE:
                    sqlQuery.append(" HAVING SUM(pcsf.quantiteAcceptee) > 0");
                    sqlQuery.append(" AND SUM(pcsf.quantiteAcceptee) = SUM(pcsf.quantiteRecue)");
                    break;
                case Constantes.STATUT_ACCEPTATION_ACCEPTEE_PART:
                    sqlQuery.append(" HAVING SUM(pcsf.quantiteAcceptee) > 0");
                    sqlQuery.append(" AND SUM(pcsf.quantiteAcceptee) < SUM(pcsf.quantiteRecue)");
                    break;
                case Constantes.STATUT_ACCEPTATION_REFUSEE:
                    sqlQuery.append(" HAVING SUM(pcsf.quantiteAcceptee) = 0 AND SUM(pcsf.quantiteRecue) > 0");
                    break;
                default:
                    break;
                }
            }
            sqlQuery.append(") resultsCsf");

            SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());

            query.setParameter("login", login);
            query.setParameter("role", Constantes.ROLE_RECEPTIONNAIRE);
            if (loginReceptionnaire != null) {
                query.setParameter("loginReceptionnaire", loginReceptionnaire.replaceAll("'", "\'"));
            }
            if (numCa != null) {
                query.setParameter("numCa", numCa);
            }
            if (dateFrom != null) {
                query.setParameter("dateFrom", dateFrom);
            }
            if (dateTo != null) {
                query.setParameter("dateTo", dateTo);
            }
            if (statutAvancement != null) {
                query.setParameter("statutAvancement", statutAvancement.displayName().replaceAll("'", "\'"));
            }
            if (statutCertification != null) {
                query.setParameter("statutCertification", statutCertification.displayName().replaceAll("'", "\'"));
            }
            if (numCSF != null) {
                query.setParameter("numCSF", numCSF);
            }
            BigInteger nbCSF = (BigInteger) query.uniqueResult();
            return nbCSF.intValueExact();
        } catch (Exception e) {
            String msgErreur =
                    String.format("Echec du calcul du nombre de CSFs répondant aux critères : %s.", e.getMessage());
            LOGGER.error(msgErreur);
            throw new TechnicalException(msgErreur, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ConstatationServiceFait> searchCSFByCriteria(String login, String loginReceptionnaire, Date dateFrom,
            Date dateTo, StatutAvancementCSF statutAvancement, StatutCertificationCSF statutCertification,
            String statutAcceptation, String numCSF, String numCa, int nbMaxResults) throws TechnicalException {

        try {
            StringBuilder sqlQuery = new StringBuilder("SELECT csf.* FROM constatation_service_fait csf");
            sqlQuery.append(" INNER JOIN profil profil ON profil.idServiceDepensier = csf.idServiceDepensier");
            sqlQuery.append(" INNER JOIN role role ON role.id = profil.idRole AND role.code = :role");
            sqlQuery.append(" INNER JOIN utilisateur currentUser ON currentUser.id = profil.idUtilisateur");
            sqlQuery.append(" AND currentUser.login = :login");

            if (loginReceptionnaire != null) {
                sqlQuery.append(" INNER JOIN utilisateur recepUser ON recepUser.id = csf.createur");
                sqlQuery.append(" AND recepUser.login = :loginReceptionnaire");
            }

            if (numCa != null) {
                sqlQuery.append(" INNER JOIN commande_achat ca ON ca.id = csf.idCA AND ca.idSAP = :numCa");
            }

            if (statutAcceptation != null && (Constantes.STATUT_ACCEPTATION_ACCEPTEE.equals(statutAcceptation)
                    || Constantes.STATUT_ACCEPTATION_ACCEPTEE_PART.equals(statutAcceptation)
                    || Constantes.STATUT_ACCEPTATION_REFUSEE.equals(statutAcceptation))) {
                sqlQuery.append(" INNER JOIN poste_constatation_service_fait pcsf ON csf.id = pcsf.idCSF");
            }

            sqlQuery.append(" WHERE 1=1");
            if (dateFrom != null) {
                sqlQuery.append(" AND csf.dateReception >= :dateFrom");
            }

            if (dateTo != null) {
                sqlQuery.append(" AND csf.dateReception <= :dateTo");
            }

            if (statutAvancement != null) {
                sqlQuery.append(" AND csf.statutAvancement = :statutAvancement");
            }

            if (statutCertification != null) {
                sqlQuery.append(" AND csf.statutCertification = :statutCertification");
            }

            if (numCSF != null) {
                sqlQuery.append(" AND csf.numeroDossier = :numCSF");
            }

            // Pas besoin de grouper sur toutes les colonnes de CSF car mysql
            // est permissif
            sqlQuery.append(" GROUP BY csf.id");

            if (statutAcceptation != null) {
                switch (statutAcceptation) {
                case Constantes.STATUT_ACCEPTATION_ACCEPTEE:
                    sqlQuery.append(" HAVING SUM(pcsf.quantiteAcceptee) > 0");
                    sqlQuery.append(" AND SUM(pcsf.quantiteAcceptee) = SUM(pcsf.quantiteRecue)");
                    break;
                case Constantes.STATUT_ACCEPTATION_ACCEPTEE_PART:
                    sqlQuery.append(" HAVING SUM(pcsf.quantiteAcceptee) > 0");
                    sqlQuery.append(" AND SUM(pcsf.quantiteAcceptee) < SUM(pcsf.quantiteRecue)");
                    break;
                case Constantes.STATUT_ACCEPTATION_REFUSEE:
                    sqlQuery.append(" HAVING SUM(pcsf.quantiteAcceptee) = 0 AND SUM(pcsf.quantiteRecue) > 0");
                    break;
                default:
                    break;
                }
            }

            sqlQuery.append(" ORDER BY csf.dateReception DESC");
            sqlQuery.append(" LIMIT :nbMaxResults");

            SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());
            query.addEntity(ConstatationServiceFait.class);

            query.setParameter("nbMaxResults", nbMaxResults);
            query.setParameter("login", login);
            query.setParameter("role", Constantes.ROLE_RECEPTIONNAIRE);
            if (loginReceptionnaire != null) {
                query.setParameter("loginReceptionnaire", loginReceptionnaire.replaceAll("'", "\'"));
            }
            if (numCa != null) {
                query.setParameter("numCa", numCa);
            }
            if (dateFrom != null) {
                query.setParameter("dateFrom", dateFrom);
            }
            if (dateTo != null) {
                query.setParameter("dateTo", dateTo);
            }
            if (statutAvancement != null) {
                query.setParameter("statutAvancement", statutAvancement.displayName().replaceAll("'", "\'"));
            }
            if (statutCertification != null) {
                query.setParameter("statutCertification", statutCertification.displayName().replaceAll("'", "\'"));
            }
            if (numCSF != null) {
                query.setParameter("numCSF", numCSF);
            }

            List<ConstatationServiceFait> results = query.list();
            return results;
        } catch (Exception e) {
            String msgErreur =
                    String.format("Echec de la récupération des CSFs répondant aux critères : %s.", e.getMessage());
            LOGGER.error(msgErreur);
            throw new TechnicalException(msgErreur, e);
        }
    }

    @Override
    public boolean isAuthorizedOnCsf(String login, ConstatationServiceFait csf) {
        try {
            StringBuilder sqlQuery = new StringBuilder("SELECT 1 FROM constatation_service_fait csf");
            sqlQuery.append(" INNER JOIN profil profil ON profil.idServiceDepensier = csf.idServiceDepensier");
            sqlQuery.append(" INNER JOIN role role ON role.id = profil.idRole AND role.code = :role");
            sqlQuery.append(" INNER JOIN utilisateur currentUser ON currentUser.id = profil.idUtilisateur");
            sqlQuery.append(" AND currentUser.login = :login");
            sqlQuery.append(" WHERE csf.numeroDossier = :numeroDossier LIMIT 1");
            SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());
            query.setParameter("login", login);
            query.setParameter("role", Constantes.ROLE_RECEPTIONNAIRE);
            query.setParameter("numeroDossier", csf.getNumeroDossier());
            return query.uniqueResult() != null;
        } catch (Exception e) {
            String msgErreur =
                    String.format("Echec de la récupération de l'habilitation de l'utilisateur %s sur la csf '%s'.",
                            login, csf.getNumeroDossier());
            LOGGER.error(msgErreur);
            return false;
        }
    }
// alpha ajout**//
    private List<ConstatationServiceFait> listNotDeletableCSF ;
    
    public List<ConstatationServiceFait> getListNotDeletableCSF() {
		return listNotDeletableCSF;
	}
	public void setListNotDeletableCSF(List<ConstatationServiceFait> listNotDeletableCSF) {
		this.listNotDeletableCSF = listNotDeletableCSF;
	}
	
    @SuppressWarnings("unchecked")  
	@Override
	public List<ConstatationServiceFait> listNotDeletableCSF(int nbJoursDepuisModif,
			int nbJoursDepuisModifStatutTerminal) {
		Session session = this.getSession();
        List<ConstatationServiceFait> csf = session.createQuery("from ConstatationServiceFait where  not ((statutAvancement = :status AND "
        		+ " dateMiseAjourSap is not NULL AND :nbJoursDepuisModifStatutTerminal < DATEDIFF(current_date(),dateMiseAjourSap))"
        		+ " OR :nbJoursDepuisModif < DATEDIFF(current_date(),dateModification))").setParameter("status", StatutAvancementCSF.TraitementTermine)
        		.setParameter("nbJoursDepuisModifStatutTerminal",nbJoursDepuisModifStatutTerminal)
        		.setParameter("nbJoursDepuisModif",nbJoursDepuisModif).list();
        listNotDeletableCSF = csf;
		return csf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConstatationServiceFait> listConstatationServiceFaits() {
		return this.constatationServiceFaitDAO.getAll();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CommandeAchat> listCAfromCSF() {
		//List<ConstatationServiceFait> cob = this.listNotDeletableCSF(365,30) ;
		Session session = this.getSession();
        List<CommandeAchat> ca = session.createQuery("select csf.commandeAchat from ConstatationServiceFait csf where csf IN :listcsf ").
        		setParameterList("listcsf", listNotDeletableCSF).list();
                return ca;	}
	@Override
	@SuppressWarnings("unchecked")
	public List<ConstatationServiceFait> listDeletableCSF(List<ConstatationServiceFait> csfa) {
		Session session = this.getSession();
        List<ConstatationServiceFait> da = session.createQuery("select distinct csf from ConstatationServiceFait csf "
        		+ "left join fetch csf.piecesJointes "
        		+ "left join fetch csf.posteConstatationServiceFait pcsf "
        		+"left join fetch pcsf.posteCommandeAchat "
        		+"left join fetch pcsf.pieceJointe "
        		+ " where csf not in :liste")
        		.setParameterList("liste", csfa).list();
        
        return da;	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ConstatationServiceFait> CSFLieesCA(List<CommandeAchat> liste) {
		Session session =this.getSession();
		List<ConstatationServiceFait> csf2 = session.createQuery("from ConstatationServiceFait csf where csf.commandeAchat  in :liste")
        		.setParameterList("liste",liste).list();
        
        return csf2 ;
	}
	@Override
	public void delete(List<ConstatationServiceFait> liste) {
		for(int i=0;i<liste.size();i++)
		{
			try {
				this.csfsAHDAO.deleteByCsf(liste.get(i).getId());
			} catch (TechnicalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.delete(liste.get(i));
		}
	}
}
