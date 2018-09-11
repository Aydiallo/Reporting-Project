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
 * Entité représentant la table unité.
 */
@Entity
@Table(name = "unite", uniqueConstraints = { @UniqueConstraint(columnNames = "code"),
        @UniqueConstraint(columnNames = "nom") })
public class Unite extends AbstractModele implements java.io.Serializable {

    /**
     * UID de sérialisation.
     */
    private static final long serialVersionUID = 5408614239310367358L;

    @Column(name = "code", unique = true, nullable = false, length = 3)
    private String code;

    @Column(name = "nom", unique = true, nullable = false, length = 30)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @Column(name = "dateCreation", nullable = false)
    private Date dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Date dateModification;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unite")
    private Set<Immobilisation> immobilisations = new HashSet<Immobilisation>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unite")
    private Set<Article> articles = new HashSet<Article>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unite")
    private Set<PosteDemandeAchat> postesDemandeAchat = new HashSet<PosteDemandeAchat>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unite")
    private Set<PosteCommandeAchat> postesCommandeAchat = new HashSet<PosteCommandeAchat>(0);

    public Unite() {
    }

    public Unite(String code, String nom, Statut statut, Date dateCreation, Date dateModification) {
        this.code = code;
        this.nom = nom;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public Unite(String code, String nom, Statut statut, Date dateCreation, Date dateModification,
            Set<Immobilisation> immobilisations, Set<Article> articles, Set<PosteDemandeAchat> posteDemandeAchats) {
        this.code = code;
        this.nom = nom;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.immobilisations = immobilisations;
        this.articles = articles;
        this.postesDemandeAchat = posteDemandeAchats;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<PosteDemandeAchat> getPostesDemandeAchat() {
        return postesDemandeAchat;
    }

    public void setPostesDemandeAchat(Set<PosteDemandeAchat> postesDemandeAchat) {
        this.postesDemandeAchat = postesDemandeAchat;
    }

    public Set<PosteCommandeAchat> getPostesCommandeAchat() {
        return postesCommandeAchat;
    }

    public void setPostesCommandeAchat(Set<PosteCommandeAchat> postesCommandeAchat) {
        this.postesCommandeAchat = postesCommandeAchat;
    }

}
