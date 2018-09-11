package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Article;

/**
 * Implémentation de la classe d'accès aux données des  articles.
 * 
 * @author Meissa Beye
 * @since 15/06/2017
 */
@Repository("articleDAO")
public class ArticleDAOImpl extends AbstractDAOImpl<Article> implements ArticleDAO {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDAOImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> getAllArticlesOfGDM(String codeGDM) throws TechnicalException {
        try {
            List<Article> articles = null;
            articles = this.getSession()
                    .createQuery(
                            " FROM Article a where a.groupeDeMarchandises.code = :codeGDM and a.groupeDeMarchandises.statut = '"
                                    + Statut.actif + "' and a.statut = '" + Statut.actif + "' ")
                    .setParameter("codeGDM", codeGDM).list();
            return articles;

        } catch (Exception e) {
            LOGGER.error("Impossible de récuperer les articles actifs du GDM de code : " + codeGDM);
            throw new TechnicalException("Impossible de récuperer les articles actifs du GDM de code :" + codeGDM, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> getArticlesByGDMAndText(String codeGDM, String searchedText) throws TechnicalException {
        try {
            List<Article> articles = null;
            articles = this.getSession()
                    .createQuery(" FROM Article a where a.groupeDeMarchandises.code = :codeGDM "
                            + "and a.groupeDeMarchandises.statut = :statut and a.statut = :statut "
                            + "and a.designation like :texteRecherche order by designation")
                    .setParameter("codeGDM", codeGDM).setParameter("statut", Statut.actif)
                    .setParameter("texteRecherche", "%" + searchedText + "%").list();

            return articles;

        } catch (Exception e) {
            LOGGER.error("Impossible de récuperer les articles actifs du GDM de code '" + codeGDM
                    + "' et pour le texte '" + searchedText + "'");
            throw new TechnicalException("Impossible de récuperer les articles actifs du GDM de code '" + codeGDM
                    + "' et pour le texte '" + searchedText + "'", e);
        }
    }
}
