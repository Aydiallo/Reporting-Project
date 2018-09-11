package com.sigif.service;

import java.util.Map;
import java.util.Set;

import com.sigif.dto.PosteConstatationServiceFaitDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.ConstatationServiceFait;
import com.sigif.modele.PosteConstatationServiceFait;

/**
 * Service d'accès aux postes des constatations de service fait.
 * @author Mickaël Beaupoil
 */
public interface PosteCsfService extends AbstractService<PosteConstatationServiceFaitDTO> {

    /**
     * Récupère les postes CSF liés à un poste CA. Si un numéro de télédossier
     * CSF est passé à l'appel, les postes de cette CSF sont exclus des
     * résultats.Le résultat est sous forme d'une Map dont chaque entrée
     * représente une colonne du tableau résultat (clé = titre de la colonne,
     * valuer = tableau de valeurs de la colonne). Les clés suivantes sont
     * disponibles : <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_CSF} : Numéro de la CSF
     * (idSap) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_POSTE_CSF} : Numéro du
     * poste CSF (idSap) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_RECEPTION} : Date de
     * réception (date de création de la CSF) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_RECEPTIONNAIRE} :
     * Réceptionnaire (sous forme Prénom Nom)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_RECUE} : Quantité reçue
     * (concaténée avec la désignation de l'unité du poste de CA entre
     * parenthèses)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} : Quantité
     * Acceptée (concaténée avec la désignation de l'unité du poste de CA entre
     * parenthèses)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} : Statut d'avancement
     * du poste CSF <BR>
     * 
     * @param numCa
     *            Numéro de CA (idSap)
     * @param numPoste
     *            numéro de poste (idSAP)
     * @param numTeledossierCsf
     *            numéro de télédossier de la CSF courante (pour exclure le
     *            poste en cours de création des résultats)
     * @return Les postes CSF liés à un poste CA
     * @throws ApplicationException
     *             si l'un des paramètres est vide ou null
     * @throws TechnicalException
     *             si la recherche échoue pour une raison technique
     */
    Map<String, Object[]> getPostesCsfByPosteCa(String numCa, String numPoste, String numTeledossierCsf)
            throws ApplicationException, TechnicalException;

    /**
     * Retourne une Map contenant les informations des pièces jointes d'un poste
     * de CSF donné : <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NATURE} : Nature de la PJ <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_INTITULE_PJ} : Intitulé de la
     * PJ <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_EMPLACEMENT} : Emplacement de
     * la PJ<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_UPLOAD_STRING_PJ} : Upload
     * String de la PJ à affecter à un champ upload.
     * 
     * @param numDossierCSF
     *            numéro de télédossier de la CSF
     * @param idCSFPoste
     *            numéro de poste CSF
     * @return une Map des infos du PosteCSF
     * @throws ApplicationException
     *             Si l'un ds paramètres est null ou vide
     * 
     * @author Meissa Beye, Mickaël Beaupoil
     * @since 23/06/2017
     */
    Map<String, String> getPjInfosOfPosteCsf(String numDossierCSF, String idCSFPoste) throws ApplicationException;

    /**
     * Récupère les postes CSF. Le résultat est sous forme d'une Liste de Map
     * dont chaque entrée représente une colonne du tableau résultat (clé =
     * titre de la colonne, valuer = tableau de valeurs de la colonne). Les clés
     * suivantes sont disponibles : <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_CA_POSTE} : Numéro de
     * la CA/numéro de poste CA (idSap) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_POSTE_CSF} : Numéro du
     * poste CSF (idSap) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} : Référence du
     * poste CSF (idSap) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} : Designation du
     * poste CA<BR>
     * * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_COMMANDEE} : Quantité
     * commandée pour le poste CA (concaténée avec la désignation de l'unité du
     * poste de CA entre parenthèses)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_RECUE} : Quantité reçue
     * (concaténée avec la désignation de l'unité du poste de CA entre
     * parenthèses)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} : Quantité
     * Acceptée (concaténée avec la désignation de l'unité du poste de CA entre
     * parenthèses)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} : Statut d'avancement
     * du poste CSF.<BR>
     * 
     * @return Une map représentant le tableau des postes CSF.
     * @param numCsf
     *            Numéro de Csf
     * @throws ApplicationException
     *             si l'un des paramètres est vide ou null
     * @throws TechnicalException
     *             si la recherche échoue pour une raison technique
     */
    Map<String, String[]> getItemsCsf(String numCsf) throws ApplicationException, TechnicalException;

