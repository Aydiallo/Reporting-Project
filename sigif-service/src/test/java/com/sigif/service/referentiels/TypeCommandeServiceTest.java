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
import com.sigif.service.TypeCommandeService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class TypeCommandeServiceTest extends AbstractDbTestCase {

	@Autowired
	private TypeCommandeService tcService;


	@Test
	public void getTypeCommandeActifsTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/TypeCommande/typeCommande.xml");
		try {
			resultat = tcService.getAllOrderTypes();
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 6);
			Assert.assertEquals(resultat.get("MACC"), "Commande Acc-cadre");
			System.out.println("GET TYPECOMMANDE ACTIFS OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET TYPECOMMANDE ACTIFS KO");
			Assert.fail();
		}
		
	}

}
