package com.sigif.service.da;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.service.PosteDaService;
import com.sigif.testutil.AbstractDbTestCase;
import com.sigif.util.AttachmentsUtils;
import com.sigif.util.Constantes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
@Transactional
public class PosteDAServiceTest extends AbstractDbTestCase {

	@Autowired
	private PosteDaService posteDaService;

	@Test
	public void getItemsByDATest() throws ApplicationException, TechnicalException, FileNotFoundException,
			ConfigurationException, DatabaseUnitException, SQLException {
		try {
			this.emptyDatabase();
			this.loadDataFileAndOverWrite("dataTestFiles/PostDAService/PosteDAService.xml");
			Map<String, String[]> posteDAListMap = posteDaService.getItemsByDA("SIGIFNUM1");
			assertTrue(!posteDAListMap.isEmpty());

			String[] ids = posteDAListMap.get(Constantes.KEY_MAP_ID);
			String[] numPostes = posteDAListMap.get(Constantes.KEY_MAP_NUMERO);
			String[] references = posteDAListMap.get(Constantes.KEY_MAP_REFERENCE);
			String[] designations = posteDAListMap.get(Constantes.KEY_MAP_DESIGNATION);
			String[] qtesCommandees = posteDAListMap.get(Constantes.KEY_MAP_QTE_COMMANDEE);
			String[] qtesAcceptees = posteDAListMap.get(Constantes.KEY_MAP_QUANTITE);
			String[] statuts = posteDAListMap.get(Constantes.KEY_MAP_STATUT);
			String[] etatDonnee = posteDAListMap.get(Constantes.KEY_MAP_DATA_STATUS);

			String[] expids = { "1" };
			String[] expnumPostes = { "1" };
			String[] expreferences = { "(S) PAPIER" };
			String[] expdesignations = { "Ramette Papier A4" };
			String[] expqtesCommandees = { "3 (SAC)" };
			String[] expqtesAcceptees = { "5 (SAC)" };
			String[] expstatuts = { "Approuvée" };
			String[] expEtatDonnee = { "Ok" };

			assertEquals(expids[0], ids[0]);
			assertEquals(expnumPostes[0], numPostes[0]);
			assertEquals(expreferences[0], references[0]);
			assertEquals(expdesignations[0], designations[0]);
			assertEquals(expqtesCommandees[0], qtesCommandees[0]);
			assertEquals(expqtesAcceptees[0], qtesAcceptees[0]);
			assertEquals(expstatuts[0], statuts[0]);
			assertEquals(expEtatDonnee[0], etatDonnee[0]);
		} catch (ApplicationException | TechnicalException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void getAlertPosteDATest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException,
			SQLException, ApplicationException, TechnicalException {
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/PostDAService/PosteDAService.xml");
		String result = posteDaService.getDataImpact("SIGIFNUM1","1");
		System.out.println("rst : "+result);
		assertNotNull(result);
	}


    @Test
    public void getPosteDAInformationVraiTest() throws ApplicationException, TechnicalException, ConfigurationException, DatabaseUnitException, SQLException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/PostDAService/PosteDaPjTest.xml");

        String path = "C:\\tmp\\PJ\\DA\\numeroM\\numeroM_12.jpg";
        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        Map<String, String> posteDAListMap = posteDaService.getPosteDAInformation("numeroM", "03");
        assertNotNull(posteDAListMap);

        assertEquals("Cabinet du ministre", posteDAListMap.get(Constantes.KEY_MAP_SERVICE_BENEFICIAIRE));
        assertEquals("11100110100", posteDAListMap.get(Constantes.KEY_MAP_CODE_SERVICE_BENEFICIAIRE));
        assertEquals("Etat", posteDAListMap.get(Constantes.KEY_MAP_FOND));
        assertEquals("ETAT", posteDAListMap.get(Constantes.KEY_MAP_CODE_FOND));
        assertEquals("Fonctionnement", posteDAListMap.get(Constantes.KEY_MAP_TYPE_ACHAT));
        assertEquals("F", posteDAListMap.get(Constantes.KEY_MAP_CODE_TYPE_ACHAT));
        assertEquals("Consommables pour impression ", posteDAListMap.get(Constantes.KEY_MAP_CATEGORIE));
        assertEquals("620601", posteDAListMap.get(Constantes.KEY_MAP_REFERENCE));
        assertEquals("Designation 6", posteDAListMap.get(Constantes.KEY_MAP_DESIGNATION));
        assertEquals("Pilotage,  gestion et coordination administrative", posteDAListMap.get(Constantes.KEY_MAP_PROGRAMME));
        assertEquals("1008", posteDAListMap.get(Constantes.KEY_MAP_CODE_PROGRAMME));
        assertEquals("Stratégie ministérielle", posteDAListMap.get(Constantes.KEY_MAP_ACTION));
        assertEquals("1001-01", posteDAListMap.get(Constantes.KEY_MAP_CODE_ACTION));
        assertEquals("DESC2", posteDAListMap.get(Constantes.KEY_MAP_ACTIVITE));
        assertEquals("act-2", posteDAListMap.get(Constantes.KEY_MAP_CODE_ACTIVITE));
        assertEquals(null, posteDAListMap.get(Constantes.KEY_MAP_LIEU_STOCKAGE));
        assertEquals(null, posteDAListMap.get(Constantes.KEY_MAP_CODE_LIEU_STOCKAGE));
        assertEquals("2", posteDAListMap.get(Constantes.KEY_MAP_QUANTITE));
        assertEquals("Paquet", posteDAListMap.get(Constantes.KEY_MAP_UNITE));
        assertEquals("PAQ", posteDAListMap.get(Constantes.KEY_MAP_CODE_UNITE));
        assertEquals("Approuvée", posteDAListMap.get(Constantes.KEY_MAP_STATUT_APPROBATION));
        assertEquals(null, posteDAListMap.get(Constantes.KEY_MAP_QTE_COMMANDEE));
        assertEquals(null, posteDAListMap.get(Constantes.KEY_MAP_MOTIF_REJET));
        assertEquals("Commentaire 6 OK", posteDAListMap.get(Constantes.KEY_MAP_COMMENTAIRE));
        assertEquals("16/06/2017", posteDAListMap.get(Constantes.KEY_MAP_DATE_LIVRAISON));
        assertEquals("Monsieur", posteDAListMap.get(Constantes.KEY_MAP_CIVILITE));
        assertEquals("Marc", posteDAListMap.get(Constantes.KEY_MAP_NOM));
        assertEquals("Félix", posteDAListMap.get(Constantes.KEY_MAP_RUE));
        assertEquals("58", posteDAListMap.get(Constantes.KEY_MAP_NUMERO));
        assertEquals("48000", posteDAListMap.get(Constantes.KEY_MAP_CODE_POSTAL));
        assertEquals(null, posteDAListMap.get(Constantes.KEY_MAP_VILLE));
        assertEquals(null, posteDAListMap.get(Constantes.KEY_MAP_CODE_VILLE));
        assertEquals("DAKAR", posteDAListMap.get(Constantes.KEY_MAP_REGION));
        assertEquals("1", posteDAListMap.get(Constantes.KEY_MAP_CODE_REGION));
        assertEquals(null, posteDAListMap.get(Constantes.KEY_MAP_PORTABLE));
        assertEquals("339562584", posteDAListMap.get(Constantes.KEY_MAP_TELEPHONE));
        assertEquals("e.mail3@atos.net", posteDAListMap.get(Constantes.KEY_MAP_EMAIL));
        assertEquals("Intitulé PJPDA 6", posteDAListMap.get(Constantes.KEY_MAP_INTITULE_PJ));
        assertEquals(path, posteDAListMap.get(Constantes.KEY_MAP_PJ_PATH));
        assertEquals("Ok", posteDAListMap.get(Constantes.KEY_MAP_DATA_STATUS));
        String uploadString = posteDAListMap.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ);
        File file = AttachmentsUtils.checkUploadStringIsCorrect(uploadString);
        assertNotNull(file);
        assertEquals("piece pda 6.jpg", file.getName());
        assertEquals(new File("C:\\tmp"), file.getParentFile().getParentFile());
        assertTrue(file.exists());
    }
    
