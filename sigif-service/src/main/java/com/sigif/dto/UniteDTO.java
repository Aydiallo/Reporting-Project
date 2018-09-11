package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table Unite.
 * 
 * @author Meissa Beye
 * @since 01/06/2017
 *
 */
public class UniteDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = 3131262527008266275L;

    private String code;

    private String nom;

    private Statut statut;

    private Date dateCreation;

    private Date dateModification;

    private Set<ImmobilisationDTO> immobilisations;

    private Set<ArticleDTO> articles;

    private Set<PosteDemandeAchatDTO> postesDemandeAchat;

    private Set<PosteCommandeAchatDTO> postesCommandeAchat;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public Set<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(Set<ArticleDTO> articles) {
		this.articles = articles;
	}

	public Set<PosteDemandeAchatDTO> getPostesDemandeAchat() {
		return postesDemandeAchat;
	}

	public void setPostesDemandeAchat(Set<PosteDemandeAchatDTO> postesDemandeAchat) {
		this.postesDemandeAchat = postesDemandeAchat;
	}

	public Set<PosteCommandeAchatDTO> getPostesCommandeAchat() {
		return postesCommandeAchat;
	}

	public void setPostesCommandeAchat(Set<PosteCommandeAchatDTO> postesCommandeAchat) {
		this.postesCommandeAchat = postesCommandeAchat;
	}
}
