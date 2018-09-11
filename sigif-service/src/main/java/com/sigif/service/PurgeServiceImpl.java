package com.sigif.service;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.CommandeAchatDAO;
import com.sigif.dao.ConstatationServiceFaitDAO;
import com.sigif.dao.DemandeAchatDAO;
import com.sigif.dao.PieceJointeDAO;
import com.sigif.dao.PosteCommandeAchatDAO;
import com.sigif.dao.PosteConstatationServiceFaitDAO;
import com.sigif.dao.PosteDemandeAchatDAO;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.ConstatationServiceFait;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PieceJointe;
import com.sigif.modele.PosteCommandeAchat;
import com.sigif.modele.PosteConstatationServiceFait;
import com.sigif.modele.PosteDemandeAchat;

//import fr.zebasto.spring.post.initialize.PostInitialize;

//import fr.zebasto.spring.post.initialize.PostInitialize;
@Service("purgeService")
@Transactional
public class PurgeServiceImpl implements PurgeService {
	
	/**
	 * 
	 */
	/**
	 * 
	 */
	private static final int nbJoursDepuisModif =365 ;
	private static final int nbJoursDepuisModifStatutTerminal = 30 ;
	
	/** Service pour enregistrer les PJ. */
   
	/*
	 * PieceJointeService
	 */
	@Autowired
    PieceJointeService pieceJointeService;
    
	/*
	 * PieceJointeService
	 */

	/** Service pour gérer les alertes. */
	@Autowired
	 DataImpactService dataImpactService;   
    
	/*
	 * DemandeAchatService
	 */
	@Autowired
    DemandeAchatService daService;
	
	/*
	 * PosteCommandeAchatService
	 */
	@Autowired
    PosteCommandeAchatService pcaService;
	
	/*
	 * PosteDemandeAchatService
	 */
	@Autowired
    PosteDaService pdaService;
	
	/*
	 * PosteConstatationServiceFaitService
	 */
	@Autowired
	PosteCsfService pcsfService;
	
	/*
	 * CommandeAchatService
	 */
	@Autowired
    CommandeAchatService caService;
	
	/*
	 * ConstatationServiceFaitService
	 */
	@Autowired
    ConstatationServiceFaitService csfService;


	

	private List<CommandeAchat> caDeletableList =new ArrayList<CommandeAchat>();
	private List<DemandeAchat> daDeletableList=new ArrayList<DemandeAchat>();
	private List<ConstatationServiceFait> csfDeletableList=new ArrayList<ConstatationServiceFait>();
	
	private List<Integer> caNDListId =new ArrayList<Integer>();
	private List<Integer> daNDListId=new ArrayList<Integer>();
	private List<Integer> csfNDListId=new ArrayList<Integer>();
	
	public void majCaNDListId(Integer i)
	{
		if(this.caNDListId.contains(i))
		     this.caNDListId.remove(i) ;
		else
			this.caNDListId.add(i) ;
		
		
		for(int k=0;k<this.caDeletableList.size();k++)
		{
			if(this.caDeletableList.get(k).getId()==i)
			{
				this.caDeletableList.get(k).setSelected(!this.caDeletableList.get(k).isSelected());break;
			}
		}
	}
	public void majCaNDListId() {
		//this.deleteDaCsfCa();
	    String initialValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
	    //this.deleteDaCsfCa();
	   
	    if (!initialValue.isEmpty()) {
	      int counter = Integer.valueOf(initialValue);
	      this.majCaNDListId(counter);
	    }
	}
	public void majToutCaNDListId()
	{
		System.out.println("****Avant**********************"+this.caNDListId.size()+"***"+this.caDeletableList.size()+"********************");
		
		if(this.caNDListId.isEmpty())
		{
			//this.caNDListId.clear();
			
			for(int k=0;k<this.caDeletableList.size();k++)
			{
			   this.caNDListId.add(this.caDeletableList.get(k).getId()) ;
			   this.caDeletableList.get(k).setSelected(false);	
				
			}
		}
		else
		{
			this.caNDListId.clear();
			
			for(int k=0;k<this.caDeletableList.size();k++)
			{
			   
			   this.caDeletableList.get(k).setSelected(true);	
				
			}
			
		}
		System.out.println("****Avant**********************"+this.caNDListId.size()+"***"+this.caDeletableList.size()+"********************");
		
	}

