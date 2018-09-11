package com.sigif.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.LieuStockageDAO;
import com.sigif.dao.PosteCommandeAchatDAO;
import com.sigif.dao.PosteConstatationServiceFaitDAO;
import com.sigif.dto.PosteConstatationServiceFaitDTO;
import com.sigif.enumeration.StatutCertificationPosteCSF;
import com.sigif.enumeration.TypePJ;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.ConstatationServiceFait;
import com.sigif.modele.LieuStockage;
import com.sigif.modele.PieceJointe;
import com.sigif.modele.PosteCommandeAchat;
import com.sigif.modele.PosteConstatationServiceFait;
import com.sigif.modele.Utilisateur;
import com.sigif.util.Constantes;
import com.sigif.util.FunctionsUtils;
import com.sigif.util.StringToDate;

/**
 * Implémentation du service d'accès aux postes des constatations de service
 * fait.
 * 
 */
@Service("posteCsfService")
@Transactional
public class PosteCsfServiceImpl extends
        AbstractServiceImpl<PosteConstatationServiceFait, PosteConstatationServiceFaitDTO> implements PosteCsfService {
    /**
     * Loggueur.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PosteCsfServiceImpl.class);

    /**
     * PosteConstatationServiceFaitDAO.
     */
    @Autowired
    PosteConstatationServiceFaitDAO pcsfDAO;

    /**
     * DAO des postes de Commande d'achat.
     */
    @Autowired
    PosteCommandeAchatDAO pCaDAO;

    /** DAO du lieu de stockage. */
    @Autowired
    LieuStockageDAO lieuStockageDAO;

    /**
     * PieceJointeService.
     */
    @Autowired
    PieceJointeService pjService;

    /**
     * Service pour les postes de Commande d'achat.
     */
    @Autowired
    PosteCommandeAchatService pcaService;
    
    /**
     * Service pour les postes de Constatation service.
     */
    @Autowired
    PosteCsfService pcsfService;

    @Override
    protected PosteConstatationServiceFaitDAO getDao() {
        return (PosteConstatationServiceFaitDAO) super.getDao();
    }

    /**
     * Service pour les catégories des postes.
     */
    @Autowired
    private CategoriePosteService categoriePosteService;

    @Override
    public Map<String, Object[]> getPostesCsfByPosteCa(String numCa, String numPoste, String numTeledossierCsf)
            throws ApplicationException, TechnicalException {
        Map<String, Object[]> resultMap = null;

        numCa = FunctionsUtils.checkNotNullNotEmptyAndTrim("numCa", numCa, LOGGER);
        numPoste = FunctionsUtils.checkNotNullNotEmptyAndTrim("numPoste", numPoste, LOGGER);
        numTeledossierCsf = FunctionsUtils.trimOrNullifyString(numTeledossierCsf);
        List<PosteConstatationServiceFait> receivedItems =
                pcsfDAO.getPostesCsfByPosteCa(numCa, numPoste, numTeledossierCsf);

        if (!receivedItems.isEmpty()) {
            int nbResults = receivedItems.size();
            resultMap = new HashMap<String, Object[]>();
            String[] numDossierCsf = new String[nbResults];
            String[] idCSFPoste = new String[nbResults];
            Object[] dateReception = new Object[nbResults];
            String[] receptionnaire = new String[nbResults];
            String[] quantiteRecue = new String[nbResults];
            String[] quantiteAcceptee = new String[nbResults];
            String[] statutAvancement = new String[nbResults];
            String[] lieuxStockage = new String[nbResults];

            int cpt = 0;
            ConstatationServiceFait csf = null;
            Utilisateur createur = null;
            String uniteCa = null;
            for (PosteConstatationServiceFait p : receivedItems) {
                numDossierCsf[cpt] = p.getConstatationServiceFait().getNumeroDossier();
                idCSFPoste[cpt] = String.valueOf(p.getIdCsfposte());
                // Récupération de certains éléments au début pour ne pas
                // multiplier les requêtes
                csf = p.getConstatationServiceFait();
                createur = csf.getCreateur();
                if (uniteCa == null) {
                    uniteCa = p.getPosteCommandeAchat().getUnite().getNom();
                }

                dateReception[cpt] = csf.getDateCreation();
                receptionnaire[cpt] = createur.getPrenom() + " " + createur.getNom();
                quantiteRecue[cpt] = String.valueOf(p.getQuantiteRecue()) + " (" + uniteCa + ")";
                quantiteAcceptee[cpt] = String.valueOf(p.getQuantiteAcceptee() + " (" + uniteCa + ")");
                statutAvancement[cpt] = p.getStatutAvancement() != null ? p.getStatutAvancement().displayName() : "";
                if (p.getLieuStockage() != null) {
                    lieuxStockage[cpt] = p.getLieuStockage().getDesignation();
                } else {
                    lieuxStockage[cpt] = "";
                }
                cpt++;
            }

            resultMap.put(Constantes.KEY_MAP_NUMERO_CSF, numDossierCsf);
            resultMap.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, idCSFPoste);
            resultMap.put(Constantes.KEY_MAP_DATE_RECEPTION, dateReception);
            resultMap.put(Constantes.KEY_MAP_RECEPTIONNAIRE, receptionnaire);
            resultMap.put(Constantes.KEY_MAP_QTE_RECUE, quantiteRecue);
            resultMap.put(Constantes.KEY_MAP_QTE_ACCEPTEE, quantiteAcceptee);
            resultMap.put(Constantes.KEY_MAP_STATUT, statutAvancement);
            resultMap.put(Constantes.KEY_MAP_LIEU_STOCKAGE, lieuxStockage);
        }
        return resultMap;
    }

    @Override
    public Map<String, String> getPjInfosOfPosteCsf(String numDossierCSF, String idCSFPoste)
            throws ApplicationException {
        numDossierCSF = FunctionsUtils.checkNotNullNotEmptyAndTrim("numDossierCSF", numDossierCSF, LOGGER);
        idCSFPoste = FunctionsUtils.checkNotNullNotEmptyAndTrim("idCSFPoste", idCSFPoste, LOGGER);
        Map<String, String> rsltToMap = null;
        try {
            PosteConstatationServiceFait pcsf = pcsfDAO.getPosteCsfByCsfAndIdCsfPoste(numDossierCSF, idCSFPoste);
            if (pcsf != null) {
                PieceJointe piece = pcsf.getPieceJointe();
                // Copie de la PJ et récupération des valeurs de la map
                if (piece != null) {
                    Map<String, String> infosPj = pjService.getPjAndCopyFileInTmp(piece.getId());
                    rsltToMap = new HashMap<String, String>();
                    rsltToMap.put(Constantes.KEY_MAP_NATURE, infosPj.get(Constantes.KEY_MAP_NATURE));
                    rsltToMap.put(Constantes.KEY_MAP_INTITULE_PJ, infosPj.get(Constantes.KEY_MAP_INTITULE_PJ));
                    rsltToMap.put(Constantes.KEY_MAP_EMPLACEMENT, infosPj.get(Constantes.KEY_MAP_EMPLACEMENT));
                    rsltToMap.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ,
                            infosPj.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ));
                }
            } else {
                LOGGER.warn("Impossible de récuperer les informations de la pièce jointe du poste d'idCSFPoste '"
                        + idCSFPoste + "' car elle n'existe pas.");
            }
        } catch (Exception e) {
            LOGGER.warn("Impossible de récuperer les informations de la pièce jointe du poste d'idCSFPoste '"
                    + idCSFPoste + "'.", e);
        }
        return rsltToMap;
    }

    @Override
    public Map<String, String[]> getItemsCsf(String numCsf) throws ApplicationException, TechnicalException {
        Map<String, String[]> resultMap = null;

        numCsf = FunctionsUtils.checkNotNullNotEmptyAndTrim("numCsf", numCsf, LOGGER);

        List<PosteConstatationServiceFait> receivedItems = this.getDao().getItemsCSF(numCsf);

        if (!receivedItems.isEmpty()) {
            int nbResults = receivedItems.size();
            resultMap = new HashMap<String, String[]>();
            String[] numPosteCsf = new String[nbResults];
            String[] numCAPoste = new String[nbResults];
            String[] reference = new String[nbResults];
            String[] designation = new String[nbResults];
            String[] quantiteCommandee = new String[nbResults];
            String[] quantiteRecue = new String[nbResults];
            String[] quantiteAcceptee = new String[nbResults];
            String[] statutAvancement = new String[nbResults];
            String[] lieuxStockage = new String[nbResults];
            String[] codeLieuxStockage = new String[nbResults];
            String[] naturePJ = new String[nbResults];
            String[] titlePJ = new String[nbResults];
            String[] filePJ = new String[nbResults];
            String[] commentaire = new String[nbResults];

            int cpt = 0;

            for (PosteConstatationServiceFait p : receivedItems) {
                numPosteCsf[cpt] = p.getIdCsfposte();
                numCAPoste[cpt] = p.getConstatationServiceFait().getCommandeAchat().getIdSap() + "/"
                        + p.getPosteCommandeAchat().getIdSap();
                reference[cpt] = p.getPosteCommandeAchat().getReference();
                designation[cpt] = p.getPosteCommandeAchat().getDesignation();
                quantiteCommandee[cpt] = String.valueOf(p.getPosteCommandeAchat().getQuantiteCommandee()) + " ("
                        + p.getPosteCommandeAchat().getUnite().getNom() + ")";
                quantiteRecue[cpt] = String.valueOf(p.getQuantiteRecue()) + " ("
                        + p.getPosteCommandeAchat().getUnite().getNom() + ")";
                quantiteAcceptee[cpt] = String
                        .valueOf(p.getQuantiteAcceptee() + " (" + p.getPosteCommandeAchat().getUnite().getNom() + ")");
                statutAvancement[cpt] = p.getStatutAvancement() != null ? p.getStatutAvancement().displayName() : "";
                if (p.getLieuStockage() != null) {
                    lieuxStockage[cpt] = p.getLieuStockage().getDesignation();
                    codeLieuxStockage[cpt] = String.valueOf(p.getLieuStockage().getId());
                } else {
                    lieuxStockage[cpt] = "";
                    codeLieuxStockage[cpt] = "";
                }
                if (p.getPieceJointe() != null) {
                    Map<String, String> mapPJ = getPjInfosOfPosteCsf(numCsf, p.getIdCsfposte());
                    naturePJ[cpt] = mapPJ.get(Constantes.KEY_MAP_NATURE);
                    titlePJ[cpt] = mapPJ.get(Constantes.KEY_MAP_INTITULE_PJ);
                    filePJ[cpt] = mapPJ.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ);
                } else {
                    naturePJ[cpt] = "";
                    titlePJ[cpt] = "";
                    filePJ[cpt] = "";
                }
                commentaire[cpt] = p.getCommentaire();
                cpt++;
            }

            resultMap.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, numPosteCsf);
            resultMap.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, numCAPoste);
            resultMap.put(Constantes.KEY_MAP_REFERENCE, reference);
            resultMap.put(Constantes.KEY_MAP_DESIGNATION, designation);
            resultMap.put(Constantes.KEY_MAP_QTE_COMMANDEE, quantiteCommandee);
            resultMap.put(Constantes.KEY_MAP_QTE_RECUE, quantiteRecue);
            resultMap.put(Constantes.KEY_MAP_QTE_ACCEPTEE, quantiteAcceptee);
            resultMap.put(Constantes.KEY_MAP_LIEU_STOCKAGE, lieuxStockage);
            resultMap.put(Constantes.KEY_MAP_CODE_LIEU_STOCKAGE, codeLieuxStockage);
            resultMap.put(Constantes.KEY_MAP_STATUT, statutAvancement);
            resultMap.put(Constantes.KEY_MAP_NATURE, naturePJ);
            resultMap.put(Constantes.KEY_MAP_INTITULE_PJ, titlePJ);
            resultMap.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, filePJ);
            resultMap.put(Constantes.KEY_MAP_COMMENTAIRE, commentaire);
        }
        return resultMap;
    }

    @Override
    public Map<String, String[]> getDetailedItemsCsf(String numCsf) throws ApplicationException, TechnicalException {
        Map<String, String[]> resultMap = null;

        numCsf = FunctionsUtils.checkNotNullNotEmptyAndTrim("numCsf", numCsf, LOGGER);

        List<PosteConstatationServiceFait> receivedItems = this.getDao().getItemsCSF(numCsf);

        if (!receivedItems.isEmpty()) {
            int nbResults = receivedItems.size();
            resultMap = new HashMap<String, String[]>();
            String[] numPosteCsf = new String[nbResults];
            String[] numCAPoste = new String[nbResults];
            String[] typeAchat = new String[nbResults];
            String[] reference = new String[nbResults];
            String[] designation = new String[nbResults];
            String[] quantiteCommandee = new String[nbResults];
            String[] quantiteRecue = new String[nbResults];
            String[] quantiteAcceptee = new String[nbResults];
            String[] unite = new String[nbResults];
            String[] statutAvancement = new String[nbResults];
            String[] motifRejet = new String[nbResults];
            String[] lieuxStockage = new String[nbResults];
            String[] codeLieuxStockage = new String[nbResults];
            String[] naturePJ = new String[nbResults];
            String[] titlePJ = new String[nbResults];
            String[] filePJ = new String[nbResults];
            String[] commentaire = new String[nbResults];
            String[] idSapPosteCsf = new String[nbResults];
            String[] dateMajSap = new String[nbResults];

            int cpt = 0;

            for (PosteConstatationServiceFait p : receivedItems) {
                numPosteCsf[cpt] = p.getIdCsfposte();
                numCAPoste[cpt] = p.getPosteCommandeAchat().getIdSap();
                typeAchat[cpt] = p.getPosteCommandeAchat().getTypeAchat().getDesignation();
                reference[cpt] = p.getPosteCommandeAchat().getReference();
                designation[cpt] = p.getPosteCommandeAchat().getDesignation();
                quantiteCommandee[cpt] = String.valueOf(p.getPosteCommandeAchat().getQuantiteCommandee());
                quantiteRecue[cpt] = String.valueOf(p.getQuantiteRecue());
                quantiteAcceptee[cpt] = String.valueOf(p.getQuantiteAcceptee());
                unite[cpt] = p.getPosteCommandeAchat().getUnite().getNom();
                statutAvancement[cpt] = p.getStatutAvancement() != null ? p.getStatutAvancement().displayName() : "";
                motifRejet[cpt] = p.getMotifRejet();
                if (p.getLieuStockage() != null) {
                    lieuxStockage[cpt] = p.getLieuStockage().getDesignation();
                    codeLieuxStockage[cpt] = String.valueOf(p.getLieuStockage().getId());
                } else {
                    lieuxStockage[cpt] = "";
                    codeLieuxStockage[cpt] = "";
                }
                if (p.getPieceJointe() != null) {
                    Map<String, String> mapPJ = getPjInfosOfPosteCsf(numCsf, p.getIdCsfposte());
                    naturePJ[cpt] = mapPJ.get(Constantes.KEY_MAP_NATURE);
                    titlePJ[cpt] = mapPJ.get(Constantes.KEY_MAP_INTITULE_PJ);
                    filePJ[cpt] = mapPJ.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ);
                } else {
                    naturePJ[cpt] = "";
                    titlePJ[cpt] = "";
                    filePJ[cpt] = "";
                }
                commentaire[cpt] = p.getCommentaire();
                idSapPosteCsf[cpt] = p.getIdSap();
                if (p.getDateMiseAjourSap() != null) {
                    dateMajSap[cpt] = StringToDate.convertDateTimeToString(p.getDateMiseAjourSap());
                } else {
                    dateMajSap[cpt] = "";
                }
                cpt++;
            }

            resultMap.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, numPosteCsf);
            resultMap.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, numCAPoste);
            resultMap.put(Constantes.KEY_MAP_TYPE_ACHAT, typeAchat);
            resultMap.put(Constantes.KEY_MAP_REFERENCE, reference);
            resultMap.put(Constantes.KEY_MAP_DESIGNATION, designation);
            resultMap.put(Constantes.KEY_MAP_QTE_COMMANDEE, quantiteCommandee);
            resultMap.put(Constantes.KEY_MAP_QTE_RECUE, quantiteRecue);
            resultMap.put(Constantes.KEY_MAP_QTE_ACCEPTEE, quantiteAcceptee);
            resultMap.put(Constantes.KEY_MAP_UNITE, unite);
            resultMap.put(Constantes.KEY_MAP_LIEU_STOCKAGE, lieuxStockage);
            resultMap.put(Constantes.KEY_MAP_CODE_LIEU_STOCKAGE, codeLieuxStockage);
            resultMap.put(Constantes.KEY_MAP_STATUT, statutAvancement);
            resultMap.put(Constantes.KEY_MAP_MOTIF_REJET, motifRejet);
            resultMap.put(Constantes.KEY_MAP_NATURE, naturePJ);
            resultMap.put(Constantes.KEY_MAP_INTITULE_PJ, titlePJ);
            resultMap.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, filePJ);
            resultMap.put(Constantes.KEY_MAP_COMMENTAIRE, commentaire);
            resultMap.put(Constantes.KEY_MAP_ID, idSapPosteCsf);
            resultMap.put(Constantes.KEY_MAP_DATE_MODIFICATION, dateMajSap);
        }
        return resultMap;
    }

    @Override
    public Map<String, String> getPosteCSFInfos(String numCsf, String numPosteCsf)
            throws ApplicationException, TechnicalException {
        Map<String, String> resultMap = null;

        numCsf = FunctionsUtils.checkNotNullNotEmptyAndTrim("numCsf", numCsf, LOGGER);
        numPosteCsf = FunctionsUtils.checkNotNullNotEmptyAndTrim("numPosteCsf", numPosteCsf, LOGGER);

        PosteConstatationServiceFait posteCsf = this.getDao().getPosteCSFInfo(numCsf, numPosteCsf);
        if (posteCsf != null) {
            resultMap = new HashMap<>();
            PosteCommandeAchat posteCommandeAchat = posteCsf.getPosteCommandeAchat();
            resultMap.put(Constantes.KEY_MAP_STATUT_AVANCEMENT,
                    posteCsf.getStatutAvancement() != null ? posteCsf.getStatutAvancement().displayName() : "");
            resultMap.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, posteCsf.getIdCsfposte());
            resultMap.put(Constantes.KEY_MAP_COMMENTAIRE, posteCsf.getCommentaire());
            resultMap.put(Constantes.KEY_MAP_NUMERO_CA_POSTE,
                    posteCsf.getConstatationServiceFait().getCommandeAchat().getIdSap() + "/"
                            + posteCommandeAchat.getIdSap());
            resultMap.put(Constantes.KEY_MAP_TYPE_ACHAT, posteCommandeAchat.getTypeAchat().getDesignation());
            resultMap.put(Constantes.KEY_MAP_CATEGORIE, this.categoriePosteService.getCategorieByTypeAndReference(
                    posteCommandeAchat.getTypeAchat().getCode(), posteCommandeAchat.getReference()));
            resultMap.put(Constantes.KEY_MAP_LIEU_STOCKAGE,
                    posteCsf.getLieuStockage() != null ? posteCsf.getLieuStockage().getDesignation() : null);
            resultMap.put(Constantes.KEY_MAP_REFERENCE, posteCommandeAchat.getReference());
            resultMap.put(Constantes.KEY_MAP_DESIGNATION, posteCommandeAchat.getDesignation());
            resultMap.put(Constantes.KEY_MAP_QTE_COMMANDEE, String.valueOf(posteCommandeAchat.getQuantiteCommandee())
                    + " (" + posteCommandeAchat.getUnite().getNom() + ")");
            resultMap.put(Constantes.KEY_MAP_QTE_RECUE,
                    String.valueOf(posteCsf.getQuantiteRecue()) + " (" + posteCommandeAchat.getUnite().getNom() + ")");
            resultMap.put(Constantes.KEY_MAP_QTE_ACCEPTEE, String
                    .valueOf(posteCsf.getQuantiteAcceptee() + " (" + posteCommandeAchat.getUnite().getNom() + ")"));
            resultMap.put(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF,
                    this.getStatutAcceptationOfPosteCSF(posteCsf.getQuantiteAcceptee(), posteCsf.getQuantiteRecue()));
            resultMap.put(Constantes.KEY_MAP_MOTIF_REJET, posteCsf.getMotifRejet());

        }
        return resultMap;
    }

    @Override
    public boolean savePosteCsf(Map<String, String> mapPosteCsf, ConstatationServiceFait csf, CommandeAchat ca)
            throws TechnicalException {
        // Validation des paramètres
        if (StringUtils.isBlank(mapPosteCsf.get(Constantes.KEY_MAP_NUMERO_CA_POSTE))
                || StringUtils.isBlank(mapPosteCsf.get(Constantes.KEY_MAP_NUMERO_POSTE_CSF))
                || StringUtils.isBlank(mapPosteCsf.get(Constantes.KEY_MAP_QTE_RECUE))
                || StringUtils.isBlank(mapPosteCsf.get(Constantes.KEY_MAP_QTE_ACCEPTEE))) {
            throw new TechnicalException("Informations obligatoires manquantes "
                    + "(N° de poste CA, n° de poste CSF, quantité reçue et/ou quantité acceptée) pour la création du poste CSF.");
        }

        final int radixbase10 = 10;
        String numPosteCsf = mapPosteCsf.get(Constantes.KEY_MAP_NUMERO_POSTE_CSF);
        StatutCertificationPosteCSF statutCertif =
                StatutCertificationPosteCSF.fromValue(mapPosteCsf.get(Constantes.KEY_MAP_STATUT_AVANCEMENT));
        String commentaire = mapPosteCsf.get(Constantes.KEY_MAP_COMMENTAIRE);

        try {
            Map<String, Object> crits = new HashMap<String, Object>();
            crits.put("commandeAchat", ca);
            crits.put("idSap", mapPosteCsf.get(Constantes.KEY_MAP_NUMERO_CA_POSTE));
            PosteCommandeAchat pCa = pCaDAO.getUniqueByParams(crits);
            Long quantiteRecue = Long.valueOf(mapPosteCsf.get(Constantes.KEY_MAP_QTE_RECUE), radixbase10);
            Long quantiteAcceptee = Long.valueOf(mapPosteCsf.get(Constantes.KEY_MAP_QTE_ACCEPTEE), radixbase10);
            LieuStockage lieuStockage = null;
            if (StringUtils.isNotBlank(mapPosteCsf.get(Constantes.KEY_MAP_LIEU_STOCKAGE))) {
                lieuStockage = lieuStockageDAO.getUniqueByParam("id",
                        Integer.parseInt(mapPosteCsf.get(Constantes.KEY_MAP_LIEU_STOCKAGE).toString(), radixbase10));
            }

            PosteConstatationServiceFait poste = new PosteConstatationServiceFait(pCa, null, csf, numPosteCsf,
                    quantiteRecue, quantiteAcceptee, commentaire, statutCertif, lieuStockage);
            String motifRejet = mapPosteCsf.get(Constantes.KEY_MAP_MOTIF_REJET);
            if (StringUtils.isNotBlank(motifRejet)) {
                poste.setMotifRejet(motifRejet);
            }
            String idSAP = mapPosteCsf.get(Constantes.KEY_MAP_ID);
            if (StringUtils.isNotBlank(idSAP)) {
                poste.setIdSap(idSAP);
            }
            try {
                String dateModif = mapPosteCsf.get(Constantes.KEY_MAP_DATE_MODIFICATION);
                if (dateModif != null) {
                    Date dateMajSap = StringToDate.convertStringToDate(dateModif);
                    poste.setDateMiseAjourSap(dateMajSap);
                }
            } catch (ApplicationException appEx) {
                // Date incorrecte, on ne l'enregistre pas
            }

            this.getDao().save(poste);

            // Report des modifs sur le statut et la quantité restante de la CA            
            if (!pcaService.updateRemainingQuantityAndStatusAfterCsfSave(pCa, quantiteAcceptee)) {
                // Si la quantité est insuffisante, renvoi false
               return false;
            }

            // Enregistrement de la pièce jointe associée au poste CSF si elle
            // existe
            if (StringUtils.isNotBlank(mapPosteCsf.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ))
                    && StringUtils.isNotBlank(mapPosteCsf.get(Constantes.KEY_MAP_INTITULE))
                    && StringUtils.isNotBlank(mapPosteCsf.get(Constantes.KEY_MAP_NATURE))) {
                pjService.savePJ(csf.getNumeroDossier(), mapPosteCsf.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ),
                        mapPosteCsf.get(Constantes.KEY_MAP_INTITULE), TypePJ.Poste_CSF,
                        mapPosteCsf.get(Constantes.KEY_MAP_NATURE), null, null, null, poste);
            }
            return true;
        } catch (TechnicalException customExc) {
            throw customExc;
        } catch (Exception e) {
            String errorMessage =
                    String.format("Erreur lors de la création du poste CSF numéro '%s' pour la CSF '%s' : %s",
                            numPosteCsf, csf.getNumeroDossier(), e.getMessage());
            LOGGER.error(errorMessage);
            throw new TechnicalException(errorMessage, e);
        }
    }

    @Override
    public void deleteByCsf(ConstatationServiceFait csf, boolean deletePj) throws TechnicalException {
        try {
            // recherche des postes et suppression des PJ liées
        	
            Set<PosteConstatationServiceFait> postesASupprimer = csf.getPosteConstatationServiceFait();
           // this.pcsfService.initialise(postesASupprimer) ;
            System.out.println("**************Set<PosteConstatationServiceFait> postesASupprimer = csf.getPosteConstatationServiceFait(); "+postesASupprimer.size()+"****************");
            if (postesASupprimer != null) {
            	Iterator<PosteConstatationServiceFait> iterator = postesASupprimer.iterator();
        		while ( iterator.hasNext() ) {
        			PosteConstatationServiceFait posteCsf = iterator.next();
                	System.out.println("**************for (PosteConstatationServiceFait posteCsf : postesASupprimer)"+posteCsf.getId()+" ****************");
                    if (deletePj) {
                    	System.out.println("**************if (deletePj) ****************");
                        PieceJointe pj = posteCsf.getPieceJointe();
                        System.out.println("**************PieceJointe pj = posteCsf.getPieceJointe(); ****************");
                        if (pj != null) {
                            pjService.deletePj(pj);
                            System.out.println("**************  pjService.deletePj(pj);****************");
                        }
                        System.out.println("**************fin boucle if ****************");
                    }
                    // Remise à jour de la quantité restante du poste CA
                    System.out.println("**************Remise à jour de la quantité restante du poste CA ****************");
                    pcaService.updateRemainingQuantityAndStatusAfterCsfDelete(posteCsf.getPosteCommandeAchat(),
                            posteCsf.getQuantiteAcceptee());
                    System.out.println("**************pcaService.updateRemainingQuantityAndStatusAfterCsfDelete(posteCsf.getPosteCommandeAchat(),posteCsf.getQuantiteAcceptee()) ****************");

                    this.getDao().delete(posteCsf);
                }
            }
        } catch (TechnicalException techEx) {
            throw techEx;
        } catch (Exception e) {
            throw new TechnicalException(
                    "Erreur lors de la suppression des postes de la CSF '" + csf.getNumeroDossier() + "'", e);
        }

    }

    /**
     * Retourne le Statut d'acceptation d'un poste CSF. <BR>
     * - {@link com.sigif.util.Constantes#STATUT_ACCEPTATION_ACCEPTEE} si la
     * quantité acceptée est égale à la quantité reçue <BR>
     * - {@link com.sigif.util.Constantes#STATUT_ACCEPTATION_ACCEPTEE_PART} si
     * la quantité acceptée est inférieure à la quantité reçue mais différente
     * de 0 <BR>
     * - {@link com.sigif.util.Constantes#STATUT_ACCEPTATION_ACCEPTEE} si la
     * quantité acceptée est égale à 0.
     * 
     * @param qteAcceptee
     *            Quantité acceptée
     * @param qteRecue
     *            Quantité recue
     * @return le statut d'acceptation du poste CSF (Acceptée, Acceptée
     *         partiellement ou refusée)
     */
    private String getStatutAcceptationOfPosteCSF(Long qteAcceptee, Long qteRecue) {
        String statut = "";
        if (qteAcceptee > 0 && qteAcceptee >= qteRecue) {
            statut = Constantes.STATUT_ACCEPTATION_ACCEPTEE;
        } else if (qteAcceptee > 0 && qteAcceptee < qteRecue) {
            statut = Constantes.STATUT_ACCEPTATION_ACCEPTEE_PART;
        } else if (qteRecue != 0) {
            statut = Constantes.STATUT_ACCEPTATION_REFUSEE;
        }
        return statut;
    }

    /* Alpha ajout*/
	@Override
	public void initialise(Set<PosteConstatationServiceFait> postesASupprimer) {
		// TODO Auto-generated method stub
		this.pcsfDAO.initialise(postesASupprimer);
	}
}
