package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table Type de Fonds.
 * 
 * @author Meissa Beye
 * @since 02/05/2017
 *
 */
public class TypeFondsDTO extends AbstractDTO implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -1721341609317831029L;

	private String perimetreFinancier;

	private String code;

	private String designation;

	private Statut statut;

	private Date dateCreation;

	private Date dateModification;

	private Set<FondsDTO> setFonds;

	public String getPerimetreFinancier() {
		return perimetreFinancier;
	}

	public void setPerimetreFinancier(String perimetreFinancier) {
		this.perimetreFinancier = perimetreFinancier;
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

	public Set<FondsDTO> getSetFonds() {
		return setFonds;
	}

	public void setSetFonds(Set<FondsDTO> setFonds) {
		this.setFonds = setFonds;
	}

}
