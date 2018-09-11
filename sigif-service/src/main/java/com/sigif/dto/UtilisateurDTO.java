package com.sigif.dto;

import java.util.Date;
import java.util.List;

import com.sigif.enumeration.Civilite;
import com.sigif.enumeration.Statut;

/**
 * DTO de la table utilisateur
 *
 */
public class UtilisateurDTO extends AbstractDTO {

	private String idSAP;
	
	private String login;

	private String password;
	
	private Civilite civilite;
	
	private String nom;
	
	private String prenom;
	
	private String telephone;
	
	private String courriel;
	
	private boolean compteActif;
	
	private Date dateDerniereConnexion;
	
	private boolean motDePasseGenere;
	
	private Date dateCreation;
	
	private Date dateModification;
    
    private boolean avecCompte;
	
	private Statut statut;
	
	private List<ProfilDTO> profils; 
	
    private List<DemandeAchatDTO> demandeAchatsCreees;
    
    private List<DemandeAchatDTO> demandeAchatsDemandees;

    private List<DemandeAchatDTO> demandeAchatsModifiees;

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
	
	
	
	public String getIdSAP() {
		return idSAP;
	}

	public void setIdSAP(String idSAP) {
		this.idSAP = idSAP;
	}

	public Civilite getCivilite() {
		return civilite;
	}

	public void setCivilite(Civilite civilite) {
		this.civilite = civilite;
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

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public List<ProfilDTO> getProfils() {
		return profils;
	}

	public void setProfils(List<ProfilDTO> profils) {
		this.profils = profils;
	}
	

	public boolean isAvecCompte() {
		return avecCompte;
	}

	public void setAvecCompte(boolean avecCompte) {
		this.avecCompte = avecCompte;
	}
    
    public List<DemandeAchatDTO> getDemandeAchatsCreees() {
        return demandeAchatsCreees;
    }

    public void setDemandeAchatsCreees(List<DemandeAchatDTO> demandeAchatsCreees) {
        this.demandeAchatsCreees = demandeAchatsCreees;
    }

    public List<DemandeAchatDTO> getDemandeAchatsDemandees() {
        return demandeAchatsDemandees;
    }

    public void setDemandeAchatsDemandees(List<DemandeAchatDTO> demandeAchatsDemandees) {
        this.demandeAchatsDemandees = demandeAchatsDemandees;
    }

    public List<DemandeAchatDTO> getDemandeAchatsModifiees() {
        return demandeAchatsModifiees;
    }

    public void setDemandeAchatsModifiees(List<DemandeAchatDTO> demandeAchatsModifiees) {
        this.demandeAchatsModifiees = demandeAchatsModifiees;
    }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Utilisateur{");
		sb.append("id=").append(getId());
		sb.append(", login='").append(login).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
