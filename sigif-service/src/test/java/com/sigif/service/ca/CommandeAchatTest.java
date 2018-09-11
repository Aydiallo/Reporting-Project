package com.sigif.service.ca;

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
import com.sigif.service.CommandeAchatService;
import com.sigif.testutil.AbstractDbTestCase;
import com.sigif.util.Constantes;
import com.sigif.util.StringToDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
@Transactional
public class CommandeAchatTest extends AbstractDbTestCase {

	@Autowired
	CommandeAchatService commandeAchatService;

	@Test
	public void countCaTest() throws TechnicalException, ApplicationException, FileNotFoundException,
			ConfigurationException, DatabaseUnitException, SQLException {
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/commandeAchatService/commandeAchatService.xml");
		// recuperer les CA dont un poste, au moins, à le service dépensier n°12
		int result = commandeAchatService.searchCANbResults("11700550100", "", "", "", "", "", "");
		assertNotNull(result);
		// 3 CA réponde à ce critère
		assertEquals(3, result);

		// recuperer CA selon son numméro
		result = commandeAchatService.searchCANbResults("92200820100", "9210000010", "", "", "", "", "");
		assertEquals(1, result);

		// recuperer CA selon le type de commande n°4
		result = commandeAchatService.searchCANbResults("11700550100", "", "MACC", "", "", "", "");
		assertEquals(1, result);

		// recuperer CA selon la categorie d'achat n°4
		result = commandeAchatService.searchCANbResults("12200820100", "", "", "T", "", "", "");
		assertEquals(1, result);

		// recuperer CA selon la date de création
		result = commandeAchatService.searchCANbResults("12200820100", "", "", "", "", "22/05/2017", "");
		assertEquals(5, result);

		// recuperer CA selon la date de création et le statut
		result = commandeAchatService.searchCANbResults("12200820100", "", "", "", "Partiellement réceptionnée",
				"22/05/2017", "");
		assertEquals(1, result);

		// recuperer CA selon la date de création et le statut
		result = commandeAchatService.searchCANbResults("12200820100", "", "", "", "Non réceptionnée", "22/05/2017",
				"");
		assertEquals(4, result);

		// recuperer CA selon la date de modification
		result = commandeAchatService.searchCANbResults("12200820100", "", "", "", "", "", "23/05/2017");
		assertEquals(4, result);

		// Test qui marche pas: recuperer des CA non receptionnable: ca n°.
		result = commandeAchatService.searchCANbResults("11400040100", "", "", "", "", "", "");
		assertEquals(0, result);

		// Test qui marche pas: recuperer un CA receptionnable mais qui contient
		// un(seul) poste non receptionnable
		result = commandeAchatService.searchCANbResults("11700560100", "", "", "", "", "", "");
		assertEquals(0, result);

	}

	@Test
	public void searchCATest() throws TechnicalException, ApplicationException, FileNotFoundException,
			ConfigurationException, DatabaseUnitException, SQLException {
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/commandeAchatService/commandeAchatService.xml");
		// Verifier que le resultat contient bien les 6 tableaux
		Map<String, Object[]> result = commandeAchatService.searchCA("11700550100", "", "", "", "", "", "", 100);
		// ce service depensier est lié à 3 CA
		assertNotNull(result);
		assertEquals(3, ((Object[]) result.get(Constantes.KEY_MAP_NUMERO)).length);

		result = commandeAchatService.searchCA("92200820100", "", "", "", "", "", "", 100);
		// ce service depensier est lié à 1 CA
		assertEquals(1, ((Object[]) result.get(Constantes.KEY_MAP_NUMERO)).length);
		// verifier les numéros de CA récuperés: la premier liste contient que
		// les numéros de CA
		assertEquals("9210000010", result.get(Constantes.KEY_MAP_NUMERO)[0]);
		// verifier le type de commande récuperé
		assertEquals("Charges", result.get(Constantes.KEY_MAP_TYPE_COMMANDE)[0]);
		// verifier le commentaire de la CA
		assertEquals("test CA 10", result.get(Constantes.KEY_MAP_COMMENTAIRE)[0]);
		// verifier la categorie d'achat
		assertEquals("Fournitures", result.get(Constantes.KEY_MAP_CATEGORIE_ACHAT)[0]);
		// verifier le nombre de CSF lié à la CA
		assertEquals("1", result.get(Constantes.KEY_MAP_NB_CSF)[0]);
		// les id de CA
		assertEquals("22", result.get(Constantes.KEY_MAP_ID)[0]);
		// le statut de CA
		assertEquals("Non réceptionnée", result.get(Constantes.KEY_MAP_STATUT)[0]);
		// la date de création de CA
		assertEquals("25/05/2017", StringToDate.convertDateToString((Date) result.get(Constantes.KEY_MAP_DATE_CREATION)[0]));

		// recuperer CA selon la date de création et le statut
		result = commandeAchatService.searchCA("12200820100", "", "", "", "Partiellement réceptionnée", "22/05/2017",
				"", 15);
		assertEquals(1, ((Object[]) result.get(Constantes.KEY_MAP_NUMERO)).length);
		assertEquals("9210000011", result.get(Constantes.KEY_MAP_NUMERO)[0]);

		// recuperer CA selon la date de création et le statut
		result = commandeAchatService.searchCA("12200820100", "", "", "", "Non réceptionnée", "22/05/2017", "", 2);
		assertEquals(2, ((Object[]) result.get(Constantes.KEY_MAP_NUMERO)).length);
		assertEquals("9210000010", result.get(Constantes.KEY_MAP_NUMERO)[0]);
		assertEquals("9210000009", result.get(Constantes.KEY_MAP_NUMERO)[1]);
	}

