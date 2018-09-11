package com.sigif.service;

/**
 * Implémentation du service de gestion de la séquence
 */
public interface SequenceServiceInterface {
	
	/**
	 * Insère une nouvelle entrée dans la table sequence et récupère son id. 
	 * 
	 * @return l'id d'une nouvelle entrée dans la table séquence après insertion
	 * 
	 */
	Long getNumero();

}
