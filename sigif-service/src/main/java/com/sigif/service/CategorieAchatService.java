package com.sigif.service;

import java.util.Map;

import com.sigif.dto.CategorieAchatDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux catégories d'achat.
 * @author Mamadou Ndir 
 * @since 23 mai 2017 09:16:41
 */
public interface CategorieAchatService extends AbstractService<CategorieAchatDTO> {

    /**
     * Retourne toutes les catégories d'achat dont le statut est actif.
     * 
     * @param status
     *            status = actif (que les données au statut actif) <BR>
     *            status = inactif (que les données au statut inactif) <BR>
     *            status = all (toutes quel que soit le statut)
     * 
     * @return la liste des catégories d'achat au statut actif (code ,
     *         désignation)
     * @throws ApplicationException
     *             Si le paramètre statut est vide
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique (connexion à
     *             la Bd, ...)
     */
    Map<String, String> getAllPurchasingCategories(String status) throws ApplicationException, TechnicalException;

    /**
     * Retourne les infos de la catégorie d'achat avec le code fourni en
     * parametre sous forme de map (si elle existe et est active). 
     * 
     * @author Mickael
     * @param codeCA
     *            code de la catégorie d'achat
     * @return Une map donnant les infos de la catégorie d'achat. La map est une
     *         map de tableaux, dont les clés sont les suivantes : <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_CODE} : Valeur = Code
     *         de la catégorie d'achat <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} : Valeur
     *         = Désignation de la catégorie d'achat <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} : Valeur =
     *         Statut de la catégorie d'achat<BR>
     * @throws ApplicationException
     *             Si le code CA est vide ou null
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique
     */
    Map<String, String> getInformationCategorieAchatByCode(String codeCA)
            throws ApplicationException, TechnicalException;
}
