package com.sigif.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.stream.IntStream;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.StopWatch;

public class GenerationUtilTest {

	@Test
	public void testGenererNumeroTeledossierSansSequence() throws Exception {
		String n = GenerationUtil.generateTeledossierNumberWithoutSequence();
		assertNotNull(n);
	}

	@Ignore
	@Test
	public void testGenererNumeroTeledossierParallel() throws Exception {
		int nbRepetitions = 100000;

		StopWatch sw = new StopWatch();
		sw.start();
		long count = IntStream.range(0, nbRepetitions).parallel().mapToObj(i -> {
			try {
				return GenerationUtil.generateTeledossierNumberWithoutSequence();
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

	
	@Test
	public void genereRandomPasswordTest(){
		for(int i=0; i<20; i++){
			System.out.println("Mot de passe aléatoire généré "+i+" : "+GenerationUtil.genereRandomPassword());
		}
	}
}
