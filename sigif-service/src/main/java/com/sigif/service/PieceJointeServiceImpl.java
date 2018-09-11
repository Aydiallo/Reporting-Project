package com.sigif.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.app.SigifProprietes;
import com.sigif.dao.PieceJointeDAO;
import com.sigif.dto.PieceJointeDTO;
import com.sigif.enumeration.TypePJ;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.ConstatationServiceFait;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PieceJointe;
import com.sigif.modele.PosteConstatationServiceFait;
import com.sigif.modele.PosteDemandeAchat;
import com.sigif.util.AttachmentsUtils;
import com.sigif.util.CleaningThread;
import com.sigif.util.Constantes;

/**
 * Implémentation du service de gestion des pièces jointes.
 * 
 *
 * @since 19-05-2017
 */
@Service("pieceJointeService")
@Transactional
public class PieceJointeServiceImpl extends AbstractServiceImpl<PieceJointe, PieceJointeDTO>
        implements PieceJointeService {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PieceJointeServiceImpl.class);

    /**
     * Environnement Spring (pour la récupération des propriétés).
     */
    @Autowired
    Environment environnement;

    /**
     * Récupère le chemin du dossier des PJ (paramétré dans le fichier de conf).
     * 
     * @return le chemin du dossier des PJ
     * @throws TechnicalException
     *             si le dossier n'est pas configuré
     */
    private String getFolderPj() throws TechnicalException {
        try {
            return environnement.getRequiredProperty(SigifProprietes.PROP_ATTACHMENTS_FOLDER_PATH);
        } catch (IllegalStateException ise) {
            String msgErreur = String.format(
                    "Le dossier de stockage des pièces jointes (%s) "
                            + "n'est pas paramétré dans le fichier de configuration. L'enregistrement est impossible.",
                    SigifProprietes.PROP_ATTACHMENTS_FOLDER_PATH);
            throw new TechnicalException(msgErreur);
        }
    }

    /**
     * Récupère le chemin du dossier d'upload de SMG (paramétré dans le fichier
     * de conf).
     * 
     * @return le chemin du dossier d'upload de SMG
     * @throws TechnicalException
     *             si le dossier n'est pas configuré
     */
    private String getFolderUploadSmg() throws TechnicalException {
        try {
            return environnement.getRequiredProperty(SigifProprietes.PROP_SMARTGUIDE_UPLOAD);
        } catch (IllegalStateException ise) {
            String msgErreur = String.format(
                    "Le dossier d'upload des fichiers temporaires (%s) "
                            + "n'est pas paramétré dans le fichier de configuration. La récupération de la PJ est impossible.",
                    SigifProprietes.PROP_SMARTGUIDE_UPLOAD);
            throw new TechnicalException(msgErreur);
        }
    }

    @Override
    protected PieceJointeDAO getDao() {
        return (PieceJointeDAO) super.getDao();
    }

    @Override
    public int addPieceJointe(PieceJointeDTO piecej) throws TechnicalException {
        return this.getDao().addPieceJointe(toEntite(piecej));
    }

    @Override
    public void updatePieceJointe(PieceJointeDTO piecej) throws TechnicalException {
        this.getDao().updatePieceJointe(toEntite(piecej));
    }

    @Override
    public void deletePieceJointe(int idPiecej) throws TechnicalException {
        this.getDao().deletePieceJointe(idPiecej);
    }

    @Override
    public PieceJointe savePJ(String numTeledossier, String uploadStringPJ, String intitulePJ, TypePJ typePJ,
            String naturePj, DemandeAchat da, PosteDemandeAchat posteDa, ConstatationServiceFait csf,
            PosteConstatationServiceFait posteCsf) throws TechnicalException {
        try {
            File filePjToSave = AttachmentsUtils.checkUploadStringIsCorrect(uploadStringPJ);
            if (filePjToSave == null) {
                String msgErr = String.format(
                        "La PJ correspondant au texte '%s' est incorrecte (fichier inexistant ou texte incorrect)",
                        uploadStringPJ);
                LOGGER.error(msgErr);
                throw new ApplicationException(msgErr);
            }
            String filePjToSaveName = filePjToSave.getName();
            int idPJ = -1;
            PieceJointe pieceJ = new PieceJointe();
            String idPjToUpdate = "";

            // Num random pour ne pas risquer les erreurs à l'enregistrement en
            // cas d'enregistrement concurrent
            Random r = new Random();
            final int maxRand = 100000;
            int randomNum = r.nextInt(maxRand);
            switch (typePJ) {
            case DA:
                idPjToUpdate = "DA_" + numTeledossier + String.valueOf(randomNum) + "_Tmp";
                break;
            case Poste_DA:
                idPjToUpdate = "PDA_" + numTeledossier + String.valueOf(randomNum) + "_Tmp";
                break;
            case CSF:
                idPjToUpdate = "CSF_" + numTeledossier + String.valueOf(randomNum) + "_Tmp";
                break;
            case Poste_CSF:
                idPjToUpdate = "PCSF_" + numTeledossier + String.valueOf(randomNum) + "_Tmp";
                break;
            default:
                throw new TechnicalException("Type de pièce jointe inconnu : " + typePJ.toString());
            }

            // id piece jointe qui doit être unique (ne pas prendre un texte
            // en dur pour éviter une erreur à l'insertion)
            pieceJ.setIdPieceJointe(idPjToUpdate);
            pieceJ.setType(typePJ);
            pieceJ.setNature(naturePj);
            pieceJ.setNomFichier(filePjToSaveName);
            pieceJ.setIntitule(intitulePJ);
            pieceJ.setEmplacement("ToUpdate");
            pieceJ.setDemandeAchat(da);
            pieceJ.setConstatationServiceFait(csf);
            pieceJ.setPosteCsf(posteCsf);
            pieceJ.setPosteDemandeAchat(posteDa);
            idPJ = this.getDao().save(pieceJ);

            if (idPJ > 0) {
                String extension = filePjToSaveName.substring(filePjToSaveName.lastIndexOf('.'));
                String finalIdPieceJointe = numTeledossier + "_" + idPJ;
                String finalFileName = finalIdPieceJointe + extension;

                try {
                    // Deplacement de la PJ de emplacement vers disque local
                    File finalFile = AttachmentsUtils.copyFileToAttachmentsFolder(filePjToSave, typePJ, numTeledossier,
                            finalFileName, this.getFolderPj());
                    // Mettre à jour l'Identifiant et l'emplacement de la
                    // pièce jointe :
                    PieceJointe toUpdate = this.getDao().getPieceJointeById(idPJ);
                    toUpdate.setIdPieceJointe(finalIdPieceJointe);
                    toUpdate.setEmplacement(finalFile.getAbsolutePath());
                    this.getDao().updatePieceJointe(toUpdate);
                    return toUpdate;
                } catch (Exception e) {
                    LOGGER.error("Erreur lors de la copie de la PJ ou de la MAJ de la PJ =>"
                            + " Rollback de la PJ temporaire : " + e.getMessage());
                    this.getDao().deleteById(idPJ);
                    throw new TechnicalException("Erreur lors de la copie de la PJ ou de la MAJ de la PJ ", e);
                }

            } else {
                LOGGER.error("Erreur lors de l'enregistrement de la pièce jointe. ");
                throw new TechnicalException("Erreur lors de l'enregistrement de la pièce jointe. ");
            }
        } catch (TechnicalException techEx) {
            throw techEx;
        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'enregistrement de la PJ : " + e.getMessage());
            throw new TechnicalException("Erreur lors de l'enregistrement de la PJ", e);
        }
    }

    @Override
    public Map<String, String> getPjAndCopyFileInTmp(int idPj) throws TechnicalException {
        Map<String, String> infosPj = null;
        try {            
            // Lancement du thread de nettoyage
            Thread cleaningThread = new Thread(new CleaningThread(this.getFolderUploadSmg()));
            cleaningThread.start();
            
            PieceJointe pj = this.getDao().getById(idPj);
            File fileInPjFolder = new File(pj.getEmplacement());

            // Copie de la PJ du dossier des PJ vers le dossier tmp
            File fileInTmpFolder = AttachmentsUtils.copyFileToUploadFolder(fileInPjFolder, pj.getNomFichier(),
                    this.getFolderUploadSmg());
            infosPj = new HashMap<String, String>();
            infosPj.put(Constantes.KEY_MAP_ID, String.valueOf(pj.getId()));
            infosPj.put(Constantes.KEY_MAP_NOM, pj.getNomFichier());
            infosPj.put(Constantes.KEY_MAP_INTITULE_PJ, pj.getIntitule());
            infosPj.put(Constantes.KEY_MAP_NATURE, pj.getNature());
            infosPj.put(Constantes.KEY_MAP_TYPE_PJ, pj.getType().toString());
            infosPj.put(Constantes.KEY_MAP_EMPLACEMENT, pj.getEmplacement());
            infosPj.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ,
                    AttachmentsUtils.generateUploadStringFromFile(fileInTmpFolder));
            
        } catch (TechnicalException techEx) {
            LOGGER.error("Erreur lors de la lecture des infos de la PJ ou de sa copie vers le dossier tmp : "
                    + techEx.getMessage());
            throw techEx;
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la lecture des infos de la PJ ou de sa copie vers le dossier tmp : "
                    + e.getMessage());
            throw new TechnicalException(
                    "Erreur lors de la lecture des infos de la PJ ou de sa copie vers le dossier tmp", e);
        }
        return infosPj;
    }

    @Override
    public void deletePjDA(DemandeAchat da) throws TechnicalException {
        if (da.getPieceJointe() != null) {
            this.deletePj(da.getPieceJointe());
        }
    }

    @Override
    public void deletePjCsf(ConstatationServiceFait csf) throws TechnicalException {
        if (csf.getPiecesJointes() != null) {
            for (PieceJointe pj : csf.getPiecesJointes()) {
                this.deletePj(pj);
            }
        }
    }

    @Override
    public void deletePj(PieceJointe pj) throws TechnicalException {
        try {
            // Suppression du fichier dans le dossier upload
            File filePj = new File(pj.getEmplacement());
            String msgWarn = String.format(
                    "Le fichier PJ ayant les caractéristiques suivantes n'a pas pu être supprimé : "
                            + "idPieceJointe = %s, nom de fichier = %s, intitulé = %s, type = %s, nature = %s.",
                    pj.getIdPieceJointe(), pj.getNomFichier(), pj.getIntitule(),
                    pj.getType() != null ? pj.getType().toString() : "", pj.getNature());
            try {
                // Si la suppression du fichier échoue, on loggue un warning
                if (!filePj.delete()) {
                    LOGGER.warn(msgWarn);
                } else {
                    String[] listFiles = filePj.getParentFile().list();
                    if (listFiles != null && listFiles.length == 0) {
                        FileUtils.deleteQuietly(filePj.getParentFile());
                    }
                }
            } catch (SecurityException e) {
                LOGGER.warn(msgWarn);
            }
            // Suppression de l'entité en BD
            this.getDao().delete(pj);
        } catch (Exception e) {
            String msgErr = String.format(
                    "Erreur lors de la suppression de la PJ ayant les caractéristiques suivantes : "
                            + "idPieceJointe = %s, nom de fichier = %s, intitulé = %s, type = %s, nature = %s.",
                    pj.getIdPieceJointe(), pj.getNomFichier(), pj.getIntitule(),
                    pj.getType() != null ? pj.getType().toString() : "", pj.getNature());
            LOGGER.error(msgErr);
            throw new TechnicalException(msgErr, e);
        }
    }

    @Override
    public void saveFolderPj(TypePJ typePj, String numTeledossier) throws TechnicalException {
        AttachmentsUtils.saveAttachmentsFolder(typePj, numTeledossier, getFolderPj());
    }

    @Override
    public void rollbackFolderPj(TypePJ typePj, String numTeledossier) {
        try {
            AttachmentsUtils.rollbackAttachmentsFolder(typePj, numTeledossier, getFolderPj());
        } catch (TechnicalException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteSaveFolderPj(TypePJ typePj, String numTeledossier) {
        try {
            AttachmentsUtils.deleteSaveFolderPj(typePj, numTeledossier, getFolderPj());
        } catch (TechnicalException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteFolderPj(TypePJ typePj, String numTeledossier) {
        try {
            AttachmentsUtils.deleteFolderPj(typePj, numTeledossier, getFolderPj());
        } catch (TechnicalException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
