package com.sigif.bouchons;

import java.util.Date;

import com.sigif.enumeration.Statut;
import com.sigif.modele.CategorieAchat;

public class CategorieAchatBouchon {

	public static CategorieAchat getCategorieAchat1(){
		CategorieAchat catAchat1 = new CategorieAchat();
		catAchat1.setId(1);
		catAchat1.setCode("T");
		catAchat1.setDateCreation(new Date());
		catAchat1.setDateModification(new Date());
		catAchat1.setDesignation("Travaux");
		catAchat1.setStatut(Statut.actif);
		return catAchat1;
	}

}
