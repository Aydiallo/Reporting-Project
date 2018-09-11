package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.CategorieAchatDAO;
import com.sigif.dto.CategorieAchatDTO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CategorieAchat;
import com.sigif.util.Constantes;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux catégories d'acht.
 * 
 * @author Mamadou Ndir 
 * @since 23 mai 2017 09:20:36
 */
@Service("categorieAchatService")
@Transactional
public class CategorieAchatServiceImpl extends AbstractServiceImpl<CategorieAchat, CategorieAchatDTO>
        implements CategorieAchatService {
    /**
     * Loggueur.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CategorieAchatServiceImpl.class);

    @Override
    protected CategorieAchatDAO getDao() {
        return (CategorieAchatDAO) super.getDao();
    }

    @Override
    public Map<String, String> getAllPurchasingCategories(String status)
            throws ApplicationException, TechnicalException {
        status = FunctionsUtils.checkNotNullNotEmptyAndTrim("statut", status, LOG);
        List<CategorieAchat> categoriesAchat = this.getDao().getAllPurchasingCategories(status);
        Map<String, String> categDachatMap = null;

        if (categoriesAchat.size() > 0) {
            categDachatMap = new HashMap<String, String>();
            for (CategorieAchat c : categoriesAchat) {
                categDachatMap.put(c.getCode(), c.getDesignation());
            }
            categDachatMap = FunctionsUtils.sortByValue(categDachatMap);
        }
        return categDachatMap;
    }

    @Override
    public Map<String, String> getInformationCategorieAchatByCode(String codeCA)
            throws ApplicationException, TechnicalException {
        codeCA = FunctionsUtils.checkNotNullNotEmptyAndTrim("codeCA", codeCA, LOG);
        CategorieAchat categorie = this.getDao().getCategorieAchatByCode(codeCA);
        Map<String, String> categorieMap = null;
        if (categorie != null) {
            categorieMap = new HashMap<String, String>();
            categorieMap.put(Constantes.KEY_MAP_CODE, categorie.getCode());
            categorieMap.put(Constantes.KEY_MAP_DESIGNATION, categorie.getDesignation());
            categorieMap.put(Constantes.KEY_MAP_STATUT, categorie.getStatut().toString());
        }
        return categorieMap;
    }

}
