package com.sigif.modele;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.sigif.enumeration.StatutEchanges;

/**
 * Entité représentant la table des échanges avec Sigif-SAP.
 */
@Entity
@Table(name = "echanges_sap")
public class EchangesSAP extends AbstractModele implements java.io.Serializable {
    /** uid de sérialisation.*/
    private static final long serialVersionUID = 1538083221277107225L;

    /**
     * Date de lancement de l'import/export
     */
    @Column(name = "dateLancement", nullable = false)
    private Date dateLancement;

    /**
     * Date de fin de l'import/export.
     */
    @Column(name = "dateFin")
    private Date dateFin;

    /**
     * Statut du lancement.
     */
    @Column(name = "statut", columnDefinition = "ENUM('EnCours', 'OK', 'KO')")
    @Enumerated(EnumType.STRING)
    private StatutEchanges statut;

    /**
     * Constructeur par défaut.
     */
    public EchangesSAP() {
        super();
    }

    public Date getDateLancement() {
        return dateLancement;
    }

    public void setDateLancement(Date dateLancement) {
        this.dateLancement = dateLancement;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public StatutEchanges getStatut() {
        return statut;
    }

    public void setStatut(StatutEchanges statut) {
        this.statut = statut;
    }
}
