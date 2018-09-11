package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.LieuStockageDAO;
import com.sigif.dto.LieuStockageDTO;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.LieuStockage;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux lieux de stockage.
 * 
 * @author Meissa Beye
 *
 */
@Service("lieuStockageService")
@Transactional
public class LieuStockageServiceImpl extends AbstractServiceImpl<LieuStockage, LieuStockageDTO>
        implements LieuStockageService {

    @Override
    protected LieuStockageDAO getDao() {
        return (LieuStockageDAO) super.getDao();
    }

    @Override
    public Map<String, String> getAllActifStorageArea() throws TechnicalException {
        List<LieuStockage> lieuStockages = this.getDao().getAllLieuStockageActifs();
        Map<String, String> resultat = new HashMap<String, String>();
        if (lieuStockages != null) {
            for (LieuStockage lieuStockage : lieuStockages) {
                resultat.put(String.valueOf(lieuStockage.getId()), lieuStockage.getDesignation());
            }
        }
        return FunctionsUtils.sortByValue(resultat);
    }

    @Override
    public Map<String, String> getActifStorageAreaInSameDivision(int idStorageArea) throws TechnicalException {
        List<LieuStockage> lieuStockages = this.getDao().getActifStorageAreaInSameDivision(idStorageArea);
        Map<String, String> resultat = new HashMap<String, String>();
        if (lieuStockages != null) {
            for (LieuStockage lieuStockage : lieuStockages) {
                resultat.put(String.valueOf(lieuStockage.getId()), lieuStockage.getDesignation());
            }
        }
        return FunctionsUtils.sortByValue(resultat);
    }

}
