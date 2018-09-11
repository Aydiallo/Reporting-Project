package com.sigif.modele;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sigif.enumeration.Statut;

/**
 * Entité représentant la table Fonds.
 */
@Entity
@Table(name = "fonds", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Fonds extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = 2223454345229568540L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTypeFonds", nullable = false)
    private TypeFonds typeFonds;

    @Column(name = "code", unique = true, nullable = false, length = 10)
    private String code;

    @Column(name = "designation", nullable = false, length = 20)
    private String designation;

    @Column(name = "description", length = 40)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @Column(name = "dateCreation", nullable = false)
    private Date dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Date dateModification;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fonds")
    private Set<PosteDemandeAchat> postesDemandeAchat = new HashSet<PosteDemandeAchat>(0);

    public Fonds() {
    }

    public Fonds(TypeFonds typeFonds, String code, String designation, Statut statut, Date dateCreation,
            Date dateModification) {
        this.typeFonds = typeFonds;
        this.code = code;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public Fonds(TypeFonds typeFonds, String code, String designation, String description, Statut statut,
            Date dateCreation, Date dateModification, Set<PosteDemandeAchat> posteDemandeAchats) {
        this.typeFonds = typeFonds;
        this.code = code;
        this.designation = designation;
        this.description = description;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.postesDemandeAchat = posteDemandeAchats;
    }

    public TypeFonds getTypeFonds() {
        return this.typeFonds;
    }

    public void setTypeFonds(TypeFonds typeFonds) {
        this.typeFonds = typeFonds;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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
