package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table Ville.
 * 
 * @author Meissa Beye
 * @since 02/05/2017
 *
 */
public class VilleDTO extends AbstractDTO implements Serializable {
  
	 /**
	 * 
	 */
	private static final long serialVersionUID = 8758761634290951854L;

	private DepartementDTO departement;

    private String code;

    private String designation;

    private Statut statut;

    private Date dateCreation;

    private Date dateModification;

    private Set<PosteDemandeAchatDTO> postesDemandeAchat;

	public DepartementDTO getDepartement() {
		return departement;
	}

	public void setRegion(DepartementDTO departement) {
		this.departement = departement;
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

	public Set<PosteDemandeAchatDTO> getPostesDemandeAchat() {
		return postesDemandeAchat;
	}

	public void setPostesDemandeAchat(Set<PosteDemandeAchatDTO> postesDemandeAchat) {
		this.postesDemandeAchat = postesDemandeAchat;
	}	    
}
