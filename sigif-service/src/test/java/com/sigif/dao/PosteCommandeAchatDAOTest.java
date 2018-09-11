package com.sigif.dao;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.enumeration.StatutPosteCA;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.PosteCommandeAchat;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
@Transactional
public class PosteCommandeAchatDAOTest extends AbstractDbTestCase {
    @Autowired
    PosteCommandeAchatDAO posteCaDao;

    private final String pathToTestFiles = "dataTestFiles/posteCADao/";

    @Before
    public void loadData() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
        this.loadDataFileAndOverWrite(pathToTestFiles + "posteCADaoTest.xml");
    }

    @Test
    public void testGetClosedItemsByCA() throws ApplicationException, TechnicalException {
        List<PosteCommandeAchat> closedItemsByCA = this.posteCaDao.getClosedItemsByNumCA("9210000004", null);
        assertNotNull(closedItemsByCA);
        assertFalse(closedItemsByCA.isEmpty());
        Iterator<PosteCommandeAchat> it = closedItemsByCA.iterator();
        assertTrue(it.hasNext());
        PosteCommandeAchat poste = (PosteCommandeAchat) it.next();
        assertEquals(10, poste.getId());
        assertEquals("Gommes (par 2)", poste.getDesignation());
        assertEquals(StatutPosteCA.Cloture, poste.getStatut());
        assertFalse(it.hasNext());
    }

    @Test
    public void testGetById() {
        PosteCommandeAchat poste = this.posteCaDao.getById(5);
        assertEquals(5, poste.getId());
        assertEquals("terrain de foot", poste.getDesignation());
        assertEquals(StatutPosteCA.Cloture, poste.getStatut());
    }

    @Test
    public void testGetUniqueByParam() {
        PosteCommandeAchat poste = this.posteCaDao.getUniqueByParam("reference", "200000000001");
        assertEquals(5, poste.getId());
        assertEquals("terrain de foot", poste.getDesignation());
        assertEquals(StatutPosteCA.Cloture, poste.getStatut());
    }

    @Test
    public void testDeleteById() {
        this.posteCaDao.deleteById(5);
        assertNull(this.posteCaDao.getById(5));
    }

    @Test
    public void testDelete() {
        PosteCommandeAchat poste = this.posteCaDao.getById(5);
        this.posteCaDao.delete(poste);
        assertNull(this.posteCaDao.getById(5));
    }

    @Test
    public void testMerge() {
        PosteCommandeAchat poste = this.posteCaDao.getById(5);
        poste.setDesignation("yoyo");
        this.posteCaDao.merge(poste);
        PosteCommandeAchat posteApres = this.posteCaDao.getById(5);
        assertNotNull(posteApres);
        assertEquals("yoyo", posteApres.getDesignation());
    }

}
