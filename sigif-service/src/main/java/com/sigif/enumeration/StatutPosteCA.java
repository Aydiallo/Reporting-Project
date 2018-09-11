package com.sigif.enumeration;

/**
 * Statut d'un poste de la CA
 * @author Mickael Beaupoil
 *
 */
public enum StatutPosteCA {
    NonReceptionne("Non réceptionné"), 
    PartiellementReceptionne("Partiellement réceptionné"), 
    Cloture("Clôturé");

    private String displayName;

    StatutPosteCA(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static StatutPosteCA fromValue(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
        case "Non réceptionné":
            return StatutPosteCA.NonReceptionne;
        case "Partiellement réceptionné":
            return StatutPosteCA.PartiellementReceptionne;
        case "Clôturé":
            return StatutPosteCA.Cloture;
        default:
            return null;
        }
    }
}
