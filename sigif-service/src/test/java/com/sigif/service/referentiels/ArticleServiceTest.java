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
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.service.ArticleService;
import com.sigif.testutil.AbstractDbTestCase;
import com.sigif.util.Constantes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class ArticleServiceTest extends AbstractDbTestCase {

	@Autowired
	private ArticleService articleService;

	@Test
	public void getAllArticlesOfGDMTest()
			throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		Map<String, String> resultat = new HashMap<String, String>();
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/article/article.xml");
		try {
			// GDM id=1 : 2 articles actifs/3
			resultat = articleService.getAllArticlesOfGDM("620101");

			assertNotNull(resultat);
			Assert.assertEquals(resultat.size(), 2);
			Assert.assertEquals(resultat.get("EQUERRE"), "Equerre");
			Assert.assertEquals(resultat.get("1"), "DESK PRODUCT TEST");

			// Groupe de marchandises inactif
			resultat = articleService.getAllArticlesOfGDM("620701");
			assertNotNull(resultat);
			Assert.assertEquals(resultat.size(), 0);

			System.out.println("GET toutes les articles actifs OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET toutes les articles actifs KO");
			Assert.fail();
		}
	}
	
	@Test
    public void getArticlesByGDMAndTextTest()
            throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
        Map<String, String[]> resultat;
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/article/articleFreeSearch.xml");
        try {
            // GDM id=8 : 2 articles avec yon
            resultat = articleService.getArticlesByGDMAndText("621101", "yon");
            assertNotNull(resultat);
            assertEquals(2, ((String[]) resultat.get(Constantes.KEY_MAP_NUMERO)).length);

            // verifier les numéros de CA récuperés: la premier liste contient que
            // les code GDM
            assertEquals("621101", resultat.get(Constantes.KEY_MAP_CODE_GDM)[0]);
            assertEquals("621101", resultat.get(Constantes.KEY_MAP_CODE_GDM)[1]);
            // verifier la désignation GDM
            assertEquals("Matériels de ramassage et de stockage d'ordures", resultat.get(Constantes.KEY_MAP_GDM)[0]);
            assertEquals("Matériels de ramassage et de stockage d'ordures", resultat.get(Constantes.KEY_MAP_GDM)[1]);
            // verifier la désignation article
            assertEquals("Crayon noir", resultat.get(Constantes.KEY_MAP_DESIGNATION)[0]);
            assertEquals("trayon", resultat.get(Constantes.KEY_MAP_DESIGNATION)[1]);
            // verifier le num article
            assertEquals("CRAYON", resultat.get(Constantes.KEY_MAP_NUMERO)[0]);
            assertEquals("TRAYAU", resultat.get(Constantes.KEY_MAP_NUMERO)[1]);
            // verifier le code unité
            assertEquals("/PC", resultat.get(Constantes.KEY_MAP_CODE_UNITE)[0]);
            assertEquals("MLM", resultat.get(Constantes.KEY_MAP_CODE_UNITE)[1]);
            // la désignation unité
            assertEquals("par pièce", resultat.get(Constantes.KEY_MAP_UNITE)[0]);
            assertEquals("ml mat.ac.", resultat.get(Constantes.KEY_MAP_UNITE)[1]);

            // GDM id=2 : 2 articles actifs / 3 avec cla*eur
            resultat = articleService.getArticlesByGDMAndText("620501", "Cla*eur");
            assertNotNull(resultat);
            assertEquals(2, ((String[]) resultat.get(Constantes.KEY_MAP_NUMERO)).length);

            // verifier les numéros de CA récuperés: la premier liste contient que
            // les code GDM
            assertEquals("620501", resultat.get(Constantes.KEY_MAP_CODE_GDM)[0]);
            assertEquals("620501", resultat.get(Constantes.KEY_MAP_CODE_GDM)[1]);
            // verifier la désignation GDM
            assertEquals("Accessoires  de bureau", resultat.get(Constantes.KEY_MAP_GDM)[0]);
            assertEquals("Accessoires  de bureau", resultat.get(Constantes.KEY_MAP_GDM)[1]);
            // verifier la désignation article
            assertEquals("aClasseur documentsq", resultat.get(Constantes.KEY_MAP_DESIGNATION)[0]);
            assertEquals("Clapeur documentsdsq", resultat.get(Constantes.KEY_MAP_DESIGNATION)[1]);
            // verifier le num article
            assertEquals("CLASSEURTRUC", resultat.get(Constantes.KEY_MAP_NUMERO)[0]);
            assertEquals("CLASSEURDOC", resultat.get(Constantes.KEY_MAP_NUMERO)[1]);
            // verifier le code unité
            assertEquals("L", resultat.get(Constantes.KEY_MAP_CODE_UNITE)[0]);
            assertEquals("L", resultat.get(Constantes.KEY_MAP_CODE_UNITE)[1]);
            // la désignation unité
            assertEquals("Litre", resultat.get(Constantes.KEY_MAP_UNITE)[0]);
            assertEquals("Litre", resultat.get(Constantes.KEY_MAP_UNITE)[1]);


            // GDM id=2 : 2 articles actifs / 3 avec cla*eur
            resultat = articleService.getArticlesByGDMAndText("620501", "Cla%eur");
            assertNotNull(resultat);
            assertEquals(2, ((String[]) resultat.get(Constantes.KEY_MAP_NUMERO)).length);

            // verifier les numéros de CA récuperés: la premier liste contient que
            // les code GDM
            assertEquals("620501", resultat.get(Constantes.KEY_MAP_CODE_GDM)[0]);
            assertEquals("620501", resultat.get(Constantes.KEY_MAP_CODE_GDM)[1]);
            // verifier la désignation GDM
            assertEquals("Accessoires  de bureau", resultat.get(Constantes.KEY_MAP_GDM)[0]);
            assertEquals("Accessoires  de bureau", resultat.get(Constantes.KEY_MAP_GDM)[1]);
            // verifier la désignation article
            assertEquals("aClasseur documentsq", resultat.get(Constantes.KEY_MAP_DESIGNATION)[0]);
            assertEquals("Clapeur documentsdsq", resultat.get(Constantes.KEY_MAP_DESIGNATION)[1]);
            // verifier le num article
            assertEquals("CLASSEURTRUC", resultat.get(Constantes.KEY_MAP_NUMERO)[0]);
            assertEquals("CLASSEURDOC", resultat.get(Constantes.KEY_MAP_NUMERO)[1]);
            // verifier le code unité
            assertEquals("L", resultat.get(Constantes.KEY_MAP_CODE_UNITE)[0]);
            assertEquals("L", resultat.get(Constantes.KEY_MAP_CODE_UNITE)[1]);
            // la désignation unité
            assertEquals("Litre", resultat.get(Constantes.KEY_MAP_UNITE)[0]);
            assertEquals("Litre", resultat.get(Constantes.KEY_MAP_UNITE)[1]);
            
            // GDM id=2 : 2 articles actifs / 3 avec cla*eur
            resultat = articleService.getArticlesByGDMAndText("620101", "d'eco");
            assertNotNull(resultat);
            assertEquals(1, ((String[]) resultat.get(Constantes.KEY_MAP_NUMERO)).length);

            // verifier les numéros de CA récuperés: la premier liste contient que
            // les code GDM
            assertEquals("620101", resultat.get(Constantes.KEY_MAP_CODE_GDM)[0]);
            // verifier la désignation GDM
            assertEquals("Rame, paquet et rouleau de papier", resultat.get(Constantes.KEY_MAP_GDM)[0]);
            // verifier la désignation article
            assertEquals("Equerre d'écolier", resultat.get(Constantes.KEY_MAP_DESIGNATION)[0]);
            // verifier le num article
            assertEquals("EQUERRE", resultat.get(Constantes.KEY_MAP_NUMERO)[0]);
            // verifier le code unité
            assertEquals("/PC", resultat.get(Constantes.KEY_MAP_CODE_UNITE)[0]);
            // la désignation unité
            assertEquals("par pièce", resultat.get(Constantes.KEY_MAP_UNITE)[0]);            

            resultat = articleService.getArticlesByGDMAndText("620101", "rr\"dfezf ere");
            Assert.assertEquals(1, resultat.get(Constantes.KEY_MAP_NUMERO).length);
            
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
	
	@Test
	public void getArticleInformationsTest() throws ConfigurationException, DatabaseUnitException, SQLException, FileNotFoundException, ApplicationException, TechnicalException {
	    this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/article/articleFreeSearch.xml");
        Map<String, String> resultat;
        resultat = articleService.getArticleInformations("CLASSEURTRUC");
        assertEquals("620501", resultat.get(Constantes.KEY_MAP_CODE_GDM));
        // verifier la désignation GDM
        assertEquals("Accessoires  de bureau", resultat.get(Constantes.KEY_MAP_GDM));
        // verifier la désignation article
        assertEquals("aClasseur documentsq", resultat.get(Constantes.KEY_MAP_DESIGNATION));
        // verifier le num article
        assertEquals("CLASSEURTRUC", resultat.get(Constantes.KEY_MAP_NUMERO));
        // verifier le code unité
        assertEquals("L", resultat.get(Constantes.KEY_MAP_CODE_UNITE));
        // la désignation unité
        assertEquals("Litre", resultat.get(Constantes.KEY_MAP_UNITE));
        

        resultat = articleService.getArticleInformations("EQUERRE");
        assertEquals("620101", resultat.get(Constantes.KEY_MAP_CODE_GDM));
        // verifier la désignation GDM
        assertEquals("Rame, paquet et rouleau de papier", resultat.get(Constantes.KEY_MAP_GDM));
        // verifier la désignation article
        assertEquals("Equerre d'écolier", resultat.get(Constantes.KEY_MAP_DESIGNATION));
        // verifier le num article
        assertEquals("EQUERRE", resultat.get(Constantes.KEY_MAP_NUMERO));
        // verifier le code unité
        assertEquals("/PC", resultat.get(Constantes.KEY_MAP_CODE_UNITE));
        // la désignation unité
        assertEquals("par pièce", resultat.get(Constantes.KEY_MAP_UNITE));
	}	
}
