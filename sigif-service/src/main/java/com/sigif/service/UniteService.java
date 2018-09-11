package com.sigif.service;

import java.util.Map;

import com.sigif.dto.UniteDTO;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux unités.
 * 
 * @author Meissa Beye
 *
 */
public interface UniteService extends AbstractService<UniteDTO> {

    /**
     * Retourne la liste des unités au statut actif.
     * 
     * @author Meissa Beye
     * @return la liste des unités au statut actif (code, désignation) triée par
     *         désignation
     * @throws TechnicalException
     *             si l'accès Bd échoue
     */
    Map<String, String> getAllActifUnits() throws TechnicalException;

}
