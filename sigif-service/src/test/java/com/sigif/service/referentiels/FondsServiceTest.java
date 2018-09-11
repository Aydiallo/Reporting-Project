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
import com.sigif.service.FondsService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class FondsServiceTest extends AbstractDbTestCase {

	@Autowired
	private FondsService fondsService;

	@Test
	public void getAllActifFondsByTypeFondsTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();		
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/typeFonds/typeFonds.xml"); //5 actifs sur 8 typeFonds
		try {
			resultat = fondsService.getAllFundsByType("1");// code = RP : id =1, 2 fonds actifs/4
			
			assertNotNull(resultat);
			Assert.assertEquals(2, resultat.size());
			Assert.assertEquals("Etat1", resultat.get("ETAT1"));
			Assert.assertEquals("Etat4", resultat.get("ETAT4"));
			System.out.println("GET tous les fonds actifs OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET tous les fonds actifs KO");
			Assert.fail();
		}
	}


}
