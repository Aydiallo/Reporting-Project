package com.sigif.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;

/**
 * Implémentation de la classe d'accès aux données des catégories de postes.
 * 
 * @author Mickael Beaupoil
 *
 */
@Repository("categoriePosteDao")
public class CategoriePosteDAOImpl implements CategoriePosteDAO {
    /**
     * Loggueur.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriePosteDAOImpl.class);

    /**
     * SessionFactory Hibernate.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Récupère la session courante Hibernate.
     * 
     * @return la session courante Hibernate
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public String getCategoriePosteForTypeS(String referenceArticle) throws TechnicalException {
        try {
            String valeur = null;
            Query query = this.getSession()
                    .createQuery("SELECT gdm.designation FROM Article as art "
                            + "INNER JOIN art.groupeDeMarchandises as gdm WHERE art.numero = :reference")
                    .setParameter("reference", referenceArticle);
            if (query.iterate().hasNext()) {
                valeur = (String) query.iterate().next();
            }
            return valeur;
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Echec de lecture de la catégorie du poste de type d'achat 'S' pour la référence '%s'",
                    referenceArticle));

            throw new TechnicalException(String.format(
                    "Echec de lecture de la catégorie du poste de type d'achat 'S' pour la référence '%s'",
                    referenceArticle), e);
        }
    }

    @Override
    public String getCategoriePosteForTypeF(String referenceGroupeMarch) throws TechnicalException {
        try {
            String valeur = null;
            Query query = this.getSession()
                    .createQuery("SELECT gdm.designation FROM GroupeDeMarchandises as gdm WHERE gdm.code = :reference")
                    .setParameter("reference", referenceGroupeMarch);
            if (query.iterate().hasNext()) {
                valeur = (String) query.iterate().next();
            }
            return valeur;
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Echec de lecture de la catégorie du poste de type d'achat 'F' pour la référence '%s'",
                    referenceGroupeMarch));

            throw new TechnicalException(String.format(
                    "Echec de lecture de la catégorie du poste de type d'achat 'F' pour la référence '%s'",
                    referenceGroupeMarch), e);
        }
    }

    @Override
    public String getCategoriePosteForTypeI(String referenceImmo) throws TechnicalException {
        try {
            Query query = this.getSession()
                    .createQuery("SELECT catImmo.designation AS cat, typImmo.designation AS typ FROM Immobilisation as immo "
                            + "INNER JOIN immo.categorieImmobilisation as catImmo "
                            + "INNER JOIN catImmo.typeImmobilisation as typImmo "
                            + "WHERE immo.code = :reference")
                    .setParameter("reference", referenceImmo);
            
            Object[] result = (Object[]) query.uniqueResult();
            if (result == null) {
                return null;
            }
            
            String catImmo = (String) result[0];
            String typImmo = (String) result[1];
                        
            return typImmo + "/" + catImmo;
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Echec de lecture de la catégorie du poste de type d'achat 'I' pour la référence '%s'",
                    referenceImmo));

            throw new TechnicalException(String.format(
                    "Echec de lecture de la catégorie du poste de type d'achat 'I' pour la référence '%s'",
                    referenceImmo), e);
        }
    }

    @Override
    public String getCategorieActivePosteForTypeS(String referenceArticle) throws TechnicalException {
        try {
            String valeur = null;
            Query query = this.getSession()
                    .createQuery("SELECT gdm.designation FROM Article as art "
                            + "INNER JOIN art.groupeDeMarchandises as gdm WHERE art.numero = :reference AND art.statut = :statut")
                    .setParameter("reference", referenceArticle).setParameter("statut", Statut.actif);
            if (query.iterate().hasNext()) {
                valeur = (String) query.iterate().next();
            }
            return valeur;
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Echec de lecture de la catégorie active du poste de type d'achat 'S' pour la référence '%s'",
                    referenceArticle));

            throw new TechnicalException(String.format(
                    "Echec de lecture de la catégorie active du poste de type d'achat 'S' pour la référence '%s'",
                    referenceArticle), e);
        }
    }

    @Override
    public String getCategorieActivePosteForTypeF(String referenceGroupeMarch) throws TechnicalException {
        try {
            String valeur = null;
            Query query = this.getSession()
                    .createQuery(
                            "SELECT gdm.designation FROM GroupeDeMarchandises as gdm "
                            + "WHERE gdm.code = :reference AND gdm.statut = :statut")
                    .setParameter("reference", referenceGroupeMarch).setParameter("statut", Statut.actif);
            if (query.iterate().hasNext()) {
                valeur = (String) query.iterate().next();
            }
            return valeur;
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Echec de lecture de la catégorie active du poste de type d'achat 'F' pour la référence '%s'",
                    referenceGroupeMarch));

            throw new TechnicalException(String.format(
                    "Echec de lecture de la catégorie active du poste de type d'achat 'F' pour la référence '%s'",
                    referenceGroupeMarch), e);
        }
    }

    @Override
    public String getCategorieActivePosteForTypeI(String referenceImmo) throws TechnicalException {
        try {
            String valeur = null;
            Query query = this.getSession()
                    .createQuery("SELECT catImmo.designation FROM Immobilisation as immo "
                            + "INNER JOIN immo.categorieImmobilisation as catImmo "
                            + "WHERE immo.code = :reference AND immo.statut = :statut")
                    .setParameter("reference", referenceImmo).setParameter("statut", Statut.actif);
            if (query.iterate().hasNext()) {
                valeur = (String) query.iterate().next();
            }
            return valeur;
        } catch (Exception e) {
            LOGGER.error(String.format(
                    "Echec de lecture de la catégorie active du poste de type d'achat 'I' pour la référence '%s'",
                    referenceImmo));

            throw new TechnicalException(String.format(
                    "Echec de lecture de la catégorie active du poste de type d'achat 'I' pour la référence '%s'",
                    referenceImmo), e);
        }
    }

}
