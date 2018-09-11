package com.sigif.service.da;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import com.sigif.dao.PieceJointeDAO;
import com.sigif.dao.PosteDemandeAchatDAO;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.service.DemandeAchatService;
import com.sigif.service.PosteDaService;
import com.sigif.testutil.AbstractDbTestCase;
import com.sigif.util.AttachmentsUtils;
import com.sigif.util.Constantes;
import com.sigif.util.StringToDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class DemandeAchatServiceTest extends AbstractDbTestCase {

    @Autowired
    DemandeAchatService demandeAchatService;

    @Autowired
    PosteDaService posteDemandeAchatService;

    @Autowired
    PieceJointeDAO pjDAO;

    @Autowired
    PosteDemandeAchatDAO posteDAO;

    /**
     * Teste l'enregistrement d'une DA sans PJ ni Postes DA.
     */
    @Test
    @Rollback(false)
    public void saveDAwithoutPJAndPDATest()
            throws ApplicationException, ConfigurationException, DatabaseUnitException, SQLException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DAService.xml");
        String login = "beye";
        String requesterLogin = "ndir";
        String ministeryCode = "31";
        String spendingServiceCode = "11701440110";
        String programCode = "1001";
        String purchasingCategoriesCode = "T";
        String numTeledossier = "numero1";
        Date creationDate = new Date();
        String statutDavancement = "Brouillon";

        try {
            boolean resultat = demandeAchatService.saveDAwithPJ(login, ministeryCode, spendingServiceCode, programCode,
                    creationDate, requesterLogin, purchasingCategoriesCode, null, null, null, null, null,
                    numTeledossier, statutDavancement);
            assertEquals(true, resultat);
         // Vérification de l'histo
            String statut =
                    this.getDataFromTable("Select histo.* FROM da_statut_avancement_historique histo, demande_achat da "
                            + "Where histo.idDA=da.id AND da.numeroDossier='"
                            + numTeledossier + "'", "statutAvancement");
            assertEquals(statutDavancement, statut);           
            
            System.out.println("SAVE DA simple OK");
        } catch (Exception e) {
            System.out.println("SAVE DA simple KO");
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Teste l'enregistrement d'une DA avec PJ sans les Postes DA.
     */
    @Test
    @Rollback(false)
    public void saveDAwithPJwithoutPDATest()
            throws ApplicationException, ConfigurationException, DatabaseUnitException, SQLException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DAService.xml");
        String path = "C:" + File.separator + "tmp" + File.separator + "piece jointe 1.gif";
        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String login = "admin";
        String requesterLogin = "beaupoil";
        String ministeryCode = "21";
        String spendingServiceCode = "11701290110";
        String programCode = "1011";
        String purchasingCategoriesCode = "P";
        Date creationDate = new Date();

        String intitulePJ = "DA IntituléTest1";
        String demandTitle = "DA TitleTest1";
        String objectDemand = "DA ObjetTest1";
        String numTeledossier = "numero2"; // A changer chauqe fois
        String uploadStringPJ = "piece jointe 1.gif C%3A%5Ctmp%2Fpiece%20jointe%201.gif";
        String statutAvancement = "Brouillon";
        try {
            boolean resultat = demandeAchatService.saveDAwithPJ(login, ministeryCode, spendingServiceCode, programCode,
                    creationDate, requesterLogin, purchasingCategoriesCode, demandTitle, objectDemand, uploadStringPJ,
                    intitulePJ, null, numTeledossier, statutAvancement);
            assertEquals(true, resultat);
            // Vérification de la copie des PJ
            String emplacementPj = this.getDataFromTable(
                    "Select pj.* FROM piece_jointe pj, demande_achat da Where pj.idDa=da.id", "emplacement");
            assertNotNull(emplacementPj);
            File pjFile = new File(emplacementPj);
            assertEquals("C:\\tmp\\PJ\\DA\\numero2", pjFile.getParent());
            assertTrue(pjFile.exists());
         // Vérification de l'histo
            String statut =
                    this.getDataFromTable("Select histo.* FROM da_statut_avancement_historique histo, demande_achat da "
                            + "Where histo.idDA=da.id AND da.numeroDossier='"
                            + numTeledossier + "'", "statutAvancement");
            assertEquals(statutAvancement, statut);   

            System.out.println("SAVE DA avec PJ sans PDA OK");
        } catch (Exception e) {
            System.out.println("SAVE DA  avec PJ sans PDA KO");
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Teste l'enregistrement d'une DA sans PJ mais avec des Postes DA.
     */
    @Test
    @Rollback(false)
    public void saveDAwithoutPJwithPDATest()
            throws ApplicationException, ConfigurationException, DatabaseUnitException, SQLException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DAService.xml");
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\DA"));
        String login = "admin";
        String requesterLogin = "beaupoil";
        String ministeryCode = "21";
        String spendingServiceCode = "11701290110";
        String programCode = "1011";
        String purchasingCategoriesCode = "P";
        String numTeledossier = "numero3";
        Date creationDate = new Date();
        String intitulePJ = "DA IntituléTest3";
        String statutAvancement = "Brouillon";

        List<Map<String, Object>> listePostesDAmap = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>(); // Poste qui a une PJ
        map1.put("NumeroPosteDA", "01");
        map1.put("Type", "F");
        map1.put("Categorie", "2111ISLN");
        map1.put("Designation", "Designation 1");
        map1.put("Quantite", 23);
        map1.put("Unite", "SAC");
        map1.put("DateLivraisonSouhaite", new Date());
        map1.put("Commentaire", "Commentaire 1 OK");
        String path = "C:" + File.separator + "tmp" + File.separator + "piece pda 1.jpg";
        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJ = "piece pda 1.jpg C%3A%5Ctmp%2Fpiece%20pda%201.jpg";
        map1.put("UploadStringPJ", uploadStringPJ);
        map1.put("IntitulePJ", "Intitulé PJPDA 1");
        map1.put("ServiceBeneficiaire", "11100110100");
        map1.put("Programme", "1008");
        map1.put("Action", "1001-01");
        map1.put("Activite", "act-2");
        map1.put("Fond", "ETAT");
        map1.put("Civilite", "Monsieur");
        map1.put("Nom", "Marc");
        map1.put("Rue", "Félix");
        map1.put("Numero", "58");
        map1.put("Ville", "1123");
        map1.put("Region", "1");
        map1.put("CodePostal", "48000");
        map1.put("Telephone", "339562584");
        map1.put("Portable", "772389011");
        map1.put("Email", "e.mail1@atos.net");
        listePostesDAmap.add(map1);

        Map<String, Object> map2 = new HashMap<>(); // Poste qui n'a pas de PJ
        map2.put("NumeroPosteDA", "02");
        map2.put("Type", "F");
        map2.put("Categorie", "2129ISLN");
        map2.put("Designation", "Designation 2");
        map2.put("Quantite", 800);
        map2.put("Unite", "BT");
        map2.put("DateLivraisonSouhaite", new Date());
        map2.put("Commentaire", "Commentaire 2 OK");
        map2.put("UploadStringPJ", null);
        map2.put("IntitulePJ", "Intitulé PJPDA 2");
        map2.put("ServiceBeneficiaire", "11701540111");
        map2.put("Programme", "1017");
        map2.put("Action", "1017-02");
        map2.put("Activite", "act-7");
        map2.put("Fond", "ETAT");
        map2.put("Civilite", "Madame");
        map2.put("Nom", "FAYETTE");
        map2.put("Rue", "ESCLANGON");
        map2.put("Numero", "23");
        map2.put("Ville", "1111");
        map2.put("Region", "1");
        map2.put("CodePostal", "44000");
        map2.put("Telephone", "339582584");
        map2.put("Portable", "772389012");
        map2.put("Email", "e.mail2@atos.net");
        listePostesDAmap.add(map2);

        Map<String, Object> map3 = new HashMap<>(); // Poste avec qques champs
                                                    // manquants qui a
        // une PJ
        map3.put("NumeroPosteDA", "03");
        map3.put("Type", "F");
        map3.put("Categorie", "2111ISLN");
        map3.put("Designation", "Designation 3");
        map3.put("Quantite", 2);
        map3.put("Unite", "PAQ");
        map3.put("DateLivraisonSouhaite", new Date());
        map3.put("Commentaire", "Commentaire 3 OK");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pda 3.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJ = "piece pda 3.jpg C%3A%5Ctmp%2Fpiece%20pda%203.jpg";
        map3.put("UploadStringPJ", uploadStringPJ);
        map3.put("IntitulePJ", "Intitulé PJPDA 3");
        map3.put("ServiceBeneficiaire", "11100110100");
        map3.put("Programme", "1008");
        map3.put("Action", null);
        map3.put("Activite", "act-2");
        map3.put("Fond", "ETAT");
        map3.put("Civilite", "Monsieur");
        map3.put("Nom", "Marc");
        map3.put("Rue", "Félix");
        map3.put("Numero", "58");
        map3.put("Ville", null);
        map3.put("Region", "1");
        map3.put("CodePostal", "48000");
        map3.put("Telephone", "339562584");
        map3.put("Portable", null);
        map3.put("Email", "e.mail3@atos.net");
        listePostesDAmap.add(map3);

        try {
            boolean resultat = demandeAchatService.saveDAwithPJ(login, ministeryCode, spendingServiceCode, programCode,
                    creationDate, requesterLogin, purchasingCategoriesCode, null, null, null, intitulePJ,
                    listePostesDAmap, numTeledossier, statutAvancement);
            assertEquals(true, resultat);

            // Vérification de la copie des PJ
            String emplacementPj =
                    this.getDataFromTable("Select pj.* FROM piece_jointe pj, poste_demande_achat pda, demande_achat da "
                            + "Where pj.idPosteDa=pda.id and pda.idDA=da.id AND pda.idDAPoste='01' AND da.numeroDossier='"
                            + numTeledossier + "'", "emplacement");
            assertNotNull(emplacementPj);
            File pjFile = new File(emplacementPj);
            assertEquals("C:\\tmp\\PJ\\DA\\numero3", pjFile.getParent());
            assertTrue(pjFile.exists());
            emplacementPj =
                    this.getDataFromTable("Select pj.* FROM piece_jointe pj, poste_demande_achat pda, demande_achat da "
                            + "Where pj.idPosteDa=pda.id and pda.idDA=da.id AND pda.idDAPoste='02' AND da.numeroDossier='"
                            + numTeledossier + "'", "emplacement");
            assertNull(emplacementPj);
            emplacementPj =
                    this.getDataFromTable("Select pj.* FROM piece_jointe pj, poste_demande_achat pda, demande_achat da "
                            + "Where pj.idPosteDa=pda.id and pda.idDA=da.id AND pda.idDAPoste='03' AND da.numeroDossier='"
                            + numTeledossier + "'", "emplacement");
            assertNotNull(emplacementPj);
            pjFile = new File(emplacementPj);
            assertEquals("C:\\tmp\\PJ\\DA\\numero3", pjFile.getParent());
            assertTrue(pjFile.exists());
            System.out.println("SAVE DA sans PJ avec PDA OK");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("SAVE DA sans PJ avec PDA KO");
        }
    }

    /**
     * Teste de modification d'une DA avec PJ et avec des Postes DA.
     */
    @Test
    @Rollback(false)
    public void updateDAwithPjAndPostesPJTest()
            throws ApplicationException, ConfigurationException, DatabaseUnitException, SQLException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/UpdateDa.xml");
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\DA"));
        String login = "beye";
        String requesterLogin = "bouchot";
        String ministeryCode = "21";
        String spendingServiceCode = "11701290110";
        String programCode = "1011";
        String purchasingCategoriesCode = "P";
        Date creationDate = new Date();
        String statutAvancement = "En attente d'envoi";

        String intitulePJ = "DA IntituléTestMUpd";
        String demandTitle = "DA TitleTestMUpd";
        String objectDemand = "DA ObjetTestMUpd";
        String numTeledossier = "numeroM"; 
        String path = "C:" + File.separator + "tmp" + File.separator + "piece jointe MUpd.jpg";

        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJ = "piece jointe MUpd.jpg C%3A%5Ctmp%2Fpiece%20jointe%20MUpd.jpg";

        List<Map<String, Object>> listePostesDAmap = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>(); // Poste qui a une PJ
        map1.put("Type", "F");
        map1.put("Categorie", "620101");
        map1.put("Designation", "Designation 4");
        map1.put("Quantite", 23);
        map1.put("Unite", "SAC");
        map1.put("DateLivraisonSouhaite", new Date());
        map1.put("Commentaire", "Commentaire 1 OK");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pda 4upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJPda = "piece pda 4upd.jpg C%3A%5Ctmp%2Fpiece%20pda%204upd.jpg";
        map1.put("NumeroPosteDA", "01");
        map1.put("UploadStringPJ", uploadStringPJPda);
        map1.put("IntitulePJ", "Intitulé PJPDA 4upd");
        map1.put("ServiceBeneficiaire", "11100110100");
        map1.put("Programme", "1008");
        map1.put("Action", "1001-01");
        map1.put("Activite", "act-2");
        map1.put("Fond", "ETAT");
        map1.put("Civilite", "Madame");
        map1.put("Nom", "Marc");
        map1.put("Rue", "Félixupd");
        map1.put("Numero", "58");
        map1.put("Ville", "1123");
        map1.put("Region", "1");
        map1.put("CodePostal", "48000");
        map1.put("Telephone", "339562584");
        map1.put("Portable", "772389011");
        map1.put("Email", "e.mail1@atos.netupd");
        listePostesDAmap.add(map1);

        Map<String, Object> map2 = new HashMap<>(); // Poste qui n'a pas de PJ
        map2.put("NumeroPosteDA", "02");
        map2.put("Type", "F");
        map2.put("Categorie", "620501");
        map2.put("Designation", "Designation 5upd");
        map2.put("Quantite", 800);
        map2.put("Unite", "BT");
        map2.put("DateLivraisonSouhaite", new Date());
        map2.put("Commentaire", "Commentaire 5 OK");
        map2.put("UploadStringPJ", null);
        map2.put("IntitulePJ", "Intitulé PJPDA 5");
        map2.put("ServiceBeneficiaire", "11701540111");
        map2.put("Programme", "1017");
        map2.put("Action", "1017-02");
        map2.put("Activite", "act-7");
        map2.put("Fond", "ETAT");
        map2.put("Civilite", "Madame");
        map2.put("Nom", "FAYETTEupd");
        map2.put("Rue", "ESCLANGON");
        map2.put("Numero", "23");
        map2.put("Ville", "1111");
        map2.put("Region", "1");
        map2.put("CodePostal", "44000");
        map2.put("Telephone", "339582584");
        map2.put("Portable", "772389012");
        map2.put("Email", "e.mail2@atos.net");
        listePostesDAmap.add(map2);

        Map<String, Object> map3 = new HashMap<>(); // Poste avec qques champs
                                                    // manquants qui a
        // une PJ
        map3.put("NumeroPosteDA", "03");
        map3.put("Type", "F");
        map3.put("Categorie", "620601");
        map3.put("Designation", "Designation 6");
        map3.put("Quantite", 2);
        map3.put("Unite", "PAQ");
        map3.put("DateLivraisonSouhaite", new Date());
        map3.put("Commentaire", "Commentaire 6 OK");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pda 6.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJPda = "piece pda 6.jpg C%3A%5Ctmp%2Fpiece%20pda%206.jpg";
        map3.put("UploadStringPJ", uploadStringPJPda);
        map3.put("IntitulePJ", "Intitulé PJPDA 6");
        map3.put("ServiceBeneficiaire", "11100110100");
        map3.put("Programme", "1008");
        map3.put("Action", null);
        map3.put("Activite", "act-2");
        map3.put("Fond", "ETAT");
        map3.put("Civilite", "Monsieur");
        map3.put("Nom", "Marc");
        map3.put("Rue", "Félix");
        map3.put("Numero", "58");
        map3.put("Ville", null);
        map3.put("Region", "1");
        map3.put("CodePostal", "48000");
        map3.put("Telephone", "339562584");
        map3.put("Portable", null);
        map3.put("Email", "e.mail3@atos.net");
        map3.put("LieuStockage", "1");
        listePostesDAmap.add(map3);

        try {
            boolean resultat = demandeAchatService.saveDAwithPJ(login, ministeryCode, spendingServiceCode, programCode,
                    creationDate, requesterLogin, purchasingCategoriesCode, demandTitle, objectDemand, uploadStringPJ,
                    intitulePJ, listePostesDAmap, numTeledossier, statutAvancement);
            assertEquals(true, resultat);
            
         // Vérification de la copie des PJ
            String emplacementPj = this.getDataFromTable(
                    "Select pj.* FROM piece_jointe pj, demande_achat da Where pj.idDa=da.id", "emplacement");
            assertNotNull(emplacementPj);
            File pjFile = new File(emplacementPj);
            assertEquals("C:\\tmp\\PJ\\DA\\numeroM", pjFile.getParent());
            assertTrue(pjFile.exists());
            
            emplacementPj =
                    this.getDataFromTable("Select pj.* FROM piece_jointe pj, poste_demande_achat pda, demande_achat da "
                            + "Where pj.idPosteDa=pda.id and pda.idDA=da.id AND pda.idDAPoste='01' AND da.numeroDossier='"
                            + numTeledossier + "'", "emplacement");
            assertNotNull(emplacementPj);
            pjFile = new File(emplacementPj);
            assertEquals("C:\\tmp\\PJ\\DA\\numeroM", pjFile.getParent());
            assertTrue(pjFile.exists());
            emplacementPj =
                    this.getDataFromTable("Select pj.* FROM piece_jointe pj, poste_demande_achat pda, demande_achat da "
                            + "Where pj.idPosteDa=pda.id and pda.idDA=da.id AND pda.idDAPoste='02' AND da.numeroDossier='"
                            + numTeledossier + "'", "emplacement");
            assertNull(emplacementPj);
            emplacementPj =
                    this.getDataFromTable("Select pj.* FROM piece_jointe pj, poste_demande_achat pda, demande_achat da "
                            + "Where pj.idPosteDa=pda.id and pda.idDA=da.id AND pda.idDAPoste='03' AND da.numeroDossier='"
                            + numTeledossier + "'", "emplacement");
            assertNotNull(emplacementPj);
            pjFile = new File(emplacementPj);
            assertEquals("C:\\tmp\\PJ\\DA\\numeroM", pjFile.getParent());
            assertTrue(pjFile.exists());
            assertEquals(3, pjFile.getParentFile().list().length);
         // Vérification de l'histo
            Date dateOldStatut =
                    this.getDateDataFromTable("Select histo.* FROM da_statut_avancement_historique histo, demande_achat da "
                            + "Where histo.idDA=da.id AND histo.statutAvancement = 'Brouillon' AND da.numeroDossier='"
                            + numTeledossier + "'", "date");
            Date dateNewStatut =
                    this.getDateDataFromTable("Select histo.* FROM da_statut_avancement_historique histo, demande_achat da "
                            + "Where histo.idDA=da.id AND histo.statutAvancement = 'En attente d''envoi' AND da.numeroDossier='"
                            + numTeledossier + "'", "date");
            assertTrue(dateOldStatut.before(dateNewStatut));   
            
            System.out.println("SAVE DA WITH PJ AND PDA OK");
        } catch (Exception e) {
            System.out.println("SAVE DA WITH PJ AND PDA KO");
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    @Rollback(false)
    public void saveDAwithPJTest()
            throws ApplicationException, ConfigurationException, DatabaseUnitException, SQLException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DAService.xml");
        String path = "C:" + File.separator + "tmp" + File.separator + "piece jointe 1.gif";
        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\DA"));
        String login = "beye";
        String requesterLogin = "ndir";
        String ministeryCode = "31";
        String spendingServiceCode = "11701440110";
        String programCode = "1001";
        String purchasingCategoriesCode = "T";
        Date creationDate = new Date();
        String demandTitle = "DA TitleTest";
        String objectDemand = "DA ObjetTest";
        String numTeledossier = "numero1PJ";
        String statutAvancement = "Brouillon";

        String uploadStringPJ = "piece jointe 1.gif C%3A%5Ctmp%2Fpiece%20jointe%201.gif";
        String intitulePJ = "intitulé pj 1";
        try {
            boolean resultat = demandeAchatService.saveDAwithPJ(login, ministeryCode, spendingServiceCode, programCode,
                    creationDate, requesterLogin, purchasingCategoriesCode, demandTitle, objectDemand, uploadStringPJ,
                    intitulePJ, null, numTeledossier, statutAvancement);
            assertEquals(true, resultat);
            // Vérification de la copie des PJ
            String emplacementPj = this.getDataFromTable(
                    "Select pj.* FROM piece_jointe pj, demande_achat da Where pj.idDa=da.id", "emplacement");
            assertNotNull(emplacementPj);
            File pjFile = new File(emplacementPj);
            assertEquals("C:\\tmp\\PJ\\DA\\numero1PJ", pjFile.getParent());
            assertTrue(pjFile.exists());
            System.out.println("SAVE DA WITH PJ AND PDA OK");
        } catch (Exception e) {
            System.out.println("SAVE DA WITH PJ AND PDA KO");
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Test
    public void countDaTest() throws ApplicationException, TechnicalException, ConfigurationException,
            DatabaseUnitException, SQLException, FileNotFoundException {
        try {
            this.emptyDatabase();
            this.loadDataFileAndOverWrite("dataTestFiles/DAService/DAService2.xml");
            int rslt = demandeAchatService.countDANbResults("beye", null, "", "", null, null, "", "", "", "");
            assertNotNull(rslt);
            assertEquals(2, rslt);

            // rslt = demandeAchatService.countDANbResults("beye", "34",
            // "11701270110", "reynaud", "T", "30/05/2017", "01/06/2017", "",
            // "", "S-3-2RQL3");
            rslt = demandeAchatService.countDANbResults("beye", null, "11701270110", "", "T", "30/05/2017", null, "",
                    "", null);

            assertNotNull(rslt);
            assertEquals(1, rslt);
            System.out.println("countDaTest OK");
        } catch (Exception e) {
            System.out.println("countDaTest KO");
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void searchDATest() throws TechnicalException, ApplicationException {
        try {
            this.emptyDatabase();
            this.loadDataFileAndOverWrite("dataTestFiles/DAService/DAService2.xml");
            Map<String, Object[]> rslt = demandeAchatService.searchDA("beye", null, "", "", "", "", "", "", "", "", 20);

            assertNotNull(rslt);
            String[] numeros = (String[]) rslt.get(Constantes.KEY_MAP_NUMERO);
            String[] ministeres = (String[]) rslt.get(Constantes.KEY_MAP_MINISTERE);
            String[] services = (String[]) rslt.get(Constantes.KEY_MAP_SERVICE_DEPENSIER);

            assertEquals(2, numeros.length);
            assertEquals("Ministère de l'economie des finances et ", ministeres[1]);
            assertEquals("Service de communication", services[0]);

            // ***********************************************************************************************************************
            rslt = demandeAchatService.searchDA("beye", "34", "", "", "T", "30/05/2017", "01/06/2017", "", "",
                    "S-3-2RQL3", 10);
            assertNotNull(rslt);
            numeros = (String[]) rslt.get(Constantes.KEY_MAP_NUMERO);
            ministeres = (String[]) rslt.get(Constantes.KEY_MAP_MINISTERE);
            services = (String[]) rslt.get(Constantes.KEY_MAP_SERVICE_DEPENSIER);

            assertEquals(1, numeros.length);
            assertEquals("Ministère de la justice", ministeres[0]);// 5
            assertEquals("Service de communication", services[0]);// 14

            // ***********************************************************************************************************************
            rslt = demandeAchatService.searchDA("beye", null, "12200820100", "", "F", "30/05/2017", null, "", "", null,
                    10);
            assertNotNull(rslt);
            numeros = (String[]) rslt.get(Constantes.KEY_MAP_NUMERO);
            ministeres = (String[]) rslt.get(Constantes.KEY_MAP_MINISTERE);
            String[] categories = (String[]) rslt.get(Constantes.KEY_MAP_CATEGORIE_ACHAT);

            assertEquals(1, numeros.length);
            assertEquals("Ministère de l'economie des finances et ", ministeres[0]);// 7
            assertEquals("Fournitures", categories[0]);// 2
            System.out.println("searchDATest OK");

        } catch (Exception e) {
            System.out.println("searchDATest KO");
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Teste l'enregistrement d'une DA avec PJ et avec des Postes DA qui plante
     * et vérifie qu'il ne reste rien en Base.
     */
    @Test
    @Rollback(false)
    public void saveDAwithPjAndPostesPJRollbackTest()
            throws ApplicationException, ConfigurationException, DatabaseUnitException, SQLException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DAService.xml");
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\DA"));
        String login = "beye";
        String requesterLogin = "bouchot";
        String ministeryCode = "21";
        String spendingServiceCode = "11701290110";
        String programCode = "1011";
        String purchasingCategoriesCode = "P";
        Date creationDate = new Date();
        String statutAvancement = "Brouillon";

        String intitulePJ = "DA IntituléTestM";
        String demandTitle = "DA TitleTestM";
        String objectDemand = "DA ObjetTestM";
        String numTeledossier = "numeroM"; // A changer chauqe fois
        String path = "C:" + File.separator + "tmp" + File.separator + "piece jointe M.jpg";
        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJ = "piece jointe M.jpg C%3A%5Ctmp%2Fpiece%20jointe%20M.jpg";

        List<Map<String, Object>> listePostesDAmap = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>(); // Poste qui a une PJ
        map1.put("NumeroPosteDA", "numero4");
        map1.put("Type", "F");
        map1.put("Categorie", "620101");
        map1.put("Designation", "Designation 4");
        map1.put("Quantite", 23);
        map1.put("Unite", "SAC");
        map1.put("DateLivraisonSouhaite", new Date());
        map1.put("Commentaire", "Commentaire 1 OK");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pda 4.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJPda = "piece pda 4.jpg C%3A%5Ctmp%2Fpiece%20pda%204.jpg";
        map1.put("UploadStringPJ", uploadStringPJPda);
        map1.put("IntitulePJ", "Intitulé PJPDA 4");
        map1.put("ServiceBeneficiaire", "11100110100");
        map1.put("Programme", "1008");
        map1.put("Action", "1001-01");
        map1.put("Activite", "act-2");
        map1.put("Fond", "ETAT");
        map1.put("Civilite", "Monsieur");
        map1.put("Nom", "Marc");
        map1.put("Rue", "Félix");
        map1.put("Numero", "58");
        map1.put("Ville", "1123");
        map1.put("Region", "1");
        map1.put("CodePostal", "48000");
        map1.put("Telephone", "339562584");
        map1.put("Portable", "772389011");
        map1.put("Email", "e.mail1@atos.net");
        listePostesDAmap.add(map1);

        Map<String, Object> map2 = new HashMap<>(); // Poste qui n'a pas de PJ
        map2.put("NumeroPosteDA", "numero5");
        map2.put("Type", "F");
        map2.put("Categorie", "620501");
        map2.put("Designation", "Designation 5");
        map2.put("Quantite", 800);
        map2.put("Unite", "BT");
        map2.put("DateLivraisonSouhaite", new Date());
        map2.put("Commentaire", "Commentaire 5 OK");
        map2.put("UploadStringPJ", null);
        map2.put("IntitulePJ", "Intitulé PJPDA 5");
        map2.put("ServiceBeneficiaire", "11701540111");
        map2.put("Programme", "1017");
        map2.put("Action", "1017-02");
        map2.put("Activite", "act-7");
        map2.put("Fond", "ETAT");
        map2.put("Civilite", "Madame");
        map2.put("Nom", "FAYETTE");
        map2.put("Rue", "ESCLANGON");
        map2.put("Numero", "23");
        map2.put("Ville", "1111");
        map2.put("Region", "1");
        map2.put("CodePostal", "44000");
        map2.put("Telephone", "339582584");
        map2.put("Portable", "772389012");
        map2.put("Email", "e.mail2@atos.net");
        listePostesDAmap.add(map2);

        Map<String, Object> map3 = new HashMap<>(); // Poste avec qques champs
                                                    // manquants qui a
        // une PJ
        map3.put("NumeroPosteDA", "numero6");
        map3.put("Type", "F");
        map3.put("Categorie", "620601");
        map3.put("Designation", "Designation 6");
        map3.put("Quantite", 2);
        map3.put("Unite", "PAQ");
        map3.put("DateLivraisonSouhaite", new Date());
        map3.put("Commentaire", "Commentaire 6 OK");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pda 6.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJPda = "piece pda 8.jpg C%3A%5Ctmp%2Fpiece%20pda%206.jpg";
        map3.put("UploadStringPJ", uploadStringPJPda);
        map3.put("IntitulePJ", "Intitulé PJPDA 6");
        map3.put("ServiceBeneficiaire", "11100110100");
        map3.put("Programme", "1008");
        map3.put("Action", null);
        map3.put("Activite", "act-2");
        map3.put("Fond", "ETAT");
        map3.put("Civilite", "Monsieur");
        map3.put("Nom", "Marc");
        map3.put("Rue", "Félix");
        map3.put("Numero", "58");
        map3.put("Ville", null);
        map3.put("Region", "1");
        map3.put("CodePostal", "48000");
        map3.put("Telephone", "339562584");
        map3.put("Portable", null);
        map3.put("Email", "e.mail3@atos.net");
        listePostesDAmap.add(map3);

        try {
            demandeAchatService.saveDAwithPJ(login, ministeryCode, spendingServiceCode, programCode, creationDate,
                    requesterLogin, purchasingCategoriesCode, demandTitle, objectDemand, uploadStringPJ, intitulePJ,
                    listePostesDAmap, numTeledossier, statutAvancement);
            fail("La sauvegarde doit échouer !");
        } catch (Exception e) {
            assertEquals(3, this.demandeAchatService.getAll().size());
            File folderPj = new File("C:\\tmp\\PJ\\DA\\numeroM");
            assertFalse(folderPj.exists());
        }
    }

    @Test
    public void getDAInformationTest() throws ApplicationException, TechnicalException, FileNotFoundException,
            ConfigurationException, DatabaseUnitException, SQLException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/PostDAService/PosteDAService.xml");
        Map<String, String> result = demandeAchatService.getDAInformation("SIGIFNUM1");
        assertNotNull(result);
        assertEquals("Mamadou NDIR", result.get(Constantes.KEY_MAP_DEMANDEUR));
    }

    @Test
    public void getAlertDATest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException,
            SQLException, ApplicationException, TechnicalException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/PostDAService/PosteDAService.xml");
        String result = demandeAchatService.getDataImpact("SIGIFNUM1");
        assertNotNull(result);
        System.out.println("*Msg* : " + result);
    }

    @Test
    public void isDaUpdatableTest() throws ApplicationException, ConfigurationException, DatabaseUnitException,
            SQLException, FileNotFoundException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DALockService.xml");

        // Da modifiable si :
        // en attente d'envoi
        assertTrue(demandeAchatService.isDAUpdatable("beaupoil", "123451"));
        // brouillon
        assertTrue(demandeAchatService.isDAUpdatable("beaupoil", "S-7-B8LPV"));
        // verrou par soi-même
        assertTrue(demandeAchatService.isDAUpdatable("alardo", "S-7-2RQL8"));
        // verrou périmé
        assertTrue(demandeAchatService.isDAUpdatable("beaupoil", "S-7-2RQL9"));

        // DA non modifiable si
        // traitement en cours (staut ni brouillon, ni en attente d'envoi)
        assertFalse(demandeAchatService.isDAUpdatable("beaupoil", "S-7-2RQL7"));

        // verrou par quelqu'un d'autre non périmé
        assertFalse(demandeAchatService.isDAUpdatable("beaupoil", "S-7-2RQL8"));
    }

    @Test
    public void getDAInformationFullTest() throws ApplicationException, TechnicalException, ConfigurationException,
            DatabaseUnitException, SQLException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DAwithPj.xml");
        String path = "C:\\tmp\\PJ\\DA\\numeroM\\numeroM_10.jpg";
        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }

        Map<String, String> result = demandeAchatService.getDAInformation("numeroM");
        assertNotNull(result);
        assertEquals("Meissa BEYE", result.get(Constantes.KEY_MAP_CREATEUR));
        assertEquals("beye", result.get(Constantes.KEY_MAP_LOGIN_CREATEUR));
        assertEquals("16/06/2017", result.get(Constantes.KEY_MAP_DATE_CREATION));
        assertEquals("16/06/2017", result.get(Constantes.KEY_MAP_DATE_MODIFICATION));
        assertEquals("Béatrice BOUCHOT", result.get(Constantes.KEY_MAP_DEMANDEUR));
        assertEquals("Brouillon", result.get(Constantes.KEY_MAP_STATUT));
        assertEquals(null, result.get(Constantes.KEY_MAP_STATUT_APPROBATION));
        assertEquals("Présidence de la république", result.get(Constantes.KEY_MAP_MINISTERE));
        assertEquals("21", result.get(Constantes.KEY_MAP_CODE_MINISTERE));
        assertEquals("Pilotage,  gestion et coordination administrative", result.get(Constantes.KEY_MAP_PROGRAMME));
        assertEquals("1011", result.get(Constantes.KEY_MAP_CODE_PROGRAMME));
        assertEquals("Bureau de coordination du comiac", result.get(Constantes.KEY_MAP_SERVICE_DEPENSIER));
        assertEquals("11701290110", result.get(Constantes.KEY_MAP_CODE_SERVICE_DEPENSIER));
        assertEquals("Prestations Intellectuelles", result.get(Constantes.KEY_MAP_CATEGORIE_ACHAT));
        assertEquals("P", result.get(Constantes.KEY_MAP_CODE_CATEGORIE_ACHAT));
        assertEquals("DA TitleTestM", result.get(Constantes.KEY_MAP_TITRE));
        assertEquals("DA ObjetTestM", result.get(Constantes.KEY_MAP_OBJET));
        assertEquals("DA IntituléTestM", result.get(Constantes.KEY_MAP_INTITULE));

        String uploadString = result.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ);
        File file = AttachmentsUtils.checkUploadStringIsCorrect(uploadString);
        assertNotNull(file);
        assertEquals("piece jointe M.jpg", file.getName());
        assertEquals(new File("C:\\tmp"), file.getParentFile().getParentFile());
        assertTrue(file.exists());
    }

    @Test
    public void deleteDATest() throws ApplicationException, ConfigurationException, DatabaseUnitException, SQLException,
            FileNotFoundException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DADeleteService.xml");
        try {
            boolean result = demandeAchatService.deleteDA("123451", "beye");
            assertEquals(true, result);
            System.out.println("SUPPRESSION DA OK");
        } catch (Exception e) {
            System.out.println("SUPPRESSION DA KO");
            Assert.fail();
        }
    }

    @Test
    @Rollback(false)
    public void duplicateDATest() throws ApplicationException, ConfigurationException, DatabaseUnitException,
            SQLException, FileNotFoundException, TechnicalException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DADuplicateService.xml");

        // Duplication normale
        String result = demandeAchatService.duplicateDA("alardo", "123451", "M-7-2RQ23");
        assertEquals("", result);
        Map<String, String> newDaMap = demandeAchatService.getDAInformation("M-7-2RQ23");
        assertNotNull(result);
        assertEquals(StringToDate.convertDateToString(new Date()), newDaMap.get(Constantes.KEY_MAP_DATE_CREATION));
        assertEquals(StringToDate.convertDateToString(new Date()), newDaMap.get(Constantes.KEY_MAP_DATE_MODIFICATION));
        assertEquals("avec compte SANS MAIL", newDaMap.get(Constantes.KEY_MAP_DEMANDEUR));
        assertEquals("Brouillon", newDaMap.get(Constantes.KEY_MAP_STATUT));
        assertEquals(null, newDaMap.get(Constantes.KEY_MAP_STATUT_APPROBATION));
        assertEquals("Ministère des affaires etrangeres et des", newDaMap.get(Constantes.KEY_MAP_MINISTERE));
        assertEquals("31", newDaMap.get(Constantes.KEY_MAP_CODE_MINISTERE));
        assertEquals("Pilotage,  gestion et coordination administrative", newDaMap.get(Constantes.KEY_MAP_PROGRAMME));
        assertEquals("1001", newDaMap.get(Constantes.KEY_MAP_CODE_PROGRAMME));
        assertEquals("Service social mae", newDaMap.get(Constantes.KEY_MAP_SERVICE_DEPENSIER));
        assertEquals("11701440110", newDaMap.get(Constantes.KEY_MAP_CODE_SERVICE_DEPENSIER));
        assertEquals("Travaux", newDaMap.get(Constantes.KEY_MAP_CATEGORIE_ACHAT));
        assertEquals("T", newDaMap.get(Constantes.KEY_MAP_CODE_CATEGORIE_ACHAT));
        assertEquals("DA Test", newDaMap.get(Constantes.KEY_MAP_TITRE));
        assertEquals(null, newDaMap.get(Constantes.KEY_MAP_OBJET));
        assertEquals("", newDaMap.get(Constantes.KEY_MAP_INTITULE));

        Map<String, String[]> postes = posteDemandeAchatService.getItemsByDA("M-7-2RQ23");
        String[] numPostes = postes.get(Constantes.KEY_MAP_NUMERO);
        String[] references = postes.get(Constantes.KEY_MAP_REFERENCE);
        String[] designations = postes.get(Constantes.KEY_MAP_DESIGNATION);
        String[] qtesCommandees = postes.get(Constantes.KEY_MAP_QTE_COMMANDEE);
        String[] qtesAcceptees = postes.get(Constantes.KEY_MAP_QUANTITE);
        String[] statuts = postes.get(Constantes.KEY_MAP_STATUT);
        String[] etatDonnee = postes.get(Constantes.KEY_MAP_DATA_STATUS);
        
        
        for (int i = 0; i < 3; i++) {
            Map<String, String> mapItemsDataDA = posteDemandeAchatService.getPosteDAInformation("M-7-2RQ23", numPostes[i]);
            assertEquals(String.valueOf(i+1), mapItemsDataDA.get(Constantes.KEY_MAP_CODE_LIEU_STOCKAGE));
        }

        String[] expnumPostes = { "1", "2", "3" };
        String[] expreferences = { "(S) PAPIER", "(F) 623901", "(I) " };
        String[] expdesignations = { "Ramette Papier A4", "Produits alimentaires : blé" , "Produits alimentaires : blé" };
        String[] expqtesCommandees = { null, null, null };
        String[] expqtesAcceptees = { "23 (SAC)", "5 (NC)", "5 (NC)" };
        String[] expstatuts = { null, null, null };
        String[] expEtatDonnee = { "Ok", "Ok", "Ok" };

        Assert.assertArrayEquals(expnumPostes, numPostes);
        Assert.assertArrayEquals(expreferences, references);
        Assert.assertArrayEquals(expdesignations, designations);
        Assert.assertArrayEquals(expqtesCommandees, qtesCommandees);
        Assert.assertArrayEquals(expqtesAcceptees, qtesAcceptees);
        Assert.assertArrayEquals(expstatuts, statuts);
        Assert.assertArrayEquals(expEtatDonnee, etatDonnee);

        // Da non duplicable car données de l'entête inactives
        try {
            demandeAchatService.duplicateDA("alardo", "66666", "01234");
            fail("Une exception aurait dû se produire");
        } catch (ApplicationException e) {
            String msg = e.getMessage();
            assertTrue(msg.contains(
                    "Impossible de dupliquer la DA car les éléments suivants de son entête ont été supprimés : "));
            assertTrue(msg.contains("Demandeur"));
            assertTrue(msg.contains("Ministère"));
            assertTrue(msg.contains("Service dépensier"));
            assertTrue(msg.contains("Programme"));
        }

        // DA duplicables mais pas les postes
        result = demandeAchatService.duplicateDA("alardo", "55555", "AAAA");
        assertTrue(result.contains("Les postes suivants n'ont pas pu être dupliqués : "));
        assertTrue(result.contains("01"));
        assertTrue(result.contains("02"));

        newDaMap = demandeAchatService.getDAInformation("AAAA");
        assertNotNull(result);

        assertEquals("Catherine ALARDO", newDaMap.get(Constantes.KEY_MAP_CREATEUR));
        assertEquals("alardo", newDaMap.get(Constantes.KEY_MAP_LOGIN_CREATEUR));
        assertEquals(StringToDate.convertDateToString(new Date()), newDaMap.get(Constantes.KEY_MAP_DATE_CREATION));
        assertEquals(StringToDate.convertDateToString(new Date()), newDaMap.get(Constantes.KEY_MAP_DATE_MODIFICATION));
        assertEquals("Mamadou NDIR", newDaMap.get(Constantes.KEY_MAP_DEMANDEUR));
        assertEquals("Brouillon", newDaMap.get(Constantes.KEY_MAP_STATUT));
        assertEquals(null, newDaMap.get(Constantes.KEY_MAP_STATUT_APPROBATION));

        postes = posteDemandeAchatService.getItemsByDA("AAAA");
        assertNull(postes);

        // Da non duplicable car elle n'existe pas
        try {
            demandeAchatService.duplicateDA("alardo", "777", "01234");
            fail("Une exception aurait dû se produire");
        } catch (ApplicationException e) {
            String msg = e.getMessage();
            assertEquals("Impossible de dupliquer la DA '777' car elle n'existe pas.", msg);
        }

        // Da non duplicable car la DA cible existe déjà
        try {
            demandeAchatService.duplicateDA("alardo", "55555", "123451");
            fail("Une exception aurait dû se produire");
        } catch (ApplicationException e) {
            String msg = e.getMessage();
            assertEquals(
                    "Impossible de dupliquer la DA '55555' pour créer la DA '123451' car cette dernière existe déjà.",
                    msg);
        }
    }

    @Test
    public void getDataImpactTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException,
            SQLException, ApplicationException, TechnicalException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DAServiceDataImpact.xml");
        String result = demandeAchatService.getDataImpact("123451");
        assertNotNull(result);
        String regex = "^Champs modifiés : (Ministère, Service dépensier|Service dépensier, Ministère)<BR/>"
                + "Champs supprimés : (Programme, Demandeur|Demandeur, Programme)$";
        assertTrue(Pattern.matches(regex, result));
        result = demandeAchatService.getDataImpact("S-7-B8LPV");
        assertNull(result);
        result = demandeAchatService.getDataImpact("S-7-2RQL7");
        assertNotNull(result);
        assertEquals("Champs supprimés : Catégorie achat", result);

    }

    /**
     * Teste l'enregistrement d'une DA avec PJ et avec des Postes DA.
     */
    @Test
    @Rollback(false)
    public void saveDAwithPjAndPostesPJTest()
            throws ApplicationException, ConfigurationException, DatabaseUnitException, SQLException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DAService.xml");
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\DA"));
        String login = "beye";
        String requesterLogin = "bouchot";
        String ministeryCode = "21";
        String spendingServiceCode = "11701290110";
        String programCode = "1011";
        String purchasingCategoriesCode = "P";
        Date creationDate = new Date();
        String statutAvancement = "Brouillon";

        String intitulePJ = "DA IntituléTestM";
        String demandTitle = "DA TitleTestM";
        String objectDemand = "DA ObjetTestM";
        String numTeledossier = "numeroM"; // A changer chauqe fois
        String path = "C:" + File.separator + "tmp" + File.separator + "piece jointe M.jpg";

        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJ = "piece jointe M.jpg C%3A%5Ctmp%2Fpiece%20jointe%20M.jpg";

        List<Map<String, Object>> listePostesDAmap = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>(); // Poste qui a une PJ
        map1.put("NumeroPosteDA", "numero4");
        map1.put("Type", "F");
        map1.put("Categorie", "620101");
        map1.put("Designation", "Designation 4");
        map1.put("Quantite", 23);
        map1.put("Unite", "SAC");
        map1.put("DateLivraisonSouhaite", new Date());
        map1.put("Commentaire", "Commentaire 1 OK");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pda 4.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJPda = "piece pda 4.jpg C%3A%5Ctmp%2Fpiece%20pda%204.jpg";
        map1.put("NumeroPosteDA", "01");
        map1.put("UploadStringPJ", uploadStringPJPda);
        map1.put("IntitulePJ", "Intitulé PJPDA 4");
        map1.put("ServiceBeneficiaire", "11100110100");
        map1.put("Programme", "1008");
        map1.put("Action", "1001-01");
        map1.put("Activite", "act-2");
        map1.put("Fond", "ETAT");
        map1.put("Civilite", "Monsieur");
        map1.put("Nom", "Marc");
        map1.put("Rue", "Félix");
        map1.put("Numero", "58");
        map1.put("Ville", "1123");
        map1.put("Region", "1");
        map1.put("CodePostal", "48000");
        map1.put("Telephone", "339562584");
        map1.put("Portable", "772389011");
        map1.put("Email", "e.mail1@atos.net");
        listePostesDAmap.add(map1);

        Map<String, Object> map2 = new HashMap<>(); // Poste qui n'a pas de PJ
        map2.put("NumeroPosteDA", "02");
        map2.put("Type", "F");
        map2.put("Categorie", "620501");
        map2.put("Designation", "Designation 5");
        map2.put("Quantite", 800);
        map2.put("Unite", "BT");
        map2.put("DateLivraisonSouhaite", new Date());
        map2.put("Commentaire", "Commentaire 5 OK");
        map2.put("UploadStringPJ", null);
        map2.put("IntitulePJ", "Intitulé PJPDA 5");
        map2.put("ServiceBeneficiaire", "11701540111");
        map2.put("Programme", "1017");
        map2.put("Action", "1017-02");
        map2.put("Activite", "act-7");
        map2.put("Fond", "ETAT");
        map2.put("Civilite", "Madame");
        map2.put("Nom", "FAYETTE");
        map2.put("Rue", "ESCLANGON");
        map2.put("Numero", "23");
        map2.put("Ville", "1111");
        map2.put("Region", "1");
        map2.put("CodePostal", "44000");
        map2.put("Telephone", "339582584");
        map2.put("Portable", "772389012");
        map2.put("Email", "e.mail2@atos.net");
        listePostesDAmap.add(map2);

        Map<String, Object> map3 = new HashMap<>(); // Poste avec qques champs
                                                    // manquants qui a
        // une PJ
        map3.put("NumeroPosteDA", "03");
        map3.put("Type", "F");
        map3.put("Categorie", "620601");
        map3.put("Designation", "Designation 6");
        map3.put("Quantite", 2);
        map3.put("Unite", "PAQ");
        map3.put("DateLivraisonSouhaite", new Date());
        map3.put("Commentaire", "Commentaire 6 OK");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pda 6.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJPda = "piece pda 6.jpg C%3A%5Ctmp%2Fpiece%20pda%206.jpg";
        map3.put("UploadStringPJ", uploadStringPJPda);
        map3.put("IntitulePJ", "Intitulé PJPDA 6");
        map3.put("ServiceBeneficiaire", "11100110100");
        map3.put("Programme", "1008");
        map3.put("Action", null);
        map3.put("Activite", "act-2");
        map3.put("Fond", "ETAT");
        map3.put("Civilite", "Monsieur");
        map3.put("Nom", "Marc");
        map3.put("Rue", "Félix");
        map3.put("Numero", "58");
        map3.put("Ville", null);
        map3.put("Region", "1");
        map3.put("CodePostal", "48000");
        map3.put("Telephone", "339562584");
        map3.put("Portable", null);
        map3.put("Email", "e.mail3@atos.net");
        map3.put("LieuStockage", "1");
        listePostesDAmap.add(map3);

        try {
            boolean resultat = demandeAchatService.saveDAwithPJ(login, ministeryCode, spendingServiceCode, programCode,
                    creationDate, requesterLogin, purchasingCategoriesCode, demandTitle, objectDemand, uploadStringPJ,
                    intitulePJ, listePostesDAmap, numTeledossier, statutAvancement);
            assertEquals(true, resultat);
            // Vérification de la copie des PJ

            String emplacementPj = this.getDataFromTable(
                    "Select pj.* FROM piece_jointe pj, demande_achat da Where pj.idDa=da.id", "emplacement");
            assertNotNull(emplacementPj);
            File pjFile = new File(emplacementPj);
            assertEquals("C:\\tmp\\PJ\\DA\\numeroM", pjFile.getParent());
            assertTrue(pjFile.exists());
            
            emplacementPj =
                    this.getDataFromTable("Select pj.* FROM piece_jointe pj, poste_demande_achat pda, demande_achat da "
                            + "Where pj.idPosteDa=pda.id and pda.idDA=da.id AND pda.idDAPoste='01' AND da.numeroDossier='"
                            + numTeledossier + "'", "emplacement");
            assertNotNull(emplacementPj);
            pjFile = new File(emplacementPj);
            assertEquals("C:\\tmp\\PJ\\DA\\numeroM", pjFile.getParent());
            assertTrue(pjFile.exists());
            emplacementPj =
                    this.getDataFromTable("Select pj.* FROM piece_jointe pj, poste_demande_achat pda, demande_achat da "
                            + "Where pj.idPosteDa=pda.id and pda.idDA=da.id AND pda.idDAPoste='02' AND da.numeroDossier='"
                            + numTeledossier + "'", "emplacement");
            assertNull(emplacementPj);
            emplacementPj =
                    this.getDataFromTable("Select pj.* FROM piece_jointe pj, poste_demande_achat pda, demande_achat da "
                            + "Where pj.idPosteDa=pda.id and pda.idDA=da.id AND pda.idDAPoste='03' AND da.numeroDossier='"
                            + numTeledossier + "'", "emplacement");
            assertNotNull(emplacementPj);
            pjFile = new File(emplacementPj);
            assertEquals("C:\\tmp\\PJ\\DA\\numeroM", pjFile.getParent());
            assertTrue(pjFile.exists());
            System.out.println("SAVE DA WITH PJ AND PDA OK");
        } catch (Exception e) {
            System.out.println("SAVE DA WITH PJ AND PDA KO");
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }


    /**
     * Teste de modification d'une DA avec PJ et avec des Postes DA.
     */
    @Test
    @Rollback(false)
    public void updateDAwithPjAndPostesPJRollbackTest()
            throws ApplicationException, ConfigurationException, DatabaseUnitException, SQLException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/DAService/UpdateDa.xml");
        FileUtils.deleteQuietly(new File("C:\\tmp\\PJ\\DA"));
        
        String pathDA = this.getDataFromTable(
                "Select pj.* FROM piece_jointe pj, demande_achat da Where pj.idDa=da.id", "emplacement");        
        
        // Use relative path for Unix systems
        File f = new File(pathDA);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        
        String path1Pda =
                this.getDataFromTable("Select pj.* FROM piece_jointe pj, poste_demande_achat pda, demande_achat da "
                        + "Where pj.idPosteDa=pda.id and pda.idDA=da.id AND pda.idDAPoste='01' AND da.numeroDossier='numeroM'", "emplacement");
        // Use relative path for Unix systems
         f = new File(path1Pda);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        
        String path2Pda =
                this.getDataFromTable("Select pj.* FROM piece_jointe pj, poste_demande_achat pda, demande_achat da "
                        + "Where pj.idPosteDa=pda.id and pda.idDA=da.id AND pda.idDAPoste='03' AND da.numeroDossier='numeroM'", "emplacement");
        // Use relative path for Unix systems
         f = new File(path2Pda);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        
        
        String login = "beye";
        String requesterLogin = "bouchot";
        String ministeryCode = "21";
        String spendingServiceCode = "11701290110";
        String programCode = "1011";
        String purchasingCategoriesCode = "P";
        Date creationDate = new Date();
        String statutAvancement = "Brouillon";

        String intitulePJ = "DA IntituléTestMUpd";
        String demandTitle = "DA TitleTestMUpd";
        String objectDemand = "DA ObjetTestMUpd";
        String numTeledossier = "numeroM"; 
        String path = "C:" + File.separator + "tmp" + File.separator + "piece jointe MUpd.jpg";

        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJ = "piece jointe MUpd.jpg C%3A%5Ctmp%2Fpiece%20jointe%20MUpd.jpg";

        List<Map<String, Object>> listePostesDAmap = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>(); // Poste qui a une PJ
        map1.put("Type", "F");
        map1.put("Categorie", "620101");
        map1.put("Designation", "Designation 4");
        map1.put("Quantite", 23);
        map1.put("Unite", "SAC");
        map1.put("DateLivraisonSouhaite", new Date());
        map1.put("Commentaire", "Commentaire 1 OK");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pda 4upd.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        String uploadStringPJPda = "piece pda 4upd.jpg C%3A%5Ctmp%2Fpiece%20pda%204upd.jpg";
        map1.put("NumeroPosteDA", "01");
        map1.put("UploadStringPJ", uploadStringPJPda);
        map1.put("IntitulePJ", "Intitulé PJPDA 4upd");
        map1.put("ServiceBeneficiaire", "11100110100");
        map1.put("Programme", "1008");
        map1.put("Action", "1001-01");
        map1.put("Activite", "act-2");
        map1.put("Fond", "ETAT");
        map1.put("Civilite", "Madame");
        map1.put("Nom", "Marc");
        map1.put("Rue", "Félixupd");
        map1.put("Numero", "58");
        map1.put("Ville", "1123");
        map1.put("Region", "1");
        map1.put("CodePostal", "48000");
        map1.put("Telephone", "339562584");
        map1.put("Portable", "772389011");
        map1.put("Email", "e.mail1@atos.netupd");
        listePostesDAmap.add(map1);

        Map<String, Object> map2 = new HashMap<>(); // Poste qui n'a pas de PJ
        map2.put("NumeroPosteDA", "02");
        map2.put("Type", "F");
        map2.put("Categorie", "620501");
        map2.put("Designation", "Designation 5upd");
        map2.put("Quantite", 800);
        map2.put("Unite", "BT");
        map2.put("DateLivraisonSouhaite", new Date());
        map2.put("Commentaire", "Commentaire 5 OK");
        map2.put("UploadStringPJ", null);
        map2.put("IntitulePJ", "Intitulé PJPDA 5");
        map2.put("ServiceBeneficiaire", "11701540111");
        map2.put("Programme", "1017");
        map2.put("Action", "1017-02");
        map2.put("Activite", "act-7");
        map2.put("Fond", "ETAT");
        map2.put("Civilite", "Madame");
        map2.put("Nom", "FAYETTEupd");
        map2.put("Rue", "ESCLANGON");
        map2.put("Numero", "23");
        map2.put("Ville", "1111");
        map2.put("Region", "1");
        map2.put("CodePostal", "44000");
        map2.put("Telephone", "339582584");
        map2.put("Portable", "772389012");
        map2.put("Email", "e.mail2@atos.net");
        listePostesDAmap.add(map2);

        Map<String, Object> map3 = new HashMap<>(); // Poste avec qques champs
                                                    // manquants qui a
        // une PJ
        map3.put("NumeroPosteDA", "03");
        map3.put("Type", "F");
        map3.put("Categorie", "620601");
        map3.put("Designation", "Designation 6");
        map3.put("Quantite", 2);
        map3.put("Unite", "PAQ");
        map3.put("DateLivraisonSouhaite", new Date());
        map3.put("Commentaire", "Commentaire 6 OK");
        path = "C:" + File.separator + "tmp" + File.separator + "piece pda 6.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        uploadStringPJPda = "piece pda 7.jpg C%3A%5Ctmp%2Fpiece%20pda%206.jpg";
        map3.put("UploadStringPJ", uploadStringPJPda);
        map3.put("IntitulePJ", "Intitulé PJPDA 6");
        map3.put("ServiceBeneficiaire", "11100110100");
        map3.put("Programme", "1008");
        map3.put("Action", null);
        map3.put("Activite", "act-2");
        map3.put("Fond", "ETAT");
        map3.put("Civilite", "Monsieur");
        map3.put("Nom", "Marc");
        map3.put("Rue", "Félix");
        map3.put("Numero", "58");
        map3.put("Ville", null);
        map3.put("Region", "1");
        map3.put("CodePostal", "48000");
        map3.put("Telephone", "339562584");
        map3.put("Portable", null);
        map3.put("Email", "e.mail3@atos.net");
        map3.put("LieuStockage", "1");
        listePostesDAmap.add(map3);

        try {
            demandeAchatService.saveDAwithPJ(login, ministeryCode, spendingServiceCode, programCode,
                    creationDate, requesterLogin, purchasingCategoriesCode, demandTitle, objectDemand, uploadStringPJ,
                    intitulePJ, listePostesDAmap, numTeledossier, statutAvancement);
            fail("La sauvegarde doit échouer !");
        } catch (Exception e) {
            assertEquals(4, this.demandeAchatService.getAll().size());
            assertTrue(new File(pathDA).isFile());
            assertTrue(new File(path1Pda).isFile());
            assertTrue(new File(path2Pda).isFile());
//            File folderPj = new File("C:\\tmp\\PJ\\DA\\numeroM");
//            assertFalse(folderPj.exists());
        }
    }
}
