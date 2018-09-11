package com.sigif.service;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.DADataImpact;

/**
 * Service d'accès aux alertes.
 * @author Mickael Beaupoil
 *
 */
public interface DataImpactService {
    /**
     * Permet de retourner les alertes qui concernent l'entete d'une DA.
     * 
     * @param numDA
     *            numero de la DA
     * @return Returne une liste d'alertes
     * @throws TechnicalException
     *             Probleme liés à l'execution de la requete
     */
    List<DADataImpact> searchAlertDA(String numDA) throws TechnicalException;

    /**
     * Permet de retourner les alertes qui concernent les postes d'une DA.
     * 
     * @param numDA
     *            numero de la DA
     * @param numPosteDA
     *            numero poste DA
     * @return Returne une liste d'alertes
     * @throws TechnicalException
     *             TechnicalException Probleme liés à l'execution de la requete
     */
    List<DADataImpact> searchAlertPosteDA(String numDA, String numPosteDA) throws TechnicalException;

    /**
     * Supprime table data_impact par numero DA ou numéro poste DA.
     * 
     * @param idDA id de la DA
     * @param idPosteDa id du posteDa
     * @throws TechnicalException Probleme liés à l'execution de la requete
     * @author Mamadou Ndir
     */
    void cleanDataImpact(int idDA, int idPosteDa) throws TechnicalException;
}
