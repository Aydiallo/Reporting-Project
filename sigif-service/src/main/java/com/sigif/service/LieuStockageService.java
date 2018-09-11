package com.sigif.service;

import java.util.Map;

import com.sigif.dto.LieuStockageDTO;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux lieux de stockage.
 * 
 * @author Meissa Beye
 *
 */
public interface LieuStockageService extends AbstractService<LieuStockageDTO> {

    /**
     * Retourne la liste des lieuStockages au statut actif.
     * 
     * @author Meissa
     * @return la liste des lieuStockages au statut actif (id, désignation)
     *         triée par désignation
     * @throws TechnicalException
     *             si la recherche échoue pour une raison technique
     */
    Map<String, String> getAllActifStorageArea() throws TechnicalException;

    /**
     * Renvoie la liste de tous les lieux de stockages actifs appartenant à la
     * même division que le lieu de stockage passé en paramètre.
     * 
     * @param idStorageArea
     *            id du lieu de stockage de départ
     * @return Tous les lieux de stockage appartenant à la même division (id,
     *         désignation) triés par désignation
     * @throws TechnicalException
     *             Si l'accès BD échoue
     */
    Map<String, String> getActifStorageAreaInSameDivision(int idStorageArea) throws TechnicalException;
}
