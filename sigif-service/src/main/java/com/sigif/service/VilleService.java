package com.sigif.service;

import java.util.Map;

import com.sigif.dto.VilleDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux villes.
 * 
 */
public interface VilleService extends AbstractService<VilleDTO> {
    /**
     * Retourne la liste des villes pour une région donnée au statut actif.
     * 
     * @author Meissa Beye
     * @param areaCode
     *            Code de la région
     * @return la liste des villes pour une région donnée au statut actif (code,
     *         désignation) triée par désignation
     * @throws ApplicationException
     *             si le code de la région est vide ou null
     * @throws TechnicalException
     *             si l'accès BD échoue
     */
    Map<String, String> getAllTownsByArea(String areaCode) throws ApplicationException, TechnicalException;

     
}
