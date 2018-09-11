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
import com.sigif.service.RegionService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class RegionServiceTest extends AbstractDbTestCase {

	@Autowired
	private RegionService regionService;

	
	@Test
	public void getAllActifRegionTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/region/region.xml"); //12 actifs sur 14
		try {
			resultat = regionService.getAllAreas();
			
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 12);
			Assert.assertEquals(resultat.get("5"), "TAMBACOUNDA   ");
			Assert.assertEquals(resultat.get("7"), "THIES");
			Assert.assertEquals(resultat.get("1"), "DAKAR");
			System.out.println("GET toutes les regions actives OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET toutes les regions actives KO");
			Assert.fail();
		}
	}


}
