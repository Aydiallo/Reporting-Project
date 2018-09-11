package com.sigif.service.utilitaires;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.service.CategoriePosteService;
import com.sigif.testutil.AbstractDbTestCase;

public class CategoriePosteServiceTest extends AbstractDbTestCase {
    @Autowired
    CategoriePosteService categoriePosteService;

    @Before
    public void loadData() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/categoriePosteService/categoriePoste.xml");
    }

    @Test
    public void testGetCategorieByUnknownTypeAndReference() {
        try {
            this.categoriePosteService.getCategorieByTypeAndReference("P", "useless");
            fail("ApplicationException non remontée");
        } catch (ApplicationException e) {
            assertTrue(true);
        } catch (TechnicalException e) {
            e.printStackTrace();
            fail("Exception technique non prévue !");
        }
    }

    @Test
    public void testGetCategorieByTypeSAndUnknownReference() {
        String categorie = null;
        try {
            categorie = this.categoriePosteService.getCategorieByTypeAndReference("S", "unknownReference");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception non prévue");
        }
        assertNull(categorie);
    }

    @Test
    public void testGetCategorieByTypeSAndReference() {
        String categorie = null;
        try {
            categorie = this.categoriePosteService.getCategorieByTypeAndReference("S", "GOMME");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception non prévue");
        }
        assertNotNull(categorie);
        assertEquals("Matériels et accessoires de jeux", categorie);
    }

    @Test
    public void testGetCategorieByTypeFAndUnknownReference() {
        String categorie = null;
        try {
            categorie = this.categoriePosteService.getCategorieByTypeAndReference("F", "unknownReference");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception non prévue");
        }
        assertNull(categorie);
    }

    @Test
    public void testGetCategorieByTypeFAndReference() {
        String categorie = null;
        try {
            categorie = this.categoriePosteService.getCategorieByTypeAndReference("F", "622101");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception non prévue");
        }
        assertNotNull(categorie);
        assertEquals("Pièces détachées et accessoires pour véhicules", categorie);
    }

    @Test
    public void testGetCategorieByTypeIAndUnknownReference() {
        String categorie = null;
        try {
            categorie = this.categoriePosteService.getCategorieByTypeAndReference("I", "unknownReference");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception non prévue");
        }
        assertNull(categorie);
    }

    @Test
    public void testGetCategorieByTypeIAndReference() {
        String categorie = null;
        try {
            categorie = this.categoriePosteService.getCategorieByTypeAndReference("I", "300000000000");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception non prévue");
        }
        assertNotNull(categorie);
        assertEquals("Immobilisations en Cours/IEC Frais de recherche et de dév. agronomique", categorie);
    }
}
