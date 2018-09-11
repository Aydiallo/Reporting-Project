package com.sigif.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.CategoriePosteDAO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.util.Constantes;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe de lecture de la catégorie des postes.
 * 
 * @author Mickael Beaupoil
 *
 */
@Service("categoriePosteService")
@Transactional
public class CategoriePosteServiceImpl implements CategoriePosteService {
    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriePosteServiceImpl.class);
    
    /**
     * DAO de lecture des catégories de poste.
     */
    @Autowired
    CategoriePosteDAO categorieDao;

    @Override
    public String getCategorieByTypeAndReference(String codeTypeAchat, String referencePoste)
            throws ApplicationException, TechnicalException {
        String categorie = null;
        codeTypeAchat = FunctionsUtils.trimOrNullifyString(codeTypeAchat);
        referencePoste = FunctionsUtils.checkNotNullNotEmptyAndTrim("referencePoste", referencePoste, LOGGER);
        switch (codeTypeAchat) {
        case Constantes.TYPE_ACHAT_ACHAT_STOCKE:
            categorie = this.categorieDao.getCategoriePosteForTypeS(referencePoste);
            break;
        case Constantes.TYPE_ACHAT_FONCTIONNEMENT:
            categorie = this.categorieDao.getCategoriePosteForTypeF(referencePoste);
            break;
        case Constantes.TYPE_ACHAT_IMMOBILISATION:
            categorie = this.categorieDao.getCategoriePosteForTypeI(referencePoste);
            break;
        default:
            String msgErreur = String.format("Code de type d'achat inconnu : '%s'", codeTypeAchat);
            throw new ApplicationException(msgErreur);
        }

        return categorie;
    }

    @Override
    public String getCategorieActiveByTypeAndReference(String codeTypeAchat, String referencePoste)
            throws ApplicationException, TechnicalException {
        String categorie = null;
        codeTypeAchat = FunctionsUtils.trimOrNullifyString(codeTypeAchat);
        referencePoste = FunctionsUtils.checkNotNullNotEmptyAndTrim("referencePoste", referencePoste, LOGGER);
        switch (codeTypeAchat) {
        case Constantes.TYPE_ACHAT_ACHAT_STOCKE:
            categorie = this.categorieDao.getCategorieActivePosteForTypeS(referencePoste);
            break;
        case Constantes.TYPE_ACHAT_FONCTIONNEMENT:
            categorie = this.categorieDao.getCategorieActivePosteForTypeF(referencePoste);
            break;
        case Constantes.TYPE_ACHAT_IMMOBILISATION:
            categorie = this.categorieDao.getCategorieActivePosteForTypeI(referencePoste);
            break;
        default:
            String msgErreur = String.format("Code de type d'achat inconnu : '%s'", codeTypeAchat);
            throw new ApplicationException(msgErreur);
        }

        return categorie;
    }
}
