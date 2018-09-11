package com.sigif.dao;

import com.sigif.modele.Sequence;

/**
 * Implémentation de la classe d'accès à la séquence.
 * @author Malick Diagne
 */
public interface SequenceDaoInterface {
	/**
	 * Enregistre dans la base l'instance "sequence" de l'objet.
	 * 
	 * @return L'objet enregistré.
	 */
	Sequence persist();
}
