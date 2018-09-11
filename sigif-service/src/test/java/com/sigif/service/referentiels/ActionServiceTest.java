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
import com.sigif.service.ActionService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class ActionServiceTest extends AbstractDbTestCase {

	@Autowired
	private ActionService actionService;

	
	@Test
	public void getAllActionsByProgramTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/action/action.xml"); //36 actions: 
		try {
			String codeProgramme = "1003"; // programme d'id 2 : 4 actifs sur 7
			resultat = actionService.getAllActionsByProgram(codeProgramme);
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 4);
			Assert.assertEquals(resultat.get("1003-01"), "Pilotage ministériel");
			Assert.assertEquals(resultat.get("1003-42"), "Constructions rehabilitations et equipements");
			System.out.println("GET les actions ACTIFS par Programme OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET les actions ACTIFS par Programme KO");
			Assert.fail();
		}
	}


}
