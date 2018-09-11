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
 * Entité représentant la table région.
 */
@Entity
@Table(name = "region", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Region extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = 8356486291492061289L;

    @Column(name = "code", unique = true, nullable = false, length = 5)
    private String code;

    @Column(name = "designation", nullable = false, length = 30)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @Column(name = "dateCreation", nullable = false)
    private Date dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Date dateModification;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
    private Set<PosteDemandeAchat> postesDemandeAchat = new HashSet<PosteDemandeAchat>(0);

 

    public Region() {
    }

    public Region(String code, String designation, Statut statut, Date dateCreation, Date dateModification) {
        this.code = code;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public Region(String code, String designation, Statut statut, Date dateCreation, Date dateModification,
            Set<PosteDemandeAchat> posteDemandeAchats) {
        this.code = code;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.postesDemandeAchat = posteDemandeAchats;
        
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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
        return this.postesDemandeAchat;
    }

    public void setPosteDemandeAchats(Set<PosteDemandeAchat> posteDemandeAchats) {
        this.postesDemandeAchat = posteDemandeAchats;
    }
    
    

}
