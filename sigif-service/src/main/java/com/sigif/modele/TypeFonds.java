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
 * Entité représentant la table type de fonds.
 */
@Entity
@Table(name = "type_fonds", uniqueConstraints = @UniqueConstraint(columnNames = { "perimetreFinancier", "code" }))
public class TypeFonds extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = -7997892898580517289L;

    @Column(name = "perimetreFinancier", nullable = false, length = 4)
    private String perimetreFinancier;

    @Column(name = "code", nullable = false, length = 2)
    private String code;

    @Column(name = "designation", nullable = false, length = 20)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @Column(name = "dateCreation", nullable = false)
    private Date dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Date dateModification;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeFonds")
    private Set<Fonds> setFonds = new HashSet<Fonds>(0);

    public TypeFonds() {
    }

    public TypeFonds(String perimetreFinancier, String code, String designation, Statut statut, Date dateCreation,
            Date dateModification) {
        this.perimetreFinancier = perimetreFinancier;
        this.code = code;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public TypeFonds(String perimetreFinancier, String code, String designation, Statut statut, Date dateCreation,
            Date dateModification, Set<Fonds> fondses) {
        this.perimetreFinancier = perimetreFinancier;
        this.code = code;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.setFonds = fondses;
    }

    public String getPerimetreFinancier() {
        return this.perimetreFinancier;
    }

    public void setPerimetreFinancier(String perimetreFinancier) {
        this.perimetreFinancier = perimetreFinancier;
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
    
    public Set<Fonds> getFondses() {
        return this.setFonds;
    }

    public void setFondses(Set<Fonds> fondses) {
        this.setFonds = fondses;
    }

}
