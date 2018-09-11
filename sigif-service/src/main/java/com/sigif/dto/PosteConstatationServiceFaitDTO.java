package com.sigif.dto;

import java.util.Date;

import com.sigif.enumeration.StatutCertificationPosteCSF;

/**
 * DTO représentant un poste de CSF.
 * 
 * @author Mickael Beaupoil
 *
 */
public class PosteConstatationServiceFaitDTO extends AbstractDTO {
    private PosteCommandeAchatDTO posteCommandeAchat;

    private String idSap;

    private int idCsf;

    private String idCsfposte;

    private Long quantiteRecue;

    private Long quantiteAcceptee;

    private String commentaire;

    private Date dateMiseAjourSap;

    private StatutCertificationPosteCSF statutAvancement;
    
    private LieuStockageDTO lieuStockage;

    public PosteCommandeAchatDTO getPosteCommandeAchat() {
        return posteCommandeAchat;
    }

    public void setPosteCommandeAchat(PosteCommandeAchatDTO posteCommandeAchat) {
        this.posteCommandeAchat = posteCommandeAchat;
    }

    public String getIdSap() {
        return idSap;
    }

    public void setIdSap(String idSap) {
        this.idSap = idSap;
    }

    public int getIdCsf() {
        return idCsf;
    }

    public void setIdCsf(int idCsf) {
        this.idCsf = idCsf;
    }

    public String getIdCsfposte() {
        return idCsfposte;
    }

    public void setIdCsfposte(String idCsfposte) {
        this.idCsfposte = idCsfposte;
    }

    public Long getQuantiteRecue() {
        return quantiteRecue;
    }

    public void setQuantiteRecue(Long quantiteRecue) {
        this.quantiteRecue = quantiteRecue;
    }

    public Long getQuantiteAcceptee() {
        return quantiteAcceptee;
    }

    public void setQuantiteAcceptee(Long quantiteAcceptee) {
        this.quantiteAcceptee = quantiteAcceptee;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateMiseAjourSap() {
        return dateMiseAjourSap;
    }

    public void setDateMiseAjourSap(Date dateMiseAjourSap) {
        this.dateMiseAjourSap = dateMiseAjourSap;
    }

    public StatutCertificationPosteCSF getStatutAvancement() {
        return statutAvancement;
    }

    public void setStatutAvancement(StatutCertificationPosteCSF statutAvancement) {
        this.statutAvancement = statutAvancement;
    }
    
	public LieuStockageDTO getLieuStockage() {
		return lieuStockage;
	}

	public void setLieuStockage(LieuStockageDTO lieuStockage) {
		this.lieuStockage = lieuStockage;
	}

}
