package com.sigif.util;

/**
 * Liste des codes d'erreur.
 *
 */
public final class ListErreurs {

    /**
     * Constructeur privé vide car la classe est utilitaire.
     */
    private ListErreurs() {
    }

    /**
     * Code d'erreur d'accès à la base de données.
     */
    public static final String ERREUR_ACCES_DATABASE = "erreur.acces.baseDonnee.impossible";

    /**
     * Code d'erreur de communication avec la base de données.
     */
    public static final String ERREUR_DATABASE = "erreur.baseDonnee";

    /**
     * Code d'erreur d'ajout d'entité.
     */
    public static final String ERREUR_ADD_ENTITY = "erreur.add.entity";

    /**
     * Code d'erreur de mise à jour d'entité.
     */
    public static final String ERREUR_UPDATE_ENTITY = "erreur.update.entity";

    /**
     * Code d'erreur de suppression d'entité.
     */
    public static final String ERREUR_DELETE_ENTITY = "erreur.delete.entity";

    /**
     * Code d'erreur lors de la récupération d'une valeur.
     */
    public static final String ERREUR_GET_VALUES = "erreur.get.values";

    /**
     * Code d'erreur lors du passage d'un parametre null.
     */
    public static final String ERREUR_PARAM_NULL = "erreur.parametre.null";

}
