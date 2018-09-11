package com.sigif.modele;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entité représentant la table rôle.
 */
@Entity
@Table(name = "role")
public class Role extends AbstractModele {
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String designation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<Profil> profils;

    public Role() {
        super();
    }

    @Column(nullable = false, length = 3)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(nullable = false, length = 50)
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<Profil> getProfils() {
        return profils;
    }

    public void setProfils(List<Profil> profils) {
        this.profils = profils;
    }

    @Override
    public String toString() {
        return "Role [code=" + code + ", designation=" + designation + "]";
    }

}
