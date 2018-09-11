package com.sigif.dao;

import java.util.List;

import com.sigif.exception.TechnicalException;
import com.sigif.modele.Programme;

/**
 * Classe d'accès aux données des programmes.
 * @author Mamadou Ndir
 */
public interface ProgrammeDAO extends AbstractDAO<Programme> {

    /**
     * Permet de récupérer le ou les programme(s) lié au ministére(s) dont le
     * code est donné en paramétre.
     * 
     * @author Mamadou Ndir
     * @param ministery
     *            code du ministère
     * @since 22/05/2017 11:29
     * @return la liste des programmes du ministère
     * @throws TechnicalException Si l'accès aux données de la BD a échoué
     */
    List<Programme> getAllProgrammOfMinistery(String ministery)
            throws TechnicalException;

    /**
     * Retourne le programme avec le code fourni en parametre.
     * 
     * @author Meissa
     * @param codePrg code du programme
     * @return le programme correspondant au code
     * @throws TechnicalException Si l'accès aux données de la BD a échoué
     */
    Programme getProgrammeByCode(String codePrg) throws TechnicalException;
}
