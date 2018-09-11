package com.sigif.service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.bouchons.UtilisateurBouchon;
import com.sigif.dao.UtilisateurDAO;
import com.sigif.dto.UtilisateurDTO;
import com.sigif.enumeration.RetourConnexion;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Utilisateur;
import com.sigif.testutil.AbstractDbTestCase;
import com.sigif.util.Constantes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class UtilisateurServiceTest extends AbstractDbTestCase {

    @Autowired
    private UtilisateurService userService;

    @InjectMocks
    private UtilisateurServiceImpl userServiceImpl;

    @Mock
    private UtilisateurDAO userDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        try {
            this.emptyDatabase();
        } catch (ConfigurationException | DatabaseUnitException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Rollback(true)
    @Transactional
    public void getUsers() {
        UtilisateurDTO user = UtilisateurBouchon.getUser2();
        userService.save(user);
        List<UtilisateurDTO> result = userService.getAll();
        assertTrue(result != null);

    }

    @Test
    @Rollback(true)
    @Transactional
    public void addUtilisateurTest() throws ApplicationException {
        UtilisateurDTO user = new UtilisateurDTO();

        user = UtilisateurBouchon.getUser2();

        int sizeBefore = userService.getAll().size();
        userService.save(user);
        int sizeAfter = userService.getAll().size();

        assertTrue(sizeAfter > sizeBefore);
    }

    @Test
    public void checkLoginAndPassword() throws ApplicationException, FileNotFoundException, ConfigurationException,
            DatabaseUnitException, SQLException, TechnicalException {
        this.loadDataFileAndOverWrite("dataTestFiles/utilisateurService/utilisateurService.xml");
        String valTest = userService.checkLoginPassword("ndir", "SigifForm5");
        assertEquals(valTest, RetourConnexion.OK.toString());
    }

    @Test
    public void savePasswordTest() {
        try {
            Utilisateur user = UtilisateurBouchon.getUser1();

            Mockito.when(userDao.getUniqueByParam(Mockito.eq("login"), Mockito.anyString())).thenReturn(user);
            Mockito.doAnswer(new Answer<Void>() {
                public Void answer(InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    System.out.println("called with arguments: " + Arrays.toString(args));
                    return null;
                }
            }).when(userDao).merge(Mockito.any(Utilisateur.class));

            boolean resultat = userServiceImpl.savePassword("samei", "samei@123", true);

            assertEquals(user.getPassword(), "19f6cc93f071762151976c0c8a5cdc91");
            assertEquals(user.isMotDePasseGenere(), true);
            assertEquals(resultat, true);
            System.out.println("SavePassword OK");

        } catch (Exception e) {
            System.out.println("SavePassword KO");
            e.printStackTrace();
            fail("Exception lors de sauvegarde");
        }

    }

    @Test
    public void getHabilitationUserTest() throws ApplicationException, FileNotFoundException, ConfigurationException,
            DatabaseUnitException, SQLException, TechnicalException {
        this.loadDataFileAndOverWrite("dataTestFiles/utilisateurService/utilisateurService.xml");
        int habilValue = userService.getUserHabilitation("ndir");
        assertEquals(3, habilValue);

    }

    @Test
    public void checkIdentityTest() throws ApplicationException, FileNotFoundException, ConfigurationException,
            DatabaseUnitException, SQLException, TechnicalException {
        this.loadDataFileAndOverWrite("dataTestFiles/utilisateurService/utilisateurService.xml");
        int statutValue = userService.checkIdentity("ndir");
        assertEquals(statutValue, 1);
    }

    @Test
    public void getRequestersOfSpendindServiceTest() throws ApplicationException, FileNotFoundException,
            ConfigurationException, DatabaseUnitException, SQLException, TechnicalException {
        this.loadDataFileAndOverWrite("dataTestFiles/utilisateurService/utilisateurService.xml");
        Map<String, String> result = userService.getRequestersByRoleBySpendingService("reynaud", "23404731300", "DDA");

        assertTrue(result != null);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("ndir"));
        assertEquals("Monsieur Mamadou NDIR", result.get("ndir"));
    }

    @Test
    public void getThreeRequestersByCriteria() throws ApplicationException, FileNotFoundException,
            ConfigurationException, DatabaseUnitException, SQLException, TechnicalException {
        this.loadDataFileAndOverWrite("dataTestFiles/utilisateurService/utilisateurService.xml");
        Map<String, String> result = userService.getRequestersByRoleBySpendingService("reynaud", "23404731300", "RSF");
        assertTrue(result != null);
        assertEquals(3, result.size());
        assertTrue(result.containsKey("ndir"));
        assertTrue(result.containsKey("bouchot"));
        assertTrue(result.containsKey("testBeaupoil"));
        assertEquals("Monsieur Mamadou NDIR", result.get("ndir"));
        assertEquals("Madame Béatrice BOUCHOT", result.get("bouchot"));
        assertEquals("Monsieur Mickael BEAUPOIL", result.get("testBeaupoil"));

        Iterator<String> it = result.keySet().iterator();
        assertTrue(it.hasNext());
        assertEquals("testBeaupoil", it.next());
        assertTrue(it.hasNext());
        assertEquals("bouchot", it.next());
        assertTrue(it.hasNext());
        assertEquals("ndir", it.next());
        assertFalse(it.hasNext());

    }

    @Test
    public void getRequestersByCriteriaTest() throws ApplicationException, FileNotFoundException,
            ConfigurationException, DatabaseUnitException, SQLException, TechnicalException {
        this.loadDataFileAndOverWrite("dataTestFiles/utilisateurService/utilisateurService.xml");
        // on récupère tous les utilisateurs avec un compte actif et qui existe
        // dans les profils sont liés au service dépensier d'id 16 
        Map<String, String> result =
                userService.getRequestersByCriteria(null, "23603930720", null, null, "actif", false);

        assertTrue(result != null);
        assertEquals(5, result.size());
        assertTrue(result.containsKey("reynaud"));
        assertTrue(result.containsKey("diagne"));
        assertTrue(result.containsKey("testBeaupoil"));
        assertTrue(result.containsKey("beye"));
        assertTrue(result.containsKey("alardo"));
        assertFalse(result.containsKey("bouchot"));
        assertFalse(result.containsKey("admin"));
        assertFalse(result.containsKey("avecCompteDemandeur"));
        assertFalse(result.containsKey("TEST6"));
        assertFalse(result.containsKey("avecCompteTousRoles"));
        assertFalse(result.containsKey("avecCompteReceptionnaire"));

        result = userService.getRequestersByCriteria(null, "23603930720", null, null, "inactif", false);

        assertTrue(result != null);
        assertEquals(1, result.size());
        Iterator<String> it1 = result.keySet().iterator();
        assertTrue(it1.hasNext());
        assertEquals("utilisateurDesactive", it1.next());

        result = userService.getRequestersByCriteria("alardo", "", null, "", "all", true);
        assertEquals(8, result.size());
        result = userService.getRequestersByCriteria("alardo", "", null, "", "actif", true);
        assertEquals(8, result.size());
        result = userService.getRequestersByCriteria("alardo", "", null, "", "inactif", true);
        assertNull(result);

        try {
            result = userService.getRequestersByCriteria("alardo", "11200040100", null, "33", "all", true);
            fail("Il devrait y avoir une erreur car le ministère n'est pas lié au service dépensier");
        } catch (ApplicationException e) {
        }

        result = userService.getRequestersByCriteria("alardo", null, "DDA", null, "all", true);
        assertEquals(7, result.size());

        // on récupère tous les utilisateurs avec un compte actif ou inactif et
        // réceptionneurs
        // sur le service dépensier d'id 16 (code 23603930720) 
        result = userService.getRequestersByCriteria("alardo", "23603930720", "RSF", null, "all", true);
        assertEquals(3, result.size());
        assertTrue(result.containsKey("diagne"));
        assertTrue(result.containsKey("testBeaupoil"));
        assertTrue(result.containsKey("beye"));

        assertFalse(result.containsKey("alardo"));
        assertFalse(result.containsKey("reynaud"));
        assertFalse(result.containsKey("ndir"));
        assertFalse(result.containsKey("bouchot"));
        assertFalse(result.containsKey("admin"));
        assertFalse(result.containsKey("avecCompteDemandeur"));
        assertFalse(result.containsKey("avecCompteTousRoles"));
        assertFalse(result.containsKey("avecCompteReceptionnaire"));

        Iterator<String> it = result.keySet().iterator();
        assertTrue(it.hasNext());
        assertEquals("testBeaupoil", it.next());
        assertTrue(it.hasNext());
        assertEquals("beye", it.next());
        assertTrue(it.hasNext());
        assertEquals("diagne", it.next());
        assertFalse(it.hasNext());

    }

    @Test
    public void getMapOfUserByLoginTest() throws TechnicalException, FileNotFoundException, ConfigurationException,
            DatabaseUnitException, SQLException, ApplicationException {
        this.loadDataFileAndOverWrite("dataTestFiles/utilisateurService/utilisateurService.xml");
        Map<String, String> result = userService.getMapOfUserByLogin("ndir");
        assertTrue(result != null);
    }

    @Test
    public void getRequestersByLogin() throws ApplicationException, FileNotFoundException, ConfigurationException,
            DatabaseUnitException, SQLException, TechnicalException {
        this.loadDataFileAndOverWrite("dataTestFiles/utilisateurService/utilisateurService.xml");
        Map<String, String> result = userService.getRequestersByCriteria("ndir", null, null, null, "all", false);
        assertTrue(result != null);
        assertEquals(4, result.size());
        assertTrue(result.containsKey("ndir"));
        assertTrue(result.containsKey("bouchot"));
        assertTrue(result.containsKey("testBeaupoil"));
        assertTrue(result.containsKey("reynaud"));
        assertEquals("Monsieur Mamadou NDIR", result.get("ndir"));
        assertEquals("Madame Béatrice BOUCHOT", result.get("bouchot"));
        assertEquals("Monsieur Mickael BEAUPOIL", result.get("testBeaupoil"));

        Iterator<String> it = result.keySet().iterator();
        assertTrue(it.hasNext());
        assertEquals("testBeaupoil", it.next());
        assertTrue(it.hasNext());
        assertEquals("bouchot", it.next());
        assertTrue(it.hasNext());
        assertEquals("ndir", it.next());
        assertTrue(it.hasNext());
        assertEquals("reynaud", it.next());
        assertFalse(it.hasNext());

    }
    @Test
    public void getInfoUserByLogin() throws ApplicationException, FileNotFoundException, ConfigurationException,
            DatabaseUnitException, SQLException, TechnicalException {
        this.loadDataFileAndOverWrite("dataTestFiles/utilisateurService/utilisateurService.xml");
        Map<String, String> result = userService.getInfoUserByLogin("alardo");
        assertTrue(result != null);
       // assertEquals(4, result.size());
        assertEquals(result.get(Constantes.KEY_MAP_LOGIN), "alardo");
        
        

    }
}
