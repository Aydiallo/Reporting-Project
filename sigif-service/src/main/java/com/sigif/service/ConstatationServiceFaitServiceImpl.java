package com.sigif.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.CommandeAchatDAO;
import com.sigif.dao.ConstatationServiceFaitDAO;
import com.sigif.dao.ServiceDepensierDAO;
import com.sigif.dao.UtilisateurDAO;
import com.sigif.dto.ConstatationServiceFaitDTO;
import com.sigif.enumeration.CsfUpdatableStatus;
import com.sigif.enumeration.StatutAvancementCSF;
import com.sigif.enumeration.StatutCertificationCSF;
import com.sigif.enumeration.StatutReceptionCA;
import com.sigif.enumeration.TypePJ;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.ConstatationServiceFait;
import com.sigif.modele.PieceJointe;
import com.sigif.modele.PosteConstatationServiceFait;
import com.sigif.modele.ServiceDepensier;
import com.sigif.modele.Utilisateur;
import com.sigif.util.AttachmentsUtils;
import com.sigif.util.Constantes;
import com.sigif.util.FunctionsUtils;
import com.sigif.util.StringToDate;

/**
 * Implémentation du service d'accès aux CSF.
 *
 */
@Service("constatationServiceFaitService")
@Transactional
public class ConstatationServiceFaitServiceImpl
        extends AbstractServiceImpl<ConstatationServiceFait, ConstatationServiceFaitDTO>
        implements ConstatationServiceFaitService {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstatationServiceFaitServiceImpl.class);

    /** ConstatationServiceFaitDAO. */
    @Autowired
    ConstatationServiceFaitDAO csfDAO;

    /** DAO des utilistaeurs. */
    @Autowired
    UtilisateurDAO userDAO;

    /** DAO des CA. */
    @Autowired
    CommandeAchatDAO caDAO;

    /** ServiceDepensierDAO. */
    @Autowired
    ServiceDepensierDAO sdDAO;

    /** PosteCsfService. */
    @Autowired
    PosteCsfService pcsfService;

    /** Service des CA. */
    @Autowired
    CommandeAchatService caService;

    /** PieceJointeService. */
    @Autowired
    PieceJointeService pjService;

    /** Service pour gérer l'historisation. */
    @Autowired
    CsfStatutAvancementHistoService statutHistoService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean saveCSF(String login, String numTeledossier, Date receptionDate, String spendingServiceCode,
            String codeCa, String comment, String progressStatus, String certificationStatus,
            List<Map<String, String>> listAttachments, List<Map<String, String>> listPostesCsfMap)
            throws ApplicationException, TechnicalException {
        // Validation des paramètres
        StatutAvancementCSF progressStatusCsf = null;
        StatutCertificationCSF certifStatusCsf = null;
        ConstatationServiceFait csf = null;
        CommandeAchat ca = null;
        boolean creation = true;
        login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOGGER);
        numTeledossier = FunctionsUtils.checkNotNullNotEmptyAndTrim("numTeledossier", numTeledossier, LOGGER);
        spendingServiceCode =
                FunctionsUtils.checkNotNullNotEmptyAndTrim("spendingServiceCode", spendingServiceCode, LOGGER);
        ServiceDepensier serviceDepensier = sdDAO.getServiceDepensierByCode(spendingServiceCode);        
        if (serviceDepensier == null) {
            throw new ApplicationException(
                    "Impossible de sauvegarder la CSF car aucun service dépensier ne correspond au code '"
                            + spendingServiceCode + "'");
        }        

        if (this.isCSFUpdatable(login, numTeledossier) != CsfUpdatableStatus.NO) {
            codeCa = FunctionsUtils.checkNotNullNotEmptyAndTrim("codeCa", codeCa, LOGGER);
            comment = FunctionsUtils.trimOrNullifyString(comment);
            certificationStatus = FunctionsUtils.trimOrNullifyString(certificationStatus);
            
            if (certificationStatus != null) {
                certifStatusCsf = StatutCertificationCSF.fromValue(certificationStatus);
            }

            if (StringUtils.isNotBlank(progressStatus)) {
                progressStatusCsf = StatutAvancementCSF.fromValue(progressStatus);
            } else {
                progressStatusCsf = StatutAvancementCSF.Brouillon;
            }
            
            checkParamSaveCsf(receptionDate, listAttachments);

            try {
                Utilisateur createurCsf = userDAO.getUniqueByParam("login", login);
                ca = caDAO.getUniqueByParam("idSap", codeCa);

                csf = this.getDao().getUniqueByParam("numeroDossier", numTeledossier);
                if (csf != null) {
                    creation = false;
                } else {
                    csf = new ConstatationServiceFait();
                    csf.setCreateur(createurCsf);
                    csf.setDateCreation(new Date());
                }
                // Affectation des champs
                csf.setServiceDepensier(serviceDepensier);
                csf.setNumeroDossier(numTeledossier);
                csf.setCommandeAchat(ca);
                csf.setDateReception(receptionDate);
                csf.setCommentaire(comment);
                csf.setModificateur(createurCsf);
                csf.setDateModification(new Date());
                csf.setStatutAvancement(progressStatusCsf);
                csf.setStatutCertification(certifStatusCsf);
            } catch (Exception e) {
                LOGGER.error("Erreur lors de la lecture des données liées à la CSF avant enregistrement : "
                        + e.getMessage());
                throw new TechnicalException(
                        "Erreur lors de la lecture des données liées à la CSF avant enregistrement ", e);
            }
            
            String messageException = "";
            // on persiste en base la Csf
            try {
                saveOrUpdateCsf(numTeledossier, csf, creation);
                statutHistoService.addNewStatusChange(csf, new Date(), progressStatusCsf);
            } catch (TechnicalException techSaveCsfException) {
                throw techSaveCsfException;
            } catch (Exception saveCsfException) {
                if (!creation) {
                    messageException = "Erreur lors de la mise à jour de la CSF : ";
                } else {
                    messageException = "Erreur lors de l'ajout de la CSF : ";
                }
                LOGGER.error(messageException + saveCsfException.getMessage());
                rollbackPjFolder(numTeledossier, creation);
                throw new TechnicalException(messageException, saveCsfException);
            }

            // Création des postes de la CSF
            if (listPostesCsfMap != null && !listPostesCsfMap.isEmpty()) {
                try {
                    savePostesCsf(listPostesCsfMap, csf, ca);                    
                } catch (TechnicalException techSavePosteCsfExc) {
                    String msg = "Erreur lors de la sauvegarde des postes de la CSF '" + numTeledossier + "'";
                    LOGGER.error(msg + " : " + techSavePosteCsfExc.getMessage());
                    rollbackPjFolder(numTeledossier, creation);
                    throw techSavePosteCsfExc;
                } catch (ApplicationException appSavePosteCsfExc) {
                    String msg = "Erreur lors de la sauvegarde des postes de la CSF '" + numTeledossier + "'";
                    LOGGER.error(msg + " : " + appSavePosteCsfExc.getMessage());
                    rollbackPjFolder(numTeledossier, creation);
                    throw appSavePosteCsfExc;
                } catch (Exception savePostesExc) {
                    String msg = "Erreur lors de la sauvegarde des postes de la CSF '" + numTeledossier + "'";
                    LOGGER.error(msg + " : " + savePostesExc.getMessage());
                    rollbackPjFolder(numTeledossier, creation);
                    throw new TechnicalException(msg, savePostesExc);
                }
            }

            // Création des PJ de la CSF
            if (listAttachments != null && !listAttachments.isEmpty()) {
                try {
                    for (Map<String, String> pieceJointe : listAttachments) {
                        pjService.savePJ(numTeledossier, pieceJointe.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ),
                                pieceJointe.get(Constantes.KEY_MAP_INTITULE), TypePJ.CSF,
                                pieceJointe.get(Constantes.KEY_MAP_NATURE), null, null, csf, null);
                    }
                } catch (Exception savePjEx) {
                    String msg = "Erreur lors de la sauvegarde des Pj de la CSF '" + numTeledossier + "'";
                    LOGGER.error(msg + " : " + savePjEx.getMessage());
                    rollbackPjFolder(numTeledossier, creation);
                    throw new TechnicalException(msg, savePjEx);
                }
            }

            // Mise à jour du statut de la CA
            caService.calculateCaStatus(ca);

            if (!creation) {
                pjService.deleteSaveFolderPj(TypePJ.CSF, numTeledossier);
            }
            return true;
        } else {
            String msgLock =
                    String.format("La CSF '%s' est verrouillée et ne peut être modifiée par l'utilisateur '%s'.",
                            numTeledossier, login);
            LOGGER.error(msgLock);
            return false;
        }
    }

    /**
     * Parcours et sauvegarde les postes CSF.
     * 
     * @param listPostesCsfMap
     *            Liste des postes CSF
     * @param csf
     *            CSF à laquelle lier les postes
     * @param ca
     *            CA concernée par cette CSF
     * @throws TechnicalException
     *             Si l'enregistrement échoue pour une raison technique
     * @throws ApplicationException
     *             Si l'enregistrement échoue car les quantités restantes d'un
     *             ou plusieurs postes CA n'étaient pas suffisantes
     */
    private void savePostesCsf(List<Map<String, String>> listPostesCsfMap, ConstatationServiceFait csf,
            CommandeAchat ca) throws TechnicalException, ApplicationException {
        HashMap<String, String> posteEnErreurs = new HashMap<String, String>();
        for (Map<String, String> posteCsf : listPostesCsfMap) {
            if (!pcsfService.savePosteCsf(posteCsf, csf, ca)) {
                posteEnErreurs.put(posteCsf.get(Constantes.KEY_MAP_NUMERO_POSTE_CSF),
                        posteCsf.get(Constantes.KEY_MAP_NUMERO_CA_POSTE));
            }
        }
        if (!posteEnErreurs.isEmpty()) {
            StringBuilder postes = new StringBuilder();
            boolean first = true;
            for (Entry<String, String> poste : posteEnErreurs.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    postes.append(", ");
                }
                postes.append("poste CSF : ").append(poste.getKey()).append(" - poste CA : ").append(poste.getValue());
            }
            String msg =
                    String.format("L'enregistrement a échoué car la quantité acceptée "
                            + "est supérieure à la quantité restante : %s", postes.toString());
            throw new ApplicationException(msg);
        }
    }

    /**
     * Vérifie que la date de réception est remplie et que la liste des PJ est correcte.
     * @param receptionDate date de réception
     * @param listAttachments Liste des PJ 
     * @throws ApplicationException si l'un des 2 est incorrect
     */
    private void checkParamSaveCsf(Date receptionDate, List<Map<String, String>> listAttachments)
            throws ApplicationException {
        if (receptionDate == null) {
            final String message =
                    "Le paramètre 'date de réception' de la CSF est null. L'enregistrement est impossible.";
            LOGGER.error(message);
            throw new ApplicationException(message);
        }
        if (listAttachments != null && !listAttachments.isEmpty()) {
            String uploadStringPj = null;
            String pjKos = "";
            for (Map<String, String> pjInfo : listAttachments) {
                uploadStringPj = pjInfo.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ);
                if (uploadStringPj != null && AttachmentsUtils.checkUploadStringIsCorrect(uploadStringPj) == null) {
                    pjKos += uploadStringPj + ", ";
                }
            }
            if (!pjKos.isEmpty()) {
                String msgErrPj = String.format(
                        "L'enregistrement de la CSF est impossible car ses pièces jointes suivantes ne sont pas correctes "
                                + "(fichier inexistant ou chaîne mal formatée) : %s",
                        pjKos.substring(0, pjKos.length() - 2));
                LOGGER.error(msgErrPj);
                throw new ApplicationException(msgErrPj);
            }
        }
    }

    @Override
    public boolean lockCSF(String login, String numeroCSF) throws ApplicationException {
        login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOGGER);
        numeroCSF = FunctionsUtils.checkNotNullNotEmptyAndTrim("numeroCSF", numeroCSF, LOGGER);
        try {
            ConstatationServiceFait csf = csfDAO.getUniqueByParam("numeroDossier", numeroCSF);
            if (this.isUpdatableByCsf(login, csf) != CsfUpdatableStatus.NO) {
                return false;
            } else {
                csf.setLoginVerrou(login);
                csf.setDateVerrou(new Date());
                csfDAO.merge(csf);
                return true;
            }
        } catch (Exception e) {
            LOGGER.warn("Verrouillage de la CSF '" + numeroCSF + "' impossible.", e);
            return false;
        }
    }

    @Override
    public void unlockCSF(String numeroCSF) throws ApplicationException {
        numeroCSF = FunctionsUtils.checkNotNullNotEmptyAndTrim("numeroCSF", numeroCSF, LOGGER);
        try {
            ConstatationServiceFait csf = csfDAO.getUniqueByParam("numeroDossier", numeroCSF);
            if (csf != null) {
                csf.setLoginVerrou(null);
                csf.setDateVerrou(null);
                csfDAO.merge(csf);
            }
        } catch (Exception e) {
            LOGGER.warn("Déverrouillage de la CSF '" + numeroCSF + "' impossible.", e);
        }
    }

    @Override
    public CsfUpdatableStatus isCSFUpdatable(String login, String numeroCSF) throws ApplicationException {
        login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOGGER);
        numeroCSF = FunctionsUtils.checkNotNullNotEmptyAndTrim("numeroCSF", numeroCSF, LOGGER);
        try {
            ConstatationServiceFait csf = csfDAO.getUniqueByParam("numeroDossier", numeroCSF);
            CsfUpdatableStatus updatableStatus = isUpdatableByCsf(login, csf);
            return updatableStatus;
        } catch (Exception e) {
            String errorMessage = String.format("Erreur lors de la vérification si la CSF '%s' est modifiable par '%s'",
                    numeroCSF, login);
            LOGGER.error(errorMessage, e.getMessage());
            return CsfUpdatableStatus.NO;
        }
    }

    @Override
    public boolean isCSFDeletable(String login, String numeroCSF) throws ApplicationException {
        login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOGGER);
        numeroCSF = FunctionsUtils.checkNotNullNotEmptyAndTrim("numeroCSF", numeroCSF, LOGGER);
        try {
            ConstatationServiceFait csf = csfDAO.getUniqueByParam("numeroDossier", numeroCSF);
            boolean result = this.isDeletableByCsf(login, csf);
            return result;
        } catch (Exception e) {
            String errorMessage = String
                    .format("Erreur lors de la vérification si la CSF '%s' est supprimable par '%s'", numeroCSF, login);
            LOGGER.error(errorMessage, e.getMessage());
            return false;
        }
    }

    @Override
    public String getStatutAcceptationOfCSF(String numDossierCSF) throws TechnicalException {
        return csfDAO.getAcceptationStatus(numDossierCSF);
    }

    @Override
    public Map<String, String> getInfosCSF(String numDossierCSF) throws ApplicationException, TechnicalException {
        numDossierCSF = FunctionsUtils.checkNotNullNotEmptyAndTrim("numDossierCSF", numDossierCSF, LOGGER);
        Map<String, String> infosCSF = new HashMap<String, String>();
        try {
            ConstatationServiceFait csf = csfDAO.getUniqueByParam("numeroDossier", numDossierCSF);
            if (csf != null) {
                infosCSF.put(Constantes.KEY_MAP_NUMERO_CSF, csf.getNumeroDossier());
                infosCSF.put(Constantes.KEY_MAP_DATE_RECEPTION,
                        StringToDate.convertDateToString(csf.getDateReception()));
                infosCSF.put(Constantes.KEY_MAP_RECEPTIONNAIRE,
                        csf.getCreateur().getPrenom() + " " + csf.getCreateur().getNom());
                infosCSF.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, csf.getStatutAvancement().displayName());
                infosCSF.put(Constantes.KEY_MAP_STATUT_VALIDATION_CSF,
                        csf.getStatutCertification() != null ? csf.getStatutCertification().displayName() : "");
                infosCSF.put(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF, getStatutAcceptationOfCSF(numDossierCSF));
                infosCSF.put(Constantes.KEY_MAP_COMMENTAIRE, csf.getCommentaire());
                infosCSF.put(Constantes.KEY_MAP_CODE_SERVICE_DEPENSIER, csf.getServiceDepensier().getCode());
                infosCSF.put(Constantes.KEY_MAP_SERVICE_DEPENSIER, csf.getServiceDepensier().getDesignation());
            } else {
                LOGGER.warn("Impossible de récuperer les informations de la CSF '" + numDossierCSF
                        + "' car elle n'existe pas.");
            }
        } catch (Exception e) {
            String msgErr = String.format("Impossible de récuperer les informations de la CSF '%s'.", numDossierCSF);
            LOGGER.error(msgErr, e);
            throw new TechnicalException(msgErr, e);
        }
        return infosCSF;
    }

    @Override
    public int countNbCSF(String login, String loginReceptionnaire, String dateFrom, String dateTo,
            String statutAvancement, String statutCertification, String statutAcceptation, String numCSF, String numCa)
            throws TechnicalException, ApplicationException {
        login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOGGER);
        numCSF = FunctionsUtils.trimOrNullifyString(numCSF);
        numCa = FunctionsUtils.trimOrNullifyString(numCa);
        loginReceptionnaire = FunctionsUtils.trimOrNullifyString(loginReceptionnaire);
        statutAcceptation = FunctionsUtils.trimOrNullifyString(statutAcceptation);
        statutAvancement = FunctionsUtils.trimOrNullifyString(statutAvancement);
        statutCertification = FunctionsUtils.trimOrNullifyString(statutCertification);
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
            return this.csfDAO.countNbCSF(login, loginReceptionnaire, dateFromValide, dateToValide,
                    StatutAvancementCSF.fromValue(statutAvancement),
                    StatutCertificationCSF.fromValue(statutCertification), statutAcceptation, numCSF, numCa);
        } catch (Exception e) {
            String msgErr = String.format("Erreur lors du compte des CSF : %s", e.getMessage());
            LOGGER.error(msgErr);
            throw new TechnicalException(msgErr, e);
        }

    }

    @Override
    public Map<String, Object[]> searchCSFByCriteria(String login, String loginReceptionnaire, String dateFrom,
            String dateTo, String statutAvancement, String statutCertification, String statutAcceptation, String numCSF,
            String numCa, int nbMaxResults) throws TechnicalException, ApplicationException {
        login = FunctionsUtils.checkNotNullNotEmptyAndTrim("login", login, LOGGER);
        numCSF = FunctionsUtils.trimOrNullifyString(numCSF);
        numCa = FunctionsUtils.trimOrNullifyString(numCa);
        loginReceptionnaire = FunctionsUtils.trimOrNullifyString(loginReceptionnaire);
        statutAcceptation = FunctionsUtils.trimOrNullifyString(statutAcceptation);
        statutAvancement = FunctionsUtils.trimOrNullifyString(statutAvancement);
        statutCertification = FunctionsUtils.trimOrNullifyString(statutCertification);
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

            List<ConstatationServiceFait> listCsfQuery = csfDAO.searchCSFByCriteria(login, loginReceptionnaire,
                    dateFromValide, dateToValide, StatutAvancementCSF.fromValue(statutAvancement),
                    StatutCertificationCSF.fromValue(statutCertification), statutAcceptation, numCSF, numCa,
                    nbMaxResults);
            Map<String, Object[]> resultInMap = null;
            if (listCsfQuery != null && !listCsfQuery.isEmpty()) {
                int retourSize = listCsfQuery.size();
                resultInMap = new HashMap<>();
                Object[] numeroCsf = new Object[retourSize];
                Object[] dateReception = new Object[retourSize];
                Object[] receptionnaire = new Object[retourSize];
                Object[] avancement = new Object[retourSize];
                Object[] validation = new Object[retourSize];
                Object[] acceptation = new Object[retourSize];
                Object[] numCommande = new Object[retourSize];
                int cpt = 0;

                for (ConstatationServiceFait csf : listCsfQuery) {
                    numeroCsf[cpt] = csf.getNumeroDossier();
                    dateReception[cpt] = csf.getDateReception();
                    receptionnaire[cpt] = csf.getCreateur() != null
                            ? csf.getCreateur().getPrenom() + " " + csf.getCreateur().getNom() : null;
                    avancement[cpt] =
                            csf.getStatutAvancement() != null ? csf.getStatutAvancement().displayName() : null;
                    validation[cpt] =
                            csf.getStatutCertification() != null ? csf.getStatutCertification().displayName() : null;
                    if (Constantes.STATUT_ACCEPTATION_ACCEPTEE.equals(statutAcceptation)
                            || Constantes.STATUT_ACCEPTATION_ACCEPTEE_PART.equals(statutAcceptation)
                            || Constantes.STATUT_ACCEPTATION_REFUSEE.equals(statutAcceptation)) {
                        acceptation[cpt] = statutAcceptation;
                    } else {
                        acceptation[cpt] = getStatutAcceptationOfCSF(csf.getNumeroDossier());
                    }
                    numCommande[cpt] = csf.getCommandeAchat() != null ? csf.getCommandeAchat().getIdSap() : null;
                    cpt++;
                }
                resultInMap.put(Constantes.KEY_MAP_NUMERO_CSF, numeroCsf);
                resultInMap.put(Constantes.KEY_MAP_RECEPTIONNAIRE, receptionnaire);
                resultInMap.put(Constantes.KEY_MAP_DATE_RECEPTION, dateReception);
                resultInMap.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, avancement);
                resultInMap.put(Constantes.KEY_MAP_STATUT_VALIDATION_CSF, validation);
                resultInMap.put(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF, acceptation);
                resultInMap.put(Constantes.KEY_MAP_NUMERO_CA, numCommande);

            }
            return resultInMap;

        } catch (Exception e) {
            String msgErr = String.format("Erreur lors de la recherche des CSF : %s", e.getMessage());
            LOGGER.error(msgErr);
            throw new TechnicalException(msgErr, e);
        }
    }

    @Override
    public Map<String, String[]> getPiecesJointesOfCSF(String numDossierCSF) throws ApplicationException {
        numDossierCSF = FunctionsUtils.checkNotNullNotEmptyAndTrim("numDossierCSF", numDossierCSF, LOGGER);
        Map<String, String[]> rsltToMap = new HashMap<String, String[]>();
        try {
            ConstatationServiceFait csf = csfDAO.getUniqueByParam("numeroDossier", numDossierCSF);
            if (csf != null) {
                int rsltSize = csf.getPiecesJointes().size();
                String[] natures = new String[rsltSize];
                String[] intitules = new String[rsltSize];
                String[] emplacements = new String[rsltSize];
                int cpt = 0;
                for (PieceJointe piece : csf.getPiecesJointes()) {
                    // Copie de la PJ et récupération des valeurs de la map
                    Map<String, String> infosPj = pjService.getPjAndCopyFileInTmp(piece.getId());
                    natures[cpt] = infosPj.get(Constantes.KEY_MAP_NATURE);
                    intitules[cpt] = infosPj.get(Constantes.KEY_MAP_INTITULE_PJ);
                    emplacements[cpt] = infosPj.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ);
                    cpt++;
                }
                rsltToMap.put(Constantes.KEY_MAP_NATURE, natures);
                rsltToMap.put(Constantes.KEY_MAP_INTITULE_PJ, intitules);
                rsltToMap.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, emplacements);
            } else {
                LOGGER.warn("Impossible de récuperer les pièces jointes de la CSF '" + numDossierCSF
                        + "' car elle n'existe pas.");
            }
        } catch (Exception e) {
            LOGGER.warn("Impossible de récuperer les pièces jointes de la CSF '" + numDossierCSF + "'.", e);
        }
        return rsltToMap;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean deleteCSF(String numDossierCSF, String login) throws ApplicationException {
        numDossierCSF = FunctionsUtils.checkNotNullNotEmptyAndTrim("numDossierCSF", numDossierCSF, LOGGER);
        boolean deleteOK = false;
        try {
            ConstatationServiceFait csf = csfDAO.getUniqueByParam("numeroDossier", numDossierCSF);
            if (csf != null && this.isDeletableByCsf(login, csf)) {
                // Nettoyage de la table d'historisation
                statutHistoService.cleanHistory(csf.getId());

                // Suppression des PJ de chaque PosteCSF lié puis du Poste CSF
                // (mais pas des fichiers PJ qui ne seront supprimés qu'à la
                // fin pour permettre le rollback)
                pcsfService.deleteByCsf(csf, false);

                // Suppression des PJ de la CSF
                for (PieceJointe pjCSF : csf.getPiecesJointes()) {
                    pjService.deleteById(pjCSF.getId());
                }

                // Recacul du statut de la CA
                caService.calculateCaStatus(csf.getCommandeAchat());

                // Suppression de la CSF
                csfDAO.delete(csf);
                deleteOK = true;

                // Nettoyage du dossier des PJ (s'il échoue, on renvoie quand
                // même true car les données sont supprimées de la BD)
                pjService.deleteFolderPj(TypePJ.CSF, numDossierCSF);
            }
        } catch (Exception e) {
            LOGGER.error("Impossible de supprimer la CSF '" + numDossierCSF + "'.", e);
        }
        return deleteOK;
    }

    /**
     * Selon la valeur du paramètre creation, enregistre ou met à jour la CSF.
     * 
     * @param numTeledossier
     *            numéro de dossier de la CSF
     * @param csf
     *            La CSF à sauvegarder
     * @param creation
     *            Indique si la CSF sera créée ou mise à jour
     * @throws TechnicalException
     *             en mode update, si la sauvegarde des PJ (pour pouvoir gérer
     *             le rollback), la suppression des postes ou la suppression des
     *             PJ échoue
     */
    private void saveOrUpdateCsf(String numTeledossier, ConstatationServiceFait csf, boolean creation)
            throws TechnicalException {
        if (creation) {
            csfDAO.save(csf);
        } else {
            // Sauvegarde des PJ pour pouvoir rollbacker en cas d'erreur
            pjService.saveFolderPj(TypePJ.CSF, numTeledossier);

            // Suppression des postes existants
            pcsfService.deleteByCsf(csf, true);
            csf.setPosteConstatationServiceFait(null);
            // Suppression des PJ existantes
            pjService.deletePjCsf(csf);
            csf.setPiecesJointes(null);
            csfDAO.merge(csf);
        }
    }

    /**
     * Selon la valeur du paramètre creation, supprime les fichiers PJ
     * éventuellement créées ou remet en place les fichiers précédemment en
     * place.
     * 
     * @param numTeledossier
     *            numéro de télédossier
     * @param creation
     *            Indique si la CSF était en mode création ou en mise à jour
     */
    private void rollbackPjFolder(String numTeledossier, boolean creation) {
        // Si erreur, rollback des fichiers
        if (!creation) {
            pjService.rollbackFolderPj(TypePJ.CSF, numTeledossier);
        } else {
            pjService.deleteFolderPj(TypePJ.CSF, numTeledossier);
        }
    }

    /**
     * Vérifie que la CSF est modifiable par l'utilisateur. Une CSF est
     * modifiable seulement si : <BR>
     * - Son statut d’avancement est : <BR>
     * ¤¤¤ « Brouillon » ou « En attente d'envoi » <BR>
     * ¤¤¤ OU « en cours de traitement » ET au moins un des postes n'est pas «
     * certifié » (correspond au cas où la CSF est rectifiable)<BR>
     * - Sa commande (CA) de rattachement n’est pas au statut « Non validé »<BR>
     * - Le service dépensier de la CSF correspond à un service dépensier de
     * l’utilisateur connecté <BR>
     * - Si elle a été créée par l’utilisateur connecté
     * 
     * @param login
     *            login de l'utilisateur
     * @param csf
     *            Constatation de ServiceFait
     * @return - {@link com.sigif.enumeration.CsfUpdatableStatus#NO} si la CSF
     *         n'est pas modifiable<BR>
     *         - {@link com.sigif.enumeration.CsfUpdatableStatus#FULL} si la CSF
     *         est complètement modifiable<BR>
     *         -
     *         {@link com.sigif.enumeration.CsfUpdatableStatus#CORRECTION_ALLOWED}
     *         si la CSF a déjà été envoyée et peut être corrigée<BR>
     * @throws ApplicationException
     *             Si le login ou le no CSF est vide ou null
     */
    private CsfUpdatableStatus isUpdatableByCsf(String login, ConstatationServiceFait csf) {
        CsfUpdatableStatus updatableStatus = CsfUpdatableStatus.NO;
        if (csf == null) {
            updatableStatus = CsfUpdatableStatus.FULL;
        } else {
            // la CSF est verrouillée
            // si elle a été créée par un autre utilisateur
            // ou si l'utilisateur n'est pas habilité sur l'un des SD
            // ou si la CA est à l'état Non validée
            if (!login.equals(csf.getCreateur().getLogin())
                    || csf.getCommandeAchat().getStatut().equals(StatutReceptionCA.NonValidee)
                    || !csfDAO.isAuthorizedOnCsf(login, csf)) {
                updatableStatus = CsfUpdatableStatus.NO;
            } else {
                Set<PosteConstatationServiceFait> postesCsf = csf.getPosteConstatationServiceFait();
                switch (csf.getStatutAvancement()) {
                case Brouillon:
                case EnAttenteEnvoi:
                    if (postesCsf == null || postesCsf.isEmpty()) {
                        updatableStatus = CsfUpdatableStatus.FULL;
                    } else {
                        // La CSF est deverrouillée si elle compte au moins un
                        // poste à un statut KO ou Rectifié
                        scanPostes: for (PosteConstatationServiceFait posteCsf : postesCsf) {
                            if (posteCsf.getStatutAvancement() == null) {
                                updatableStatus = CsfUpdatableStatus.FULL;
                            } else {
                                switch (posteCsf.getStatutAvancement()) {
                                case CertificationRefusee:
                                case ValidationRefusee:
                                case Rectifie:
                                    updatableStatus = CsfUpdatableStatus.CORRECTION_ALLOWED;
                                    break scanPostes;
                                default:
                                    updatableStatus = CsfUpdatableStatus.NO;
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case TraitementEnCours:
                    // La CSF est deverrouillée si elle compte au moins un
                    // poste à un statut KO
                    scanPostes: for (PosteConstatationServiceFait posteCsf : postesCsf) {
                        switch (posteCsf.getStatutAvancement()) {
                        case CertificationRefusee:
                        case ValidationRefusee:
                            updatableStatus = CsfUpdatableStatus.CORRECTION_ALLOWED;
                            break scanPostes;
                        default:
                            updatableStatus = CsfUpdatableStatus.NO;
                            break;
                        }
                    }
                    break;
                default:
                    updatableStatus = CsfUpdatableStatus.NO;
                    break;
                }
            }
        }
        return updatableStatus;
    }

    /**
     * Vérifie que la CSF est supprimable par l'utilisateur. Une CSF est
     * modifiable seulement si : <BR>
     * - Son statut d'avancement est « Brouillon » ou « En attente d'envoi »
     * <BR>
     * - aucun de ses postes n'est à un autre statut que NULL (correspond au cas
     * où la CSF est rectifiable)<BR>
     * - Le service dépensier de la CSF correspond à un
     * service dépensier de l’utilisateur connecté <BR>
     * - Si elle a été créée par l’utilisateur connecté
     * 
     * @param login
     *            login de l'utilisateur
     * @param csf
     *            la Constatation de Service Fait
     * @return true si elle est supprimable
     */
    private boolean isDeletableByCsf(String login, ConstatationServiceFait csf) {
        boolean isDelatable = true;
        if (csf != null) {
            // la CSF est verrouillée si elle a été créée par un autre
            // utilisateur ou si l'utilisateur n'est pas habilité sur l'un des
            // SD
            if (!login.equals(csf.getCreateur().getLogin()) || !csfDAO.isAuthorizedOnCsf(login, csf)) {
                isDelatable = false;
            } else {
                Set<PosteConstatationServiceFait> postesCsf = csf.getPosteConstatationServiceFait();
                switch (csf.getStatutAvancement()) {
                case Brouillon:
                case EnAttenteEnvoi:
                    // La CSF est verrouillée si elle compte au moins un
                    // poste à un statut != null
                    for (PosteConstatationServiceFait posteCsf : postesCsf) {
                        if (posteCsf.getStatutAvancement() != null) {
                            isDelatable = false;
                        }
                    }
                    break;
                default:
                    isDelatable = false;
                    break;
                }
            }
        }
        return isDelatable;
    }
// Alpha ajout//
	@Override
	public List<ConstatationServiceFait> listNotDeletableCSF(int nbJoursDepuisModif,
			int nbJoursDepuisModifStatutTerminal) {
		// TODO Auto-generated method stub
		return this.csfDAO.listNotDeletableCSF(nbJoursDepuisModif, nbJoursDepuisModifStatutTerminal);
	}

	@Override
	public List<ConstatationServiceFait> listConstatationServiceFaits() {
		// TODO Auto-generated method stub
		return this.csfDAO.listConstatationServiceFaits();
	}

	@Override
	public List<CommandeAchat> listCAfromCSF() {
		// TODO Auto-generated method stub
		return this.csfDAO.listCAfromCSF();
	}

	@Override
	public List<ConstatationServiceFait> listDeletableCSF(List<ConstatationServiceFait> csf) {
		// TODO Auto-generated method stub
		return this.csfDAO.listDeletableCSF(csf);
	}

	@Override
	public List<ConstatationServiceFait> CSFLieesCA(List<CommandeAchat> liste) {
		// TODO Auto-generated method stub
		return this.csfDAO.CSFLieesCA(liste);
	}

	@Override
	public void delete(List<ConstatationServiceFait> liste) {
		// TODO Auto-generated method stub
		this.csfDAO.delete(liste);
	}
}
