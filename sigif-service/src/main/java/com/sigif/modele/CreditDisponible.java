package com.sigif.modele;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entité représentant la table creditdisponible.
 */
@Entity
@Table(name = "creditdisponible")
public class CreditDisponible extends AbstractModele implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8978427080290493428L;


	  @Column(nullable = false, length = 16)
	    private String centrefinancier;
	  
	  @Column(nullable = false, length = 16)
	    private String domainefonctionnel;
	  
	  @Column(nullable = false, length = 24)
	    private String comptebugetaire;
      
	  @Column(nullable = false)
	    private Double creditdispo; 
	
	  @Column(nullable = false, length = 2)
	    private String typecredit;
	  
	  @Column(name = "dateCreation")
	    private Date dateCreation;
	  
	  @Column(name = "dateModification")
	    private Date dateModification;

	public CreditDisponible() {
		super();
		// TODO Auto-generated constructor stub
	}

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
