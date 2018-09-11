package com.sigif.enumeration;

/**
 * Statut de certification d'un poste de la CSF.
 * @author Mickael Beaupoil
 *
 */
public enum StatutCertificationPosteCSF {
    ConstatationAnnulee("Constatation annulée"), 
    EnAttenteValidation("En attente de validation"), 
    ValidationRefusee("Validation refusée"),
    EnAttenteCertification("En attente de certification"),
    Certifie("Certifié"),
    CertificationRefusee("Certification refusée"),
    Rectifie("Rectifié");

    private String displayName;

    StatutCertificationPosteCSF(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static StatutCertificationPosteCSF fromValue(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
        case "Constatation annulée":
            return StatutCertificationPosteCSF.ConstatationAnnulee;
        case "En attente de validation":
            return StatutCertificationPosteCSF.EnAttenteValidation;
        case "Validation refusée":
            return StatutCertificationPosteCSF.ValidationRefusee;
        case "En attente de certification":
            return StatutCertificationPosteCSF.EnAttenteCertification;
        case "Certifié":
            return StatutCertificationPosteCSF.Certifie;
        case "Certification refusée":
            return StatutCertificationPosteCSF.CertificationRefusee;
        case "Rectifié":
            return StatutCertificationPosteCSF.Rectifie;
        default:
            return null;
        }
    }
}
