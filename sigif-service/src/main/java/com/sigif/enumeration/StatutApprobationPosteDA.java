package com.sigif.enumeration;

public enum StatutApprobationPosteDA {
    Refusee("Refusée"), Approuvee("Approuvée"), EnAttenteApprobation("En attente d'approbation");

    private String displayName;

    StatutApprobationPosteDA(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static StatutApprobationPosteDA fromValue(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
        case "En attente d'approbation":
            return StatutApprobationPosteDA.EnAttenteApprobation;
        case "Approuvée":
            return StatutApprobationPosteDA.Approuvee;
        case "Refusée":
            return StatutApprobationPosteDA.Refusee;
        default:
            return null;
        }
    }

}
