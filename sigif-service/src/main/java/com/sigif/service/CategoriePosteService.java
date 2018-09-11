package com.sigif.service;

import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;

/**
 * Service D4ACC7S à la catégorie des postes (différente de la
 * catégorie d'achat).
 * 
 * @author Mickael Beaupoil
 *
 */
public interface CategoriePosteService {
    /**
     * Récupère la désignation de la catégorie à partir du type d'achat et de la
     * référence de l'article ou de l'immobilisation. <BR>
     * En fonction du type d’achat : <BR>
     * - Si S alors correspond à la désignation du groupe de marchandise de
     * l’article commandé <BR>
     * - Si F alors correspond à la désignation du groupe de marchandise <BR>
     * - Si I alors correspond à la désignation de la catégorie d’immobilisation
     * de l’immobilisation commandée
     * 
     * @param codeTypeAchat
     *            Code du type d'achat (S, F ou I)
     * @param referencePoste
     *            Référence de l'article ou de l'immobilisation
     * @return La désignation de la catégorie
     * @throws ApplicationException
     *             Si le code de type d'achat n'est pas une valeur prévue (S, F
     *             ou I) ou si la référence est vide ou null
     * @throws TechnicalException
     *             Si la lecture en base échoue
     */
    String getCategorieByTypeAndReference(String codeTypeAchat, String referencePoste)
            throws ApplicationException, TechnicalException;

    /**
     * Récupère la désignation de la catégorie à partir du type d'achat et de la
     * référence de l'article ou de l'immobilisation. Renvoie null si la
     * catégorie n'existe pas ou n'est pas active.<BR>
     * En fonction du type d’achat : <BR>
     * - Si S alors correspond à la désignation du groupe de marchandise de
     * l’article commandé <BR>
     * - Si F alors correspond à la désignation du groupe de marchandise <BR>
     * - Si I alors correspond à la désignation de la catégorie d’immobilisation
     * de l’immobilisation commandée
     * 
     * @param codeTypeAchat
     *            Code du type d'achat (S, F ou I)
     * @param referencePoste
     *            Référence de l'article ou de l'immobilisation
     * @return La désignation de la catégorie
     * @throws ApplicationException
     *             Si le code de type d'achat n'est pas une valeur prévue (S, F
     *             ou I)
     * @throws TechnicalException
     *             Si la lecture en base échoue
     */
    String getCategorieActiveByTypeAndReference(String codeTypeAchat, String referencePoste)
            throws ApplicationException, TechnicalException;
}
