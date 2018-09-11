package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeCommande;

/**
 * Classe d'accès aux données des types de commande.
 * 
 * @author Mickael Beaupoil
 *
 */
public interface TypeCommandeDAO extends AbstractDAO<TypeCommande> {

	/**
	 * Retourne la liste des types de commande au statut actif.	
	 * @return liste des types de commande
	 * @throws TechnicalException Si la lecture en BD échoue
	 * @author Meissa
	 * @since 23-05-2017
	 */
	List<TypeCommande> getTypeCommandeActifs() throws TechnicalException;
}
