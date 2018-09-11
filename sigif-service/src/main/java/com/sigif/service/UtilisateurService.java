package com.sigif.service;

import java.util.Map;

import com.sigif.dto.UtilisateurDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Utilisateur;

/**
 * Service d'accès aux utilisateurs.
 * 
 * @author Mickael Beaupoil
 *
 */
public interface UtilisateurService extends AbstractService<UtilisateurDTO> {

	/**
	 * Ajoute en base un nouveau utilisateur.
	 * 
	 * @param user
	 *            le DTO de l'utilisateur à ajouter
	 * @throws TechnicalException
	 *             si l'ajout échoue pour une raison technique (connexion
	 *             perdue, ...)
	 */
	void addUtilisateur(UtilisateurDTO user) throws TechnicalException;

	/**
	 * Compte le nombre d'utilisateurs en base (pour tester que l'appli est
	 * correctement configurée).
	 * 
	 * @return le nombre d'utilisateurs en base
	 */
	int countNb();

	/**
	 * Met à jour un utilisateur.
	 * 
	 * @param user
	 *            le DTO de l'utilisateur à mettre à jour
	 * @throws TechnicalException
	 *             si la mise à jour échoue pour une raison technique (connexion
	 *             perdue, ...)
	 */
	void updateUtilisateur(UtilisateurDTO user) throws TechnicalException;

	/**
	 * Supprime de la base de donnée un utilisateur par son ID.
	 * 
	 * @param id
	 *            id de l'utilisateur à supprimer
	 * @throws TechnicalException
	 *             si la suppression échoue pour une raison technique (connexion
	 *             perdue, ...)
	 */
	void deleteUtilisateur(int id) throws TechnicalException;

	/**
	 * Envoie le nouveau mot de passe généré et enregistré à l'utilisateur de
	 * login 'login'.
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * @return true si le nouveau mot de passe a été généré et enregistré, false
	 *         en cas d'erreur technique
	 * @throws ApplicationException
	 *             Si le login est vide ou null
	 * @author Meissa Beye
	 * @since 12-05-2017
	 */
	boolean sendNewPassword(String login) throws ApplicationException;

	/**
	 * Récupération de l'adresse e-mail d'un utilisateur à partir de son login.
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * @return couriel Courriel de l'utilisateur
	 * @throws TechnicalException
	 *             Si la recherche échoue pour une raison technique
	 * @throws ApplicationException
	 *             Si le login est vide ou null
	 * @author Meissa Beye
	 * @since 15-05-2017
	 */
	String getMail(String login) throws TechnicalException, ApplicationException;

	/**
	 * Enregistre le nouveau mot de passe saisi par l'utilisateur ou généré.
	 * 
	 * @param login
	 *            Identifiant de l'utilisateur
	 * @param password
	 *            Nouveau Mot de passe saisi par l'utilisateur
	 * @param isGenerated
	 *            booléen indiquant si le mot de passe est généré ou non
	 * @return true si la génération et la sauvegarde ont réussi, false si une
	 *         Exception s'est produite
	 * @author Meissa Beye
	 * @since 15-05-2017
	 * @throws ApplicationException
	 *             Si le login est vide ou null
	 */
	boolean savePassword(String login, String password, boolean isGenerated) throws ApplicationException;

	/**
	 * Vérifie que l'utilisateur possède un rôle donné.
	 * 
	 * @param userDTO
	 *            DTO de l'utilisateur vérifié
	 * @param role
	 *            Rôle que l'utilisateur doit avoir
	 * @return true si l'utilisateur a le rôle
	 * @author Mamadou Ndir 
	 * @since  15-05-2017
	 */
	boolean checkRoleExist(UtilisateurDTO userDTO, String role);

