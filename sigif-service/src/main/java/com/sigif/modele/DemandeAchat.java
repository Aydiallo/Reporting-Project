package com.sigif.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sigif.converter.StatutApprobationDAConverter;
import com.sigif.converter.StatutAvancementDAConverter;
import com.sigif.enumeration.EtatDonnee;
import com.sigif.enumeration.StatutApprobationDA;
import com.sigif.enumeration.StatutAvancementDA;

/**
 * Entité représentant la table demande d'achat.
 */
@Entity
@Table(name = "demande_achat")
public class DemandeAchat extends AbstractModele implements Serializable {

    /**
     * Serial UID pour la sérialisation.
     */
    private static final long serialVersionUID = -3110499614930952440L;

    @Column(nullable = false, length = 12)
    private String numeroDossier;

    @Column(nullable = false)
    private Date dateCreation;

    @Column(nullable = false)
    private Date dateModification;

    @Column(length = 40)
    private String titre;

    @Column(length = 240)
    private String objet;

    @Column(length = 10)
    private String idSAP;

    @Column(name = "dateCreation_SAP", columnDefinition = "DATE")
    private Date dateCreationSAP;

    @Column(name = "dateMiseAjour_SAP")
    private Date dateMiseAjourSAP;

    @Column(nullable = false, columnDefinition = "enum('Brouillon', 'En attente d''envoi','Envoyée à l''ordonnateur',"
            + "'En attente de traitement','Traitement en cours','Traitement terminé')")
    @Convert(converter = StatutAvancementDAConverter.class)
    private StatutAvancementDA statutAvancement;

    @Column(columnDefinition = "enum('Refusée', 'Approuvée', 'Approuvée partiellement')")
    @Convert(converter = StatutApprobationDAConverter.class)
    private StatutApprobationDA statutApprobation;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('Ok', 'Warning','Error')")
    private EtatDonnee etatDonnee;

    @Column(length = 40)
    private String loginVerrou;

    @Column()
    private Date dateVerrou;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createur", nullable = false)
    private Utilisateur createur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modificateur", nullable = false)
    private Utilisateur modificateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demandeur")
    private Utilisateur demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMinistere")
    private Ministere ministere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServiceDepensier")
    private ServiceDepensier serviceDepensier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProgramme")
    private Programme programme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategorieAchat")
    private CategorieAchat categorieAchat;
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "demandeAchat")
    private PieceJointe pieceJointe;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "demandeAchat")
    private Set<PosteDemandeAchat> postesDemandeAchat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "demandeAchat")
    private Set<DaStatutAvancementHistorique> statutsAvancementDemandeAchat;

    public DemandeAchat() {
    }

    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateCreationSAP() {
        return this.dateCreationSAP;
    }

    public void setDateCreationSAP(Date dateCreationSAP) {
        this.dateCreationSAP = dateCreationSAP;
    }

    public Date getDateMiseAjourSAP() {
        return this.dateMiseAjourSAP;
    }

    public void setDateMiseAjourSAP(Date dateMiseAjourSAP) {
        this.dateMiseAjourSAP = dateMiseAjourSAP;
    }

    public Date getDateModification() {
        return this.dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public EtatDonnee getEtatDonnee() {
        return this.etatDonnee;
    }

    public void setEtatDonnee(EtatDonnee etatDonnee) {
        this.etatDonnee = etatDonnee;
    }

    public String getIdSAP() {
        return this.idSAP;
    }

    public void setIdSAP(String idSAP) {
        this.idSAP = idSAP;
    }

    public String getNumeroDossier() {
        return this.numeroDossier;
    }

    public void setNumeroDossier(String numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public String getObjet() {
        return this.objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public StatutApprobationDA getStatutApprobation() {
        return this.statutApprobation;
    }

    public void setStatutApprobation(StatutApprobationDA statutApprobation) {
        this.statutApprobation = statutApprobation;
    }

    public StatutAvancementDA getStatutAvancement() {
        return this.statutAvancement;
    }

    public void setStatutAvancement(StatutAvancementDA statutAvancement) {
        this.statutAvancement = statutAvancement;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    public Utilisateur getModificateur() {
        return modificateur;
    }

    public void setModificateur(Utilisateur modificateur) {
        this.modificateur = modificateur;
    }

    public Utilisateur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Utilisateur demandeur) {
        this.demandeur = demandeur;
    }

    public Ministere getMinistere() {
        return this.ministere;
    }

    public void setMinistere(Ministere ministere) {
        this.ministere = ministere;
    }

    public ServiceDepensier getServiceDepensier() {
        return this.serviceDepensier;
    }

    public void setServiceDepensier(ServiceDepensier serviceDepensier) {
        this.serviceDepensier = serviceDepensier;
    }

    public Programme getProgramme() {
        return this.programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public CategorieAchat getCategorieAchat() {
        return categorieAchat;
    }

    public void setCategorieAchat(CategorieAchat categorieAchat) {
        this.categorieAchat = categorieAchat;
    }

    public PieceJointe getPieceJointe() {
        return pieceJointe;
    }

    public void setPieceJointe(PieceJointe pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public Set<PosteDemandeAchat> getPostesDemandeAchat() {
        return postesDemandeAchat;
    }

    public void setPostesDemandeAchat(Set<PosteDemandeAchat> postesDemandeAchat) {
        this.postesDemandeAchat = postesDemandeAchat;
    }

    public Set<DaStatutAvancementHistorique> getStatutsAvancementDemandeAchat() {
        return statutsAvancementDemandeAchat;
    }

    public void setStatutsAvancementDemandeAchat(Set<DaStatutAvancementHistorique> statutsAvancementDemandeAchat) {
        this.statutsAvancementDemandeAchat = statutsAvancementDemandeAchat;
    }

    public String getLoginVerrou() {
        return loginVerrou;
    }

    public void setLoginVerrou(String loginVerrou) {
        this.loginVerrou = loginVerrou;
    }

    public Date getDateVerrou() {
        return dateVerrou;
    }

    public void setDateVerrou(Date dateVerrou) {
        this.dateVerrou = dateVerrou;
    }
    
    //
    @Transient
    private boolean selected = true;
    //

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}