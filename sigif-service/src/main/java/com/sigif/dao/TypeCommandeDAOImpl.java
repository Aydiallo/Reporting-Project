package com.sigif.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.TypeCommande;

/**
 * Implémentation de la classe d'accès aux données des types de commande.
 * @author Meissa Beye
 *
 */
@Repository("typeCommandeDAO")
public class TypeCommandeDAOImpl extends AbstractDAOImpl<TypeCommande> implements TypeCommandeDAO {
	/** LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TypeCommandeDAOImpl.class);
		
	@SuppressWarnings("unchecked")
	@Override
    public List<TypeCommande> getTypeCommandeActifs() throws TechnicalException {
		try {
			List<TypeCommande> typeCommandes  = this.getSession()
					 .createQuery(" FROM TypeCommande where statut = '" + Statut.actif + "' ").list();
			return typeCommandes;
		} catch (Exception e) {
		    LOGGER.error("Impossible de récuperer les types de commande actifs. ");
		    throw new TechnicalException("Impossible de récuperer les types de commande actifs", e);
		}	
	}
}
