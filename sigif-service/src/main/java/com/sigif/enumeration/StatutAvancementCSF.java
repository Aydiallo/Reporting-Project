package com.sigif.enumeration;

/**
 * Statut d'avancement d'une CSF.
 * @author Mickael Beaupoil
 *
 */
public enum StatutAvancementCSF {
    Brouillon("Brouillon"), 
    EnAttenteEnvoi("En attente d'envoi"), 
    EnvoyeeOrdonnateur("Envoyée à l'ordonnateur"),
    EnAttenteDeTraitement("En attente de traitement"),
    TraitementEnCours("Traitement en cours"),
    TraitementTermine("Traitement terminé");

    private String displayName;

    StatutAvancementCSF(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static StatutAvancementCSF fromValue(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
        case "Brouillon":
            return StatutAvancementCSF.Brouillon;
        case "En attente d'envoi":
            return StatutAvancementCSF.EnAttenteEnvoi;
        case "Envoyée à l'ordonnateur":
            return StatutAvancementCSF.EnvoyeeOrdonnateur;
        case "En attente de traitement":
            return StatutAvancementCSF.EnAttenteDeTraitement;
        case "Traitement en cours":
            return StatutAvancementCSF.TraitementEnCours;
        case "Traitement terminé":
            return StatutAvancementCSF.TraitementTermine;
        default:
            return null;
        }
    }
}
