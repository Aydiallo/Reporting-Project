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
 * Entité représentant la table article.
 */

@Entity
@Table(name = "article", uniqueConstraints = @UniqueConstraint(columnNames = "numero"))
public class Article extends AbstractModele implements java.io.Serializable {

    /**
     * UID pour la sérialisation.
     */
    private static final long serialVersionUID = 8429660369803471573L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGMA", nullable = false)
    private GroupeDeMarchandises groupeDeMarchandises;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUnite", nullable = false)
    private Unite unite;

    @Column(name = "numero", unique = true, nullable = false, length = 18)
    private String numero;
    
    @Column(name = "designation", nullable = false, length = 60)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @Column(name = "dateCreation", nullable = false)
    private Date dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Date dateModification;

    public Article() {
    }

    public Article(GroupeDeMarchandises groupeDeMarchandises, Unite unite, String numero, String designation,
            Statut statut, Date dateCreation, Date dateModification) {
        this.groupeDeMarchandises = groupeDeMarchandises;
        this.unite = unite;
        this.numero = numero;
        this.designation = designation;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public GroupeDeMarchandises getGroupeDeMarchandises() {
        return this.groupeDeMarchandises;
    }

    public void setGroupeDeMarchandises(GroupeDeMarchandises groupeDeMarchandises) {
        this.groupeDeMarchandises = groupeDeMarchandises;
    }

    public Unite getUnite() {
        return this.unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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
