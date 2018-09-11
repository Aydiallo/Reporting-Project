package com.sigif.service.referentiels;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Iterator;
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
import com.sigif.service.CategorieAchatService;
import com.sigif.testutil.AbstractDbTestCase;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class CategorieAchatServiceTest extends AbstractDbTestCase {

    @Autowired
    CategorieAchatService categorieAchatService;

    @Test
    public void getAllPurchasingCategories() throws TechnicalException, FileNotFoundException, ConfigurationException,
            DatabaseUnitException, SQLException, ApplicationException {
        this.loadDataFileAndOverWrite("dataTestFiles/CategorieAchatService/categorieAchat.xml");
        Map<String, String> result = categorieAchatService.getAllPurchasingCategories("all");

        assertNotNull(result);
        assertTrue(result.keySet().contains("T"));
        assertTrue(result.keySet().contains("S"));
        assertTrue(result.keySet().contains("F"));
        assertTrue(result.keySet().contains("P"));

        assertEquals(result.get("T"), "Travaux");
        assertEquals(result.get("S"), "Services");
        
        result = categorieAchatService.getAllPurchasingCategories("actif");

        assertNotNull(result);
        assertTrue(result.keySet().contains("T"));
        assertTrue(result.keySet().contains("S"));
        assertFalse(result.keySet().contains("F"));
        assertFalse(result.keySet().contains("P"));

        Iterator<String> it = (Iterator<String>) result.keySet().iterator();
        assertTrue(it.hasNext());

        assertEquals("S", it.next());
        assertTrue(it.hasNext());
        assertEquals("T", it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void getAllPurchasingCategoriesEmptyDB() throws TechnicalException, FileNotFoundException,
            ConfigurationException, DatabaseUnitException, SQLException, ApplicationException {
        this.emptyDatabase();
        Map<String, String> result = categorieAchatService.getAllPurchasingCategories("all");

        assertNull(result);
    }

    @Test
    public void getPurchasingCategoriesByCodeNotActif() throws TechnicalException, FileNotFoundException,
            ConfigurationException, DatabaseUnitException, SQLException, ApplicationException {
        this.loadDataFileAndOverWrite("dataTestFiles/CategorieAchatService/categorieAchat.xml");
        Map<String, String> result = categorieAchatService.getInformationCategorieAchatByCode("F");

        assertNull(result);
    }


}
