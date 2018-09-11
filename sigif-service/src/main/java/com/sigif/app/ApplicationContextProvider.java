package com.sigif.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Classe permettant de fournir le context Spring de l'application.
 *
 */
public class ApplicationContextProvider implements ApplicationContextAware {

    /**
     * Loggueur.
     */
	private static final Logger LOG = LoggerFactory.getLogger(ApplicationContextProvider.class);

	/**
	 * Contexte Spring de l'application.
	 */
	private static ApplicationContext applicationContext;

	/**
	 * Obtient le contexte Spring de l'application.
	 * @return le contexte Spring de l'application
	 */
	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			LOG.error("Le contexte Spring n'a pas été chargé. Vérifier la configuration.");
			// FIXME lever exception
		}

		return applicationContext;
	}	

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
