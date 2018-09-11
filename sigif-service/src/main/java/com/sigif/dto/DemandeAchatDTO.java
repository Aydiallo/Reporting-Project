package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.sigif.enumeration.EtatDonnee;
import com.sigif.enumeration.StatutApprobationDA;
import com.sigif.enumeration.StatutAvancementDA;
import com.sigif.modele.DaStatutAvancementHistorique;

/**
 * DTO de la table demande d'achat
 *
 */
public class DemandeAchatDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String numeroDossier;

    private Date dateCreation;

    private Date dateModification;

    private String titre;

    private String objet;

    private String idSAP;

    private Date dateCreationSAP;

    private Date dateMiseAjourSAP;

    private StatutAvancementDA statutAvancement;

    private StatutApprobationDA statutApprobation;

    private EtatDonnee etatDonnee;

    private UtilisateurDTO createur;

    private UtilisateurDTO modificateur;

    private UtilisateurDTO demandeur;

    private MinistereDTO ministere;

    private ServiceDepensierDTO serviceDepensier;

    private ProgrammeDTO programme;

    private CategorieAchatDTO categorieAchat;

    private PieceJointeDTO pieceJointe;

    private Set<PosteDemandeAchatDTO> postesDemandeAchat;

    private Set<DaStatutAvancementHistorique> statutsAvancementDemandeAchat;

    public DemandeAchatDTO() {
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

    public UtilisateurDTO getCreateur() {
        return createur;
    }

    public void setCreateur(UtilisateurDTO createur) {
        this.createur = createur;
    }

    public UtilisateurDTO getModificateur() {
        return modificateur;
    }

    public void setModificateur(UtilisateurDTO modificateur) {
        this.modificateur = modificateur;
    }

    public UtilisateurDTO getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(UtilisateurDTO demandeur) {
        this.demandeur = demandeur;
    }


    public Date getDateCreationSAP() {
        return dateCreationSAP;
    }

    public void setDateCreationSAP(Date dateCreationSAP) {
        this.dateCreationSAP = dateCreationSAP;
    }

    public Date getDateMiseAjourSAP() {
        return dateMiseAjourSAP;
    }

    public void setDateMiseAjourSAP(Date dateMiseAjourSAP) {
        this.dateMiseAjourSAP = dateMiseAjourSAP;
    }

    public Set<PosteDemandeAchatDTO> getPostesDemandeAchat() {
        return postesDemandeAchat;
    }

    public void setPostesDemandeAchat(Set<PosteDemandeAchatDTO> postesDemandeAchat) {
        this.postesDemandeAchat = postesDemandeAchat;
    }

    public Set<DaStatutAvancementHistorique> getStatutsAvancementDemandeAchat() {
        return statutsAvancementDemandeAchat;
    }

    public void setStatutsAvancementDemandeAchat(Set<DaStatutAvancementHistorique> statutsAvancementDemandeAchat) {
        this.statutsAvancementDemandeAchat = statutsAvancementDemandeAchat;
    }
    
    
	public MinistereDTO getMinistere() {
		return ministere;
	}

	public void setMinistere(MinistereDTO ministere) {
		this.ministere = ministere;
	}

	public ServiceDepensierDTO getServiceDepensier() {
		return serviceDepensier;
	}

	public void setServiceDepensier(ServiceDepensierDTO serviceDepensier) {
		this.serviceDepensier = serviceDepensier;
	}

	public ProgrammeDTO getProgramme() {
		return programme;
	}

	public void setProgramme(ProgrammeDTO programme) {
		this.programme = programme;
	}

	public CategorieAchatDTO getCategorieAchat() {
		return categorieAchat;
	}

	public void setCategorieAchat(CategorieAchatDTO categorieAchat) {
		this.categorieAchat = categorieAchat;
	}

	public PieceJointeDTO getPieceJointe() {
		return pieceJointe;
	}

	public void setPieceJointe(PieceJointeDTO pieceJointe) {
		this.pieceJointe = pieceJointe;
	}

}
