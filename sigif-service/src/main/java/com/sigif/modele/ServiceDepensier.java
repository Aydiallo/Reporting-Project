package com.sigif.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sigif.enumeration.Statut;

/**
 * Entité représentant la table service dépensier.
 */
@Entity
@Table(name = "service_depensier")
public class ServiceDepensier extends AbstractModele implements Serializable {

    private static final long serialVersionUID = -3895570427686555611L;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private String perimetreFinancier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @Column(nullable = false)
    private Date dateCreation;

    @Column(nullable = false)
    private Date dateModification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMinistere", nullable = false)
    private Ministere ministere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProgramme", nullable = false)
    private Programme programme;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceDepensier")
    private List<DemandeAchat> demandeAchats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceDepensier")
    private List<ConstatationServiceFait> constatationsServiceFait;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceDepensier")
    private List<Profil> profils;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceDepensier")
    private List<PosteDemandeAchat> postesDemandeAchat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceDepensierByIdServiceBeneficiaire")
    private List<PosteCommandeAchat> postesCommandeAchatBeneficaires;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceDepensierByIdServiceDepensier")
    private List<PosteCommandeAchat> postesCommandeAchatDepensier;

    public ServiceDepensier() {
		super();
	}

	public List<DemandeAchat> getDemandeAchats() {
        return demandeAchats;
    }

    public void setDemandeAchats(List<DemandeAchat> demandeAchats) {
        this.demandeAchats = demandeAchats;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPerimetreFinancier() {
        return perimetreFinancier;
    }

    public void setPerimetreFinancier(String perimetreFinancier) {
        this.perimetreFinancier = perimetreFinancier;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Ministere getMinistere() {
        return ministere;
    }

    public void setMinistere(Ministere ministere) {
        this.ministere = ministere;
    }

    @Column(nullable = false, length = 14)
    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Column(nullable = false, length = 14)
    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public List<Profil> getProfils() {
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    public List<ConstatationServiceFait> getConstatationsServiceFait() {
        return constatationsServiceFait;
    }

    public void setConstatationsServiceFait(List<ConstatationServiceFait> constatationsServiceFait) {
        this.constatationsServiceFait = constatationsServiceFait;
    }

    public List<PosteDemandeAchat> getPostesDemandeAchat() {
        return postesDemandeAchat;
    }

    public void setPostesDemandeAchat(List<PosteDemandeAchat> postesDemandeAchat) {
        this.postesDemandeAchat = postesDemandeAchat;
    }

    public List<PosteCommandeAchat> getPostesCommandeAchatBeneficaires() {
        return postesCommandeAchatBeneficaires;
    }

    public void setPostesCommandeAchatBeneficaires(List<PosteCommandeAchat> postesCommandeAchatBeneficaires) {
        this.postesCommandeAchatBeneficaires = postesCommandeAchatBeneficaires;
    }

    public List<PosteCommandeAchat> getPostesCommandeAchatDepensier() {
        return postesCommandeAchatDepensier;
    }

    public void setPostesCommandeAchatDepensier(List<PosteCommandeAchat> postesCommandeAchatDepensier) {
        this.postesCommandeAchatDepensier = postesCommandeAchatDepensier;
    }

    @Override
    public String toString() {
        return "ServiceDepensier [code=" + code + ", designation=" + designation + ", perimetreFinancier="
                + perimetreFinancier + ", statut=" + statut + ", dateCreation=" + dateCreation + ", dateModification="
                + dateModification + "]";
    }

}
