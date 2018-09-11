package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table service dépensier
 *
 */
public class ServiceDepensierDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = 8712706110453872051L;

    private String code;

    private String designation;

    private String perimetreFinancier;

    private Statut statut;

    private Date dateCreation;

    private Date dateModification;

    private List<ProfilDTO> profils;

    private List<DemandeAchatDTO> demandeAchats;

    private MinistereDTO ministere;
    
    private ProgrammeDTO programme;

    private List<PosteDemandeAchatDTO> postesDemandeAchat;

    private List<PosteCommandeAchatDTO> postesCommandeAchatBeneficaires;

    private List<PosteCommandeAchatDTO> postesCommandeAchatDepensier;

    
    public ProgrammeDTO getProgramme() {
		return programme;
	}

	public void setProgramme(ProgrammeDTO programme) {
		this.programme = programme;
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

    public String getPerimetreFinancier() {
        return perimetreFinancier;
    }

    public void setPerimetreFinancier(String perimetreFinancier) {
        this.perimetreFinancier = perimetreFinancier;
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

    public List<ProfilDTO> getProfils() {
        return profils;
    }

    public void setProfils(List<ProfilDTO> profils) {
        this.profils = profils;
    }

    public MinistereDTO getMinistere() {
        return ministere;
    }

    public void setMinistere(MinistereDTO ministere) {
        this.ministere = ministere;
    }

    public List<DemandeAchatDTO> getDemandeAchats() {
        return demandeAchats;
    }

    public void setDemandeAchats(List<DemandeAchatDTO> demandeAchats) {
        this.demandeAchats = demandeAchats;
    }

    public List<PosteDemandeAchatDTO> getPostesDemandeAchat() {
        return postesDemandeAchat;
    }

    public void setPostesDemandeAchat(List<PosteDemandeAchatDTO> postesDemandeAchat) {
        this.postesDemandeAchat = postesDemandeAchat;
    }

    public List<PosteCommandeAchatDTO> getPostesCommandeAchatBeneficaires() {
        return postesCommandeAchatBeneficaires;
    }

    public void setPostesCommandeAchatBeneficaires(List<PosteCommandeAchatDTO> postesCommandeAchatBeneficaires) {
        this.postesCommandeAchatBeneficaires = postesCommandeAchatBeneficaires;
    }

    public List<PosteCommandeAchatDTO> getPostesCommandeAchatDepensier() {
        return postesCommandeAchatDepensier;
    }

    public void setPostesCommandeAchatDepensier(List<PosteCommandeAchatDTO> postesCommandeAchatDepensier) {
        this.postesCommandeAchatDepensier = postesCommandeAchatDepensier;
    }
}
