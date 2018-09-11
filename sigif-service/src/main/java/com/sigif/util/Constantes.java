package com.sigif.util;

/**
 * Classe regroupant toutes les constantes de l'application.
 * 
 *
 */
public final class Constantes {

	/**
	 * Constructeur privé vide car classe utilitaire.
	 */
	private Constantes() {
	}

	// Valeurs de retour de la fonction de récupération des habilitations

	/**
	 * Valeur du retour de la getHabilitations indiquant que l'utilisateur est
	 * réceptionnaire et demandeur.
	 */
	public static final int HABILITATION_DEMAND_AND_RECEP = 3;

	/**
	 * Valeur du retour de la getHabilitations indiquant que l'utilisateur est
	 * uniquement demandeur.
	 */
	public static final int HABILITATION_ONLY_DEMAND = 1;

	/**
	 * Valeur du retour de la getHabilitations indiquant que l'utilisateur est
	 * uniquement réceptionnaire.
	 */
	public static final int HABILITATION_ONLY_RECEP = 2;

	/**
	 * Valeur du retour de la getHabilitations indiquant que l'utilisateur n'est
	 * ni réceptionnaire, ni demandeur.
	 */
	public static final int HABILITATION_NEITHER_DEMAND_NOR_RECEP = 0;

	// Code en base des rôles
	/**
	 * Rôle Demandeur.
	 */
	public static final String ROLE_DEMANDEUR = "DDA";

	/**
	 * Rôle Réceptionnaire.
	 */
	public static final String ROLE_RECEPTIONNAIRE = "RSF";

	// Valeur de retour du check identity
	/**
	 * L'utilisateur a un compte Sigif et son compte n'est pas désactivé
	 * (colonne compteActif), ni inactif (colonne statut).
	 */
	public static final int CHECK_IDENTITY_OK = 1;

	/**
	 * L'utilisateur n'a pas de compte Sigif ou il est désactivé.
	 */
	public static final int CHECK_IDENTITY_INVALID_ACCOUNT = 2;

	/**
	 * L'utilisateur a un compte Sigif non désactivé mais il a été supprimé
	 * (colonne statut à inactif).
	 */
	public static final int CHECK_IDENTITY_INACTIVE_ACCOUNT = 3;

	// Noms des éléments renvoyés dans les Map pour les fonctions
	/**
	 * Clé pour les colonnes Id.
	 */
	public static final String KEY_MAP_ID = "Id";

	/**
	 * Clé pour les colonnes Action.
	 */
	public static final String KEY_MAP_ACTION = "Action";

	/**
	 * Clé pour les colonnes Activite.
	 */
	public static final String KEY_MAP_ACTIVITE = "Activite";

	/**
	 * Clé pour les colonnes Categorie.
	 */
	public static final String KEY_MAP_CATEGORIE = "Categorie";

	/**
	 * Clé pour les colonnes Categorie d'achat.
	 */
	public static final String KEY_MAP_CATEGORIE_ACHAT = "CategorieAchat";

	/**
	 * Clé pour les colonnes Civilite.
	 */
	public static final String KEY_MAP_CIVILITE = "Civilite";

	/**
	 * Clé pour les colonnes Code.
	 */
	public static final String KEY_MAP_CODE = "Code";

	/**
	 * Clé pour les colonnes code devise.
	 */
	public static final String KEY_MAP_CODE_DEVISE = "CodeDevise";

	/**
	 * Clé pour les colonnes code postal.
	 */
	public static final String KEY_MAP_CODE_POSTAL = "CodePostal";

	/**
	 * Clé pour les colonnes Commentaire.
	 */
	public static final String KEY_MAP_COMMENTAIRE = "Commentaire";

	/**
	 * Clé pour les colonnes date de création.
	 */
	public static final String KEY_MAP_DATE_CREATION = "DateCreation";

	/**
	 * Clé pour les colonnes date de livraison.
	 */
	public static final String KEY_MAP_DATE_LIVRAISON = "DateLivraisonSouhaite";

	/**
	 * Clé pour les colonnes date de réception.
	 */
	public static final String KEY_MAP_DATE_RECEPTION = "DateReception";

	/**
	 * Demandeur.
	 */
	public static final String KEY_MAP_DEMANDEUR = "Demandeur";

	/**
	 * Demandeur.
	 */
	public static final String KEY_MAP_LOGIN = "Login";

	/**
	 * Clé pour les colonnes Designation.
	 */
	public static final String KEY_MAP_DESIGNATION = "Designation";

	/**
	 * Clé pour les colonnes Devise.
	 */
	public static final String KEY_MAP_DEVISE = "Devise";

