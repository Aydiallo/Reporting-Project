package com.sigif.service;

import java.util.List;
import java.util.Map;

import com.sigif.dto.PosteDemandeAchatDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.DemandeAchat;

/**
 * Interface d'accès aux postes des demandes d'achat.
 *
 */
public interface PosteDaService extends AbstractService<PosteDemandeAchatDTO> {

	/**
	 * Enregistre une liste de Poste DA en BD.
	 * 
	 * @param listPostesDAmap
	 *            liste des postes de DA sous forme de map dont les clés sont :<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_POSTE_DA} - NumeroPosteDA <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE} - Code du type d'achat <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_UNITE} - Code de l'unité <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_SERVICE_BENEFICIAIRE} - Code du service bénéficiaire<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_PROGRAMME} - Code du programme<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_FOND} - Code du fonds <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QUANTITE} - Quantité demandée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_LIVRAISON} - Date de livraison souhaitée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_ACTION} - Code de l'action <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_ACTIVITE} - Code de l'activité <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_LIEU_STOCKAGE} - Code du lieu de stockage <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_VILLE} - Code de la ville <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REGION} - Code de la région <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CIVILITE} - Civilité du contact de livraison<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} - Désignation <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} - Référence <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_COMMENTAIRE} - Commentaire <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NOM} - Nom du contact de livraison <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_RUE} - Rue de l'adresse de livraison <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} - Numéro de l'adresse de livraison <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_POSTAL} - Code postal de l'adresse de livraison <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TELEPHONE} - Téléphone du contact de livraison <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_PORTABLE} - Portable du contact de livraison<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_EMAIL} - Courriel du contact de livraison<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CATEGORIE} - Catégorie d'immobilisation <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_UPLOAD_STRING_PJ} - Chaîne "GetString" du champ upload de la PJ<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_INTITULE_PJ} - Intitulé de la PJ.<br>
	 * @param idDemandeAchat
	 *            identifiant de la demande d'achat à laquelle les postes seront
	 *            rattachés
	 * @author Meissa Beye
	 * @throws TechnicalException
	 *             si l'ajout échoue
	 * @since 07-06-2017
	 */
	void savePostesDA(List<Map<String, Object>> listPostesDAmap, int idDemandeAchat) throws TechnicalException;

	/**
	 * Supprime les postes DA liés à une DA (ainsi que leurs PJ).
	 * 
	 * @param demandeAchat
	 *            id de la demande d'achat
	 * @throws TechnicalException
	 *             Si la suppression échoue
	 */
	void deletePostesDaByDa(DemandeAchat demandeAchat) throws TechnicalException;

	/**
	 * Retourne sous forme de map (key = nom du champ) les informations du poste
	 * de la DA avec les alertes de chaque champs si besoin: <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_SERVICE_BENEFICIAIRE} - Service Bénéficiaire (désignation), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_SERVICE_BENEFICIAIRE} - Code du service bénéficaire, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_FOND} - Fonds (désignation), <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_FOND} - Fonds (code), <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_FOND} - Fonds (code), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_TYPE_FOND} - Code du type de fonds, <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_ACHAT} - Type d'achat (désignation), <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_TYPE_ACHAT} - Type d'achat (code), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CATEGORIE} - Categorie (catégorie du poste), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} - Reference, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} - Designation, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_PROGRAMME} - Programme (désignation), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_PROGRAMME} - Programme (code), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_ACTION} - Action (désignation), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_ACTION} - Action (code), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_ACTIVITE} - Activité (désignation), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_ACTIVITE} - Activité (code), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_LIEU_STOCKAGE} - Lieu de stockage (désignation), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_LIEU_STOCKAGE} - Lieu de stockage (code), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_QUANTITE} - Quantité demandée, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_UNITE} - Unité (désignation), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_UNITE} - Unité (code), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_APPROBATION} - Statut d'approbation , <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_COMMANDEE} - Quantité commandée (QuantiteRetenue), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_MOTIF_REJET} - Motif du rejet, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_COMMENTAIRE} - Commentaire, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_LIVRAISON} - Date de livraison (JJ/MM/YYYY), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CIVILITE} - Civilité du contact de livraison, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_NOM} - Nom du contact de livraison, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_RUE} - Rue de l'adresse de livraison, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} - Numéro de l'adresse de livraison, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_POSTAL} - Code postal de l'adresse de livraison, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_VILLE} - Ville de livraison (désignation), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_VILLE} - Ville de livraison (code), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_REGION} - Region de livraison (désignation), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_REGION} - Region de livraison (code), <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_PORTABLE} - Portable du contact de livraison, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_TELEPHONE} - Téléphone du contact de livraison, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_EMAIL} - Courriel du contact de livraison, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_INTITULE_PJ} - Intitulé de la PJ, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_PJ_PATH} - Chemin complet de la pièce jointe, <BR>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_UPLOAD_STRING_PJ} 
	 * - IploadString de la PJ (à affecter au champ upload par SetString), <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATA_STATUS} - Etat des données du poste (alertes)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION_CATEGORIEIMMO} - Désignation de la catégorie d'immobilisation<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_CATEGORIEIMMO} - Code de la catégorie d'immobilisation<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION_TYPEIMMO} - Désignation du type d'immobilisation<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_TYPEIMMO} - Code du type d'immobilisation. <BR>
	 * 
	 * @param idDA
	 *            : identifiant de la DA (idSAP)
	 * @param noPoste
	 *            : numéro du poste la DA
	 * @return : les informations du poste de la DA sous forme de Map ainsi que
	 *         les alertes détaillées
	 * @throws ApplicationException
	 *             si l'une des 2 données d'entrée est vide ou null ou s'il
	 *             existe plusieurs postes correspondants aux paramètres
	 * @throws TechnicalException
	 *             si l'accès aux données de la BD échoue
	 * @author Mamadou Ndir
	 */
	Map<String, String> getPosteDAInformation(String idDA, String noPoste)
			throws ApplicationException, TechnicalException;

