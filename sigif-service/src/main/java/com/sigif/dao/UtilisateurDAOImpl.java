
package com.sigif.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Utilisateur;
import com.sigif.util.Constantes;

/**
 * Implémentation de la classe d'accès aux données utilisateur.
 *
 */
@Repository("utilisateurDAO")
public class UtilisateurDAOImpl extends AbstractDAOImpl<Utilisateur> implements UtilisateurDAO {
	/**
	 * Loggueur.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurDAOImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	public int getUserHabilitation(String login) throws TechnicalException {
		boolean demandeur = false, receptionnaire = false;

		try {
			List<String> roles = this.getSession()
					.createQuery(
							"select r.code FROM Profil p left outer join p.role r where p.utilisateur.login = :userLogin")
					.setParameter("userLogin", login).list();
			for (String role : roles) {
				if (role.equals(Constantes.ROLE_DEMANDEUR)) {
					demandeur = true;
				} else if (role.equals(Constantes.ROLE_RECEPTIONNAIRE)) {
					receptionnaire = true;
				}
			}
			if (demandeur && receptionnaire) {
				return Constantes.HABILITATION_DEMAND_AND_RECEP;
			} else if (!demandeur && receptionnaire) {
				return Constantes.HABILITATION_ONLY_RECEP;
			} else if (demandeur && !receptionnaire) {
				return Constantes.HABILITATION_ONLY_DEMAND;
			} else {
				return Constantes.HABILITATION_NEITHER_DEMAND_NOR_RECEP;
			}
		} catch (Exception e) {
			String msgErreur = String
					.format("Erreur lors de la récuperation de la liste des rôles de l'utilisateur '%s'", login);
			LOGGER.error(msgErreur);
			throw new TechnicalException(msgErreur, e);
		}
	}

	@Override
	public int checkIdentity(String login) throws TechnicalException {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("login", login));

		Utilisateur user = (Utilisateur) criteria.uniqueResult();
		try {
			if (user != null) {
				if (user.isAvecCompte() && user.isCompteActif() && user.getStatut().equals(Statut.actif)) {
					return Constantes.CHECK_IDENTITY_OK;
				} else if (user.getStatut().equals(Statut.inactif)) {
					return Constantes.CHECK_IDENTITY_INACTIVE_ACCOUNT;
				} else if (!user.isAvecCompte() || !user.isCompteActif()) {
					return Constantes.CHECK_IDENTITY_INVALID_ACCOUNT;
				}
			}
		} catch (Exception e) {
			String msgErreur = String.format("Erreur lors de la vérification de l'état du compte de l'utilisateur '%s'",
					login);
			LOGGER.error(msgErreur);
			throw new TechnicalException(msgErreur, e);
		}
		return 0;
	}

	@Override
	public Utilisateur getUserByLogin(String login) throws TechnicalException {

		try {
			Criteria criteria = createCriteria();
			criteria.add(Restrictions.eq("login", login));
			return (Utilisateur) criteria.uniqueResult();
		} catch (Exception e) {
			throw new TechnicalException(
					"Erreur lors de la recuperation de l'utilisateur unique de login '" + login + "'.");
		}
	}

	@Override
	public List<Utilisateur> getRequestersByCriteriaOrderByNomPrenom(String login, String ministry,
			String spendingService, String role, String statut) throws TechnicalException {
		try {
			List<Utilisateur> userList = null;
			// si le filtre porte sur le service dépensier ou que le login n'est
			// pas renseigné,
			// il n'est pas nécessaire de filtrer les résultats en fonction du
			// login
			if (login != null && spendingService == null) {
				userList = this.getRequestersByCriteriaWithLogin(login, ministry, role, statut);
			} else {
				userList = this.getRequestersByCriteriaWithoutLogin(ministry, spendingService, role, statut);
			}
			return userList;
		} catch (Exception e) {
			String msgErreur = String.format(
					"Erreur lors de la récupération de la liste des demandeurs possibles "
							+ "pour le login '%s', le ministère '%s', le service dépensier '%s', le rôle '%s' et le statut '%s'",
					login, ministry, spendingService, role, statut);
			LOGGER.error(msgErreur);
			throw new TechnicalException(msgErreur, e);
		}
	}

	/**
	 * Retourne les demandeurs ayant des habilitations communes avec un
	 * utilisateur donné. Les résultats peuvent être filtrés en fonction d'un
	 * rôle, d'un ministère et du statut des demandeurs.
	 * 
	 * @param login
	 *            Login de l'utilisateur de référence
	 * @param ministry
	 *            Code du ministère devant être commun dans les habilitations
	 * @param role
	 *            Code du rôle devant être commun dans les habilitations
	 * @param statut
	 *            Statut des demandeurs ('actif', 'inactif' ou 'all')
	 * @return les demandeurs ayant des habilitations communes avec
	 *         l'utilisateur donné
	 */
	@SuppressWarnings("unchecked")
	private List<Utilisateur> getRequestersByCriteriaWithLogin(String login, String ministry, String role,
			String statut) {
		// Utilisation d'un appel en SQL direct à cause du self join
		String sqlQuery = "SELECT distinct u.* FROM utilisateur uRef "
				+ "INNER JOIN profil prRef on uRef.id = prRef.idUtilisateur "
				+ "INNER JOIN role roleRef on roleRef.id = prRef.idRole "
				+ "INNER JOIN ministere mRef on mRef.id = prRef.idMinistere "
				+ "INNER JOIN service_depensier sdRef on sdRef.id = prRef.idServiceDepensier "
				+ "INNER JOIN utilisateur u "
				+ "INNER JOIN profil pr on u.id = pr.idUtilisateur AND pr.idRole = prRef.idRole "
				+ "INNER JOIN ministere m on m.id = pr.idMinistere AND m.id = mRef.id "
				+ "INNER JOIN service_depensier sd on sd.id = pr.idServiceDepensier AND sd.id = sdRef.id "
				+ "WHERE uRef.login = :login";

		if (role != null) {
			sqlQuery += " and roleRef.code = :role";
		}
		if (ministry != null) {
			sqlQuery += " and mRef.code = :ministry";
		}
		if (statut != null && !statut.equalsIgnoreCase("all")) {
			sqlQuery += " and u.statut = :statut";
		}

		sqlQuery += " order by u.nom asc, u.prenom asc";

		SQLQuery query = this.getSession().createSQLQuery(sqlQuery);
		query.addEntity(Utilisateur.class);

		query.setParameter("login", login);

		if (role != null) {
			query.setParameter("role", role);
		}
		if (ministry != null) {
			query.setParameter("ministry", ministry);
		}
		if (statut != null && !"all".equalsIgnoreCase(statut)) {
			// utilisation du string et pas de l'enum car la requête est en SQL
			// direct
			query.setParameter("statut", statut);
		}
		List<Utilisateur> results = query.list();
		return results;
	}

