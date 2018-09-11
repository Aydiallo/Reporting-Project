package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table TypeImmobilisation.
 * 
 * @author Meissa
 * @since 14/06/2017
 *
 */
public class TypeImmobilisationDTO extends AbstractDTO implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -1314050281660161850L;

	private String code;

	private String designation;

	private Statut statut;

	private Date dateCreation;

	private Date dateModification;

	private Set<CategorieImmobilisationDTO> categoriesImmobilisation;

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

	public Set<CategorieImmobilisationDTO> getCategoriesImmobilisation() {
		return categoriesImmobilisation;
	}

	public void setCategoriesImmobilisation(Set<CategorieImmobilisationDTO> categoriesImmobilisation) {
		this.categoriesImmobilisation = categoriesImmobilisation;
	}

}
