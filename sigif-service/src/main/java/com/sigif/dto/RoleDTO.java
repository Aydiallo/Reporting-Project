package com.sigif.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO de la table rôlet
 *
 */

public class RoleDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = 4998459464572539770L;

    private String code;

	private String designation;

	private List<ProfilDTO> profils;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<ProfilDTO> getProfils() {
		return profils;
	}

	public void setProfils(List<ProfilDTO> profils) {
		this.profils = profils;
	}

}
