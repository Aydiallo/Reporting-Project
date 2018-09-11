package com.sigif.dto;

import java.util.Date;
import java.util.List;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table ministère
 *
 */
public class MinistereDTO extends AbstractDTO {
	private String code;

	private String designation;

	private String perimetreFinancier;

	private Statut statut;

	private Date dateCreation;

	private Date dateModification;
	
    private List<DemandeAchatDTO> demandeAchats;

	private List<ProfilDTO> profils;

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

	public List<DemandeAchatDTO> getDemandeAchats() {
        return demandeAchats;
    }

    public void setDemandeAchats(List<DemandeAchatDTO> demandeAchats) {
        this.demandeAchats = demandeAchats;
    }

    public List<ProfilDTO> getProfils() {
		return profils;
	}

	public void setProfils(List<ProfilDTO> profils) {
		this.profils = profils;
	}
}
