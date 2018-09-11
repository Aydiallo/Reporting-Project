package com.sigif.service.utilitaires;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.service.GenerateurUidAvecSequence;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
@Transactional
public class GenererNumeroDossierTest {
		
	@Autowired
	GenerateurUidAvecSequence generateur;
		
	@Test
	@Rollback(true)
	public void generernumeroDossierTest() {
		String numeroDossier = generateur.generateTeledossierUid();
		assertNotNull(numeroDossier);
	}
	
	
	//@Test
	@Rollback(true)
	public void testGenererNumeroTeledossierParallel() throws Exception {
		int nbRepetitions = 100000;

		StopWatch sw = new StopWatch();
		sw.start();
		long count = IntStream.range(0, nbRepetitions).parallel().mapToObj(i -> {
			try {
				return generateur.generateTeledossierUid();
			} catch (Exception e) {
				return null;
			}
		}).distinct().count();
		sw.stop();

		// Aucun des 100000 générés ne sont égaux
		assertEquals(nbRepetitions, count);

		System.out.println("Temps de calcul moyen d'un numéro de télédossier : "
				+ Long.valueOf(sw.getTotalTimeMillis()).floatValue() / nbRepetitions + "ms");
	}
	
}
