
package com.sigif.util;

import org.junit.Test;

import junit.framework.Assert;

/**
 * Classe de test des fonctions utiles
 * @author Meissa / 10-05-2017
 */
@SuppressWarnings("deprecation")
public class FonctionsUtilesTest {

	
	/**
	 * Test de la fonction de cryptage d'un mot de passe
	 * @author Meissa / 10-05-2017
	 */
	@Test
	public void getCryptedPasswordTest(){
		
		String referentPassword = "19f6cc93f071762151976c0c8a5cdc91"; //samei@123
		
		String cryptedPassword = FunctionsUtils.getCryptedPassword("samei@123");
		
		Assert.assertEquals(referentPassword, cryptedPassword);

	}


}
