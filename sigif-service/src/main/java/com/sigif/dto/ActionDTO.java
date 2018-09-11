package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table Action.
 * 
 * @author Meissa Beye
 * @since 31/05/2017
 *
 */
public class ActionDTO extends AbstractDTO implements Serializable {
   
	private static final long serialVersionUID = 3131262527008266275L;

    private String code;

    private Date dateCreation;

    private Date dateModification;

    private String description;

    private String perimetreFinancier;

    private Statut statut;

    private ProgrammeDTO programme;

    private List<ActiviteDTO> activites;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public ProgrammeDTO getProgramme() {
		return programme;
	}

	public void setProgramme(ProgrammeDTO programme) {
		this.programme = programme;
	}

	public List<ActiviteDTO> getActivites() {
		return activites;
	}

	public void setActivites(List<ActiviteDTO> activites) {
		this.activites = activites;
	}
    
}
