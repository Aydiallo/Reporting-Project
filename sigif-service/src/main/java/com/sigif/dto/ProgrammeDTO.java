package com.sigif.dto;

import java.util.Date;
import java.util.List;

import com.sigif.enumeration.Statut;

/**
 * DTO de la table programme
 *
 */
public class ProgrammeDTO extends AbstractDTO {

    private String code;

    private Date dateCreation;

    private Date dateModification;

    private String description;

    private String perimetreFinancier;

    private Statut statut;

    private List<ActionDTO> actions;

    private List<DemandeAchatDTO> demandeAchats;

    private MinistereDTO ministere;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPerimetreFinancier() {
        return perimetreFinancier;
    }

    public void setPerimetreFinancier(String perimetreFinancier) {
        this.perimetreFinancier = perimetreFinancier;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public List<ActionDTO> getActions() {
        return actions;
    }

    public void setActions(List<ActionDTO> actions) {
        this.actions = actions;
    }

    public List<DemandeAchatDTO> getDemandeAchats() {
        return demandeAchats;
    }

    public void setDemandeAchats(List<DemandeAchatDTO> demandeAchats) {
        this.demandeAchats = demandeAchats;
    }

    public MinistereDTO getMinistere() {
        return ministere;
    }

    public void setMinistere(MinistereDTO ministere) {
        this.ministere = ministere;
    }

}
