package com.sigif.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.sigif.modele.AbstractModele;
import com.sigif.util.ReflectionUtil;

/**
 * Implémentation de la classe abstraite des DAO.
 *
 * @param <E>
 *            Modèle sur lequel s'appuie ce DAO
 */
public abstract class AbstractDAOImpl<E extends AbstractModele> implements AbstractDAO<E> {

    /**
     * SessionFactory Hibernate.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Classe de l'entité.
     */
    protected Class<E> entityClass;

    /**
     * Constructeur (récupère la classe de l'entité).
     */
    @SuppressWarnings("unchecked")
    public AbstractDAOImpl() {
        // Définit automatiquement le type générique manipulé par le DAO
        this.entityClass = (Class<E>) ReflectionUtil.getTypeParametreGenerique(getClass(), 0);
    }

    /**
     * Récupère la session courante Hibernate.
     * 
     * @return la session courante Hibernate
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Crée la classe d'interrogation correspondant à l'entité.
     * 
     * @return la classe d'interrogation correspondant à l'entité
     */
    protected Criteria createCriteria() {
        return getSession().createCriteria(entityClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E getById(int id) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("id", id));
        return (E) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> getAll() {
        Criteria criteria = createCriteria();
        return criteria.list();
    }

    @Override
    public E getUniqueByParam(String attribut, Object valeur) {
        return getUniqueByParams(Collections.singletonMap(attribut, valeur));
    }

    @Override
    public List<E> getByParam(String attribut, Object valeur) {
        return getByParams(Collections.singletonMap(attribut, valeur));
    }

    @SuppressWarnings("unchecked")
    @Override
    public E getUniqueByParams(Map<String, Object> params) {
        Criteria criteria = createCriteria();
        params.forEach((attribut, valeur) -> criteria.add(Restrictions.eq(attribut, valeur)));
        return (E) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> getByParams(Map<String, Object> params) {
        Criteria criteria = createCriteria();
        params.forEach((attribut, valeur) -> criteria.add(Restrictions.eq(attribut, valeur)));
        return criteria.list();
    }

    @Override
    public int save(E entity) {
        getSession().persist(entity);
        getSession().flush();
        return entity.getId();
    }

    @Override
    public void deleteById(int id) {
        E entity = getById(id);
        delete(entity);
    }

    @Override
    public void delete(E entity) {
        getSession().delete(entity);
        getSession().flush();
    }

    @Override
    public void merge(E entity) {
        getSession().merge(entity);
        getSession().flush();
    }
}