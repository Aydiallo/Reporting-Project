
package com.sigif.dao;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.PieceJointe;
import com.sigif.util.ListErreurs;

/**
 * Implémentation de la classe d'accès aux pièces jointes.
 * 
 * @author Meissa Beye
 * @since 19-05-2017
 */
@Repository("pieceJointeDAO")
public class PieceJointeDAOImpl extends AbstractDAOImpl<PieceJointe> implements PieceJointeDAO {
    /**
     * Loggueur.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PieceJointeDAOImpl.class);

    @Override
    public int addPieceJointe(PieceJointe pieceJointe) throws TechnicalException {
        try {
            return save(pieceJointe);
        } catch (Exception e) {
            LOGGER.error("Impossible d'ajouter la pièce jointe : " + pieceJointe + " :" + e.getMessage());
            throw new TechnicalException("Echec lors de l'enregistrement d'une pièce jointe", e);
        }
    }

    @Override
    public void updatePieceJointe(PieceJointe pieceJointe) throws TechnicalException {
        try {
            merge(pieceJointe);
        } catch (Exception e) {
            LOGGER.error("Impossible de mettre à jour la pièce jointe: " + pieceJointe + " :" + e.getMessage());

            throw new TechnicalException(
                    ListErreurs.ERREUR_UPDATE_ENTITY + " - Impossible de mettre à jour une pièce jointe", e);
        }
    }

    @Override
    public void deletePieceJointe(int id) throws TechnicalException {
        try {
            PieceJointe pieceJointe = getPieceJointeById(id);
            if (pieceJointe != null) {
                FileUtils.deleteQuietly(new File(pieceJointe.getEmplacement()));
                this.delete(pieceJointe);
            } else {
                LOGGER.error("Aucune PJ avec l'id : " + id + ". Suppression impossible. ");
            }
        } catch (Exception e) {
            LOGGER.error("Impossible de supprimer la pièce jointe avec l'id : " + id + " : " + e.getMessage());
            throw new TechnicalException(
                    ListErreurs.ERREUR_DELETE_ENTITY + " - Impossible de supprimer la pièce jointe avec l'id : " + id,
                    e);
        }
    }

    @Override
    public PieceJointe getPieceJointeById(int id) throws TechnicalException {
        try {
            Criteria criteria = createCriteria();

            criteria.add(Restrictions.eq("id", id));

            return (PieceJointe) criteria.uniqueResult();
        } catch (Exception e) {
            throw new TechnicalException("Erreur lors de la récupération de la pièce jointe d'id : " + id, e);
        }
    }

    @Override
    public int getIdPieceJointeByDemandeAchat(DemandeAchat demandeAchat) throws TechnicalException {
        try {
            return (int) this.getSession().createQuery(" SELECT id FROM PieceJointe where demandeAchat = :demandeA ")
                    .setParameter("demandeA", demandeAchat).uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            throw new TechnicalException(
                    "Erreur lors de la récupération de la pièce jointe de la DA d'id : " + demandeAchat.getId(), e);
        }
    }
}