	/**
	 * Vérification que le login donné correspond à un compte valide.
	 * 
	 * @param login
	 *            le login à tester
	 * @throws TechnicalException
	 *             Si la recherche de l'utilisateur a échoué
	 * @throws ApplicationException
	 *             Si le login est vide ou null
	 * @return 0 si l'utilisateur n'existe pas, 1 si l'utilisateur a un compte
	 *         Sigif et son compte n'est pas désactivé (colonne compteActif), ni
	 *         inactif (colonne statut), 2 si l'utilisateur n'a pas de compte
	 *         Sigif ou il est désactivé 3 si l'utilisateur a un compte Sigif
	 *         non désactivé mais il a été supprimé (colonne statut à inactif)
	 * @author Mamadou Ndir 
	 * @since 15-05-2017
	 */
	int checkIdentity(String login) throws TechnicalException, ApplicationException;

	/**
	 * Vérification que le login et le mot de passe non crypté fournis
	 * correspondent à un utilisateur valide.
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * @param mdpClear
	 *            Mot de passe non crypté
	 * @return 'KO' si l'utilisateur n'existe pas ou n'a pas un compte valide,
	 *         'OK' si 'il a un compte valide, 'changePassword' si le compte
	 *         existe mais que le mot de passe courant est généré
	 * @throws TechnicalException
	 *             si le recherche échoue pour une raison technique (connexion
	 *             perdue par exemple)
	 * @throws ApplicationException
	 *             Si un des 2 paramètres est vide ou null
	 * @author Mamadou Ndir 
	 * @since 15-05-2017
	 */
	String checkLoginPassword(String login, String mdpClear) throws TechnicalException, ApplicationException;

	/**
	 * Récupère les habilitations de l'utilisateur correspondant au login.
	 * 
	 * @param login
	 *            Le Login de l'utilisateur
	 * @return Les habilitations de l'utilisateur correspondant au login.
	 * @throws TechnicalException
	 *             si la lecture des rôles échoue pour une raison technique
	 * @throws ApplicationException
	 *             Si le login est vide ou null
	 * @author Mamadou Ndir
	 *
	 */
	int getUserHabilitation(String login) throws TechnicalException, ApplicationException;

	/**
	 * Retourne sous forme de map les données suivante de l'utilisateur au login
	 * passé en param. key: civility - civilité de l'utilisateur <BR>
	 * key: firstname - prénom <BR>
	 * key: lastname -nom <BR>
	 * key: email - adresse mail de l'utilisateur <BR>
	 * key: roles - code des rôle de l'utilisateur (0, 1, 2, 3) <BR>
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * @return l'utilisateur lié au login passé en param sous forme de Map
	 * @throws TechnicalException
	 *             si la sélection échoue pour une raison technique (connexion
	 *             perdue, login non unique ...)
	 * @throws ApplicationException
	 *             Si le login est vide ou null
	 * 
	 */
	Map<String, String> getMapOfUserByLogin(String login) throws TechnicalException, ApplicationException;

