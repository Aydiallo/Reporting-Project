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
import com.sigif.service.TypeFondsService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class TypeFondsServiceTest extends AbstractDbTestCase {

	@Autowired
	private TypeFondsService typeFondsService;

	
	@Test
	public void getAllActifTypeFondsTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/typeFonds/typeFonds.xml"); //2 actifs sur 3
		try {
			resultat = typeFondsService.getAllFundTypes();
			
			assertNotNull(resultat);
			Assert.assertEquals(2, resultat.size());
			Assert.assertEquals("Ressources propres (SNBG)", resultat.get("1"));
			Assert.assertEquals("Emprunts (SNBG)", resultat.get("3"));
			System.out.println("GET tous les typeFondss actifs OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET tous les typeFondss actifs KO");
			Assert.fail();
		}
	}


}
