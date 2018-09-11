package com.sigif.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dto.RoleDTO;
import com.sigif.modele.Role;

/**
 * Implémentation du service d'accès aux rôles
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl extends AbstractServiceImpl<Role, RoleDTO>
implements RoleService {
}