	/**
	 * Récupère la liste des données des postes pour une demande d'achat donnée.
	 * L'objet retourné est une map contenant en clé le nom de la donnée (id,
	 * numero, designation, ...) et en valeur un liste contenant une entrée par
	 * poste. Les clés sont les suivantes :<br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_ID} - Id <br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} - Numéro de poste (IdDaPoste)<br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} - Désignation du poste<br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} - Référence précédée du type d'achat entre parenthèses<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QUANTITE} - Quantité demandée suivie de l'unité entre parenthèses<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_COMMANDEE} - Quantité commandée suivie de l'unité entre parenthèses<br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} - Statut d'approbation<br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_CA} - N°de CA<br>
	 * - {@link com.sigif.util.Constantes#KEY_MAP_DATA_STATUS} - Etat de la donnée (Alertes)
	 * 
	 * @param numDA
	 *            numéro de la demande d'achat (idSAP)
	 * @return La map des infos des postes, null s'il n'y en a aucun
	 * 
	 * @throws ApplicationException
	 *             Si le numéro de la demande d'achat est vide ou null
	 * @throws TechnicalException
	 *             Si l'accès aux données de la BD a échoué
	 * @author Mamadou Ndir
	 */
	Map<String, String[]> getItemsByDA(String numDA) throws ApplicationException, TechnicalException;

	/**
	 * Permet de retourner les alertes qui concernent les postes d'une DA.
	 * 
	 * @param idDA
	 *            numero de la DA
	 * @param noPoste
	 *            numero poste DA
	 * @return Returne une liste d'alertes
	 * @throws ApplicationException
	 *             Si le numéro de la demande d'achat ou du poste DA est vide ou null
	 * @throws TechnicalException
	 *             Si l'accès aux données de la BD a échoué
	 * @author Mamadou Ndir
	 */
	String getDataImpact(String idDA, String noPoste) throws ApplicationException, TechnicalException;

	/**
	 * Récupère la liste des données des postes duplicables pour une demande d'achat donnée.
	 * Le retour est constitué d'une liste de postes duplicables (sous forme de Map) 
	 * et le dernier élément est une Map ne contenant que l'élément 
	 * {@link com.sigif.util.Constantes#KEY_MAP_MSG_ERROR} dont la vealeur est la liste des numéros de postes 
	 * non duplicables (pour cause de données inactives).
	 * Une Map représentant un poste contient les clés suivantes : <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_POSTE_DA} - NumeroPosteDA <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE} - Code du type d'achat <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_UNITE} - Code de l'unité <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_SERVICE_BENEFICIAIRE} - Code du service bénéficiaire<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_PROGRAMME} - Code du programme<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_FOND} - Code du fonds <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QUANTITE} - Quantité demandée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_LIVRAISON} - Date de livraison souhaitée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_CREATION} - Date de création <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_MODIFICATION} - Date de modification <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_ACTION} - Code de l'action <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_ACTIVITE} - Code de l'activité <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_LIEU_STOCKAGE} - Code du lieu de stockage <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_VILLE} - Code de la ville <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REGION} - Code de la région <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CIVILITE} - Civilité du contact de livraison<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} - Désignation <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} - Référence <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_COMMENTAIRE} - Commentaire <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NOM} - Nom du contact de livraison <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_RUE} - Rue de l'adresse de livraison <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} - Numéro de l'adresse de livraison <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_POSTAL} - Code postal de l'adresse de livraison <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TELEPHONE} - Téléphone du contact de livraison <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_PORTABLE} - Portable du contact de livraison<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_EMAIL} - Courriel du contact de livraison<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CATEGORIE} - Catégorie d'immobilisation <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_UPLOAD_STRING_PJ} 
     *      - Chaîne "GetString" du champ upload de la PJ (copiée dans le tmp)<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_INTITULE_PJ} - Intitulé de la PJ.<br>
	 *  
	 * 
	 * @param numDA
	 *            Numero de la DA
	 * @return La map des infos des postes, null s'il n'y en a aucun
	 * 
	 * @throws ApplicationException
	 *             Si le numéro de la demande d'achat est vide ou null
	 * @throws TechnicalException
	 *             Si l'accès aux données de la BD a échoué
	 * @author Mamadou Ndir
	 */
	List<Map<String, Object>> checkPostesDA(String numDA)
			throws ApplicationException, TechnicalException;
}
