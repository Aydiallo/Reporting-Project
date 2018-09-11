package com.sigif.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.sigif.enumeration.TypePJ;
import com.sigif.exception.TechnicalException;

/**
 * Classe utilitaire pour la gestion des fichiers pièces jointes.
 * 
 * @author Mickael Beaupoil
 *
 */
public final class AttachmentsUtils {
    /**
     * Constructeur privé pour classe utilitaire.
     */
    private AttachmentsUtils() {
    }

    /**
     * Vérifie que le paramètre (GetString de smartguide sur un champ upload)
     * est correct (le nom du fichier est lisible, le chemin est décodable et le
     * fichier existe).<BR>
     * Note : Le format du getString est le suivant : "Nom du fichier" + espace
     * + "chemin du fichier encodé avec l'encodage iso-8859-1" *
     * 
     * @param uploadString
     *            GetString de smartguide sur un champ upload
     * @return le fichier dans le dossier temporaire s'il est correct, null
     *         sinon
     */
    public static File checkUploadStringIsCorrect(String uploadString) {
        File result = null;
        int nbLastSpace = uploadString.lastIndexOf(' ');
        String fileName = uploadString.substring(0, nbLastSpace);
        String filePathEncoded = uploadString.substring(nbLastSpace + 1);
        try {
            String filePath = URLDecoder.decode(filePathEncoded, "iso-8859-1");
            File fichier = new File(filePath);
            // Vérifie que le nom décodé est le même que celui envoyé par SMG +
            // le fichier existe dans le tmp
            if (fileName != null && fileName.equals(fichier.getName()) && fichier.isFile()) {
                result = fichier;
            }
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        return result;
    }

    /**
     * Copie le fichier indiqué par le getString de smartguide dans le
     * répertoire de stockage des PJ en fonction de son type et de son n° de
     * télédossier. Le format de stockage est le suivant :
     * sigif.attachments.folder/[DA ou CSF]/
     * [N°dossier]/[N°dossier]_[idPJ].[extension]
     * 
     * @param typePJ
     *            Type de PJ (DA, poste_DA, CSF ou Poste_CSF)
     * @param uploadFile
     *            Fichier dans le dossier temporaire
     * @param numTeledossier
     *            N° de télédossier
     * @param fileName
     *            Nom du fichier final (idPieceJointe)
     * @param folderPjPath
     *            Chemin du dossier des PJ
     * @return Fichier après copie
     * @throws TechnicalException
     *             - Si la propriété "sigif.attachments.folder" n'est pas
     *             configurée<BR>
     *             - Si le dossier indiqué n'existe pas (pas de création
     *             automatique) <BR>
     *             - Si le paramètre typePJ n'est pas un type reconnu <BR>
     *             - Si un fichier portant le nom du fichier final existe déjà
     *             <BR>
     *             - Si une erreur se produit lors de la copie
     */
    public static File copyFileToAttachmentsFolder(File uploadFile, TypePJ typePJ, String numTeledossier,
            String fileName, String folderPjPath) throws TechnicalException {
        File finalFile = null;

        // Récupération du chemin du dossier des PJ et vérif qu'il existe

        File folderPj = new File(folderPjPath);
        if (!folderPj.isDirectory()) {
            String msgErreur = String.format(
                    "Le dossier de stockage des pièces jointes (%s) n'existe pas. L'enregistrement est impossible.",
                    folderPj.getAbsolutePath());
            throw new TechnicalException(msgErreur);
        }

        String subFolder = getSubFolderByTypePj(typePJ);

        // Les PJ sont stockées dans un dossier portant le numéro de
        // télédossier
        File subFolderPj = new File(folderPj, subFolder);
        subFolderPj = new File(subFolderPj, numTeledossier);
        if (subFolderPj.isFile()) {
            String msgErreur =
                    String.format("Un fichier portant le nom du dossier de stockage des pièces jointes (%s) existe. "
                            + "L'enregistrement est impossible.", subFolderPj.getAbsolutePath());
            throw new TechnicalException(msgErreur);
        }
        // Création éventuelle des sous-dossiers
        if (!subFolderPj.isDirectory()) {
            subFolderPj.mkdirs();
        }

        finalFile = new File(subFolderPj, fileName);
        try {
            FileUtils.copyFile(uploadFile, finalFile, true);
        } catch (IOException e) {
            String msgErreur =
                    String.format("Une erreur s'est produite lors de l'enregistrement de la pièce jointe '%s' : %s",
                            subFolderPj.getAbsolutePath(), e.getMessage());
            throw new TechnicalException(msgErreur, e);
        }

        return finalFile;
    }

    /**
     * Supprime un fichier du dossier des pièces jointes.
     * 
     * @param typePJ
     *            Type de PJ (DA, poste_DA, CSF ou Poste_CSF)
     * @param uploadFile
     *            Fichier dans le dossier temporaire
     * @param numTeledossier
     *            N° de télédossier
     * @param fileName
     *            Nom du fichier final (idPieceJointe)
     * @param folderPjPath
     *            Chemin du dossier des PJ
     * @throws TechnicalException
     *             si le type de PJ n'existe pas
     */
    public static void deleteFileInAttachmentsFolder(File uploadFile, TypePJ typePJ, String numTeledossier,
            String fileName, String folderPjPath) throws TechnicalException {
        File folderPj = new File(folderPjPath);
        String subFolder = getSubFolderByTypePj(typePJ);

        File finalFile = new File(folderPj, subFolder);
        finalFile = new File(finalFile, numTeledossier);
        finalFile = new File(finalFile, fileName);
        FileUtils.deleteQuietly(finalFile);
    }

    /**
     * Copie le fichier qui est dans le dossier PJ dans un dossier temporaire et
     * renvoie ce fichier.
     * 
     * @param fileInPjFolder
     *            Fichier dans le dossier PJ
     * @param fileName
     *            Nom initial (et final donc) du fichier
     * @param folderUploadSmg
     *            Dossier d'upload des fichiers temporaires
     * @return le fichier après copie dans le dossier d'upload
     * @throws TechnicalException
     *             si la copie vers le dossier d'upload échoue
     */
    public static File copyFileToUploadFolder(File fileInPjFolder, String fileName, String folderUploadSmg)
            throws TechnicalException {
        File finalFile = null;
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmssSSSSS");
        File finalFolder = new File(folderUploadSmg, formater.format(new Date()));
        try {
            if (!finalFolder.exists()) {
                finalFolder.mkdirs();
            }
            finalFile = new File(finalFolder, fileName);
            FileUtils.copyFile(fileInPjFolder, finalFile, true);
        } catch (IOException e) {
            String msgErreur = String.format(
                    "Une erreur s'est produite lors de la copie de la pièce jointe '%s' vers le dossier d'upload '%s' : %s",
                    fileInPjFolder.getAbsolutePath(), folderUploadSmg, e.getMessage());
            throw new TechnicalException(msgErreur, e);
        }

        return finalFile;
    }

    /**
     * Génère la chaîne à affecter à un champ upload Smg pour afficher un
     * fichier déjà chargé.
     * 
     * @param uploadedFile
     *            le fichier dans le dossier temporaire de SMG
     * @return la chaîne à affecter à un champ upload Smg pour afficher un
     *         fichier déjà chargé.
     */
    public static String generateUploadStringFromFile(File uploadedFile) {
        String fileName = uploadedFile.getName();
        String filePath = uploadedFile.getAbsolutePath();
        String folderPathEncoded;        
        String result = null;
        try {
            // encodage du chemin (les espaces sont remplacés par des + (règles de codage de paramètre)
            // mais comme on l'utilise dans une URL, il faut remplacer les + par des %20
            folderPathEncoded = URLEncoder.encode(filePath, "iso-8859-1").replace("+", "%20");
            result = fileName + ' ' + folderPathEncoded;
        } catch (UnsupportedEncodingException e) {
            // exception impossible car l'encodage est indiquée en dur
        }
        return result;
    }

    /**
     * Copie le dossier contenant les PJ pour un télédossier dans un dossier
     * Save pour pouvoir replacer les PJ à leur place en cas d'erreur d'update.
     * 
     * @param typePJ
     *            type de PJ
     * @param numTeledossier
     *            numéro de télédossier
     * @param folderPjPath
     *            chemin des pièces jointes
     * @throws TechnicalException
     *             si l'opération échoue
     */
    public static void saveAttachmentsFolder(TypePJ typePJ, String numTeledossier, String folderPjPath)
            throws TechnicalException {
        File folderPj = new File(folderPjPath);
        String subFolder = getSubFolderByTypePj(typePJ);

        File globalDir = new File(folderPj, subFolder);
        File initialDir = new File(globalDir, numTeledossier);

        File finalDir = new File(globalDir, "Save");

        try {
            // S'il y a des fichiers à sauvegarder
            if (initialDir.isDirectory() && initialDir.list().length > 0) {
                // Suppression du dossier de sauvegarde s'il existe
                if (finalDir.isDirectory()) {
                    FileUtils.deleteDirectory(finalDir);
                }
                FileUtils.copyDirectoryToDirectory(initialDir, finalDir);
            }

        } catch (IOException e) {
            throw new TechnicalException("Impossible de sauvegarder le dossier des PJ pour le télédossier '"
                    + numTeledossier + "' : " + e.getMessage());
        }

    }

    /**
     * Copie le dossier sauvegardé contenant les PJ pour un télédossier à la
     * place du dossier normal en cas d'erreur d'update.
     * 
     * @param typePJ
     *            type de PJ
     * @param numTeledossier
     *            numéro de télédossier
     * @param folderPjPath
     *            chemin des pièces jointes
     * @throws TechnicalException
     *             si l'opération échoue
     */
    public static void rollbackAttachmentsFolder(TypePJ typePJ, String numTeledossier, String folderPjPath)
            throws TechnicalException {
        File folderPj = new File(folderPjPath);
        String subFolder = getSubFolderByTypePj(typePJ);

        File globalDir = new File(folderPj, subFolder);

        File rollbackDir = new File(globalDir, "Save");
        rollbackDir = new File(rollbackDir, numTeledossier);
        File normalDir = new File(globalDir, numTeledossier);

        try {
            // S'il y a des fichiers à rollbacker
            if (rollbackDir.isDirectory() && rollbackDir.list().length > 0) {
                // Suppression du dossier de sauvegarde s'il existe
                if (normalDir.isDirectory()) {
                    FileUtils.deleteDirectory(normalDir);
                }
                FileUtils.moveDirectoryToDirectory(rollbackDir, globalDir, true);
            }

        } catch (IOException e) {
            throw new TechnicalException("Impossible de rollbacker le dossier des PJ pour le télédossier '"
                    + numTeledossier + "' : " + e.getMessage());
        }
    }

    /**
     * Supprime le dossier sauvegardé des PJ d'un télédossier après réussite de
     * l'update.
     * 
     * @param typePJ
     *            Type de dossier
     * @param numTeledossier
     *            Numéro de télédossier
     * @param folderPjPath
     *            chemin des pièces jointes
     * @author Mickael Beaupoil
     * @throws TechnicalException
     *             si la suppression échoue
     */
    public static void deleteSaveFolderPj(TypePJ typePJ, String numTeledossier, String folderPjPath)
            throws TechnicalException {
        File folderPj = new File(folderPjPath);
        String subFolder = getSubFolderByTypePj(typePJ);

        File globalDir = new File(folderPj, subFolder);

        File rollbackDir = new File(globalDir, "Save");
        rollbackDir = new File(rollbackDir, numTeledossier);

        try {
            // Suppression du dossier de sauvegarde s'il existe
            if (rollbackDir.isDirectory()) {
                FileUtils.deleteDirectory(rollbackDir);
            }
        } catch (IOException e) {
            throw new TechnicalException("Impossible de supprimer le dossier de sauvegarde des PJ pour le télédossier '"
                    + numTeledossier + "' : " + e.getMessage());
        }
    }

    /**
     * Supprime le dossier des PJ d'un télédossier après échec d'une création.
     * 
     * @param typePJ
     *            Type de dossier
     * @param numTeledossier
     *            Numéro de télédossier
     * @param folderPjPath
     *            chemin des pièces jointes
     * @author Mickael Beaupoil
     * @throws TechnicalException
     *             si la suppression échoue
     */
    public static void deleteFolderPj(TypePJ typePJ, String numTeledossier, String folderPjPath)
            throws TechnicalException {
        File folderPj = new File(folderPjPath);
        String subFolder = getSubFolderByTypePj(typePJ);

        File globalDir = new File(folderPj, subFolder);

        File pjDir = new File(globalDir, numTeledossier);

        try {
            // Suppression du dossier de sauvegarde s'il existe
            if (pjDir.isDirectory()) {
                FileUtils.deleteDirectory(pjDir);
            }
        } catch (IOException e) {
            throw new TechnicalException("Impossible de supprimer le dossier des PJ pour le télédossier '"
                    + numTeledossier + "' : " + e.getMessage());
        }
    }

    /**
     * Récupère le nom du sous-dossier stockant les pj en fonction du type de PJ.
     * @param typePJ type de PJ
     * @return le nom du sous-dossier stockant les pj en fonction du type de PJ.
     * @throws TechnicalException si le type de pj est inconnu
     */
    private static String getSubFolderByTypePj(TypePJ typePJ) throws TechnicalException {
        String subFolder = "";
        switch (typePJ) {
        case DA:
        case Poste_DA:
            subFolder = Constantes.ATTACHMENTS_FOLDER_DA;
            break;
        case CSF:
        case Poste_CSF:
            subFolder = Constantes.ATTACHMENTS_FOLDER_CSF;
            break;
        default:
            throw new TechnicalException("Type de PJ inconnu : " + typePJ.toString());
        }
        return subFolder;
    }
}
