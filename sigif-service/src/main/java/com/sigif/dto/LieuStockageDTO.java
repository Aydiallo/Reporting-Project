package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table LieuStockage.
 * 
 * @author Meissa Beye
 * @since 14/06/2017
 *
 */
public class LieuStockageDTO extends AbstractDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2899972420779116411L;

	private String code;

	private String division;

	private String designation;

	private Statut statut;

	private Date dateCreation;

	private Date dateModification;

	private Set<PosteDemandeAchatDTO> posteDemandeAchats = new HashSet<PosteDemandeAchatDTO>(0);
	
	private Set<PosteConstatationServiceFaitDTO> posteCSFs = new HashSet<PosteConstatationServiceFaitDTO>(0);

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
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

	public Set<PosteDemandeAchatDTO> getPosteDemandeAchats() {
		return posteDemandeAchats;
	}

	public void setPosteDemandeAchats(Set<PosteDemandeAchatDTO> posteDemandeAchats) {
		this.posteDemandeAchats = posteDemandeAchats;
	}

	public Set<PosteConstatationServiceFaitDTO> getPosteCSFs() {
		return posteCSFs;
	}

	public void setPosteCSFs(Set<PosteConstatationServiceFaitDTO> posteCSFs) {
		this.posteCSFs = posteCSFs;
	}

}
