package com.sigif.modele;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sigif.converter.StatutCertificationPosteCSFConverter;
import com.sigif.enumeration.StatutCertificationPosteCSF;

/**
 * Entité représentant la table poste de constatation de service fait.
 */
@Entity
@Table(name = "poste_constatation_service_fait", uniqueConstraints = @UniqueConstraint(columnNames = "idSAP"))
public class PosteConstatationServiceFait extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = 3379165036475900627L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCAPoste")
    private PosteCommandeAchat posteCommandeAchat;

    @Column(name = "idSAP", unique = true, length = 5)
    private String idSap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCSF", nullable = false)
    private ConstatationServiceFait constatationServiceFait;

    @Column(name = "idCSFPoste", length = 3, nullable = false)
    private String idCsfposte;

    @Column(name = "quantiteRecue", precision = 13, scale = 0, columnDefinition = "DECIMAL(13)")
    private Long quantiteRecue;

    @Column(name = "quantiteAcceptee", precision = 13, scale = 0, columnDefinition = "DECIMAL(13)")
    private Long quantiteAcceptee;

    @Column(name = "commentaire", length = 50)
    private String commentaire;

    @Column(name = "dateMiseAjour_SAP")
    private Date dateMiseAjourSap;

    @Column(name = "statutAvancement", columnDefinition = "enum('Constatation annulée', "
            + "'En attente de validation', 'Validation refusée',"
            + "'En attente de certification', 'Certifié', 'Certification refusée', 'Rectifié')")
    @Convert(converter = StatutCertificationPosteCSFConverter.class)
    private StatutCertificationPosteCSF statutAvancement;

    @Column(name = "motifRejet", length = 20)
    private String motifRejet;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "posteCsf")
    private PieceJointe pieceJointe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idLieuStockage")
    private LieuStockage lieuStockage;

    public PosteConstatationServiceFait() {
    }

    public PosteConstatationServiceFait(PosteCommandeAchat posteCommandeAchat, String idSap,
            ConstatationServiceFait constatationServiceFait, String idCsfposte, Long quantiteRecue,
            Long quantiteAcceptee, String commentaire, StatutCertificationPosteCSF statutAvancement,
            LieuStockage lieuStockage) {
        this.posteCommandeAchat = posteCommandeAchat;
        this.idSap = idSap;
        this.constatationServiceFait = constatationServiceFait;
        this.idCsfposte = idCsfposte;
        this.quantiteRecue = quantiteRecue;
        this.quantiteAcceptee = quantiteAcceptee;
        this.commentaire = commentaire;
        this.lieuStockage = lieuStockage;
        this.statutAvancement = statutAvancement;
    }

    public PosteCommandeAchat getPosteCommandeAchat() {
        return this.posteCommandeAchat;
    }

    public void setPosteCommandeAchat(PosteCommandeAchat posteCommandeAchat) {
        this.posteCommandeAchat = posteCommandeAchat;
    }

    public String getIdSap() {
        return this.idSap;
    }

    public void setIdSap(String idSap) {
        this.idSap = idSap;
    }

    public ConstatationServiceFait getConstatationServiceFait() {
        return constatationServiceFait;
    }

    public void setConstatationServiceFait(ConstatationServiceFait constatationServiceFait) {
        this.constatationServiceFait = constatationServiceFait;
    }

    public String getIdCsfposte() {
        return this.idCsfposte;
    }

    public void setIdCsfposte(String idCsfposte) {
        this.idCsfposte = idCsfposte;
    }

    public Long getQuantiteRecue() {
        return this.quantiteRecue;
    }

    public void setQuantiteRecue(Long quantiteRecue) {
        this.quantiteRecue = quantiteRecue;
    }

    public Long getQuantiteAcceptee() {
        return this.quantiteAcceptee;
    }

    public void setQuantiteAcceptee(Long quantiteAcceptee) {
        this.quantiteAcceptee = quantiteAcceptee;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateMiseAjourSap() {
        return this.dateMiseAjourSap;
    }

    public void setDateMiseAjourSap(Date dateMiseAjourSap) {
        this.dateMiseAjourSap = dateMiseAjourSap;
    }

    public StatutCertificationPosteCSF getStatutAvancement() {
        return this.statutAvancement;
    }

    public void setStatutAvancement(StatutCertificationPosteCSF statutAvancement) {
        this.statutAvancement = statutAvancement;
    }

    public String getMotifRejet() {
        return motifRejet;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    public LieuStockage getLieuStockage() {
        return lieuStockage;
    }

    public void setLieuStockage(LieuStockage lieuStockage) {
        this.lieuStockage = lieuStockage;
    }

    public PieceJointe getPieceJointe() {
        return pieceJointe;
    }

    public void setPieceJointe(PieceJointe pieceJointe) {
        this.pieceJointe = pieceJointe;
    }
}
