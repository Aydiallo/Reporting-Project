package com.sigif.service;

import java.util.Map;

import com.sigif.dto.ProgrammeDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux programmes
 * @author Mamadou Ndir 
 * @since 22 mai 201717:04:10
 */
public interface ProgrammeService extends AbstractService<ProgrammeDTO> {
    /**
     * Permet de récupérer le ou les programme(s) lié(s) au ministère(s) dont le
     * code est donné en paramétre.
     * 
     * @param login
     *            login de l'utilisateur (paramètre inutile)
     * @param ministery
     *            code du minstère
     * @param role
     *            rôle de l'utilisateur pour le ministère (paramètre inutile)
     *            
     * @return Map dont chaque entrée représente un
     *         programme (clé = code du programme, valeur = description) triée
     *         par description.
     * @throws ApplicationException
     *             si le code ministère est vide ou null
     * @throws TechnicalException
     *             Si l'accès BD échoue
     */
    Map<String, String> getAllProgrammOfUserByLoginRoleAndMinistery(String login, String ministery, String role)
            throws ApplicationException, TechnicalException;
}
