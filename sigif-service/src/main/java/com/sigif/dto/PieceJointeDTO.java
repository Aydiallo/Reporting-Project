package com.sigif.dto;

import java.io.Serializable;

import com.sigif.enumeration.TypePJ;

/**
 * DTO de la table pièce jointe
 *
 */

public class PieceJointeDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = -3890882453340759738L;

    private String idPieceJointe;

    private String emplacement;

    private String intitule;

    private String nature;

    private String nomFichier;

    private TypePJ type;

    public String getIdPieceJointe() {
        return idPieceJointe;
    }

    public void setIdPieceJointe(String idPieceJointe) {
        this.idPieceJointe = idPieceJointe;
    }
    
    public String getEmplacement() {
        return this.emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getIntitule() {
        return this.intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getNature() {
        return this.nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getNomFichier() {
        return this.nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public TypePJ getType() {
        return this.type;
    }

    public void setType(TypePJ type) {
        this.type = type;
    }
}