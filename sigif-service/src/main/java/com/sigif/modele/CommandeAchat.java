package com.sigif.modele;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

import com.sigif.converter.StatutReceptionCAConverter;
import com.sigif.enumeration.StatutReceptionCA;

/**
 * Entité représentant la table commande d'achat.
 */
@Entity
@Table(name = "commande_achat", uniqueConstraints = @UniqueConstraint(columnNames = "idSAP"))
public class CommandeAchat extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = -858179261049709421L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategorieAchat", nullable = false)
    private CategorieAchat categorieAchat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDevise", nullable = false)
    private Devise devise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTypeCommande", nullable = false)
    private TypeCommande typeCommande;

    @Column(name = "idSAP", unique = true, nullable = false, length = 10)
    private String idSap;

	@Column(name = "dateCreation_SAP", nullable = false, columnDefinition = "DATE")
    private Date dateCreationSap;

    @Column(name = "dateMiseAjour_SAP", nullable = false)
    private Date dateMiseAjourSap;

    @Column(name = "description", length = 25)
    private String description;

    @Column(name = "statut", nullable = false, columnDefinition = "enum('Non réceptionnée', "
            + "'Partiellement réceptionnée','Réceptionnée','Non validée')")
    @Convert(converter = StatutReceptionCAConverter.class)
    private StatutReceptionCA statut;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commandeAchat")
    private List<PosteCommandeAchat> posteCommandeAchats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commandeAchat")
    private Set<ConstatationServiceFait> constatationsServiceFait = new HashSet<ConstatationServiceFait>(0);

    public CommandeAchat() {
    }

    public CommandeAchat(CategorieAchat categorieAchat, Devise devise, TypeCommande typeCommande, String idSap,
            Date dateCreationSap, Date dateMiseAjourSap, StatutReceptionCA statut) {
        this.categorieAchat = categorieAchat;
        this.devise = devise;
        this.typeCommande = typeCommande;
        this.idSap = idSap;
        this.dateCreationSap = dateCreationSap;
        this.dateMiseAjourSap = dateMiseAjourSap;
        this.statut = statut;
    }

    public CommandeAchat(CategorieAchat categorieAchat, Devise devise, TypeCommande typeCommande, String idSap,
            Date dateCreationSap, Date dateMiseAjourSap, String description, StatutReceptionCA statut,
            Set<ConstatationServiceFait> constatationServiceFaits) {
        this.categorieAchat = categorieAchat;
        this.devise = devise;
        this.typeCommande = typeCommande;
        this.idSap = idSap;
        this.dateCreationSap = dateCreationSap;
        this.dateMiseAjourSap = dateMiseAjourSap;
        this.description = description;
        this.statut = statut;
        this.constatationsServiceFait = constatationServiceFaits;
    }

    public CategorieAchat getCategorieAchat() {
        return this.categorieAchat;
    }

    public void setCategorieAchat(CategorieAchat categorieAchat) {
        this.categorieAchat = categorieAchat;
    }

    public Devise getDevise() {
        return this.devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public TypeCommande getTypeCommande() {
        return this.typeCommande;
    }

    public void setTypeCommande(TypeCommande typeCommande) {
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
    public Set<ConstatationServiceFait> getConstatationServiceFaits() {
        return this.constatationsServiceFait;
    }

    public void setConstatationServiceFaits(Set<ConstatationServiceFait> constatationServiceFaits) {
        this.constatationsServiceFait = constatationServiceFaits;
    }
    
    public List<PosteCommandeAchat> getPosteCommandeAchats() {
		return posteCommandeAchats;
	}

	public void setPosteCommandeAchats(List<PosteCommandeAchat> posteCommandeAchats) {
		this.posteCommandeAchats = posteCommandeAchats;
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
