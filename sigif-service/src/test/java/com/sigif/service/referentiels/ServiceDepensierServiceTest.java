package com.sigif.service.referentiels;

import java.io.FileNotFoundException;
import java.sql.SQLException;
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
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.service.ServiceDepensierService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class ServiceDepensierServiceTest extends AbstractDbTestCase {

	@Autowired
	ServiceDepensierService serviceDepensierService;

	@Test
	public void getAllSpendingServicesOfUserByLoginRoleAndMinisteryTest()
			throws ApplicationException, TechnicalException, FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		 this.emptyDatabase();
	        this.loadDataFileAndOverWrite("dataTestFiles/DAService/DAService.xml");
		try {
			Map<String, String> result = serviceDepensierService
					.getAllSpendingServicesOfUserByLoginRoleAndMinistery("reynaud", "31", "DDA", "actif");

			assertTrue(result.size() > 0);

			result = serviceDepensierService
					.getAllSpendingServicesOfUserByLoginRoleAndMinistery("", "31", "", "actif");

			assertTrue(result.size() > 0);
			/*for (Map.Entry<String, String> entry : result.entrySet()) {
				assertTrue(entry.getKey().equals("11200040100"));
				assertFalse(entry.getKey().equals("11200040107"));

			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getAllSpendingServicesOfUserByLoginRoleAndEmptyMinisteryTest() throws ApplicationException,
			TechnicalException, FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		//this.loadDataFileAndOverWrite("dataTestFiles/full.xml");
		try {
			Map<String, String> result = serviceDepensierService
					.getAllSpendingServicesOfUserByLoginRoleAndMinistery(null, null, null, "actif");
			System.out.println("code " + result.size());
			assertTrue(result.size() > 0);

			for (Map.Entry<String, String> entry : result.entrySet()) {
				// assertTrue(entry.getKey().equals("11200040100"));
				System.out.println("code " + entry.getKey());
				// assertFalse(entry.getKey().equals("11200040107"));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
}
