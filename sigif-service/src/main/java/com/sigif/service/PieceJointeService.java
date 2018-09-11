package com.sigif.service;

import java.util.Map;

import com.sigif.dto.PieceJointeDTO;
import com.sigif.enumeration.TypePJ;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.ConstatationServiceFait;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PieceJointe;
import com.sigif.modele.PosteConstatationServiceFait;
import com.sigif.modele.PosteDemandeAchat;

/**
 * Service de gestion des pièces jointes.
 * 
 * @author Mickaël Beaupoil
 *
 */
public interface PieceJointeService extends AbstractService<PieceJointeDTO> {

    /**
     * Ajoute en base une pièce jointe.
     * 
     * @param pieceJ
     *            DTO de la PJ à ajouter
     * @throws TechnicalException
     *             Si l'ajout échoue
     * @return Id de la PJ après ajout
     * @author Meissa Beye, Mickaël Beaupoil
     * @since 19-05-2017
     */
    int addPieceJointe(PieceJointeDTO pieceJ) throws TechnicalException;

    /**
     * Met à jour une pièce jointe.
     * 
     * @param piecej
     *            DTO de la PJ à ajouter
     * @throws TechnicalException
     *             Si la mise à jour échoue
     * @author Meissa Beye, Mickaël Beaupoil
     * @since 19-05-2017
     */
    void updatePieceJointe(PieceJointeDTO piecej) throws TechnicalException;

    /**
     * Supprime une pièce jointe à partir de son id.
     * 
     * @author Meissa Beye, Mickaël Beaupoil
     * @since 19-05-2017
     * @param idPiecej
     *            id de la PJ à supprimer
     * @throws TechnicalException
     *             si la suppression échoue
     */
    void deletePieceJointe(int idPiecej) throws TechnicalException;

    /**
     * Enregistre une PJ en BD et renvoie l'entité correspondante.
     * 
     * @param numTeledossier
     *            numéro de télédossier
     * @param uploadStringPJ
     *            getSTring sur un champ upload
     * @param intitulePJ
     *            intitulé de la PJ
     * @param typePJ
     *            type de la pièce jointe
     * @param naturePJ
     *            nature de la PJ (seulement pour CSF et posteCSF)
     * @param da
     *            Da à laquelle est rattachée la PJ
     * @param posteDa
     *            Poste Da auquel est rattachée la PJ
     * @param csf
     *            CSF à laquelle est rattachée la PJ
     * @param posteCsf
     *            Poste Csf auquel est rattachée la PJ
     * @author Meissa Beye, Mickaël Beaupoil
     * @return L'entité correspondant à la PJ enregistrée.
     * @throws TechnicalException
     *             Si la sauvegarde échoue
     * @since 07-06-2017
     */
    PieceJointe savePJ(String numTeledossier, String uploadStringPJ, String intitulePJ, TypePJ typePJ, String naturePJ,
            DemandeAchat da, PosteDemandeAchat posteDa, ConstatationServiceFait csf,
            PosteConstatationServiceFait posteCsf) throws TechnicalException;

    /**
     * Obtient les infos d'une pièce jointe et copie le fichier correspondant
     * dans un dossier de tmp pour pouvoir être affiché. Les données se
     * présentent sous forme de Map dont les clés sont :<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_ID} : Valeur = Id de la PJ
     * <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NOM} : Valeur = Nom du fichier
     * <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_INTITULE_PJ} : Valeur =
     * Intitulé de la PJ<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NATURE} : Valeur = Nature de
     * la PJ<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_PJ} : Valeur = Type de la
     * PJ<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_EMPLACEMENT} : Valeur =
     * Emplacement de la PJ dans le dossier de stockage<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_UPLOAD_STRING_PJ} : Valeur =
     * Chaîne utilisable pour afficher la PJ (qui pointe vers une copie dans le
     * dossier temporaire d'upload)<BR>
     * 
     * @param idPj
     *            id de la pièce jointe
     * @return Infos de la pièce jointe
     * @throws TechnicalException
     *             Si une erreur technique se produit (prb de copie, prb de
     *             BD,... )
     * @author Meissa Beye, Mickaël Beaupoil
     */
    Map<String, String> getPjAndCopyFileInTmp(int idPj) throws TechnicalException;

    /**
     * Permet de supprimer la piece jointe liée à une DA.
     * 
     * @param da
     *            la Demande d'achat
     * @throws TechnicalException
     *             Erreur lors de la suppression (entité ou fichier)
     * @author Mamadou Ndir, Mickaël Beaupoil
     */
    void deletePjDA(DemandeAchat da) throws TechnicalException;

    /**
     * Permet de supprimer la piece jointe liée à une CSF.
     * 
     * @param csf
     *            la Constatation de Service Fait
     * @throws TechnicalException
     *             Erreur lors de la suppression (entité ou fichier)
     * @author Mamadou Ndir, Mickaël Beaupoil
     */
    void deletePjCsf(ConstatationServiceFait csf) throws TechnicalException;

    /**
     * Permet de supprimer une piece jointe de la BD et su système de fichiers.
     * 
     * @param pj
     *            La pièce jointe à supprimer
     * @throws TechnicalException
     *             Erreur lors de la suppression (entité ou fichier)
     * @author Mickael Beaupoil
     */
    void deletePj(PieceJointe pj) throws TechnicalException;

    /**
     * Permet de sauvegarder le dossier des PJ d'un télédossier avant update.
     * 
     * @param typePj
     *            Type de dossier
     * @param numTeledossier
     *            Numéro de télédossier
     * @throws TechnicalException
     *             Erreur lors de la sauvegarde
     * @author Mickael Beaupoil
     */
    void saveFolderPj(TypePJ typePj, String numTeledossier) throws TechnicalException;

    /**
     * Permet de rollbacker le dossier sauvegardé des PJ d'un télédossier après
     * échec de l'update.
     * 
     * @param typePj
     *            Type de dossier
     * @param numTeledossier
     *            Numéro de télédossier
     * @author Mickael Beaupoil
     */
    void rollbackFolderPj(TypePJ typePj, String numTeledossier);

    /**
     * Supprime le dossier sauvegardé des PJ d'un télédossier après réussite de
     * l'update.
     * 
     * @param typePj
     *            Type de dossier
     * @param numTeledossier
     *            Numéro de télédossier
     * @author Mickael Beaupoil
     */
    void deleteSaveFolderPj(TypePJ typePj, String numTeledossier);

    /**
     * Supprime le dossier des PJ d'un télédossier après échec d'une création ou
     * lors de la suppression de la CSF complète.
     * 
     * @param typePj
     *            Type de dossier
     * @param numTeledossier
     *            Numéro de télédossier
     * @author Mickael Beaupoil
     */
    void deleteFolderPj(TypePJ typePj, String numTeledossier);

}