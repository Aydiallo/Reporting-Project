package com.sigif.service;

import java.util.Map;

import com.sigif.dto.TypeImmobilisationDTO;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux types d'immobilisation
 * 
 * @author Meissa Beye
 *
 */
public interface TypeImmobilisationService extends AbstractService<TypeImmobilisationDTO> {
    /**
     * Retourne la liste des typeImmos au statut actif.
     * 
     * @author Meissa Beye
     * @return la liste des typeImmos au statut actif (code, désignation) triée par désignation
     * @throws TechnicalException
     *             si l'accès BD échoue
     */
    Map<String, String> getAllActifTypeImmobilisation() throws TechnicalException;

}
