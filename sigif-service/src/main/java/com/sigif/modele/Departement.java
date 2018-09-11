package com.sigif.modele;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sigif.enumeration.Civilite;
import com.sigif.enumeration.Statut;

/**
 * Entité représentant la table departement.
 */
@Entity
@Table(name = "departement")
public class Departement extends AbstractModele implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8274944262637568889L;

	 @Column(nullable = false, length = 4)
	    private String code;

	 
	 @Column(nullable = false, length = 30)
	    private String designation;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "idRegion", nullable = false)
	    private Region region;
	 
	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false, columnDefinition = "enum('actif', 'inactif')")
	    private Statut statut;
	 
	    @Column(nullable = false)
	    private Date dateCreation;

	    @Column(nullable = false)
	    private Date dateModification;

		public Departement() {
			super();
			// TODO Auto-generated constructor stub
		}
		 public Departement(Region region, String code, String designation, Statut statut, Date dateCreation,
            Date dateModification) {
        this.region = region;
        this.code = code;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
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

		public Region getRegion() {								
			return region;									 
		}

		public void setRegion(Region region) {							 
			this.region = region;
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
