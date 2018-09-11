package com.sigif.service.referentiels;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.exception.TechnicalException;
import com.sigif.service.LieuStockageService;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class LieuStockageServiceTest extends AbstractDbTestCase {

    @Autowired
    private LieuStockageService lieuStockageService;

    @Test
    public void getAllActifStorageAreaTest() throws FileNotFoundException, ConfigurationException,
            DatabaseUnitException, SQLException, TechnicalException {
        Map<String, String> resultat = null;
        this.emptyDatabase();
        // 50 actifs sur 53
        this.loadDataFileAndOverWrite("dataTestFiles/lieuStockage/lieuStockage.xml");
        resultat = lieuStockageService.getAllActifStorageArea();

        assertNotNull(resultat);
        Assert.assertEquals(resultat.size(), 50);
        Assert.assertEquals(resultat.get("4"), "DK32 - Dakar");
        Assert.assertEquals(resultat.get("16"), "KL54 - Kaolack");
        Assert.assertEquals(resultat.get("38"), "TC50 - Tambac.");
        System.out.println("GET TOUS les lieuStockages ACTIFS OK");
    }

    @Test
    public void getLieuStockageInSameDivTest()
            throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException, TechnicalException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/lieuStockage/lieuStockage.xml");
        Entry<String, String> next = null;
        Map<String, String> resultat = null;

        
        resultat = lieuStockageService.getActifStorageAreaInSameDivision(31);
        assertNotNull(resultat);
        assertEquals(3, resultat.size());
        Iterator<Entry<String, String>> it = resultat.entrySet().iterator();
        assertTrue(it.hasNext());
        next = it.next();
        assertEquals("DK75-Univ Gb", next.getValue());
        assertEquals("33", next.getKey());
        assertTrue(it.hasNext());
        next = it.next();
        assertEquals("SL50 - St-Louis", next.getValue());
        assertEquals("31", next.getKey());
        assertTrue(it.hasNext());
        next = it.next();
        assertEquals("SL54 - St-Louis", next.getValue());
        assertEquals("30", next.getKey());
        assertFalse(it.hasNext());
        
        resultat = lieuStockageService.getActifStorageAreaInSameDivision(3);
        assertNotNull(resultat);
        assertEquals(5, resultat.size());
        it = resultat.entrySet().iterator();
        assertTrue(it.hasNext());
        next = it.next();
        assertEquals("DK32 - Dakar", next.getValue());
        assertEquals("4", next.getKey());
        assertTrue(it.hasNext());
        next = it.next();
        assertEquals("DK43-DMTA Dakar", next.getValue());
        assertEquals("1", next.getKey());
        assertTrue(it.hasNext());
        next = it.next();
        assertEquals("DK50 - Dakar", next.getValue());
        assertEquals("3", next.getKey());
        assertTrue(it.hasNext());
        next = it.next();
        assertEquals("DK54 - Dakar", next.getValue());
        assertEquals("2", next.getKey());
        assertTrue(it.hasNext());
        next = it.next();
        assertEquals("DK75-Univ CAD", next.getValue());
        assertEquals("5", next.getKey());
        assertFalse(it.hasNext());

    }

}
