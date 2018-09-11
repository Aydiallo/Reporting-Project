package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.sigif.enumeration.StatutReceptionCA;
import com.sigif.modele.Devise;

/**
 * DTO de la table commande d'achat
 *
 */
public class CommandeAchatDTO extends AbstractDTO implements Serializable {

    private static final long serialVersionUID = -1822547046253438428L;

    private CategorieAchatDTO categorieAchat;

    private Devise devise;

    private TypeCommandeDTO typeCommande;

    private String idSap;

    private Date dateCreationSap;

    private Date dateMiseAjourSap;

    private String description;

    private StatutReceptionCA statut;

    private Set<ConstatationServiceFaitDTO> constatationServiceFaits;

    public CommandeAchatDTO() {
    }

    public CommandeAchatDTO(CategorieAchatDTO categorieAchat, Devise devise, TypeCommandeDTO typeCommande, String idSap,
            Date dateCreationSap, Date dateMiseAjourSap, StatutReceptionCA statut) {
        this.categorieAchat = categorieAchat;
        this.devise = devise;
        this.typeCommande = typeCommande;
        this.idSap = idSap;
        this.dateCreationSap = dateCreationSap;
        this.dateMiseAjourSap = dateMiseAjourSap;
        this.statut = statut;
    }

    public CommandeAchatDTO(CategorieAchatDTO categorieAchat, Devise devise, TypeCommandeDTO typeCommande, String idSap,
            Date dateCreationSap, Date dateMiseAjourSap, String description, StatutReceptionCA statut,
            Set<ConstatationServiceFaitDTO> constatationServiceFaits) {
        this.categorieAchat = categorieAchat;
        this.devise = devise;
        this.typeCommande = typeCommande;
        this.idSap = idSap;
        this.dateCreationSap = dateCreationSap;
        this.dateMiseAjourSap = dateMiseAjourSap;
        this.description = description;
        this.statut = statut;
        this.constatationServiceFaits = constatationServiceFaits;
    }

    public CategorieAchatDTO getCategorieAchat() {
        return this.categorieAchat;
    }

    public void setCategorieAchat(CategorieAchatDTO categorieAchat) {
        this.categorieAchat = categorieAchat;
    }

    public Devise getDevise() {
        return this.devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public TypeCommandeDTO getTypeCommande() {
        return this.typeCommande;
    }

    public void setTypeCommande(TypeCommandeDTO typeCommande) {
        this.typeCommande = typeCommande;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatutReceptionCA getStatut() {
        return this.statut;
    }

    public void setStatut(StatutReceptionCA statut) {
        this.statut = statut;
    }

    public Set<ConstatationServiceFaitDTO> getConstatationServiceFaits() {
        return this.constatationServiceFaits;
    }

    public void setConstatationServiceFaits(Set<ConstatationServiceFaitDTO> constatationServiceFaits) {
        this.constatationServiceFaits = constatationServiceFaits;
    }

}
