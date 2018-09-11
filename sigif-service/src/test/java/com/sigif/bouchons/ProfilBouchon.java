package com.sigif.bouchons;

import java.util.Calendar;

import com.sigif.dto.MinistereDTO;
import com.sigif.dto.ProfilDTO;
import com.sigif.dto.RoleDTO;
import com.sigif.dto.ServiceDepensierDTO;
import com.sigif.dto.UtilisateurDTO;
import com.sigif.enumeration.Civilite;
import com.sigif.enumeration.Statut;

public class ProfilBouchon {

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

	public static RoleDTO getRoleDDA() {
		RoleDTO role = new RoleDTO();
		role.setCode("DA");
		role.setDesignation("Demandeur2");
		return role;
	}

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

	public static ServiceDepensierDTO getServiceDepensierA() {
		ServiceDepensierDTO serviceDepensier = new ServiceDepensierDTO();
		serviceDepensier.setCode("SD1");
		serviceDepensier.setDateCreation(Calendar.getInstance().getTime());
		serviceDepensier.setDateModification(Calendar.getInstance().getTime());
		serviceDepensier.setDesignation("Comptabilite A");
		serviceDepensier.setMinistere(getMinistereA());
		// serviceDepensier.getProfils().add(profil);
		serviceDepensier.setPerimetreFinancier("SDA");
		serviceDepensier.setStatut(Statut.actif);
		return serviceDepensier;
	}

	public static ProfilDTO getProfilA() {
		ProfilDTO profil = new ProfilDTO();
		profil.setUtilisateur(retreiveUserA());
		profil.setRole(getRoleDDA());
		profil.setMinistere(getMinistereA());
		profil.setServiceDepensier(getServiceDepensierA());
		return profil;

	}
}
