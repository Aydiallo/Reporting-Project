package com.sigif.bouchons;

import java.util.Date;

import com.sigif.enumeration.Statut;
import com.sigif.modele.Programme;

public class ProgrammeBouchon {

	public static Programme getProgramme1(){
		Programme programme1 = new Programme();
		programme1.setId(1);
		programme1.setCode("1001");
		programme1.setDateCreation(new Date());
		programme1.setDateModification(new Date());
		programme1.setDescription("Pilotage, gestion et coordination administrative");
		programme1.setStatut(Statut.actif);
		programme1.setMinistere(MinistereBouchon.getMinistereMMA1());
		programme1.setPerimetreFinancier("SNBG");
		return programme1;
	}

}
