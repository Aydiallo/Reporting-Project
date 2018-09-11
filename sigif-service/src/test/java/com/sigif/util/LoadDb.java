package com.sigif.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.time.DateUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Ignore;
import org.junit.Test;

import com.sigif.testutil.AbstractDbTestCase;

public class LoadDb extends AbstractDbTestCase {

    public static final String DATA_TEST_FILE_FULL = "dataTestFiles/full.xml";
    
    @Ignore
    @Test
    public void loadFull() throws ConfigurationException, DatabaseUnitException, SQLException, FileNotFoundException {
        this.loadDataFileAndOverWriteWithoutWarn(DATA_TEST_FILE_FULL);
    }

    public void loadDataFileAndOverWriteWithoutWarn(String fileName)
            throws FileNotFoundException, DatabaseUnitException, SQLException, ConfigurationException {
        if (!dataSource.getConnection().getMetaData().getURL().contains("localhost")) {
            throw new ConfigurationException("Il est déconseillé d'effectuer un nettoyage sur une base distante.");
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
}
