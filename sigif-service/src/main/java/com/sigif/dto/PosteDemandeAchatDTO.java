package com.sigif.dto;

import java.util.Date;
import java.util.Set;

import com.sigif.enumeration.Civilite;
import com.sigif.enumeration.EtatDonnee;
import com.sigif.enumeration.StatutApprobationPosteDA;

/**
 * DTO représentant un poste de DA.
 * 
 * @author Mickael Beaupoil
 *
 */
public class PosteDemandeAchatDTO extends AbstractDTO {
    private ActionDTO action;

    private ActiviteDTO activite;

    private CategorieImmobilisationDTO categorieImmobilisation;

    private DemandeAchatDTO demandeAchat;

    private FondsDTO fonds;

    private LieuStockageDTO lieuStockage;

    private PieceJointeDTO pieceJointe;

    private ProgrammeDTO programme;

    private RegionDTO region;
    
    private DepartementDTO departement;

    private ServiceDepensierDTO serviceDepensier;

    private TypeAchatDTO typeAchat;

    private UniteDTO unite;

    private VilleDTO villeDeLivraison;

    private String idDaposte;

    private Date dateCreation;

    private Date dateModification;

    private String reference;

    private String designation;

    private long quantiteDemandee;

    private String commentaire;

    private Date dateLivraison;

    private Civilite civiliteLivraison;

    private String nomLivraison;

    private String rueLivraison;

    private String numeroRueLivraison;

    private String codePostalLivraison;

    private String telephoneContact;

    private String portableContact;

    private String courrielContact;

    private String idSap;

    private Long quantiteRetenue;

    private String numeroCa;

    private Date dateCreationSap;

    private Date dateMiseAjourSap;

    private StatutApprobationPosteDA statutApprobation;

    private String motifRejet;

    private EtatDonnee etatDonnee;

    private Set<PosteCommandeAchatDTO> posteCommandeAchats;

    
    
    public DepartementDTO getDepartement() {
		return departement;
	}

	public void setDepartement(DepartementDTO departement) {
		this.departement = departement;
	}

	public ActionDTO getAction() {
        return action;
    }

    public void setAction(ActionDTO action) {
        this.action = action;
    }

    public ActiviteDTO getActivite() {
        return activite;
    }

    public void setActivite(ActiviteDTO activite) {
        this.activite = activite;
    }

    public CategorieImmobilisationDTO getCategorieImmobilisation() {
        return categorieImmobilisation;
    }

    public void setCategorieImmobilisation(CategorieImmobilisationDTO categorieImmobilisation) {
        this.categorieImmobilisation = categorieImmobilisation;
    }

    public DemandeAchatDTO getDemandeAchat() {
        return demandeAchat;
    }

    public void setDemandeAchat(DemandeAchatDTO demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public FondsDTO getFonds() {
        return fonds;
    }

    public void setFonds(FondsDTO fonds) {
        this.fonds = fonds;
    }

    public LieuStockageDTO getLieuStockage() {
        return lieuStockage;
    }

    public void setLieuStockage(LieuStockageDTO lieuStockage) {
        this.lieuStockage = lieuStockage;
    }

    public PieceJointeDTO getPieceJointe() {
        return pieceJointe;
    }

    public void setPieceJointe(PieceJointeDTO pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public ProgrammeDTO getProgramme() {
        return programme;
    }

    public void setProgramme(ProgrammeDTO programme) {
        this.programme = programme;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    public ServiceDepensierDTO getServiceDepensier() {
        return serviceDepensier;
    }

    public void setServiceDepensier(ServiceDepensierDTO serviceDepensier) {
        this.serviceDepensier = serviceDepensier;
    }

    public TypeAchatDTO getTypeAchat() {
        return typeAchat;
    }

    public void setTypeAchat(TypeAchatDTO typeAchat) {
        this.typeAchat = typeAchat;
    }

    public UniteDTO getUnite() {
        return unite;
    }

    public void setUnite(UniteDTO unite) {
        this.unite = unite;
    }

    public VilleDTO getVilleDeLivraison() {
        return villeDeLivraison;
    }

    public void setVilleDeLivraison(VilleDTO villeDeLivraison) {
        this.villeDeLivraison = villeDeLivraison;
    }

    public String getIdDaposte() {
        return idDaposte;
    }

    public void setIdDaposte(String idDaposte) {
        this.idDaposte = idDaposte;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public long getQuantiteDemandee() {
        return quantiteDemandee;
    }

    public void setQuantiteDemandee(long quantiteDemandee) {
        this.quantiteDemandee = quantiteDemandee;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Civilite getCiviliteLivraison() {
        return civiliteLivraison;
    }

    public void setCiviliteLivraison(Civilite civiliteLivraison) {
        this.civiliteLivraison = civiliteLivraison;
    }

    public String getNomLivraison() {
        return nomLivraison;
    }

    public void setNomLivraison(String nomLivraison) {
        this.nomLivraison = nomLivraison;
    }

    public String getRueLivraison() {
        return rueLivraison;
    }

    public void setRueLivraison(String rueLivraison) {
        this.rueLivraison = rueLivraison;
    }

    public String getNumeroRueLivraison() {
        return numeroRueLivraison;
    }

    public void setNumeroRueLivraison(String numeroRueLivraison) {
        this.numeroRueLivraison = numeroRueLivraison;
    }

    public String getCodePostalLivraison() {
        return codePostalLivraison;
    }

    public void setCodePostalLivraison(String codePostalLivraison) {
        this.codePostalLivraison = codePostalLivraison;
    }

    public String getTelephoneContact() {
        return telephoneContact;
    }

    public void setTelephoneContact(String telephoneContact) {
        this.telephoneContact = telephoneContact;
    }

    public String getPortableContact() {
        return portableContact;
    }

    public void setPortableContact(String portableContact) {
        this.portableContact = portableContact;
    }

    public String getCourrielContact() {
        return courrielContact;
    }

    public void setCourrielContact(String courrielContact) {
        this.courrielContact = courrielContact;
    }

    public String getIdSap() {
        return idSap;
    }

    public void setIdSap(String idSap) {
        this.idSap = idSap;
    }

    public Long getQuantiteRetenue() {
        return quantiteRetenue;
    }

    public void setQuantiteRetenue(Long quantiteRetenue) {
        this.quantiteRetenue = quantiteRetenue;
    }

    public String getNumeroCa() {
        return numeroCa;
    }

    public void setNumeroCa(String numeroCa) {
        this.numeroCa = numeroCa;
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

    public StatutApprobationPosteDA getStatutApprobation() {
        return statutApprobation;
    }

    public void setStatutApprobation(StatutApprobationPosteDA statutApprobation) {
        this.statutApprobation = statutApprobation;
    }

    public String getMotifRejet() {
        return motifRejet;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    public EtatDonnee getEtatDonnee() {
        return etatDonnee;
    }

    public void setEtatDonnee(EtatDonnee etatDonnee) {
        this.etatDonnee = etatDonnee;
    }

    public Set<PosteCommandeAchatDTO> getPosteCommandeAchats() {
        return posteCommandeAchats;
    }

    public void setPosteCommandeAchats(Set<PosteCommandeAchatDTO> posteCommandeAchats) {
        this.posteCommandeAchats = posteCommandeAchats;
    }

}
