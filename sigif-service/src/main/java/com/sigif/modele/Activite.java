package com.sigif.modele;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sigif.enumeration.Statut;

/**
 * Entité représentant la table activité.
 */
@Entity
@Table(name = "activite")
public class Activite extends AbstractModele implements Serializable {

    private static final long serialVersionUID = 1838386572863644329L;

    @Column(nullable = false, length = 16)
    private String code;

    @Column(nullable = false)
    private Date dateCreation;

    @Column(nullable = false)
    private Date dateModification;

    @Column(nullable = false, length = 50)
    private String description;

    @Column(nullable = false, length = 4)
    private String perimetreFinancier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAction", nullable = false)
    private Action action;

    public Activite() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPerimetreFinancier() {
        return this.perimetreFinancier;
    }

    public void setPerimetreFinancier(String perimetreFinancier) {
        this.perimetreFinancier = perimetreFinancier;
    }

    public Statut getStatut() {
        return this.statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

}