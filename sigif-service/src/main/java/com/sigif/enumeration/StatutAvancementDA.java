package com.sigif.enumeration;

public enum StatutAvancementDA {
	Brouillon("Brouillon"), EnAttenteEnvoi("En attente d'envoi"), EnvoyeeOrdonnateur(
			"Envoyée à l'ordonnateur"), EnAttenteDeTraitement("En attente de traitement"), TraitementEnCours(
					"Traitement en cours"), TraitementTermine("Traitement terminé");

	private String displayName;

	StatutAvancementDA(String displayName) {
		this.displayName = displayName;
	}

	public String displayName() {
		return displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}

	public static StatutAvancementDA fromValue(String value) {
		if (value != null && !value.isEmpty()) {
			switch (value) {
			case "Brouillon":
				return StatutAvancementDA.Brouillon;
			case "En attente d'envoi":
				return StatutAvancementDA.EnAttenteEnvoi;
			case "Envoyée à l'ordonnateur":
				return StatutAvancementDA.EnvoyeeOrdonnateur;
			case "En attente de traitement":
				return StatutAvancementDA.EnAttenteDeTraitement;
			case "Traitement en cours":
				return StatutAvancementDA.TraitementEnCours;
			case "Traitement terminé":
				return StatutAvancementDA.TraitementTermine;
			default:
				return null;
			}
		}
		return null;
	}

}
