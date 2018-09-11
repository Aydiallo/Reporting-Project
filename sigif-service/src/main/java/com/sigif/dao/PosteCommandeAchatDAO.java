package com.sigif.dao;

import java.util.List;

import org.hibernate.NonUniqueResultException;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PosteCommandeAchat;

/**
 * Interface d'accès aux données des postes des commandes d'achat.
 * 
 * @author Mickael Beaupoil
 *
 */
public interface PosteCommandeAchatDAO extends AbstractDAO<PosteCommandeAchat> {
    /**
     * Retourne le poste correspondant à une CA et un numéro de poste.
     * 
     * @param numCA
     *            Numéro de la commande d'achat (idSap de la CA)
     * @param numPoste
     *            Numéro de poste (idSap)
     * @return le poste correspondant aux paramètres
     * @throws TechnicalException
     *             Si l'accès aux données de la BD a échoué
     * @throws NonUniqueResultException
     *             Si plus d'un poste correspond aux paramètres
     */
    PosteCommandeAchat getPosteCaInformation(String numCA, String numPoste)
            throws TechnicalException, NonUniqueResultException;

    /**
     * Retourne la liste des postes clôturés pour une commande d'achat.
     * 
     * @param numCA
     *            Numéro (idSap) de la commande d'achat
     * @param orderBy
     *            Nom de l'attribut sur lequel trier la liste de résultats
     *            (null, si tri par défaut)
     * @return la liste des postes réceptionnés pour une commande d'achat
     * @throws TechnicalException
     *             Si l'accès aux données de la BD a échoué
     */
    List<PosteCommandeAchat> getClosedItemsByNumCA(String numCA, String orderBy) throws TechnicalException;

    /**
     * Retourne la liste des postes réceptionnables pour une commande d'achat et
     * un service dépensier.
     * 
     * @param numCA
     *            numéro de la commande d'achat (idSap)
     * @param codeSpendingService
     *            code du service dépensier
     * @param orderBy
     *            Nom de l'attribut sur lequel trier la liste de résultats
     *            (null, si tri par défaut)
     * @return la liste des postes réceptionnables pour une commande d'achat et
     *         un service dépensier.
     * @throws TechnicalException
     *             Si l'accès aux données de la BD a échoué
     */
    List<PosteCommandeAchat> getReceivableItemsByCaAndSpendingService(String numCA, String codeSpendingService,
            String orderBy) throws TechnicalException;

    /**
     * Retourne la liste des postes réceptionnables pour une commande d'achat et
     * un service dépensier et qui ne sont pas déjà réceptionnés dans la CSF.
     * 
     * @param numCA
     *            numéro de la commande d'achat (idSap)
     * @param codeSpendingService
     *            code du service dépensier
     * @param orderBy
     *            Nom de l'attribut sur lequel trier la liste de résultats
     *            (null, si tri par défaut)
     * @param numCSF
     *            Numéro de télédossier de la CSF
     * @return la liste des postes réceptionnables pour une commande d'achat et
     *         un service dépensier (et non déjà réceptionnés dans une CSF donnée).
     * @throws TechnicalException
     *             Si l'accès aux données de la BD a échoué
     */
    List<PosteCommandeAchat> getReceivableItemsByCaAndSpendingServiceAndCsf(String numCA, String codeSpendingService,
            String orderBy, String numCSF) throws TechnicalException;

    /**
     * Retourne la liste des postes non réceptionnables pour une commande
     * d'achat et un service dépensier.
     * 
     * @param numCA
     *            numéro de la commande d'achat (idSap)
     * @param codeSpendingService
     *            code du service dépensier
     * @param orderBy
     *            Nom de l'attribut sur lequel trier la liste de résultats
     *            (null, si tri par défaut)
     * @return la liste des postes non réceptionnables pour une commande d'achat
     *         et un service dépensier.
     * @throws TechnicalException
     *             Si l'accès aux données de la BD a échoué
     */
    List<PosteCommandeAchat> getNotReceivableItemsByCaAndSpendingService(String numCA, String codeSpendingService,
            String orderBy) throws TechnicalException;

    /**
     * Retourne une liste ordonnée des postes d'une CA.
     * 
     * @param idSapOfCA
     *            l'idSAP de la CA
     * @param orderBy
     *            paramètre de tri
     * @return liste de PosteCA
     * @throws TechnicalException 
     *             Si l'accès aux données de la BD a échoué
     * @author Meissa
     * @since 08/06/2017
     */
    List<PosteCommandeAchat> getAllPosteOfCA(String idSapOfCA, String orderBy) throws TechnicalException;

    /**
     * Met à jour la quantité restante pour un poste CA en ajoutant ou
     * supprimant une quantité (selon que le poste CSF est créé ou supprimé).
     * 
     * @param pca
     *            le poste de CA à modifier
     * @param acceptedQty
     *            la quantité acceptée dans le poste CSF
     * @param csfCreated
     *            true si le poste CSF est créé, false s'il est supprimé
     * @return false si le poste CA ne permet pas d'effectuer l'opération (quantitié insuffisante)
     */
    boolean updateQuantityAndStatus(PosteCommandeAchat pca, Long acceptedQty, boolean csfCreated);
    
    // alpha ajout//
/*supprimer une liste de posteCommandeAchat***/
    
    public void delete(List<PosteCommandeAchat> liste) ;
    
    /* Les DA liees au CA **/
    
    public List<DemandeAchat> daLieesCa(List<CommandeAchat> listDeletableCa) ;
}