    @Test
    public void checkPosteDATest()
    	 throws ApplicationException, TechnicalException, FileNotFoundException,
			ConfigurationException, DatabaseUnitException, SQLException {
		try {
			this.emptyDatabase();
			this.loadDataFileAndOverWrite("dataTestFiles/PostDAService/PosteDAServiceCheckPoste.xml");
			List<Map<String, Object>> posteDAListMap = posteDaService.checkPostesDA("SIGIFNUM1");
			assertTrue(!posteDAListMap.isEmpty());
			assertEquals(2, posteDAListMap.size());
			Iterator<Map<String, Object>> it = posteDAListMap.iterator();
	        assertTrue(it.hasNext());
	        Map<String, Object> poste = (Map<String, Object>) it.next();
            assertEquals("Produits alimentaires : blé blé", poste.get(Constantes.KEY_MAP_DESIGNATION));
            assertTrue(it.hasNext());
            poste = (Map<String, Object>) it.next();
            assertEquals("01, 03", poste.get(Constantes.KEY_MAP_MSG_ERROR));
	        assertFalse(it.hasNext());	
			
		} catch (ApplicationException | TechnicalException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
    }

    @Test
    public void getDataImpactTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException,
            SQLException, ApplicationException, TechnicalException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/PostDAService/PosteDAServiceDataImpact.xml");
        String result = posteDaService.getDataImpact("123451", "01");
        assertNull(result);
        
        result = posteDaService.getDataImpact("S-7-2RQL7", "01");
        assertNotNull(result);
        String regex =
                "^Champs modifiés : Activité<BR/>"
                + "Champs supprimés : Action$";
        assertTrue(Pattern.matches(regex, result));
        
        result = posteDaService.getDataImpact("S-7-2RQL7", "02");

        assertNotNull(result);
        regex =
                "^Champs supprimés : (Lieu de stockage, Service bénéficiaire|Service bénéficiaire, Lieu de stockage)$";
        assertTrue(Pattern.matches(regex, result));

    }
    
    
}
