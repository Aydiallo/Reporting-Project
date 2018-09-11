package com.sigif.modele;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;

import com.sigif.enumeration.Statut;

/**
 * Entité représentant la table ministère.
 */
@Entity
@Table(name = "ministere")
public class Ministere extends AbstractModele {

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private String perimetreFinancier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @Column(nullable = false)
    private Date dateCreation;

    @Column(nullable = false)
    private Date dateModification;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ministere")
    private List<DemandeAchat> demandeAchats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ministere")
    private List<Profil> profils;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ministere")
    private List<ServiceDepensier> servicesDepensiers;

    public Ministere() {
		super();
	}

	@Column(nullable = false, length = 16)
    public String getCode() {
        return code;
    }

    public List<DemandeAchat> getDemandeAchats() {
        return demandeAchats;
    }

    public void setDemandeAchats(List<DemandeAchat> demandeAchats) {
        this.demandeAchats = demandeAchats;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(nullable = false, length = 40)
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Column(nullable = false, length = 4)
    public String getPerimetreFinancier() {
        return perimetreFinancier;
    }

    public void setPerimetreFinancier(String perimetreFinancier) {
        this.perimetreFinancier = perimetreFinancier;
    }

    @Column(nullable = false)
    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    @Column(nullable = false, length = 14)
    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Column(nullable = false, length = 14)
    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public List<Profil> getProfils() {
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }
    
    public List<ServiceDepensier> getServicesDepensiers() {
        return servicesDepensiers;
    }

    public void setServicesDepensiers(List<ServiceDepensier> servicesDepensiers) {
        this.servicesDepensiers = servicesDepensiers;
    }

    @Override
    public String toString() {
        return "Ministere [code=" + code + ", designation=" + designation + ", perimetreFinancier=" + perimetreFinancier
                + ", statut=" + statut + ", dateCreation=" + dateCreation + ", dateModification=" + dateModification
                + "]";
    }

}