	/**
	 * Clé pour les colonnes Email.
	 */
	public static final String KEY_MAP_EMAIL = "Email";

	/**
	 * Clé pour les colonnes Emplacement.
	 */
	public static final String KEY_MAP_EMPLACEMENT = "Emplacement";

	/**
	 * Clé pour les colonnes Nom de fichier.
	 */
	public static final String KEY_MAP_FILENAME = "FileName";

	/**
	 * Clé pour les colonnes Fond.
	 */
	public static final String KEY_MAP_FOND = "Fond";

	/**
	 * Clé pour les colonnes Intitulé.
	 */
	public static final String KEY_MAP_INTITULE = "Intitule";

	/**
	 * Clé pour les colonnes IntitulePJ.
	 */
	public static final String KEY_MAP_INTITULE_PJ = "IntitulePJ";

	/**
	 * Ministere.
	 */
	public static final String KEY_MAP_MINISTERE = "Ministere";

	/**
	 * Clé pour les colonnes du nombre de CSF liées.
	 */
	public static final String KEY_MAP_NB_CSF = "NbCsf";

	/**
	 * Clé pour les colonnes de la nature (pour PJ).
	 */
	public static final String KEY_MAP_NATURE = "Nature";

	/**
	 * Clé pour les colonnes du nombre de postes de CSF liés.
	 */
	public static final String KEY_MAP_NB_POSTES_CSF = "NBPostesCsf";

	/**
	 * Clé pour les colonnes du nom.
	 */
	public static final String KEY_MAP_NOM = "Nom";

	/**
	 * Clé pour les colonnes Numero.
	 */
	public static final String KEY_MAP_NUMERO = "Numero";

	/**
	 * Clé pour les colonnes Numero de poste des DA.
	 */
	public static final String KEY_MAP_NUMERO_POSTE_DA = "NumeroPosteDA";

	/**
	 * Clé pour les colonnes Numero de CSF.
	 */
	public static final String KEY_MAP_NUMERO_CSF = "NumeroCsf";

	/**
	 * Clé pour les colonnes Numero de CA.
	 */
	public static final String KEY_MAP_NUMERO_CA = "NumeroCa";

	/**
	 * Clé pour les colonnes Numero de CSF.
	 */
	public static final String KEY_MAP_NUMERO_POSTE_CSF = "NumeroPosteCsf";

	/**
	 * Clé pour les colonnes Portable.
	 */
	public static final String KEY_MAP_PORTABLE = "Portable";

	/**
	 * Clé pour les colonnes Prix.
	 */
	public static final String KEY_MAP_PRIX = "Prix";

	/**
	 * Clé pour les colonnes Programme.
	 */
	public static final String KEY_MAP_PROGRAMME = "Programme";

	/**
	 * Clé pour les colonnes Quantite.
	 */
	public static final String KEY_MAP_QUANTITE = "Quantite";

	/**
	 * Clé pour les colonnes QteAcceptee.
	 */
	public static final String KEY_MAP_QTE_ACCEPTEE = "QteAcceptee";

	/**
	 * Clé pour les colonnes QteCommandee.
	 */
	public static final String KEY_MAP_QTE_COMMANDEE = "QteCommandee";

	/**
	 * Clé pour les colonnes QteRestante.
	 */
	public static final String KEY_MAP_QTE_RESTANTE = "QteRestante";

	/**
	 * Clé pour les colonnes Quantité reçue.
	 */
	public static final String KEY_MAP_QTE_RECUE = "QteRecue";

	/**
	 * Clé pour les colonnes Receptionnaire.
	 */
	public static final String KEY_MAP_RECEPTIONNAIRE = "Receptionnaire";

	/**
	 * Clé pour les colonnes Reference.
	 */
	public static final String KEY_MAP_REFERENCE = "Reference";

	/**
	 * Clé pour les colonnes Region.
	 */
	public static final String KEY_MAP_REGION = "Region";

	/**
	 * Clé pour les colonnes Rue.
	 */
	public static final String KEY_MAP_RUE = "Rue";

	/**
	 * Clé pour les colonnes ServiceBeneficiaire.
	 */
	public static final String KEY_MAP_SERVICE_BENEFICIAIRE = "ServiceBeneficiaire";

	/**
	 * Clé pour les colonnes ServiceDepensier.
	 */
	public static final String KEY_MAP_SERVICE_DEPENSIER = "ServiceDepensier";

	/**
	 * Clé pour les colonnes Statut.
	 */
	public static final String KEY_MAP_STATUT = "Statut";

	/**
	 * Clé pour les colonnes Statut approbation.
	 */
	public static final String KEY_MAP_STATUT_APPROBATION = "StatutApprobation";

