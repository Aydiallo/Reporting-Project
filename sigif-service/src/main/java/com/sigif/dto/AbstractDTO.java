package com.sigif.dto;

/**
 * DTO de base.
 */
public abstract class AbstractDTO {

    /**
     * Id du DTO.
     */
    private int id;

    /**
     * Constructeur vide.
     */
    public AbstractDTO() {
    }

    /**
     * Récupère l'id du DTO.
     * @return l'id du DTO
     */
    public int getId() {
        return id;
    }

    /**
     * Affecte l'id du DTO.
     * @param id Id du DTO
     */
    public void setId(int id) {
        this.id = id;
    }
}
