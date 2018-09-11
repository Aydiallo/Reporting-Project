package com.sigif.service.ca;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.service.PosteCommandeAchatService;
import com.sigif.testutil.AbstractDbTestCase;
import com.sigif.util.Constantes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
@Transactional
public class PosteCommandeAchatServiceTest extends AbstractDbTestCase {

	@Autowired
	private PosteCommandeAchatService posteCAService;

	@Before
	public void loadData() throws FileNotFoundException, ConfigurationException, DatabaseUnitException, SQLException {
		this.emptyDatabase();
		this.loadDataFileAndOverWrite("dataTestFiles/posteCAService/posteCAService.xml");
	}

	@Test
	public void testGetOneClosedItemsByCA() {
		try {
			Map<String, String[]> mapInfosPostes = this.posteCAService.getClosedItemsByCA("9210000004");
			assertTrue(!mapInfosPostes.isEmpty());

			String[] ids = mapInfosPostes.get(Constantes.KEY_MAP_ID);
			String[] numPostes = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO);
			String[] typesAchat = mapInfosPostes.get(Constantes.KEY_MAP_TYPE_ACHAT);
			String[] references = mapInfosPostes.get(Constantes.KEY_MAP_REFERENCE);
			String[] designations = mapInfosPostes.get(Constantes.KEY_MAP_DESIGNATION);
			String[] qtesCommandees = mapInfosPostes.get(Constantes.KEY_MAP_QTE_COMMANDEE);
			String[] qtesAcceptees = mapInfosPostes.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
			String[] statuts = mapInfosPostes.get(Constantes.KEY_MAP_STATUT);

			String[] expids = { "10" };
			String[] expnumPostes = { "04" };
			String[] exptypesAchat = { "Achats stockés" };
			String[] expreferences = { "GOMME" };
			String[] expdesignations = { "Gommes (par 2)" };
			String[] expqtesCommandees = { "10 (Pièce)" };
			String[] expqtesAcceptees = { "0 (Pièce)" };
			String[] expstatuts = { "Clôturé" };

			assertEquals(expids[0], ids[0]);
			assertEquals(expnumPostes[0], numPostes[0]);
			assertEquals(exptypesAchat[0], typesAchat[0]);
			assertEquals(expreferences[0], references[0]);
			assertEquals(expdesignations[0], designations[0]);
			assertEquals(expqtesCommandees[0], qtesCommandees[0]);
			assertEquals(expqtesAcceptees[0], qtesAcceptees[0]);
			assertEquals(expstatuts[0], statuts[0]);
		} catch (ApplicationException | TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetTwoClosedItemsByCA() {
		try {
			Map<String, String[]> mapInfosPostes = this.posteCAService.getClosedItemsByCA("9210000002");
			assertTrue(!mapInfosPostes.isEmpty());

			String[] ids = mapInfosPostes.get(Constantes.KEY_MAP_ID);
			String[] numPostes = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO);
			String[] typesAchat = mapInfosPostes.get(Constantes.KEY_MAP_TYPE_ACHAT);
			String[] references = mapInfosPostes.get(Constantes.KEY_MAP_REFERENCE);
			String[] designations = mapInfosPostes.get(Constantes.KEY_MAP_DESIGNATION);
			String[] qtesCommandees = mapInfosPostes.get(Constantes.KEY_MAP_QTE_COMMANDEE);
			String[] qtesAcceptees = mapInfosPostes.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
			String[] statuts = mapInfosPostes.get(Constantes.KEY_MAP_STATUT);

			String[] expids = { "4", "5" };
			String[] expnumPostes = { "02", "03" };
			String[] exptypesAchat = { "Fonctionnement", "Immobilisation" };
			String[] expreferences = { "623901", "200000000001" };
			String[] expdesignations = { "Produits alimentaires : blé", "terrain de foot" };
			String[] expqtesCommandees = { "1000 (par pièce)", "1 (Pièce)" };
			String[] expqtesAcceptees = { "0 (par pièce)", "0 (Pièce)" };
			String[] expstatuts = { "Clôturé", "Clôturé" };

			assertEquals(expids[0], ids[0]);
			assertEquals(expnumPostes[0], numPostes[0]);
			assertEquals(exptypesAchat[0], typesAchat[0]);
			assertEquals(expreferences[0], references[0]);
			assertEquals(expdesignations[0], designations[0]);
			assertEquals(expqtesCommandees[0], qtesCommandees[0]);
			assertEquals(expqtesAcceptees[0], qtesAcceptees[0]);
			assertEquals(expstatuts[0], statuts[0]);

			assertEquals(expids[1], ids[1]);
			assertEquals(expnumPostes[1], numPostes[1]);
			assertEquals(exptypesAchat[1], typesAchat[1]);
			assertEquals(expreferences[1], references[1]);
			assertEquals(expdesignations[1], designations[1]);
			assertEquals(expqtesCommandees[1], qtesCommandees[1]);
			assertEquals(expqtesAcceptees[1], qtesAcceptees[1]);
			assertEquals(expstatuts[1], statuts[1]);
		} catch (ApplicationException | TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetNoneClosedItemsByCA() {
		try {
			Map<String, String[]> mapInfosPostes = this.posteCAService.getClosedItemsByCA("9210000001");
			assertNull(mapInfosPostes);
		} catch (ApplicationException | TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetTwoReceivableItemsByCA() {
		try {
			Map<String, String[]> mapInfosPostes = this.posteCAService
					.getReceivableItemsByCaAndSpendingService("9210000004  ", "  12200820100");
			assertTrue(!mapInfosPostes.isEmpty());

			String[] ids = mapInfosPostes.get(Constantes.KEY_MAP_ID);
			String[] numPostes = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO);
			String[] typesAchat = mapInfosPostes.get(Constantes.KEY_MAP_TYPE_ACHAT);
			String[] references = mapInfosPostes.get(Constantes.KEY_MAP_REFERENCE);
			String[] designations = mapInfosPostes.get(Constantes.KEY_MAP_DESIGNATION);
			String[] qtesCommandees = mapInfosPostes.get(Constantes.KEY_MAP_QTE_COMMANDEE);
			String[] qtesAcceptees = mapInfosPostes.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
			String[] statuts = mapInfosPostes.get(Constantes.KEY_MAP_STATUT);

			String[] expids = { "8", "9" };
			String[] expnumPostes = { "01", "02" };
			String[] exptypesAchat = { "Achats stockés", "Immobilisation" };
			String[] expreferences = { "PAPIER", "200000000000" };
			String[] expdesignations = { "Ramette Papier A4", "1 terrain de tennis" };
			String[] expqtesCommandees = { "100 (Pièce)", "4 (Pièce)" };
			String[] expqtesAcceptees = { "20 (Pièce)", "1 (Pièce)" };
			String[] expstatuts = { "Non réceptionné", "Non réceptionné" };

			assertEquals(expids[0], ids[0]);
			assertEquals(expnumPostes[0], numPostes[0]);
			assertEquals(exptypesAchat[0], typesAchat[0]);
			assertEquals(expreferences[0], references[0]);
			assertEquals(expdesignations[0], designations[0]);
			assertEquals(expqtesCommandees[0], qtesCommandees[0]);
			assertEquals(expqtesAcceptees[0], qtesAcceptees[0]);
			assertEquals(expstatuts[0], statuts[0]);

			assertEquals(expids[1], ids[1]);
			assertEquals(expnumPostes[1], numPostes[1]);
			assertEquals(exptypesAchat[1], typesAchat[1]);
			assertEquals(expreferences[1], references[1]);
			assertEquals(expdesignations[1], designations[1]);
			assertEquals(expqtesCommandees[1], qtesCommandees[1]);
			assertEquals(expqtesAcceptees[1], qtesAcceptees[1]);
			assertEquals(expstatuts[1], statuts[1]);
		} catch (ApplicationException | TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetNoneReceivablesItems() {
		try {
			Map<String, String[]> mapInfosPostes = this.posteCAService
					.getReceivableItemsByCaAndSpendingService("9210000012", "12200820100");
			assertNull(mapInfosPostes);
		} catch (ApplicationException | TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetReceivablesItemsByEmptyCA() {
		try {
			this.posteCAService.getReceivableItemsByCaAndSpendingService("  ", "12200820100");
			fail();
		} catch (ApplicationException e) {
			assertTrue(true);
		} catch (TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetReceivablesItemsByEmptySD() {
		try {
			this.posteCAService.getReceivableItemsByCaAndSpendingService("9210000004 ", " ");
			fail();
		} catch (ApplicationException e) {
			assertTrue(true);
		} catch (TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetTwoNotReceivableItemsByCA() {
		try {
			Map<String, String[]> mapInfosPostes = this.posteCAService
					.getNotReceivableItemsByCaAndSpendingService("9210000010  ", "  12200820100");
			assertTrue(!mapInfosPostes.isEmpty());

			String[] ids = mapInfosPostes.get(Constantes.KEY_MAP_ID);
			String[] numPostes = mapInfosPostes.get(Constantes.KEY_MAP_NUMERO);
			String[] typesAchat = mapInfosPostes.get(Constantes.KEY_MAP_TYPE_ACHAT);
			String[] references = mapInfosPostes.get(Constantes.KEY_MAP_REFERENCE);
			String[] designations = mapInfosPostes.get(Constantes.KEY_MAP_DESIGNATION);
			String[] qtesCommandees = mapInfosPostes.get(Constantes.KEY_MAP_QTE_COMMANDEE);
			String[] qtesAcceptees = mapInfosPostes.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
			String[] statuts = mapInfosPostes.get(Constantes.KEY_MAP_STATUT);

			String[] expids = { "20", "23" };
			String[] expnumPostes = { "02", "05" };
			String[] exptypesAchat = { "Fonctionnement", "Achats stockés" };
			String[] expreferences = { "GOMME", "AUTRES" };
			String[] expdesignations = { "Gommes (par 2)", "Autres (pardix)" };
			String[] expqtesCommandees = { "8 (Pièce)", "39 (Pièce)" };
			String[] expqtesAcceptees = { "0 (Pièce)", "32 (Pièce)" };
			String[] expstatuts = { "Non réceptionné", "Non réceptionné" };

			assertEquals(expids[0], ids[0]);
			assertEquals(expnumPostes[0], numPostes[0]);
			assertEquals(exptypesAchat[0], typesAchat[0]);
			assertEquals(expreferences[0], references[0]);
			assertEquals(expdesignations[0], designations[0]);
			assertEquals(expqtesCommandees[0], qtesCommandees[0]);
			assertEquals(expqtesAcceptees[0], qtesAcceptees[0]);
			assertEquals(expstatuts[0], statuts[0]);

			assertEquals(expids[1], ids[1]);
			assertEquals(expnumPostes[1], numPostes[1]);
			assertEquals(exptypesAchat[1], typesAchat[1]);
			assertEquals(expreferences[1], references[1]);
			assertEquals(expdesignations[1], designations[1]);
			assertEquals(expqtesCommandees[1], qtesCommandees[1]);
			assertEquals(expqtesAcceptees[1], qtesAcceptees[1]);
			assertEquals(expstatuts[1], statuts[1]);
		} catch (ApplicationException | TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetNoneNotReceivablesItems() {
		try {
			Map<String, String[]> mapInfosPostes = this.posteCAService
					.getNotReceivableItemsByCaAndSpendingService("9210000012", "12200820100");
			assertNull(mapInfosPostes);
		} catch (ApplicationException | TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetNotReceivablesItemsByEmptyCA() {
		try {
			this.posteCAService.getNotReceivableItemsByCaAndSpendingService("  ", "12200820100");
			fail("Exception non remontée");
		} catch (ApplicationException e) {
			assertTrue(true);
			return;
		} catch (TechnicalException e) {
			e.printStackTrace();
			fail("Exception technique non prévue");
		}
	}

	@Test
	public void testGetNotReceivablesItemsByEmptySD() {
		try {
			this.posteCAService.getNotReceivableItemsByCaAndSpendingService("9210000004 ", " ");
			fail();
		} catch (ApplicationException e) {
			assertTrue(true);
		} catch (TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetPosteCaInformationByEmptyCa() {
		try {
			this.posteCAService.getPosteCAInformation(" ", "12200820100");
			fail();
		} catch (ApplicationException e) {
			assertTrue(true);
		} catch (TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetPosteCaInformationByEmptyNumPoste() {
		try {
			this.posteCAService.getPosteCAInformation("9210000004", " ");
			fail();
		} catch (ApplicationException e) {
			assertTrue(true);
		} catch (TechnicalException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetPosteCaInformationWithoutCsf() {
		try {
			Map<String, String> posteCAInformation = this.posteCAService.getPosteCAInformation("9210000005", "01 ");
			assertNotNull(posteCAInformation);

			String id = posteCAInformation.get(Constantes.KEY_MAP_ID);
			String typeAchat = posteCAInformation.get(Constantes.KEY_MAP_TYPE_ACHAT);
			String reference = posteCAInformation.get(Constantes.KEY_MAP_REFERENCE);
			String designation = posteCAInformation.get(Constantes.KEY_MAP_DESIGNATION);
			String qtesCommandee = posteCAInformation.get(Constantes.KEY_MAP_QTE_COMMANDEE);
			String qtesAcceptee = posteCAInformation.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
			String statut = posteCAInformation.get(Constantes.KEY_MAP_STATUT);
			String commentaire = posteCAInformation.get(Constantes.KEY_MAP_COMMENTAIRE);
			String categorie = posteCAInformation.get(Constantes.KEY_MAP_CATEGORIE);
			String unite = posteCAInformation.get(Constantes.KEY_MAP_UNITE);
			String prix = posteCAInformation.get(Constantes.KEY_MAP_PRIX);
			String devise = posteCAInformation.get(Constantes.KEY_MAP_DEVISE);
			String servDep = posteCAInformation.get(Constantes.KEY_MAP_SERVICE_DEPENSIER);
			String servBen = posteCAInformation.get(Constantes.KEY_MAP_SERVICE_BENEFICIAIRE);
			String nbPostes = posteCAInformation.get(Constantes.KEY_MAP_NB_POSTES_CSF);

			String expid = "11";
			String exptypeAchat = "Achats stockés";
			String expreference = "PAPIER";
			String expdesignation = "Ramette Papier A4";
			String expqtesCommandee = "150";
			String expqtesAcceptee = "135";
			String expstatut = "Non réceptionné";
			String expcommentaire = null;
			String expcategorie = "Rame, paquet et rouleau de papier";
			String expunite = "Pièce";
			String expprix = "5";
			String expdevise = "Franc CFA  BCEAO";
			String expservDep = "Cellule de planification";
			String expservBen = "Mon service";
			String expnbPostes = "0";

			assertEquals(expid, id);
			assertEquals(exptypeAchat, typeAchat);
			assertEquals(expreference, reference);
			assertEquals(expdesignation, designation);
			assertEquals(expqtesCommandee, qtesCommandee);
			assertEquals(expqtesAcceptee, qtesAcceptee);
			assertEquals(expcommentaire, commentaire);
			assertEquals(expunite, unite);
			assertEquals(expprix, prix);
			assertEquals(expdevise, devise);
			assertEquals(expservDep, servDep);
			assertEquals(expservBen, servBen);
			assertEquals(expnbPostes, nbPostes);
			assertEquals(expstatut, statut);
			assertEquals(expcategorie, categorie);

			Map<String, String> posteCAInformation1 = this.posteCAService.getPosteCAInformation("9210000001", "02 ");
			assertNotNull(posteCAInformation1);

			id = posteCAInformation1.get(Constantes.KEY_MAP_ID);
			typeAchat = posteCAInformation1.get(Constantes.KEY_MAP_TYPE_ACHAT);
			reference = posteCAInformation1.get(Constantes.KEY_MAP_REFERENCE);
			designation = posteCAInformation1.get(Constantes.KEY_MAP_DESIGNATION);
			qtesCommandee = posteCAInformation1.get(Constantes.KEY_MAP_QTE_COMMANDEE);
			qtesAcceptee = posteCAInformation1.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
			statut = posteCAInformation1.get(Constantes.KEY_MAP_STATUT);
			commentaire = posteCAInformation1.get(Constantes.KEY_MAP_COMMENTAIRE);
			categorie = posteCAInformation1.get(Constantes.KEY_MAP_CATEGORIE);
			unite = posteCAInformation1.get(Constantes.KEY_MAP_UNITE);
			prix = posteCAInformation1.get(Constantes.KEY_MAP_PRIX);
			devise = posteCAInformation1.get(Constantes.KEY_MAP_DEVISE);
			servDep = posteCAInformation1.get(Constantes.KEY_MAP_SERVICE_DEPENSIER);
			servBen = posteCAInformation1.get(Constantes.KEY_MAP_SERVICE_BENEFICIAIRE);
			nbPostes = posteCAInformation1.get(Constantes.KEY_MAP_NB_POSTES_CSF);

			expid = "2";
			exptypeAchat = "Fonctionnement";
			expreference = "621501";
			expdesignation = "Pétrole";
			expqtesCommandee = "1000";
			expqtesAcceptee = "0";
			expstatut = "Non réceptionné";
			expcommentaire = null;
			expcategorie = "Carburants et combustibles";
			expunite = "";
			expprix = "1000";
			expdevise = "Franc CFA  BCEAO";
			expservDep = "Direction  du développement du capital h";
			expservBen = "Direction  du développement du capital h";
			expnbPostes = "0";

			assertEquals(expid, id);
			assertEquals(exptypeAchat, typeAchat);
			assertEquals(expreference, reference);
			assertEquals(expdesignation, designation);
			assertEquals(expqtesCommandee, qtesCommandee);
			assertEquals(expqtesAcceptee, qtesAcceptee);
			assertEquals(expcommentaire, commentaire);
			assertEquals(expunite, unite);
			assertEquals(expprix, prix);
			assertEquals(expdevise, devise);
			assertEquals(expservDep, servDep);
			assertEquals(expservBen, servBen);
			assertEquals(expnbPostes, nbPostes);
			assertEquals(expstatut, statut);
			assertEquals(expcategorie, categorie);

			Map<String, String> posteCAInformation11 = this.posteCAService.getPosteCAInformation("9210000006", "01 ");
			assertNotNull(posteCAInformation11);

			id = posteCAInformation11.get(Constantes.KEY_MAP_ID);
			typeAchat = posteCAInformation11.get(Constantes.KEY_MAP_TYPE_ACHAT);
			reference = posteCAInformation11.get(Constantes.KEY_MAP_REFERENCE);
			designation = posteCAInformation11.get(Constantes.KEY_MAP_DESIGNATION);
			qtesCommandee = posteCAInformation11.get(Constantes.KEY_MAP_QTE_COMMANDEE);
			qtesAcceptee = posteCAInformation11.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
			statut = posteCAInformation11.get(Constantes.KEY_MAP_STATUT);
			commentaire = posteCAInformation11.get(Constantes.KEY_MAP_COMMENTAIRE);
			categorie = posteCAInformation11.get(Constantes.KEY_MAP_CATEGORIE);
			unite = posteCAInformation11.get(Constantes.KEY_MAP_UNITE);
			prix = posteCAInformation11.get(Constantes.KEY_MAP_PRIX);
			devise = posteCAInformation11.get(Constantes.KEY_MAP_DEVISE);
			servDep = posteCAInformation11.get(Constantes.KEY_MAP_SERVICE_DEPENSIER);
			servBen = posteCAInformation11.get(Constantes.KEY_MAP_SERVICE_BENEFICIAIRE);
			nbPostes = posteCAInformation11.get(Constantes.KEY_MAP_NB_POSTES_CSF);

			expid = "12";
			exptypeAchat = "Immobilisation";
			expreference = "200000000000";
			expdesignation = "Gommes (par 2)";
			expqtesCommandee = "8";
			expqtesAcceptee = "3";
			expstatut = "Partiellement réceptionné";
			expcommentaire = null;
			expcategorie = "Immeubles (Parc immobilier)/Infrastructures sportives";
			expunite = "Pièce";
			expprix = "1";
			expdevise = "Franc CFA  BCEAO";
			expservDep = "Inspection des postes diplomatiques et c";
			expservBen = "Service du chiffre";
			expnbPostes = "0";

			assertEquals(expid, id);
			assertEquals(exptypeAchat, typeAchat);
			assertEquals(expreference, reference);
			assertEquals(expdesignation, designation);
			assertEquals(expqtesCommandee, qtesCommandee);
			assertEquals(expqtesAcceptee, qtesAcceptee);
			assertEquals(expcommentaire, commentaire);
			assertEquals(expunite, unite);
			assertEquals(expprix, prix);
			assertEquals(expdevise, devise);
			assertEquals(expservDep, servDep);
			assertEquals(expservBen, servBen);
			assertEquals(expnbPostes, nbPostes);
			assertEquals(expstatut, statut);
			assertEquals(expcategorie, categorie);
		} catch (TechnicalException | ApplicationException e) {
			e.printStackTrace();
			fail("Exception non prévue");
		}
	}

	@Test
	public void getAllPosteOfCATest() throws TechnicalException {
		try {
			Map<String, String[]> listeMap = posteCAService.getAllPosteOfCA("9210000010"); // id=22
			assertNotNull(listeMap);
			assertEquals(listeMap.size(), 7);
			String[] idsaps = listeMap.get(Constantes.KEY_MAP_NUMERO);
			String[] references = listeMap.get(Constantes.KEY_MAP_REFERENCE);
			String[] designations = listeMap.get(Constantes.KEY_MAP_DESIGNATION);
			String[] qteAcceptees = listeMap.get(Constantes.KEY_MAP_QTE_ACCEPTEE);
            String[] qteCommandees = listeMap.get(Constantes.KEY_MAP_QTE_COMMANDEE);
            String[] typesAchat = listeMap.get(Constantes.KEY_MAP_TYPE_ACHAT);
            String[] statuts = listeMap.get(Constantes.KEY_MAP_STATUT);


            Object[] expidSap = { "01","02","03","04","05","06", };
            Object[] expreferences = { "FEUILLE","GOMME","CRAYON","AUTRE","AUTRES","AUTRES", };
            Object[] expdesignations = { "Feuilles (par 100)","Gommes (par 2)","Crayons (par 8)","Autre (pardi)","Autres (pardix)","Autres (pardix)", };
            Object[] expqteAcceptees = { "92 (Pièce)","0 (Pièce)","8 (Pièce)","32 (Pièce)","32 (Pièce)","32 (Pièce)", };
            Object[] expqteCommandees = { "100 (Pièce)","8 (Pièce)","16 (Pièce)","39 (Pièce)","39 (Pièce)","39 (Pièce)", };
            Object[] exptypesAchat = { "Achats stockés","Fonctionnement","Immobilisation","Achats stockés","Achats stockés","Achats stockés", };
            Object[] expstatuts = { "Clôturé","Non réceptionné","Clôturé","Non réceptionné","Non réceptionné","Non réceptionné", };

            Assert.assertArrayEquals(expidSap, idsaps);
            Assert.assertArrayEquals(expreferences, references);
            Assert.assertArrayEquals(expdesignations, designations);
            Assert.assertArrayEquals(expqteAcceptees, qteAcceptees);
            Assert.assertArrayEquals(expqteCommandees, qteCommandees);
            Assert.assertArrayEquals(exptypesAchat, typesAchat);
            Assert.assertArrayEquals(expstatuts, statuts);
			System.out.println("GET ALL POSTE OF CA OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GET ALL POSTE OF CA KO");
			Assert.fail();
		}
	}
}
