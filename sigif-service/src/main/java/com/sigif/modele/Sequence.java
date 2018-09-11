package com.sigif.modele;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entité représentant la table séquence.
 */
@Entity
@Table(name = "sequence")
public class Sequence implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8821382147712365079L;
    
    /**
     * Id de l'entité.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Récupère l'id du modèle.
     * 
     * @return id du modèle
     */
    public Long getId() {
        return id;
    }

    /**
     * Met à jour l'id du modèle.
     * 
     * @param id
     *            id du modèle
     */
    public void setId(Long id) {
        this.id = id;
    }

}
