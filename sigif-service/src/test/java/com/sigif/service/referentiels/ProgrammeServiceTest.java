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
import com.sigif.service.ProgrammeService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class ProgrammeServiceTest extends AbstractDbTestCase{
	
	@Autowired
	private ProgrammeService programmeService;
	
	@Test
	public void getProgramsOfMinisteryTest() throws ApplicationException, FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException, TechnicalException{
	//this.loadFullDataFileAndOverWrite();
	Map<String, String> result = programmeService.getAllProgrammOfUserByLoginRoleAndMinistery("reynaud","54","DDA");	
	/*for ( String key : result.keySet() ) {
	    System.out.println( key );
	}*/
	 assertNotNull(result);
		
	}

}
