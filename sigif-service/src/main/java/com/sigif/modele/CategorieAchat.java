package com.sigif.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sigif.enumeration.Statut;

/**
 * Entité représentant la table catégories d'achat.
 */

@Entity
@Table(name = "categorie_achat")
public class CategorieAchat extends AbstractModele implements Serializable {

    private static final long serialVersionUID = -8858143288866334594L;

    @Column(nullable = false, length = 1)
    private String code;

    @Column(nullable = false)
    private Date dateCreation;

    @Column(nullable = false)
    private Date dateModification;

    @Column(nullable = false, length = 60)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categorieAchat")
    private List<DemandeAchat> demandeAchats;

    public CategorieAchat() {
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

    public List<DemandeAchat> getDemandeAchats() {
        return this.demandeAchats;
    }

    public void setDemandeAchats(List<DemandeAchat> demandeAchats) {
        this.demandeAchats = demandeAchats;
    }

}