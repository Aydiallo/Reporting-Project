package com.sigif.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sigif.enumeration.Statut;

/**
 * Entité représentant la table programme.
 */
@Entity
@Table(name = "programme")
public class Programme extends AbstractModele implements Serializable {

    private static final long serialVersionUID = 2779721607388448425L;

    @Column(nullable = false, length = 16)
    private String code;

    @Column(nullable = false)
    private Date dateCreation;

    @Column(nullable = false)
    private Date dateModification;

    @Column(nullable = false, length = 50)
    private String description;

    @Column(nullable = false, length = 4)
    private String perimetreFinancier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('actif', 'inactif')")
    private Statut statut;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "programme")
    private List<Action> actions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "programme")
    private List<DemandeAchat> demandeAchats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMinistere", nullable = false)
    private Ministere ministere;

    public Programme() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPerimetreFinancier() {
        return this.perimetreFinancier;
    }

    public void setPerimetreFinancier(String perimetreFinancier) {
        this.perimetreFinancier = perimetreFinancier;
    }

    public Statut getStatut() {
        return this.statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public List<Action> getActions() {
        return this.actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Action addAction(Action action) {
        getActions().add(action);
        action.setProgramme(this);

        return action;
    }

    public Action removeAction(Action action) {
        getActions().remove(action);
        action.setProgramme(null);

        return action;
    }

    public List<DemandeAchat> getDemandeAchats() {
        return this.demandeAchats;
    }

    public void setDemandeAchats(List<DemandeAchat> demandeAchats) {
        this.demandeAchats = demandeAchats;
    }

    public DemandeAchat addDemandeAchat(DemandeAchat demandeAchat) {
        getDemandeAchats().add(demandeAchat);
        demandeAchat.setProgramme(this);

        return demandeAchat;
    }

    public DemandeAchat removeDemandeAchat(DemandeAchat demandeAchat) {
        getDemandeAchats().remove(demandeAchat);
        demandeAchat.setProgramme(null);

        return demandeAchat;
    }

    public Ministere getMinistere() {
        return this.ministere;
    }

    public void setMinistere(Ministere ministere) {
        this.ministere = ministere;
    }

}