	public void majDaNDListId(Integer i)
	{
		if(this.daNDListId.contains(i))
		     this.daNDListId.remove(i) ;
		else
			this.daNDListId.add(i) ;
		
		//this.deleteDaCsfCa();
		for(int k=0;k<this.daDeletableList.size();k++)
		{
			if(this.daDeletableList.get(k).getId()==i)
			{
				this.daDeletableList.get(k).setSelected(!this.daDeletableList.get(k).isSelected());break;
			}
		}
	}
	public void majDaNDListId() {
		//this.deleteDaCsfCa();
	    String initialValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
	    //this.deleteDaCsfCa();
	   
	    if (!initialValue.isEmpty()) {
	      int counter = Integer.valueOf(initialValue);
	      this.majDaNDListId(counter);
	    
	    }
	}
	public void majToutDaNDListId()
	{
		System.out.println("****Avant**********************"+this.daNDListId.size()+"***"+this.daDeletableList.size()+"********************");
		if(this.daNDListId.isEmpty())
		{
			//this.caNDListId.clear();
			System.out.println("*********************************************************************************Empty************");
			for(int k=0;k<this.daDeletableList.size();k++)
			{
			   this.daNDListId.add(this.daDeletableList.get(k).getId()) ;
			   this.daDeletableList.get(k).setSelected(false);	
				
			}
		}
		else
		{
			System.out.println("***********************************************************************************NOT Empty************");
			this.daNDListId.clear();
			
			for(int k=0;k<this.daDeletableList.size();k++)
			{
			   
			   this.daDeletableList.get(k).setSelected(true);	
				
			}
			
		}
		System.out.println("****Apres**********************"+this.daNDListId.size()+"***"+this.daDeletableList.size()+"********************");
		
	}
	
	
	public void majCsfNDListId(Integer i)
	{
		if(this.csfNDListId.contains(i))
		     this.csfNDListId.remove(i) ;
		else
			this.csfNDListId.add(i) ;
		
		for(int k=0;k<this.csfDeletableList.size();k++)
		{
			if(this.csfDeletableList.get(k).getId()==i)
			{
				this.csfDeletableList.get(k).setSelected(!this.csfDeletableList.get(k).isSelected());break;
			}
		}
	}
	public void majCsfNDListId() {
		//this.deleteDaCsfCa();
	    String initialValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
	    //this.deleteDaCsfCa();
	   
	    if (!initialValue.isEmpty()) {
	      int counter = Integer.valueOf(initialValue);
	      this.majCsfNDListId(counter);
	    }
	}
	public void majToutCsfNDListId()
	{
		if(this.csfNDListId.isEmpty())
		{
			//this.caNDListId.clear();
			
			for(int k=0;k<this.csfDeletableList.size();k++)
			{
			   this.csfNDListId.add(this.csfDeletableList.get(k).getId()) ;
			   this.csfDeletableList.get(k).setSelected(false);	
				
			}
		}
		else
		{
			this.csfNDListId.clear();
			
			for(int k=0;k<this.csfDeletableList.size();k++)
			{
			   
			   this.csfDeletableList.get(k).setSelected(true);	
				
			}
			
		}
	}

	

	//@PostInitialize 
	//@Transactional
   /* public void init(){
		this.deleteDaCsfCa();
	}
*/
	public List<CommandeAchat> getCaDeletableList() {
		return caDeletableList;
	}

	public List<DemandeAchat> getDaDeletableList() {
		return daDeletableList;
	}

	public List<ConstatationServiceFait> getCsfDeletableList() {
		return csfDeletableList;
	}

	
    
