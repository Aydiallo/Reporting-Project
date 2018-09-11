package com.sigif.enumeration;

/**
 * Statut de certification de la CSF.
 * @author Mickael Beaupoil
 *
 */
public enum StatutCertificationCSF {
    ConstatationAnnulee("Constatation annulée"), 
    ValidationEnCours("Validation en cours"), 
    ValidationRefusee("Validation refusée"),
    EnAttenteCertification("En attente de certification"),
    CertificationEnCours("Certification en cours"),
    Certifiee("Certifiée"),
    CertificationRefusee("Certification refusée"),
    CertificationPartielle("Certification partielle");

    private String displayName;

    StatutCertificationCSF(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static StatutCertificationCSF fromValue(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
        case "Constatation annulée":
            return StatutCertificationCSF.ConstatationAnnulee;
        case "Validation en cours":
            return StatutCertificationCSF.ValidationEnCours;
        case "Validation refusée":
            return StatutCertificationCSF.ValidationRefusee;
        case "En attente de certification":
            return StatutCertificationCSF.EnAttenteCertification;
        case "Certification en cours":
            return StatutCertificationCSF.CertificationEnCours;
        case "Certifiée":
            return StatutCertificationCSF.Certifiee;
        case "Certification refusée":
            return StatutCertificationCSF.CertificationRefusee;
        case "Certification partielle":
            return StatutCertificationCSF.CertificationPartielle;
        default:
            return null;
        }
    }
}
