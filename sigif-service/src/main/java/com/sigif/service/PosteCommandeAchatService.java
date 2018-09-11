package com.sigif.service;

import java.util.List;
import java.util.Map;

import com.sigif.dto.PosteCommandeAchatDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PosteCommandeAchat;

/**
 * Service d'accès aux postes des commandes d'achat.
 *
 */
public interface PosteCommandeAchatService extends AbstractService<PosteCommandeAchatDTO> {
    /**
     * Retourne sous forme de map (key = nom du champ) les informations du poste
     * de la CA : <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_ID} - Id <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} - Statut <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_COMMENTAIRE} - Commentaire
     * <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_ACHAT} - Désignation du
     * type d'achat<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CATEGORIE} - Catégorie<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} - Référence <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} - Désignation
     * du poste <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_COMMANDEE} - Quantité
     * commandée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} - Quantité
     * acceptée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_RESTANTE} - Quantité
     * restante <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_UNITE} - Unité <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_PRIX} - Prix <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DEVISE} - Devise <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_SERVICE_DEPENSIER} - Service
     * dépensier <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_SERVICE_BENEFICIAIRE} -
     * Service bénéficiaire <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NB_POSTES_CSF} - Nombre de
     * postes CSF liés à ce poste CA <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_LIEU_STOCKAGE} - Lieu de
     * stockage. <br>
     * 
     * @param idCA
     *            : identifiant de la CA (idSAP)
     * @param noPoste
     *            : numéro du poste la CA
     * @return : les informations du poste de la CA sous forme de Map
     * @throws ApplicationException
     *             si l'une des 2 données d'entrée est vide ou null ou s'il
     *             existe plusieurs postes correspondants aux paramètres
     * @throws TechnicalException
     *             si l'accès aux données de la BD échoue
     */
    Map<String, String> getPosteCAInformation(String idCA, String noPoste)
            throws ApplicationException, TechnicalException;

    /**
     * Récupère la liste des données des postes clôturés pour une commande
     * d'achat donnée. L'objet retournée est une map contenant en clé le nom de
     * la donnée (id, numero, designation, ...) et en valeur un liste contenant
     * une entrée par poste (triée par idSap). Les clés sont les suivantes :<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_ID} - Id <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} - Numéro de poste
     * (idSAP)<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_ACHAT} - Désignation du
     * type d'achat<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} - Référence <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} - Désignation
     * du poste <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_COMMANDEE} - Quantité
     * commandée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} - Quantité
     * acceptée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} - Statut <br>
     * 
     * @param numCA
     *            numéro de la commande d'achat (idSAP)
     * @return La map des infos des postes clôturés pour la CA, null s'il n'y en
     *         a aucun
     * 
     * @throws ApplicationException
     *             Si le numéro de la commande d'achat est vide ou null
     * @throws TechnicalException
     *             Si l'accès aux données de la BD a échoué
     */
    Map<String, String[]> getClosedItemsByCA(String numCA) throws ApplicationException, TechnicalException;

    /**
     * Récupère la liste des données des postes réceptionnables pour une
     * commande d'achat et un service dépensier donné triée par idSap. L'objet
     * retourné est une map contenant en clé le nom de la donnée (id, numero,
     * designation, ...) et en valeur un liste contenant une entrée par poste.
     * Les clés sont les suivantes :<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_ID} - Id <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} - Numéro de poste
     * (idSAP)<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_ACHAT} - Désignation du
     * type d'achat<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} - Référence <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} - Désignation
     * du poste <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_COMMANDEE} - Quantité
     * commandée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} - Quantité
     * acceptée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} - Statut <br>
     * 
     * @param numCA
     *            numéro de la commande d'achat (idSAP)
     * @param codeSpendingService
     *            code du service dépensier
     * @return La map des infos des postes réceptionnables pour la CA et le
     *         service dépensier, null s'il n'y en a aucun
     * 
     * @throws ApplicationException
     *             Si le numéro de la commande d'achat est vide ou null ou si le
     *             code du service dépensier est vide ou null
     * @throws TechnicalException
     *             Si l'accès aux données de la BD a échoué
     */
    Map<String, String[]> getReceivableItemsByCaAndSpendingService(String numCA, String codeSpendingService)
            throws ApplicationException, TechnicalException;

