package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table groupe_de_marchandises.
 * 
 * @author Meissa Beye
 * @since 31/05/2017
 *
 */
public class GroupeDeMarchandisesDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = 3131262527008266275L;

    private String code;

    private String designation;

    private Statut statut;

    private Date dateCreation;

    private Date dateModification;

    private List<ArticleDTO> articles;

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

	public List<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleDTO> articles) {
		this.articles = articles;
	}
}
