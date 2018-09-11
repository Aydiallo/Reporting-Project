package com.sigif.service.referentiels;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.service.GroupeDeMarchandisesService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class GroupeDeMarchandisesServiceTest extends AbstractDbTestCase {

	@Autowired
	private GroupeDeMarchandisesService grpMService;

	
	@Test
	public void getAllActifMerchandisesGroupTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/GroupeDeMarchandises/grpDeMarchandises.xml");
		try {
			resultat = grpMService.getAllActifMerchandisesGroup();
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 7);
			Assert.assertEquals(resultat.get("620501"), "Accessoires  de bureau");
			Assert.assertEquals(resultat.get("620101"), "Rame, paquet et rouleau de papier");
			System.out.println("GET TOUS les GroupeDeMarchandises ACTIFS OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET TOUS les GroupeDeMarchandises ACTIFS KO");
			Assert.fail();
		}
	}

	@Test
	public void getGroupeDeMarchandisesActifsAvecArticlesTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/GroupeDeMarchandises/grpDeMarchandises.xml");
		try {
			resultat = grpMService.getAllMerchandisesGroup("S"); // avec article
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 3);
			Assert.assertEquals(resultat.get("620101"), "Rame, paquet et rouleau de papier");
			Assert.assertEquals(resultat.get("621001"), "Accessoires  pour nettoiement             ");
			System.out.println("GET GroupeDeMarchandises ACTIFS ET AVEC ARTICLES OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET GroupeDeMarchandises ACTIFS ET AVEC ARTICLES KO");
			Assert.fail();
		}
	}

	
	@Test
	public void getGroupeDeMarchandisesActifsSansArticlesTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/GroupeDeMarchandises/grpDeMarchandises.xml");
		try {
			resultat = grpMService.getAllMerchandisesGroup("F"); // sans article
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 4);
			Assert.assertEquals(resultat.get("620501"), "Accessoires  de bureau");
			Assert.assertEquals(resultat.get("621201"), "Détergents");
			System.out.println("GET GroupeDeMarchandises ACTIFS ET SANS ARTICLES OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET GroupeDeMarchandises ACTIFS ET SANS ARTICLES KO");
			Assert.fail();
		}
	}
	
}