    /**
     * Récupère les postes CSF en vue de les modifier. Le résultat est sous
     * forme d'une Liste de Map dont chaque entrée représente une colonne du
     * tableau résultat (clé = titre de la colonne, valeur = tableau de valeurs
     * de la colonne). Les clés suivantes sont disponibles : <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_CA_POSTE} : numéro de
     * poste CA (idSap) <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_POSTE_CSF} : Numéro du
     * poste CSF<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_ACHAT} : Type d'achat du
     * poste CA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} : Référence du
     * poste CA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} : Designation du
     * poste CA<BR>
     * * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_COMMANDEE} : Quantité
     * commandée pour le poste CA <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_RECUE} : Quantité reçue
     * <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} : Quantité
     * Acceptée <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} : Quantité
     * Acceptée <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_UNITE} : Unité du poste
     * CA.<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_COMMENTAIRE} : Commentaire du
     * poste <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NATURE} : Nature de la PJ <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_INTITULE_PJ} : Intitulé de la
     * PJ <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_UPLOAD_STRING_PJ} : PJ <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CODE_LIEU_STOCKAGE} : Id du
     * Lieu de stockage <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_LIEU_STOCKAGE} : Lieu de
     * stockage <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT} : Statut d'avancement
     * <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_MOTIF_REJET} : Motif de rejet
     * du poste <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_ID} : Id SAP (pour ne pas le
     * perdre à la rectif)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DATE_MODIFICATION} : Date de
     * modification SAP (pour ne pas la perdre à la rectif) <BR>
     * 
     * @return Une map représentant le tableau des postes CSF.
     * @param numCsf
     *            Numéro de Csf
     * @throws ApplicationException
     *             si l'un des paramètres est vide ou null
     * @throws TechnicalException
     *             si la recherche échoue pour une raison technique
     */
    Map<String, String[]> getDetailedItemsCsf(String numCsf) throws ApplicationException, TechnicalException;
    
    /**
     * Récupère les informations du poste CSF. Le résultat est sous forme d'une
     * Map dont chaque entrée représente une colonne du tableau résultat (clé =
     * titre de la colonne, valuer = tableau de valeurs de la colonne). Les clés
     * suivantes sont disponibles : <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_AVANCEMENT} : Statut
     * d'avancement du poste CSF<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_POSTE_CSF} : Numéro du
     * poste CSF<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_COMMENTAIRE} : Commentaire du
     * poste CSF<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_CA_POSTE} : Numéro de
     * CA / Numéro de poste CA<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_TYPE_ACHAT} : Désignation du
     * type d'achat<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_CATEGORIE} : Catégorie du
     * poste CSF<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_REFERENCE} : Référence du
     * poste CSF<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_DESIGNATION} : Désignation du
     * poste CSF<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_COMMANDEE} : Quantité
     * commandée pour le poste CA (concaténée avec la désignation de l'unité du
     * poste de CA entre parenthèses)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_RECUE} : Quantité reçue
     * (concaténée avec la désignation de l'unité du poste de CA entre
     * parenthèses)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} : Quantité
     * Acceptée (concaténée avec la désignation de l'unité du poste de CA entre
     * parenthèses)<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_ACCEPTATION_CSF} :
     * Statut d'acceptation du poste CSF<BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_LIEU_STOCKAGE} : Désignation
     * du lieu de stockage <BR>
     * - {@link com.sigif.util.Constantes#KEY_MAP_MOTIF_REJET} : Motif de rejet
     * du poste <BR>
     * 
     * @param numCsf
     *            Numéro de Csf
     * @param numPosteCsf
     *            Numero du poste CSF
     * @return Les infos du poste CSF
     * @throws ApplicationException
     *             si l'un des paramètres est vide ou null
     * @throws TechnicalException
     *             si la recherche échoue pour une raison technique
     */
    Map<String, String> getPosteCSFInfos(String numCsf, String numPosteCsf)
            throws ApplicationException, TechnicalException;

    /**
     * Enregistre le poste CSF en base.
     * 
     * @param mapPosteCsf
     *            Map représentant un poste de la CSF. Chaque map contient les
     *            clés suivantes : <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_POSTE_CSF} :
     *            Id du poste (IdCSFPoste) <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_NUMERO_CA_POSTE} :
     *            Numéro du poste CA (idSAP du poste) <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_QTE_RECUE} :
     *            Quantité reçue <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_QTE_ACCEPTEE} :
     *            Quantité acceptée <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_COMMENTAIRE} :
     *            Commentaire<BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_STATUT_AVANCEMENT}
     *            : Statut d'avancement <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_LIEU_STOCKAGE} : Id
     *            du lieu de stockage <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_NATURE} : Nature de
     *            pièce jointe ("Autre", "PV", "BL", "Facture") <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_UPLOAD_STRING_PJ} :
     *            Chaîne getString du champ upload de la PJ <BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_INTITULE} :
     *            Intitulé de la PJ (Référence)<BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_MOTIF_REJET} :
     *            Motif de rejet du poste<BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_ID} :
     *            Id SAP<BR>
     *            - {@link com.sigif.util.Constantes#KEY_MAP_DATE_MODIFICATION} :
     *            Date de modification SAP<BR>
     * 
     * @param csf
     *            Constatation de Service Fait à laquelle sera rattaché le poste
     * @param ca
     *            Commande d'achat à laquelle appartient le poste de CA lié au
     *            poste CSF
     * @return false si la quantité restante est insuffisante pour enlever la quantité acceptée
     * @throws TechnicalException
     *             si l'insertion échoue
     */
    boolean savePosteCsf(Map<String, String> mapPosteCsf, ConstatationServiceFait csf, CommandeAchat ca)
            throws TechnicalException;

    /**
     * Supprime tous les postes liés à une CSF.
     * 
     * @param csf
     *            La CSF à laquelle sont rattachés les postes à supprimer
     * @param deletePj TODO
     * @throws TechnicalException
     *             Si la suppression échoue
     */
    void deleteByCsf(ConstatationServiceFait csf, boolean deletePj) throws TechnicalException;
    
    /*alpha ajout*/
    void initialise(Set<PosteConstatationServiceFait> postesASupprimer);
}
