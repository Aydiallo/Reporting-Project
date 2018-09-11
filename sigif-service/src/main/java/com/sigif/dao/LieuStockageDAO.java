package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.LieuStockage;

/**
 * Classe d'accès aux données des lieuStockages.
 * 
 * @author Meissa Beye
 *
 */
public interface LieuStockageDAO extends AbstractDAO<LieuStockage> {

	/**
	 * Retourne la liste des lieuStockages au statut actif.	
	 * @return tous les lieuStockages actifs
	 * @throws TechnicalException si l'accès BD échoue
	 * @author Meissa Beye
	 * @since 14-06-2017
	 */
	List<LieuStockage> getAllLieuStockageActifs() throws TechnicalException;


    /**
     * Renvoie la liste de tous les lieux de stockages actifs appartenant à la
     * même division que le lieu de stockage passé en paramètre.
     * 
     * @param idStorageArea
     *            id du lieu de stockage de départ
     * @return Tous les lieux de stockage appartenant à la même division
     * @throws TechnicalException
     *             Si l'accès BD échoue
     */
	List<LieuStockage> getActifStorageAreaInSameDivision(int idStorageArea) throws TechnicalException;
}
