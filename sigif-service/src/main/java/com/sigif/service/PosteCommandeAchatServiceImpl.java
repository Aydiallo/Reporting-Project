package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.PosteCommandeAchatDAO;
import com.sigif.dao.PosteConstatationServiceFaitDAO;
import com.sigif.dto.PosteCommandeAchatDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CommandeAchat;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PosteCommandeAchat;
import com.sigif.modele.Unite;
import com.sigif.util.Constantes;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation du service d'accès aux postes des commandes d'achat.
 *
 */
@Service("posteCommandeAchatService")
@Transactional
public class PosteCommandeAchatServiceImpl extends AbstractServiceImpl<PosteCommandeAchat, PosteCommandeAchatDTO>
        implements PosteCommandeAchatService {
    /**
     * Loggueur.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PosteCommandeAchatServiceImpl.class);

    @Override
    protected PosteCommandeAchatDAO getDao() {
        return (PosteCommandeAchatDAO) super.getDao();
    }

    /**
     * DAO pour les postes de CSF.
     */
    @Autowired
    private PosteConstatationServiceFaitDAO posteCsfDao;
    
    /**
     * DAO pour les postes de CA.
     */
    @Autowired
    private PosteCommandeAchatDAO posteCaDao;

    /**
     * Service pour les catégories des postes.
     */
    @Autowired
    private CategoriePosteService categoriePosteService;

    @Override
    public Map<String, String> getPosteCAInformation(String idCA, String noPoste)
            throws ApplicationException, TechnicalException {
        Map<String, String> resultMap = null;

        idCA = FunctionsUtils.checkNotNullNotEmptyAndTrim("idCA", idCA, LOGGER);
        noPoste = FunctionsUtils.checkNotNullNotEmptyAndTrim("noPoste", noPoste, LOGGER);

        // récupération du poste par le DAO et transfo en DTO
        // et pas de transfo en DTO car pas de modif et pour éviter de multiples
        // appels SQL par hibernate
        PosteCommandeAchat posteCommandeAchat;
        try {
            posteCommandeAchat = this.getDao().getPosteCaInformation(idCA, noPoste);
        } catch (NonUniqueResultException e) {
            String msgPlusieursPostes =
                    String.format("Récupération des infos d'un poste : plusieurs postes correspondent "
                            + "aux paramètres : id de CA = '%s' et numéro de poste = '%s'.", idCA, noPoste);
            LOGGER.error(msgPlusieursPostes);
            throw new ApplicationException(msgPlusieursPostes, e);
        }

        if (posteCommandeAchat != null) {
            int nbPostesCsfLies = this.posteCsfDao.getNbPostesCsfLinkedToPosteCa(posteCommandeAchat.getId());
            String nomUnite = "";
            String qteCommandee = String.valueOf(posteCommandeAchat.getQuantiteCommandee());
            String qteRestante = String.valueOf(posteCommandeAchat.getQuantiteRestante());
            String qteAcceptee = String
                    .valueOf(posteCommandeAchat.getQuantiteCommandee() - posteCommandeAchat.getQuantiteRestante());
            if (posteCommandeAchat.getUnite() != null) {
                nomUnite = posteCommandeAchat.getUnite().getNom();
            }

            resultMap = new HashMap<String, String>();
            resultMap.put(Constantes.KEY_MAP_ID, String.valueOf(posteCommandeAchat.getId()));
            resultMap.put(Constantes.KEY_MAP_STATUT,
                    posteCommandeAchat.getStatut() != null ? posteCommandeAchat.getStatut().displayName() : null);
            resultMap.put(Constantes.KEY_MAP_COMMENTAIRE, posteCommandeAchat.getCommentaire());
            resultMap.put(Constantes.KEY_MAP_TYPE_ACHAT, posteCommandeAchat.getTypeAchat() != null
                    ? posteCommandeAchat.getTypeAchat().getDesignation() : null);
            resultMap.put(Constantes.KEY_MAP_CODE_TYPE_ACHAT, posteCommandeAchat.getTypeAchat() != null
                    ? posteCommandeAchat.getTypeAchat().getCode() : null);
            resultMap.put(Constantes.KEY_MAP_CATEGORIE, this.categoriePosteService.getCategorieByTypeAndReference(
                    posteCommandeAchat.getTypeAchat().getCode(), posteCommandeAchat.getReference()));
            resultMap.put(Constantes.KEY_MAP_REFERENCE, posteCommandeAchat.getReference());
            resultMap.put(Constantes.KEY_MAP_DESIGNATION, posteCommandeAchat.getDesignation());
            resultMap.put(Constantes.KEY_MAP_QTE_COMMANDEE, qteCommandee);
            resultMap.put(Constantes.KEY_MAP_QTE_ACCEPTEE, qteAcceptee);
            resultMap.put(Constantes.KEY_MAP_QTE_RESTANTE, qteRestante);
            resultMap.put(Constantes.KEY_MAP_UNITE, nomUnite);
            resultMap.put(Constantes.KEY_MAP_PRIX, String.valueOf(posteCommandeAchat.getPrixUnitaire()));
            resultMap.put(Constantes.KEY_MAP_DEVISE,
                    posteCommandeAchat.getCommandeAchat().getDevise().getDesignation());
            resultMap.put(Constantes.KEY_MAP_SERVICE_DEPENSIER,
                    posteCommandeAchat.getServiceDepensierByIdServiceDepensier() != null
                            ? posteCommandeAchat.getServiceDepensierByIdServiceDepensier().getDesignation() : null);
            resultMap.put(Constantes.KEY_MAP_SERVICE_BENEFICIAIRE,
                    posteCommandeAchat.getServiceDepensierByIdServiceBeneficiaire() != null
                            ? posteCommandeAchat.getServiceDepensierByIdServiceBeneficiaire().getDesignation() : null);
            resultMap.put(Constantes.KEY_MAP_NB_POSTES_CSF, String.valueOf(nbPostesCsfLies));
            resultMap.put(Constantes.KEY_MAP_LIEU_STOCKAGE, posteCommandeAchat.getLieuStockage() != null
                    ? posteCommandeAchat.getLieuStockage().getDesignation() : null);
            resultMap.put(Constantes.KEY_MAP_CODE_LIEU_STOCKAGE, posteCommandeAchat.getLieuStockage() != null
                    ? String.valueOf(posteCommandeAchat.getLieuStockage().getId()) : null);
        }

        return resultMap;
    }

    @Override
    public Map<String, String[]> getClosedItemsByCA(String numCA) throws ApplicationException, TechnicalException {
        Map<String, String[]> resultMap = null;

        numCA = FunctionsUtils.checkNotNullNotEmptyAndTrim("numCA", numCA, LOGGER);

        // récupération des postes par le DAO et transfo en DTO
        List<PosteCommandeAchat> receivedItems = this.getDao().getClosedItemsByNumCA(numCA, "idSap");

        if (!receivedItems.isEmpty()) {
            int nbResults = receivedItems.size();
            resultMap = new HashMap<String, String[]>();
            String[] ids = new String[nbResults];
            String[] numPostes = new String[nbResults];
            String[] typesAchat = new String[nbResults];
            String[] references = new String[nbResults];
            String[] designations = new String[nbResults];
            String[] qtesCommandees = new String[nbResults];
            String[] qtesAcceptees = new String[nbResults];
            String[] statuts = new String[nbResults];

            int cpt = 0;
            // Parcours des postes pour créer les listes attendues par la fct
            // d'extension
            for (PosteCommandeAchat posteCommandeAchat : receivedItems) {
                String nomUnite = "";
                String qteCommandee = String.valueOf(posteCommandeAchat.getQuantiteCommandee());
                String qteAcceptee = String
                        .valueOf(posteCommandeAchat.getQuantiteCommandee() - posteCommandeAchat.getQuantiteRestante());
                if (posteCommandeAchat.getUnite() != null) {
                    nomUnite = posteCommandeAchat.getUnite().getNom();
                    qteCommandee = String.format("%s (%s)", qteCommandee, nomUnite);
                    qteAcceptee = String.format("%s (%s)", qteAcceptee, nomUnite);
                }

                ids[cpt] = String.valueOf(posteCommandeAchat.getId());
                numPostes[cpt] = String.valueOf(posteCommandeAchat.getIdSap());
                typesAchat[cpt] = posteCommandeAchat.getTypeAchat().getDesignation();
                references[cpt] = posteCommandeAchat.getReference();
                designations[cpt] = posteCommandeAchat.getDesignation();
                qtesCommandees[cpt] = qteCommandee;
                qtesAcceptees[cpt] = qteAcceptee;
                statuts[cpt] = posteCommandeAchat.getStatut().displayName();
                cpt++;
            }

            resultMap.put(Constantes.KEY_MAP_ID, ids);
            resultMap.put(Constantes.KEY_MAP_NUMERO, numPostes);
            resultMap.put(Constantes.KEY_MAP_TYPE_ACHAT, typesAchat);
            resultMap.put(Constantes.KEY_MAP_REFERENCE, references);
            resultMap.put(Constantes.KEY_MAP_DESIGNATION, designations);
            resultMap.put(Constantes.KEY_MAP_QTE_COMMANDEE, qtesCommandees);
            resultMap.put(Constantes.KEY_MAP_QTE_ACCEPTEE, qtesAcceptees);
            resultMap.put(Constantes.KEY_MAP_STATUT, statuts);
        }

        return resultMap;
    }

    @Override
    public Map<String, String[]> getReceivableItemsByCaAndSpendingService(String numCA, String codeSpendingService)
            throws ApplicationException, TechnicalException {
        Map<String, String[]> resultMap = null;

        numCA = FunctionsUtils.checkNotNullNotEmptyAndTrim("numCA", numCA, LOGGER);
        codeSpendingService =
                FunctionsUtils.checkNotNullNotEmptyAndTrim("codeSpendingService", codeSpendingService, LOGGER);

        // récupération des postes par le DAO et transfo en DTO
        List<PosteCommandeAchat> receivableItems =
                this.getDao().getReceivableItemsByCaAndSpendingService(numCA, codeSpendingService, "idSap");

        if (!receivableItems.isEmpty()) {
            int nbResults = receivableItems.size();
            resultMap = new HashMap<String, String[]>();
            String[] ids = new String[nbResults];
            String[] numPostes = new String[nbResults];
            String[] typesAchat = new String[nbResults];
            String[] references = new String[nbResults];
            String[] designations = new String[nbResults];
            String[] qtesCommandees = new String[nbResults];
            String[] qtesAcceptees = new String[nbResults];
            String[] statuts = new String[nbResults];

            int cpt = 0;
            String unite = "";
            String qteCommandee = null;
            String qteAcceptee = null;
            Unite unit = null;
            // Parcours des postes pour créer les listes attendues par la fct
            // d'extension
            for (PosteCommandeAchat posteCommandeAchat : receivableItems) {
                qteCommandee = String.valueOf(posteCommandeAchat.getQuantiteCommandee());
                qteAcceptee = String
                        .valueOf(posteCommandeAchat.getQuantiteCommandee() - posteCommandeAchat.getQuantiteRestante());
                unit = posteCommandeAchat.getUnite();
                if (unit != null) {
                    unite = unit.getNom();
                    qteCommandee = String.format("%s (%s)", qteCommandee, unite);
                    qteAcceptee = String.format("%s (%s)", qteAcceptee, unite);
                }

                ids[cpt] = String.valueOf(posteCommandeAchat.getId());
                numPostes[cpt] = posteCommandeAchat.getIdSap();
                typesAchat[cpt] = posteCommandeAchat.getTypeAchat().getDesignation();
                references[cpt] = posteCommandeAchat.getReference();
                designations[cpt] = posteCommandeAchat.getDesignation();
                qtesCommandees[cpt] = qteCommandee;
                qtesAcceptees[cpt] = qteAcceptee;
                statuts[cpt] = posteCommandeAchat.getStatut().displayName();
                cpt++;
            }

            resultMap.put(Constantes.KEY_MAP_ID, ids);
            resultMap.put(Constantes.KEY_MAP_NUMERO, numPostes);
            resultMap.put(Constantes.KEY_MAP_TYPE_ACHAT, typesAchat);
            resultMap.put(Constantes.KEY_MAP_REFERENCE, references);
            resultMap.put(Constantes.KEY_MAP_DESIGNATION, designations);
            resultMap.put(Constantes.KEY_MAP_QTE_COMMANDEE, qtesCommandees);
            resultMap.put(Constantes.KEY_MAP_QTE_ACCEPTEE, qtesAcceptees);
            resultMap.put(Constantes.KEY_MAP_STATUT, statuts);
        }
        return resultMap;
    }

    @Override
    public Map<String, String[]> getReceivableItemsByCaAndSpendingServiceAndCsf(String numCA,
            String codeSpendingService, String numCSF)
            throws ApplicationException, TechnicalException {
        Map<String, String[]> resultMap = null;

        numCA = FunctionsUtils.checkNotNullNotEmptyAndTrim("numCA", numCA, LOGGER);
        codeSpendingService =
                FunctionsUtils.checkNotNullNotEmptyAndTrim("codeSpendingService", codeSpendingService, LOGGER);
        numCSF = FunctionsUtils.checkNotNullNotEmptyAndTrim("numCSF", numCSF, LOGGER);

        // récupération des postes par le DAO et transfo en DTO
        List<PosteCommandeAchat> receivableItems =
                this.getDao().getReceivableItemsByCaAndSpendingServiceAndCsf(numCA, codeSpendingService, "idSap", numCSF);

        if (!receivableItems.isEmpty()) {
            int nbResults = receivableItems.size();
            resultMap = new HashMap<String, String[]>();
            String[] ids = new String[nbResults];
            String[] numPostes = new String[nbResults];
            String[] typesAchat = new String[nbResults];
            String[] references = new String[nbResults];
            String[] designations = new String[nbResults];
            String[] qtesCommandees = new String[nbResults];
            String[] qtesAcceptees = new String[nbResults];
            String[] statuts = new String[nbResults];

            int cpt = 0;
            String unite = "";
            String qteCommandee = null;
            String qteAcceptee = null;
            Unite unit = null;
            // Parcours des postes pour créer les listes attendues par la fct
            // d'extension
            for (PosteCommandeAchat posteCommandeAchat : receivableItems) {
                qteCommandee = String.valueOf(posteCommandeAchat.getQuantiteCommandee());
                qteAcceptee = String
                        .valueOf(posteCommandeAchat.getQuantiteCommandee() - posteCommandeAchat.getQuantiteRestante());
                unit = posteCommandeAchat.getUnite();
                if (unit != null) {
                    unite = unit.getNom();
                    qteCommandee = String.format("%s (%s)", qteCommandee, unite);
                    qteAcceptee = String.format("%s (%s)", qteAcceptee, unite);
                }

                ids[cpt] = String.valueOf(posteCommandeAchat.getId());
                numPostes[cpt] = posteCommandeAchat.getIdSap();
                typesAchat[cpt] = posteCommandeAchat.getTypeAchat().getDesignation();
                references[cpt] = posteCommandeAchat.getReference();
                designations[cpt] = posteCommandeAchat.getDesignation();
                qtesCommandees[cpt] = qteCommandee;
                qtesAcceptees[cpt] = qteAcceptee;
                statuts[cpt] = posteCommandeAchat.getStatut().displayName();
                cpt++;
            }

            resultMap.put(Constantes.KEY_MAP_ID, ids);
            resultMap.put(Constantes.KEY_MAP_NUMERO, numPostes);
            resultMap.put(Constantes.KEY_MAP_TYPE_ACHAT, typesAchat);
            resultMap.put(Constantes.KEY_MAP_REFERENCE, references);
            resultMap.put(Constantes.KEY_MAP_DESIGNATION, designations);
            resultMap.put(Constantes.KEY_MAP_QTE_COMMANDEE, qtesCommandees);
            resultMap.put(Constantes.KEY_MAP_QTE_ACCEPTEE, qtesAcceptees);
            resultMap.put(Constantes.KEY_MAP_STATUT, statuts);
        }
        return resultMap;
    }
    @Override
    public Map<String, String[]> getNotReceivableItemsByCaAndSpendingService(String numCA, String codeSpendingService)
            throws ApplicationException, TechnicalException {
        Map<String, String[]> resultMap = null;

        numCA = FunctionsUtils.checkNotNullNotEmptyAndTrim("numCA", numCA, LOGGER);
        codeSpendingService =
                FunctionsUtils.checkNotNullNotEmptyAndTrim("codeSpendingService", codeSpendingService, LOGGER);

        // récupération des postes par le DAO et transfo en DTO
        List<PosteCommandeAchat> notReceivableItems =
                this.getDao().getNotReceivableItemsByCaAndSpendingService(numCA, codeSpendingService, "idSap");

        if (!notReceivableItems.isEmpty()) {
            int nbResults = notReceivableItems.size();
            resultMap = new HashMap<String, String[]>();
            String[] ids = new String[nbResults];
            String[] numPostes = new String[nbResults];
            String[] typesAchat = new String[nbResults];
            String[] references = new String[nbResults];
            String[] designations = new String[nbResults];
            String[] qtesCommandees = new String[nbResults];
            String[] qtesAcceptees = new String[nbResults];
            String[] statuts = new String[nbResults];
            String[] serviceDepensiers = new String[nbResults];

            int cpt = 0;
            String unite = "";
            String qteCommandee = null;
            String qteAcceptee = null;
            Unite unit = null;
            // Parcours des postes pour créer les listes attendues par la fct
            // d'extension
            for (PosteCommandeAchat posteCommandeAchat : notReceivableItems) {
                qteCommandee = String.valueOf(posteCommandeAchat.getQuantiteCommandee());
                qteAcceptee = String
                        .valueOf(posteCommandeAchat.getQuantiteCommandee() - posteCommandeAchat.getQuantiteRestante());
                unit = posteCommandeAchat.getUnite();
                if (unit != null) {
                    unite = unit.getNom();
                    qteCommandee = String.format("%s (%s)", qteCommandee, unite);
                    qteAcceptee = String.format("%s (%s)", qteAcceptee, unite);
                }

                ids[cpt] = String.valueOf(posteCommandeAchat.getId());
                numPostes[cpt] = posteCommandeAchat.getIdSap();
                typesAchat[cpt] = posteCommandeAchat.getTypeAchat().getDesignation();
                references[cpt] = posteCommandeAchat.getReference();
                designations[cpt] = posteCommandeAchat.getDesignation();
                qtesCommandees[cpt] = qteCommandee;
                qtesAcceptees[cpt] = qteAcceptee;
                statuts[cpt] = posteCommandeAchat.getStatut().displayName();
                serviceDepensiers[cpt] = posteCommandeAchat.getServiceDepensierByIdServiceDepensier().getDesignation();
                cpt++;
            }

            resultMap.put(Constantes.KEY_MAP_ID, ids);
            resultMap.put(Constantes.KEY_MAP_NUMERO, numPostes);
            resultMap.put(Constantes.KEY_MAP_TYPE_ACHAT, typesAchat);
            resultMap.put(Constantes.KEY_MAP_REFERENCE, references);
            resultMap.put(Constantes.KEY_MAP_DESIGNATION, designations);
            resultMap.put(Constantes.KEY_MAP_QTE_COMMANDEE, qtesCommandees);
            resultMap.put(Constantes.KEY_MAP_QTE_ACCEPTEE, qtesAcceptees);
            resultMap.put(Constantes.KEY_MAP_SERVICE_DEPENSIER, serviceDepensiers);
            resultMap.put(Constantes.KEY_MAP_STATUT, statuts);
        }
        return resultMap;
    }

    @Override
    public Map<String, String[]> getAllPosteOfCA(String idSapOfCA) throws TechnicalException {
        Map<String, String[]> resultatMap = new HashMap<String, String[]>();
        List<PosteCommandeAchat> listePostesCA = getDao().getAllPosteOfCA(idSapOfCA, "idSap");

        if (!listePostesCA.isEmpty()) {
            int nbResults = listePostesCA.size();
            String[] numPostes = new String[nbResults];
            String[] typeAchats = new String[nbResults];
            String[] references = new String[nbResults];
            String[] designations = new String[nbResults];
            String[] qtesCommandees = new String[nbResults];
            String[] qtesAcceptees = new String[nbResults];
            String[] statuts = new String[nbResults];

            int cpt = 0;
            String unite = "";
            String qteCommandee = null;
            String qteAcceptee = null;
            Unite unit = null;
            for (PosteCommandeAchat posteCA : listePostesCA) {
                qteCommandee = String.valueOf(posteCA.getQuantiteCommandee());
                qteAcceptee = String.valueOf(posteCA.getQuantiteCommandee() - posteCA.getQuantiteRestante());
                unit = posteCA.getUnite();
                if (unit != null) {
                    unite = unit.getNom();
                    qteCommandee = String.format("%s (%s)", qteCommandee, unite);
                    qteAcceptee = String.format("%s (%s)", qteAcceptee, unite);
                }

                numPostes[cpt] = posteCA.getIdSap();
                typeAchats[cpt] = posteCA.getTypeAchat().getDesignation();
                references[cpt] = posteCA.getReference();
                designations[cpt] = posteCA.getDesignation();
                qtesCommandees[cpt] = qteCommandee;
                qtesAcceptees[cpt] = qteAcceptee;
                statuts[cpt] = posteCA.getStatut().displayName();
                cpt++;
            }
            resultatMap.put(Constantes.KEY_MAP_NUMERO, numPostes);
            resultatMap.put(Constantes.KEY_MAP_TYPE_ACHAT, typeAchats);
            resultatMap.put(Constantes.KEY_MAP_REFERENCE, references);
            resultatMap.put(Constantes.KEY_MAP_DESIGNATION, designations);
            resultatMap.put(Constantes.KEY_MAP_QTE_COMMANDEE, qtesCommandees);
            resultatMap.put(Constantes.KEY_MAP_QTE_ACCEPTEE, qtesAcceptees);
            resultatMap.put(Constantes.KEY_MAP_STATUT, statuts);
        }
        return resultatMap;
    }

    @Override
    public boolean updateRemainingQuantityAndStatusAfterCsfSave(PosteCommandeAchat pca, Long acceptedQuantity) {
        return this.getDao().updateQuantityAndStatus(pca, acceptedQuantity, true);        
    }

    @Override
    public void updateRemainingQuantityAndStatusAfterCsfDelete(PosteCommandeAchat pca, Long acceptedQuantity) {
    	System.out.println("**********************"+pca.getId()+"*************************");
    	System.out.println("**********************"+acceptedQuantity+"*************************");
        this.getDao().updateQuantityAndStatus(pca, acceptedQuantity, false);        
    }

    // alpha ajout
	@Override
	public void delete(List<PosteCommandeAchat> liste) {
		// TODO Auto-generated method stub
		System.out.print("***************delete(List<PosteCommandeAchat> liste) taille liste="+liste.size()+"*****************");
		this.posteCaDao.delete(liste);
	}

	@Override
	public List<DemandeAchat> daLieesCa(List<CommandeAchat> listDeletableCa) {
		// TODO Auto-generated method stub
		return this.posteCaDao.daLieesCa(listDeletableCa);
	}
}
