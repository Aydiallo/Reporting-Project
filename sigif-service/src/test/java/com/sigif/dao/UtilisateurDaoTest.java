package com.sigif.dao;

import static org.junit.Assert.assertNotEquals;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.enumeration.Civilite;
import com.sigif.enumeration.Statut;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Utilisateur;
import com.sigif.testutil.AbstractDbTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
@Transactional
public class UtilisateurDaoTest extends AbstractDbTestCase {

    @Autowired
    private UtilisateurDAO dao;

    @Before
    public void emptyDatabse() throws ConfigurationException, DatabaseUnitException, SQLException {
        this.emptyDatabase();
    }

    /**
     * Vérifie la récupération d'un utilisateur par le DAO.
     * 
     * @throws SQLException
     * @throws DatabaseUnitException
     * @throws FileNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void getUtilisateur()
            throws FileNotFoundException, DatabaseUnitException, SQLException, ConfigurationException {
        this.loadDataFileAndOverWrite("dataTestFiles/utilisateurService/utilisateurService.xml");
        Utilisateur userById = dao.getById(3);
        assertNotNull(userById);

        ITable tableAttendue =
                this.getExpectedTableFromXml("dataTestFiles/utilisateurDao/utilisateurId3.xml", "utilisateur");
        Utilisateur user = new Utilisateur();
        user.setLogin(this.getStringValue(tableAttendue, 0, "login"));
        user.setAvecCompte(this.getBooleanValue(tableAttendue, 0, "AvecCompte"));
        user.setCivilite(Civilite.valueOf(this.getStringValue(tableAttendue, 0, "civilite")));
        user.setCompteActif(this.getBooleanValue(tableAttendue, 0, "compteActif"));
        user.setCourriel(this.getStringValue(tableAttendue, 0, "courriel"));
        user.setDateCreation(this.getDateValue(tableAttendue, 0, "dateCreation"));
        user.setDateDerniereConnexion(this.getDateValue(tableAttendue, 0, "dateDerniereConnexion"));
        user.setDateModification(this.getDateValue(tableAttendue, 0, "dateModification"));
        user.setIdSAP(this.getStringValue(tableAttendue, 0, "idSAP"));
        user.setMotDePasseGenere(this.getBooleanValue(tableAttendue, 0, "motDePasseGenere"));
        user.setNom(this.getStringValue(tableAttendue, 0, "nom"));
        user.setPassword(this.getStringValue(tableAttendue, 0, "password"));
        user.setPrenom(this.getStringValue(tableAttendue, 0, "prenom"));
        user.setStatut(Statut.valueOf(this.getStringValue(tableAttendue, 0, "statut")));
        user.setTelephone(this.getStringValue(tableAttendue, 0, "telephone"));
        user.setId(this.getintValue(tableAttendue, 0, "id"));
        assertEquals(user.toString(), userById.toString());
    }

    /**
     * Vérifie l'ajout d'un utilisateur par le DAO (Ajouter
     * l'annotation @Rollback(false) pour vérifier visuellement le contenu de la
     * base après le test).
     * 
     * @throws DatabaseUnitException
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void addUtilisateur()
            throws DatabaseUnitException, SQLException, FileNotFoundException, ConfigurationException {
        this.emptyTable("utilisateur");
        Date dateTest = new GregorianCalendar(2017, 05, 15, 13, 54, 33).getTime();
        Utilisateur user = new Utilisateur();
        user.setLogin("test");
        user.setAvecCompte(true);
        user.setCivilite(Civilite.Mademoiselle);
        user.setCompteActif(true);
        user.setCourriel("email@test.test");
        user.setDateCreation(dateTest);
        user.setDateDerniereConnexion(dateTest);
        user.setDateModification(dateTest);
        user.setIdSAP("idSAPtest");
        user.setMotDePasseGenere(false);
        user.setNom("test Nom");
        user.setPassword("8d860a823d9bc076fcc388c5a1cb7507");
        user.setPrenom("testPrenom");
        user.setStatut(Statut.inactif);
        user.setTelephone("0102030405");

        int id = dao.save(user);

        assertNotEquals(0, id);
        Utilisateur userResult = dao.getById(id);
        assertNotNull(userResult);
        assertEquals(id, userResult.getId());
        assertEquals(user.toString(), userResult.toString());
    }

    @Test
    public void updateUtilisateur() throws FileNotFoundException, ConfigurationException, DatabaseUnitException,
            SQLException, TechnicalException {
        this.loadDataFileAndOverWrite("dataTestFiles/utilisateurService/utilisateurService.xml");
        Utilisateur userToUpdate = dao.getById(8);
        userToUpdate.setCourriel("nouveauMail@test.unitaire");
        userToUpdate.setCivilite(Civilite.Mademoiselle);
        userToUpdate.setLogin("utilisateurUpdate");
        dao.merge(userToUpdate);

        Utilisateur userAfterUpdate = dao.getById(8);
        assertNotNull(userAfterUpdate);

        ITable tableAttendue =
                this.getExpectedTableFromXml("dataTestFiles/utilisateurDao/updateUtilisateur.xml", "utilisateur");
        Utilisateur user = new Utilisateur();
        user.setLogin(this.getStringValue(tableAttendue, 0, "login"));
        user.setAvecCompte(this.getBooleanValue(tableAttendue, 0, "AvecCompte"));
        user.setCivilite(Civilite.valueOf(this.getStringValue(tableAttendue, 0, "civilite")));
        user.setCompteActif(this.getBooleanValue(tableAttendue, 0, "compteActif"));
        user.setCourriel(this.getStringValue(tableAttendue, 0, "courriel"));
        user.setDateCreation(this.getDateValue(tableAttendue, 0, "dateCreation"));
        user.setDateDerniereConnexion(this.getDateValue(tableAttendue, 0, "dateDerniereConnexion"));
        user.setDateModification(this.getDateValue(tableAttendue, 0, "dateModification"));
        user.setIdSAP(this.getStringValue(tableAttendue, 0, "idSAP"));
        user.setMotDePasseGenere(this.getBooleanValue(tableAttendue, 0, "motDePasseGenere"));
        user.setNom(this.getStringValue(tableAttendue, 0, "nom"));
        user.setPassword(this.getStringValue(tableAttendue, 0, "password"));
        user.setPrenom(this.getStringValue(tableAttendue, 0, "prenom"));
        user.setStatut(Statut.valueOf(this.getStringValue(tableAttendue, 0, "statut")));
        user.setTelephone(this.getStringValue(tableAttendue, 0, "telephone"));
        user.setId(this.getintValue(tableAttendue, 0, "id"));

        assertEquals(user.toString(), userAfterUpdate.toString());
    }
}
