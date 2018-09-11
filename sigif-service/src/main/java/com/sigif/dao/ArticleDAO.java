package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Article;

/**
 * Classe d'accès aux données des articles.
 * @author Meissa Beye
 * 
 */
public interface ArticleDAO extends AbstractDAO<Article> {
	/**
	 * Retourne la liste des articles au statut actif d'un groupe de marchandises donné.
	 * @param codeGDM Code du groupe de marchandises
	 * @return tous les articles d'un groupe de marchandises donné
	 * @throws TechnicalException Si la connexion à la BD échoue
	 */
	List<Article> getAllArticlesOfGDM(String codeGDM) throws TechnicalException;

	/**
	 * Retourne la liste des articles au statut actif pour un groupe de marchandises donné
	 * et dont la désignation contient le texte donné.
	 * @param codeGDM Code du groupe de marchadises
	 * @param searchedText Texte recherché (avec wildcard % éventuellement)
	 * @return la liste des articles au statut actif pour un groupe de marchandises donné
     * et dont la désignation contient le texte donné
	 * @throws TechnicalException si la recherche échoue pour une raison technique
	 */
    List<Article> getArticlesByGDMAndText(String codeGDM, String searchedText) throws TechnicalException;
}
