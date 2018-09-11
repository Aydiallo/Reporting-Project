package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.ServiceDepensier;

/**
 * Classe d'accès aux données des services dépensiers.
 * 
 * @author Mamadou Ndir 
 * @since 22 mai 201722:06:58
 */
public interface ServiceDepensierDAO extends AbstractDAO<ServiceDepensier> {

    /**
     * Retourne la liste des services dépensiers auxquels est habilité un
     * utilisateur (reconnu par son login) avec un rôle donné et pour un
     * ministère. Les résultats peuvent être filtrés par statut.
     * 
     * @param login
     *            login
     * @param ministery
     *            code ministère
     * @param role
     *            code role
     * @param statut
     *            statut du service dépensier
     * @return liste des services dépensiers
     * @throws TechnicalException
     *             Si l'accès BD échoue
     */
    List<ServiceDepensier> getAllSpendingServicesOfUserByLoginRoleAndMinistery(String login, String ministery,
            String role, String statut) throws TechnicalException;

    /**
     * Retourne le service depensier avec le code fourni en parametre.
     * 
     * @author Meissa
     * @param codeSD code du service dépensier
     * @return le service dépensier avec le code fourni en paramètre
     * @throws TechnicalException Si l'accès BD échoue
     */
    ServiceDepensier getServiceDepensierByCode(String codeSD) throws TechnicalException;
    
    /**
     * Retourne la liste des services dépensiers pour un
     * ministère. Les résultats peuvent être filtrés par statut.
     * 
     * @param ministery
     *            code ministère
     * @param statut
     *            statut du service dépensier
     * @return liste des services dépensiers
     * @throws TechnicalException
     *             Si l'accès BD échoue
     */
    List<ServiceDepensier> getAllSpendingServicesByMinistery(String ministery, String statut) throws TechnicalException;
}
