package com.sigif.service;

import com.sigif.exception.TechnicalException;

/**
 * Interface de gestion des échanges Sap-Sigif-formulaires (import/export).
 * 
 */
public interface EchangesSapService {
    /**
     * Indique si un traitement d'import/export SAP est actuellement en cours.
     * 
     * @return true si un traitement est en cours, false sinon.
     * @throws TechnicalException si l'accès BD échoue.
     */
    boolean isSapProcessRunning() throws TechnicalException;

}
