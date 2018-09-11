package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeAchat;

/**
 * Classe d'accès aux données des types d'achat.
 * 
 * @author Meissa Beye
 *
 */
public interface TypeAchatDAO extends AbstractDAO<TypeAchat> {

	/**
	 * Retourne la liste des types d'achat au statut actif.
	 * 
	 * @return tous les types d'achat au statut actif
	 * @throws TechnicalException Si l'accès aux données de la BD a échoué
	 * @author Meissa Beye
	 * @since 16/06/2017
	 */
	List<TypeAchat> getAllTypeAchatActifs() throws TechnicalException;

}
