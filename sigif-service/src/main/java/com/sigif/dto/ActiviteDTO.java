package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table Activite.
 * 
 * @author Meissa Beye
 * @since 01/06/2017
 *
 */
public class ActiviteDTO extends AbstractDTO implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

    private Date dateCreation;

    private Date dateModification;

    private String description;

    private String perimetreFinancier;

    private Statut statut;

    private ActionDTO action;

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

	public ActionDTO getAction() {
		return action;
	}

	public void setAction(ActionDTO action) {
		this.action = action;
	}    
}
