package com.sigif.dao;

import java.util.List;
import java.util.Map;

import com.sigif.modele.AbstractModele;

/**
 * @author Malick DIAGNE
 * 
 * Interface de tous les DAOs.
 *
 * @param <E> type d'entité manipulé
 */

public interface AbstractDAO<E extends AbstractModele> {

	/**
	 * Récupère une entité par son identifiant.
	 *
	 * @param id identifiant
	 * @return l'entité
	 */
	E getById(int id);

	/**
	 * Récupère toutes les entités.
	 *
	 * @return liste des entités (vide si aucune entité)
	 */
	List<E> getAll();

    /**
     * Récupère une entité à partir de la valeur d'un attribut.
     * @param attribut Attribut à tester
     * @param valeur Valeur de l'attribut permettant de trouver l'entité
     * @return l'entité pour laquelle l'attribut a la valeur donnée
     */
	E getUniqueByParam(String attribut, Object valeur);

    /**
     * Récupère une liste d'entités à partir de la valeur d'un attribut.
     * @param attribut Attribut à tester
     * @param valeur Valeur de l'attribut permettant de trouver l'entité
     * @return les entités pour lesquelles l'attribut a la valeur donnée
     */
	List<E> getByParam(String attribut, Object valeur);

    /**
     * Récupère une entité à partir d'une liste d'attribut-valeur.
     * @param params liste de paires attribut-valeur
     * @return l'entité pour laquelle les attributs ont les valeurs données
     */
	E getUniqueByParams(Map<String, Object> params);

    /**
     * Récupère une liste d'entités à partir d'une liste d'attribut-valeur.
     * @param params liste de paires attribut-valeur
     * @return la liste des entités pour lesquelles les attributs ont les valeurs données
     */
	List<E> getByParams(Map<String, Object> params);

    /**
     * Sauvegarde l'entité.
     * @param entity Entité à sauvegarder
     * @return l'id de l'entité sauvegardée
     */
    int save(E entity);

    /**
     * Supprime une entité à partir de son id.
     * @param id id de l'entité à supprimer
     */
    void deleteById(int id);

    /**
     * Supprime une entité.
     * @param entity l'entité à supprimer
     */
    void delete(E entity);

    /**
     * Merge une entité.
     * @param entity Entité à merger
     */
    void merge(E entity);
}