    /**
     * Récupère la liste des données des postes réceptionnables pour une
     * commande d'achat et un service dépensier donné triée par idSap. Les postes CA ayant déjà fait l'objet
     * d'une réception (poste CSF) sont exclus.
     * L'objet retourné est une map contenant en clé le nom de la donnée (id, numero,
     * designation, ...) et en valeur un liste contenant une entrée par poste.
     * Les clés sont les suivantes :<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_ID} - Id <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} - Numéro de poste
     * (idSAP)<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_ACHAT} - Désignation du
     * type d'achat<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} - Référence <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} - Désignation
     * du poste <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_COMMANDEE} - Quantité
     * commandée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} - Quantité
     * acceptée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} - Statut <br>
     * 
     * @param numCA
     *            numéro de la commande d'achat (idSAP)
     * @param codeSpendingService
     *            code du service dépensier
     * @param numCSF
     *            numéro de la CSF (numeroDossier)
     * @return La map des infos des postes réceptionnables pour la CA et le
     *         service dépensier, null s'il n'y en a aucun
     * 
     * @throws ApplicationException
     *             Si le numéro de la commande d'achat est vide ou null ou si le
     *             code du service dépensier est vide ou null
     * @throws TechnicalException
     *             Si l'accès aux données de la BD a échoué
     */
    Map<String, String[]> getReceivableItemsByCaAndSpendingServiceAndCsf(String numCA, String codeSpendingService, String numCSF)
            throws ApplicationException, TechnicalException;
    /**
     * Récupère la liste des données des postes non réceptionnables pour une
     * commande d'achat et un service dépensier donné (c'est à dire les postes
     * non clôturés mais liés à un autre service dépensier). L'objet retourné
     * est une map contenant en clé le nom de la donnée (id, numero,
     * designation, ...) et en valeur un liste contenant une entrée par poste.
     * Les clés sont les suivantes :<BR>
     * <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_ID} - Id <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} - Numéro de poste
     * (idSAP)<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_ACHAT} - Désignation du
     * type d'achat<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} - Référence <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} - Désignation
     * du poste <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_COMMANDEE} - Quantité
     * commandée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} - Quantité
     * acceptée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} - Statut <br>
     * 
     * @param numCA
     *            numéro de la commande d'achat (idSAP)
     * @param codeSpendingService
     *            code du service dépensier
     * @return La map des infos des postes non réceptionnables pour la CA et le
     *         service dépensier, null s'il n'y en a aucun
     * 
     * @throws ApplicationException
     *             Si le numéro de la commande d'achat est vide ou null ou si le
     *             code du service dépensier est vide ou null
     * @throws TechnicalException
     *             Si l'accès aux données de la BD a échoué
     */
    Map<String, String[]> getNotReceivableItemsByCaAndSpendingService(String numCA, String codeSpendingService)
            throws ApplicationException, TechnicalException;

    /**
     * Retourne tous les postes d'une CA. L'objet retourné est une map contenant
     * en clé le nom de la donnée (id, numero, designation, ...) et en valeur un
     * liste contenant une entrée par poste et triée par idSap. Les clés sont
     * les suivantes <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} - Numéro de poste
     * (idSAP)<br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} - Référence <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_ACHAT} - Type d'achat <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} - Désignation
     * du poste <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_COMMANDEE} - Quantité
     * commandée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} - Quantité
     * acceptée <br>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} - Statut <br>
     * 
     * 
     * @param idSapOfCA
     *            idSap de la CA
     * @return un MAP des infos des postes CA
     * @author Meissa
     * @throws TechnicalException
     *             Si l'accès aux données de la BD a échoué
     * @since 08/06/2017
     */
    Map<String, String[]> getAllPosteOfCA(String idSapOfCA) throws TechnicalException;

    /**
     * Met à jour la quantité restante du poste et son statut après
     * enregistrement d'un poste de CSF.
     * 
     * @param pca
     *            le poste de Commande d'achat concerné
     * @param acceptedQuantity
     *            la quantité enregistrée comme accepté dans le poste CSF à
     *            supprimer de la quantité restante
     * @return false si la quantité restante est inférieure à la quantité acceptée
     */
    boolean updateRemainingQuantityAndStatusAfterCsfSave(PosteCommandeAchat pca, Long acceptedQuantity);

    /**
     * Met à jour la quantité restante du poste et son statut après suppression
     * d'un poste de CSF.
     * 
     * @param pca
     *            le poste de Commande d'achat concerné
     * @param acceptedQuantity
     *            la quantité enregistrée comme reçue dans le poste CSF à
     *            réajouter.
     */
    void updateRemainingQuantityAndStatusAfterCsfDelete(PosteCommandeAchat pca, Long acceptedQuantity);
    
 // alpha ajout//
    /*supprimer une liste de posteCommandeAchat***/
        
        public void delete(List<PosteCommandeAchat> liste) ;
        
        /* Les DA liees au CA **/
        
        public List<DemandeAchat> daLieesCa(List<CommandeAchat> listDeletableCa) ;
}
