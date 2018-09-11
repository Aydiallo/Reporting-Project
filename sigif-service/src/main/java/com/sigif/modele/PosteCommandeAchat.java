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

import com.sigif.converter.StatutPosteCAConverter;
import com.sigif.enumeration.StatutPosteCA;

/**
 * Entité représentant la table poste de commande d'achat.
 */
@Entity
@Table(name = "poste_commande_achat")
public class PosteCommandeAchat extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = 3683198000513139392L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCA", nullable = false)
    private CommandeAchat commandeAchat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDA")
    private DemandeAchat demandeAchat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDAPoste")
    private PosteDemandeAchat posteDemandeAchat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServiceBeneficiaire", nullable = false)
    private ServiceDepensier serviceDepensierByIdServiceBeneficiaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServiceDepensier", nullable = false)
    private ServiceDepensier serviceDepensierByIdServiceDepensier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTypeAchat", nullable = false)
    private TypeAchat typeAchat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUnite")
    private Unite unite;

    @Column(name = "idSAP", nullable = false, length = 5)
    private String idSap;

    @Column(name = "commentaire", length = 50)
    private String commentaire;

    @Column(name = "dateMiseAjour_SAP", nullable = false)
    private Date dateMiseAjourSap;

    @Column(name = "dateLivraison")
    private Date dateLivraison;
    
    
    @Column(name = "reference", nullable = false, length = 18)
    private String reference;

    @Column(name = "designation", nullable = false, length = 60)
    private String designation;

    @Column(name = "quantiteCommandee", nullable = false, precision = 13, scale = 0, columnDefinition = "DECIMAL(13)")
    private long quantiteCommandee;

    @Column(name = "prixUnitaire", nullable = false, precision = 5, scale = 0, columnDefinition = "DECIMAL(5)")
    private int prixUnitaire;

    @Column(name = "quantiteRestante", nullable = false, precision = 13, scale = 0, columnDefinition = "DECIMAL(13)")
    private long quantiteRestante;

    @Column(name = "statut", nullable = false, 
            columnDefinition = "enum('Non réceptionné', 'Partiellement réceptionné','Clôturé')")
    @Convert(converter = StatutPosteCAConverter.class)
    private StatutPosteCA statut;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "posteCommandeAchat")
    private Set<PosteConstatationServiceFait> posteConstatationServiceFaits =
            new HashSet<PosteConstatationServiceFait>(0);
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idLieuStockage")
    private LieuStockage lieuStockage;
    
    public PosteCommandeAchat() {
    }

    public PosteCommandeAchat(CommandeAchat commandeAchat, ServiceDepensier serviceDepensierByIdServiceBeneficiaire,
            ServiceDepensier serviceDepensierByIdServiceDepensier, TypeAchat typeAchat, String idSap,
            Date dateMiseAjourSap, String reference, String designation, long quantiteCommandee, int prixUnitaire,
            long quantiteRestante, StatutPosteCA statut) {
        this.commandeAchat = commandeAchat;
        this.serviceDepensierByIdServiceBeneficiaire = serviceDepensierByIdServiceBeneficiaire;
        this.serviceDepensierByIdServiceDepensier = serviceDepensierByIdServiceDepensier;
        this.typeAchat = typeAchat;
        this.idSap = idSap;
        this.dateMiseAjourSap = dateMiseAjourSap;
        this.reference = reference;
        this.designation = designation;
        this.quantiteCommandee = quantiteCommandee;
        this.prixUnitaire = prixUnitaire;
        this.quantiteRestante = quantiteRestante;
        this.statut = statut;
    }

    public PosteCommandeAchat(CommandeAchat commandeAchat, DemandeAchat demandeAchat,
            PosteDemandeAchat posteDemandeAchat, ServiceDepensier serviceDepensierByIdServiceBeneficiaire,
            ServiceDepensier serviceDepensierByIdServiceDepensier, TypeAchat typeAchat, String idSap,
            String commentaire, Date dateMiseAjourSap, String reference, String designation, long quantiteCommandee,
            Unite unite, int prixUnitaire, long quantiteRestante, StatutPosteCA statut,
            Set<PosteConstatationServiceFait> posteConstatationServiceFaits) {
        this.commandeAchat = commandeAchat;
        this.demandeAchat = demandeAchat;
        this.posteDemandeAchat = posteDemandeAchat;
        this.serviceDepensierByIdServiceBeneficiaire = serviceDepensierByIdServiceBeneficiaire;
        this.serviceDepensierByIdServiceDepensier = serviceDepensierByIdServiceDepensier;
        this.typeAchat = typeAchat;
        this.idSap = idSap;
        this.commentaire = commentaire;
        this.dateMiseAjourSap = dateMiseAjourSap;
        this.reference = reference;
        this.designation = designation;
        this.quantiteCommandee = quantiteCommandee;
        this.unite = unite;
        this.prixUnitaire = prixUnitaire;
        this.quantiteRestante = quantiteRestante;
        this.statut = statut;
        this.posteConstatationServiceFaits = posteConstatationServiceFaits;
    }

    public CommandeAchat getCommandeAchat() {
        return this.commandeAchat;
    }

    public void setCommandeAchat(CommandeAchat commandeAchat) {
        this.commandeAchat = commandeAchat;
    }

    public DemandeAchat getDemandeAchat() {
        return this.demandeAchat;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public PosteDemandeAchat getPosteDemandeAchat() {
        return this.posteDemandeAchat;
    }

    public void setPosteDemandeAchat(PosteDemandeAchat posteDemandeAchat) {
        this.posteDemandeAchat = posteDemandeAchat;
    }

    public ServiceDepensier getServiceDepensierByIdServiceBeneficiaire() {
        return this.serviceDepensierByIdServiceBeneficiaire;
    }

    public void setServiceDepensierByIdServiceBeneficiaire(ServiceDepensier serviceDepensierByIdServiceBeneficiaire) {
        this.serviceDepensierByIdServiceBeneficiaire = serviceDepensierByIdServiceBeneficiaire;
    }

    public ServiceDepensier getServiceDepensierByIdServiceDepensier() {
        return this.serviceDepensierByIdServiceDepensier;
    }

    public void setServiceDepensierByIdServiceDepensier(ServiceDepensier serviceDepensierByIdServiceDepensier) {
        this.serviceDepensierByIdServiceDepensier = serviceDepensierByIdServiceDepensier;
    }

    public TypeAchat getTypeAchat() {
        return this.typeAchat;
    }

    public void setTypeAchat(TypeAchat typeAchat) {
        this.typeAchat = typeAchat;
    }

    public String getIdSap() {
        return this.idSap;
    }

    public void setIdSap(String idSap) {
        this.idSap = idSap;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateMiseAjourSap() {
        return this.dateMiseAjourSap;
    }

    public void setDateMiseAjourSap(Date dateMiseAjourSap) {
        this.dateMiseAjourSap = dateMiseAjourSap;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public long getQuantiteCommandee() {
        return this.quantiteCommandee;
    }

    public void setQuantiteCommandee(long quantiteCommandee) {
        this.quantiteCommandee = quantiteCommandee;
    }

    public Unite getUnite() {
        return this.unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public int getPrixUnitaire() {
        return this.prixUnitaire;
    }

    public void setPrixUnitaire(int prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public long getQuantiteRestante() {
        return this.quantiteRestante;
    }

    public void setQuantiteRestante(long quantiteRestante) {
        this.quantiteRestante = quantiteRestante;
    }

    public StatutPosteCA getStatut() {
        return this.statut;
    }

    public void setStatut(StatutPosteCA statut) {
        this.statut = statut;
    }

    public Set<PosteConstatationServiceFait> getPosteConstatationServiceFaits() {
        return this.posteConstatationServiceFaits;
    }

    public void setPosteConstatationServiceFaits(Set<PosteConstatationServiceFait> posteConstatationServiceFaits) {
        this.posteConstatationServiceFaits = posteConstatationServiceFaits;
    }

	public LieuStockage getLieuStockage() {
		return lieuStockage;
	}

	public void setLieuStockage(LieuStockage lieuStockage) {
		this.lieuStockage = lieuStockage;
	}

}