	/**
	 * Retourne tous les demandeurs possibles répondant aux critères suivants
	 * :<BR>
	 * - login différent de celui en paramètre<BR>
	 * - habilité au service dépensier indiqué ou à tous les services dépensiers
	 * du ministère auquel appartient ce service<BR>
	 * - habilité avec le rôle indiqué pour le service dépensier ou son
	 * ministère indiqué.<BR>
	 * Note :<BR>
	 * - Tous les critères sont facultatifs, s'ils sont vides ou null, tous les
	 * demandeurs sont renvoyés sans filtrer.<BR>
	 * - La map contient le login de l'utilisateur en tant que clé et la
	 * concaténation "Civilité Prénom Nom" en tant que valeur<BR>
	 * - La map est ordonnée par nom puis prénom de l'utilisateur
	 * 
	 * @param login
	 *            Les demandeurs du résultat doivent avoir une habilitation à un
	 *            ministère ou à un service dépensier en commun (paramètre
	 *            utilisé seulement si les paramètres ministère et service
	 *            dépensier ne sont pas renseignés)
	 * @param spendingService
	 *            Code du service dépensier auquel doit être habilité le
	 *            demandeur (ne peut pas être vide)
	 * @param role
	 *            Code du rôle que doit posséder l'utilisateur pour le ministère
	 *            et le service dépensier (ou null pour ne pas vérifier les
	 *            rôles)
	 * @param ministry
	 *            Code du ministère
	 * @param status
	 *            Statut ("actif", "inactif" ou "all")
	 * @param excludeLoginFromResult
	 *            Indique s'il faut retirer des résultats l'utilisateur
	 *            correspondant au paramètre login
	 * @return tous les demandeurs possibles répondant aux critères ou null s'il
	 *         n'y a aucun résultat
	 * @throws TechnicalException
	 *             Si la recherche échoue pour une raison technique
	 * @throws ApplicationException
	 *             Si le service dépensier n'existe pas ou s'il n'est pas lié au
	 *             ministère indiqué
	 * @author Mickael Beaupoil
	 */
	Map<String, String> getRequestersByCriteria(String login, String spendingService, String role, String ministry,
			String status, boolean excludeLoginFromResult) throws TechnicalException, ApplicationException;

	/**
	 * Récupère la liste des demandeurs rattachés au même service dépensier que
	 * l'utilisateur de référence.
	 * 
	 * @param login
	 *            Login de l'utilisateur de référence
	 * 
	 * @param spendingService
	 *            Code du service dépensier choisi
	 * 
	 * @param role
	 *            Code du rôle
	 * 
	 * @return la liste des demandeurs rattachés au même service dépensier que
	 *         l'utilisateur de référence
	 * 
	 * @throws TechnicalException
	 *             si la recherche échoue pour une raison technique
	 * 
	 * @throws ApplicationException
	 *             Si un des paramètres est vide ou null
	 */
	Map<String, String> getRequestersByRoleBySpendingService(String login, String spendingService, String role)
			throws TechnicalException, ApplicationException;

	/**
	 * Récupère un map des infos de l'utilisateur.
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * @param nom
	 *            Nom de famille de l'utilisateur
	 * 
	 * @param nbMaxResults
	 *            Nombre maximum de résultats
	 * 
	 * @return map des infos de l'utilisateur
	 * 
	 * @throws TechnicalException
	 *             si la recherche échoue pour une raison technique
	 * 
	 * @throws ApplicationException
	 *             Si un des paramètres est vide ou null
	 * 
	 * @author Meissa Beye
	 * @since 19/07/2017
	 */
	Map<String, String[]> getMapOfUserByLoginOrName(String login, String nom, int nbMaxResults)
			throws TechnicalException, ApplicationException;
	/**
	 * Récupère le nombre d'utilisateur correspondant au résultat de la requete.
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * @param nom
	 *            Nom de famille de l'utilisateur
	 * 
	 * @return le nombre d'utilisateurs correspondant
	 * 
	 * @author Meissa Beye
	 * @since 19/07/2017
	 */
	int countNbUserByLoginOrName(String login, String nom);
	/**
	 * Récupère un map des infos de l'utilisateur.
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * 
	 * 
	 * @return map des infos de l'utilisateur
	 * 
	 * @throws TechnicalException
	 *             si la recherche échoue pour une raison technique
	 * 
	 * @throws ApplicationException
	 *             Si un des paramètres est vide ou null
	 * 
	 * @author Mamadou Ndir
	 * @since 24/07/2017
	 */
	Map<String, String> getInfoUserByLogin(String login)
			throws TechnicalException, ApplicationException;
	/**
	 * Activier ou desactiver un utilisateur.
	 * 
	 * @param login
	 *            le login de l'utilisateur
	 * @throws TechnicalException
	 *             si la mise à jour échoue pour une raison technique (connexion
	 *             perdue, ...)
	 * @author Mamadou ndir
	 */
	void activeDesactiveUtilisateur(String login) throws TechnicalException;
	
	
}
