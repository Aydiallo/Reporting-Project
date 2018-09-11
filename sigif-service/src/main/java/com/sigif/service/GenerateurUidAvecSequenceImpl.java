package com.sigif.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.util.GenerationUtil;


/**
 * @author Malick Diagne
 * 
 *         Cette classe surcharge la methode generateTeledossierUID pour
 *         utiliser le service SequenceInterface
 */
@Service("generateurUidAvecSequence")
@Transactional
public class GenerateurUidAvecSequenceImpl implements GenerateurUidAvecSequence {
    /** LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateurUidAvecSequenceImpl.class);

    /**
     * Service de generation de sequence.
     */
    @Autowired
    private SequenceServiceInterface sequenceService;

    /**
     * pointeur sur l'element de la configuration.
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yy");

    /**
     * Génère un UID de télédossier.
     * 
     * @return un UID de télédossier
     */
    public String generateTeledossierUid() {
        String uid = null;

        if (null != getSequenceService()) {
            try {
                uid = "S" + "-";
                synchronized (DATE_FORMAT) {
                    uid += DATE_FORMAT.format(new Date()).substring(1) + "-";
                }
                uid += GenerationUtil.textGenerator(getSequenceService().getNumero());

            } catch (Exception e) {
                LOGGER.error("Erreur lors de la génération du numéro de télédossier", e);
            }
        }

        return uid;
    }

    /**
     * Génère un numéro aléatoire.
     * 
     * @return un numéro aléatoire
     */
    public String generateRandomNumber() {
        String uid = null;

        if (null != getSequenceService()) {
            try {
                uid += GenerationUtil.textGenerator(getSequenceService().getNumero());

            } catch (Exception e) {
                LOGGER.error("Erreur lors de la génération du numéro aléatoire pour le numéro de télédossier", e);
            }
        }

        return uid;
    }

    /**
     * Renvoie sequenceService.
     * 
     * @return Le sequenceService
     */
    public SequenceServiceInterface getSequenceService() {
        return sequenceService;
    }

}
