package com.sigif.bouchons;

import java.util.Calendar;

import com.sigif.dto.MinistereDTO;
import com.sigif.enumeration.Statut;
import com.sigif.modele.Ministere;

public class MinistereBouchon {

	public static MinistereDTO getMinistereA() {
		MinistereDTO ministere = new MinistereDTO();
		ministere.setCode("Min1");
		ministere.setDateCreation(Calendar.getInstance().getTime());
		ministere.setDateModification(Calendar.getInstance().getTime());
		ministere.setDesignation("Minitére de l'education nationale");
		ministere.setPerimetreFinancier("MF");
		ministere.setStatut(Statut.actif);
		return ministere;
	}
	
	
	public static Ministere getMinistereMMA1() {
		Ministere ministere = new Ministere();
		ministere.setId(3);
		ministere.setCode("31");
		ministere.setDateCreation(Calendar.getInstance().getTime());
		ministere.setDateModification(Calendar.getInstance().getTime());
		ministere.setDesignation("Ministere des affaires");
		ministere.setPerimetreFinancier("SNBG");
		ministere.setStatut(Statut.actif);
		return ministere;
	}

}
