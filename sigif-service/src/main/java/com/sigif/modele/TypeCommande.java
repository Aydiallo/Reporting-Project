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
 * Entité représentant la table type de commande.
 */
@Entity
@Table(name = "type_commande", uniqueConstraints = { @UniqueConstraint(columnNames = "code"),
        @UniqueConstraint(columnNames = "designation") })
public class TypeCommande extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = -36652900446177404L;

    @Column(name = "code", unique = true, nullable = false, length = 4)
    private String code;

    @Column(name = "designation", unique = true, nullable = false, length = 20)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @Column(name = "dateCreation", nullable = false)
    private Date dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Date dateModification;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeCommande")
    private Set<CommandeAchat> commandesAchat = new HashSet<CommandeAchat>(0);

    public TypeCommande() {
    }

    public TypeCommande(String code, String designation, Statut statut, Date dateCreation, Date dateModification) {
        this.code = code;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public TypeCommande(String code, String designation, Statut statut, Date dateCreation, Date dateModification,
            Set<CommandeAchat> commandeAchats) {
        this.code = code;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.commandesAchat = commandeAchats;
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

    public Set<CommandeAchat> getCommandeAchats() {
        return this.commandesAchat;
    }

    public void setCommandeAchats(Set<CommandeAchat> commandeAchats) {
        this.commandesAchat = commandeAchats;
    }
}
