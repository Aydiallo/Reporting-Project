package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table Immobilisation.
 * 
 * @author Meissa Beye
 * @since 15/06/2017
 *
 */
public class ImmobilisationDTO extends AbstractDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4354917451992671769L;

	private CategorieImmobilisationDTO categorieImmobilisation;

	private UniteDTO unite;

	private String code;

	private String designation;

	private Statut statut;

	private Date dateCreation;

	private Date dateModification;

	public CategorieImmobilisationDTO getCategorieImmobilisation() {
		return categorieImmobilisation;
	}

	public void setCategorieImmobilisation(CategorieImmobilisationDTO categorieImmobilisation) {
		this.categorieImmobilisation = categorieImmobilisation;
	}

	public UniteDTO getUnite() {
		return unite;
	}

	public void setUnite(UniteDTO unite) {
		this.unite = unite;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

}
