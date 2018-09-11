package com.sigif.service;

import java.util.List;

import com.sigif.dto.AbstractDTO;

/**
 * Interface générique des services.
 *
 * @param <D> type de DTO manipulé
 */
public interface AbstractService<D extends AbstractDTO> {

	/**
	 * Récupère une DTO par son identifiant.
	 *
	 * @param id identifiant
	 * @return l'entité
	 */
	D getById(int id);

	/**
	 * Récupère toutes les DTOs.
	 *
	 * @return liste des DTOs (vide si aucune)
	 */
	List<D> getAll();

	/**
	 * Sauvegarde une entité à partir d'un DTO.
	 * @param dto le DTO à sauvegarder
	 * @return l'id de l'entité sauvegardée
	 */
	int save(D dto);

	/**
	 * Supprime une entité à partir de son id.
	 * @param id id de l'entité à supprimer
	 */
	void deleteById(int id);

	/**
	 * Supprime une entité à partir d'un DTO.
	 * @param dto DTO de l'entité à supprimer
	 */
	void delete(D dto);

	/**
	 * Met à jour une entité à partir d'un DTO modifié.
	 * @param dto DTO modifié à partir duquel mettre à jour l'entité
	 */
	void update(D dto);

}
