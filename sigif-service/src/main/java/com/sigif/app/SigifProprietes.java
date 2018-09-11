package com.sigif.app;

/**
 * Nom des propriétés utilisées dans l'application.
 */
public final class SigifProprietes {

    /**
     * Constructeur privé car classe non instanciable.
     */
    private SigifProprietes() {
    }

    /**
     * Nom JNDI de la datasource.
     */
    public static final String PROP_DB_JNDI = "db.jndi";

    /* Propriétés Hibernate */

    /**
     * Nom de propriété du dialecte Hibernate.
     */
    public static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";

    /**
     * Nom de propriété du show Sql Hibernate.
     */
    public static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    /**
     * Nom de propriété du format Sql Hibernate.
     */
    public static final String PROP_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";

    /**
     * Nom de propriété de hbm2ddl.auto.
     */
    public static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    /**
     * Nom de propriété de session.
     */
//    public static final String PROP_SESSION = "current_session_context_class";

    /* Propriétés SmartGuide */

    /**
     * Chemin des smartlets.
     */
    public static final String PROP_SMARTGUIDE_SMARTLETS = "smartguide.smartlets";

    /**
     * Chemin d'upload des fichiers temporaires.
     */
    public static final String PROP_SMARTGUIDE_UPLOAD = "smartguide.upload";

    /**
     * Chemin de la licence.
     */
    public static final String PROP_SMARTGUIDE_LICENCE = "smartguide.licence";

    /* Propriétés SendMail */
    /**
     * Passerelle SMTP.
     */
    public static final String PROP_PASSERELLE_SMTP = "mail.passerelle.smtp";

    /**
     * Emetteur du mail.
     */
    public static final String PROP_EMETTEUR_MAIL_SIGIF = "mail.emetteur";

    /**
     * L'URL de la plateforme SIGIF.
     */
    public static final String PROP_URL_SIGIF_PLATEFORM = "mail.url";

    /**
     * Chemin du dossier de stockage des PJ.
     */
    public static final String PROP_ATTACHMENTS_FOLDER_PATH = "sigif.attachments.folder";

}
