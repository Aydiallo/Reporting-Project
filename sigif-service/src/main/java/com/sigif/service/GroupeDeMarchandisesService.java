package com.sigif.service;

import java.util.Map;

import com.sigif.dto.GroupeDeMarchandisesDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux types de commande.
 * 
 * @author Meissa Beye
 *
 */
public interface GroupeDeMarchandisesService extends AbstractService<GroupeDeMarchandisesDTO> {

    /**
     * Retourne les groupes de marchandises en fonction du type d'achat sous
     * forme de map (code, désignation) triée par désignation. <BR>
     * Type d'achat F : groupes de marchandises sans articles Type d'achat S :
     * groupes de marchandises avec articles
     * 
     * @author Meissa
     * @since 31/05/2017
     * @param codeTypeAchat
     *            code du type d'achat
     * @return le Map des groupes de marchandises au statut actif (code,
     *         désignation)
     * @throws ApplicationException
     *             si le code du type d'achat est vide ou null
     * @throws TechnicalException
     *             si la recherche échoue pour une raison technique
     */
    Map<String, String> getAllMerchandisesGroup(String codeTypeAchat) throws ApplicationException, TechnicalException;

    /**
     * Retourne tous les groupes de marchandises actifs.
     * 
     * @author Meissa
     * @since 31/05/2017
     * @return le Map des groupes de marchandises au statut actif (code,
     *         désignation) triée par désignation.
     * @throws TechnicalException si la recherche échoue pour une raison technique
     */
    Map<String, String> getAllActifMerchandisesGroup() throws TechnicalException;

}
