package com.sigif.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.CommandeAchatDAO;
import com.sigif.dao.ConstatationServiceFaitDAO;
import com.sigif.dao.ServiceDepensierDAO;
import com.sigif.dto.CommandeAchatDTO;
import com.sigif.enumeration.StatutReceptionCA;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PosteCommandeAchat;
import com.sigif.util.Constantes;
import com.sigif.util.FunctionsUtils;
import com.sigif.util.StringToDate;

/**
 * Implémentation de la classe d'accès aux commandes d'achat
 */
@Service("commandeAchatService")
@Transactional
public class CommandeAchatServiceImpl extends AbstractServiceImpl<CommandeAchat, CommandeAchatDTO>
        implements CommandeAchatService {

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandeAchatServiceImpl.class);

    /**
     * DAO des commandes d'achat.
     */
    @Autowired
    CommandeAchatDAO commandeAchatDAO;

    /**
     * ConstatationServiceFaitDAO.
     */
    @Autowired
    ConstatationServiceFaitDAO csfDAO;
    /**
     * Dao des services dépensiers.
     */
    @Autowired
    ServiceDepensierDAO serviceDepensierDAO;

    @Override
    public int searchCANbResults(String spendingService, String noCA, String orderType, String purchasingCategory,
            String status, String dateFrom, String dateTo) throws TechnicalException, ApplicationException {
        spendingService = FunctionsUtils.checkNotNullNotEmptyAndTrim("spendingService", spendingService, LOGGER);
        noCA = FunctionsUtils.trimOrNullifyString(noCA);
        orderType = FunctionsUtils.trimOrNullifyString(orderType);
        purchasingCategory = FunctionsUtils.trimOrNullifyString(purchasingCategory);
        status = FunctionsUtils.trimOrNullifyString(status);
        StatutReceptionCA statusValide = null;
        if (status != null) {
            statusValide = StatutReceptionCA.fromValue(status);
        }
        dateFrom = FunctionsUtils.trimOrNullifyString(dateFrom);
        dateTo = FunctionsUtils.trimOrNullifyString(dateTo);
        Date dateFromValide = null;
        Date dateToValide = null;
        if (dateFrom != null) {
            dateFromValide = StringToDate.convertStringToDate(dateFrom + " 00:00:00");
        }
        if (dateTo != null) {
            dateToValide = StringToDate.convertStringToDate(dateTo + " 23:59:59");
        }

        int reslt = commandeAchatDAO.searchCANbResults(spendingService, noCA, orderType, purchasingCategory,
                statusValide, dateFromValide, dateToValide);
        return reslt;
    }

    @Override
    public Map<String, Object[]> searchCA(String spendingService, String noCA, String orderType,
            String purchasingCategory, String status, String dateFrom, String dateTo, int nbMaxResults)
            throws TechnicalException, ApplicationException {
    	
        // Validation des paramètres
        spendingService = FunctionsUtils.checkNotNullNotEmptyAndTrim("spendingService", spendingService, LOGGER);
        noCA = FunctionsUtils.trimOrNullifyString(noCA);
        orderType = FunctionsUtils.trimOrNullifyString(orderType);
        purchasingCategory = FunctionsUtils.trimOrNullifyString(purchasingCategory);
        status = FunctionsUtils.trimOrNullifyString(status);
        StatutReceptionCA statusValide = null;
        if (status != null) {
            statusValide = StatutReceptionCA.fromValue(status);
        }
        dateFrom = FunctionsUtils.trimOrNullifyString(dateFrom);
        dateTo = FunctionsUtils.trimOrNullifyString(dateTo);
        Date dateFromValide = null;
        Date dateToValide = null;
        if (dateFrom != null) {
            dateFromValide = StringToDate.convertStringToDate(dateFrom + " 00:00:00");
        }
        if (dateTo != null) {
            dateToValide = StringToDate.convertStringToDate(dateTo + " 23:59:59");
        }

        try {
            // on recupère la liste des commandes d'achats qui répondent au
            // critères de selection
            List<CommandeAchat> results = commandeAchatDAO.searchCA(spendingService, noCA, orderType,
                    purchasingCategory, statusValide, dateFromValide, dateToValide, nbMaxResults);

            Map<String, Object[]> finalMap = null;
            if (results != null && !results.isEmpty()) {
                finalMap = new HashMap<String, Object[]>();
                // on crée les différentes listes requises par la fonction
                // d'extension
                int nbResults = results.size();
                Object[] numeroCaList = new Object[nbResults];
                Object[] idCaList = new Object[nbResults];
                Object[] commTypeList = new Object[nbResults];
                Object[] commentaireList = new Object[nbResults];
                Object[] dateCreationList = new Object[nbResults];
                Object[] catAchatList = new Object[nbResults];
                Object[] nbCsfList = new Object[nbResults];
                Object[] caStatutList = new Object[nbResults];

                int cpt = 0;
                for (CommandeAchat ca : results) {
                    idCaList[cpt] = String.valueOf(ca.getId());
                    numeroCaList[cpt] = String.valueOf(ca.getIdSap());
                    commTypeList[cpt] = ca.getTypeCommande().getDesignation();
                    commentaireList[cpt] = ca.getDescription();
                    catAchatList[cpt] = ca.getCategorieAchat().getDesignation();
                    dateCreationList[cpt] = ca.getDateCreationSap();
                    // TODO pour optimiser la perf: creer methodes getCSFNB()
                    // avec count sql
                    nbCsfList[cpt] = String.valueOf(ca.getConstatationServiceFaits().size());
                    caStatutList[cpt] = ca.getStatut().toString();
                    cpt++;
                }

                // on crée la liste qui contient les précédentes
                finalMap.put(Constantes.KEY_MAP_ID, idCaList);
                finalMap.put(Constantes.KEY_MAP_NUMERO, numeroCaList);
                finalMap.put(Constantes.KEY_MAP_TYPE_COMMANDE, commTypeList);
                finalMap.put(Constantes.KEY_MAP_COMMENTAIRE, commentaireList);
                finalMap.put(Constantes.KEY_MAP_CATEGORIE_ACHAT, catAchatList);
                finalMap.put(Constantes.KEY_MAP_DATE_CREATION, dateCreationList);
                finalMap.put(Constantes.KEY_MAP_NB_CSF, nbCsfList);
                finalMap.put(Constantes.KEY_MAP_STATUT, caStatutList);
            }

            return finalMap;

        } catch (Exception e) {
            String errorMessage = "erreur lors de la recherche de commande d'achat";
            LOGGER.error(errorMessage);
            throw new TechnicalException(errorMessage, e);
        }
    }

    @Override
    public Map<String, String> getCAInformation(String idCA) throws TechnicalException, ApplicationException {
        idCA = FunctionsUtils.checkNotNullNotEmptyAndTrim("idCA", idCA, LOGGER);        
        CommandeAchat cmdAchat = commandeAchatDAO.getCAInformation(idCA);
        Map<String, String> caListMap = new HashMap<>();
        caListMap.put(Constantes.KEY_MAP_TYPE_COMMANDE, cmdAchat.getTypeCommande().getDesignation());
        caListMap.put(Constantes.KEY_MAP_CATEGORIE_ACHAT, cmdAchat.getCategorieAchat().getDesignation());
        caListMap.put(Constantes.KEY_MAP_DATE_CREATION,
                StringToDate.convertDateToString(cmdAchat.getDateCreationSap()));
        caListMap.put(Constantes.KEY_MAP_COMMENTAIRE, cmdAchat.getDescription());
        caListMap.put(Constantes.KEY_MAP_STATUT, cmdAchat.getStatut().toString());
        caListMap.put(Constantes.KEY_MAP_CODE_DEVISE, cmdAchat.getDevise().getCode());
        caListMap.put(Constantes.KEY_MAP_DEVISE, cmdAchat.getDevise().getDesignation());
        return caListMap;
    }

    @Override
    public Map<String, Object[]> getAllCAOfDA(String numDossierDA, String noPosteDA)
            throws ApplicationException, TechnicalException {
        numDossierDA = FunctionsUtils.checkNotNullNotEmptyAndTrim("numDossierDA", numDossierDA, LOGGER);   
        Map<String, Object[]> resultatMap = new HashMap<String, Object[]>();
        List<CommandeAchat> listeCA =
                commandeAchatDAO.getAllCommandeAchatOfDA(numDossierDA, noPosteDA, "commandeAchat.idSap");

        if (!listeCA.isEmpty()) {
            int nbResults = listeCA.size();
            String[] numCommandes = new String[nbResults];
            String[] descriptions = new String[nbResults];
            Date[] datesCommandes = new Date[nbResults];
            String[] statuts = new String[nbResults];

            int cpt = 0;
            for (CommandeAchat commande : listeCA) {
                numCommandes[cpt] = commande.getIdSap();
                descriptions[cpt] = commande.getDescription();
                datesCommandes[cpt] = commande.getDateCreationSap();
                statuts[cpt] = commande.getStatut().toString();
                cpt++;
            }
            resultatMap.put(Constantes.KEY_MAP_NUMERO, numCommandes);
            resultatMap.put(Constantes.KEY_MAP_DESCRIPTION, descriptions);
            resultatMap.put(Constantes.KEY_MAP_DATE_CREATION, datesCommandes);
            resultatMap.put(Constantes.KEY_MAP_STATUT, statuts);
        }
        return resultatMap;
    }

    @Override
    public Map<String, Object[]> getCAInfoOfCSF(String numeroDossierCSF) throws TechnicalException, ApplicationException {
        numeroDossierCSF = FunctionsUtils.checkNotNullNotEmptyAndTrim("numeroDossierCSF", numeroDossierCSF, LOGGER); 
        try {
            Map<String, Object[]> caInfoMap = new HashMap<String, Object[]>();
            CommandeAchat caOfCSF = csfDAO.getCAofCSF(numeroDossierCSF);
            String[] ids = new String[1];
            String[] designations = new String[1];
            String[] categories = new String[1];
            Date[] dates = new Date[1];
            String[] statuts = new String[1];
            String[] descriptions = new String[1];
            String[] devises = new String[1];
            String[] codedevises = new String[1];
            if (caOfCSF != null) {
            	ids[0] = caOfCSF.getIdSap();
                caInfoMap.put(Constantes.KEY_MAP_NUMERO_CA, ids);
                designations[0] = caOfCSF.getTypeCommande().getDesignation();
                caInfoMap.put(Constantes.KEY_MAP_TYPE_COMMANDE, designations);
                categories[0] = caOfCSF.getCategorieAchat().getDesignation();
                caInfoMap.put(Constantes.KEY_MAP_CATEGORIE_ACHAT, categories);
                dates[0] = caOfCSF.getDateCreationSap();
                caInfoMap.put(Constantes.KEY_MAP_DATE_CREATION, dates);
                statuts[0] = caOfCSF.getStatut().toString();
                caInfoMap.put(Constantes.KEY_MAP_STATUT, statuts);
                descriptions[0] = caOfCSF.getDescription();
                caInfoMap.put(Constantes.KEY_MAP_DESCRIPTION, descriptions);
                devises[0] = caOfCSF.getDevise().getCode();
                caInfoMap.put(Constantes.KEY_MAP_CODE_DEVISE, devises);
                codedevises[0] = caOfCSF.getDevise().getDesignation();
                caInfoMap.put(Constantes.KEY_MAP_DEVISE, codedevises);
            }
            return caInfoMap;
        } catch (Exception e) {
            String errorMessage = "erreur lors de la récupération de la CA de la CSF " + numeroDossierCSF;
            LOGGER.error("erreur lors de la récupération de la CA de la CSF " + numeroDossierCSF, e.getMessage());
            throw new TechnicalException(errorMessage, e);
        }
    }

    @Override
    public StatutReceptionCA calculateCaStatus(CommandeAchat ca) throws TechnicalException {
        return commandeAchatDAO.calculateCaStatus(ca);
    }
