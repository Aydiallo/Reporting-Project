package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;

import com.sigif.enumeration.StatutAvancementCSF;
import com.sigif.enumeration.StatutCertificationCSF;

/**
 * DTO pour les CSF.
 * 
 * @author Mickael Beaupoil
 *
 */
public class ConstatationServiceFaitDTO extends AbstractDTO implements Serializable {
    /**
     * UID de sérialisation.
     */
    private static final long serialVersionUID = 8888863473650884847L;

    private CommandeAchatDTO commandeAchat;

    private String numeroDossier;

    private Date dateReception;

    private String commentaire;

    private int createur;

    private int modificateur;

    private Date dateCreation;

    private Date dateModification;

    private String idSap;

    private Date dateCreationSap;

    private Date dateMiseAjourSap;

    private StatutAvancementCSF statutAvancement;

    private StatutCertificationCSF statutCertification;
    
    private String loginVerrou;

    private Date dateVerrou;

    public CommandeAchatDTO getCommandeAchat() {
        return commandeAchat;
    }

    public void setCommandeAchat(CommandeAchatDTO commandeAchat) {
        this.commandeAchat = commandeAchat;
    }

    public String getNumeroDossier() {
        return numeroDossier;
    }

    public void setNumeroDossier(String numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public Date getDateReception() {
        return dateReception;
    }

    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getCreateur() {
        return createur;
    }

    public void setCreateur(int createur) {
        this.createur = createur;
    }

    public int getModificateur() {
        return modificateur;
    }

    public void setModificateur(int modificateur) {
        this.modificateur = modificateur;
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

    public String getIdSap() {
        return idSap;
    }

    public void setIdSap(String idSap) {
        this.idSap = idSap;
    }

    public Date getDateCreationSap() {
        return dateCreationSap;
    }

    public void setDateCreationSap(Date dateCreationSap) {
        this.dateCreationSap = dateCreationSap;
    }

    public Date getDateMiseAjourSap() {
        return dateMiseAjourSap;
    }

    public void setDateMiseAjourSap(Date dateMiseAjourSap) {
        this.dateMiseAjourSap = dateMiseAjourSap;
    }

    public StatutAvancementCSF getStatutAvancement() {
        return statutAvancement;
    }

    public void setStatutAvancement(StatutAvancementCSF statutAvancement) {
        this.statutAvancement = statutAvancement;
    }

    public StatutCertificationCSF getStatutCertification() {
        return statutCertification;
    }

    public void setStatutCertification(StatutCertificationCSF statutCertification) {
        this.statutCertification = statutCertification;
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
    
}
