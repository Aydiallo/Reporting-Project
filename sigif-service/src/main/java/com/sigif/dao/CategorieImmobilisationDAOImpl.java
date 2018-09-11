package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.CategorieImmobilisation;

/**
 * Implémentation de la classe d'accès aux données des catégories d'immobilisation.
 * 
 * @author Meissa Beye
 */
@Repository("categorieImmobilisationDAO")
public class CategorieImmobilisationDAOImpl extends AbstractDAOImpl<CategorieImmobilisation>
        implements CategorieImmobilisationDAO {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategorieImmobilisationDAOImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<CategorieImmobilisation> getCategorieImmoByTypeImmo(String codeTypeImmo) throws TechnicalException {
        try {
            List<CategorieImmobilisation> categories = null;
            categories = this.getSession()
                    .createQuery(
                            " FROM CategorieImmobilisation where typeImmobilisation.code = :codeTypeImmo and statut = '"
                                    + Statut.actif + "'")
                    .setParameter("codeTypeImmo", codeTypeImmo).list();
            return categories;

        } catch (Exception e) {
            LOGGER.error(
                    "Impossible de récuperer les catégories d'immobilisation actives pour le code " + codeTypeImmo);
            throw new TechnicalException(
                    "Impossible de récuperer les catégories d'immobilisation actives pour le code " + codeTypeImmo, e);
        }
    }
}
