package com.sigif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.SequenceDao;

/**
 * Service de gestion de la séquence
 * @author Malick Diagne
 */
@Service("sequenceService")
@Transactional
public class SequenceService implements SequenceServiceInterface {

    /**
     * DAO pour l'accès à la table des séquences.
     */
	@Autowired
	SequenceDao sequenceDao;
	
	@Override
	public Long getNumero() {		
		return sequenceDao.persist().getId();
	}

}
