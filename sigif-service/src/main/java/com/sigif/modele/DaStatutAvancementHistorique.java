package com.sigif.modele;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sigif.converter.StatutAvancementDAConverter;
import com.sigif.enumeration.StatutAvancementDA;

/**
 * Entité représentant la table historisant le statut d'avancement des DA.
 */
@Entity
@Table(name = "da_statut_avancement_historique")
public class DaStatutAvancementHistorique extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = -1540712845280388336L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDA", nullable = false)
    private DemandeAchat demandeAchat;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "statutAvancement", nullable = false, columnDefinition = "enum('Brouillon', 'En attente d''envoi',"
            + "'Envoyée à l''ordonnateur', 'En attente de traitement','Traitement en cours','Traitement terminé')")
    @Convert(converter = StatutAvancementDAConverter.class)
    private StatutAvancementDA statutAvancement;
    
    public DaStatutAvancementHistorique() {
    }

    public DaStatutAvancementHistorique(DemandeAchat demandeAchat, Date date, StatutAvancementDA statutAvancement) {
        this.demandeAchat = demandeAchat;
        this.date = date;
        this.statutAvancement = statutAvancement;
    }

    public DemandeAchat getDemandeAchat() {
        return this.demandeAchat;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StatutAvancementDA getStatutAvancement() {
        return this.statutAvancement;
    }

    public void setStatutAvancement(StatutAvancementDA statutAvancement) {
        this.statutAvancement = statutAvancement;
    }

}
