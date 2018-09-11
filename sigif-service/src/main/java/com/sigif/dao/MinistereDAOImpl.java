package com.sigif.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Ministere;

/**
 * Implémentation de la classe d'accès aux données des ministères. 
 *
 */
@Repository("ministereDAO")
public class MinistereDAOImpl extends AbstractDAOImpl<Ministere> implements MinistereDAO {
    /**
     * Loggueur.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MinistereDAOImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<Ministere> getAllMinisteryOfUserByLoginAndRole(String login, String role, String status)
            throws TechnicalException {
        try {
        	
        	System.out.println("*************************login"+login+"******************************");
        	System.out.println("*************************role"+role+"******************************");
        	System.out.println("*************************status"+status+"******************************");
            StringBuilder queryHql =
                    new StringBuilder("Select distinct m FROM Profil p left join p.ministere m left join p.role r"
                            + " where p.utilisateur.login =:userLogin and p.role.code =:role");
            if (!status.equalsIgnoreCase("all")) {
                queryHql.append(" and m.statut = :paramStatus ");
            }
            queryHql.append(" ORDER BY m.designation");
            
            Query q = this.getSession().createQuery(queryHql.toString());
            q.setParameter("userLogin", login);
            q.setParameter("role", role);
            if (status.equalsIgnoreCase("actif")) {
                q.setParameter("paramStatus", Statut.actif);
            } else if (status.equalsIgnoreCase("inactif")) {
                q.setParameter("paramStatus", Statut.inactif);
            }

            List<Ministere> userMinisteresQuery = q.list();

        	System.out.println("*************************designation ministere "+userMinisteresQuery.get(0).getDesignation()+"******************************");
            return userMinisteresQuery;

        } catch (Exception e) {
            String msgErreur = String.format(
                    "Erreur lors de la recherche des ministères liés à l'utilisateur '%s' pour le rôle '%s' et le staut '%s'",
                    login, role, status);
            LOGGER.error(msgErreur);
            throw new TechnicalException(msgErreur, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ministere> getMinisteres() {
        Criteria criteria = createCriteria();
        List<Ministere> ministeresList = (List<Ministere>) criteria.list();

        return ministeresList;
    }

    @Override
    public Ministere getMinistereByCode(String codeMin) throws TechnicalException {
        try {
            Query query = this.getSession()
                    .createQuery(" From Ministere where code = :codeMin and statut = '" + Statut.actif + "'")
                    .setParameter("codeMin", codeMin);
            Ministere ministere = (Ministere) query.uniqueResult();
            return ministere;

        } catch (Exception e) {
            String msg = String.format("Erreur lors de la recherche du ministere pour le code %s.",  codeMin);
            throw new TechnicalException(msg, e);
        }
    }

	
}
