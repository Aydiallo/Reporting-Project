package com.sigif.enumeration;

/**
 * Statut de la CA
 * 
 * @author Mickael Beaupoil
 *
 */
public enum StatutReceptionCA {
    NonReceptionnee("Non réceptionnée"), PartiellementReceptionnee("Partiellement réceptionnée"), Receptionnee(
            "Réceptionnée"), NonValidee("Non validée");

    private String displayName;

    StatutReceptionCA(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static StatutReceptionCA fromValue(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
        case "Partiellement réceptionnée":
            return StatutReceptionCA.PartiellementReceptionnee;
        case "Réceptionnée":
            return StatutReceptionCA.Receptionnee;
        case "Non validée":
            return StatutReceptionCA.NonValidee;
        case "Non réceptionnée":
            return StatutReceptionCA.NonReceptionnee;
        default:
            return null;
        }
    }
}
