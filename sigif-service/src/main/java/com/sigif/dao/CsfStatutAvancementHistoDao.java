package com.sigif.dao;

import java.util.Date;

import com.sigif.enumeration.StatutAvancementCSF;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.ConstatationServiceFait;
import com.sigif.modele.CsfStatutAvancementHistorique;

/**
 * Interface d'accès à la table d'historisation des statuts d'avancement des CSF.
 *
 */
public interface CsfStatutAvancementHistoDao extends AbstractDAO<CsfStatutAvancementHistorique> {
    /**
     * Ajoute une nouvelle entrée dans la table d'historisation si le dernier
     * changement pour la CSF concernée n'avait pas le même statut.
     * 
     * @param csf
     *            la Constatation de service fait
     * @param dateModification
     *            date de la modification
     * @param statutAvancement
     *            nouveau statut d'avancement de la CSF
     * @throws TechnicalException
     *             si l'accès BD échoue
     */
    void addNewStatusChange(ConstatationServiceFait csf, Date dateModification, StatutAvancementCSF statutAvancement)
            throws TechnicalException;
    /**
     * Supprime les entrées de la table d'histo liées à une CSF.
     * @param idCsf id de la Csf
     * @throws TechnicalException si la suppression échoue
     */
    void deleteByCsf(int idCsf) throws TechnicalException;
}
