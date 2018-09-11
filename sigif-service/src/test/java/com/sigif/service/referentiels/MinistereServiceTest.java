package com.sigif.service.referentiels;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.service.MinistereService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class MinistereServiceTest extends AbstractDbTestCase {

	@Autowired
	private MinistereService ministereService;

	@Test
	public void getAllMinisteryOfUserByLoginAndRoleTest() throws TechnicalException, ApplicationException,
			FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
	    this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/ministryService/ministry.xml");
		Map<String, String> result = ministereService.getAllMinisteryOfUserByLoginAndRole("reynaud", "DDA", "actif");
		System.out.println("size**" + result.size());
		assertNotNull(result);
		assertEquals(2, result.size());		

		/*for (Map.Entry<String, String> entry : result.entrySet()) {
			assertTrue("43".equals(entry.getKey()));
			assertTrue("54".equals(entry.getKey()));
			assertTrue("31".equals(entry.getKey()));
			System.out.println( entry.getKey() + " Value : " + entry.getValue());
		}*/
	}

}
