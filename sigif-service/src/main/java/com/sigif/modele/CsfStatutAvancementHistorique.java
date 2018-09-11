package com.sigif.modele;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sigif.converter.StatutAvancementCSFConverter;
import com.sigif.enumeration.StatutAvancementCSF;

/**
 * Entité représentant la table historisant le statut d'avancement des CSF.
 */
@Entity
@Table(name = "csf_statut_avancement_historique")
public class CsfStatutAvancementHistorique extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = 7961743815130427292L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCSF", nullable = false)
    private ConstatationServiceFait constatationServiceFait;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "statutAvancement", nullable = false, 
            columnDefinition = "enum('Brouillon', 'En attente d''envoi','Envoyée à l''ordonnateur',"
            + "'En attente de traitement','Traitement en cours','Traitement terminé')")
    @Convert(converter = StatutAvancementCSFConverter.class)
    private StatutAvancementCSF statutAvancement;

    public CsfStatutAvancementHistorique() {
    }

    public CsfStatutAvancementHistorique(ConstatationServiceFait constatationServiceFait, Date date,
            StatutAvancementCSF statutAvancement) {
        this.constatationServiceFait = constatationServiceFait;
        this.date = date;
        this.statutAvancement = statutAvancement;
    }

    public ConstatationServiceFait getConstatationServiceFait() {
        return this.constatationServiceFait;
    }

    public void setConstatationServiceFait(ConstatationServiceFait constatationServiceFait) {
        this.constatationServiceFait = constatationServiceFait;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StatutAvancementCSF getStatutAvancement() {
        return this.statutAvancement;
    }

    public void setStatutAvancement(StatutAvancementCSF statutAvancement) {
        this.statutAvancement = statutAvancement;
    }
}
