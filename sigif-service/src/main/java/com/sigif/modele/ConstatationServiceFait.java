package com.sigif.modele;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.sigif.converter.StatutAvancementCSFConverter;
import com.sigif.converter.StatutCertificationCSFConverter;
import com.sigif.enumeration.StatutAvancementCSF;
import com.sigif.enumeration.StatutCertificationCSF;

/**
 * Entité représentant la table constatation de service fait.
 */
@Entity
@Table(name = "constatation_service_fait", uniqueConstraints = { @UniqueConstraint(columnNames = "idSAP"),
        @UniqueConstraint(columnNames = "numeroDossier") })
public class ConstatationServiceFait extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = 2485094096036438734L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCA", nullable = false)
    private CommandeAchat commandeAchat;

    @Column(name = "numeroDossier", unique = true, nullable = false, length = 12)
    private String numeroDossier;

    @Column(name = "dateReception", nullable = false)
    private Date dateReception;

    @Column(name = "commentaire", length = 25)
    private String commentaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createur", nullable = false)
    private Utilisateur createur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modificateur", nullable = false)
    private Utilisateur modificateur;

    @Column(name = "dateCreation", nullable = false)
    private Date dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Date dateModification;

    @Column(name = "idSAP", unique = true, length = 10)
    private String idSap;

    @Column(name = "dateCreation_SAP", columnDefinition = "DATE")
    private Date dateCreationSap;

    @Column(name = "dateMiseAjour_SAP")
    private Date dateMiseAjourSap;

    @Column(name = "statutAvancement", nullable = false, columnDefinition = "enum('Brouillon', 'En attente d''envoi',"
            + "'Envoyée à l''ordonnateur','En attente de traitement','Traitement en cours','Traitement terminé')")
    @Convert(converter = StatutAvancementCSFConverter.class)
    private StatutAvancementCSF statutAvancement;

    @Column(name = "statutCertification", columnDefinition = "enum('Constatation annulée', "
            + "'Validation en cours','Validation refusée','En attente de certification',"
            + "'Certification en cours','Certifiée','Certification refusée','Certification partielle')")
    @Convert(converter = StatutCertificationCSFConverter.class)
    private StatutCertificationCSF statutCertification;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "constatationServiceFait")
    private Set<PieceJointe> piecesJointes = new HashSet<PieceJointe>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "constatationServiceFait")
    private Set<CsfStatutAvancementHistorique> csfStatutAvancementHistoriques =
            new HashSet<CsfStatutAvancementHistorique>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "constatationServiceFait")
    private Set<PosteConstatationServiceFait> posteConstatationServiceFait =
            new HashSet<PosteConstatationServiceFait>(0);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServiceDepensier")
    private ServiceDepensier serviceDepensier;

    @Column(length = 40)
    private String loginVerrou;

    @Column()
    private Date dateVerrou;

    public ConstatationServiceFait() {
    }

    public ConstatationServiceFait(CommandeAchat commandeAchat, String numeroDossier, Date dateReception,
            String commentaire, Utilisateur createur, Utilisateur modificateur, Date dateCreation,
            Date dateModification, StatutAvancementCSF statutAvancement, StatutCertificationCSF statutCertification) {
        this.commandeAchat = commandeAchat;
        this.numeroDossier = numeroDossier;
        this.dateReception = dateReception;
        this.createur = createur;
        this.modificateur = modificateur;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.statutAvancement = statutAvancement;
        this.statutCertification = statutCertification;
        this.commentaire = commentaire;
    }

    public ConstatationServiceFait(CommandeAchat commandeAchat, String numeroDossier, Date dateReception,
            String commentaire, Utilisateur createur, Utilisateur modificateur, Date dateCreation,
            Date dateModification, String idSap, Date dateCreationSap, Date dateMiseAjourSap,
            StatutAvancementCSF statutAvancement, StatutCertificationCSF statutCertification,
            Set<CsfStatutAvancementHistorique> csfStatutAvancementHistoriques) {
        this.commandeAchat = commandeAchat;
        this.numeroDossier = numeroDossier;
        this.dateReception = dateReception;
        this.commentaire = commentaire;
        this.createur = createur;
        this.modificateur = modificateur;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.idSap = idSap;
        this.dateCreationSap = dateCreationSap;
        this.dateMiseAjourSap = dateMiseAjourSap;
        this.statutAvancement = statutAvancement;
        this.statutCertification = statutCertification;
        this.csfStatutAvancementHistoriques = csfStatutAvancementHistoriques;
    }

    public CommandeAchat getCommandeAchat() {
        return this.commandeAchat;
    }

    public void setCommandeAchat(CommandeAchat commandeAchat) {
        this.commandeAchat = commandeAchat;
    }

    public String getNumeroDossier() {
        return this.numeroDossier;
    }

    public void setNumeroDossier(String numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public Date getDateReception() {
        return this.dateReception;
    }

    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Utilisateur getCreateur() {
        return this.createur;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    public Utilisateur getModificateur() {
        return this.modificateur;
    }

    public void setModificateur(Utilisateur modificateur) {
        this.modificateur = modificateur;
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

    public String getIdSap() {
        return this.idSap;
    }

    public void setIdSap(String idSap) {
        this.idSap = idSap;
    }

    public Date getDateCreationSap() {
        return this.dateCreationSap;
    }

    public void setDateCreationSap(Date dateCreationSap) {
        this.dateCreationSap = dateCreationSap;
    }

    public Date getDateMiseAjourSap() {
        return this.dateMiseAjourSap;
    }

    public void setDateMiseAjourSap(Date dateMiseAjourSap) {
        this.dateMiseAjourSap = dateMiseAjourSap;
    }

    public StatutAvancementCSF getStatutAvancement() {
        return this.statutAvancement;
    }

    public void setStatutAvancement(StatutAvancementCSF statutAvancement) {
        this.statutAvancement = statutAvancement;
    }

    public StatutCertificationCSF getStatutCertification() {
        return this.statutCertification;
    }

    public void setStatutCertification(StatutCertificationCSF statutCertification) {
        this.statutCertification = statutCertification;
    }

    public Set<CsfStatutAvancementHistorique> getCsfStatutAvancementHistoriques() {
        return this.csfStatutAvancementHistoriques;
    }

    public void setCsfStatutAvancementHistoriques(Set<CsfStatutAvancementHistorique> csfStatutAvancementHistoriques) {
        this.csfStatutAvancementHistoriques = csfStatutAvancementHistoriques;
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

    public Set<PieceJointe> getPiecesJointes() {
        return piecesJointes;
    }

    public void setPiecesJointes(Set<PieceJointe> piecesJointes) {
        this.piecesJointes = piecesJointes;
    }

    public Set<PosteConstatationServiceFait> getPosteConstatationServiceFait() {
        return posteConstatationServiceFait;
    }

    public void setPosteConstatationServiceFait(Set<PosteConstatationServiceFait> posteConstatationServiceFait) {
        this.posteConstatationServiceFait = posteConstatationServiceFait;
    }

    public ServiceDepensier getServiceDepensier() {
        return serviceDepensier;
    }

    public void setServiceDepensier(ServiceDepensier serviceDepensier) {
        this.serviceDepensier = serviceDepensier;
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
