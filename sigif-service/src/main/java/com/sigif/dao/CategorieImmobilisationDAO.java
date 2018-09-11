package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.CategorieImmobilisation;

/**
 * Classe d'accès aux données des Categories d'immobilisation.
 * @author Meissa Beye
 */
public interface CategorieImmobilisationDAO extends AbstractDAO<CategorieImmobilisation> {

	/**
	 * Retourne la liste des catégories immobilisation au statut actif en
	 * fonction du type d'Immobilisation.
	 * 
	 * @return tous les typeImmos actifs en fonction du typeImmo
	 * @param codeTypeImmo code du tyme d'immobilistation
	 * @throws TechnicalException si la récupération échoue pour une raison technique
	 */
	List<CategorieImmobilisation> getCategorieImmoByTypeImmo(String codeTypeImmo) throws TechnicalException;

}
