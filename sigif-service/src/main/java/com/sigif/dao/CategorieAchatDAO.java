package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.CategorieAchat;

/**
 * Classe d'accès aux données des catégories d'achat.
 * @author Mamadou Ndir 
 */
public interface CategorieAchatDAO extends AbstractDAO<CategorieAchat> {

    /**
     * Retourne toutes les catégories d'achat en fonction de leur statut.
     * 
	 * @param status
	 *            status = actif (que les données au statut actif) 
	 *            status = inactif (que les données au statut inactif)
	 *            status = all (toutes quelque soit le statut)
	 *            
     * @return la liste des catégories d'achat (code ,
     *         désignation)
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique
     */
    List<CategorieAchat> getAllPurchasingCategories(String status) throws TechnicalException;

    /**
     * Retourne la catégorie d'achat au statut actif et avec le code fourni en parametre.
     * 
     * @param codeCA
     *            Code de la catégorie d'achat
     * @return categorieAchat la catégorie d'achat associée ou null s'il n'existe pas
     * @throws TechnicalException
     *             Si la recherche échoue pour une raison technique
     */
    CategorieAchat getCategorieAchatByCode(String codeCA) throws TechnicalException;
}
