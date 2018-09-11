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
import com.sigif.service.TypeImmobilisationService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class TypeImmobilisationServiceTest extends AbstractDbTestCase {

	@Autowired
	private TypeImmobilisationService typeImmoService;

	
	@Test
	public void getAllActifTypeImmobilisationTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/typeImmo/typeImmo.xml"); //6 actifs sur 8
		try {
			resultat = typeImmoService.getAllActifTypeImmobilisation();
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 6);
			Assert.assertEquals(resultat.get("IIC"), "Immobilisations Incorporelles");
			Assert.assertEquals(resultat.get("VHC"), "Véhicule");
			Assert.assertEquals(resultat.get("IMM"), "Immeubles (Parc immobilier)");
			System.out.println("GET TOUS les typeImmos ACTIFS OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET TOUS les typeImmos ACTIFS KO");
			Assert.fail();
		}
	}


}
