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
import javax.persistence.UniqueConstraint;

import com.sigif.enumeration.Statut;

/**
 * Entité représentant la table immobilisation.
 */
@Entity
@Table(name = "immobilisation", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Immobilisation extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = -427934919465934079L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategorieImmo", nullable = false)
    private CategorieImmobilisation categorieImmobilisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUnite", nullable = false)
    private Unite unite;

    @Column(name = "code", unique = true, nullable = false, length = 12)
    private String code;

    @Column(name = "designation", nullable = false, length = 50)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @Column(name = "dateCreation", nullable = false)
    private Date dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Date dateModification;

    public Immobilisation() {
    }

    public Immobilisation(CategorieImmobilisation categorieImmobilisation, Unite unite, String code, String designation,
            Statut statut, Date dateCreation, Date dateModification) {
        this.categorieImmobilisation = categorieImmobilisation;
        this.unite = unite;
        this.code = code;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public CategorieImmobilisation getCategorieImmobilisation() {
        return this.categorieImmobilisation;
    }

    public void setCategorieImmobilisation(CategorieImmobilisation categorieImmobilisation) {
        this.categorieImmobilisation = categorieImmobilisation;
    }

    public Unite getUnite() {
        return this.unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
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

}
