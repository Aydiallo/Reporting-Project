package com.sigif.modele;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Classe de base des entités.
 */
@MappedSuperclass
public abstract class AbstractModele {

    /**
     * Id de l'entité.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Constructeur.
     */
    public AbstractModele() {
    }

    /**
     * Récupère l'id du modèle.
     * @return id du modèle
     */
    public int getId() {
        return id;
    }

    /**
     * Met à jour l'id du modèle.
     * @param id id du modèle
     */
    public void setId(int id) {
        this.id = id;
    }

}