package com.sigif.modele;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sigif.converter.StatutApprobationPosteDAConverter;
import com.sigif.enumeration.Civilite;
import com.sigif.enumeration.EtatDonnee;
import com.sigif.enumeration.StatutApprobationPosteDA;

/**
 * Entité représentant la table poste de demande d'achat.
 */
@Entity
@Table(name = "poste_demande_achat", uniqueConstraints = { @UniqueConstraint(columnNames = "idSAP") })
public class PosteDemandeAchat extends AbstractModele implements java.io.Serializable {

    private static final long serialVersionUID = -7548844818854380787L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAction")
    private Action action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idActivite")
    private Activite activite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategorieImmo")
    private CategorieImmobilisation categorieImmobilisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDA", nullable = false)
    private DemandeAchat demandeAchat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFonds", nullable = false)
    private Fonds fonds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idLieuStockage")
    private LieuStockage lieuStockage;

    @OneToOne(mappedBy = "posteDemandeAchat")
    private PieceJointe pieceJointe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProgramme", nullable = false)
    private Programme programme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRegionLivraison")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServiceBeneficiaire", nullable = false)
    private ServiceDepensier serviceDepensier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTypeAchat", nullable = false)
    private TypeAchat typeAchat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUnite", nullable = false)
    private Unite unite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVilleLivraison")
    private Ville villeDeLivraison;

    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDepartementLivraison")
    private Departement departement;
    
    @Column(name = "idDAPoste", length = 3, nullable = false)
    private String idDaposte;

    @Column(name = "dateCreation", nullable = false)
    private Date dateCreation;

    @Column(name = "dateModification", nullable = false)
    private Date dateModification;

    @Column(name = "reference", length = 18)
    private String reference;

    @Column(name = "designation", length = 60)
    private String designation;

    @Column(name = "quantiteDemandee", nullable = false, precision = 13, scale = 0, columnDefinition = "DECIMAL(13)")
    private long quantiteDemandee;

    @Column(name = "commentaire", length = 240)
    private String commentaire;

    @Column(name = "dateLivraison", nullable = false, columnDefinition = "DATE")
    private Date dateLivraison;

    @Enumerated(EnumType.STRING)
    @Column(name = "civiliteLivraison", columnDefinition = "enum('Madame', 'Mademoiselle', 'Monsieur')")
    private Civilite civiliteLivraison;

    @Column(name = "nomLivraison", length = 40)
    private String nomLivraison;

    @Column(name = "rueLivraison", length = 40)
    private String rueLivraison;

    @Column(name = "numeroRueLivraison", length = 10)
    private String numeroRueLivraison;

    @Column(name = "codePostalLivraison", length = 10)
    private String codePostalLivraison;

    @Column(name = "telephoneContact", length = 15)
    private String telephoneContact;

    @Column(name = "portableContact", length = 15)
    private String portableContact;

    @Column(name = "courrielContact", length = 80)
    private String courrielContact;

    @Column(name = "idSAP", unique = true, length = 2)
    private String idSap;

    @Column(name = "quantiteRetenue", precision = 13, scale = 0, columnDefinition = "DECIMAL(13)")
    private Long quantiteRetenue;

    @Column(name = "numeroCA", length = 10)
    private String numeroCa;

    @Column(name = "dateCreation_SAP", columnDefinition = "DATE")
    private Date dateCreationSap;

    @Column(name = "dateMiseAjour_SAP")
    private Date dateMiseAjourSap;

    @Column(name = "statutApprobation", columnDefinition = "enum('En attende d''approbation','Approuvée','Refusée')")
    @Convert(converter = StatutApprobationPosteDAConverter.class)
    private StatutApprobationPosteDA statutApprobation;

    @Column(name = "motifRejet", length = 60)
    private String motifRejet;

    @Enumerated(EnumType.STRING)
    @Column(name = "etatDonnee", columnDefinition = "ENUM('Ok', 'Warning','Error')")
    private EtatDonnee etatDonnee;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "posteDemandeAchat")
    private Set<PosteCommandeAchat> posteCommandeAchats = new HashSet<PosteCommandeAchat>(0);

    public PosteDemandeAchat() {
    }

    public PosteDemandeAchat(DemandeAchat demandeAchat, Fonds fonds, Programme programme,
            ServiceDepensier serviceDepensier, TypeAchat typeAchat, Unite unite, Date dateCreation,
            Date dateModification, long quantiteDemandee, Date dateLivraison) {
        this.demandeAchat = demandeAchat;
        this.fonds = fonds;
        this.programme = programme;
        this.serviceDepensier = serviceDepensier;
        this.typeAchat = typeAchat;
        this.unite = unite;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.quantiteDemandee = quantiteDemandee;
        this.dateLivraison = dateLivraison;
    }

    public PosteDemandeAchat(Action action, Activite activite, CategorieImmobilisation categorieImmobilisation,
            DemandeAchat demandeAchat, Fonds fonds, LieuStockage lieuStockage, PieceJointe pieceJointe,
            Programme programme, Region region, ServiceDepensier serviceDepensier, TypeAchat typeAchat, Unite unite,
            Ville ville,Departement departement, String idDaposte, Date dateCreation, Date dateModification, String reference,
            String designation, long quantiteDemandee, String commentaire, Date dateLivraison,
            Civilite civiliteLivraison, String nomLivraison, String rueLivraison, String numeroRueLivraison,
            String codePostalLivraison, String telephoneContact, String portableContact, String courrielContact,
            String idSap, Long quantiteRetenue, String numeroCa, Date dateCreationSap, Date dateMiseAjourSap,
            StatutApprobationPosteDA statutApprobation, String motifRejet, EtatDonnee etatDonnee,
            Set<PosteCommandeAchat> posteCommandeAchats) {
    	this.departement = departement;
        this.action = action;
        this.activite = activite;
        this.categorieImmobilisation = categorieImmobilisation;
        this.demandeAchat = demandeAchat;
        this.fonds = fonds;
        this.lieuStockage = lieuStockage;
        this.pieceJointe = pieceJointe;
        this.programme = programme;
        this.region = region;
        this.serviceDepensier = serviceDepensier;
        this.typeAchat = typeAchat;
        this.unite = unite;
        this.villeDeLivraison = ville;
        this.idDaposte = idDaposte;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.reference = reference;
        this.designation = designation;
        this.quantiteDemandee = quantiteDemandee;
        this.commentaire = commentaire;
        this.dateLivraison = dateLivraison;
        this.civiliteLivraison = civiliteLivraison;
        this.nomLivraison = nomLivraison;
        this.rueLivraison = rueLivraison;
        this.numeroRueLivraison = numeroRueLivraison;
        this.codePostalLivraison = codePostalLivraison;
        this.telephoneContact = telephoneContact;
        this.portableContact = portableContact;
        this.courrielContact = courrielContact;
        this.idSap = idSap;
        this.quantiteRetenue = quantiteRetenue;
        this.numeroCa = numeroCa;
        this.dateCreationSap = dateCreationSap;
        this.dateMiseAjourSap = dateMiseAjourSap;
        this.statutApprobation = statutApprobation;
        this.motifRejet = motifRejet;
        this.etatDonnee = etatDonnee;
        this.posteCommandeAchats = posteCommandeAchats;
    }

    public Ville getVilleDeLivraison() {
		return villeDeLivraison;
	}

	public void setVilleDeLivraison(Ville villeDeLivraison) {
		this.villeDeLivraison = villeDeLivraison;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public Action getAction() {
        return this.action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Activite getActivite() {
        return this.activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public CategorieImmobilisation getCategorieImmobilisation() {
        return this.categorieImmobilisation;
    }

    public void setCategorieImmobilisation(CategorieImmobilisation categorieImmobilisation) {
        this.categorieImmobilisation = categorieImmobilisation;
    }

    public DemandeAchat getDemandeAchat() {
        return this.demandeAchat;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public Fonds getFonds() {
        return this.fonds;
    }

    public void setFonds(Fonds fonds) {
        this.fonds = fonds;
    }

    public LieuStockage getLieuStockage() {
        return this.lieuStockage;
    }

    public void setLieuStockage(LieuStockage lieuStockage) {
        this.lieuStockage = lieuStockage;
    }

    public PieceJointe getPieceJointe() {
        return this.pieceJointe;
    }

    public void setPieceJointe(PieceJointe pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public Programme getProgramme() {
        return this.programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public ServiceDepensier getServiceDepensier() {
        return this.serviceDepensier;
    }

    public void setServiceDepensier(ServiceDepensier serviceDepensier) {
        this.serviceDepensier = serviceDepensier;
    }

    public TypeAchat getTypeAchat() {
        return this.typeAchat;
    }

    public void setTypeAchat(TypeAchat typeAchat) {
        this.typeAchat = typeAchat;
    }

    public Unite getUnite() {
        return this.unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public Ville getVille() {
        return this.villeDeLivraison;
    }

    public void setVille(Ville ville) {
        this.villeDeLivraison = ville;
    }

    public String getIdDaposte() {
        return this.idDaposte;
    }

    public void setIdDaposte(String idDaposte) {
        this.idDaposte = idDaposte;
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

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public long getQuantiteDemandee() {
        return this.quantiteDemandee;
    }

    public void setQuantiteDemandee(long quantiteDemandee) {
        this.quantiteDemandee = quantiteDemandee;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateLivraison() {
        return this.dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Civilite getCiviliteLivraison() {
        return this.civiliteLivraison;
    }

    public void setCiviliteLivraison(Civilite civiliteLivraison) {
        this.civiliteLivraison = civiliteLivraison;
    }

    public String getNomLivraison() {
        return this.nomLivraison;
    }

    public void setNomLivraison(String nomLivraison) {
        this.nomLivraison = nomLivraison;
    }

    public String getRueLivraison() {
        return this.rueLivraison;
    }

    public void setRueLivraison(String rueLivraison) {
        this.rueLivraison = rueLivraison;
    }

    public String getNumeroRueLivraison() {
        return this.numeroRueLivraison;
    }

    public void setNumeroRueLivraison(String numeroRueLivraison) {
        this.numeroRueLivraison = numeroRueLivraison;
    }

    public String getCodePostalLivraison() {
        return this.codePostalLivraison;
    }

    public void setCodePostalLivraison(String codePostalLivraison) {
        this.codePostalLivraison = codePostalLivraison;
    }

    public String getTelephoneContact() {
        return this.telephoneContact;
    }

    public void setTelephoneContact(String telephoneContact) {
        this.telephoneContact = telephoneContact;
    }

    public String getPortableContact() {
        return this.portableContact;
    }

    public void setPortableContact(String portableContact) {
        this.portableContact = portableContact;
    }

    public String getCourrielContact() {
        return this.courrielContact;
    }

    public void setCourrielContact(String courrielContact) {
        this.courrielContact = courrielContact;
    }

    public String getIdSap() {
        return this.idSap;
    }

    public void setIdSap(String idSap) {
        this.idSap = idSap;
    }

    public Long getQuantiteRetenue() {
        return this.quantiteRetenue;
    }

    public void setQuantiteRetenue(Long quantiteRetenue) {
        this.quantiteRetenue = quantiteRetenue;
    }

    public String getNumeroCa() {
        return this.numeroCa;
    }

    public void setNumeroCa(String numeroCa) {
        this.numeroCa = numeroCa;
    }

    public Date getDateCreationSap() {
        return this.dateCreationSap;
    }

    public void setDateCreationSap(Date dateCreationSap) {
        this.dateCreationSap = dateCreationSap;
    }

    public Date getDateMiseAjourSap() {
        return this.dateMiseAjourSap;
    }

    public void setDateMiseAjourSap(Date dateMiseAjourSap) {
        this.dateMiseAjourSap = dateMiseAjourSap;
    }

    public StatutApprobationPosteDA getStatutApprobation() {
        return this.statutApprobation;
    }

    public void setStatutApprobation(StatutApprobationPosteDA statutApprobation) {
        this.statutApprobation = statutApprobation;
    }

    public String getMotifRejet() {
        return this.motifRejet;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    public EtatDonnee getEtatDonnee() {
        return this.etatDonnee;
    }

    public void setEtatDonnee(EtatDonnee etatDonnee) {
        this.etatDonnee = etatDonnee;
    }

    public Set<PosteCommandeAchat> getPosteCommandeAchats() {
        return this.posteCommandeAchats;
    }

    public void setPosteCommandeAchats(Set<PosteCommandeAchat> posteCommandeAchats) {
        this.posteCommandeAchats = posteCommandeAchats;
    }

}
