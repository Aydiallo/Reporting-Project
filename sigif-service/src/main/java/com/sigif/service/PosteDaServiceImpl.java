package com.sigif.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.ActionDAO;
import com.sigif.dao.ActiviteDAO;
import com.sigif.dao.CategorieImmobilisationDAO;
import com.sigif.dao.DemandeAchatDAO;
import com.sigif.dao.FondsDAO;
import com.sigif.dao.LieuStockageDAO;
import com.sigif.dao.PosteDemandeAchatDAO;
import com.sigif.dao.ProgrammeDAO;
import com.sigif.dao.RegionDAO;
import com.sigif.dao.ServiceDepensierDAO;
import com.sigif.dao.TypeAchatDAO;
import com.sigif.dao.UniteDAO;
import com.sigif.dao.VilleDAO;
import com.sigif.dto.PosteDemandeAchatDTO;
import com.sigif.enumeration.Civilite;
import com.sigif.enumeration.EtatDonnee;
import com.sigif.enumeration.ImpactedData;
import com.sigif.enumeration.Statut;
import com.sigif.enumeration.TypePJ;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Action;
import com.sigif.modele.Activite;
import com.sigif.modele.CategorieImmobilisation;
import com.sigif.modele.DADataImpact;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.Fonds;
import com.sigif.modele.LieuStockage;
import com.sigif.modele.PieceJointe;
import com.sigif.modele.PosteDemandeAchat;
import com.sigif.modele.Programme;
import com.sigif.modele.Region;
import com.sigif.modele.ServiceDepensier;
import com.sigif.modele.TypeAchat;
import com.sigif.modele.TypeImmobilisation;
import com.sigif.modele.Unite;
import com.sigif.modele.Ville;
import com.sigif.util.Constantes;
import com.sigif.util.FunctionsUtils;
import com.sigif.util.StringToDate;

/**
 * Implémentation du service d'accès aux postes des demandes d'achat.
 * 
 */
