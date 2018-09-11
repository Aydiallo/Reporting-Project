package com.sigif.modele;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sigif.enumeration.Civilite;
import com.sigif.enumeration.Statut;

/**
 * Entité représentant la table utilisateur.
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur extends AbstractModele {

    private String idSAP;

    @Column(nullable = true)
    private String login;

    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('Madame', 'Mademoiselle', 'Monsieur')")
    private Civilite civilite;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private String telephone;

    private String courriel;

    @Column(nullable = false)
    private boolean compteActif;

    @Column(columnDefinition = "DATE")
    private Date dateDerniereConnexion;

    @Column(nullable = false)
    private boolean motDePasseGenere;

    @Column(nullable = false)
    private Date dateCreation;

    @Column(nullable = false)
    private Date dateModification;

    @Column(nullable = false)
    private boolean avecCompte;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
    private List<Profil> profils;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createur")
    private List<DemandeAchat> demandeAchatsCreees;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "demandeur")
    private List<DemandeAchat> demandeAchatsDemandees;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "modificateur")
    private List<DemandeAchat> demandeAchatsModifiees;
    
    public Utilisateur() {
        super();
    }
    
    public List<DemandeAchat> getDemandeAchatsCreees() {
        return demandeAchatsCreees;
    }

    public void setDemandeAchatsCreees(List<DemandeAchat> demandeAchatsCreees) {
        this.demandeAchatsCreees = demandeAchatsCreees;
    }

    public List<DemandeAchat> getDemandeAchatsDemandees() {
        return demandeAchatsDemandees;
    }

    public void setDemandeAchatsDemandees(List<DemandeAchat> demandeAchatsDemandees) {
        this.demandeAchatsDemandees = demandeAchatsDemandees;
    }

    public String getIdSAP() {
        return idSAP;
    }

    public void setIdSAP(String idSAP) {
        this.idSAP = idSAP;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public Date getDateDerniereConnexion() {
        return dateDerniereConnexion;
    }

    public void setDateDerniereConnexion(Date dateDerniereConnexion) {
        this.dateDerniereConnexion = dateDerniereConnexion;
    }

    public boolean isMotDePasseGenere() {
        return motDePasseGenere;
    }

    public void setMotDePasseGenere(boolean motDePasseGenere) {
        this.motDePasseGenere = motDePasseGenere;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCompteActif() {
        return compteActif;
    }

    public void setCompteActif(boolean compteActif) {
        this.compteActif = compteActif;
    }

    public Civilite getCivilite() {
        return civilite;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public List<Profil> getProfils() {
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    public boolean isAvecCompte() {
        return avecCompte;
    }

    public void setAvecCompte(boolean avecCompte) {
        this.avecCompte = avecCompte;
    }

    public List<DemandeAchat> getDemandeAchatsModifiees() {
        return demandeAchatsModifiees;
    }

    public void setDemandeAchatsModifiees(List<DemandeAchat> demandeAchatsModifiees) {
        this.demandeAchatsModifiees = demandeAchatsModifiees;
    }

    /**
     * Retourne une représentation sous forme de chaine de l'entité utilisateur.
     * Les liens vers les profils et les demandes d'achat ne sont pas affichées.
     * 
     * @return une représentation sous forme de chaine de l'entité utilisateur
     */
    @Override
    public String toString() {
        SimpleDateFormat formatDates = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return "Utilisateur [idSAP=" + idSAP + ", login=" + login + ", password=" + password + ", civilite=" + civilite
                + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone + ", courriel=" + courriel
                + ", compteActif=" + compteActif + ", avecCompte=" + avecCompte + ", dateDerniereConnexion="
                + (dateDerniereConnexion == null ? null : formatDates.format(dateDerniereConnexion))
                + ", motDePasseGenere=" + motDePasseGenere + ", dateCreation="
                + (dateCreation == null ? null : formatDates.format(dateCreation)) + ", dateModification="
                + (dateModification == null ? null : formatDates.format(dateModification)) + ", statut=" + statut + "]";
    }

}
