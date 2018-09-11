package com.sigif.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sigif.modele.Sequence;


/**
 * Classe d'accès à la séquence.
 * @author Malick Diagne
 */
@Repository("sequenceDao")
public class SequenceDao implements SequenceDaoInterface {
	/**
	 * La session factory.
	 */
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Sequence persist() {
		Sequence seq = new Sequence();

		// ici save suite à la lecture de la javadoc
		getSessionFactory().getCurrentSession().save(seq);

		return seq;
	}

	/**
	 * Renvoie m_SessionFactory.
	 * 
	 * @return Le m_SessionFactory.
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	
}
