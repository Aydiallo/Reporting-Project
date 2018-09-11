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
import com.sigif.service.TypeAchatService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class TypeAchatServiceTest extends AbstractDbTestCase {

	@Autowired
	private TypeAchatService typeAchatService;

	@Test
	public void getAllTypeAchatTest()
			throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {

		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/typeAchat/typeAchat.xml"); 
		
		Map<String, String> resultat = new HashMap<String, String>();
		try {
			resultat = typeAchatService.getAllTypeAchat(); //2 actifs / 3

			assertNotNull(resultat);
			Assert.assertEquals(resultat.size(), 2);
			Assert.assertEquals(resultat.get("F"), "Fonctionnement");
			Assert.assertEquals(resultat.get("I"), "Immobilisation");

			System.out.println("GET TOUS les typeAchats ACTIFS OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET TOUS les typeAchats ACTIFS KO");
			Assert.fail();
		}
	}

}
