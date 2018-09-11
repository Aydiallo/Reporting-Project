package com.sigif.service;

import java.util.Map;

import com.sigif.dto.ArticleDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;

/**
 * Service d'accès aux articles.
 * 
 */
public interface ArticleService extends AbstractService<ArticleDTO> {

    /**
     * Retourne un Map (numéro, désignation) d'articles au statut actif d'un
     * groupe de marchandises donné.
     * 
     * @param codeGDM
     *            Code du groupe de marchandises
     * @return un Map (numéro, désignation) contenant tous les articles d'un
     *         groupe de marchandises donné
     * @throws ApplicationException
     *             Si le code du groupe de marchandises n'est pas renseigné
     * @throws TechnicalException
     *             si la recherche échoue pour une raison technique
     * 
     * @author Meissa
     * @since 15-06-2017
     */
    Map<String, String> getAllArticlesOfGDM(String codeGDM) throws ApplicationException, TechnicalException;

    /**
     * Retourne une Map d'articles au statut actif d'un groupe de marchandises
     * donné contenant le texte recherché (caractères wildcard * et %
     * autorisés).
     * 
     * @param codeGDM
     *            Code du groupe de marchandises
     * @param searchedText
     *            Texte recherché
     * @return une Map d'articles au statut actif d'un groupe de marchandises
     *         donné contenant le texte recherché. La map est une map de tableaux, dont les clés sont les suivantes : <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_CODE_GDM} : Valeur = Code du groupe de marchandises <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_GDM} : Valeur =
     *         Désignation du groupe de marchandises <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} :
     *         Valeur = Désignation de l'article<BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} : Valeur
     *         = Numéro de l'article <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_UNITE} :
     *         Valeur = unité (désignation)<BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_CODE_UNITE} :
     *         Valeur = code de l'unité<BR>
     *         
     * @throws ApplicationException
     *             si l'un des paramètres est null ou vide
     * @throws TechnicalException
     *             si la recherche échoue pour une raison technique
     * 
     * @author Mickael
     * @since 15-06-2017
     */
    Map<String, String[]> getArticlesByGDMAndText(String codeGDM, String searchedText)
            throws ApplicationException, TechnicalException;
    


    /**
     * Retourne une Map de String représentant un article.
     * 
     * @param numArticle
     *            Numéro de l'article
     * @return Une map représentant l'article, dont les clés sont les suivantes : <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_CODE_GDM} : Valeur = Code du groupe de marchandises <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_GDM} : Valeur =
     *         Désignation du groupe de marchandises <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} :
     *         Valeur = Désignation de l'article<BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO} : Valeur
     *         = Numéro de l'article <BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_UNITE} :
     *         Valeur = unité (désignation)<BR>
     *         - {@link com.sigif.util.Constantes#KEY_MAP_CODE_UNITE} :
     *         Valeur = code de l'unité<BR>
     *         
     * @throws ApplicationException
     *             si l'un des paramètres est null ou vide
     * @throws TechnicalException
     *             si la recherche échoue pour une raison technique
     * 
     * @author Mickael
     * @since 16-06-2017
     */
    Map<String, String> getArticleInformations(String numArticle)
            throws ApplicationException, TechnicalException;
}
