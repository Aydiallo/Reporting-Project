package com.sigif.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.DADataImpact;

/**
 * Classe d'accès aux données des impacts.
 * 
 * @author Mamadou Ndir
 * @since 7 juin 2017 17:38:12
 *
 */
@Repository("dADataImpactDAO")
public interface DADataImpactDAO extends AbstractDAO<DADataImpact> {

	/**
	 * Permet de retourner les alertes qui concernent l'entete d'une DA.
	 * 
	 * @param numDA
	 *            numéro de la DA
	 * @return Returne une liste d'alertes
	 * @throws TechnicalException Si la connexion à la BD échoue  
	 */
	List<DADataImpact> searchAlertDA(String numDA) throws TechnicalException;

	/**
	 * Permet de retourner les alertes qui concernent les postes d'une DA.
	 * 
	 * @param numDA
	 *            numéro de la DA
	 * @param numPosteDA
	 *            numero poste DA
	 * @return Returne une liste d'alertes
	 * @throws TechnicalException  Si la connexion à la BD échoue 
	 */
	List<DADataImpact> searchAlertPosteDA(String numDA, String numPosteDA) throws TechnicalException;

	/**
	 * Supprime table data_impact par numero DA ou numéro poste DA.
	 * 
	 * @param idDA id de la DA
	 * @param idPosteDa id du posteDa
	 * @author Mamadou Ndir
	 * @throws TechnicalException Si la connexion à la BD échoue  
	 */
	void cleanDataImpact(int idDA, int idPosteDa) throws TechnicalException;

}
