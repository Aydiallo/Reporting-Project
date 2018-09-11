package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.ArticleDAO;
import com.sigif.dto.ArticleDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Article;
import com.sigif.util.Constantes;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux articles.
 * 
 * @author Meissa Beye
 * @since 02/06/2017
 */
@Service("articleService")
@Transactional
public class ArticleServiceImpl extends AbstractServiceImpl<Article, ArticleDTO> implements ArticleService {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    /**
     * DAO pour Article.
     */
    @Autowired
    private ArticleDAO articleDAO;

    @Override
    public Map<String, String> getAllArticlesOfGDM(String codeGDM) throws ApplicationException, TechnicalException {
        codeGDM = FunctionsUtils.checkNotNullNotEmptyAndTrim("CodeGDM", codeGDM, LOGGER);
        List<Article> articles = articleDAO.getAllArticlesOfGDM(codeGDM);
        Map<String, String> resultat = new HashMap<String, String>();
        if (articles != null) {
            for (Article article : articles) {
                resultat.put(article.getNumero(), article.getDesignation());
            }
        }
        return FunctionsUtils.sortByValue(resultat);
    }

    @Override
    public Map<String, String[]> getArticlesByGDMAndText(String codeGDM, String searchedText)
            throws ApplicationException, TechnicalException {
        codeGDM = FunctionsUtils.checkNotNullNotEmptyAndTrim("CodeGDM", codeGDM, LOGGER);
        searchedText = FunctionsUtils.checkNotNullNotEmptyAndTrim("searchedText", searchedText, LOGGER);
        searchedText = searchedText.replace('*', '%');
        Map<String, String[]> resultat = null;
        List<Article> articles = articleDAO.getArticlesByGDMAndText(codeGDM, searchedText);
        if (articles != null) {
            resultat = new HashMap<String, String[]>();
            // on crée les différentes listes requises par la fonction
            // d'extension
            int nbResults = articles.size();
            String[] codeGdmList = new String[nbResults];
            String[] gdmList = new String[nbResults];
            String[] designationList = new String[nbResults];
            String[] numList = new String[nbResults];
            String[] uniteList = new String[nbResults];
            String[] codeUniteList = new String[nbResults];
            int cpt = 0;
            for (Article article : articles) {
                codeGdmList[cpt] = codeGDM;
                gdmList[cpt] = article.getGroupeDeMarchandises().getDesignation();
                designationList[cpt] = article.getDesignation();
                numList[cpt] = article.getNumero();
                codeUniteList[cpt] = article.getUnite().getCode();
                uniteList[cpt] = article.getUnite().getNom();
                cpt++;
            }
            // on crée la liste qui contient les précédentes
            resultat.put(Constantes.KEY_MAP_CODE_GDM, codeGdmList);
            resultat.put(Constantes.KEY_MAP_GDM, gdmList);
            resultat.put(Constantes.KEY_MAP_DESIGNATION, designationList);
            resultat.put(Constantes.KEY_MAP_NUMERO, numList);
            resultat.put(Constantes.KEY_MAP_CODE_UNITE, codeUniteList);
            resultat.put(Constantes.KEY_MAP_UNITE, uniteList);
        }
        return resultat;
    }

    @Override
    public Map<String, String> getArticleInformations(String numArticle)
            throws ApplicationException, TechnicalException {
        numArticle = FunctionsUtils.checkNotNullNotEmptyAndTrim("numArticle", numArticle, LOGGER);
        Map<String, String> result = null;
        try {
            Article article = articleDAO.getUniqueByParam("numero", numArticle);
            result = new HashMap<String, String>();
            result.put(Constantes.KEY_MAP_CODE_GDM, article.getGroupeDeMarchandises().getCode());
            result.put(Constantes.KEY_MAP_GDM, article.getGroupeDeMarchandises().getDesignation());
            result.put(Constantes.KEY_MAP_DESIGNATION, article.getDesignation());
            result.put(Constantes.KEY_MAP_NUMERO, article.getNumero());
            result.put(Constantes.KEY_MAP_CODE_UNITE, article.getUnite().getCode());
            result.put(Constantes.KEY_MAP_UNITE, article.getUnite().getNom());

        } catch (Exception e) {
            String msg = String.format("Une erreur s'est produite lors de la lecture des infos de l'article '%s' : %s",
                    numArticle, e.getMessage());
            LOGGER.error(msg);
            throw new TechnicalException(msg);
        }
        return result;
    }
}
