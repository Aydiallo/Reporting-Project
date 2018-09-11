package com.sigif.bouchons;

import com.sigif.dto.RoleDTO;

public class RoleBouchon {
	
	public static RoleDTO getRoleDDA() {
		RoleDTO role = new RoleDTO();
		role.setCode("DA");
		role.setDesignation("Demandeur2");
		return role;
	}

}
