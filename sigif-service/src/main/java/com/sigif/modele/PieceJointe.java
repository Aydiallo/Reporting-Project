package com.sigif.modele;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sigif.enumeration.TypePJ;

/**
 * Entité représentant la table pièces jointes.
 */
@Entity
@Table(name = "piece_jointe")
public class PieceJointe extends AbstractModele implements Serializable {
    /**
     * Serial UID pour la sérialisation.
     */
    private static final long serialVersionUID = -5203898527137639306L;

    /**
     * Identifiant de la pièce jointe : concaténation num DA/CSF poste DA/CSF si
     * nécessaire et id
     */
    @Column(nullable = false, length = 25)
    private String idPieceJointe;

    @Column(nullable = false, length = 75)
    private String nomFichier;

    @Column(nullable = false, length = 40)
    private String intitule;

    @Column(nullable = false, length = 500, columnDefinition = "VARCHAR(500)")
    private String emplacement;

    @Column(length = 25)
    private String nature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('DA','Poste_DA','CSF','Poste_CSF')")
    private TypePJ type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDa")
    private DemandeAchat demandeAchat;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPosteDa")
    private PosteDemandeAchat posteDemandeAchat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCsf")
    private ConstatationServiceFait constatationServiceFait;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPosteCsf")
    private PosteConstatationServiceFait posteCsf;
    
    public PieceJointe() {
    }

    public String getIdPieceJointe() {
        return idPieceJointe;
    }

    public void setIdPieceJointe(String idPieceJointe) {
        this.idPieceJointe = idPieceJointe;
    }

    public String getEmplacement() {
        return this.emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getIntitule() {
        return this.intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getNature() {
        return this.nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getNomFichier() {
        return this.nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public TypePJ getType() {
        return this.type;
    }

    public void setType(TypePJ type) {
        this.type = type;
    }

    public DemandeAchat getDemandeAchat() {
        return demandeAchat;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public PosteDemandeAchat getPosteDemandeAchat() {
        return posteDemandeAchat;
    }

    public void setPosteDemandeAchat(PosteDemandeAchat posteDemandeAchat) {
        this.posteDemandeAchat = posteDemandeAchat;
    }

    public PosteConstatationServiceFait getPosteCsf() {
        return posteCsf;
    }

    public void setPosteCsf(PosteConstatationServiceFait posteCsf) {
        this.posteCsf = posteCsf;
    }

    public ConstatationServiceFait getConstatationServiceFait() {
        return constatationServiceFait;
    }

    public void setConstatationServiceFait(ConstatationServiceFait constatationServiceFait) {
        this.constatationServiceFait = constatationServiceFait;
    }
}