package com.sigif.service.csf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.apache.commons.io.FileUtils;
import org.dbunit.DatabaseUnitException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.enumeration.CsfUpdatableStatus;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.service.ConstatationServiceFaitService;
import com.sigif.testutil.AbstractDbTestCase;
import com.sigif.util.Constantes;
import com.sigif.util.StringToDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class ConstatationServiceFaitServiceTest extends AbstractDbTestCase {

    @Autowired
    ConstatationServiceFaitService csfService;

    @Test
    public void isCSFDeletableTest() throws ApplicationException, ConfigurationException, DatabaseUnitException,
            SQLException, FileNotFoundException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CsfLockService.xml");
        try {
            // beaupoil n'est pas le créateur de la CSF
            assertFalse(csfService.isCSFDeletable("beaupoil", "S-7-B8LPV"));

            // beye est le créateur de la CSF et il a les droits sur un service dépensier
            assertTrue(csfService.isCSFDeletable("beye", "S-7-B8LPV"));

            // beye est le créateur de la CSF mais il n'a pas les droits sur un service dépensier
            assertFalse(csfService.isCSFDeletable("beye", "S-7-XXXXX"));

            // la CSF est en mode Brouillon mais a un poste Rectifié
            assertFalse(csfService.isCSFDeletable("beye", "S-7-YYYYY"));

            // le traitement de la CSF est en cours mais il a un poste refusé
            assertFalse(csfService.isCSFDeletable("beye", "S-7-ZZZZZ"));
            
            // le traitement de la CSF est en cours et il n'a pas de poste refusé
            assertFalse(csfService.isCSFDeletable("beye", "S-7-AAAAA"));
            
            // le traitement de la CSF est en cours et il n'a que des postes certifié ou en constatation annulée
            assertFalse(csfService.isCSFDeletable("beye", "S-7-BBBBB"));
            
            // le traitement de la CSF est Brouillon mais a des postes déjà traités
            assertFalse(csfService.isCSFDeletable("beye", "S-7-CCCCC"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void isCSFUpdatableTest() throws ApplicationException, ConfigurationException, DatabaseUnitException,
            SQLException, FileNotFoundException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CsfLockService.xml");
        try {
            // beaupoil n'est pas le créateur de la CSF
            assertEquals(CsfUpdatableStatus.NO, csfService.isCSFUpdatable("beaupoil", "S-7-B8LPV"));

            // beye est le créateur de la CSF et il a les droits sur un service dépensier
            assertEquals(CsfUpdatableStatus.FULL, csfService.isCSFUpdatable("beye", "S-7-B8LPV"));
            
            // beye est le créateur de la CSF et il a les droits sur un service dépensier et il n'y a pas de postes
            assertEquals(CsfUpdatableStatus.FULL, csfService.isCSFUpdatable("beye", "S-7-B8LPL"));

            // beye est le créateur de la CSF mais il n'a pas les droits sur un service dépensier
            assertEquals(CsfUpdatableStatus.NO, csfService.isCSFUpdatable("beye", "S-7-XXXXX"));

            // la CSF est en mode Brouillon mais a un poste Rectifié
            assertEquals(CsfUpdatableStatus.CORRECTION_ALLOWED, csfService.isCSFUpdatable("beye", "S-7-YYYYY"));

            // le traitement de la CSF est en cours mais il a un poste refusé
            assertEquals(CsfUpdatableStatus.CORRECTION_ALLOWED, csfService.isCSFUpdatable("beye", "S-7-ZZZZZ"));

            // le traitement de la CSF est en cours et il n'a pas de poste refusé
            assertEquals(CsfUpdatableStatus.NO, csfService.isCSFUpdatable("beye", "S-7-AAAAA"));
            // le traitement de la CSF est en cours et il n'a que des postes certifié ou en constatation annulée
            assertEquals(CsfUpdatableStatus.NO, csfService.isCSFUpdatable("beye", "S-7-BBBBB"));
            
            // le traitement de la CSF est Brouillon mais a un poste en erreur
            assertEquals(CsfUpdatableStatus.CORRECTION_ALLOWED, csfService.isCSFUpdatable("beye", "S-7-CCCCC"));

            System.out.println("isCSFUpdatableTest OK");
        } catch (Exception e) {
            System.out.println("isCSFUpdatableTest KO");
            fail(e.getMessage());
        }
    }

    @Test
    public void getInfosCSFTest() throws ApplicationException, ConfigurationException, DatabaseUnitException,
            SQLException, FileNotFoundException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/InfosCSFService.xml");
        try {
            Map<String, String> infosCSF = csfService.getInfosCSF("S-7-B8LPV");

            assertEquals(9, infosCSF.size());
            assertEquals(infosCSF.get(Constantes.KEY_MAP_NUMERO_CSF), "S-7-B8LPV");
            assertEquals(infosCSF.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF),
                    Constantes.STATUT_ACCEPTATION_ACCEPTEE_PART);
            assertEquals(infosCSF.get(Constantes.KEY_MAP_RECEPTIONNAIRE), "Meissa BEYE");
            assertEquals(infosCSF.get(Constantes.KEY_MAP_STATUT_AVANCEMENT), "Brouillon");
            assertEquals(infosCSF.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF), "Certification partielle");
            assertEquals(infosCSF.get(Constantes.KEY_MAP_COMMENTAIRE), "CoCommentaire");
            System.out.println("getInfosCSFTest OK");
        } catch (Exception e) {
            System.out.println("getInfosCSFTest KO");
            fail(e.getMessage());
        }
    }

    @Test
    public void countNbCsfTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException,
            SQLException, TechnicalException, ApplicationException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CsfSearch.xml");
        try {
            int rst = csfService.countNbCSF("beaupoil", null, null, null, null, null, null, null, null);
            // id = 1 à 15
            assertEquals(15, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, null, null, null, null, null);
            // id = 2 à 15
            assertEquals(14, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", "02/06/2017", null, null, null, null, null, null);
            // id = 8
            assertEquals(1, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, "02/06/2017", null, null, null, null, null);
            // id = 2 à 7 et 9 à 15
            assertEquals(13, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, "En attente d'envoi", null, null, null, null);
            // id = 6 et 7
            assertEquals(2, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, "Envoyée à l'ordonnateur", null, null, null,
                    null);
            // id = 5
            assertEquals(1, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, "En attente de traitement", null, null, null,
                    null);
            // id = 4
            assertEquals(1, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, "Traitement en cours", null, null, null,
                    null);
            // id = 3
            assertEquals(1, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, "Traitement terminé", null, null, null, null);
            // id = 2
            assertEquals(1, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, null, "Constatation annulée", null, null,
                    null);
            // id = 9
            assertEquals(1, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, null, "Certification partielle", null, null,
                    null);
            // id = 10
            assertEquals(1, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, null, "Validation refusée", null, null, null);
            // id = 11
            assertEquals(1, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, null, null, null, "CSF005", null);
            // id = 5
            assertEquals(1, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, null, null, null, null, "9210000014");
            // id = 14 et 15
            assertEquals(2, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, null, null, "Acceptée", null, null);
            // id = 3-5-10-12
            assertEquals(4, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, null, null, "Partiellement acceptée", null,
                    null);
            // id = 2-4-9
            assertEquals(3, rst);
            rst = csfService.countNbCSF("beaupoil", "alardo", null, null, null, null, "Refusée", null, null);
            // id = 6-7-8-11
            assertEquals(4, rst);

        } catch (ApplicationException | TechnicalException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void searchCsfTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException,
            SQLException, TechnicalException, ApplicationException {

        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CsfSearch.xml");
        try {
            Map<String, Object[]> rst =
                    csfService.searchCSFByCriteria("beaupoil", null, null, null, null, null, null, null, null, 100);
            // id = 1 à 15
            Object[] numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            Object[] dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            Object[] receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            Object[] statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            Object[] statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            Object[] statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            Object[] expnumDossierCsf = { "CSF008", "CSF001", "CSF002", "CSF003", "CSF004", "CSF005", "CSF006",
                    "CSF007", "CSF009", "CSF010", "CSF011", "CSF012", "CSF013", "CSF014", "CSF015", };
            Object[] expdateReception = { "18/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017",
                    "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017",
                    "01/06/2017", "01/06/2017", "01/06/2017", };
            Object[] expreceptionnaire = { "Catherine ALARDO", "Angélique REYNAUD", "Catherine ALARDO",
                    "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO",
                    "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO",
                    "Catherine ALARDO", "Catherine ALARDO", };
            Object[] expstatutAvancement = { "Brouillon", "Brouillon", "Traitement terminé", "Traitement en cours",
                    "En attente de traitement", "Envoyée à l'ordonnateur", "En attente d'envoi", "En attente d'envoi",
                    "Brouillon", "Brouillon", "Brouillon", "Brouillon", "Brouillon", "Brouillon", "Brouillon", };
            Object[] expstatutValidation = { null, null, null, null, null, null, null, null, "Constatation annulée",
                    "Certification partielle", "Validation refusée", null, null, null, null, };
            Object[] expstatutAcceptation = { "Refusée", "Partiellement acceptée", "Partiellement acceptée", "Acceptée",
                    "Partiellement acceptée", "Acceptée", "Refusée", "Refusée", "Partiellement acceptée", "Acceptée",
                    "Refusée", "Acceptée", null, null, null, };

            Assert.assertArrayEquals(expnumDossierCsf, numDossierCsf);
            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expdateReception, dateReception);
            Assert.assertArrayEquals(expreceptionnaire, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", null, null, null, null, null, null, null, null, 3);
            // id = 8, 1 et 2 (trié par date de réception)
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf1 = { "CSF008", "CSF001", "CSF002", };
            String[] expdateReception1 = { "18/06/2017", "01/06/2017", "01/06/2017", };
            String[] expreceptionnaire1 = { "Catherine ALARDO", "Angélique REYNAUD", "Catherine ALARDO", };
            String[] expstatutAvancement1 = { "Brouillon", "Brouillon", "Traitement terminé", };
            String[] expstatutValidation1 = { null, null, null, };
            String[] expstatutAcceptation1 = { "Refusée", "Partiellement acceptée", "Partiellement acceptée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf1, numDossierCsf);
            Assert.assertArrayEquals(expdateReception1, dateReception);
            Assert.assertArrayEquals(expreceptionnaire1, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement1, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation1, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation1, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, null, null, null, null, null, 100);
            // id = 2 à 15
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf2 = { "CSF008", "CSF002", "CSF003", "CSF004", "CSF005", "CSF006", "CSF007",
                    "CSF009", "CSF010", "CSF011", "CSF012", "CSF013", "CSF014", "CSF015", };
            String[] expdateReception2 = { "18/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017",
                    "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017",
                    "01/06/2017", "01/06/2017", };
            String[] expreceptionnaire2 = { "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO",
                    "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO",
                    "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO",
                    "Catherine ALARDO", };
            String[] expstatutAvancement2 = { "Brouillon", "Traitement terminé", "Traitement en cours",
                    "En attente de traitement", "Envoyée à l'ordonnateur", "En attente d'envoi", "En attente d'envoi",
                    "Brouillon", "Brouillon", "Brouillon", "Brouillon", "Brouillon", "Brouillon", "Brouillon", };
            String[] expstatutValidation2 = { null, null, null, null, null, null, null, "Constatation annulée",
                    "Certification partielle", "Validation refusée", null, null, null, null, };
            String[] expstatutAcceptation2 =
                    { "Refusée", "Partiellement acceptée", "Acceptée", "Partiellement acceptée", "Acceptée", "Refusée",
                            "Refusée", "Partiellement acceptée", "Acceptée", "Refusée", "Acceptée", null, null, null, };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf2, numDossierCsf);
            Assert.assertArrayEquals(expdateReception2, dateReception);
            Assert.assertArrayEquals(expreceptionnaire2, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement2, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation2, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation2, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", "02/06/2017", null, null, null, null, null, null,
                    100);
            // id = 8
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf3 = { "CSF008", };
            String[] expdateReception3 = { "18/06/2017", };
            String[] expreceptionnaire3 = { "Catherine ALARDO", };
            String[] expstatutAvancement3 = { "Brouillon", };
            String[] expstatutValidation3 = { null, };
            String[] expstatutAcceptation3 = { "Refusée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf3, numDossierCsf);
            Assert.assertArrayEquals(expdateReception3, dateReception);
            Assert.assertArrayEquals(expreceptionnaire3, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement3, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation3, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation3, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, "02/06/2017", null, null, null, null, null,
                    100);
            // id = 2 à 7 et 9 à 15
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf4 = { "CSF002", "CSF003", "CSF004", "CSF005", "CSF006", "CSF007", "CSF009",
                    "CSF010", "CSF011", "CSF012", "CSF013", "CSF014", "CSF015", };
            String[] expdateReception4 =
                    { "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017",
                            "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", };
            String[] expreceptionnaire4 = { "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO",
                    "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO",
                    "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO",
                    "Catherine ALARDO", };
            String[] expstatutAvancement4 = { "Traitement terminé", "Traitement en cours", "En attente de traitement",
                    "Envoyée à l'ordonnateur", "En attente d'envoi", "En attente d'envoi", "Brouillon", "Brouillon",
                    "Brouillon", "Brouillon", "Brouillon", "Brouillon", "Brouillon", };
            String[] expstatutValidation4 = { null, null, null, null, null, null, "Constatation annulée",
                    "Certification partielle", "Validation refusée", null, null, null, null, };
            String[] expstatutAcceptation4 =
                    { "Partiellement acceptée", "Acceptée", "Partiellement acceptée", "Acceptée", "Refusée", "Refusée",
                            "Partiellement acceptée", "Acceptée", "Refusée", "Acceptée", null, null, null, };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf4, numDossierCsf);
            Assert.assertArrayEquals(expdateReception4, dateReception);
            Assert.assertArrayEquals(expreceptionnaire4, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement4, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation4, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation4, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, "En attente d'envoi", null, null,
                    null, null, 100);
            // id = 6 et 7
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf5 = { "CSF006", "CSF007", };
            String[] expdateReception5 = { "01/06/2017", "01/06/2017", };
            String[] expreceptionnaire5 = { "Catherine ALARDO", "Catherine ALARDO", };
            String[] expstatutAvancement5 = { "En attente d'envoi", "En attente d'envoi", };
            String[] expstatutValidation5 = { null, null, };
            String[] expstatutAcceptation5 = { "Refusée", "Refusée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf5, numDossierCsf);
            Assert.assertArrayEquals(expdateReception5, dateReception);
            Assert.assertArrayEquals(expreceptionnaire5, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement5, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation5, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation5, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, "Envoyée à l'ordonnateur", null,
                    null, null, null, 100);
            // id = 5
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf6 = { "CSF005", };
            String[] expdateReception6 = { "01/06/2017", };
            String[] expreceptionnaire6 = { "Catherine ALARDO", };
            String[] expstatutAvancement6 = { "Envoyée à l'ordonnateur", };
            String[] expstatutValidation6 = { null, };
            String[] expstatutAcceptation6 = { "Acceptée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf6, numDossierCsf);
            Assert.assertArrayEquals(expdateReception6, dateReception);
            Assert.assertArrayEquals(expreceptionnaire6, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement6, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation6, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation6, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, "En attente de traitement", null,
                    null, null, null, 100);
            // id = 4
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf7 = { "CSF004", };
            String[] expdateReception7 = { "01/06/2017", };
            String[] expreceptionnaire7 = { "Catherine ALARDO", };
            String[] expstatutAvancement7 = { "En attente de traitement", };
            String[] expstatutValidation7 = { null, };
            String[] expstatutAcceptation7 = { "Partiellement acceptée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf7, numDossierCsf);
            Assert.assertArrayEquals(expdateReception7, dateReception);
            Assert.assertArrayEquals(expreceptionnaire7, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement7, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation7, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation7, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, "Traitement en cours", null, null,
                    null, null, 100);
            // id = 3
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf8 = { "CSF003", };
            String[] expdateReception8 = { "01/06/2017", };
            String[] expreceptionnaire8 = { "Catherine ALARDO", };
            String[] expstatutAvancement8 = { "Traitement en cours", };
            String[] expstatutValidation8 = { null, };
            String[] expstatutAcceptation8 = { "Acceptée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf8, numDossierCsf);
            Assert.assertArrayEquals(expdateReception8, dateReception);
            Assert.assertArrayEquals(expreceptionnaire8, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement8, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation8, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation8, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, "Traitement terminé", null, null,
                    null, null, 100);
            // id = 2
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf9 = { "CSF002", };
            String[] expdateReception9 = { "01/06/2017", };
            String[] expreceptionnaire9 = { "Catherine ALARDO", };
            String[] expstatutAvancement9 = { "Traitement terminé", };
            String[] expstatutValidation9 = { null, };
            String[] expstatutAcceptation9 = { "Partiellement acceptée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf9, numDossierCsf);
            Assert.assertArrayEquals(expdateReception9, dateReception);
            Assert.assertArrayEquals(expreceptionnaire9, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement9, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation9, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation9, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, null, "Constatation annulée", null,
                    null, null, 100);
            // id = 9
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf10 = { "CSF009", };
            String[] expdateReception10 = { "01/06/2017", };
            String[] expreceptionnaire10 = { "Catherine ALARDO", };
            String[] expstatutAvancement10 = { "Brouillon", };
            String[] expstatutValidatio10 = { "Constatation annulée", };
            String[] expstatutAcceptation10 = { "Partiellement acceptée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf10, numDossierCsf);
            Assert.assertArrayEquals(expdateReception10, dateReception);
            Assert.assertArrayEquals(expreceptionnaire10, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement10, statutAvancement);
            Assert.assertArrayEquals(expstatutValidatio10, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation10, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, null, "Certification partielle",
                    null, null, null, 100);
            // id = 10
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf11 = { "CSF010", };
            String[] expdateReception11 = { "01/06/2017", };
            String[] expreceptionnaire11 = { "Catherine ALARDO", };
            String[] expstatutAvancement11 = { "Brouillon", };
            String[] expstatutValidation11 = { "Certification partielle", };
            String[] expstatutAcceptation11 = { "Acceptée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf11, numDossierCsf);
            Assert.assertArrayEquals(expdateReception11, dateReception);
            Assert.assertArrayEquals(expreceptionnaire11, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement11, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation11, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation11, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, null, "Validation refusée", null,
                    null, null, 100);
            // id = 11
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf12 = { "CSF011", };
            String[] expdateReception12 = { "01/06/2017", };
            String[] expreceptionnaire12 = { "Catherine ALARDO", };
            String[] expstatutAvancement12 = { "Brouillon", };
            String[] expstatutValidation12 = { "Validation refusée", };
            String[] expstatutAcceptation12 = { "Refusée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf12, numDossierCsf);
            Assert.assertArrayEquals(expdateReception12, dateReception);
            Assert.assertArrayEquals(expreceptionnaire12, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement12, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation12, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation12, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, null, null, null, "CSF005", null,
                    100);
            // id = 5
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf13 = { "CSF005", };
            String[] expdateReception13 = { "01/06/2017", };
            String[] expreceptionnaire13 = { "Catherine ALARDO", };
            String[] expstatutAvancement13 = { "Envoyée à l'ordonnateur", };
            String[] expstatutValidation13 = { null, };
            String[] expstatutAcceptation13 = { "Acceptée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf13, numDossierCsf);
            Assert.assertArrayEquals(expdateReception13, dateReception);
            Assert.assertArrayEquals(expreceptionnaire13, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement13, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation13, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation13, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, null, null, null, null, "9210000014",
                    100);
            // id = 14 et 15
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf14 = { "CSF014", "CSF015", };
            String[] expdateReception14 = { "01/06/2017", "01/06/2017", };
            String[] expreceptionnaire14 = { "Catherine ALARDO", "Catherine ALARDO", };
            String[] expstatutAvancement14 = { "Brouillon", "Brouillon", };
            String[] expstatutValidation14 = { null, null, };
            String[] expstatutAcceptation14 = { null, null, };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf14, numDossierCsf);
            Assert.assertArrayEquals(expdateReception14, dateReception);
            Assert.assertArrayEquals(expreceptionnaire14, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement14, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation14, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation14, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, null, null, "Acceptée", null, null,
                    100);
            // id = 3-5-10-12
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf15 = { "CSF003", "CSF005", "CSF010", "CSF012", };
            String[] expdateReception15 = { "01/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", };
            String[] expreceptionnaire15 =
                    { "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", };
            String[] expstatutAvancement15 =
                    { "Traitement en cours", "Envoyée à l'ordonnateur", "Brouillon", "Brouillon", };
            String[] expstatutValidation15 = { null, null, "Certification partielle", null, };
            String[] expstatutAcceptation15 = { "Acceptée", "Acceptée", "Acceptée", "Acceptée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf15, numDossierCsf);
            Assert.assertArrayEquals(expdateReception15, dateReception);
            Assert.assertArrayEquals(expreceptionnaire15, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement15, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation15, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation15, statutAcceptation);

            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, null, null, "Partiellement acceptée",
                    null, null, 100);
            // id = 2-4-9
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf16 = { "CSF002", "CSF004", "CSF009", };
            String[] expdateReception16 = { "01/06/2017", "01/06/2017", "01/06/2017", };
            String[] expreceptionnaire16 = { "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", };
            String[] expstatutAvancement16 = { "Traitement terminé", "En attente de traitement", "Brouillon", };
            String[] expstatutValidation16 = { null, null, "Constatation annulée", };
            String[] expstatutAcceptation16 =
                    { "Partiellement acceptée", "Partiellement acceptée", "Partiellement acceptée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf16, numDossierCsf);
            Assert.assertArrayEquals(expdateReception16, dateReception);
            Assert.assertArrayEquals(expreceptionnaire16, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement16, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation16, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation16, statutAcceptation);
            rst = csfService.searchCSFByCriteria("beaupoil", "alardo", null, null, null, null, "Refusée", null, null,
                    100);
            // id = 6-7-8-11
            numDossierCsf = rst.get(Constantes.KEY_MAP_NUMERO_CSF);
            dateReception = rst.get(Constantes.KEY_MAP_DATE_RECEPTION);
            receptionnaire = rst.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
            statutAvancement = rst.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            statutValidation = rst.get(Constantes.KEY_MAP_STATUT_VALIDATION_CSF);
            statutAcceptation = rst.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String[] expnumDossierCsf17 = { "CSF008", "CSF006", "CSF007", "CSF011", };
            String[] expdateReception17 = { "18/06/2017", "01/06/2017", "01/06/2017", "01/06/2017", };
            String[] expreceptionnaire17 =
                    { "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", "Catherine ALARDO", };
            String[] expstatutAvancement17 = { "Brouillon", "En attente d'envoi", "En attente d'envoi", "Brouillon", };
            String[] expstatutValidation17 = { null, null, null, "Validation refusée", };
            String[] expstatutAcceptation17 = { "Refusée", "Refusée", "Refusée", "Refusée", };

            for (int i = 0; i< dateReception.length; i++) {
                dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
            }
            Assert.assertArrayEquals(expnumDossierCsf17, numDossierCsf);
            Assert.assertArrayEquals(expdateReception17, dateReception);
            Assert.assertArrayEquals(expreceptionnaire17, receptionnaire);
            Assert.assertArrayEquals(expstatutAvancement17, statutAvancement);
            Assert.assertArrayEquals(expstatutValidation17, statutValidation);
            Assert.assertArrayEquals(expstatutAcceptation17, statutAcceptation);

        } catch (ApplicationException | TechnicalException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    @Rollback(false)
    public void deleteCSFTest() throws ApplicationException, ConfigurationException, DatabaseUnitException,
            SQLException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CSFDeleteService.xml");
        File f1 = new File("C:/tmp/PJ/CSF/S-7-B8LPV/S-7-B8LPV_1.jpg");
        if (!f1.isFile()) {
            f1.getParentFile().mkdirs();
            f1.createNewFile();
        }
        File f2 = new File("C:/tmp/PJ/CSF/S-7-B8LPV/S-7-B8LPV_2.jpg");
        f2.createNewFile();
        File f3 = new File("C:/tmp/PJ/CSF/S-7-B8LPV/S-7-B8LPV_3.jpg");
        f3.createNewFile();
        try {
            boolean result = csfService.deleteCSF("S-7-B8LPV", "beye");
            assertEquals(true, result);
            assertEquals("0",
                    this.getDataFromTable(
                            "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait WHERE numeroDossier='S-7-B8LPV'",
                            "nbCsf"));
            assertEquals("0",
                    this.getDataFromTable(
                            "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait WHERE idCSF=1",
                            "nbPCsf"));
            assertEquals("0",
                    this.getDataFromTable(
                            "Select Cast(count(1) as char) As nbPJ FROM piece_jointe WHERE idCsf=1 OR idPosteCsf=2",
                            "nbPJ"));

            assertFalse(f1.isFile());
            assertFalse(f2.isFile());
            assertFalse(f3.isFile());

            assertEquals("Non réceptionné",
                    this.getDataFromTable(
                            "Select statut FROM poste_commande_achat WHERE id=23",
                            "statut"));
            assertEquals("39",
                    this.getDataFromTable(
                            "Select Cast(quantiteRestante as CHAR) as qte FROM poste_commande_achat WHERE id=23",
                            "qte"));

            assertEquals("Partiellement réceptionnée",
                    this.getDataFromTable(
                            "Select statut FROM commande_achat WHERE id=22",
                            "statut"));

            result = csfService.deleteCSF("S-7-XXXXX", "beye");
            assertTrue(result);
            result = csfService.deleteCSF("S-7-YYYYY", "beye");
            assertFalse(result);
            result = csfService.deleteCSF("S-7-ZZZZZ", "beye");
            assertFalse(result);
            result = csfService.deleteCSF("S-7-AAAAA", "beye");
            assertFalse(result);
            
            System.out.println("deleteCSFTest OK");
        } catch (Exception e) {
            System.out.println("deleteCSFTest KO");
            fail(e.getMessage());
        }
    }

    @Test
    @Rollback(false)
    public void saveCsfWithoutPjAndWithoutPosteExceptions()
            throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CSFSave.xml");
        Date receptionDate = null;
        try {
            receptionDate = StringToDate.convertStringToDate("01/07/2017 12:39:39");
        } catch (ApplicationException e) {
        }
        try {
            csfService.saveCSF("beaupoil", "   ", receptionDate, null, "9210000002", "Commenterreux", "Brouillon", null,
                    null, null);
        } catch (ApplicationException e) {
            // Vérification du non enregistrement
            assertEquals("0", this.getDataFromTable(
                    "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait", "nbCsf"));
            assertEquals("0", this.getDataFromTable(
                    "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));
        } catch (TechnicalException e) {
            fail(e.getMessage());
        }
        try {
            csfService.saveCSF("", "SAVE_CSF", receptionDate, null, "9210000002", "Commenterreux", "Brouillon", null,
                    null, null);
        } catch (ApplicationException e) {
            // Vérification du non enregistrement
            assertEquals("0", this.getDataFromTable(
                    "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait", "nbCsf"));
            assertEquals("0", this.getDataFromTable(
                    "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));
        } catch (TechnicalException e) {
            fail(e.getMessage());
        }
        try {
            csfService.saveCSF("beaupoil", "SAVE_CSF", null, null, "9210000002", "Commenterreux", "Brouillon", null,
                    null, null);
        } catch (ApplicationException e) {
            // Vérification du non enregistrement
            assertEquals("0", this.getDataFromTable(
                    "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait", "nbCsf"));
            assertEquals("0", this.getDataFromTable(
                    "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));
        } catch (TechnicalException e) {
            fail(e.getMessage());
        }
        try {
            csfService.saveCSF("beaupoil", "SAVE_CSF", receptionDate, null, null, "Commenterreux", "Brouillon", null,
                    null, null);
        } catch (ApplicationException e) {
            // Vérification du non enregistrement
            assertEquals("0", this.getDataFromTable(
                    "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait", "nbCsf"));
            assertEquals("0", this.getDataFromTable(
                    "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));
        } catch (TechnicalException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Rollback(false)
    public void saveCsfWithoutPjAndWithoutPoste() throws FileNotFoundException, ConfigurationException,
            DatabaseUnitException, SQLException, ApplicationException, TechnicalException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CSFSave.xml");
        Date receptionDate = StringToDate.convertStringToDate("01/07/2017 12:39:39");
        boolean result = csfService.saveCSF("beaupoil", "SAVE_CSF", receptionDate, "12200820100", "9210000002",
                "Commenterreux", "Brouillon", null, null, null);
        assertTrue(result);
        assertEquals("1", this.getDataFromTable("Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait",
                "nbCsf"));
        assertEquals("1",
                this.getDataFromTable(
                        "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait WHERE numeroDossier='SAVE_CSF'",
                        "nbCsf"));
        assertEquals("0", this.getDataFromTable(
                "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));

        assertEquals("Partiellement réceptionnée",
                this.getDataFromTable(
                        "Select statut FROM commande_achat WHERE id=14",
                        "statut"));
    }

    @Test
    @Rollback(false)
    public void saveCsfWithoutPjAndWithPoste() throws ConfigurationException, DatabaseUnitException, SQLException,
            ApplicationException, TechnicalException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CSFSave.xml");
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\CSF"));
        Date receptionDate = StringToDate.convertStringToDate("01/07/2017 12:39:39");
        List<Map<String, String>> listPostesCsfMap = new ArrayList<Map<String, String>>();
        Map<String, String> poste1 = new HashMap<String, String>();
        poste1.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "01");
        poste1.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "02");
        poste1.put(Constantes.KEY_MAP_QTE_RECUE, "15");
        poste1.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "10");
        poste1.put(Constantes.KEY_MAP_COMMENTAIRE, "CommPoste");
        poste1.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste1.put(Constantes.KEY_MAP_LIEU_STOCKAGE, "44");
        poste1.put(Constantes.KEY_MAP_NATURE, "BL");
        poste1.put(Constantes.KEY_MAP_INTITULE, "Bon de livraison");
        String path = "C:" + File.separator + "tmp" + File.separator + "piece pcsf 1.jpg";
        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJ = "piece pcsf 1.jpg C%3A%5Ctmp%2Fpiece%20pcsf%201.jpg";
        poste1.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);

        Map<String, String> poste2 = new HashMap<String, String>();
        poste2.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "02");
        poste2.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "03");
        poste2.put(Constantes.KEY_MAP_QTE_RECUE, "1");
        poste2.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "1");
        poste2.put(Constantes.KEY_MAP_COMMENTAIRE, "CommPoste2");
        poste2.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste2.put(Constantes.KEY_MAP_NATURE, "Facture");
        poste2.put(Constantes.KEY_MAP_INTITULE, "Facture du poste");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pcsf 2.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJ = "piece pcsf 2.jpg C%3A%5Ctmp%2Fpiece%20pcsf%202.jpg";
        poste2.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);

        Map<String, String> poste3 = new HashMap<String, String>();
        poste3.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "03");
        poste3.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "01");
        poste3.put(Constantes.KEY_MAP_QTE_RECUE, "100");
        poste3.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "50");
        poste3.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste3.put(Constantes.KEY_MAP_LIEU_STOCKAGE, "44");

        listPostesCsfMap.add(poste1);
        listPostesCsfMap.add(poste2);
        listPostesCsfMap.add(poste3);

        final String numTeledossier = "SAVE_CSF";
        boolean result = csfService.saveCSF("beaupoil", numTeledossier, receptionDate, "12200820100", "9210000002",
                "Commenterreux", "Brouillon", null, null, listPostesCsfMap);
        assertTrue(result);
        assertEquals("1", this.getDataFromTable("Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait",
                "nbCsf"));
        assertEquals("1",
                this.getDataFromTable(
                        "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait WHERE numeroDossier='SAVE_CSF'",
                        "nbCsf"));
        assertEquals("3", this.getDataFromTable(
                "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));
        // Vérification de la copie des PJ
        String emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='01' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNotNull(emplacementPj);
        File pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());

        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='02' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());
        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='03' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNull(emplacementPj);
        

        assertEquals("Clôturé",
                this.getDataFromTable(
                        "Select statut FROM poste_commande_achat WHERE id=3",
                        "statut"));
        assertEquals("Partiellement réceptionné",
                this.getDataFromTable(
                        "Select statut FROM poste_commande_achat WHERE id=4",
                        "statut"));
        assertEquals("Clôturé",
                this.getDataFromTable(
                        "Select statut FROM poste_commande_achat WHERE id=5",
                        "statut"));
        assertEquals("490",
                this.getDataFromTable(
                        "Select Cast(quantiteRestante as CHAR) as qte FROM poste_commande_achat WHERE id=4",
                        "qte"));

        assertEquals("Partiellement réceptionnée",
                this.getDataFromTable(
                        "Select statut FROM commande_achat WHERE id=14",
                        "statut"));
    }

    @Test
    @Rollback(false)
    public void saveCsfWithPjAndWithoutPoste() throws ConfigurationException, DatabaseUnitException, SQLException,
            ApplicationException, TechnicalException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CSFSave.xml");
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\CSF"));
        Date receptionDate = StringToDate.convertStringToDate("01/07/2017 12:39:39");

        List<Map<String, String>> listAttachments = new ArrayList<Map<String, String>>();
        Map<String, String> pj1 = new HashMap<String, String>();
        pj1.put(Constantes.KEY_MAP_NATURE, "BL");
        pj1.put(Constantes.KEY_MAP_INTITULE, "Intitulé BL");
        String path = "C:" + File.separator + "tmp" + File.separator + "piece csf 1.jpg";
        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJ = "piece csf 1.jpg C%3A%5Ctmp%2Fpiece%20csf%201.jpg";
        pj1.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj1);

        Map<String, String> pj2 = new HashMap<String, String>();
        pj2.put(Constantes.KEY_MAP_NATURE, "Autre");
        pj2.put(Constantes.KEY_MAP_INTITULE, "Intitulé Autre");
        path = "C:" + File.separator + "tmp" + File.separator + "piece csf 2.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJ = "piece csf 2.jpg C%3A%5Ctmp%2Fpiece%20csf%202.jpg";
        pj2.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj2);

        Map<String, String> pj3 = new HashMap<String, String>();
        pj3.put(Constantes.KEY_MAP_NATURE, "PV");
        pj3.put(Constantes.KEY_MAP_INTITULE, "Intitulé PV");
        path = "C:" + File.separator + "tmp" + File.separator + "piece csf 3.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJ = "piece csf 3.jpg C%3A%5Ctmp%2Fpiece%20csf%203.jpg";
        pj3.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj3);

        final String numTeledossier = "SAVE_CSF";
        boolean result = csfService.saveCSF("beaupoil", numTeledossier, receptionDate, "12200820100", "9210000002",
                "Commenterreux", "Brouillon", null, listAttachments, null);
        assertTrue(result);
        assertEquals("1", this.getDataFromTable("Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait",
                "nbCsf"));
        assertEquals("1",
                this.getDataFromTable(
                        "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait WHERE numeroDossier='SAVE_CSF'",
                        "nbCsf"));
        assertEquals("0", this.getDataFromTable(
                "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));

        // Vérification de la copie des PJ
        String emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='BL'",
                "emplacement");
        assertNotNull(emplacementPj);
        File pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());

        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='Autre'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());
        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='PV'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());
    }

    @Test
    @Rollback(false)
    public void saveCsfWithPjAndWithPoste() throws ConfigurationException, DatabaseUnitException, SQLException,
            ApplicationException, TechnicalException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CSFSave.xml");
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\CSF"));
        Date receptionDate = StringToDate.convertStringToDate("01/07/2017 12:39:39");

        List<Map<String, String>> listAttachments = new ArrayList<Map<String, String>>();
        Map<String, String> pj1 = new HashMap<String, String>();
        pj1.put(Constantes.KEY_MAP_NATURE, "BL");
        pj1.put(Constantes.KEY_MAP_INTITULE, "Intitulé BL");
        String path = "C:" + File.separator + "tmp" + File.separator + "piece csf 1.jpg";
        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJ = "piece csf 1.jpg C%3A%5Ctmp%2Fpiece%20csf%201.jpg";
        pj1.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj1);

        Map<String, String> pj2 = new HashMap<String, String>();
        pj2.put(Constantes.KEY_MAP_NATURE, "Autre");
        pj2.put(Constantes.KEY_MAP_INTITULE, "Intitulé Autre");
        path = "C:" + File.separator + "tmp" + File.separator + "piece csf 2.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJ = "piece csf 2.jpg C%3A%5Ctmp%2Fpiece%20csf%202.jpg";
        pj2.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj2);

        Map<String, String> pj3 = new HashMap<String, String>();
        pj3.put(Constantes.KEY_MAP_NATURE, "PV");
        pj3.put(Constantes.KEY_MAP_INTITULE, "Intitulé PV");
        path = "C:" + File.separator + "tmp" + File.separator + "piece csf 3.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJ = "piece csf 3.jpg C%3A%5Ctmp%2Fpiece%20csf%203.jpg";
        pj3.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj3);

        List<Map<String, String>> listPostesCsfMap = new ArrayList<Map<String, String>>();
        Map<String, String> poste1 = new HashMap<String, String>();
        poste1.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "01");
        poste1.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "02");
        poste1.put(Constantes.KEY_MAP_QTE_RECUE, "1500");
        poste1.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "500");
        poste1.put(Constantes.KEY_MAP_COMMENTAIRE, "CommPoste");
        poste1.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste1.put(Constantes.KEY_MAP_LIEU_STOCKAGE, "44");
        poste1.put(Constantes.KEY_MAP_NATURE, "BL");
        poste1.put(Constantes.KEY_MAP_INTITULE, "Bon de livraison");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pcsf 1.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJ = "piece pcsf 1.jpg C%3A%5Ctmp%2Fpiece%20pcsf%201.jpg";
        poste1.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);

        Map<String, String> poste2 = new HashMap<String, String>();
        poste2.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "02");
        poste2.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "03");
        poste2.put(Constantes.KEY_MAP_QTE_RECUE, "1");
        poste2.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "1");
        poste2.put(Constantes.KEY_MAP_COMMENTAIRE, "CommPoste2");
        poste2.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste2.put(Constantes.KEY_MAP_NATURE, "Facture");
        poste2.put(Constantes.KEY_MAP_INTITULE, "Facture du poste");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pcsf 2.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJ = "piece pcsf 2.jpg C%3A%5Ctmp%2Fpiece%20pcsf%202.jpg";
        poste2.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);

        Map<String, String> poste3 = new HashMap<String, String>();
        poste3.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "03");
        poste3.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "01");
        poste3.put(Constantes.KEY_MAP_QTE_RECUE, "150");
        poste3.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "50");
        poste3.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste3.put(Constantes.KEY_MAP_LIEU_STOCKAGE, "44");

        listPostesCsfMap.add(poste1);
        listPostesCsfMap.add(poste2);
        listPostesCsfMap.add(poste3);

        final String numTeledossier = "SAVE_CSF";
        boolean result = csfService.saveCSF("beaupoil", numTeledossier, receptionDate, "12200820100", "9210000002",
                "Commenterreux", "Brouillon", null, listAttachments, listPostesCsfMap);
        assertTrue(result);
        assertEquals("1", this.getDataFromTable("Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait",
                "nbCsf"));
        assertEquals("1",
                this.getDataFromTable(
                        "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait WHERE numeroDossier='SAVE_CSF'",
                        "nbCsf"));
        assertEquals("3", this.getDataFromTable(
                "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));

        // Vérification de la copie des PJ
        String emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='BL'",
                "emplacement");
        assertNotNull(emplacementPj);
        File pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());

        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='Autre'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());
        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='PV'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());

        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='01' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());

        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='02' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());
        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='03' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNull(emplacementPj);
        

        assertEquals("Clôturé",
                this.getDataFromTable(
                        "Select statut FROM poste_commande_achat WHERE id=3",
                        "statut"));
        assertEquals("Clôturé",
                this.getDataFromTable(
                        "Select statut FROM poste_commande_achat WHERE id=4",
                        "statut"));
        assertEquals("Clôturé",
                this.getDataFromTable(
                        "Select statut FROM poste_commande_achat WHERE id=5",
                        "statut"));

        assertEquals("Réceptionnée",
                this.getDataFromTable(
                        "Select statut FROM commande_achat WHERE id=14",
                        "statut"));
    }

    @Test
    @Rollback(false)
    public void updateCsfWithoutPjAndWithoutPosteExceptions()
            throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CSFUpdate.xml");
        try {
            csfService.saveCSF("beaupoil", "SAVE_CSF", null, "12200820100", "9210000002", "CommenterreuxUpd", "Brouillon", null,
                    null, null);
        } catch (ApplicationException e) {
            // Vérification du non enregistrement
            assertEquals("1", this.getDataFromTable(
                    "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait", "nbCsf"));
            assertEquals("3", this.getDataFromTable(
                    "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));
            assertEquals("Commenterreux", this.getDataFromTable(
                    "Select commentaire As nbPCsf FROM constatation_service_fait Where numeroDossier='SAVE_CSF'", "commentaire"));
        } catch (TechnicalException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Rollback(false)
    public void updateCsfWithoutPjAndWithoutPoste() throws FileNotFoundException, ConfigurationException,
            DatabaseUnitException, SQLException, ApplicationException, TechnicalException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CSFUpdate.xml");
        Date receptionDate = StringToDate.convertStringToDate("03/07/2017 15:49:19");
        boolean result = csfService.saveCSF("beaupoil", "SAVE_CSF", receptionDate, "12200820100", "9210000002",
                "CommenterreuxUpd", "En attente d'envoi", null, null, null);
        assertTrue(result);
        assertEquals("1", this.getDataFromTable("Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait",
                "nbCsf"));
        assertEquals("1",
                this.getDataFromTable(
                        "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait WHERE numeroDossier='SAVE_CSF'",
                        "nbCsf"));
        assertEquals("0", this.getDataFromTable(
                "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));
        assertEquals("CommenterreuxUpd", this.getDataFromTable(
                "Select commentaire As nbPCsf FROM constatation_service_fait Where numeroDossier='SAVE_CSF'", "commentaire"));
        assertEquals("En attente d'envoi", this.getDataFromTable(
                "Select statutAvancement As nbPCsf FROM constatation_service_fait Where numeroDossier='SAVE_CSF'", "statutAvancement"));
    }

    @Test
    @Rollback(false)
    public void updateCsfWithoutPjAndWithPoste() throws ConfigurationException, DatabaseUnitException, SQLException,
            ApplicationException, TechnicalException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CSFUpdate.xml");
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\CSF"));

        File f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_88.jpg");
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_89.jpg");
        f.createNewFile();
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_90.jpg");
        f.createNewFile();
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_91.jpg");
        f.createNewFile();
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_92.jpg");
        f.createNewFile();
        
        Date receptionDate = StringToDate.convertStringToDate("01/07/2017 12:39:39");
        List<Map<String, String>> listPostesCsfMap = new ArrayList<Map<String, String>>();
        Map<String, String> poste1 = new HashMap<String, String>();
        poste1.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "01");
        poste1.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "02");
        poste1.put(Constantes.KEY_MAP_QTE_RECUE, "1500");
        poste1.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "400");
        poste1.put(Constantes.KEY_MAP_COMMENTAIRE, "CommPosteUpd");
        poste1.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste1.put(Constantes.KEY_MAP_LIEU_STOCKAGE, "48");
        poste1.put(Constantes.KEY_MAP_NATURE, "BL");
        poste1.put(Constantes.KEY_MAP_INTITULE, "Bon de livraisonUpd");
        String path = "C:" + File.separator + "tmp" + File.separator + "piece pcsf 1upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJ = "piece pcsf 1upd.jpg C%3A%5Ctmp%2Fpiece%20pcsf%201upd.jpg";
        poste1.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);

        Map<String, String> poste2 = new HashMap<String, String>();
        poste2.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "02");
        poste2.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "03");
        poste2.put(Constantes.KEY_MAP_QTE_RECUE, "201");
        poste2.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "201");
        poste2.put(Constantes.KEY_MAP_COMMENTAIRE, "CommPoste2 UPD");
        poste2.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste2.put(Constantes.KEY_MAP_NATURE, "Facture");
        poste2.put(Constantes.KEY_MAP_INTITULE, "Facture du poste UPD");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pcsf 2upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        f.createNewFile();

        uploadStringPJ = "piece pcsf 2upd.jpg C%3A%5Ctmp%2Fpiece%20pcsf%202upd.jpg";
        poste2.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);

        Map<String, String> poste3 = new HashMap<String, String>();
        poste3.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "03");
        poste3.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "01");
        poste3.put(Constantes.KEY_MAP_QTE_RECUE, "100");
        poste3.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "49");
        poste3.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste3.put(Constantes.KEY_MAP_LIEU_STOCKAGE, "40");

        listPostesCsfMap.add(poste1);
        listPostesCsfMap.add(poste2);
        listPostesCsfMap.add(poste3);

        final String numTeledossier = "SAVE_CSF";
        boolean result = csfService.saveCSF("beaupoil", numTeledossier, receptionDate, "12200820100", "9210000002",
                "CommenterreuxUpd", "Brouillon", null, null, listPostesCsfMap);
        assertTrue(result);
        assertEquals("1", this.getDataFromTable("Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait",
                "nbCsf"));
        assertEquals("1",
                this.getDataFromTable(
                        "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait WHERE numeroDossier='SAVE_CSF'",
                        "nbCsf"));
        assertEquals("3", this.getDataFromTable(
                "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));
        // Vérification de la copie des PJ
        String emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='01' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNotNull(emplacementPj);
        File pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());

        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='02' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());
        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='03' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNull(emplacementPj);
        
        
        File pjFolder = new File("C:\\tmp\\PJ\\CSF\\" + numTeledossier);
        assertEquals(2, pjFolder.list().length);        

        assertEquals("Partiellement réceptionné",
                this.getDataFromTable(
                        "Select statut FROM poste_commande_achat WHERE id=3",
                        "statut"));
        assertEquals("1001",
                this.getDataFromTable(
                        "Select Cast(quantiteRestante as CHAR) as qte FROM poste_commande_achat WHERE id=3",
                        "qte"));
        assertEquals("Partiellement réceptionné",
                this.getDataFromTable(
                        "Select statut FROM poste_commande_achat WHERE id=4",
                        "statut"));
        assertEquals("200",
                this.getDataFromTable(
                        "Select Cast(quantiteRestante as CHAR) as qte FROM poste_commande_achat WHERE id=4",
                        "qte"));

        assertEquals("Clôturé",
                this.getDataFromTable(
                        "Select statut FROM poste_commande_achat WHERE id=5",
                        "statut"));
        assertEquals("Partiellement réceptionnée",
                this.getDataFromTable(
                        "Select statut FROM commande_achat WHERE id=14",
                        "statut"));
    }

    @Test
    @Rollback(false)
    public void updateCsfWithPjAndWithoutPoste() throws ConfigurationException, DatabaseUnitException, SQLException,
            ApplicationException, TechnicalException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CSFUpdate.xml");
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\CSF"));
        File f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_88.jpg");
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_89.jpg");
        f.createNewFile();
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_90.jpg");
        f.createNewFile();
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_91.jpg");
        f.createNewFile();
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_92.jpg");
        f.createNewFile();
        
        
        Date receptionDate = StringToDate.convertStringToDate("01/07/2017 01:01:01");

        List<Map<String, String>> listAttachments = new ArrayList<Map<String, String>>();
        Map<String, String> pj1 = new HashMap<String, String>();
        pj1.put(Constantes.KEY_MAP_NATURE, "BL");
        pj1.put(Constantes.KEY_MAP_INTITULE, "Intitulé BL");
        String path = "C:" + File.separator + "tmp" + File.separator + "piece csf 1upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        f.createNewFile();

        String uploadStringPJ = "piece csf 1upd.jpg C%3A%5Ctmp%2Fpiece%20csf%201upd.jpg";
        pj1.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj1);

        Map<String, String> pj2 = new HashMap<String, String>();
        pj2.put(Constantes.KEY_MAP_NATURE, "Autre");
        pj2.put(Constantes.KEY_MAP_INTITULE, "Intitulé Autreupd");
        path = "C:" + File.separator + "tmp" + File.separator + "piece csf 2upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        f.createNewFile();
        
        uploadStringPJ = "piece csf 2upd.jpg C%3A%5Ctmp%2Fpiece%20csf%202upd.jpg";
        pj2.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj2);

        Map<String, String> pj3 = new HashMap<String, String>();
        pj3.put(Constantes.KEY_MAP_NATURE, "PV");
        pj3.put(Constantes.KEY_MAP_INTITULE, "Intitulé PVupd");
        path = "C:" + File.separator + "tmp" + File.separator + "piece csf 3upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        f.createNewFile();
        
        uploadStringPJ = "piece csf 3upd.jpg C%3A%5Ctmp%2Fpiece%20csf%203upd.jpg";
        pj3.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj3);

        final String numTeledossier = "SAVE_CSF";
        boolean result = csfService.saveCSF("beaupoil", numTeledossier, receptionDate, "12200820100", "9210000002",
                "Commenterreuxupd", "Brouillon", null, listAttachments, null);
        assertTrue(result);
        assertEquals("1", this.getDataFromTable("Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait",
                "nbCsf"));
        assertEquals("1",
                this.getDataFromTable(
                        "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait WHERE numeroDossier='SAVE_CSF'",
                        "nbCsf"));
        assertEquals("0", this.getDataFromTable(
                "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));

        // Vérification de la copie des PJ
        String emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='BL'",
                "emplacement");
        assertNotNull(emplacementPj);
        File pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());

        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='Autre'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());
        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='PV'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());

        File pjFolder = new File("C:\\tmp\\PJ\\CSF\\" + numTeledossier);
        assertEquals(3, pjFolder.list().length);
    }

    @Test
    @Rollback(false)
    public void updateCsfWithPjAndWithPoste() throws ConfigurationException, DatabaseUnitException, SQLException,
            ApplicationException, TechnicalException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CSFUpdate.xml");
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\CSF"));

        File f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_88.jpg");
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_89.jpg");
        f.createNewFile();
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_90.jpg");
        f.createNewFile();
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_91.jpg");
        f.createNewFile();
        f = new File("C:\\tmp\\PJ\\CSF\\SAVE_CSF\\SAVE_CSF_92.jpg");
        f.createNewFile();
        
        
        Date receptionDate = StringToDate.convertStringToDate("01/07/2017 12:39:39");

        List<Map<String, String>> listAttachments = new ArrayList<Map<String, String>>();
        Map<String, String> pj1 = new HashMap<String, String>();
        pj1.put(Constantes.KEY_MAP_NATURE, "BL");
        pj1.put(Constantes.KEY_MAP_INTITULE, "Intitulé BLupd");
        String path = "C:" + File.separator + "tmp" + File.separator + "piece csf 1upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        f.createNewFile();

        String uploadStringPJ = "piece csf 1upd.jpg C%3A%5Ctmp%2Fpiece%20csf%201upd.jpg";
        pj1.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj1);

        Map<String, String> pj2 = new HashMap<String, String>();
        pj2.put(Constantes.KEY_MAP_NATURE, "Autre");
        pj2.put(Constantes.KEY_MAP_INTITULE, "Intitulé Autre");
        path = "C:" + File.separator + "tmp" + File.separator + "piece csf 2upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        f.createNewFile();

        uploadStringPJ = "piece csf 2upd.jpg C%3A%5Ctmp%2Fpiece%20csf%202upd.jpg";
        pj2.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj2);

        Map<String, String> pj3 = new HashMap<String, String>();
        pj3.put(Constantes.KEY_MAP_NATURE, "PV");
        pj3.put(Constantes.KEY_MAP_INTITULE, "Intitulé PV");
        path = "C:" + File.separator + "tmp" + File.separator + "piece csf 3upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        f.createNewFile();

        uploadStringPJ = "piece csf 3upd.jpg C%3A%5Ctmp%2Fpiece%20csf%203upd.jpg";
        pj3.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);
        listAttachments.add(pj3);

        List<Map<String, String>> listPostesCsfMap = new ArrayList<Map<String, String>>();
        Map<String, String> poste1 = new HashMap<String, String>();
        poste1.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "01");
        poste1.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "02");
        poste1.put(Constantes.KEY_MAP_QTE_RECUE, "15");
        poste1.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "10");
        poste1.put(Constantes.KEY_MAP_COMMENTAIRE, "CommPoste");
        poste1.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste1.put(Constantes.KEY_MAP_LIEU_STOCKAGE, "44");
        poste1.put(Constantes.KEY_MAP_NATURE, "BL");
        poste1.put(Constantes.KEY_MAP_INTITULE, "Bon de livraison");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pcsf 1upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        f.createNewFile();

        uploadStringPJ = "piece pcsf 1upd.jpg C%3A%5Ctmp%2Fpiece%20pcsf%201upd.jpg";
        poste1.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);

        Map<String, String> poste2 = new HashMap<String, String>();
        poste2.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "02");
        poste2.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "03");
        poste2.put(Constantes.KEY_MAP_QTE_RECUE, "150");
        poste2.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "100");
        poste2.put(Constantes.KEY_MAP_COMMENTAIRE, "CommPoste2");
        poste2.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste2.put(Constantes.KEY_MAP_NATURE, "Facture");
        poste2.put(Constantes.KEY_MAP_INTITULE, "Facture du poste");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pcsf 2upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        f.createNewFile();
        
        uploadStringPJ = "piece pcsf 2upd.jpg C%3A%5Ctmp%2Fpiece%20pcsf%202upd.jpg";
        poste2.put(Constantes.KEY_MAP_UPLOAD_STRING_PJ, uploadStringPJ);

        Map<String, String> poste3 = new HashMap<String, String>();
        poste3.put(Constantes.KEY_MAP_NUMERO_POSTE_CSF, "03");
        poste3.put(Constantes.KEY_MAP_NUMERO_CA_POSTE, "01");
        poste3.put(Constantes.KEY_MAP_QTE_RECUE, "1500");
        poste3.put(Constantes.KEY_MAP_QTE_ACCEPTEE, "1000");
        poste3.put(Constantes.KEY_MAP_STATUT_AVANCEMENT, null);
        poste3.put(Constantes.KEY_MAP_LIEU_STOCKAGE, "44");

        listPostesCsfMap.add(poste1);
        listPostesCsfMap.add(poste2);
        listPostesCsfMap.add(poste3);

        final String numTeledossier = "SAVE_CSF";
        boolean result = csfService.saveCSF("beaupoil", numTeledossier, receptionDate, "12200820100", "9210000002",
                "Commenterreux", "Brouillon", null, listAttachments, listPostesCsfMap);
        assertTrue(result);
        assertEquals("1", this.getDataFromTable("Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait",
                "nbCsf"));
        assertEquals("1",
                this.getDataFromTable(
                        "Select Cast(count(1) as char) As nbCsf FROM constatation_service_fait WHERE numeroDossier='SAVE_CSF'",
                        "nbCsf"));
        assertEquals("3", this.getDataFromTable(
                "Select Cast(count(1) as char) As nbPCsf FROM poste_constatation_service_fait", "nbPCsf"));

        // Vérification de la copie des PJ
        String emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='BL'",
                "emplacement");
        assertNotNull(emplacementPj);
        File pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());

        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='Autre'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());
        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, constatation_service_fait csf "
                        + "Where pj.idCsf=csf.id AND csf.numeroDossier='" + numTeledossier + "' AND pj.nature='PV'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());

        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='01' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());

        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='02' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNotNull(emplacementPj);
        pjFile = new File(emplacementPj);
        assertEquals("C:\\tmp\\PJ\\CSF\\" + numTeledossier, pjFile.getParent());
        assertTrue(pjFile.exists());
        emplacementPj = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, poste_constatation_service_fait pcsf, constatation_service_fait csf "
                        + "Where pj.idPosteCsf=pcsf.id and pcsf.idCsf=csf.id AND pcsf.idCSFPoste='03' AND csf.numeroDossier='"
                        + numTeledossier + "'",
                "emplacement");
        assertNull(emplacementPj);

        File pjFolder = new File("C:\\tmp\\PJ\\CSF\\" + numTeledossier);
        assertEquals(5, pjFolder.list().length);
    }
}
