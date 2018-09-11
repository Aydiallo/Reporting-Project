package com.sigif.service;

import java.util.List;

import com.sigif.modele.CommandeAchat;
import com.sigif.modele.ConstatationServiceFait;
import com.sigif.modele.DemandeAchat;
 
public interface PurgeService {
 
    //public void addPerson(Person p);
	/*
	public List<DemandeAchat> listDemandeAchats() ;
	public  List<CommandeAchat> listCommandeAchats() ;
	public List<ConstatationServiceFait> listConstatationServiceFaits();
	
	public List<DemandeAchat> listNotDeletableDA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal);
	 public List<CommandeAchat> listNotDeletableCA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal) ;
	 public List<ConstatationServiceFait> listNotDeletableCSF(int nbJoursDepuisModif,
				int nbJoursDepuisModifStatutTerminal) ;
	 
	 public List<CommandeAchat> listCAfromCSF() ;
	 
	 
	 public void addListNotDeletableDAorCA(List<CommandeAchat> listeca);
	 public void loadNotDeletableDaCaCsf() ;
	 
	 public List<CommandeAchat> listDeletableCA() ;
	 */
	public  void deleteDaCsfCa();
	public void nettoyer() ;
	//public void delete(List<DemandeAchat> listDeletableDa, List<CommandeAchat> listDeletableCa, List<ConstatationServiceFait>listDeletableCsf) ;
	public List<CommandeAchat> getCaDeletableList();
	public List<DemandeAchat> getDaDeletableList();
	public List<ConstatationServiceFait> getCsfDeletableList();
	//public List<CommandeAchat> listDeletableCA(List<CommandeAchat> listNotDeletableCA) ;
	
	
	public void majDaNDListId();
	public void majCsfNDListId();
	public void majCaNDListId();
	

	
	public void majToutCaNDListId();
	public void majToutDaNDListId();
	public void majToutCsfNDListId();
	
	public boolean toutDa() ;
	public boolean toutCa() ;
	public boolean toutCsf() ;
	/*fonction de purge
	 * se lance automatiquement tous les 6 mois
	 */
	public void purgeSixMonths() ;
}