	/**
	 * Clé pour les colonnes Statut avancement.
	 */
	public static final String KEY_MAP_STATUT_AVANCEMENT = "StatutAvancement";

	/**
	 * Clé pour les colonnes Telephone.
	 */
	public static final String KEY_MAP_TELEPHONE = "Telephone";

	/**
	 * Clé pour les colonnes Type.
	 */
	public static final String KEY_MAP_TYPE = "Type";

	/**
	 * Clé pour les colonnes TypeAchat.
	 */
	public static final String KEY_MAP_TYPE_ACHAT = "TypeAchat";

	/**
	 * Clé pour les colonnes TypeCommande.
	 */
	public static final String KEY_MAP_TYPE_COMMANDE = "TypeCommande";

	/**
	 * Clé pour les colonnes des types de pièces jointes.
	 */
	public static final String KEY_MAP_TYPE_PJ = "TypePj";

	/**
	 * Clé pour les colonnes Unite.
	 */
	public static final String KEY_MAP_UNITE = "Unite";

	/**
	 * Clé pour les colonnes UploadStringPJ.
	 */
	public static final String KEY_MAP_UPLOAD_STRING_PJ = "UploadStringPJ";

	/**
	 * Clé pour les colonnes Ville.
	 */
	public static final String KEY_MAP_VILLE = "Ville";

	/**
	 * Type d'achat "Achat stocké".
	 */
	public static final String TYPE_ACHAT_ACHAT_STOCKE = "S";

	/**
	 * Type d'achat "Fonctionnement".
	 */
	public static final String TYPE_ACHAT_FONCTIONNEMENT = "F";

	/**
	 * Type d'achat "Immobilisation".
	 */
	public static final String TYPE_ACHAT_IMMOBILISATION = "I";

	/**
	 * Nom du dossier contenant les PJ pour les DA et les postes DA.
	 */
	public static final String ATTACHMENTS_FOLDER_DA = "DA";

	/**
	 * Nom du dossier contenant les PJ pour les CSF et les postes CSF.
	 */
	public static final String ATTACHMENTS_FOLDER_CSF = "CSF";
	/**
	 * Clé pour la colonne date création.
	 */
	public static final String KEY_MAP_DATE_MODIFICATION = "DateModification";
	/**
	 * Titre.
	 */
	public static final String KEY_MAP_TITRE = "Titre";
	/**
	 * Objet.
	 */
	public static final String KEY_MAP_OBJET = "Objet";
	/**
	 * chemin de la pj de la DA.
	 */
	public static final String KEY_MAP_PJ_PATH = "Emplacement";

	/**
	 * Lieu de stockage.
	 */
	public static final String KEY_MAP_LIEU_STOCKAGE = "LieuStockage";
	/**
	 * Lieu de motif de rejet.
	 */
	public static final String KEY_MAP_MOTIF_REJET = "MotifRejet";
	/**
	 * Lieu de etat de la donnee.
	 */
	public static final String KEY_MAP_DATA_STATUS = "EtatDonnee";

	/**
	 * Clé pour les colonnes Description.
	 */
	public static final String KEY_MAP_DESCRIPTION = "Description";

	/**
	 * Clé pour code ministere.
	 */
	public static final String KEY_MAP_CODE_MINISTERE = "CodeMinistere";
	/**
	 * Clé pour code Service Depensier.
	 */
	public static final String KEY_MAP_CODE_SERVICE_DEPENSIER = "CodeServiceDepensier";
	/**
	 * Clé pour code Categorie Achat.
	 */
	public static final String KEY_MAP_CODE_CATEGORIE_ACHAT = "CodeCategorieAchat";
	/**
	 * Clé pour code Service Beneficiaire.
	 */
	public static final String KEY_MAP_CODE_SERVICE_BENEFICIAIRE = "CodeServiceBeneficiaire";
	/**
	 * Clé pour code Fond.
	 */
	public static final String KEY_MAP_CODE_FOND = "CodeFond";
	/**
	 * Clé pour code Type Fond.
	 */	
	public static final String KEY_MAP_CODE_TYPE_FOND = "CodeTypeFond";

