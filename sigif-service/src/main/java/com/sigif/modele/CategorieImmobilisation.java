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
 * Entité représentant la table catégorie d'immobilisation.
 */

@Entity
@Table(name = "categorie_immobilisation", uniqueConstraints = { @UniqueConstraint(columnNames = "code"),
        @UniqueConstraint(columnNames = "designation") })
public class CategorieImmobilisation extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = -8069314865042375675L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTypeImmo", nullable = false)
    private TypeImmobilisation typeImmobilisation;

    @Column(name = "code", unique = true, nullable = false, length = 8)
    private String code;

    @Column(name = "designation", unique = true, nullable = false, length = 50)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @Column(name = "dateCreation", nullable = false)
    private Date dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Date dateModification;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categorieImmobilisation")
    private Set<Immobilisation> immobilisations = new HashSet<Immobilisation>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categorieImmobilisation")
    private Set<PosteDemandeAchat> postesDemandeAchat = new HashSet<PosteDemandeAchat>(0);

    public CategorieImmobilisation() {
    }

    public CategorieImmobilisation(TypeImmobilisation typeImmobilisation, String code, String designation,
            Statut statut, Date dateCreation, Date dateModification) {
        this.typeImmobilisation = typeImmobilisation;
        this.code = code;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public CategorieImmobilisation(TypeImmobilisation typeImmobilisation, String code, String designation,
            Statut statut, Date dateCreation, Date dateModification, Set<Immobilisation> immobilisations,
            Set<PosteDemandeAchat> posteDemandeAchats) {
        this.typeImmobilisation = typeImmobilisation;
        this.code = code;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.immobilisations = immobilisations;
        this.postesDemandeAchat = posteDemandeAchats;
    }
    
    public TypeImmobilisation getTypeImmobilisation() {
        return this.typeImmobilisation;
    }

    public void setTypeImmobilisation(TypeImmobilisation typeImmobilisation) {
        this.typeImmobilisation = typeImmobilisation;
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
    
    public Set<Immobilisation> getImmobilisations() {
        return this.immobilisations;
    }

    public void setImmobilisations(Set<Immobilisation> immobilisations) {
        this.immobilisations = immobilisations;
    }
    
    public Set<PosteDemandeAchat> getPosteDemandeAchats() {
        return this.postesDemandeAchat;
    }

    public void setPosteDemandeAchats(Set<PosteDemandeAchat> posteDemandeAchats) {
        this.postesDemandeAchat = posteDemandeAchats;
    }

}
