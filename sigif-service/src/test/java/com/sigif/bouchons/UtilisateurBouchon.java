package com.sigif.bouchons;

import java.util.Calendar;

import com.sigif.dto.UtilisateurDTO;
import com.sigif.enumeration.Civilite;
import com.sigif.enumeration.Statut;
import com.sigif.modele.Utilisateur;

public class UtilisateurBouchon {

	public static Utilisateur getUser1(){
		Utilisateur user1 = new Utilisateur();
		
		user1.setId(11);
		user1.setIdSAP("idSAP");
		user1.setCivilite(Civilite.Monsieur);
		user1.setCourriel("mame-meissa.beye@atos.net");
		user1.setLogin("samei");
		user1.setMotDePasseGenere(false);
		user1.setNom("BEYE");
		user1.setPrenom("Meissa");
		user1.setStatut(Statut.actif);
		user1.setCompteActif(true);
		user1.setAvecCompte(true);
		user1.setDateCreation(Calendar.getInstance().getTime());
		user1.setDateModification(Calendar.getInstance().getTime());
		user1.setPassword("8d860a823d9bc076fcc388c5a1cb7507");//SigifForm5

		return user1;
	}
	
	
	public static UtilisateurDTO getUser2(){
		UtilisateurDTO user2 = new UtilisateurDTO();
		
		user2.setCivilite(Civilite.Monsieur);
		user2.setCourriel("el-hadji-malick.diagne@atos.net");
		user2.setLogin("malickDiagne");
		user2.setMotDePasseGenere(true);
		user2.setNom("diagne");
		user2.setPrenom("Malick");
		user2.setStatut(Statut.actif);
		user2.setCompteActif(true);
		user2.setDateCreation(Calendar.getInstance().getTime());
		user2.setDateModification(Calendar.getInstance().getTime());
		user2.setPassword("56603f997564c6f24e51ecdfaca37329");//a598jqer qui est généré

		return user2;
	}
	
	public static UtilisateurDTO retreiveUserA() {
		UtilisateurDTO user1 = new UtilisateurDTO();

		user1.setCivilite(Civilite.Monsieur);
		user1.setCourriel("mamadou.nidr@atos.net");
		user1.setLogin("mam");
		user1.setMotDePasseGenere(true);
		user1.setNom("Ndir");
		user1.setPrenom("Mamadou");
		user1.setStatut(Statut.actif);
		user1.setCompteActif(true);
		user1.setAvecCompte(true);
		user1.setDateCreation(Calendar.getInstance().getTime());
		user1.setDateModification(Calendar.getInstance().getTime());
		user1.setPassword("56603f997564c6f24e51ecdfaca37329");
		return user1;
	}
}
