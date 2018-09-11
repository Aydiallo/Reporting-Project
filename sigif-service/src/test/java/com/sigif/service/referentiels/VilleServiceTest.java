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
import com.sigif.service.VilleService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class VilleServiceTest extends AbstractDbTestCase {

	@Autowired
	private VilleService villeService;

	@Test
	public void getAllActifVilleByRegionTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/region/region.xml");
		try {
			resultat = villeService.getAllTownsByArea("4");//ST Louis 15 villes actives/18
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 15);
			Assert.assertEquals(resultat.get("4321"), "Dodel");
			Assert.assertEquals(resultat.get("4341"), "Fanaye");
			Assert.assertEquals(resultat.get("4153"), "Gandon");
			System.out.println("GET toutes les villes actives OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET toutes les villes actives KO");
			Assert.fail();
		}
	}


}
