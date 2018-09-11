package com.sigif.dto;

/**
 * DTO de la table profil
 *
 */
public class ProfilDTO extends AbstractDTO {

    private UtilisateurDTO utilisateur;

    private RoleDTO role;

    private ServiceDepensierDTO serviceDepensier;

    private MinistereDTO ministere;

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public ServiceDepensierDTO getServiceDepensier() {
        return serviceDepensier;
    }

    public MinistereDTO getMinistere() {
        return ministere;
    }

    public void setMinistere(MinistereDTO ministere) {
        this.ministere = ministere;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setServiceDepensier(ServiceDepensierDTO serviceDepensier) {
        this.serviceDepensier = serviceDepensier;
    }

}
