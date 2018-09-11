package com.sigif.enumeration;

public enum StatutApprobationDA {
	Refusee("Refusée"), Approuvee("Approuvée"), ApprouveePartiellement("Approuvée partiellement");

	private String displayName;

	StatutApprobationDA(String displayName) {
		this.displayName = displayName;
	}

	public String displayName() {
		return displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}

	public static StatutApprobationDA fromValue(String value) {
		if (value != null && !value.isEmpty()) {
			switch (value) {
			case "Approuvée partiellement":
				return StatutApprobationDA.ApprouveePartiellement;
			case "Approuvée":
				return StatutApprobationDA.Approuvee;
			case "Refusée":
				return StatutApprobationDA.Refusee;
			default:
				return null;
			}
		}
		return null;
	}
}
