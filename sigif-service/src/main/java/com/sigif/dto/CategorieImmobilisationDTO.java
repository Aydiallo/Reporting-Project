package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table Categorie_Immobilisation.
 * 
 * @author Meissa Beye
 * @since 14/05/2017
 *
 */
public class CategorieImmobilisationDTO extends AbstractDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7426747982211583297L;

	private TypeImmobilisationDTO typeImmobilisation;

	private String code;

	private String designation;

	private Statut statut;

	private Date dateCreation;

	private Date dateModification;

	private Set<ImmobilisationDTO> immobilisations;

	private Set<PosteDemandeAchatDTO> postesDemandeAchat;

	public TypeImmobilisationDTO getTypeImmobilisation() {
		return typeImmobilisation;
	}

	public void setTypeImmobilisation(TypeImmobilisationDTO typeImmobilisation) {
		this.typeImmobilisation = typeImmobilisation;
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

	public Set<ImmobilisationDTO> getImmobilisations() {
		return immobilisations;
	}

	public void setImmobilisations(Set<ImmobilisationDTO> immobilisations) {
		this.immobilisations = immobilisations;
	}

	public Set<PosteDemandeAchatDTO> getPostesDemandeAchat() {
		return postesDemandeAchat;
	}

	public void setPostesDemandeAchat(Set<PosteDemandeAchatDTO> postesDemandeAchat) {
		this.postesDemandeAchat = postesDemandeAchat;
	}

}
