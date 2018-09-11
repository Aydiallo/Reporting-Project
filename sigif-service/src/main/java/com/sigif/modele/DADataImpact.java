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

import com.sigif.enumeration.Impact;
import com.sigif.enumeration.ImpactedData;

/**
 * Entité représentant la table d'analyse détaillée des impacts de données pour les DA.
 */
@Entity
@Table(name = "da_data_impact")
public class DADataImpact extends AbstractModele implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4870720523950756031L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDA", nullable = false)
	private DemandeAchat demandeAchat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPosteDA")
	private PosteDemandeAchat posteDA;

	@Column(name = "impacted_data", columnDefinition = "enum('idTypeAchat','idProgramme','idAction', 'idActivite',"
			+ "'reference','designation','idCategorieImmo','idUnite','idVilleLivraison','idRegionLivraison',"
			+ "'idLieuStockage','idServiceBeneficiaire','idFonds','demandeur','idMinistere','idServiceDepensier',"
			+ "'idCategorieAchat')")
	@Enumerated(EnumType.STRING)
	private ImpactedData impactedData;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "enum('Modified', 'Deleted')")
	private Impact impact;

	@Column(nullable = false)
	private Date date;

	public DADataImpact() {
		super();
	}

	public DemandeAchat getDemandeAchat() {
		return demandeAchat;
	}

	public void setDemandeAchat(DemandeAchat demandeAchat) {
		this.demandeAchat = demandeAchat;
	}

	public PosteDemandeAchat getPosteDA() {
		return posteDA;
	}

	public void setPosteDA(PosteDemandeAchat posteDA) {
		this.posteDA = posteDA;
	}

	public ImpactedData getImpactedData() {
		return impactedData;
	}

	public void setImpactedData(ImpactedData impactedData) {
		this.impactedData = impactedData;
	}

	public Impact getImpact() {
		return impact;
	}

	public void setImpact(Impact impact) {
		this.impact = impact;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
