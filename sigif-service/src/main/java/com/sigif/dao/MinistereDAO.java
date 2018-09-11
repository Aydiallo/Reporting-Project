package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Ministere;

/**
 * Interface d'accès aux données des ministères.
 * 
 * @author Mamadou Ndir 
 * @since 29 mai 2017 16:07:18
 */
public interface MinistereDAO extends AbstractDAO<Ministere> {
    /**
     * Récupère tous les ministères.
     * 
     * @return tous les ministères.
     */
    List<Ministere> getMinisteres();

    /**
     * Retourne les ministeres liés à l'utilisateur par login et role ordonnés par désignation.
     * 
     * @param login
     *            login de l'utilisateur
     * @param role
     *            role
     * @param status
     *            statut du ministère
     * @return Liste des ministères
     * @throws TechnicalException
     *             Si la recherche échoue
     */
    List<Ministere> getAllMinisteryOfUserByLoginAndRole(String login, String role, String status) throws TechnicalException;

    /**
     * Retourne le ministère avec le code fourni en paramètre s'il est actif.
     * 
     * @author Meissa
     * @param codeMin
     *            Code du ministère
     * @return ministere le ministère correspondant au code
     * @throws TechnicalException
     *             si la recherche échoue
     */
    Ministere getMinistereByCode(String codeMin) throws TechnicalException;
    
    
}