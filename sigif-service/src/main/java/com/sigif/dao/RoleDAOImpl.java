package com.sigif.dao;

import org.springframework.stereotype.Repository;

import com.sigif.modele.Role;

/**
 * Implémentation de la classe d'accès aux données des rôles.
 *
 */
@Repository("roleDAO")
public class RoleDAOImpl extends AbstractDAOImpl<Role> implements RoleDAO {
}
