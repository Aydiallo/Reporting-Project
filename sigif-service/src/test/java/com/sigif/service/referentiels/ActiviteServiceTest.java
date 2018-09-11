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
import com.sigif.service.ActiviteService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class ActiviteServiceTest extends AbstractDbTestCase {

	@Autowired
	private ActiviteService activiteService;

	
	@Test
	public void getAllActiviteActifsByActionTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/activite/activite.xml"); //10 activites: 
		try {
			String codeAction = "1001-01"; // action d'id 1 : 3 actifs sur 5
			resultat = activiteService.getAllActivitiesByAction(codeAction);
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 3);
			Assert.assertEquals(resultat.get("act-2"), "DESC2");
			Assert.assertEquals(resultat.get("act-9"), "DESC9");
			System.out.println("GET les activites ACTIFS par Action OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET les activites ACTIFS par Action KO");
			Assert.fail();
		}
	}


	
	@Test
	public void getAllActiviteActifsByActionInexistantTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/activite/activite.xml"); //10 activites: 
		try {
			String codeAction = "1003"; // action inexistante
			resultat = activiteService.getAllActivitiesByAction(codeAction);
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 0);
			System.out.println("GET les activites ACTIFS par Action OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET les activites ACTIFS par Action KO");
			Assert.fail();
		}
	}
}
