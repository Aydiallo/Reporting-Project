package com.sigif.service.csf;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.service.PosteCsfService;
import com.sigif.testutil.AbstractDbTestCase;
import com.sigif.util.Constantes;
import com.sigif.util.StringToDate;

/**
 * 
 * @author Mamadou Ndir 2 juin 2017 17:40:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
@Transactional
public class PosteCsfServiceTest extends AbstractDbTestCase {

    @Autowired
    private PosteCsfService posteCsfService;

    /*
     * @Before public void loadData() throws FileNotFoundException,
     * ConfigurationException, DatabaseUnitException, SQLException {
     * this.emptyDatabase(); this.loadDataFileAndOverWrite(
     * "dataTestFiles/posteCAService/posteCAService.xml"); }
     */

    @Test
    public void getPostesCsfByPosteCaTest()
            throws ConfigurationException, DatabaseUnitException, SQLException, FileNotFoundException, ApplicationException, TechnicalException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/posteCSFService/posteCSFByCa.xml");

        Map<String, Object[]> mapInfosPostes = this.posteCsfService.getPostesCsfByPosteCa("9210000010", "05", null);
        assertTrue(!mapInfosPostes.isEmpty());

        Object[] numDossierCsf = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_CSF);
        Object[] idCSFPoste = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_POSTE_CSF);
        Object[] dateReception = mapInfosPostes.get(Constantes.KEY_MAP_DATE_RECEPTION);
        Object[] receptionnaire = mapInfosPostes.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
        Object[] quantiteRecue = mapInfosPostes.get(Constantes.KEY_MAP_QTE_RECUE);
        Object[] quantiteAcceptee = mapInfosPostes.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
        Object[] statutAvancement = mapInfosPostes.get(Constantes.KEY_MAP_STATUT);

        String[] expnumDossierCsf = { "123456789012" };
        String[] expidCSFPoste = { "01" };
        String[] expdateReception = { "22/05/2017" };
        String[] expreceptionnaire = { "Catherine ALARDO" };
        String[] expquantiteRecue = { "1 (Pièce)" };
        String[] expquantiteAcceptee = { "1 (Pièce)" };
        String[] expstatutAvancement = { "En attente de validation" };

        Assert.assertArrayEquals(expnumDossierCsf, numDossierCsf);
        Assert.assertArrayEquals(expidCSFPoste, idCSFPoste);

        for (int i = 0; i< dateReception.length; i++) {
            dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
        }
        Assert.assertArrayEquals(expdateReception, dateReception);
        Assert.assertArrayEquals(expreceptionnaire, receptionnaire);
        Assert.assertArrayEquals(expquantiteRecue, quantiteRecue);
        Assert.assertArrayEquals(expquantiteAcceptee, quantiteAcceptee);
        Assert.assertArrayEquals(expstatutAvancement, statutAvancement);

        mapInfosPostes = this.posteCsfService.getPostesCsfByPosteCa("9210000010", "06", null);
        assertTrue(!mapInfosPostes.isEmpty());

        numDossierCsf = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_CSF);
        idCSFPoste = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_POSTE_CSF);
        dateReception = mapInfosPostes.get(Constantes.KEY_MAP_DATE_RECEPTION);
        receptionnaire = mapInfosPostes.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
        quantiteRecue = mapInfosPostes.get(Constantes.KEY_MAP_QTE_RECUE);
        quantiteAcceptee = mapInfosPostes.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
        statutAvancement = mapInfosPostes.get(Constantes.KEY_MAP_STATUT);

        String[] expnumDossierCsf2 = { "123456789013", "123456789014" };
        String[] expidCSFPoste2 = { "02", "01" };
        String[] expdateReception2 = { "22/05/2017", "22/05/2017" };
        String[] expreceptionnaire2 = { "Angélique REYNAUD", "Mickaël BEAUPOIL" };
        String[] expquantiteRecue2 = { "2 (Pièce)", "5 (Pièce)" };
        String[] expquantiteAcceptee2 = { "1 (Pièce)", "2 (Pièce)" };
        String[] expstatutAvancement2 = { "En attente de validation", "En attente de validation" };

        for (int i = 0; i< dateReception.length; i++) {
            dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
        }
        Assert.assertArrayEquals(expnumDossierCsf2, numDossierCsf);
        Assert.assertArrayEquals(expidCSFPoste2, idCSFPoste);
        Assert.assertArrayEquals(expdateReception2, dateReception);
        Assert.assertArrayEquals(expreceptionnaire2, receptionnaire);
        Assert.assertArrayEquals(expquantiteRecue2, quantiteRecue);
        Assert.assertArrayEquals(expquantiteAcceptee2, quantiteAcceptee);
        Assert.assertArrayEquals(expstatutAvancement2, statutAvancement);

        mapInfosPostes = this.posteCsfService.getPostesCsfByPosteCa("9210000010", "06", "123456789013");
        assertTrue(!mapInfosPostes.isEmpty());

        numDossierCsf = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_CSF);
        idCSFPoste = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_POSTE_CSF);
        dateReception = mapInfosPostes.get(Constantes.KEY_MAP_DATE_RECEPTION);
        receptionnaire = mapInfosPostes.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
        quantiteRecue = mapInfosPostes.get(Constantes.KEY_MAP_QTE_RECUE);
        quantiteAcceptee = mapInfosPostes.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
        statutAvancement = mapInfosPostes.get(Constantes.KEY_MAP_STATUT);

        for (int i = 0; i< dateReception.length; i++) {
            dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
        }
        String[] expnumDossierCsf3 = { "123456789014" };
        String[] expidCSFPoste3 = { "01" };
        String[] expdateReception3 = { "22/05/2017" };
        String[] expreceptionnaire3 = { "Mickaël BEAUPOIL" };
        String[] expquantiteRecue3 = { "5 (Pièce)" };
        String[] expquantiteAcceptee3 = { "2 (Pièce)" };
        String[] expstatutAvancement3 = { "En attente de validation" };

        Assert.assertArrayEquals(expnumDossierCsf3, numDossierCsf);
        Assert.assertArrayEquals(expidCSFPoste3, idCSFPoste);
        Assert.assertArrayEquals(expdateReception3, dateReception);
        Assert.assertArrayEquals(expreceptionnaire3, receptionnaire);
        Assert.assertArrayEquals(expquantiteRecue3, quantiteRecue);
        Assert.assertArrayEquals(expquantiteAcceptee3, quantiteAcceptee);
        Assert.assertArrayEquals(expstatutAvancement3, statutAvancement);

        mapInfosPostes = this.posteCsfService.getPostesCsfByPosteCa("9210000010", "06", "123456789014");
        assertTrue(!mapInfosPostes.isEmpty());

        numDossierCsf = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_CSF);
        idCSFPoste = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_POSTE_CSF);
        dateReception = mapInfosPostes.get(Constantes.KEY_MAP_DATE_RECEPTION);
        receptionnaire = mapInfosPostes.get(Constantes.KEY_MAP_RECEPTIONNAIRE);
        quantiteRecue = mapInfosPostes.get(Constantes.KEY_MAP_QTE_RECUE);
        quantiteAcceptee = mapInfosPostes.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
        statutAvancement = mapInfosPostes.get(Constantes.KEY_MAP_STATUT);

        String[] expnumDossierCsf4 = { "123456789013" };
        String[] expidCSFPoste4 = { "02" };
        String[] expdateReception4 = { "22/05/2017" };
        String[] expreceptionnaire4 = { "Angélique REYNAUD" };
        String[] expquantiteRecue4 = { "2 (Pièce)" };
        String[] expquantiteAcceptee4 = { "1 (Pièce)" };
        String[] expstatutAvancement4 = { "En attente de validation" };

        for (int i = 0; i< dateReception.length; i++) {
            dateReception[i] = StringToDate.convertDateToString((Date) dateReception[i]);
        }
        Assert.assertArrayEquals(expnumDossierCsf4, numDossierCsf);
        Assert.assertArrayEquals(expidCSFPoste4, idCSFPoste);
        Assert.assertArrayEquals(expdateReception4, dateReception);
        Assert.assertArrayEquals(expreceptionnaire4, receptionnaire);
        Assert.assertArrayEquals(expquantiteRecue4, quantiteRecue);
        Assert.assertArrayEquals(expquantiteAcceptee4, quantiteAcceptee);
        Assert.assertArrayEquals(expstatutAvancement4, statutAvancement);

    }

    @Test
    public void getItemsCSFTest()
            throws ConfigurationException, DatabaseUnitException, SQLException, FileNotFoundException {
        try {
            this.emptyDatabase();
            this.loadDataFileAndOverWrite("dataTestFiles/posteCSFService/posteCsfService.xml");
            Map<String, String[]> mapInfosPostes = this.posteCsfService.getItemsCsf("123456789012");
            assertTrue(!mapInfosPostes.isEmpty());

            String[] numCAPoste = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_CA_POSTE);
            String[] numPosteCsf = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_POSTE_CSF);
            String[] reference = mapInfosPostes.get(Constantes.KEY_MAP_REFERENCE);
            String[] designation = mapInfosPostes.get(Constantes.KEY_MAP_DESIGNATION);
            String[] quantiteCommandee = mapInfosPostes.get(Constantes.KEY_MAP_QTE_COMMANDEE);
            String[] quantiteRecue = mapInfosPostes.get(Constantes.KEY_MAP_QTE_RECUE);
            String[] quantiteAcceptee = mapInfosPostes.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
            String[] statutAvancement = mapInfosPostes.get(Constantes.KEY_MAP_STATUT);

            String[] expnumCAPoste = { "9210000010/05" };
            String[] expnumPosteCsf = { "01" };
            String[] expreference = { "PAPIER" };
            String[] expdesignation = { "Ramette Papier A4" };
            String[] expquantiteCommandee = { "39 (Pièce)" };
            String[] expquantiteRecue = { "30 (Pièce)" };
            String[] expquantiteAcceptee = { "30 (Pièce)" };
            String[] expstatutAvancement = { "En attente de validation" };

            assertEquals(expnumCAPoste[0], numCAPoste[0]);
            assertEquals(expnumPosteCsf[0], numPosteCsf[0]);
            assertEquals(expreference[0], reference[0]);
            assertEquals(expdesignation[0], designation[0]);
            assertEquals(expquantiteCommandee[0], quantiteCommandee[0]);
            assertEquals(expquantiteRecue[0], quantiteRecue[0]);
            assertEquals(expquantiteAcceptee[0], quantiteAcceptee[0]);
            assertEquals(expstatutAvancement[0], statutAvancement[0]);
        } catch (ApplicationException | TechnicalException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getPosteCSFInfoTest()
            throws ConfigurationException, DatabaseUnitException, SQLException, FileNotFoundException {
        try {
            this.emptyDatabase();
            this.loadDataFileAndOverWrite("dataTestFiles/posteCSFService/posteCsfService.xml");
            Map<String, String> mapInfosPostes = this.posteCsfService.getPosteCSFInfos("123456789012", "01");
            assertTrue(!mapInfosPostes.isEmpty());

            String avancement = mapInfosPostes.get(Constantes.KEY_MAP_STATUT_AVANCEMENT);
            String numPosteCsf = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_POSTE_CSF);
            String commentaire = mapInfosPostes.get(Constantes.KEY_MAP_COMMENTAIRE);
            String numCaPoste = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO_CA_POSTE);
            String typeAchat = mapInfosPostes.get(Constantes.KEY_MAP_TYPE_ACHAT);
            String categorie = mapInfosPostes.get(Constantes.KEY_MAP_CATEGORIE);
            String reference = mapInfosPostes.get(Constantes.KEY_MAP_REFERENCE);
            String designation = mapInfosPostes.get(Constantes.KEY_MAP_DESIGNATION);
            String qteCommandee = mapInfosPostes.get(Constantes.KEY_MAP_QTE_COMMANDEE);
            String qteRecue = mapInfosPostes.get(Constantes.KEY_MAP_QTE_RECUE);
            String qteAcceptee = mapInfosPostes.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
            String acceptation = mapInfosPostes.get(Constantes.KEY_MAP_STATUT_ACCEPTATION_CSF);

            String expavancement = "En attente de validation";
            String expnumPosteCsf = "01";
            String expCommentaire = "gfdgfd";
            String expnumCaPoste = "9210000010/05";
            String expcategorie = "Rame, paquet et rouleau de papier";
            String exptypeAchat = "Achats stockés";
            String expreference = "PAPIER";
            String expdesignation = "Ramette Papier A4";
            String expqteCommandee = "39 (Pièce)";
            String expqteRecue = "30 (Pièce)";
            String expqteAcceptee = "30 (Pièce)";
            String expacceptation = "Acceptée";

            assertEquals(avancement, expavancement);
            assertEquals(numPosteCsf, expnumPosteCsf);
            assertEquals(commentaire, expCommentaire);
            assertEquals(numCaPoste, expnumCaPoste);
            assertEquals(typeAchat, exptypeAchat);
            assertEquals(categorie, expcategorie);
            assertEquals(reference, expreference);
            assertEquals(designation, expdesignation);
            assertEquals(qteCommandee, expqteCommandee);
            assertEquals(qteRecue, expqteRecue);
            assertEquals(qteAcceptee, expqteAcceptee);
            assertEquals(acceptation, expacceptation);
            assertEquals("parce queeeeeeeeee", mapInfosPostes.get(Constantes.KEY_MAP_MOTIF_REJET));
            assertEquals("DK43-DMTA Dakar", mapInfosPostes.get(Constantes.KEY_MAP_LIEU_STOCKAGE));

        } catch (ApplicationException | TechnicalException e) {
            e.printStackTrace();
            fail();
        }
    }

}
