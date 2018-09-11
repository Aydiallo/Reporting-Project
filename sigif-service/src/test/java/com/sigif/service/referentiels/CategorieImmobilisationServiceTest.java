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
import com.sigif.service.CategorieImmobilisationService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class CategorieImmobilisationServiceTest extends AbstractDbTestCase {

	@Autowired
	private CategorieImmobilisationService categorieImmoService;

	@Test
	public void getCategorieImmoByTypeImmoTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/typeImmo/typeImmo.xml");
		
		try {
			resultat = categorieImmoService.getCategorieImmoByTypeImmo("EQM");//idTypeImmo = 4 : 6 catégories actives/8
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 6);
			Assert.assertEquals(resultat.get("2523ESLN"), "Ouvrages et infrastructures maritimes");
			Assert.assertEquals(resultat.get("2521ESLN"), "Ouvrages et infrastructures terrestres");
			Assert.assertEquals(resultat.get("2532ESLN"), "Matériels");
			System.out.println("GET toutes les categorieImmos actives OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET toutes les categorieImmos actives KO");
			Assert.fail();
		}
	}


}