@Service("posteDaService")
@Transactional
public class PosteDaServiceImpl extends AbstractServiceImpl<PosteDemandeAchat, PosteDemandeAchatDTO>

        implements PosteDaService {
    /**
     * Loggueur.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PosteDaServiceImpl.class);

    @Override
    protected PosteDemandeAchatDAO getDao() {
        return (PosteDemandeAchatDAO) super.getDao();
    }

    /** DemandeAchatDAO. */
    @Autowired
    DemandeAchatDAO daDAO;

    /** TypeAchatDAO. */
    @Autowired
    TypeAchatDAO typeAchatDAO;

    /** UniteDAO. */
    @Autowired
    UniteDAO uniteDAO;

    /** ActionDAO. */
    @Autowired
    ActionDAO actionDAO;

    /** ActiviteDAO. */
    @Autowired
    ActiviteDAO activiteDAO;

    /** FondsDAO. */
    @Autowired
    FondsDAO fondDAO;

    /** RegionDAO. */
    @Autowired
    VilleDAO villeDAO;

    /** RegionDAO. */
    @Autowired
    RegionDAO regionDAO;

    /** ServiceDepensierDAO. */
    @Autowired
    ServiceDepensierDAO sdDAO;

    /** ProgrammeDAO. */
    @Autowired
    ProgrammeDAO programmeDAO;

    /** Service pour enregistrer les PJ. */
    @Autowired
    PieceJointeService pjService;

    /** Service pour gérer les alertes. */
    @Autowired
    DataImpactService dataImpactService;

    /**
     * Service pour groupe marchandise et categorie immo.
     */
    @Autowired
    CategoriePosteService categoriePosteService;

    /** LieuStockageDAO. */
    @Autowired
    LieuStockageDAO lieuStockageDAO;

    /**
     * CategorieImmobilisationDAO.
     */
    @Autowired
    CategorieImmobilisationDAO categorieImmoDAO;

    @Override
    public void savePostesDA(List<Map<String, Object>> listPostesDAmap, int idDemandeAchat) throws TechnicalException {
        DemandeAchat demandeAchat = daDAO.getById(idDemandeAchat);
        try {
            // Suppression des poste DA liés à la DA et de leurs piéces
            // jointes
            this.deletePostesDaByDa(demandeAchat);

            for (Map<String, Object> map : listPostesDAmap) {
                savePosteDa(demandeAchat, map);
            }

        } catch (Exception e) {
            LOGGER.error("Erreur lors de la sauvegarde des postes de la DA d'id '" + idDemandeAchat
                    + "'. Rollback des postes déjà créés.");
            try {
                this.deletePostesDaByDa(demandeAchat);
            } catch (TechnicalException e1) {
                LOGGER.error("Erreur lors du rollback des postes DA");
            }
            throw new TechnicalException(
                    "Erreur lors de la sauvegarde des postes de la DA d'id '" + idDemandeAchat + "'", e);
        }
    }

    @Override
    public void deletePostesDaByDa(DemandeAchat demandeAchat) throws TechnicalException {
        try {
            // recherche des postes et suppression des PJ liées
            Set<PosteDemandeAchat> postesASupprimer = demandeAchat.getPostesDemandeAchat();
            if (postesASupprimer != null) {
                for (PosteDemandeAchat posteDemandeAchat : postesASupprimer) {
                    PieceJointe pj = posteDemandeAchat.getPieceJointe();
                    if (pj != null) {
                        pjService.deletePj(pj);
                    }

                    // Nettoyage de la table Impact pour le pôste
                    dataImpactService.cleanDataImpact(0, posteDemandeAchat.getId());
                    this.getDao().delete(posteDemandeAchat);
                }
            }
        } catch (TechnicalException techEx) {
            throw techEx;
        } catch (Exception e) {
            throw new TechnicalException(
                    "Erreur lors de la suppression des postes de la DA '" + demandeAchat.getNumeroDossier() + "'", e);
        }
    }

    @Override
    public Map<String, String> getPosteDAInformation(String idDA, String noPoste)
            throws ApplicationException, TechnicalException {
        idDA = FunctionsUtils.checkNotNullNotEmptyAndTrim("idDA", idDA, LOGGER);
        noPoste = FunctionsUtils.checkNotNullNotEmptyAndTrim("noPoste", noPoste, LOGGER);

        Map<String, String> posteDAListOnMap = null;
        try {
            PosteDemandeAchat pda = this.getDao().getPosteDAInformation(idDA, noPoste);

            posteDAListOnMap = new HashMap<>();

            posteDAListOnMap.put(Constantes.KEY_MAP_SERVICE_BENEFICIAIRE,
                    pda.getServiceDepensier() != null ? pda.getServiceDepensier().getDesignation() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_SERVICE_BENEFICIAIRE,
                    pda.getServiceDepensier() != null ? pda.getServiceDepensier().getCode() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_FOND,
                    pda.getFonds() != null ? pda.getFonds().getDesignation() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_FOND,
                    pda.getFonds() != null ? pda.getFonds().getCode() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_TYPE_FOND,
                    pda.getFonds() != null ? String.valueOf(pda.getFonds().getTypeFonds().getId()) : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_TYPE_ACHAT,
                    pda.getTypeAchat() != null ? pda.getTypeAchat().getDesignation() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_TYPE_ACHAT,
                    pda.getTypeAchat() != null ? pda.getTypeAchat().getCode() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_REFERENCE, pda.getReference());
            posteDAListOnMap.put(Constantes.KEY_MAP_DESIGNATION, pda.getDesignation());
            posteDAListOnMap.put(Constantes.KEY_MAP_PROGRAMME,
                    pda.getProgramme() != null ? pda.getProgramme().getDescription() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_PROGRAMME,
                    pda.getProgramme() != null ? pda.getProgramme().getCode() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_ACTION,
                    pda.getAction() != null ? pda.getAction().getDescription() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_ACTION,
                    pda.getAction() != null ? pda.getAction().getCode() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_ACTIVITE,
                    pda.getActivite() != null ? pda.getActivite().getDescription() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_ACTIVITE,
                    pda.getActivite() != null ? pda.getActivite().getCode() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_LIEU_STOCKAGE,
                    pda.getLieuStockage() != null ? pda.getLieuStockage().getDesignation() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_LIEU_STOCKAGE,
                    pda.getLieuStockage() != null ? String.valueOf(pda.getLieuStockage().getId()) : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_QUANTITE,
                    pda.getQuantiteDemandee() > 0L ? String.valueOf(pda.getQuantiteDemandee()) : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_UNITE, pda.getUnite().getNom());
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_UNITE, pda.getUnite().getCode());
            posteDAListOnMap.put(Constantes.KEY_MAP_STATUT_APPROBATION,
                    pda.getStatutApprobation() != null ? pda.getStatutApprobation().displayName() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_QTE_COMMANDEE,
                    ((pda.getQuantiteRetenue() != null) && (pda.getQuantiteRetenue() > 0L))
                            ? String.valueOf(pda.getQuantiteRetenue()) : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_MOTIF_REJET, pda.getMotifRejet());
            posteDAListOnMap.put(Constantes.KEY_MAP_COMMENTAIRE, pda.getCommentaire());
            posteDAListOnMap.put(Constantes.KEY_MAP_DATE_LIVRAISON,
                    StringToDate.convertDateToString(pda.getDateLivraison()));
            posteDAListOnMap.put(Constantes.KEY_MAP_CIVILITE,
                    pda.getCiviliteLivraison() != null ? pda.getCiviliteLivraison().toString() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_NOM, pda.getNomLivraison());
            posteDAListOnMap.put(Constantes.KEY_MAP_RUE, pda.getRueLivraison());
            posteDAListOnMap.put(Constantes.KEY_MAP_NUMERO, pda.getNumeroRueLivraison());
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_POSTAL, pda.getCodePostalLivraison());
            posteDAListOnMap.put(Constantes.KEY_MAP_VILLE,
                    pda.getVille() != null ? pda.getVille().getDesignation() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_VILLE,
                    pda.getVille() != null ? pda.getVille().getCode() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_REGION,
                    pda.getRegion() != null ? pda.getRegion().getDesignation() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_REGION,
                    pda.getRegion() != null ? pda.getRegion().getCode() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_PORTABLE, pda.getPortableContact());
            posteDAListOnMap.put(Constantes.KEY_MAP_TELEPHONE, pda.getTelephoneContact());
            posteDAListOnMap.put(Constantes.KEY_MAP_EMAIL, pda.getCourrielContact());
            posteDAListOnMap.put(Constantes.KEY_MAP_INTITULE_PJ,
                    pda.getPieceJointe() != null ? pda.getPieceJointe().getIntitule() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_PJ_PATH,
                    pda.getPieceJointe() != null ? pda.getPieceJointe().getEmplacement() : null);
            posteDAListOnMap.put(Constantes.KEY_MAP_DATA_STATUS,
                    pda.getEtatDonnee() != null ? pda.getEtatDonnee().toString() : null);

            String catImmo = null;
            String codeCatImmo = null;
            String typeImmo = null;
            String codeTypeImmo = null;
            if (pda.getCategorieImmobilisation() != null) {
                CategorieImmobilisation categorie = pda.getCategorieImmobilisation();
                catImmo = categorie.getDesignation();
                codeCatImmo = categorie.getCode();

                if (categorie.getTypeImmobilisation() != null) {
                    TypeImmobilisation typeImmob = categorie.getTypeImmobilisation();
                    typeImmo = typeImmob.getDesignation();
                    codeTypeImmo = typeImmob.getCode();
                }
            }
            posteDAListOnMap.put(Constantes.KEY_MAP_DESIGNATION_CATEGORIEIMMO, catImmo);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_CATEGORIEIMMO, codeCatImmo);
            posteDAListOnMap.put(Constantes.KEY_MAP_DESIGNATION_TYPEIMMO, typeImmo);
            posteDAListOnMap.put(Constantes.KEY_MAP_CODE_TYPEIMMO, codeTypeImmo);

            String categorie = null;
            if (pda.getTypeAchat() != null && StringUtils.isNotBlank(pda.getReference())) {
                categorie = this.categoriePosteService.getCategorieByTypeAndReference(pda.getTypeAchat().getCode(),
                        pda.getReference());
            } else if (pda.getTypeAchat() != null && Constantes.TYPE_ACHAT_IMMOBILISATION.equals(pda.getTypeAchat().getCode())) {
                categorie = typeImmo + "/" + catImmo;
            }

            posteDAListOnMap.put(Constantes.KEY_MAP_CATEGORIE,
                    categorie);
            
            String uploadString = null;
            if (pda.getPieceJointe() != null) {
                Map<String, String> infosPj = pjService.getPjAndCopyFileInTmp(pda.getPieceJointe().getId());
                uploadString = infosPj.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ);
            }
            posteDAListOnMap.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadString);
        } catch (Exception e) {
            String errorMessage = String
                    .format("Erreur lors de la récupération des infos du poste '%s' pour la DA '%s' ", noPoste, idDA);
            LOGGER.error(errorMessage);
            throw new TechnicalException(errorMessage, e);
        }

        return posteDAListOnMap;
    }

    @Override
    public Map<String, String[]> getItemsByDA(String numDA) throws ApplicationException, TechnicalException {
        numDA = FunctionsUtils.checkNotNullNotEmptyAndTrim("numDA", numDA, LOGGER);
        try {
            List<PosteDemandeAchat> posteDAList = this.getDao().getItemsByDA(numDA);
            int rstSize = posteDAList.size();
            Map<String, String[]> posteDAListMap = null;
            if (rstSize > 0) {
                String[] id = new String[rstSize];
                String[] numero = new String[rstSize];
                String[] reference = new String[rstSize];
                String[] designation = new String[rstSize];
                String[] qteDemandee = new String[rstSize];
                String[] qteRetenue = new String[rstSize];
                String[] statut = new String[rstSize];
                String[] commande = new String[rstSize];
                String[] etatDonnee = new String[rstSize];
                int cpt = 0;
                for (PosteDemandeAchat pda : posteDAList) {
                    posteDAListMap = new HashMap<String, String[]>();
                    id[cpt] = String.valueOf(pda.getId());
                    numero[cpt] = String.valueOf(pda.getIdDaposte());
                    reference[cpt] = "(" + pda.getTypeAchat().getCode() + ") " + pda.getReference();
                    designation[cpt] = pda.getDesignation();
                    qteDemandee[cpt] =
                            String.valueOf(pda.getQuantiteDemandee()) + " (" + pda.getUnite().getCode() + ")";
                    if (pda.getQuantiteRetenue() != null) {
                        qteRetenue[cpt] =
                                String.valueOf(pda.getQuantiteRetenue()) + " (" + pda.getUnite().getCode() + ")";
                    }
                    statut[cpt] = pda.getStatutApprobation() != null ? pda.getStatutApprobation().displayName() : null;
                    commande[cpt] = pda.getNumeroCa();
                    etatDonnee[cpt] = pda.getEtatDonnee().toString();
                    cpt++;
                }
                posteDAListMap.put(Constantes.KEY_MAP_ID, id);
                posteDAListMap.put(Constantes.KEY_MAP_NUMERO, numero);
                posteDAListMap.put(Constantes.KEY_MAP_REFERENCE, reference);
                posteDAListMap.put(Constantes.KEY_MAP_DESIGNATION, designation);
                posteDAListMap.put(Constantes.KEY_MAP_QUANTITE, qteDemandee);
                posteDAListMap.put(Constantes.KEY_MAP_QTE_COMMANDEE, qteRetenue);
                posteDAListMap.put(Constantes.KEY_MAP_STATUT, statut);
                posteDAListMap.put(Constantes.KEY_MAP_NUMERO_CA, commande);
                posteDAListMap.put(Constantes.KEY_MAP_DATA_STATUS, etatDonnee);

            }
            return posteDAListMap;
        } catch (Exception e) {
            String errorMessage = "Erreur lors de la récupération des postes de la DA '" + numDA + "'";
            LOGGER.error(errorMessage);
            throw new TechnicalException(errorMessage, e);

        }
    }

    @Override
    public String getDataImpact(String numDA, String noPoste) throws ApplicationException, TechnicalException {
        numDA = FunctionsUtils.checkNotNullNotEmptyAndTrim("numDA", numDA, LOGGER);
        noPoste = FunctionsUtils.checkNotNullNotEmptyAndTrim("noPoste", noPoste, LOGGER);
        List<DADataImpact> dataImpactList = dataImpactService.searchAlertPosteDA(numDA, noPoste);
        try {
            String modifiedMessage = "";
            String deletedMessage = "";

            if (dataImpactList != null && dataImpactList.size() > 0) {
                for (DADataImpact dataImpact : dataImpactList) {
                    String impact = dataImpact.getImpact().toString();
                    String dataImpacted = ImpactedData.displayName(dataImpact.getImpactedData());
                    switch (impact) {
                    case "Modified":
                        modifiedMessage += dataImpacted + ", ";
                        break;
                    default:
                        deletedMessage += dataImpacted + ", ";
                        break;
                    }
                }
            }
            if (StringUtils.isNotBlank(modifiedMessage) || StringUtils.isNotBlank(deletedMessage)) {
                if (modifiedMessage.length() > 2) {
                    modifiedMessage = modifiedMessage.substring(0, modifiedMessage.length() - 2);
                    modifiedMessage = "Champs modifiés : " + modifiedMessage + "<BR/>";
                }
                if (deletedMessage.length() > 2) {
                    deletedMessage = deletedMessage.substring(0, deletedMessage.length() - 2);
                    deletedMessage = "Champs supprimés : " + deletedMessage;
                }
                return modifiedMessage + "" + deletedMessage;
            } else {
                return null;
            }
        } catch (Exception e) {
            String errorMessage = "Erreur lors de la récupération des data impact de la DA '" + numDA
                    + "' et du poste '" + noPoste + "'";
            LOGGER.error(errorMessage);
            throw new TechnicalException(errorMessage, e);
        }
    }

    @Override
    public List<Map<String, Object>> checkPostesDA(String numDA) throws ApplicationException, TechnicalException {
        List<PosteDemandeAchat> postesExistants = this.getDao().getItemsByDA(numDA);
        List<Map<String, Object>> posteDADupliquables = new ArrayList<Map<String, Object>>();
        String postesEnErreur = "";
        for (PosteDemandeAchat poste : postesExistants) {
            if (checkPosteDa(poste)) {
                posteDADupliquables.add(this.getPosteInfoMap(poste));
            } else {
                postesEnErreur += poste.getIdDaposte() + ", ";
            }
        }

        if (!postesEnErreur.isEmpty()) {
            postesEnErreur = postesEnErreur.substring(0, postesEnErreur.length() - 2);
        }
        Map<String, Object> mapError = new HashMap<String, Object>();
        mapError.put(Constantes.KEY_MAP_MSG_ERROR, postesEnErreur);
        posteDADupliquables.add(mapError);
        return posteDADupliquables;
    }

    /**
     * Vérifie si le poste en paramètre ne contient pas de données inactives.
     * 
     * @param poste
     *            le poste à vérifier
     * @return true si le poste est correct et ne contient pas de données
     *         supprimées, false sinon
     */
    private boolean checkPosteDa(PosteDemandeAchat poste) {
        boolean isValid = true;

        // Vérification des différents éléments accessibles directement
        if (poste.getTypeAchat().getStatut().equals(Statut.inactif)
                || (poste.getProgramme() != null && poste.getProgramme().getStatut().equals(Statut.inactif))
                || (poste.getAction() != null && poste.getAction().getStatut().equals(Statut.inactif))
                || (poste.getActivite() != null && poste.getActivite().getStatut().equals(Statut.inactif))
                || (poste.getCategorieImmobilisation() != null
                        && poste.getCategorieImmobilisation().getStatut().equals(Statut.inactif))
                || (poste.getUnite() != null && poste.getUnite().getStatut().equals(Statut.inactif))
                || (poste.getVille() != null && poste.getVille().getStatut().equals(Statut.inactif))
                || (poste.getRegion() != null && poste.getRegion().getStatut().equals(Statut.inactif))
                || (poste.getLieuStockage() != null && poste.getLieuStockage().getStatut().equals(Statut.inactif))
                || (poste.getServiceDepensier() != null
                        && poste.getServiceDepensier().getStatut().equals(Statut.inactif))
                || (poste.getFonds() != null && poste.getFonds().getStatut().equals(Statut.inactif))
                || (poste.getProgramme() != null && poste.getProgramme().getStatut().equals(Statut.inactif))) {
            isValid = false;
        }

        // Vérification de la référence (qui dépend du type d'achat)
        try {
            if (isValid && poste.getTypeAchat() != null && StringUtils.isNotBlank(poste.getReference())
                    && this.categoriePosteService.getCategorieActiveByTypeAndReference(poste.getTypeAchat().getCode(),
                            poste.getReference()) == null) {
                isValid = false;
            }
        } catch (ApplicationException | TechnicalException e) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Obtient une map représentant le poste.
     * 
     * @param pda
     *            le poste
     * @return une map représentant le poste
     * @throws TechnicalException
     *             si la copie de la PJ échoue
     */
    private Map<String, Object> getPosteInfoMap(PosteDemandeAchat pda) throws TechnicalException {
        Map<String, Object> posteDAMap = new HashMap<String, Object>();
        posteDAMap.put(Constantes.KEY_MAP_NUMERO_POSTE_DA, pda.getIdDaposte());
        posteDAMap.put(Constantes.KEY_MAP_TYPE, pda.getTypeAchat() != null ? pda.getTypeAchat().getCode() : null);
        posteDAMap.put(Constantes.KEY_MAP_UNITE, pda.getUnite() != null ? pda.getUnite().getCode() : null);

        posteDAMap.put(Constantes.KEY_MAP_SERVICE_BENEFICIAIRE,
                pda.getServiceDepensier() != null ? pda.getServiceDepensier().getCode() : null);

        posteDAMap.put(Constantes.KEY_MAP_PROGRAMME, pda.getProgramme() != null ? pda.getProgramme().getCode() : null);

        posteDAMap.put(Constantes.KEY_MAP_FOND, pda.getFonds() != null ? pda.getFonds().getCode() : null);

        posteDAMap.put(Constantes.KEY_MAP_QUANTITE, pda.getQuantiteDemandee());

        posteDAMap.put(Constantes.KEY_MAP_DATE_LIVRAISON, pda.getDateLivraison());

        posteDAMap.put(Constantes.KEY_MAP_DATE_CREATION, pda.getDateCreation());

        posteDAMap.put(Constantes.KEY_MAP_DATE_MODIFICATION, pda.getDateModification());

        posteDAMap.put(Constantes.KEY_MAP_ACTION, pda.getAction() != null ? pda.getAction().getCode() : null);

        posteDAMap.put(Constantes.KEY_MAP_ACTIVITE, pda.getActivite() != null ? pda.getActivite().getCode() : null);

        posteDAMap.put(Constantes.KEY_MAP_LIEU_STOCKAGE,
                pda.getLieuStockage() != null ? pda.getLieuStockage().getId() : null);

        posteDAMap.put(Constantes.KEY_MAP_VILLE, pda.getVille() != null ? pda.getVille().getCode() : null);

        posteDAMap.put(Constantes.KEY_MAP_REGION, pda.getRegion() != null ? pda.getRegion().getCode() : null);

        posteDAMap.put(Constantes.KEY_MAP_CIVILITE,
                pda.getCiviliteLivraison() != null ? pda.getCiviliteLivraison().toString() : null);

        posteDAMap.put(Constantes.KEY_MAP_DESIGNATION, pda.getDesignation());

        posteDAMap.put(Constantes.KEY_MAP_REFERENCE, pda.getReference());

        posteDAMap.put(Constantes.KEY_MAP_COMMENTAIRE, pda.getCommentaire());

        posteDAMap.put(Constantes.KEY_MAP_NOM, pda.getNomLivraison());

        posteDAMap.put(Constantes.KEY_MAP_RUE, pda.getRueLivraison());

        posteDAMap.put(Constantes.KEY_MAP_NUMERO, pda.getNumeroRueLivraison());

        posteDAMap.put(Constantes.KEY_MAP_CODE_POSTAL, pda.getCodePostalLivraison());

        posteDAMap.put(Constantes.KEY_MAP_TELEPHONE, pda.getTelephoneContact());

        posteDAMap.put(Constantes.KEY_MAP_PORTABLE, pda.getPortableContact());

        posteDAMap.put(Constantes.KEY_MAP_EMAIL, pda.getCourrielContact());

        posteDAMap.put(Constantes.KEY_MAP_CATEGORIE,
                pda.getCategorieImmobilisation() != null ? pda.getCategorieImmobilisation().getCode() : null);

        posteDAMap.put(Constantes.KEY_MAP_STATUT_APPROBATION, null);

        posteDAMap.put(Constantes.KEY_MAP_DATA_STATUS, pda.getEtatDonnee().toString());
        if (pda.getPieceJointe() != null) {
            Map<String, String> getInfoPJ = pjService.getPjAndCopyFileInTmp(pda.getPieceJointe().getId());
            posteDAMap.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, getInfoPJ.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ));

            posteDAMap.put(Constantes.KEY_MAP_INTITULE_PJ, getInfoPJ.get(Constantes.KEY_MAP_INTITULE_PJ));

        }
        return posteDAMap;
    }

    /**
     * Enregistre un poste DA représenté par une map.
     * 
     * @param demandeAchat
     *            la demande d'achat à laquelle rattacher le poste
     * @param mapPoste
     *            la map représentant les infos du poste
     * @throws TechnicalException
     *             Si l'ajout échoue
     */
    private void savePosteDa(DemandeAchat demandeAchat, Map<String, Object> mapPoste) throws TechnicalException {
        PieceJointe pieceAjoutee = null;
        PosteDemandeAchat posteDA = new PosteDemandeAchat();
        final int radixBase10 = 10;
        try {
            posteDA.setDemandeAchat(demandeAchat);

            posteDA.setIdDaposte(mapPoste.get(Constantes.KEY_MAP_NUMERO_POSTE_DA).toString());

            TypeAchat typeAchat = typeAchatDAO.getUniqueByParam("code", mapPoste.get(Constantes.KEY_MAP_TYPE));
            posteDA.setTypeAchat(typeAchat);

            Unite unite = uniteDAO.getUniqueByParam("code", mapPoste.get(Constantes.KEY_MAP_UNITE));
            posteDA.setUnite(unite);

            ServiceDepensier serviceBenef =
                    sdDAO.getUniqueByParam("code", mapPoste.get(Constantes.KEY_MAP_SERVICE_BENEFICIAIRE));
            posteDA.setServiceDepensier(serviceBenef);

            Programme programme = programmeDAO.getUniqueByParam("code", mapPoste.get(Constantes.KEY_MAP_PROGRAMME));
            posteDA.setProgramme(programme);

            Fonds fond = fondDAO.getUniqueByParam("code", mapPoste.get(Constantes.KEY_MAP_FOND));
            posteDA.setFonds(fond);

            if (mapPoste.get(Constantes.KEY_MAP_QUANTITE) != null) {
                posteDA.setQuantiteDemandee(Long.parseLong(mapPoste.get(Constantes.KEY_MAP_QUANTITE).toString()));
            }

            Date dateLivraison = (Date) mapPoste.get(Constantes.KEY_MAP_DATE_LIVRAISON);
            posteDA.setDateLivraison(dateLivraison);
            posteDA.setDateCreation(new Date());
            posteDA.setDateModification(new Date());

            if (mapPoste.get(Constantes.KEY_MAP_ACTION) != null) {
                Action action = actionDAO.getUniqueByParam("code", mapPoste.get(Constantes.KEY_MAP_ACTION).toString());
                posteDA.setAction(action);
            }

            if (mapPoste.get(Constantes.KEY_MAP_ACTIVITE) != null) {
                Activite activite =
                        activiteDAO.getUniqueByParam("code", mapPoste.get(Constantes.KEY_MAP_ACTIVITE).toString());
                posteDA.setActivite(activite);
            }
            if (mapPoste.get(Constantes.KEY_MAP_LIEU_STOCKAGE) != null
                    && !mapPoste.get(Constantes.KEY_MAP_LIEU_STOCKAGE).toString().isEmpty()) {
                LieuStockage lieuStockage = lieuStockageDAO.getUniqueByParam("id",
                        Integer.parseInt(mapPoste.get(Constantes.KEY_MAP_LIEU_STOCKAGE).toString(), radixBase10));
                posteDA.setLieuStockage(lieuStockage);
            }
            if (mapPoste.get(Constantes.KEY_MAP_VILLE) != null) {
                Ville ville = villeDAO.getUniqueByParam("code", mapPoste.get(Constantes.KEY_MAP_VILLE).toString());
                posteDA.setVille(ville);
            }

            if (mapPoste.get(Constantes.KEY_MAP_REGION) != null) {
                Region region = regionDAO.getUniqueByParam("code", mapPoste.get(Constantes.KEY_MAP_REGION).toString());
                posteDA.setRegion(region);
            }

            if (mapPoste.get(Constantes.KEY_MAP_CIVILITE) != null) {
                Civilite civilite = Civilite.valueOf(mapPoste.get(Constantes.KEY_MAP_CIVILITE).toString());
                posteDA.setCiviliteLivraison(civilite);
            }

            if (mapPoste.get(Constantes.KEY_MAP_DESIGNATION) != null) {
                posteDA.setDesignation(mapPoste.get(Constantes.KEY_MAP_DESIGNATION).toString());
            }
            if (mapPoste.get(Constantes.KEY_MAP_REFERENCE) != null) {
                posteDA.setReference(mapPoste.get(Constantes.KEY_MAP_REFERENCE).toString());
            }
            if (mapPoste.get(Constantes.KEY_MAP_COMMENTAIRE) != null) {
                posteDA.setCommentaire(mapPoste.get(Constantes.KEY_MAP_COMMENTAIRE).toString());
            }
            if (mapPoste.get(Constantes.KEY_MAP_NOM) != null) {
                posteDA.setNomLivraison(mapPoste.get(Constantes.KEY_MAP_NOM).toString());
            }
            if (mapPoste.get(Constantes.KEY_MAP_RUE) != null) {
                posteDA.setRueLivraison(mapPoste.get(Constantes.KEY_MAP_RUE).toString());
            }
            if (mapPoste.get(Constantes.KEY_MAP_NUMERO) != null) {
                posteDA.setNumeroRueLivraison(mapPoste.get(Constantes.KEY_MAP_NUMERO).toString());
            }
            if (mapPoste.get(Constantes.KEY_MAP_CODE_POSTAL) != null) {
                posteDA.setCodePostalLivraison(mapPoste.get(Constantes.KEY_MAP_CODE_POSTAL).toString());
            }
            if (mapPoste.get(Constantes.KEY_MAP_TELEPHONE) != null) {
                posteDA.setTelephoneContact(mapPoste.get(Constantes.KEY_MAP_TELEPHONE).toString());
            }
            if (mapPoste.get(Constantes.KEY_MAP_PORTABLE) != null) {
                posteDA.setPortableContact(mapPoste.get(Constantes.KEY_MAP_PORTABLE).toString());
            }
            if (mapPoste.get(Constantes.KEY_MAP_EMAIL) != null) {
                posteDA.setCourrielContact(mapPoste.get(Constantes.KEY_MAP_EMAIL).toString());
            }
            if (mapPoste.get(Constantes.KEY_MAP_CATEGORIE) != null) {
                CategorieImmobilisation categImmo = categorieImmoDAO.getUniqueByParam("code",
                        mapPoste.get(Constantes.KEY_MAP_CATEGORIE).toString());
                posteDA.setCategorieImmobilisation(categImmo);
            }

            posteDA.setEtatDonnee(EtatDonnee.Ok);
        } catch (Exception lectureDataException) {
            final String erreurMes = "Erreur lors de la lecture des données liées au poste DA avant enregistrement : ";
            LOGGER.error(erreurMes + lectureDataException.getMessage());
            throw new TechnicalException(erreurMes, lectureDataException);
        }

        try {
            // On persiste en base le posteDA
            this.getDao().save(posteDA);
        } catch (Exception savePDaException) {
            LOGGER.error("Erreur lors de la sauvegarde du poste DA : " + savePDaException.getMessage());
            throw new TechnicalException("Erreur lors de la sauvegarde du poste DA", savePDaException);
        }

        // Enregistrement de la pièce jointe associée au posteDA si elle existe
        if (mapPoste.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ) != null
                && mapPoste.get(Constantes.KEY_MAP_INTITULE_PJ) != null
                && StringUtils.isNotBlank(mapPoste.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ).toString())
                && StringUtils.isNotBlank(mapPoste.get(Constantes.KEY_MAP_INTITULE_PJ).toString())) {

            try {
                pieceAjoutee = pjService.savePJ(demandeAchat.getNumeroDossier(),
                        mapPoste.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ).toString(),
                        mapPoste.get(Constantes.KEY_MAP_INTITULE_PJ).toString(), TypePJ.Poste_DA,
                        TypePJ.Poste_DA.toString(), null, posteDA, null, null);
                posteDA.setPieceJointe(pieceAjoutee);
            } catch (Exception savePjEx) {
                String msgErr = "Erreur lors de la sauvegarde de la pièce jointe du poste DA";
                LOGGER.error(msgErr + " : " + savePjEx.getMessage());
                throw new TechnicalException(msgErr, savePjEx);
            }
        }
    }
}
