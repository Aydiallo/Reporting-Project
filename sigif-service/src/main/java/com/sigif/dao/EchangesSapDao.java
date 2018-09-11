package com.sigif.dao;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.EchangesSAP;

/**
 * Interface d'accès à la table des échanges avec SAP.
 * 
 *
 */
public interface EchangesSapDao extends AbstractDAO<EchangesSAP> {
    /**
     * Récupère la dernière ligne (date de lancement la plus récente)
     * correspondant à un import/export en cours. Normalement, il ne doit y en
     * avoir qu'une seule.
     * 
     * @return la dernière ligne (date de lancement la plus récente)
     *         correspondant à un import/export en cours (null, si aucune ligne
     *         en cours).
     * @throws TechnicalException
     *             si l'accès BD échoue
     */
    EchangesSAP getEchangeEnCours() throws TechnicalException;
}