// Alpha ajout//

	@Override
	public List<CommandeAchat> listNotDeletableCA(int nbJoursDepuisModif, int nbJoursDepuisModifStatutTerminal) {
		// TODO Auto-generated method stub
		return this.commandeAchatDAO.listNotDeletableCA(nbJoursDepuisModif, nbJoursDepuisModifStatutTerminal);
	}

	@Override
	public List<CommandeAchat> listCommandeAchats() {
		// TODO Auto-generated method stub
		return this.listCommandeAchats();
	}

	@Override
	public void addlistNotDeletableCA(List<CommandeAchat> liste) {
		// TODO Auto-generated method stub
		this.commandeAchatDAO.addlistNotDeletableCA(liste);
	}

	@Override
	public List<DemandeAchat> listDAfromCA(List<CommandeAchat> liste) {
		// TODO Auto-generated method stub
		return this.commandeAchatDAO.listDAfromCA(liste);
	}

	@Override
	public List<CommandeAchat> listDeletableCA(List<CommandeAchat> listNotDeletableCA) {
		// TODO Auto-generated method stub
		return this.commandeAchatDAO.listDeletableCA(listNotDeletableCA);
	}

	@Override
	public List<PosteCommandeAchat> getPosteCommandeAchats(CommandeAchat da) {
		// TODO Auto-generated method stub
		return this.commandeAchatDAO.getPosteCommandeAchats(da);
	}

	@Override
	public void delete(List<CommandeAchat> liste) {
		// TODO Auto-generated method stub
		this.commandeAchatDAO.delete(liste);
	}
    
	
}
