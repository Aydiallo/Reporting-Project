package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;

public class CreditDisponibleDTO extends AbstractDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8289094593530290306L;
	
	private String centrefinancier;
	
	private String domainefonctionnel;
	
	private String comptebugetaire;
	
	private Double creditdispo;
	
	private String typecredit;
	
	private Date dateCreation;
	
	private Date dateModification;

	public String getCentrefinancier() {
		return centrefinancier;
	}

	public void setCentrefinancier(String centrefinancier) {
		this.centrefinancier = centrefinancier;
	}

	public String getDomainefonctionnel() {
		return domainefonctionnel;
	}

	public void setDomainefonctionnel(String domainefonctionnel) {
		this.domainefonctionnel = domainefonctionnel;
	}

	public String getComptebugetaire() {
		return comptebugetaire;
	}

	public void setComptebugetaire(String comptebugetaire) {
		this.comptebugetaire = comptebugetaire;
	}

	public Double getCreditdispo() {
		return creditdispo;
	}

	public void setCreditdispo(Double creditdispo) {
		this.creditdispo = creditdispo;
	}

	public String getTypecredit() {
		return typecredit;
	}

	public void setTypecredit(String typecredit) {
		this.typecredit = typecredit;
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
