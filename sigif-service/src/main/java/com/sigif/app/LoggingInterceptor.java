package com.sigif.app;

import java.lang.reflect.Array;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

/**
 * Cette classe est un "advice" (jargon du framework Spring) permettant de logger les
 * entrées/sorties/Exceptions des méthodes définies dans le bean d'id "loggingAdvisor" du
 * GlobalApplicationContext.xml. Le logging est donc intégré dans Delia comme un aspect (AOP)
 *
 * @author Christophe Robine et Gaël Weil-Jourdan
 */
@Aspect
public class LoggingInterceptor {

	@Before("bean(*DAO) || bean(*Service)")
	public void before(JoinPoint joinPoint) {
		Object[] p_Args = joinPoint.getArgs();
		String p_Method = joinPoint.getSignature().getName();
		Object p_Target = joinPoint.getTarget();

		String message = null;
		String messagearguments = null;
		Logger logger = LoggerFactory.getLogger(getTargetObject(p_Target).getClass());
		if (true == logger.isDebugEnabled()) {
			message = ">>>>> " + p_Method;
			if (null != p_Args && p_Args.length > 0) {
				try {
					messagearguments = "(";
					for (int i = 0; i < p_Args.length; i++) {
						if (null == p_Args[i]) {
							messagearguments += p_Args[i];
						} else {
							messagearguments += p_Args[i].toString();
						}
						if (i == p_Args.length - 1) {
							messagearguments += ")";
						} else {
							messagearguments += ",";
						}
					}
				} catch (Exception ze) {
					// on ne fait rien
				}
				if (messagearguments.indexOf("password") == -1) {
					message += messagearguments;
				}
			}
			logger.debug(message);
		}
	}

	@AfterReturning(value = "bean(*DAO) || bean(*Service)", returning = "p_ReturnValue")
	public void afterReturning(JoinPoint joinPoint, Object p_ReturnValue) {
		String p_Method = joinPoint.getSignature().getName();
		Object p_Target = joinPoint.getTarget();

		Logger logger = LoggerFactory.getLogger(getTargetObject(p_Target).getClass());

		if (true == logger.isDebugEnabled()) {
			String message = "<<<<< " + p_Method + ":";
			if (null != p_ReturnValue) {
				try {
					if (true == p_ReturnValue.getClass().isArray()) {
						int taille = Array.getLength(p_ReturnValue);
						message += "[";
						for (int i = 0; i < taille - 1; i++) {
							message += Array.get(p_ReturnValue, i);
							message += ",";
						}
						if (taille > 0) {
							message += Array.get(p_ReturnValue, taille - 1);
						}
						message += "]";
					} else {
						message += p_ReturnValue;
					}
				} catch (Exception ze) {
					ze.printStackTrace();
					// on ne fait rien
				}
			}
			logger.debug(message);
		}
	}

	@AfterThrowing(value = "bean(*DAO) || bean(*Service)", throwing = "p_Ex")
	public void afterThrowing(JoinPoint joinPoint, Throwable p_Ex) {
		String p_Method = joinPoint.getSignature().getName();
		Object p_Target = joinPoint.getTarget();

		Logger logger = LoggerFactory.getLogger(getTargetObject(p_Target).getClass());

		if (true == logger.isErrorEnabled()) {
			logger.error(
					"EXCEPTION (" + p_Ex.getClass().getName() + ") IN METHOD : " + p_Method + " - Exception is " + p_Ex
							.getMessage());
		}
		if (true == logger.isDebugEnabled()) {
			logger.debug("TRACE de l'exception (" + p_Ex.getClass().getName() + ") IN METHOD : " + p_Method
					+ " - Exception is " + p_Ex.getMessage(), p_Ex);
		}
	}

	/**
	 * Si l'objet donné est un proxy Spring, renvoie la cible du proxy. Sinon, renvoie
	 * l'objet.
	 * <p/>
	 * Utilisé pour l'affichage correct de la classe (et non du proxy) dans les logs, dans le cas de services
	 * transactionnels.
	 *
	 * @param p_Object Un objet, potentiellement un proxy Spring.
	 * @return La cible du proxy si l'objet donné est un proxy, sinon l'objet donné.
	 */
	private Object getTargetObject(final Object p_Object) {
		try {
			if (AopUtils.isJdkDynamicProxy(p_Object)) {
				return ((Advised) p_Object).getTargetSource().getTarget();
			}
		} catch (Exception e) {
			// Echec dans l'obtention de la cible. On retourne le proxy.
		}
		return p_Object;
	}

}
