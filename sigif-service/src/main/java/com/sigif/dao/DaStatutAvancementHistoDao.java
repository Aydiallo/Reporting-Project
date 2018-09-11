package com.sigif.dao;

import java.util.Date;
import java.util.List;

import com.sigif.enumeration.StatutAvancementDA;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.DaStatutAvancementHistorique;
import com.sigif.modele.DemandeAchat;

/**
 * Interface d'accès à la table d'historisation des statuts d'avancement des DA.
 *
 */
public interface DaStatutAvancementHistoDao extends AbstractDAO<DaStatutAvancementHistorique> {
    /**
     * Ajoute une nouvelle entrée dans la table d'historisation si le dernier
     * changement pour la DA concernée n'avait pas le même statut.
     * 
     * @param da
     *            la demande d'achat
     * @param dateModification
     *            date de la modification
     * @param statutAvancement
     *            nouveau statut d'avancement de la DA
     * @throws TechnicalException
     *             si l'accès BD échoue
     */
    void addNewStatusChange(DemandeAchat da, Date dateModification, StatutAvancementDA statutAvancement)
            throws TechnicalException;

    /**
     * Supprime les entrées de la table d'histo liées à une DA.
     * @param idDa id de la DA
     * @throws TechnicalException si la suppression échoue
     */
    void deleteByDa(int idDa) throws TechnicalException;
   
}
