
package com.sigif.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.app.ApplicationContextProvider;
import com.sigif.dao.ServiceDepensierDAO;
import com.sigif.dao.UtilisateurDAO;
import com.sigif.dto.ProfilDTO;
import com.sigif.dto.UtilisateurDTO;
import com.sigif.enumeration.RetourConnexion;
import com.sigif.enumeration.Statut;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.ServiceDepensier;
import com.sigif.modele.Utilisateur;
import com.sigif.util.Constantes;
import com.sigif.util.FunctionsUtils;
import com.sigif.util.GenerationUtil;

/**
 * Implémentation de la classe de service utilisateur.
 * 
 *
 */
@Service("utilisateurService")
@Transactional
public class UtilisateurServiceImpl extends AbstractServiceImpl<Utilisateur, UtilisateurDTO>
		implements UtilisateurService {

	/**
	 * Loggueur.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

	@Override
	protected UtilisateurDAO getDao() {
		return (UtilisateurDAO) super.getDao();
	}

	/**
	 * Dao pour les services Dep.
	 */
	@Autowired
	private ServiceDepensierDAO serviceDepDao;

	@Override
	public void addUtilisateur(UtilisateurDTO user) throws TechnicalException {
		try {
			this.getDao().save(toEntite(user));
		} catch (Exception e) {
			throw new TechnicalException("Impossible d'ajouter l'utilisateur : " + e.getMessage(), e);
		}
	}

	@Override
	public void updateUtilisateur(UtilisateurDTO user) throws TechnicalException {
		try {
			this.getDao().merge(toEntite(user));
		} catch (Exception e) {
			throw new TechnicalException("Impossible de mettre à jour l'utilisateur : " + e.getMessage(), e);
		}
	}

	@Override
	public void deleteUtilisateur(int id) throws TechnicalException {
		try {
			this.getDao().deleteById(id);
		} catch (Exception e) {
			throw new TechnicalException("Impossible de supprimer l'utilisateur d'id '" + id + "' : " + e.getMessage(),
					e);
		}
	}

	@Override
	public boolean savePassword(String login, String password, boolean isGenerated) throws ApplicationException {
		login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOG);
		boolean resultat = false;
		try {
			// on récupère l'utilisateur dont on veut changer le mot de passe
			Utilisateur user = this.getDao().getUniqueByParam("login", login);
			if (user != null) {
				// Encryptage du mot de passe généré
				String newEncodedPassword = FunctionsUtils.getCryptedPassword(password);
				user.setPassword(newEncodedPassword);
				user.setMotDePasseGenere(isGenerated);
				// on met à jour en base.
				this.getDao().merge(user);
				resultat = true;
			}
		} catch (Exception e) {
			LOG.error(String.format(
					"Le cryptage et la sauvegarde du nouveau mot de passe ont échoué pour l'utilisateur '%s' : %s",
					login, e.getMessage()));
		}
		return resultat;
	}

	@Override
	public boolean sendNewPassword(String login) throws ApplicationException {
		login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOG);
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		SendMailService mailService = (SendMailService) applicationContext.getBean("sendMailService");
		try {
			// Génération de mot de passe
			String newPassword = GenerationUtil.genereRandomPassword();

			// Enregistrement de mot de passe
			if (!savePassword(login, newPassword, true)) {
				throw new TechnicalException("La sauvegarde du mot de passe a échoué");
			}

			// Envoi d'E-mail
			Utilisateur user = this.getDao().getUniqueByParam("login", login);
			//mailService.sendNewPasswordEmail(user, newPassword);
			return true;
		} catch (Exception e) {
			LOG.error(String.format(
					"La génération et la sauvegarde du nouveau mot de passe ont échoué pour l'utilisateur '%s' : %s",
					login, e.getMessage()));
			return false;
		}
	}

	@Override
	public String getMail(String login) throws ApplicationException, TechnicalException {
		login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOG);
		try {
			String couriel = "";
			Utilisateur user = this.getDao().getUniqueByParam("login", login);
			if (user != null) {
				couriel = user.getCourriel();
				LOG.debug(String.format("Le courriel '%s' a été trouvé pour l'utilisateur de login '%s'", couriel,
						login));
			} else {
				LOG.debug(String.format("Aucun utilisateur n'a été trouvé pour le login '%s'", login));
			}
			return couriel;
		} catch (Exception e) {
			LOG.error(String.format("La recherche de courriel a échoué pour l'utilisateur '%s' : %s", login,
					e.getMessage()));
			throw new TechnicalException(
					String.format("La recherche de courriel a échoué pour l'utilisateur '%s'", login), e);
		}
	}

	@Override
	public String checkLoginPassword(String login, String mdpClear) throws TechnicalException, ApplicationException {
		login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOG);
		mdpClear = FunctionsUtils.checkNotNullNotEmptyAndTrim("mdpClear", mdpClear, LOG);
		Utilisateur user;
		String mdp = "";
		mdp = FunctionsUtils.getCryptedPassword(mdpClear);

		Map<String, Object> criteres = new HashMap<String, Object>();
		criteres.put("login", login);
		criteres.put("password", mdp);
		criteres.put("avecCompte", true);
		criteres.put("statut", Statut.actif);
		criteres.put("compteActif", true);

		try {
			user = this.getDao().getUniqueByParams(criteres);
		} catch (Exception e) {
			throw new TechnicalException("Erreur lors de la vérification du mot de passe et du login de l'utilisateur",
					e);
		}

		if (user != null) {
			if (user.isMotDePasseGenere()) {
				return RetourConnexion.changePassword.toString();
			} else {
				return RetourConnexion.OK.toString();
			}
		}

		return RetourConnexion.KO.toString();
	}

	@Override
	public boolean checkRoleExist(UtilisateurDTO userDTO, String role) {
		for (ProfilDTO profil : userDTO.getProfils()) {
			if (profil.getRole().getDesignation().equals(role)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int checkIdentity(String login) throws TechnicalException, ApplicationException {
		login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOG);
		return this.getDao().checkIdentity(login);
	}

	@Override
	public int getUserHabilitation(String login) throws TechnicalException, ApplicationException {
		login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOG);

		return getDao().getUserHabilitation(login);
	}

	@Override
	public Map<String, String> getMapOfUserByLogin(String login) throws TechnicalException, ApplicationException {
		final int radixBaseDix = 10;
		login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOG);
		// Recuperation du role
		int idRole = this.getDao().checkIdentity(login);
		// Recuperer l'utilisateur par son login
		Utilisateur user = this.getDao().getUserByLogin(login);
		Map<String, String> userInfosByMap = new HashMap<String, String>();

		userInfosByMap.put("civility", user.getCivilite().toString());
		userInfosByMap.put("firstname", user.getPrenom());
		userInfosByMap.put("lastname", user.getNom());
		userInfosByMap.put("email", user.getCourriel());
		userInfosByMap.put("roles", Integer.toString(idRole, radixBaseDix));
		return userInfosByMap;
	}

	@Override
	public Map<String, String> getRequestersByCriteria(String login, String spendingService, String role,
			String ministry, String status, boolean excludeLoginFromResult)
			throws TechnicalException, ApplicationException {
		// Aucun des critères n'est obligatoire donc on se contente des les
		// trimmer
		login = FunctionsUtils.trimOrNullifyString(login);
		ministry = FunctionsUtils.trimOrNullifyString(ministry);
		spendingService = FunctionsUtils.trimOrNullifyString(spendingService);
		role = FunctionsUtils.trimOrNullifyString(role);
		status = FunctionsUtils.trimOrNullifyString(status);
		if (status != null && !"actif".equals(status) && !"inactif".equals(status) && !"all".equals(status)) {
			throw new ApplicationException(String.format(
					"Le statut '%s' n'est pas valide pour la recherche. Utilisez 'actif', 'inactif' ou 'all'.",
					status));
		}

		if (spendingService != null) {
			// Lecture du ministère correspondant au service dépensier
			ServiceDepensier serviceDepensier = this.serviceDepDao.getUniqueByParam("code", spendingService);
			if (serviceDepensier == null) {
				String msgErreur = String.format("Il n'existe aucun service dépensier dont le code est '%s'",
						spendingService);
				LOG.error(msgErreur);
				throw new ApplicationException(msgErreur);
			} else if (ministry != null && !serviceDepensier.getMinistere().getCode().equals(ministry)) {
				String msgServiceIncorrect = String.format("Le service dépensier '%s' n'est pas lié au ministère '%s'",
						spendingService, ministry);
				LOG.error(msgServiceIncorrect);
				throw new ApplicationException(msgServiceIncorrect);
			} else {
				ministry = serviceDepensier.getMinistere().getCode();
			}
		}

		List<Utilisateur> demandeurs = this.getDao().getRequestersByCriteriaOrderByNomPrenom(login, ministry,
				spendingService, role, status);

		Map<String, String> userListMap = null;
		if (demandeurs != null && !demandeurs.isEmpty()) {
			userListMap = new LinkedHashMap<String, String>();
			for (Utilisateur u : demandeurs) {
				if (!excludeLoginFromResult || !u.getLogin().equals(login)) {
					userListMap.put(u.getLogin(),
							String.format("%s %s %s", u.getCivilite(), u.getPrenom(), u.getNom()));
				}
			}
		}

		return userListMap;
	}

	@Override
	public Map<String, String> getRequestersByRoleBySpendingService(String login, String spendingService, String role)
			throws TechnicalException, ApplicationException {
		login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOG);
		spendingService = FunctionsUtils.checkNotNullNotEmptyAndTrim("spendingService", spendingService, LOG);
		role = FunctionsUtils.checkNotNullNotEmptyAndTrim("role", role, LOG);

		Map<String, String> userListMap = this.getRequestersByCriteria(login, spendingService, role, null, "actif",
				true);
		return userListMap;
	}

	@Override
	public int countNb() {
		return this.getDao().countNb();
	}

	@Override
	public int countNbUserByLoginOrName(String login, String nom) {
		return getDao().countUsersByLoginOrName(login, nom);
	}

	@Override
	public Map<String, String[]> getMapOfUserByLoginOrName(String login, String nom, int nbMaxResults)
			throws TechnicalException, ApplicationException {
		try {
			List<Utilisateur> rsltQuery = getDao().getUsersByLoginOrName(login, nom, nbMaxResults);

			LOG.info("rsltQuery1 : " + rsltQuery.size());

			Map<String, String[]> rsltToMap = null;
			if (rsltQuery != null && !rsltQuery.isEmpty()) {
				LOG.info("rsltQuery2 : " + rsltQuery.size());
				rsltToMap = new HashMap<String, String[]>();
				int rsltSize = rsltQuery.size();
				String[] logins = new String[rsltSize];
				String[] noms = new String[rsltSize];
				String[] statuts = new String[rsltSize];
				String[] cptActifs = new String[rsltSize];
				String[] withCpts = new String[rsltSize];
				String[] pwdGeneres = new String[rsltSize];
				int cpt = 0;
				for (Utilisateur user : rsltQuery) {
					LOG.info("Utilisateur  : " + user.getLogin());
					logins[cpt] = user.getLogin();
					noms[cpt] = user.getPrenom() + " " + user.getNom();
					statuts[cpt] = user.getStatut().toString();
					cptActifs[cpt] = user.isCompteActif() + "";
					withCpts[cpt] = user.isAvecCompte() + "";
					pwdGeneres[cpt] = user.isMotDePasseGenere() + "";
					cpt++;
				}
				rsltToMap.put(Constantes.KEY_MAP_LOGIN, logins);
				rsltToMap.put(Constantes.KEY_MAP_NOM, noms);
				rsltToMap.put(Constantes.KEY_MAP_STATUT, statuts);
				rsltToMap.put(Constantes.KEY_MAP_COMPTE_ACTIF, cptActifs);
				rsltToMap.put(Constantes.KEY_MAP_AVEC_COMPTE, withCpts);
				rsltToMap.put(Constantes.KEY_MAP_PASSWORD_GENERE, pwdGeneres);

			}
			return rsltToMap;

		} catch (Exception e) {
			String errorMessage = "Erreur lors de la recherche des utilisataurs";
			LOG.error(errorMessage);
			throw new TechnicalException(errorMessage, e);
		}
	}

	@Override
	public Map<String, String> getInfoUserByLogin(String login) throws TechnicalException, ApplicationException {
		System.out.println("login " + login);
		Utilisateur user = getDao().getInfoUserByLogin(login);
		Map<String, String> userListMap = new HashMap<>();

		userListMap.put(Constantes.KEY_MAP_LOGIN, login);
		userListMap.put(Constantes.KEY_MAP_NOM, user.getPrenom() + " " + user.getNom());
		userListMap.put(Constantes.KEY_MAP_STATUT, user.getStatut().toString());
		userListMap.put(Constantes.KEY_MAP_COMPTE_ACTIF, user.isCompteActif() ? "Actif" : "Inactif");
		userListMap.put(Constantes.KEY_MAP_AVEC_COMPTE, user.isAvecCompte() ? "Actif" : "Inactif");
		userListMap.put(Constantes.KEY_MAP_PASSWORD_GENERE, user.isMotDePasseGenere() ? "Oui" : "Non");
		return userListMap;
	}

	@Override
	public void activeDesactiveUtilisateur(String login) throws TechnicalException {
		try {
			Utilisateur user = getDao().getInfoUserByLogin(login);
			if (user.isCompteActif()) {
				user.setCompteActif(false);
			} else {
				user.setCompteActif(true);
			}

			this.getDao().merge(user);
		} catch (Exception e) {
			throw new TechnicalException(
					"Impossible d'activer ou de désactiver l'utilisateur à jour l'utilisateur : " + e.getMessage(), e);
		}
	}

	
}