	/**
	 * Retourne les demandeurs ayant des habilitations pour un ministère, un
	 * service dépensier, un rôle et un statut.
	 * 
	 * @param ministry
	 *            Code du ministère
	 * @param spendingService
	 *            Code du service dépensier
	 * @param role
	 *            Code du rôle devant être commun dans les habilitations
	 * @param statut
	 *            Statut des demandeurs ('actif', 'inactif' ou 'all')
	 * @return les demandeurs ayant des habilitations pour un ministère, un
	 *         service dépensier, un rôle et un statut
	 */
	@SuppressWarnings("unchecked")
	private List<Utilisateur> getRequestersByCriteriaWithoutLogin(String ministry, String spendingService, String role,
			String statut) {
		String queryString = "SELECT DISTINCT user FROM Profil pr JOIN pr.utilisateur user LEFT JOIN pr.ministere minis "
				+ "LEFT JOIN pr.serviceDepensier servDep WHERE 1=1";

		if (ministry != null) {
			queryString += " and minis.code = :ministry";

			if (spendingService != null) {
				queryString += " and servDep.code = :spendingService";
			}
		}

		if (role != null) {
			queryString += " and pr.role.code = :role";
		}
		if (statut != null && !statut.equalsIgnoreCase("all")) {
			queryString += " and user.statut = :statut";
		}

		queryString += " order by pr.utilisateur.nom asc, pr.utilisateur.prenom asc";

		Query query = this.getSession().createQuery(queryString);

		if (ministry != null) {
			query.setParameter("ministry", ministry);

			if (spendingService != null) {
				query.setParameter("spendingService", spendingService);
			}
		}

		if (role != null) {
			query.setParameter("role", role);
		}
		if (statut != null && !"all".equalsIgnoreCase(statut)) {
			query.setParameter("statut", Statut.valueOf(statut));
		}

		List<Utilisateur> userList = query.list();
		return userList;
	}

	@Override
	public int countNb() {
		Criteria c = getSession().createCriteria(Utilisateur.class, "user");
		// remplace le count(distinct(id))
		c.setProjection(Projections.distinct(Projections.countDistinct("id")));
		Long nbUser = (Long) c.uniqueResult();
		return Math.toIntExact(nbUser);
	}

	@Override
	public int countUsersByLoginOrName(String login, String nom) {
		String queryString = "Select COUNT(*) FROM Utilisateur WHERE 1=1";

		if (!StringUtils.isBlank(login)) {
			queryString += " and login = :login ";
		}
		if (!StringUtils.isBlank(nom)) {
			queryString += " and nom = :nom ";
		}
		Query query = this.getSession().createQuery(queryString);

		if (!StringUtils.isBlank(login)) {
			query.setParameter("login", login);
		}
		if (!StringUtils.isBlank(nom)) {
			query.setParameter("nom", nom);
		}
		Long nbUser = (Long) query.uniqueResult();
		return Math.toIntExact(nbUser);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Utilisateur> getUsersByLoginOrName(String login, String nom, int nbMaxResults) {
		String queryString = "SELECT DISTINCT user FROM Utilisateur as user WHERE 1=1";

		if (!StringUtils.isBlank(login)) {
			queryString += " and login = :login ";
		}
		if (!StringUtils.isBlank(nom)) {
			queryString += " and nom = :nom ";
		}

		queryString += " order by nom asc, prenom asc";

		Query query = this.getSession().createQuery(queryString);

		if (!StringUtils.isBlank(login)) {
			query.setParameter("login", login);
		}
		if (!StringUtils.isBlank(nom)) {
			query.setParameter("nom", nom);
		}
		query.setMaxResults(nbMaxResults);
		List<Utilisateur> userList = query.list();
		return userList;
	}

	@Override
	public Utilisateur getInfoUserByLogin(String login) throws TechnicalException {
		try {
			Utilisateur user = this.getUniqueByParam("login", login);
			return user;
		} catch (Exception e) {
			String msg = String.format(
					"Erreur lors de la lecture des informations de l'utilisateur dont le login est '%s' : %s", login,
					e.getMessage());
			throw new TechnicalException(msg, e);
		}
	}
}
