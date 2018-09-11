package com.sigif.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.CategorieAchatDAO;
import com.sigif.dao.DemandeAchatDAO;
import com.sigif.dao.MinistereDAO;
import com.sigif.dao.ProgrammeDAO;
import com.sigif.dao.ServiceDepensierDAO;
import com.sigif.dao.UtilisateurDAO;
import com.sigif.dto.DemandeAchatDTO;
import com.sigif.enumeration.EtatDonnee;
import com.sigif.enumeration.ImpactedData;
import com.sigif.enumeration.Statut;
import com.sigif.enumeration.StatutApprobationDA;
import com.sigif.enumeration.StatutAvancementDA;
import com.sigif.enumeration.TypePJ;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CategorieAchat;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DADataImpact;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.Ministere;
import com.sigif.modele.PieceJointe;
import com.sigif.modele.PosteCommandeAchat;
import com.sigif.modele.Programme;
import com.sigif.modele.ServiceDepensier;
import com.sigif.modele.Utilisateur;
import com.sigif.util.Constantes;
import com.sigif.util.FunctionsUtils;
import com.sigif.util.StringToDate;

/**
 * Implémentation des services d'accès aux DA.
 * 
 *
 */

@Service("demandeAchatService")
@Transactional
public class DemandeAchatServiceImpl extends AbstractServiceImpl<DemandeAchat, DemandeAchatDTO>
        implements DemandeAchatService {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DemandeAchatServiceImpl.class);

    /** UtilisateurDAO. */
    @Autowired
    UtilisateurDAO userDAO;

    /** MinistereDAO. */
    @Autowired
    MinistereDAO ministereDAO;

    /** ServiceDepensierDAO. */
    @Autowired
    ServiceDepensierDAO sdDAO;

    /** CategorieAchatDAO. */
    @Autowired
    CategorieAchatDAO caDAO;

    /** ProgrammeDAO. */
    @Autowired
    ProgrammeDAO programmeDAO;

    /** DemandeAchatDAO. */
    @Autowired
    DemandeAchatDAO daDAO;

    /** Service pour enregistrer les PJ. */
    @Autowired
    PieceJointeService pjService;

    /** Service pour enregistrer les postes des DA. */
    @Autowired
    PosteDaService posteDaService;

    /** Service pour gérer les alertes. */
    @Autowired
    DataImpactService dataImpactService;

    /** Service pour gérer l'historisation. */
    @Autowired
    DaStatutAvancementHistoService statutHistoService;

    /** GenerateurUidAvecSequence. */
    @Autowired
    GenerateurUidAvecSequence generateur;

    @Override
    public boolean saveDA(String login, String ministeryCode, String spendingServiceCode, String programCode,
            Date creationDate, String requesterLogin, String purchasingCategoriesCode, String demandTitle,
            String objectDemand, String numTeledossier, String statutAvancement)
            throws ApplicationException, TechnicalException {
        boolean resultat = false;
        int saveOK = -1;
        login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOGGER);
        requesterLogin = FunctionsUtils.checkNotNullNotEmptyAndTrim("requesterLogin", requesterLogin, LOGGER);
        spendingServiceCode = FunctionsUtils.trimOrNullifyString(spendingServiceCode);
        programCode = FunctionsUtils.trimOrNullifyString(programCode);
        ministeryCode = FunctionsUtils.trimOrNullifyString(ministeryCode);
        purchasingCategoriesCode = FunctionsUtils.trimOrNullifyString(purchasingCategoriesCode);
        demandTitle = FunctionsUtils.trimOrNullifyString(demandTitle);
        objectDemand = FunctionsUtils.trimOrNullifyString(objectDemand);
        numTeledossier = FunctionsUtils.trimOrNullifyString(numTeledossier);
        statutAvancement = FunctionsUtils.trimOrNullifyString(statutAvancement);
        String messageException = "";
        try {
            DemandeAchat demandeAchat = daDAO.getUniqueByParam("numeroDossier", numTeledossier);
            if (demandeAchat == null) {
                // Si la DA n'existe pas en base
                demandeAchat = new DemandeAchat();
            }
            demandeAchat.setNumeroDossier(numTeledossier);

            Utilisateur createurDA = userDAO.getUniqueByParam("login", login);
            demandeAchat.setCreateur(createurDA);
            demandeAchat.setModificateur(createurDA);

            Utilisateur demandeurDA = userDAO.getUniqueByParam("login", requesterLogin);
            demandeAchat.setDemandeur(demandeurDA);

            demandeAchat.setDateCreation(creationDate);
            demandeAchat.setDateModification(creationDate);
            demandeAchat.setStatutAvancement(StatutAvancementDA.fromValue(statutAvancement));
            demandeAchat.setEtatDonnee(EtatDonnee.Ok);
            demandeAchat.setTitre(demandTitle);
            demandeAchat.setObjet(objectDemand);

            CategorieAchat categorieAchat = caDAO.getCategorieAchatByCode(purchasingCategoriesCode);
            demandeAchat.setCategorieAchat(categorieAchat);

            Ministere ministereDA = ministereDAO.getMinistereByCode(ministeryCode);
            demandeAchat.setMinistere(ministereDA);

            ServiceDepensier serviceDepensierDA = sdDAO.getServiceDepensierByCode(spendingServiceCode);
            demandeAchat.setServiceDepensier(serviceDepensierDA);

            Programme programmeDA = programmeDAO.getProgrammeByCode(programCode);
            demandeAchat.setProgramme(programmeDA);

            // on persiste en base
            saveOK = daDAO.save(demandeAchat);
            if (saveOK != -1) {
                resultat = true;
            }
        } catch (Exception e) {
            messageException = "Erreur lors de l'ajout de la DA : ";
            LOGGER.error(messageException + " " + e.getMessage());
            throw new TechnicalException(messageException, e);

        }
        return resultat;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean saveDAwithPJ(String login, String ministeryCode, String spendingServiceCode, String programCode,
            Date creationDate, String requesterLogin, String purchasingCategoriesCode, String demandTitle,
            String objectDemand, String uploadStringPJ, String intitulePJ, List<Map<String, Object>> listPostesDAmap,
            String numTeledossier, String statutAvancement) throws ApplicationException, TechnicalException {
        int idDemandeAchat = -1;
        login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOGGER);

        requesterLogin = FunctionsUtils.trimOrNullifyString(requesterLogin);
        ministeryCode = FunctionsUtils.trimOrNullifyString(ministeryCode);
        spendingServiceCode = FunctionsUtils.trimOrNullifyString(spendingServiceCode);
        programCode = FunctionsUtils.trimOrNullifyString(programCode);
        purchasingCategoriesCode = FunctionsUtils.trimOrNullifyString(purchasingCategoriesCode);
        demandTitle = FunctionsUtils.trimOrNullifyString(demandTitle);
        objectDemand = FunctionsUtils.trimOrNullifyString(objectDemand);
        uploadStringPJ = FunctionsUtils.trimOrNullifyString(uploadStringPJ);
        intitulePJ = FunctionsUtils.trimOrNullifyString(intitulePJ);
        numTeledossier = FunctionsUtils.trimOrNullifyString(numTeledossier);
        statutAvancement = FunctionsUtils.trimOrNullifyString(statutAvancement);
        DemandeAchat demandeAchat = null;
        boolean ifDaNotExist = false;
        String messageException = "";
        StatutAvancementDA newStatut = StatutAvancementDA.fromValue(statutAvancement);
        try {
            demandeAchat = daDAO.getUniqueByParam("numeroDossier", numTeledossier);
            Utilisateur createurDA = userDAO.getUniqueByParam("login", login);
            if (demandeAchat == null) {
                // Si la DA n'existe pas en base
                demandeAchat = new DemandeAchat();
                demandeAchat.setCreateur(createurDA);
                demandeAchat.setDateCreation(creationDate);
                ifDaNotExist = true;
            }

            if (requesterLogin != null) {
                Utilisateur demandeurDA = userDAO.getUniqueByParam("login", requesterLogin);
                demandeAchat.setDemandeur(demandeurDA);
            }

            demandeAchat.setNumeroDossier(numTeledossier);
            demandeAchat.setModificateur(createurDA);
            demandeAchat.setDateModification(creationDate);
            demandeAchat.setStatutAvancement(newStatut);
            demandeAchat.setEtatDonnee(EtatDonnee.Ok);
            demandeAchat.setTitre(demandTitle);
            demandeAchat.setObjet(objectDemand);

            CategorieAchat categorieAchat = caDAO.getCategorieAchatByCode(purchasingCategoriesCode);
            demandeAchat.setCategorieAchat(categorieAchat);

            Ministere ministereDA = ministereDAO.getMinistereByCode(ministeryCode);
            demandeAchat.setMinistere(ministereDA);

            ServiceDepensier serviceDepensierDA = sdDAO.getServiceDepensierByCode(spendingServiceCode);
            demandeAchat.setServiceDepensier(serviceDepensierDA);

            Programme programmeDA = programmeDAO.getProgrammeByCode(programCode);
            demandeAchat.setProgramme(programmeDA);

        } catch (Exception e) {
            LOGGER.error(
                    "Erreur lors de la lecture des données liées à la DA avant enregistrement : " + e.getMessage());
            throw new TechnicalException("Erreur lors de la lecture des données liées à la DA avant enregistrement ",
                    e);
        }

        // on persiste en base la demande d'achat
        try {
            if (ifDaNotExist) {
                messageException = "Erreur lors de l'ajout de la DA : ";
                idDemandeAchat = daDAO.save(demandeAchat);
            } else {
                messageException = "Erreur lors de la modification de la DA : ";
                pjService.saveFolderPj(TypePJ.DA, numTeledossier);
                idDemandeAchat = demandeAchat.getId();
                // Acceptation des alertes levées
                demandeAchat.setEtatDonnee(EtatDonnee.Ok);
                // Nettoyer la table data_impact
                dataImpactService.cleanDataImpact(idDemandeAchat, 0);
                // Appel de la méthode unlock (qui réalise le merge au passage).
                daDAO.unlockDaByDa(demandeAchat);
            }
            statutHistoService.addNewStatusChange(demandeAchat, new Date(), newStatut);
        } catch (Exception saveDaException) {
            LOGGER.error(messageException + " " + saveDaException.getMessage());
            if (!ifDaNotExist) {
                pjService.rollbackFolderPj(TypePJ.DA, numTeledossier);
            } else {
                pjService.deleteFolderPj(TypePJ.DA, numTeledossier);
            }
            throw new TechnicalException(messageException, saveDaException);
        }

        // Suppression de la PJ existante et sauvegarde de la nouvelle (si elle
        // existe)
        try {
            savePjDa(uploadStringPJ, intitulePJ, numTeledossier, demandeAchat);
        } catch (Exception savePjDaException) {
            LOGGER.error(
                    "Erreur lors de la sauvegarde de la pièce jointe de la DA : " + savePjDaException.getMessage());
            if (!ifDaNotExist) {
                pjService.rollbackFolderPj(TypePJ.DA, numTeledossier);
            } else {
                pjService.deleteFolderPj(TypePJ.DA, numTeledossier);
            }
            throw new TechnicalException("Erreur lors de la sauvegarde de la pièce jointe de la DA", savePjDaException);
        }

        // Enregistrement des PosteDA
        if (listPostesDAmap != null && !listPostesDAmap.isEmpty()) {
            try {
                posteDaService.savePostesDA(listPostesDAmap, idDemandeAchat);
            } catch (Exception savePosteDaException) {
                LOGGER.error("Erreur lors de la sauvegarde des postes de la DA : " + savePosteDaException.getMessage());
                if (!ifDaNotExist) {
                    pjService.rollbackFolderPj(TypePJ.DA, numTeledossier);
                } else {
                    pjService.deleteFolderPj(TypePJ.DA, numTeledossier);
                }
                throw new TechnicalException("Erreur lors de la sauvegarde des postes de la DA", savePosteDaException);
            }
        }
        // Le retour à false est impossible car si une erreur se produit, on
        // renvoie une Technical Exception
        if (!ifDaNotExist) {
            pjService.deleteSaveFolderPj(TypePJ.DA, numTeledossier);
        }
        return true;
    }

    @Override
    public int countDANbResults(String login, String ministry, String spendingService, String requesterLogin,
            String purchasingCategory, String dateFrom, String dateTo, String progessStatus, String approvalStatus,
            String numDA) throws ApplicationException, TechnicalException {

        login = FunctionsUtils.trimOrNullifyString(login);
        ministry = FunctionsUtils.trimOrNullifyString(ministry);
        spendingService = FunctionsUtils.trimOrNullifyString(spendingService);
        requesterLogin = FunctionsUtils.trimOrNullifyString(requesterLogin);
        purchasingCategory = FunctionsUtils.trimOrNullifyString(purchasingCategory);
        progessStatus = FunctionsUtils.trimOrNullifyString(progessStatus);
        dateFrom = FunctionsUtils.trimOrNullifyString(dateFrom);
        dateTo = FunctionsUtils.trimOrNullifyString(dateTo);
        Date dateFromValide = null;
        Date dateToValide = null;
        if (dateFrom != null) {
            dateFromValide = StringToDate.convertStringToDate(dateFrom + " 00:00:00");
        }
        if (dateTo != null) {
            dateToValide = StringToDate.convertStringToDate(dateTo + " 23:59:59");
        }
        approvalStatus = FunctionsUtils.trimOrNullifyString(approvalStatus);
        numDA = FunctionsUtils.trimOrNullifyString(numDA);

        int nbDA = daDAO.countDANbResults(login, ministry, spendingService, requesterLogin, purchasingCategory,
                dateFromValide, dateToValide, StatutAvancementDA.fromValue(progessStatus),
                StatutApprobationDA.fromValue(approvalStatus), numDA);

        return nbDA;
    }

    @Override
    public Map<String, Object[]> searchDA(String login, String ministry, String spendingService, String requesterLogin,
            String purchasingCategory, String dateFrom, String dateTo, String progessStatus, String approvalStatus,
            String numDA, int nbMaxResults) throws ApplicationException, TechnicalException {

        login = FunctionsUtils.trimOrNullifyString(login);
        ministry = FunctionsUtils.trimOrNullifyString(ministry);
        spendingService = FunctionsUtils.trimOrNullifyString(spendingService);
        requesterLogin = FunctionsUtils.trimOrNullifyString(requesterLogin);
        purchasingCategory = FunctionsUtils.trimOrNullifyString(purchasingCategory);
        progessStatus = FunctionsUtils.trimOrNullifyString(progessStatus);
        approvalStatus = FunctionsUtils.trimOrNullifyString(approvalStatus);
        numDA = FunctionsUtils.trimOrNullifyString(numDA);
        dateFrom = FunctionsUtils.trimOrNullifyString(dateFrom);
        dateTo = FunctionsUtils.trimOrNullifyString(dateTo);
        Date dateFromValide = null;
        Date dateToValide = null;
        if (dateFrom != null) {
            dateFromValide = StringToDate.convertStringToDate(dateFrom + " 00:00:00");
        }
        if (dateTo != null) {
            dateToValide = StringToDate.convertStringToDate(dateTo + " 23:59:59");
        }

        try {
            List<DemandeAchat> rsltQuery = daDAO.searchDA(login, ministry, spendingService, requesterLogin,
                    purchasingCategory, dateFromValide, dateToValide, StatutAvancementDA.fromValue(progessStatus),
                    StatutApprobationDA.fromValue(approvalStatus), numDA, nbMaxResults);

            Map<String, Object[]> rsltToMap = null;
            if (rsltQuery != null && !rsltQuery.isEmpty()) {
                rsltToMap = new HashMap<String, Object[]>();
                int rsltSize = rsltQuery.size();
                String[] nbrDossier = new String[rsltSize];
                Date[] dateCreation = new Date[rsltSize];
                String[] demandeur = new String[rsltSize];
                String[] ministere = new String[rsltSize];
                String[] serviceDepensier = new String[rsltSize];
                String[] categorieAchat = new String[rsltSize];
                String[] codeMinistere = new String[rsltSize];
                String[] codeServiceDepensier = new String[rsltSize];
                String[] codeCategorieAchat = new String[rsltSize];
                String[] statutApprob = new String[rsltSize];
                String[] statusAvanc = new String[rsltSize];
                String[] statusDonnees = new String[rsltSize];
                int cpt = 0;
                for (DemandeAchat c : rsltQuery) {
                    nbrDossier[cpt] = c.getNumeroDossier();
                    dateCreation[cpt] = c.getDateCreation();
                    demandeur[cpt] = c.getDemandeur() != null
                            ? c.getDemandeur().getPrenom() + " " + c.getDemandeur().getNom() : null;
                    ministere[cpt] = c.getMinistere() != null ? c.getMinistere().getDesignation() : null;
                    serviceDepensier[cpt] =
                            c.getServiceDepensier() != null ? c.getServiceDepensier().getDesignation() : null;
                    categorieAchat[cpt] = c.getCategorieAchat() != null ? c.getCategorieAchat().getDesignation() : null;
                    codeMinistere[cpt] = c.getMinistere() != null ? c.getMinistere().getCode() : null;
                    codeServiceDepensier[cpt] =
                            c.getServiceDepensier() != null ? c.getServiceDepensier().getCode() : null;
                    codeCategorieAchat[cpt] = c.getCategorieAchat() != null ? c.getCategorieAchat().getCode() : null;

                    statutApprob[cpt] =
                            c.getStatutApprobation() != null ? c.getStatutApprobation().displayName() : null;
                    statusAvanc[cpt] = c.getStatutAvancement() != null ? c.getStatutAvancement().displayName() : null;
                    statusDonnees[cpt] = c.getEtatDonnee() != null ? c.getEtatDonnee().toString() : null;
                    cpt++;
                }
                rsltToMap.put(Constantes.KEY_MAP_NUMERO, nbrDossier);
                rsltToMap.put(Constantes.KEY_MAP_DATE_CREATION, dateCreation);
                rsltToMap.put(Constantes.KEY_MAP_DEMANDEUR, demandeur);
                rsltToMap.put(Constantes.KEY_MAP_MINISTERE, ministere);
                rsltToMap.put(Constantes.KEY_MAP_SERVICE_DEPENSIER, serviceDepensier);
                rsltToMap.put(Constantes.KEY_MAP_CATEGORIE_ACHAT, categorieAchat);
                rsltToMap.put(Constantes.KEY_MAP_CODE_MINISTERE, codeMinistere);
                rsltToMap.put(Constantes.KEY_MAP_CODE_SERVICE_DEPENSIER, codeServiceDepensier);
                rsltToMap.put(Constantes.KEY_MAP_CODE_CATEGORIE_ACHAT, codeCategorieAchat);
                rsltToMap.put(Constantes.KEY_MAP_STATUT, statutApprob);
                rsltToMap.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, statusAvanc);
                rsltToMap.put(Constantes.KEY_MAP_DATA_STATUS, statusDonnees);

            }
            return rsltToMap;

        } catch (Exception e) {
            String errorMessage = "Erreur lors de la recherche des demandes d'achat";
            LOGGER.error(errorMessage);
            throw new TechnicalException(errorMessage, e);
        }
    }

    @Override
    public Map<String, String> getDAInformation(String numDA) throws TechnicalException, ApplicationException {
        numDA = FunctionsUtils.checkNotNullNotEmptyAndTrim("numDA", numDA, LOGGER);
        try {
            DemandeAchat dmdAchat = daDAO.getDAInformation(numDA);
            Map<String, String> daListMap = null;
            if (dmdAchat != null) {
                daListMap = new HashMap<>();
                daListMap.put(Constantes.KEY_MAP_CREATEUR, dmdAchat.getCreateur() != null
                        ? dmdAchat.getCreateur().getPrenom() + " " + dmdAchat.getCreateur().getNom() : "");
                daListMap.put(Constantes.KEY_MAP_LOGIN_CREATEUR,
                        dmdAchat.getCreateur() != null ? dmdAchat.getCreateur().getLogin() : "");
                daListMap.put(Constantes.KEY_MAP_MODIFICATEUR, dmdAchat.getModificateur() != null
                        ? dmdAchat.getModificateur().getPrenom() + " " + dmdAchat.getModificateur().getNom() : "");
                daListMap.put(Constantes.KEY_MAP_LOGIN_MODIFICATEUR,
                        dmdAchat.getModificateur() != null ? dmdAchat.getModificateur().getLogin() : "");
                daListMap.put(Constantes.KEY_MAP_DATE_CREATION,
                        StringToDate.convertDateToString(dmdAchat.getDateCreation()));
                daListMap.put(Constantes.KEY_MAP_DATE_MODIFICATION,
                        StringToDate.convertDateToString(dmdAchat.getDateModification()));
                daListMap.put(Constantes.KEY_MAP_DEMANDEUR, dmdAchat.getDemandeur() != null
                        ? dmdAchat.getDemandeur().getPrenom() + " " + dmdAchat.getDemandeur().getNom() : "");
                daListMap.put(Constantes.KEY_MAP_LOGIN,
                        dmdAchat.getDemandeur() != null ? dmdAchat.getDemandeur().getLogin() : "");
                daListMap.put(Constantes.KEY_MAP_STATUT,
                        dmdAchat.getStatutAvancement() != null ? dmdAchat.getStatutAvancement().displayName() : null);
                daListMap.put(Constantes.KEY_MAP_STATUT_APPROBATION,
                        dmdAchat.getStatutApprobation() != null ? dmdAchat.getStatutApprobation().displayName() : null);
                daListMap.put(Constantes.KEY_MAP_MINISTERE,
                        dmdAchat.getMinistere() != null ? dmdAchat.getMinistere().getDesignation() : "");
                daListMap.put(Constantes.KEY_MAP_CODE_MINISTERE,
                        dmdAchat.getMinistere() != null ? dmdAchat.getMinistere().getCode() : "");
                daListMap.put(Constantes.KEY_MAP_PROGRAMME,
                        dmdAchat.getProgramme() != null ? dmdAchat.getProgramme().getDescription() : "");
                daListMap.put(Constantes.KEY_MAP_CODE_PROGRAMME,
                        dmdAchat.getProgramme() != null ? dmdAchat.getProgramme().getCode() : "");
                daListMap.put(Constantes.KEY_MAP_SERVICE_DEPENSIER, dmdAchat.getServiceDepensier() != null
                        ? dmdAchat.getServiceDepensier().getDesignation() : null);
                daListMap.put(Constantes.KEY_MAP_CODE_SERVICE_DEPENSIER,
                        dmdAchat.getServiceDepensier() != null ? dmdAchat.getServiceDepensier().getCode() : null);
                daListMap.put(Constantes.KEY_MAP_CATEGORIE_ACHAT,
                        dmdAchat.getCategorieAchat() != null ? dmdAchat.getCategorieAchat().getDesignation() : "");
                daListMap.put(Constantes.KEY_MAP_CODE_CATEGORIE_ACHAT,
                        dmdAchat.getCategorieAchat() != null ? dmdAchat.getCategorieAchat().getCode() : "");
                daListMap.put(Constantes.KEY_MAP_TITRE, dmdAchat.getTitre());
                daListMap.put(Constantes.KEY_MAP_OBJET, dmdAchat.getObjet());
                daListMap.put(Constantes.KEY_MAP_INTITULE,
                        dmdAchat.getPieceJointe() != null ? dmdAchat.getPieceJointe().getIntitule() : "");
                daListMap.put(Constantes.KEY_MAP_PJ_PATH,
                        dmdAchat.getPieceJointe() != null ? dmdAchat.getPieceJointe().getEmplacement() : "");

                String uploadString = null;
                if (dmdAchat.getPieceJointe() != null) {
                    Map<String, String> infosPj = pjService.getPjAndCopyFileInTmp(dmdAchat.getPieceJointe().getId());
                    uploadString = infosPj.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ);
                }
                daListMap.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadString);
            }
            return daListMap;

        } catch (Exception e) {
            String errorMessage = "Erreur lors de la récupération de la DA " + numDA;
            LOGGER.error(errorMessage, e.getMessage());
            throw new TechnicalException(errorMessage, e);
        }
    }

    @Override
    public boolean isDAUpdatable(String login, String noDa) throws ApplicationException {
        login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOGGER);
        noDa = FunctionsUtils.checkNotNullNotEmptyAndTrim("noDa", noDa, LOGGER);
        try {
            DemandeAchat da = daDAO.getUniqueByParam("numeroDossier", noDa);
            return this.isDaUpdatableByDa(login, da);
        } catch (Exception e) {
            String errorMessage =
                    String.format("Erreur lors de la vérification si la DA '%s' est modifiable par '%s'", noDa, login);
            LOGGER.error(errorMessage, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean lockDa(String login, String noDa) throws ApplicationException {
        login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOGGER);
        noDa = FunctionsUtils.checkNotNullNotEmptyAndTrim("noDa", noDa, LOGGER);
        try {
            DemandeAchat da = daDAO.getUniqueByParam("numeroDossier", noDa);
            if (!this.isDaUpdatableByDa(login, da)) {
                return false;
            } else {
                da.setLoginVerrou(login);
                da.setDateVerrou(new Date());
                daDAO.merge(da);
                return true;
            }
        } catch (Exception e) {
            LOGGER.warn("Verrouillage de la DA '" + noDa + "' impossible.", e);
            return false;
        }
    }

    @Override
    public void unlockDa(String noDa) throws ApplicationException {
        noDa = FunctionsUtils.checkNotNullNotEmptyAndTrim("noDa", noDa, LOGGER);
        try {
            DemandeAchat da = daDAO.getUniqueByParam("numeroDossier", noDa);
            daDAO.unlockDaByDa(da);
        } catch (Exception e) {
            LOGGER.warn("Déverrouillage de la DA '" + noDa + "' impossible.", e);
        }
    }

    @Override
    public String getDataImpact(String idDA) throws ApplicationException, TechnicalException {
        idDA = FunctionsUtils.checkNotNullNotEmptyAndTrim("idDA", idDA, LOGGER);
        List<DADataImpact> dataImpactList = dataImpactService.searchAlertDA(idDA);
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

            if (!modifiedMessage.isEmpty() || !deletedMessage.isEmpty()) {
                if (modifiedMessage != null && modifiedMessage.length() > 2) {
                    modifiedMessage = modifiedMessage.substring(0, modifiedMessage.length() - 2);
                    modifiedMessage = "Champs modifiés : " + modifiedMessage + "<BR/>";
                }
                if (deletedMessage != null && deletedMessage.length() > 2) {
                    deletedMessage = deletedMessage.substring(0, deletedMessage.length() - 2);
                    deletedMessage = "Champs supprimés : " + deletedMessage;
                }
                return modifiedMessage + "" + deletedMessage;
            } else {
                return null;
            }
        } catch (Exception e) {
            String errorMessage = "erreur lors de la récupération des data impact " + idDA + "";
            LOGGER.error("erreur lors de la récupération des datas impact", e.getMessage());
            throw new TechnicalException(errorMessage, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean deleteDA(String numDossierDA, String login) {
        boolean deleteResult = false;

        try {
            DemandeAchat demandeAchat = daDAO.getUniqueByParam("numeroDossier", numDossierDA);
            if (demandeAchat != null) {
                // Verifier s'il n'est pas locké
                if (lockDa(login, numDossierDA)) {
                    // Nettoyage de la table Impacte / à la DA
                    dataImpactService.cleanDataImpact(demandeAchat.getId(), 0);

                    // Nettoyage de la table d'historisation
                    statutHistoService.cleanHistory(demandeAchat.getId());

                    // Les PosteDA et leur PJ
                    posteDaService.deletePostesDaByDa(demandeAchat);

                    // La pièce jointe de la DA
                    pjService.deletePjDA(demandeAchat);

                    daDAO.delete(demandeAchat);

                    deleteResult = true;

                } else {
                    LOGGER.warn("Impossible de supprimer la DA '" + numDossierDA + "' qui est verrouillée.");
                }
            } else {
                LOGGER.warn("Impossible de supprimer la DA '" + numDossierDA + "' qui n'existe pas.");
            }

        } catch (Exception e) {
            LOGGER.error("Erreur lors de la suppression de la DA '" + numDossierDA + "' .", e);
        }

        return deleteResult;
    }

    @Override
    public String duplicateDA(String login, String oldNumDossierDA, String newNumDossierDA)
            throws TechnicalException, ApplicationException {
        String incorrectPostes = "";
        oldNumDossierDA = FunctionsUtils.checkNotNullNotEmptyAndTrim("oldNumDossierDA", oldNumDossierDA, LOGGER);
        newNumDossierDA = FunctionsUtils.checkNotNullNotEmptyAndTrim("newNumDossierDA", newNumDossierDA, LOGGER);

        try {
            // Vérification que la DA à créer n'existe pas déjà
            DemandeAchat newDemandeAchat = daDAO.getUniqueByParam("numeroDossier", newNumDossierDA);
            if (newDemandeAchat != null) {
                String msgDaExist = String.format(
                        "Impossible de dupliquer la DA '%s' pour créer la DA '%s' car cette dernière existe déjà.",
                        oldNumDossierDA, newNumDossierDA);
                LOGGER.error(msgDaExist);
                throw new ApplicationException(msgDaExist);
            } else {
                // Vérification que la DA à dupliquer existe
                DemandeAchat demandeAchat = daDAO.getUniqueByParam("numeroDossier", oldNumDossierDA);
                if (demandeAchat != null) {

                    // Vérification que l'entête de la DA à dupliquer ne
                    // contient pas de données supprimées
                    String dataDeleted = checkDA(demandeAchat);

                    if (dataDeleted == null) {

                        String ministeryCode = "";
                        if (demandeAchat.getMinistere() != null) {
                            ministeryCode = demandeAchat.getMinistere().getCode();
                        }
                        String spendingServiceCode = "";
                        if (demandeAchat.getServiceDepensier() != null) {
                            spendingServiceCode = demandeAchat.getServiceDepensier().getCode();
                        }
                        String programCode = "";
                        if (demandeAchat.getProgramme() != null) {
                            programCode = demandeAchat.getProgramme().getCode();
                        }
                        String requesterLogin = "";
                        if (demandeAchat.getDemandeur() != null) {
                            requesterLogin = demandeAchat.getDemandeur().getLogin();
                        }
                        String purchasingCategoriesCode = "";
                        if (demandeAchat.getCategorieAchat() != null) {
                            purchasingCategoriesCode = demandeAchat.getCategorieAchat().getCode();
                        }
                        String uploadStringPJ = "";
                        String intitulePJ = "";
                        if (demandeAchat.getPieceJointe() != null) {
                            int idPJ = demandeAchat.getPieceJointe().getId();
                            Map<String, String> infosPj = pjService.getPjAndCopyFileInTmp(idPJ);
                            intitulePJ = infosPj.get(Constantes.KEY_MAP_INTITULE_PJ);
                            uploadStringPJ = infosPj.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ);
                        }
                        Date creationDate = new Date();
                        String demandTitle = demandeAchat.getTitre();
                        String objectDemand = demandeAchat.getObjet();
                        String statutAvancement = StatutAvancementDA.Brouillon.toString();

                        // Duplication des postes et listage des postes dont des
                        // données ont été supprimées
                        List<Map<String, Object>> listPostesDAmap = null;
                        List<Map<String, Object>> listePDADuplicables = posteDaService.checkPostesDA(oldNumDossierDA);
                        int taille = listePDADuplicables.size();
                        Map<String, Object> mapError = listePDADuplicables.get(taille - 1);
                        incorrectPostes = (String) mapError.get(Constantes.KEY_MAP_MSG_ERROR);
                        listPostesDAmap = listePDADuplicables;
                        listPostesDAmap.remove(mapError);

                        saveDAwithPJ(login, ministeryCode, spendingServiceCode, programCode, creationDate,
                                requesterLogin, purchasingCategoriesCode, demandTitle, objectDemand, uploadStringPJ,
                                intitulePJ, listPostesDAmap, newNumDossierDA, statutAvancement);

                    } else {
                        String msgItemDeleted = String.format(
                                "Impossible de dupliquer la DA car les éléments suivants de son entête ont été supprimés : %s",
                                dataDeleted);
                        LOGGER.info("Impossible de dupliquer la DA '" + oldNumDossierDA
                                + "' car au moins un des éléments de son entete a été supprimé.");
                        throw new ApplicationException(msgItemDeleted);
                    }
                } else {
                    String msgDaNotExist =
                            "Impossible de dupliquer la DA '" + oldNumDossierDA + "' car elle n'existe pas.";
                    LOGGER.error(msgDaNotExist);
                    throw new ApplicationException(msgDaNotExist);
                }
            }
        } catch (ApplicationException appEx) {
            throw appEx;
        } catch (Exception e) {
            String msgErr = "Erreur lors de la duplication de la DA '" + oldNumDossierDA + "' .";
            LOGGER.error(msgErr, e);
            throw new TechnicalException(msgErr, e);
        }

        // Renvoie de la liste des postes non repris
        if (incorrectPostes != null && !incorrectPostes.isEmpty()) {
            return String.format("Les postes suivants n'ont pas pu être dupliqués : %s", incorrectPostes);
        } else {
            return "";
        }
    }

    /**
     * Sauvegarde de la PJ d'une DA.
     * 
     * @param uploadStringPJ
     *            Chaîne renvoyée par le getString SMG sur le champ upload
     * @param intitulePJ
     *            intitulé de la PJ
     * @param numTeledossier
     *            le numéro de télédossier généré
     * @param demandeAchat
     *            la demande d'achat à laquelle sera rattachée la PJ
     * @return La pièce jointe sauvegardée
     * @throws TechnicalException
     *             si la sauvegarde échoue
     */
    private PieceJointe savePjDa(String uploadStringPJ, String intitulePJ, String numTeledossier,
            DemandeAchat demandeAchat) throws TechnicalException {
        PieceJointe pieceAjoutee = null;
        // Supprimer la PJ DA
        // (S'ils existent)
        // Suppression de la piéce jointe liée à la DA
        pjService.deletePjDA(demandeAchat);
        if (StringUtils.isNotBlank(uploadStringPJ) && StringUtils.isNotBlank(intitulePJ)) {
            pieceAjoutee = pjService.savePJ(numTeledossier, uploadStringPJ, intitulePJ, TypePJ.DA, TypePJ.DA.toString(),
                    demandeAchat, null, null, null);
        }
        demandeAchat.setPieceJointe(pieceAjoutee);

        return pieceAjoutee;
    }

    /**
     * Verifie une DA existante avec controle sur les données supprimées.
     * 
     * @param demandeAchat
     *            la DA à verifier.
     * @return Returne la liste des éléments manquants pour la DA (null s'il n'y
     *         en a pas).
     * @author Meissa/Mickael
     * @since 19/06/2017
     */
    private String checkDA(DemandeAchat demandeAchat) {
        String checkDAResult = "";
        try {
            if (demandeAchat.getMinistere() != null && demandeAchat.getMinistere().getStatut().equals(Statut.inactif)) {
                checkDAResult += ImpactedData.displayName(ImpactedData.idMinistere) + ", ";
            }
            if (demandeAchat.getServiceDepensier() != null
                    && demandeAchat.getServiceDepensier().getStatut().equals(Statut.inactif)) {
                checkDAResult += ImpactedData.displayName(ImpactedData.idServiceDepensier) + ", ";
            }
            if (demandeAchat.getProgramme() != null && demandeAchat.getProgramme().getStatut().equals(Statut.inactif)) {
                checkDAResult += ImpactedData.displayName(ImpactedData.idProgramme) + ", ";
            }
            if (demandeAchat.getDemandeur() != null && demandeAchat.getDemandeur().getStatut().equals(Statut.inactif)) {
                checkDAResult += ImpactedData.displayName(ImpactedData.demandeur) + ", ";
            }
            if (demandeAchat.getCategorieAchat() != null
                    && demandeAchat.getCategorieAchat().getStatut().equals(Statut.inactif)) {
                checkDAResult += ImpactedData.displayName(ImpactedData.idCategorieAchat) + ", ";
            }

            if (!checkDAResult.isEmpty()) {
                checkDAResult = checkDAResult.substring(0, checkDAResult.length() - 2);
            } else {
                checkDAResult = null;
            }
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la vérification des données supprimées de la DA '"
                    + demandeAchat.getNumeroDossier() + "' .", e);
            checkDAResult = null;
        }
        return checkDAResult;
    }

    /**
     * Vérifie qu'une demande d'achat soit modifiable par un utilisateur (pas de
     * verrou valide et appartenant à un autre utilisateur et statut brouillon
     * ou en attente d'envoi).
     * 
     * @param login
     *            login de l'utilisateur
     * @param da
     *            la demande d'achat
     * @return true si la demande est modifiable
     */
    private boolean isDaUpdatableByDa(String login, DemandeAchat da) {
        // Tps en min au delà duquel un verrou n'est plus considéré valable
        final int nbMinValidVerrou = 120;
        boolean isLocked = true;
        boolean statutKo = true;
        Date now = new Date();
        // nb de ms dans une minute
        final int nbMillisInMin = 60000;
        Date timeValid = new Date(nbMinValidVerrou * nbMillisInMin);
        // heure actuelle moins le nombre de minutes de validité du verrou
        Date dateValid = new Date(now.getTime() - timeValid.getTime());

        if (da.getLoginVerrou() == null || login.equals(da.getLoginVerrou())) {
            // si le verrou a été mis par l'utilisateur courant, on l'ignore
            isLocked = false;
        } else if (da.getDateVerrou() == null || (da.getDateVerrou() != null && da.getDateVerrou().before(dateValid))) {
            // si le verrou n'est plus valide, on le supprime au passage
            daDAO.unlockDaByDa(da);
            isLocked = false;
        } else {
            // le verrou n'est effectif que s'il appartient à quelqu'un d'autre
            // et n'est pas périmé
            isLocked = true;
        }

        if (da.getStatutAvancement() == StatutAvancementDA.Brouillon
                || da.getStatutAvancement() == StatutAvancementDA.EnAttenteEnvoi) {
            // Seules les DA brouillon ou en attente d'envoi sont modifiables
            statutKo = false;
        }

        return (!statutKo && !isLocked);
    }
/** Alpha ajout**/
	@Override
	public List<DemandeAchat> listNotDeletableDA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal) {
		// TODO Auto-generated method stub
		return this.daDAO.listNotDeletableDA(nbJoursDepuisModif, nbJoursDepuisModifStatutTerminal);
	}

	@Override
	public List<DemandeAchat> listDemandeAchats() {
		// TODO Auto-generated method stub
		System.out.println("***** List<DemandeAchat> listDemandeAchats() ***");
		return this.daDAO.listDemandeAchats();
	}

	@Override
	public List<CommandeAchat> listCAfromDA(List<DemandeAchat> liste) {
		// TODO Auto-generated method stub
		return this.daDAO.listCAfromDA(liste);
	}

	@Override
	public void addlistNotDeletableDA(List<DemandeAchat> liste) {
		// TODO Auto-generated method stub
		this.daDAO.addlistNotDeletableDA(liste);
	}

	@Override
	public List<DemandeAchat> listDeletableDA(List<DemandeAchat> da) {
		// TODO Auto-generated method stub
		return this.daDAO.listDeletableDA(da);
	}

	@Override
	public List<PosteCommandeAchat> getPosteCommandeAchats(DemandeAchat da) {
		// TODO Auto-generated method stub
		return this.daDAO.getPosteCommandeAchats(da);
	}

	@Override
	public void delete(List<DemandeAchat> liste) {
		// TODO Auto-generated method stub
		this.daDAO.delete(liste);
	}

	@Override
	public List<Object[]> listeDAforPSdM(String codeProgramme, String codeServiceDepensier, String codeMinister,
			Date dateFrom, Date dateTo) {
		// TODO Auto-generated method stub
		return this.daDAO.listeDAforPSdM(codeProgramme, codeServiceDepensier, codeMinister, dateFrom, dateTo);
	}
}
