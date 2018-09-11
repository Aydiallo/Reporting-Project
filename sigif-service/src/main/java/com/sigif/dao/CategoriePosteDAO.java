package com.sigif.dao;

import com.sigif.exception.TechnicalException;

/**
 * Classe d'accès en lecture aux catégories de postes.
 * 
 * @author Mickael Beaupoil
 *
 */
public interface CategoriePosteDAO {
    /**
     * Récupère la désignation d'une catégorie de poste pour un achat de type
     * "S" (achats stockés).
     * 
     * @param referenceArticle
     *            Référence de l'article commandé
     * @return La désignation de la catégorie de poste pour l'article commandé
     *         (désignation du groupe de marchandises de l'article)
     * @throws TechnicalException Si la lecture échoue
     */
    String getCategoriePosteForTypeS(String referenceArticle) throws TechnicalException;

    /**
     * Récupère la désignation d'une catégorie de poste pour un achat de type
     * "F" (fonctionnement).
     * 
     * @param referenceGroupeMarch
     *            Référence du groupe de marchandises commandé
     * @return La désignation de la catégorie de poste pour le groupe de
     *         marchadises commandé (désignation du groupe de marchandises)
     * @throws TechnicalException Si la lecture échoue
     */
    String getCategoriePosteForTypeF(String referenceGroupeMarch) throws TechnicalException;

    /**
     * Récupère la désignation d'une catégorie de poste pour un achat de type
     * "I" (immobilisation).
     * 
     * @param referenceImmo
     *            Référence de l'immobilisation commandé
     * @return La désignation de la catégorie de poste (désignation de la
     *         catégorie d'immobilisation de l'immobilisation commandée)
     * @throws TechnicalException Si la lecture échoue
     */
    String getCategoriePosteForTypeI(String referenceImmo) throws TechnicalException;

    /**
     * Récupère la désignation d'une catégorie active de poste pour un achat de type
     * "S" (achats stockés).
     * 
     * @param referenceArticle
     *            Référence de l'article commandé
     * @return La désignation de la catégorie de poste pour l'article commandé
     *         (désignation du groupe de marchandises de l'article)
     * @throws TechnicalException Si la lecture échoue
     */
    String getCategorieActivePosteForTypeS(String referenceArticle) throws TechnicalException;
    
    /**
     * Récupère la désignation d'une catégorie active de poste pour un achat de type
     * "F" (fonctionnement).
     * 
     * @param referenceGroupeMarch
     *            Référence du groupe de marchandises commandé
     * @return La désignation de la catégorie de poste pour le groupe de
     *         marchadises commandé (désignation du groupe de marchandises)
     * @throws TechnicalException Si la lecture échoue
     */
    String getCategorieActivePosteForTypeF(String referenceGroupeMarch) throws TechnicalException;

    /**
     * Récupère la désignation d'une catégorie active de poste pour un achat de type
     * "I" (immobilisation).
     * 
     * @param referenceImmo
     *            Référence de l'immobilisation commandé
     * @return La désignation de la catégorie de poste (désignation de la
     *         catégorie d'immobilisation de l'immobilisation commandée)
     * @throws TechnicalException Si la lecture échoue
     */
    String getCategorieActivePosteForTypeI(String referenceImmo) throws TechnicalException;
}
