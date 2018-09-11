package com.sigif.service;

import java.util.Map;

import com.sigif.dto.TypeFondsDTO;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux types de fonds.
 * 
 * @author Meissa Beye
 *
 */
public interface TypeFondsService extends AbstractService<TypeFondsDTO> {

    /**
     * Retourne la liste des types de fonds au statut actif.
     * 
     * @author Meissa Beye
     * @return la liste des types de fonds au statut actif (code, désignation
     *         suivie du périmètre financier entre parenthèses) triée par
     *         désignation
     * @throws TechnicalException
     *             si l'accès Bd échoue
     */
    Map<String, String> getAllFundTypes() throws TechnicalException;

}
