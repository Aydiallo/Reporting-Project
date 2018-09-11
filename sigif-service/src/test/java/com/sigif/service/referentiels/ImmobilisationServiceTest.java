package com.sigif.service.referentiels;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.enumeration.Statut;
import com.sigif.service.ImmobilisationService;
import com.sigif.testutil.AbstractDbTestCase;
import com.sigif.util.Constantes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class ImmobilisationServiceTest extends AbstractDbTestCase {

	@Autowired
	private ImmobilisationService immoService;

	
	@Test
	public void getImmobilisationByRefTest() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/immobilisation/immobilisation.xml");
		try {
			
			//Immobilisation inactif
			resultat = immoService.getImmobilisationByRef("200000000001", Statut.actif.toString());
			assertNull(resultat);			
			
			//Immo actif, idCategorieImmo="45" idUnite="7" et idTypeImmo="5"
			resultat = immoService.getImmobilisationByRef("200000000000", Statut.actif.toString());
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size()	, 9);
			Assert.assertEquals(resultat.get(Constantes.KEY_MAP_ID), "3");
			Assert.assertEquals(resultat.get(Constantes.KEY_MAP_CODE), "200000000000");
			Assert.assertEquals(resultat.get(Constantes.KEY_MAP_DESIGNATION), "terrain de tennis");
			Assert.assertEquals(resultat.get(Constantes.KEY_MAP_DESIGNATION_CATEGORIEIMMO), "Infrastructures sportives");
			Assert.assertEquals(resultat.get(Constantes.KEY_MAP_CODE_CATEGORIEIMMO), "2351PSLN");
			Assert.assertEquals(resultat.get(Constantes.KEY_MAP_DESIGNATION_TYPEIMMO), "Immeubles (Parc immobilier)");
			Assert.assertEquals(resultat.get(Constantes.KEY_MAP_CODE_TYPEIMMO), "IMM");
			Assert.assertEquals(resultat.get(Constantes.KEY_MAP_DESIGNATION_UNITE), "par pièce");
			Assert.assertEquals(resultat.get(Constantes.KEY_MAP_CODE_UNITE), "/PC");

			System.out.println("getImmobilisationByRef OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getImmobilisationByRef KO");
			Assert.fail();
		}
	}


}