    /**
     * Liste des CA non supprimables car leur date de dernière modification n'est pas assez ancienne.
     * 
     * @param nbJoursDepuisModif
     *            Nombres de jours depuis la dernière modification
     * @param nbJoursDepuisModifStatutTerminal
     *            Nombres de jours depuis la dernière modification pour un élément en statut terminal
     * @return Liste des CA non supprimables
     */
    private List<CommandeAchat> listNotDeletableCA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal) {
        return caService.listNotDeletableCA(nbJoursDepuisModif, nbJoursDepuisModifStatutTerminal);
    }

    /**
     * Liste des DA non supprimables car leur date de dernière modification n'est pas assez ancienne.
     * 
     * @param nbJoursDepuisModif
     *            Nombres de jours depuis la dernière modification
     * @param nbJoursDepuisModifStatutTerminal
     *            Nombres de jours depuis la dernière modification pour un élément en statut terminal
     * 
     * @return Liste des DA non supprimables
     */
    //@Transactional
    private List<DemandeAchat> listNotDeletableDA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal) {
        return daService.listNotDeletableDA(nbJoursDepuisModif, nbJoursDepuisModifStatutTerminal);
    }

    /**
     * Liste des CSF non supprimables car leur date de dernière modification n'est pas assez ancienne.
     * 
     * @param nbJoursDepuisModif
     *            Nombres de jours depuis la dernière modification
     * @param nbJoursDepuisModifStatutTerminal
     *            Nombres de jours depuis la dernière modification pour un élément en statut terminal
     * 
     * @return Liste des CSF non supprimables
     */
    //@Transactional
    private List<ConstatationServiceFait> listNotDeletableCSF(int nbJoursDepuisModif,
            int nbJoursDepuisModifStatutTerminal) {
        return csfService.listNotDeletableCSF(nbJoursDepuisModif, nbJoursDepuisModifStatutTerminal);
    }

    /**
     * Ajoute à la liste des CA non supprimables, celles liées à une DA non supprimable.
     * 
     * @param da
     *            DA non supprimable
     * @param listNotDeletableCa
     *            liste des CA non supprimables
     * @param listNotDeletableDa
     *            liste des DA non supprimables
     */
    //@Transactional
    private void addNotDeletableCAFromDA(DemandeAchat da, List<CommandeAchat> listNotDeletableCa,
            List<DemandeAchat> listNotDeletableDa) {
        for (PosteCommandeAchat pca : daService.getPosteCommandeAchats(da)) {
            CommandeAchat ca = pca.getCommandeAchat();
            if (ca!= null && !listNotDeletableCa.contains(ca)) {
                listNotDeletableCa.add(ca);
                this.addNotDeletableDAFromCA(ca, listNotDeletableDa, listNotDeletableCa);
            }
        }
    }
    
    /**
     * Ajoute à la liste des DA non supprimables, celles liées à une CA non supprimable.
     * 
     * @param ca
     *            CA non supprimable
     * @param listNotDeletableDa
     *            liste des DA non supprimables
     * @param listNotDeletableCa
     *            liste des CA non supprimables
     */
    //@Transactional
    private void addNotDeletableDAFromCA(CommandeAchat ca, List<DemandeAchat> listNotDeletableDa,
            List<CommandeAchat> listNotDeletableCa) {
        for (PosteCommandeAchat pca : this.caService.getPosteCommandeAchats(ca)) {
            DemandeAchat da = pca.getDemandeAchat();
            if (da!=null && !listNotDeletableDa.contains(da)) {
                listNotDeletableDa.add(da);
                this.addNotDeletableCAFromDA(da, listNotDeletableCa, listNotDeletableDa);
            }
        }
    }

    /**
     * Ajoute à la liste des CA non supprimables, celle liée à une CSF non supprimable.
     * 
     * @param csf
     *            CSF non supprimable
     * @param listNotDeletableCa
     *            liste des CA non supprimables
     */
    //@Transactional
    private void addNotDeletableCAFromCSF(ConstatationServiceFait csf, List<CommandeAchat> listNotDeletableCa) {
        CommandeAchat ca = csf.getCommandeAchat();
        if (ca!=null && !listNotDeletableCa.contains(ca)) {
            listNotDeletableCa.add(ca);
        }
    }

    

    /**
     * Liste les CA supprimables à partir de la liste des non supprimables.
     * 
     * @param listNotDeletableCA
     *            liste des CA non supprimables
     * @return liste des id des CA supprimables
     */
    //@Transactional
    public List<CommandeAchat> listDeletableCA(List<CommandeAchat> listNotDeletableCA) {
        return this.caService.listDeletableCA(listNotDeletableCA) ;
    }

   // @Transactional
    public List<DemandeAchat> listDeletableDa(List<DemandeAchat> listNotDeletableDA) {
        return this.daService.listDeletableDA(listNotDeletableDA) ;
    }
   // @Transactional
    public List<ConstatationServiceFait> listDeletableCsf(List<ConstatationServiceFait> listNotDeletableCSF) {
        return this.csfService.listDeletableCSF(listNotDeletableCSF);
    }
     
    @Override
    //@Transactional
    //@PostInitialize
    public void deleteDaCsfCa() {
        List<CommandeAchat> caNotDeletableList =
                this.listNotDeletableCA(nbJoursDepuisModif, nbJoursDepuisModifStatutTerminal);
        List<ConstatationServiceFait> csfNotDeletableList =
                this.listNotDeletableCSF(nbJoursDepuisModif, nbJoursDepuisModifStatutTerminal);
        List<DemandeAchat> daNotDeletableList =
                this.listNotDeletableDA(nbJoursDepuisModif, nbJoursDepuisModifStatutTerminal);
        
   
        
        for (ConstatationServiceFait csf : csfNotDeletableList) {
            this.addNotDeletableCAFromCSF(csf, caNotDeletableList);
        }
        
       
        for (CommandeAchat ca : caNotDeletableList) {
            this.addNotDeletableDAFromCA(ca, daNotDeletableList, caNotDeletableList);
        }
        
        
/*
        for (DemandeAchat da : daNotDeletableList) {
            this.addNotDeletableCAFromDA(da, caNotDeletableList, daNotDeletableList);
        }
*/
        
        this.caDeletableList =this.listDeletableCA(caNotDeletableList);
        this.daDeletableList = this.listDeletableDa(daNotDeletableList);
         this.csfDeletableList =  this.listDeletableCsf(csfNotDeletableList);
         
         // initialisation
         /*for(CommandeAchat ca:this.caDeletableList)
				this.caNDListId.add(ca.getId());
         for(ConstatationServiceFait csf:this.csfDeletableList)
				this.csfNDListId.add(csf.getId());
         for(DemandeAchat da:this.daDeletableList)
				this.daNDListId.add(da.getId());*/
        // TODO delete CA
        
        // TODO delete DA not linked to CA
       // return daNotDeletableList ;
    }

	@Override
	public boolean toutDa() {
		// TODO Auto-generated method stub
		for(DemandeAchat item:this.daDeletableList)
		{
			if(!item.isSelected())
				return false;
		}
		return true;
	}
	@Override
	public boolean toutCa() {
		// TODO Auto-generated method stub
		for(CommandeAchat item:this.caDeletableList)
		{
			if(!item.isSelected())
				return false;
		}
		return true;
	}
	@Override
	public boolean toutCsf() {
		// TODO Auto-generated method stub
		for(ConstatationServiceFait item:this.csfDeletableList)
		{
			if(!item.isSelected())
				return false;
		}
		return true;
	}

	public void delete(List<DemandeAchat> listDeletableDa, List<CommandeAchat> listDeletableCa,
			List<ConstatationServiceFait> listDeletableCsf) {
		try {
			System.out.println("*******0.1)listDeletableDa.size() = "
					+ listDeletableDa.size()+"**********");
			System.out.println("*******0.1)listDeletableCa.size() = "
					+ listDeletableCa.size()+"**********");
			System.out.println("*******0.1)listDeletableCsf.size() = "
					+ listDeletableCsf.size()+"**********");
			System.out.println("*******1)listDeletableCsf.size() = "
					+ listDeletableCsf.size()+"**********");
			this.deletePosteCsfPjFromCa(listDeletableCsf);
			System.out.println("*******2)listDeletableCa.size() = "
					+ listDeletableCa.size()+"**********");
			this.deletePosteCaPjFromCa(listDeletableCa);
			System.out.println("*******3)listDeletableDa.size() = "
					+ listDeletableDa.size()+"**********");
			// suppression Poste DA et DA ainsi que leur piece jointe
			if(listDeletableDa!=null && !listDeletableDa.isEmpty())
			{
				Iterator<DemandeAchat> iterator = listDeletableDa.iterator();
				while ( iterator.hasNext() ) {
					DemandeAchat da = iterator.next();
				    this.pdaService.deletePostesDaByDa(da);
				}
				this.deleteDaPjFromCa(listDeletableDa);
				System.out.println("*******3)this.deleteDaPjFromCa(listDA);**********");

			}
			System.out.println("*******3)listDeletableDa.size() = "
					+ listDeletableDa.size()+"**********");
			
		} 
		catch (TechnicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	private void deleteDaPjFromCa(List<DemandeAchat> listDeletableDa) throws TechnicalException
	{
		//suppression piece jointe et da 
		Iterator<DemandeAchat> iterator = listDeletableDa.iterator();
		while ( iterator.hasNext() ) {
			DemandeAchat da = iterator.next();
		    this.pieceJointeService.deletePjDA(da);
		   
		}
		this.daService.delete(listDeletableDa);
	}
	
	private void deletePosteCsfPjFromCa(List<ConstatationServiceFait> listDeletableCsf) throws TechnicalException
	{
		if(listDeletableCsf.isEmpty()) return ;
		//List<ConstatationServiceFait> listeCSF = this.csfDAO.CSFLieesCA(listDeletableCa) ;
		System.out.println("*******deletePosteCsfPjFromCa(List<ConstatationServiceFait> listDeletableCsf)**********");

		Iterator<ConstatationServiceFait> iterator = listDeletableCsf.iterator();
		while ( iterator.hasNext() ) {
			ConstatationServiceFait csf = iterator.next();
			this.pcsfService.deleteByCsf(csf, true);
		}
		System.out.println("*******deleteCsfPjFromCa(listDeletableCsf)**********");
		deleteCsfPjFromCa(listDeletableCsf) ;
		System.out.println("*******Sortie**********");
	}
	private void deleteCsfPjFromCa(List<ConstatationServiceFait> listDeletableCsf) throws TechnicalException
	{
		//suppression piece jointe et csf
		if(listDeletableCsf.isEmpty()) return ;
		
			Iterator<ConstatationServiceFait> iterator = listDeletableCsf.iterator();
			while ( iterator.hasNext() ) {
				ConstatationServiceFait csf = iterator.next();
			    this.pieceJointeService.deletePjCsf(csf);
			    
			}
			csfService.delete(listDeletableCsf);
	}
	private void deletePosteCaPjFromCa(List<CommandeAchat> listDeletableCa) throws TechnicalException
	{
		Iterator<CommandeAchat> iterator = listDeletableCa.iterator();
		while ( iterator.hasNext() ) {
			CommandeAchat ca = iterator.next();
			this.pcaService.delete(ca.getPosteCommandeAchats());
		}
		
		this.caService.delete(listDeletableCa) ;
	}

	@Override
	public void nettoyer() {
		//da
		List<DemandeAchat> daDeletableListP = daDeletableList;
		System.out.println("*******Avant DA**********");
		Iterator<DemandeAchat> iterator = daDeletableListP.iterator();
		while ( iterator.hasNext() ) {
			DemandeAchat o = iterator.next();
		    if (!o.isSelected()) {
		    	System.out.println("******* "+o.getNumeroDossier()+" **********");
		        // On supprime l'élément courant de la liste
		        iterator.remove();
		    }
		}
	
			//ca
		System.out.println("*******Avant CA**********");
		List<CommandeAchat> caDeletableListP = caDeletableList;
		Iterator<CommandeAchat> iterator1 = caDeletableListP.iterator();
		while ( iterator1.hasNext() ) {
			CommandeAchat o = iterator1.next();
		    if (!o.isSelected()) {
		    	//System.out.println("******* "+o.getNumeroDossier()+" **********");
		        // On supprime l'élément courant de la liste
		        iterator1.remove();
		    }
		}
			
			//csf
		System.out.println("*******Avant CSF**********");
		List<ConstatationServiceFait> csfDeletableListP = csfDeletableList;
		Iterator<ConstatationServiceFait> iterator2 = csfDeletableListP.iterator();
		while ( iterator2.hasNext() ) {
			ConstatationServiceFait o = iterator2.next();
		    if (!o.isSelected()) {
		    	//System.out.println("******* "+o.getNumeroDossier()+" **********");
		        // On supprime l'élément courant de la liste
		        iterator2.remove();
		    }
		}
			//suppression
		System.out.println("*******Avant supp**********");
		delete(daDeletableListP, caDeletableListP,csfDeletableListP) ;
		System.out.println("*******apres supp**********");
		
		//da
				System.out.println("*******Avant DA**********");
				iterator = daDeletableListP.iterator();
				while ( iterator.hasNext() ) {
					DemandeAchat o = iterator.next();
				    if (o.isSelected()) {
				    	System.out.println("******* "+o.getNumeroDossier()+" **********");
				        // On supprime l'élément courant de la liste
				        iterator.remove();
				    }
				}
			
					//ca
				System.out.println("*******Avant CA**********");
				iterator1 = caDeletableList.iterator();
				while ( iterator1.hasNext() ) {
					CommandeAchat o = iterator1.next();
				    if (o.isSelected()) {
				    	//System.out.println("******* "+o.getNumeroDossier()+" **********");
				        // On supprime l'élément courant de la liste
				        iterator1.remove();
				    }
				}
					
					//csf
				System.out.println("*******Avant CSF**********");
			    iterator2 = csfDeletableList.iterator();
				while ( iterator2.hasNext() ) {
					ConstatationServiceFait o = iterator2.next();
				    if (o.isSelected()) {
				    	//System.out.println("******* "+o.getNumeroDossier()+" **********");
				        // On supprime l'élément courant de la liste
				        iterator2.remove();
				    }
				}
		
	}
	/* fonction de purge automatique*/
	@Override
	public void purgeSixMonths() {
		this.deleteDaCsfCa();
		this.nettoyer();
	}
		
}