	@Test
	public void getCAInformationTest() throws ApplicationException, TechnicalException, FileNotFoundException,
			ConfigurationException, DatabaseUnitException, SQLException {
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/commandeAchatService/commandeAchatService.xml");
		Map<String, String> result = commandeAchatService.getCAInformation("9210000001");
		assertNotNull(result);
	}

	@Test
	public void getAllCAOfDATest() throws TechnicalException, FileNotFoundException, ConfigurationException,
			DatabaseUnitException, SQLException {
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/commandeAchatService/commandeAchatService2.xml");
		try {
			Map<String, Object[]> listeMap = commandeAchatService.getAllCAOfDA("SIGIFNUM1", null);

			String[] numeros = (String[]) listeMap.get(Constantes.KEY_MAP_NUMERO);
			String[] descriptions = (String[]) listeMap.get(Constantes.KEY_MAP_DESCRIPTION);
			Date[] dates = (Date[]) listeMap.get(Constantes.KEY_MAP_DATE_CREATION);
			String[] statuts = (String[]) listeMap.get(Constantes.KEY_MAP_STATUT);

			assertNotNull(listeMap);
			assertEquals(listeMap.size(), 4);
			assertEquals(numeros.length, 7);
			assertEquals(numeros[0], "9210000001");
			assertEquals(descriptions[2], "test CA 3");
			assertEquals(StringToDate.convertDateToString(dates[3]), "23/04/2017");
			assertEquals(statuts[6], "Non réceptionnée");
			System.out.println("GET ALL CA OF DA OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET ALL CA OF DA KO");
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void getAllCAOfDAAndNumPosteTest() throws TechnicalException, FileNotFoundException, ConfigurationException,
			DatabaseUnitException, SQLException {
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/commandeAchatService/commandeAchatService2.xml");
		try {
			Map<String, Object[]> listeMap = commandeAchatService.getAllCAOfDA("SIGIFNUM1", "1");

			String[] numeros = (String[]) listeMap.get(Constantes.KEY_MAP_NUMERO);
			String[] descriptions = (String[]) listeMap.get(Constantes.KEY_MAP_DESCRIPTION);
			Date[] dates = (Date[]) listeMap.get(Constantes.KEY_MAP_DATE_CREATION);
			String[] statuts = (String[]) listeMap.get(Constantes.KEY_MAP_STATUT);

			assertNotNull(listeMap);
			assertEquals(4, listeMap.size());
			assertEquals(numeros.length, 6);
			assertEquals(numeros[0], "9210000001");
			assertEquals(descriptions[2], "test CA 4");
			assertEquals(numeros[4], "9210000009");
			assertEquals(StringToDate.convertDateToString(dates[3]), "21/05/2017");
			assertEquals(statuts[1], "Non réceptionnée");
			System.out.println("GET ALL CA OF DA AND NUMPOSTE OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET ALL CA OF DA AND NUMPOSTE KO");
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void getCAInfoOfCSFTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/CSFService/CsfLockService.xml");
		try {
			Map<String, Object[]> caInfoMap = commandeAchatService.getCAInfoOfCSF("S-7-B8LPV");
			
			assertEquals(caInfoMap.size(), 8);
			assertEquals(caInfoMap.get(Constantes.KEY_MAP_NUMERO_CA)[0], "9210000010");
			assertEquals(caInfoMap.get(Constantes.KEY_MAP_TYPE_COMMANDE)[0], "Charges");
			assertEquals(caInfoMap.get(Constantes.KEY_MAP_CATEGORIE_ACHAT)[0], "Fournitures");
			assertEquals(caInfoMap.get(Constantes.KEY_MAP_STATUT)[0], "Non réceptionnée");
			assertEquals(caInfoMap.get(Constantes.KEY_MAP_DESCRIPTION)[0], "test CA 10");
			assertEquals(caInfoMap.get(Constantes.KEY_MAP_CODE_DEVISE)[0], "XOF");
			assertEquals(caInfoMap.get(Constantes.KEY_MAP_DEVISE)[0], "Franc CFA  BCEAO");
			
			System.out.println("getCAInfoOfCSFTest OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getCAInfoOfCSFTest KO");
			Assert.fail(e.getMessage());
		}
	}

}
