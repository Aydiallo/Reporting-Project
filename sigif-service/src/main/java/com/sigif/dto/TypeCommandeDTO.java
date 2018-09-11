package com.sigif.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table type_commande.
 * 
 * @author Mickael Beaupoil
 *
 */
public class TypeCommandeDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = 3131262527008266275L;

    private String code;

    private String designation;

    private Statut statut;

    private Date dateCreation;

    private Date dateModification;

    private Set<CommandeAchatDTO> commandesAchat;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Set<CommandeAchatDTO> getCommandesAchat() {
        return commandesAchat;
    }

    public void setCommandesAchat(Set<CommandeAchatDTO> commandesAchat) {
        this.commandesAchat = commandesAchat;
    }
}
