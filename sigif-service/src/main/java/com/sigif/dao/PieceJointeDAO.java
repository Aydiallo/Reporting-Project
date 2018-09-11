package com.sigif.dao;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PieceJointe;

/**
 * Interface d'accès aux données des pièces jointes.
 *
 */
public interface PieceJointeDAO extends AbstractDAO<PieceJointe> {

    /**
     * Ajoute une PJ en base.
     * 
     * @param pieceJointe
     *            Entité représentant la PJ
     * @throws TechnicalException
     *             Si l'ajout échoue
     * @return l'id de l'entité sauvegardée
     */
    int addPieceJointe(PieceJointe pieceJointe) throws TechnicalException;

    /**
     * Met à jour une PJ.
     * 
     * @param pieceJointe
     *            pièce jointe à mettre à jour
     * @throws TechnicalException
     *             Si la MAJ échoue
     */
    void updatePieceJointe(PieceJointe pieceJointe) throws TechnicalException;

    /**
     * Supprime une pièce jointe à partir de son id.
     * 
     * @param id
     *            id de la pièce jointe à supprimer
     * @throws TechnicalException
     *             Si la suppression échoue
     */
    void deletePieceJointe(int id) throws TechnicalException;

    /**
     * Récupère une pièce jointe à partir de son id.
     * 
     * @param id
     *            id de la pièce jointe
     * @return la pièce jointe correspondant à l'id
     * @throws TechnicalException
     *             si la recherche échoue pour une raison technique
     */
    PieceJointe getPieceJointeById(int id) throws TechnicalException;

    /**
     * Retourne l'id d'une pièce jointe à partir de sa Demande Achat. et -1 si
     * null (Aucun résultat trouvé)
     * 
     * @param demandeAchat la DA dont on cehrche la PJ
     * @throws TechnicalException
     *             si la lecture échoue
     * @return id d'une pièce jointe à partir de sa Demande Achat
     */
    int getIdPieceJointeByDemandeAchat(DemandeAchat demandeAchat) throws TechnicalException;
}
