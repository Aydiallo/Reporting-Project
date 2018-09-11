package com.sigif.bouchons;

import java.util.Calendar;

import com.sigif.dto.ServiceDepensierDTO;
import com.sigif.enumeration.Statut;
import com.sigif.modele.ServiceDepensier;

public class ServiceDepnsierBouchon {

	public static ServiceDepensierDTO getServiceDepensierA() {
		ServiceDepensierDTO serviceDepensier = new ServiceDepensierDTO();
		serviceDepensier.setCode("SD1");
		serviceDepensier.setDateCreation(Calendar.getInstance().getTime());
		serviceDepensier.setDateModification(Calendar.getInstance().getTime());
		serviceDepensier.setDesignation("Comptabilite A");
		serviceDepensier.setMinistere(MinistereBouchon.getMinistereA());
		// serviceDepensier.getProfils().add(profil);
		serviceDepensier.setPerimetreFinancier("SDA");
		serviceDepensier.setStatut(Statut.actif);
		return serviceDepensier;
	}
	
	public static ServiceDepensier getServiceDepensierMMA1() {
		ServiceDepensier serviceDepensier = new ServiceDepensier();
		serviceDepensier.setId(1);
		serviceDepensier.setCode("11701440110");
		serviceDepensier.setDateCreation(Calendar.getInstance().getTime());
		serviceDepensier.setDateModification(Calendar.getInstance().getTime());
		serviceDepensier.setDesignation("Service sociale mae");
		serviceDepensier.setMinistere(MinistereBouchon.getMinistereMMA1());
		serviceDepensier.setPerimetreFinancier("SNBG");
		serviceDepensier.setStatut(Statut.actif);
		return serviceDepensier;
	}

}
