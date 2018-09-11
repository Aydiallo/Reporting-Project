package com.sigif.service.utilitaires;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sigif.app.SigifServicesTestConfig;
import com.sigif.bouchons.UtilisateurBouchon;
import com.sigif.modele.Utilisateur;
import com.sigif.service.SendMailService;
import com.sigif.util.GenerationUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class sendMailTest {

    @Autowired
    private SendMailService mailService;

    @Ignore
    @Test
    public void sendEmailTest(){
		String email = "user@atos.net";
		String newPassword = GenerationUtil.genereRandomPassword();
		String message = "Bonjour MeissaTest,\n Votre nouveau mot de passe est: " + newPassword;
		String objet = "[TEST] Nouveau mot de passe";
		try {
			//mailService.sendEmail(email, objet, message);
			System.out.println("************* SendMail OK ***************");
		} catch (Exception e) {
			System.out.println("************* SendEmail KO **************");
			Assert.fail(e.getMessage());
		}
	}

    @Ignore   
    @Test
    public void sendNewPasswordEmailTest(){
		Utilisateur user = UtilisateurBouchon.getUser1();
		String newPassword = GenerationUtil.genereRandomPassword();
		
		try {
			//mailService.sendNewPasswordEmail(user, newPassword);
			System.out.println("************* sendNewPasswordEmail OK ***************");
		} catch (Exception e) {
			System.out.println("************* sendNewPasswordEmail KO **************");
            Assert.fail(e.getMessage());
		}
	}
}
