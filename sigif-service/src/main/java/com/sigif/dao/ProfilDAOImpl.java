package com.sigif.dao;

import org.springframework.stereotype.Repository;

import com.sigif.modele.Profil;

/**
 * Implémentation de la classe d'accès aux données des profils.
 * 
 * @author Mickael Beaupoil
 *
 */
@Repository("profilDAO")
public class ProfilDAOImpl extends AbstractDAOImpl<Profil> implements ProfilDAO {
}
