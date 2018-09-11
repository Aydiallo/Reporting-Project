package com.sigif.modele;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sigif.enumeration.Statut;

/**
 * Entité représentant la table lieu de stockage.
 */
@Entity
@Table(name = "lieu_stockage", uniqueConstraints = { @UniqueConstraint(columnNames = "designation"),
        @UniqueConstraint(columnNames = { "code", "division" }) })
public class LieuStockage extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = 3337201495106807303L;

    @Column(name = "code", nullable = false, length = 4)
    private String code;

    @Column(name = "division", nullable = false, length = 2)
    private String division;

    @Column(name = "designation", unique = true, nullable = false, length = 30)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @Column(name = "dateCreation", nullable = false)
    private Date dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Date dateModification;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lieuStockage")
    private Set<PosteDemandeAchat> posteDemandeAchats = new HashSet<PosteDemandeAchat>(0);
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lieuStockage")
    private Set<PosteConstatationServiceFait> posteCSFs = new HashSet<PosteConstatationServiceFait>(0);

    public LieuStockage() {
    }

    public LieuStockage(String code, String division, String designation, Statut statut, Date dateCreation,
            Date dateModification) {
        this.code = code;
        this.division = division;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public LieuStockage(String code, String division, String designation, Statut statut, Date dateCreation,
            Date dateModification, Set<PosteDemandeAchat> posteDemandeAchats) {
        this.code = code;
        this.division = division;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.posteDemandeAchats = posteDemandeAchats;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDivision() {
        return this.division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Statut getStatut() {
        return this.statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return this.dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }
    
    public Set<PosteDemandeAchat> getPosteDemandeAchats() {
        return this.posteDemandeAchats;
    }

    public void setPosteDemandeAchats(Set<PosteDemandeAchat> posteDemandeAchats) {
        this.posteDemandeAchats = posteDemandeAchats;
    }

	public Set<PosteConstatationServiceFait> getPosteCSFs() {
		return posteCSFs;
	}

	public void setPosteCSFs(Set<PosteConstatationServiceFait> posteCSFs) {
		this.posteCSFs = posteCSFs;
	}

    
}
