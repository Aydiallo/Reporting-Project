package com.sigif.dto;

import java.util.Date;
import java.util.Set;

import com.sigif.enumeration.StatutPosteCA;
import com.sigif.modele.Unite;

/**
 * DTO représentant un poste de commande d'achat.
 * 
 * @author Mickael Beaupoil
 *
 */
public class PosteCommandeAchatDTO extends AbstractDTO {
    private CommandeAchatDTO commandeAchat;

    private DemandeAchatDTO demandeAchat;

    private PosteDemandeAchatDTO posteDemandeAchat;

    private ServiceDepensierDTO serviceDepensierByIdServiceBeneficiaire;

    private ServiceDepensierDTO serviceDepensierByIdServiceDepensier;

    private TypeAchatDTO typeAchat;

    private String idSap;

    private String commentaire;

    private Date dateMiseAjourSap;
    
    private Date dateLivraison;

    private String reference;

    private String designation;

    private long quantiteCommandee;

    private Unite unite;

    private int prixUnitaire;

    private long quantiteRestante;

    private StatutPosteCA statut;

    private Set<PosteConstatationServiceFaitDTO> posteConstatationServiceFaits;

    
    public Date getDateLivraison() {
		return dateLivraison;
	}

	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	public CommandeAchatDTO getCommandeAchat() {
        return commandeAchat;
    }

    public void setCommandeAchat(CommandeAchatDTO commandeAchat) {
        this.commandeAchat = commandeAchat;
    }

    public DemandeAchatDTO getDemandeAchat() {
        return demandeAchat;
    }

    public void setDemandeAchat(DemandeAchatDTO demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public PosteDemandeAchatDTO getPosteDemandeAchat() {
        return posteDemandeAchat;
    }

    public void setPosteDemandeAchat(PosteDemandeAchatDTO posteDemandeAchat) {
        this.posteDemandeAchat = posteDemandeAchat;
    }

    public ServiceDepensierDTO getServiceDepensierByIdServiceBeneficiaire() {
        return serviceDepensierByIdServiceBeneficiaire;
    }

    public void setServiceDepensierByIdServiceBeneficiaire(
            ServiceDepensierDTO serviceDepensierByIdServiceBeneficiaire) {
        this.serviceDepensierByIdServiceBeneficiaire = serviceDepensierByIdServiceBeneficiaire;
    }

    public ServiceDepensierDTO getServiceDepensierByIdServiceDepensier() {
        return serviceDepensierByIdServiceDepensier;
    }

    public void setServiceDepensierByIdServiceDepensier(ServiceDepensierDTO serviceDepensierByIdServiceDepensier) {
        this.serviceDepensierByIdServiceDepensier = serviceDepensierByIdServiceDepensier;
    }

    public TypeAchatDTO getTypeAchat() {
        return typeAchat;
    }

    public void setTypeAchat(TypeAchatDTO typeAchat) {
        this.typeAchat = typeAchat;
    }

    public String getIdSap() {
        return idSap;
    }

    public void setIdSap(String idSap) {
        this.idSap = idSap;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateMiseAjourSap() {
        return dateMiseAjourSap;
    }

    public void setDateMiseAjourSap(Date dateMiseAjourSap) {
        this.dateMiseAjourSap = dateMiseAjourSap;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public long getQuantiteCommandee() {
        return quantiteCommandee;
    }

    public void setQuantiteCommandee(long quantiteCommandee) {
        this.quantiteCommandee = quantiteCommandee;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public int getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(int prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public long getQuantiteRestante() {
        return quantiteRestante;
    }

    public void setQuantiteRestante(long quantiteRestante) {
        this.quantiteRestante = quantiteRestante;
    }

    public StatutPosteCA getStatut() {
        return statut;
    }

    public void setStatut(StatutPosteCA statut) {
        this.statut = statut;
    }

    public Set<PosteConstatationServiceFaitDTO> getPosteConstatationServiceFaits() {
        return posteConstatationServiceFaits;
    }

    public void setPosteConstatationServiceFaits(Set<PosteConstatationServiceFaitDTO> posteConstatationServiceFaits) {
        this.posteConstatationServiceFaits = posteConstatationServiceFaits;
    }
}
