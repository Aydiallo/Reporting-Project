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
import com.sigif.service.UniteService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class UniteServiceTest extends AbstractDbTestCase {

	@Autowired
	private UniteService uniteService;

	
	@Test
	public void getAllActifUniteTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/unite/unite.xml"); //25 actifs sur 30
		try {
			resultat = uniteService.getAllActifUnits();
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 25);
			Assert.assertEquals(resultat.get("DEG"), "Degré");
			Assert.assertEquals(resultat.get("GRL"), "gma/litre");
			Assert.assertEquals(resultat.get("MLR"), "Milliers");
			Assert.assertEquals(resultat.get("PRS"), "Personnes");
			System.out.println("GET TOUS les unites ACTIFS OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET TOUS les unites ACTIFS KO");
			Assert.fail();
		}
	}


}
