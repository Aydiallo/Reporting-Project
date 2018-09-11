package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table Article.
 * 
 * @author Meissa Beye
 * @since 15/06/2017
 *
 */
public class ArticleDTO extends AbstractDTO implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 7476912568086797998L;

	private GroupeDeMarchandisesDTO groupeDeMarchandises;

	private UniteDTO unite;

	private String numero;

	private String designation;

	private Statut statut;

	private Date dateCreation;

	private Date dateModification;

	public GroupeDeMarchandisesDTO getGroupeDeMarchandises() {
		return groupeDeMarchandises;
	}

	public void setGroupeDeMarchandises(GroupeDeMarchandisesDTO groupeDeMarchandises) {
		this.groupeDeMarchandises = groupeDeMarchandises;
	}

	public UniteDTO getUnite() {
		return unite;
	}

	public void setUnite(UniteDTO unite) {
		this.unite = unite;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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
