package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeFonds;

/**
 * Classe d'accès aux données des types de Fonds.
 * 
 * @author Meissa Beye
 *
 */
public interface TypeFondsDAO extends AbstractDAO<TypeFonds> {

	/**
	 * Retourne la liste des Types de Fonds au statut actif.
	 * 
	 * @return tous les Types de Fonds actifs
	 * @throws TechnicalException Si l'accès aux données de la BD a échoué
	 * @author Meissa
	 * @since 02-06-2017
	 */
	List<TypeFonds> getAllTypeFondsActifs() throws TechnicalException;

}
