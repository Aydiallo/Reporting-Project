package com.sigif.testutil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sigif.app.SigifServicesTestConfig;

import junit.framework.TestCase;

/**
 * Classe abstraite de test permettant de vérifier une entité en comparant - une
 * base de référence (param "jdbc.reference." dans
 * test/resources/application.properties) - une base générée lors des tests
 * (param "jdbc." en utilisant la valeur "create" pour hibernate.hbm2ddl.auto).
 * 
 * Pour que ces tests fonctionnent, - la base de référence doit avoir été
 * remplie avec les scripts SQL - la base générée doit exister (mais peut être
 * vide car elle sera recréée)
 * 
 * @author Mickael Beaupoil
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public abstract class AbstractModeleTest extends TestCase {

    @Autowired
    Connection referenceConnection;

    @Autowired
    DataSource dataSource;

    private Hashtable<String, String> refColumnType = new Hashtable<>();
    private Hashtable<String, String> generatedColumnType = new Hashtable<>();
    private Hashtable<String, String> refColumnNullable = new Hashtable<>();
    private Hashtable<String, String> generatedColumnNullable = new Hashtable<>();
    private Hashtable<String, String> refColumnAutoIncrement = new Hashtable<>();
    private Hashtable<String, String> generatedColumnAutoIncrement = new Hashtable<>();

    public static final String FAILURE_MSG = "L'info '%s' de la colonne '%s' est de %s pour la colonne de référence et %s pour la colonne générée.";

    /**
     * Renvoie le nom de la table à tester.
     * 
     * @return le nom de la table à tester
     */
    public abstract String getTableName();

    /**
     * Charge les métadonnées pour les 2 bases testées.
     * 
     * @throws SQLException
     *             Le chargement des métadonnées a échoué
     */
    @Before
    public void getMetadata() throws SQLException {
        // Obtient les metadata de chacune des 2 bases
        DatabaseMetaData referenceMetadata = referenceConnection.getMetaData();
        DatabaseMetaData generatedMetadata = dataSource.getConnection().getMetaData();

        // Extrait les metadata des colonnes de la table testée
        ResultSet refRes = referenceMetadata.getColumns(null, null, getTableName(), null);
        ResultSet generatedRes = generatedMetadata.getColumns(null, null, getTableName(), null);

        String columnName;

        while (refRes.next()) {
            columnName = refRes.getString("COLUMN_NAME");

            // Stocke les infos de type, is nullable et is auto increment pour
            // chaque colonne de la table de la base de référence
            refColumnType.put(columnName, refRes.getString("TYPE_NAME"));
            refColumnNullable.put(columnName, refRes.getString("IS_NULLABLE"));
            refColumnAutoIncrement.put(columnName, refRes.getString("IS_AUTOINCREMENT"));
        }
        refRes.close();

        while (generatedRes.next()) {
            columnName = generatedRes.getString("COLUMN_NAME");

            // Stocke les infos de type, is nullable et is auto increment pour
            // chaque colonne de la table de la base générée par Hibernate
            generatedColumnType.put(columnName, generatedRes.getString("TYPE_NAME"));
            generatedColumnNullable.put(columnName, generatedRes.getString("IS_NULLABLE"));
            generatedColumnAutoIncrement.put(columnName, generatedRes.getString("IS_AUTOINCREMENT"));
        }
        generatedRes.close();
    }

    @Test
    public void checkSameColumns() {
        assertTrue("L'une des colonnes générées n'est pas présente dans la table de référence",
                refColumnType.keySet().containsAll(generatedColumnType.keySet()));
        assertTrue("L'une des colonnes de la table de référence n'est pas présente dans la table générée",
                generatedColumnType.keySet().containsAll(refColumnType.keySet()));
    }

    @Test
    public void checkColumnsTypes() {
        Enumeration<String> keys = refColumnType.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            assertTrue(String.format(FAILURE_MSG, "Type", key, refColumnType.get(key), generatedColumnType.get(key)),
                    refColumnType.get(key).equals(generatedColumnType.get(key)));
        }
    }

    @Test
    public void checkColumnsNullable() {
        Enumeration<String> keys = refColumnType.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            assertTrue(
                    String.format(FAILURE_MSG, "Nullable", key, refColumnNullable.get(key),
                            generatedColumnNullable.get(key)),
                    refColumnNullable.get(key).equals(generatedColumnNullable.get(key)));
        }
    }

    @Test
    public void checkColumnsAutoIncrement() {
        Enumeration<String> keys = refColumnType.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            assertTrue(
                    String.format(FAILURE_MSG, "AutoIncrement", key, refColumnAutoIncrement.get(key),
                            generatedColumnAutoIncrement.get(key)),
                    refColumnAutoIncrement.get(key).equals(generatedColumnAutoIncrement.get(key)));
        }
    }
}
