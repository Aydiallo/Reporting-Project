package com.sigif.service;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sigif.exception.TechnicalException;
import com.sigif.testutil.AbstractDbTestCase;

public class EchangesSapTest extends AbstractDbTestCase {
    @Autowired
    EchangesSapService echangesService;

    @Test
    public void isProcessRunningTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException, TechnicalException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/echangesSap/echangesSapEnCours.xml");
        assertTrue(echangesService.isSapProcessRunning());
        
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/echangesSap/echangesSapOK.xml");
        assertFalse(echangesService.isSapProcessRunning());
    }

}
