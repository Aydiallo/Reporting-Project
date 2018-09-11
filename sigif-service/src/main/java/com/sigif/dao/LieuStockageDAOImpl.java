package com.sigif.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.LieuStockage;

/**
 * Implémentation de la classe d'accès aux données des lieux de stockage.
 * 
 * @author Meissa Beye
 * @since 14/06/2017
 */
@Repository("LieuStockageDAO")
public class LieuStockageDAOImpl extends AbstractDAOImpl<LieuStockage> implements LieuStockageDAO {

    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LieuStockageDAOImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<LieuStockage> getAllLieuStockageActifs() throws TechnicalException {
        try {
            List<LieuStockage> lieuStockages =
                    this.getSession().createQuery(" FROM LieuStockage where statut = '" + Statut.actif + "'").list();

            return lieuStockages;

        } catch (Exception e) {
            LOGGER.error("Impossible de récupérer les lieux de stockage actifs ");
            throw new TechnicalException("Impossible de récupérer les lieus de stockage actifs", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LieuStockage> getActifStorageAreaInSameDivision(int idStorageArea) throws TechnicalException {
        try {
            // Passage par une requête en SQL à cause du self join
            StringBuilder sqlQuery = new StringBuilder("SELECT lieuInDiv.* FROM lieu_stockage lieuInDiv");
            sqlQuery.append(" INNER JOIN lieu_stockage lieuInitial ON lieuInitial.division = lieuInDiv.division");
            sqlQuery.append(" WHERE lieuInitial.id = :id AND lieuInDiv.statut = :statut");
            SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());
            query.setParameter("id", idStorageArea);
            query.setParameter("statut", Statut.actif.toString());
            query.addEntity(LieuStockage.class);
            return (List<LieuStockage>) query.list();
        } catch (Exception e) {
            String msgErreur = String.format(
                    "Echec de la récupération des lieux de stockage de la même division que celui d'id '%d'.",
                    idStorageArea);
            LOGGER.error(msgErreur);
            throw new TechnicalException(msgErreur);
        }
    }
}