	/**
	 * Clé pour code Gdm.
	 */
	public static final String KEY_MAP_CODE_GDM = "CodeGdm";
	/**
	 * Clé pour code Type Achat.
	 */
	public static final String KEY_MAP_CODE_TYPE_ACHAT = "CodeTypeAchat";
	/**
	 * Clé pour code Categorie.
	 */
	public static final String KEY_MAP_CODE_CATEGORIE = "CodeCategorie";
	/**
	 * Clé pour code Programme.
	 */
	public static final String KEY_MAP_CODE_PROGRAMME = "CodeProgramme";
	/**
	 * Clé pour code Action.
	 */
	public static final String KEY_MAP_CODE_ACTION = "CodeAction";
	/**
	 * Clé pour code Activite.
	 */
	public static final String KEY_MAP_CODE_ACTIVITE = "CodeActivite";
	/**
	 * Clé pour code Lieu de Stockage.
	 */
	public static final String KEY_MAP_CODE_LIEU_STOCKAGE = "CodeLieuStockage";
	/**
	 * Clé pour code Ville.
	 */
	public static final String KEY_MAP_CODE_VILLE = "CodeVille";
	/**
	 * Clé pour code Region.
	 */
	public static final String KEY_MAP_CODE_REGION = "CodeRegion";
	/**
	 * Clé pour code unité.
	 */
	public static final String KEY_MAP_CODE_UNITE = "CodeUnite";

	/**
	 * Clé pour Désignation unité.
	 */
	public static final String KEY_MAP_DESIGNATION_UNITE = "DesignationUnite";

	/**
	 * Clé pour les colonnes Designation de CategorieImmobilisation.
	 */
	public static final String KEY_MAP_DESIGNATION_CATEGORIEIMMO = "DesignationCategorieImmobilisation";

	/**
	 * Clé pour les colonnes Designation de groupe de marchandises.
	 */
	public static final String KEY_MAP_GDM = "Gdm";

	/**
	 * Clé pour les colonnes Code de CategorieImmobilisation.
	 */
	public static final String KEY_MAP_CODE_CATEGORIEIMMO = "CodeCategorieImmobilisation";

	/**
	 * Clé pour les colonnes Designation de TypeImmobilisation.
	 */
	public static final String KEY_MAP_DESIGNATION_TYPEIMMO = "DesignationTypeImmobilisation";

	/**
	 * Clé pour les colonnes Code de TypeImmobilisation.
	 */
	public static final String KEY_MAP_CODE_TYPEIMMO = "CodeTypeImmobilisation";

	/**
	 * Clé pour les colonnes message d'erreur.
	 */
	public static final String KEY_MAP_MSG_ERROR = "ErrorMessage";

	/**
	 * Statut d'acceptation acceptée.
	 */
	public static final String STATUT_ACCEPTATION_ACCEPTEE = "Acceptée";

	/**
	 * Statut d'acceptation acceptée partiellement.
	 */
	public static final String STATUT_ACCEPTATION_ACCEPTEE_PART = "Partiellement acceptée";

	/**
	 * Statut d'acceptation Refusée.
	 */
	public static final String STATUT_ACCEPTATION_REFUSEE = "Refusée";

	/**
	 * Statut d'acceptation d'une CSF.
	 */
	public static final String KEY_MAP_STATUT_ACCEPTATION_CSF = "Statut Acceptation CSF";

	/**
	 * Statut dde validation d'une CSF.
	 */
	public static final String KEY_MAP_STATUT_VALIDATION_CSF = "Statut Validation CSF";

    /**
     * Clé pour le créateur.
     */
    public static final String KEY_MAP_CREATEUR = "Createur";

    /**
     * Clé pour le login du créateur.
     */
    public static final String KEY_MAP_LOGIN_CREATEUR = "LoginCreateur";

    /**
     * Clé pour le modificateur.
     */
    public static final String KEY_MAP_MODIFICATEUR = "Modificateur";

    /**
     * Clé pour le login du modificateur.
     */
    public static final String KEY_MAP_LOGIN_MODIFICATEUR = "LoginModificateur";

	/**
	 * Clé pour les colonnes Statut avancement csf.
	 */
	public static final String KEY_MAP_STATUT_AVANCEMENT_CSF = "StatutAvancement CSF";
	
	/**
	 * Clé pour les colonnes numero CA/Poste.
	 */
	public static final String KEY_MAP_NUMERO_CA_POSTE = "N° CA/Poste";

	
	/**
	 * Clé pour les colonnes Compte Actif des utilisateur.
	 */
	public static final String KEY_MAP_COMPTE_ACTIF = "CptActif";
	
	/**
	 * Clé pour les colonnes Avec Compte des utilisateur.
	 */
	public static final String KEY_MAP_AVEC_COMPTE = "AvecCompte";
	
	/**
	 * Clé pour les colonnes Mot de passe géneré des utilisateur.
	 */
	public static final String KEY_MAP_PASSWORD_GENERE = "PasswordGenere";
	/**
	 * Clé pour les colonnes Mot de passe géneré des utilisateur.
	 */
	public static final String KEY_MAP_PASSWORD_ACTIF = "PasswordActif";
}
