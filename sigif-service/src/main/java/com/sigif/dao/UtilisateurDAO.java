package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Utilisateur;

/**
 * Classe d'accès aux données des utilisateurs.
 *
 */
public interface UtilisateurDAO extends AbstractDAO<Utilisateur> {
	/**
	 * Compte le nombre d'utilisateurs en base (pour tester que l'appli est
	 * correctement configurée).
	 * 
	 * @return le nombre d'utilisateurs en base
	 */
	int countNb();

	/**
	 * Récupère les habilitations de l'utilisateur correspondant au login.
	 * 
	 * @param login
	 *            Le Login de l'utilisateur
	 * @return Les habilitations de l'utilisateur correspondant au login.
	 *
	 * @throws TechnicalException
	 *             si l'accès aux données à échoué pour une raison technique
	 */
	int getUserHabilitation(String login) throws TechnicalException;

	/**
	 * Vérifie que l'utilisateur possède un compte correct et renvoie une valeur
	 * indiquant les rôles qu'il possède.
	 * 
	 * @param login
	 *            Login du compte à tester
	 * @return 0 si l'utilisateur n'existe pas, <br>
	 *         1 si l'utilisateur a un compte Sigif et son compte n'est pas
	 *         désactivé (colonne compteActif), ni inactif (colonne statut),
	 *         <br>
	 *         2 si l'utilisateur n'a pas de compte Sigif ou il est désactivé
	 *         <br>
	 *         3 si l'utilisateur a un compte Sigif non désactivé mais il a été
	 *         supprimé (colonne statut à inactif)
	 * @throws TechnicalException
	 *             si l'accès aux données à échoué pour une raison technique
	 */
	int checkIdentity(String login) throws TechnicalException;

	/**
	 * Retourne l'utilisateur ayant le login en paramètre.
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * @return l'utilisateur ayant le login en paramètre.
	 * @throws TechnicalException
	 *             Si une erreur se produit lors de l'accès aux données
	 */
	Utilisateur getUserByLogin(String login) throws TechnicalException;

	/**
	 * Retourne tous les demandeurs possibles répondant aux critères suivants
	 * :<BR>
	 * - login différent de celui en paramètre<BR>
	 * - habilité au ministère indiqué<BR>
	 * - habilité au service dépensier indiqué<BR>
	 * - habilité avec le rôle indiqué pour le ministère et le service dépensier
	 * indiqué.<BR>
	 * Note :<BR>
	 * - Tous les critères sont facultatifs, s'ils sont vides ou null, tous les
	 * demandeurs sont renvoyés sans filtrer.<BR>
	 * - La liste est ordonnée par nom et prénom
	 * 
	 * @param login
	 *            Login à filtrer des résultats (ou null pour ne pas filtrer le
	 *            login de l'utilisateur courant)
	 * @param ministry
	 *            Code du ministère auquel doit être habilité le demandeur (ou
	 *            null pour ne pas vérifier l'habilitation aux ministères) (doit
	 *            obligatoirement être celui du service dépensier si le service
	 *            dépensier est renseigné)
	 * @param spendingService
	 *            Code du service dépensier auquel doit être habilité le
	 *            demandeur (ou null pour ne pas vérifier les habilitations aux
	 *            services dépensier)
	 * @param role
	 *            Code du rôle que doit posséder l'utilisateur pour le ministère
	 *            et le service dépensier (ou null pour ne pas vérifier les
	 *            rôles)
	 * @param statut
	 *            statut sur lequel filtrer
	 * @return tous les demandeurs possibles répondant aux critères ou null s'il
	 *         n'y a pas de résultat
	 * @throws TechnicalException
	 *             Si la recherche échoue pour une raison technique
	 * @author Mickael Beaupoil
	 */
	List<Utilisateur> getRequestersByCriteriaOrderByNomPrenom(String login, String ministry, String spendingService,
			String role, String statut) throws TechnicalException;

	/**
	 * Retourne la liste des utilisateurs selon le login ou le nom de famille.
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * @param nom
	 *            Nom de famille de l'utilisateur
	 * 
	 * @param nbMaxResults
	 *            Nombre maximum de résultats
	 * 
	 * @return la liste des utilisateurs correspondante aux critéres de
	 *         recherche.
	 */
	List<Utilisateur> getUsersByLoginOrName(String login, String nom, int nbMaxResults);

	/**
	 * Retourne le nombre d'utilisateurs selon le login ou le nom de famille.
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * @param nom
	 *            Nom de famille de l'utilisateur
	 * 
	 * 
	 * @return le nombre d'utilisateurs correspondant aux critéres de
	 *         recherche.
	 */
	int countUsersByLoginOrName(String login, String nom);
	/**
	 * Récupère un map des infos de l'utilisateur.
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * 
	 * @return map des infos de l'utilisateur
	 * 
	 * @throws TechnicalException
	 *             si la recherche échoue pour une raison technique
	 * 
	 * @author Ndir
	 * @since 24/07/2017
	 */
	Utilisateur getInfoUserByLogin(String login)
			throws TechnicalException;
}
