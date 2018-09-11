package com.sigif.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.ServiceDepensier;

/**
 * Implémentation de la classe d'accès aux données des services dépensiers.
 * 
 * @author Mamadou Ndir 
 * @since 1 juin 2017 10:51:50
 */
@Repository("serviceDepensierDAO")
public class ServiceDepensierDAOImpl extends AbstractDAOImpl<ServiceDepensier> implements ServiceDepensierDAO {
    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ServiceDepensierDAOImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<ServiceDepensier> getAllSpendingServicesOfUserByLoginRoleAndMinistery(String login, String ministery,
            String role, String statut) throws TechnicalException {
        try {
            StringBuilder queryHql = new StringBuilder("Select s FROM Profil p join p.serviceDepensier s where 1=1");

            if (login != null) {
                queryHql.append(" and p.utilisateur.login =:userLogin");
            }

            if (ministery != null) {
                queryHql.append(" and p.ministere.code =:codeMinistery");
            }
            if (role != null) {
                queryHql.append(" and p.role.code =:role");
            }
            if (statut.equalsIgnoreCase("actif") || statut.equalsIgnoreCase("inactif")) {
                queryHql.append(" and s.statut = :paramStatut");
            }
            Query query = this.getSession().createQuery(queryHql.toString());
            if (login != null) {
                query.setParameter("userLogin", login);
            }
            if (ministery != null) {
                query.setParameter("codeMinistery", ministery);
            }
            if (role != null) {
                query.setParameter("role", role);
            }
            if (statut.equalsIgnoreCase("actif")) {
                query.setParameter("paramStatut", Statut.actif);
            }
            if (statut.equalsIgnoreCase("inactif")) {
                query.setParameter("paramStatut", Statut.inactif);
            }
            List<ServiceDepensier> servDepListQuery = query.list();
            return servDepListQuery;
        } catch (Exception e) {
            String msg = String.format(
                    "Erreur lors de la récupération de la liste des services depensiers "
                            + "pour le login '%s', le ministère '%s', le rôle '%s' et le statut '%s'.",
                    login, ministery, role, statut);
            LOG.error(msg);
            throw new TechnicalException(msg, e);
        }
    }

    @Override
    public ServiceDepensier getServiceDepensierByCode(String codeSD) throws TechnicalException {
        try {
            Query query = this.getSession()
                    .createQuery(" From ServiceDepensier where code = :codeSD and statut = '" + Statut.actif + "'")
                    .setParameter("codeSD", codeSD);
            ServiceDepensier serviceD = (ServiceDepensier) query.uniqueResult();
            return serviceD;

        } catch (Exception e) {
            String msg = String.format("Erreur lors de la récupération du service depensier de code '%s'.", codeSD);
            LOG.error(msg);
            throw new TechnicalException(msg, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ServiceDepensier> getAllSpendingServicesByMinistery(String ministery, String statut)
            throws TechnicalException {
        try {
            StringBuilder queryHql = new StringBuilder("FROM ServiceDepensier where ministere.code = :ministery");

            if (statut.equalsIgnoreCase("actif") || statut.equalsIgnoreCase("inactif")) {
                queryHql.append(" and statut = :paramStatut");
            }
            Query query = this.getSession().createQuery(queryHql.toString());

            if (ministery != null) {
                query.setParameter("ministery", ministery);
            }

            if (statut.equalsIgnoreCase("actif")) {
                query.setParameter("paramStatut", Statut.actif);
            }
            if (statut.equalsIgnoreCase("inactif")) {
                query.setParameter("paramStatut", Statut.inactif);
            }
            List<ServiceDepensier> servDepListQuery = query.list();
            return servDepListQuery;
        } catch (Exception e) {
            String msg = String.format("Erreur lors de la récupération de la liste des services depensiers "
                    + "pour le ministère '%s' et le statut '%s'.", ministery, statut);
            LOG.error(msg);
            throw new TechnicalException(msg, e);
        }
    }

}
