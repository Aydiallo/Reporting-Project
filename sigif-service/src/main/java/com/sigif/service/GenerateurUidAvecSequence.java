package com.sigif.service;

/**
 * Service de génération du numero de teledossier.
 * 
 * @author Malick Diagne
 */
public interface GenerateurUidAvecSequence {

    /**
     * Génère un UID de télédossier.
     * 
     * @return un UID de télédossier
     */
    String generateTeledossierUid();

    /**
     * Génère un numéro aléatoire.
     * 
     * @return un numéro aléatoire
     */
    String generateRandomNumber();
}
