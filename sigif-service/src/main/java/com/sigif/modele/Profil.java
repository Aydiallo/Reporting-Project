package com.sigif.modele;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entité représentant la table profil.
 */
@Entity
@Table(name = "profil")
public class Profil extends AbstractModele {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRole", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMinistere", nullable = false)
    private Ministere ministere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUtilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServiceDepensier", nullable = false)
    private ServiceDepensier serviceDepensier;

    public Profil() {
        super();
    }

    public ServiceDepensier getServiceDepensier() {
        return serviceDepensier;
    }

    public void setServiceDepensier(ServiceDepensier serviceDepensier) {
        this.serviceDepensier = serviceDepensier;
    }

    public Ministere getMinistere() {
        return ministere;
    }

    public void setMinistere(Ministere ministere) {
        this.ministere = ministere;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
