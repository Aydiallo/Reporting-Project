package com.sigif.dto;

import java.util.Date;
import java.util.List;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table categorie_achat.
 * @author Mamadou Ndir
 * 23 mai 2017 00:11:12
 */
public class CategorieAchatDTO extends AbstractDTO {
	    private String code;

	    private Date dateCreation;

	    private Date dateModification;
	    
	    private String designation;

	    private Statut statut;

	    private List<DemandeAchatDTO> demandeAchats;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
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

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}

		public Statut getStatut() {
			return statut;
		}

		public void setStatut(Statut statut) {
			this.statut = statut;
		}

		public List<DemandeAchatDTO> getDemandeAchats() {
			return demandeAchats;
		}

		public void setDemandeAchats(List<DemandeAchatDTO> demandeAchats) {
			this.demandeAchats = demandeAchats;
		}
}
