package com.sigif.testutil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.ConfigurationException;
import javax.sql.DataSource;

import org.apache.commons.lang.time.DateUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sigif.app.SigifServicesTestConfig;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public abstract class AbstractDbTestCase extends TestCase {

    /**
     * Datasource de la base de test.
     */
    @Autowired
    protected DataSource dataSource;

    public static final String DATA_TEST_FILE_FULL = "dataTestFiles/full.xml";
    public static final String DATA_TEST_FILE_EMPTY = "dataTestFiles/empty.xml";

    /**
     * Charge le fichier xml en paramètre dans la base en écrasant tout le
     * contenu actuel.
     * 
     * @param fileName
     *            Fichier XML dbUnit à insérer (sous forme
     *            dataTestFiles/full.xml")
     * @throws FileNotFoundException
     *             fichier Xml non trouvé
     * @throws SQLException
     *             Erreur lors de la tentative d'insert des données dans la base
     * @throws DatabaseUnitException
     *             Erreur lors de la tentative d'insert des données dans la base
     * @throws ConfigurationException
     *             La connexion n'est pas branchée sur la base locale
     *             "sigifTest" (protection pour éviter l'écrasement de données
     *             par erreur)
     */
    public void loadDataFileAndOverWrite(String fileName)
            throws FileNotFoundException, DatabaseUnitException, SQLException, ConfigurationException {
        if (!dataSource.getConnection().getMetaData().getURL().contains("localhost")) {
            throw new ConfigurationException("Il est déconseillé d'effectuer un nettoyage sur une base distante.");
        }
        if (!dataSource.getConnection().getMetaData().getURL().toLowerCase().contains("sigiftest")) {
            throw new ConfigurationException(
                    "Il est déconseillé d'effectuer un nettoyage sur une base qui ne soit pas sigiftest.");
        }

        FlatXmlDataSetBuilder xmlDSBuilder = new FlatXmlDataSetBuilder();
        IDatabaseConnection dbUnitConnection = new DatabaseConnection(dataSource.getConnection());
        // on indique qu'on ne tiendra pas compte de la casse pour les noms de
        // tables présents dans le XML
        xmlDSBuilder.setCaseSensitiveTableNames(false);

        InputStream inputStreamXML = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        ReplacementDataSet repdataSet = new ReplacementDataSet(xmlDSBuilder.build(inputStreamXML));
        repdataSet.addReplacementObject("[NULL]", null);
        repdataSet.addReplacementObject("[NOW_MINUS_1H]", DateUtils.addHours(new Date(), -1));
        repdataSet.addReplacementObject("[NOW_MINUS_3H]", DateUtils.addHours(new Date(), -3));
        IDataSet dataSet = repdataSet;
        // Insertion des données en écrasant le contenu
        // existant des tables présentes dans le dataset
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, dataSet);
        dbUnitConnection.close();
    }

    /**
     * Charge le fichier de test "full.xml" dans la base en écrasant tout le
     * contenu actuel.
     * 
     * @throws FileNotFoundException
     *             fichier Xml non trouvé
     * @throws SQLException
     *             Erreur lors de la tentative d'insert des données dans la base
     * @throws DatabaseUnitException
     *             Erreur lors de la tentative d'insert des données dans la base
     * @throws ConfigurationException
     */
    public void loadFullDataFileAndOverWrite()
            throws FileNotFoundException, DatabaseUnitException, SQLException, ConfigurationException {
        this.loadDataFileAndOverWrite(DATA_TEST_FILE_FULL);
    }

    /**
     * Vide la table indiquée en paramètre et toutes les tables liées à
     * celle-ci.
     * 
     * @param nomTable
     *            nom de la table à vider avant test
     * 
     * @throws DatabaseUnitException
     *             Erreur lors de la tentative de suppression des données dans
     *             la base
     * @throws SQLException
     *             Erreur lors de la tentative de suppression des données dans
     *             la base
     * @throws ConfigurationException
     *             La connexion n'est pas branchée sur la base locale
     *             "sigifTest" (protection pour éviter l'écrasement de données
     *             par erreur)
     * 
     */
    public void emptyTable(String nomTable) throws DatabaseUnitException, SQLException, ConfigurationException {
        if (!dataSource.getConnection().getMetaData().getURL().contains("localhost")) {
            throw new ConfigurationException("Il est déconseillé d'effectuer un nettoyage sur une base distante.");
        }
        if (!dataSource.getConnection().getMetaData().getURL().toLowerCase().contains("sigiftest")) {
            throw new ConfigurationException(
                    "Il est déconseillé d'effectuer un nettoyage sur une base qui ne soit pas sigiftest.");
        }

        IDatabaseConnection dbUnitConnection = new DatabaseConnection(dataSource.getConnection());
        FlatXmlDataSetBuilder xmlDSBuilder = new FlatXmlDataSetBuilder();

        // on indique qu'on ne tiendra pas compte de la casse pour les noms de
        // tables présents dans le XML
        xmlDSBuilder.setCaseSensitiveTableNames(false);

        String[] depTableNames = TablesDependencyHelper.getAllDependentTables(dbUnitConnection, nomTable);
        IDataSet depDataset = dbUnitConnection.createDataSet(depTableNames);
        // Suppression des données des tables présentes dans le dataset
        DatabaseOperation.DELETE_ALL.execute(dbUnitConnection, depDataset);
        dbUnitConnection.close();
    }

    /**
     * Vide la base.
     * 
     * @throws DatabaseUnitException
     *             Erreur lors de la tentative de suppression des données dans
     *             la base
     * @throws SQLException
     *             Erreur lors de la tentative de suppression des données dans
     *             la base
     * @throws ConfigurationException
     *             La connexion n'est pas branchée sur la base locale
     *             "sigifTest" (protection pour éviter l'écrasement de données
     *             par erreur)
     * 
     */
    public void emptyDatabase() throws DatabaseUnitException, SQLException, ConfigurationException {
        if (!dataSource.getConnection().getMetaData().getURL().contains("localhost")) {
            throw new ConfigurationException("Il est déconseillé d'effectuer un nettoyage sur une base distante.");
        }
        if (!dataSource.getConnection().getMetaData().getURL().toLowerCase().contains("sigiftest")) {
            throw new ConfigurationException(
                    "Il est déconseillé d'effectuer un nettoyage sur une base qui ne soit pas sigiftest.");
        }

        IDatabaseConnection dbUnitConnection = new DatabaseConnection(dataSource.getConnection());
        FlatXmlDataSetBuilder xmlDSBuilder = new FlatXmlDataSetBuilder();

        // on indique qu'on ne tiendra pas compte de la casse pour les noms de
        // tables présents dans le XML
        xmlDSBuilder.setCaseSensitiveTableNames(false);

        InputStream inputStreamXML =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(DATA_TEST_FILE_EMPTY);
        IDataSet depDataset = xmlDSBuilder.build(inputStreamXML);
        // Suppression des données des tables présentes dans le dataset
        DatabaseOperation.DELETE_ALL.execute(dbUnitConnection, depDataset);
        dbUnitConnection.close();
    }

    public String getDataFromTable(String request, String columnName) throws DatabaseUnitException, SQLException {
        IDatabaseConnection dbUnitConnection = new DatabaseConnection(dataSource.getConnection());
        QueryDataSet partialDataSet = new QueryDataSet(dbUnitConnection);
        partialDataSet.addTable("Result", request);
        ITable expectedTable = partialDataSet.getTable("Result");
        return getStringValue(expectedTable, 0, columnName);
    }

    public Date getDateDataFromTable(String request, String columnName) throws DatabaseUnitException, SQLException {
        IDatabaseConnection dbUnitConnection = new DatabaseConnection(dataSource.getConnection());
        QueryDataSet partialDataSet = new QueryDataSet(dbUnitConnection);
        partialDataSet.addTable("Result", request);
        ITable expectedTable = partialDataSet.getTable("Result");
        return getDirectDateValue(expectedTable, 0, columnName);
    }

    /**
     * Obtient une table fictive à partir d'un fichier xml de description pour
     * le comparer.
     * 
     * @param xmlFileName
     *            Nom du fichier contenant les données de la table
     * @param nomTable
     *            Nom de la table à extraire
     * @return le contenu de la table
     * @throws DataSetException
     *             Erreur lors de la création du Dataset à partir du fichier
     */
    public ITable getExpectedTableFromXml(String xmlFileName, String nomTable) throws DataSetException {
        InputStream inputStreamXML = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlFileName);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputStreamXML);
        ITable expectedTable = expectedDataSet.getTable(nomTable);
        return expectedTable;
    }

    public String getStringValue(ITable table, int rowNumber, String columnName) {
        try {
            return (String) table.getValue(rowNumber, columnName);
        } catch (DataSetException e) {
            return null;
        }
    }

    public Boolean getBooleanValue(ITable table, int rowNumber, String columnName) {
        try {
            return Boolean.valueOf((String) table.getValue(rowNumber, columnName));
        } catch (DataSetException e) {
            return null;
        }
    }

    public Date getDateValue(ITable table, int rowNumber, String columnName) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            String resultat = (String) table.getValue(rowNumber, columnName);
            return simpleDateFormat.parse(resultat);
        } catch (DataSetException | ParseException e) {
            return null;
        }
    }

    public Date getDirectDateValue(ITable table, int rowNumber, String columnName) {
        try {
            return (Date) table.getValue(rowNumber, columnName);
        } catch (Exception e) {
            return null;
        }
    }

    public int getintValue(ITable table, int rowNumber, String columnName) {
        try {
            return Integer.parseInt((String) table.getValue(rowNumber, columnName), 10);
        } catch (DataSetException e) {
            return -666;
        }
    }
}
