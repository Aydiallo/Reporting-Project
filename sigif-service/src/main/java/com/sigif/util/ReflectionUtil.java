package com.sigif.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;

/**
 * Utilitaires pour la reflection.
 *
 * @author Gael Weil-Jourdan
 */
public final class ReflectionUtil {

    /**
     * Constructeur privé vide car la classe est utilitaire.
     */
	private ReflectionUtil() {
		// Classe statique
	}

	// -------------------- Methodes --------------------

	/**
	 * <p>
	 * Récupère le type d'un paramètre générique.
	 * </p>
	 *
	 * @param classe         classe dont on souhaite connaitre le type du paramètre générique
	 * @param indexParametre index du paramètre concerné
	 * @return le type du paramètre
	 */
	public static Class<?> getTypeParametreGenerique(Class<?> classe, int indexParametre) {
		// Remarque : (C) Fabien Dumay, April Technologies
		// Si un jour on a besoin de gérer des cas plus complexes :
		// https://www.javacodegeeks.com/2013/12/advanced-java-generics-retreiving-generic-type-arguments.html

		Class<?> clazz = classe;
		// Remarque : max 5 niveaux, ca nous fait une petite sécurité pour des cas complexes
        final int maxNbRetry = 5;
		int retriesCount = 0;
        while (!(clazz.getGenericSuperclass() instanceof ParameterizedType) && (retriesCount < maxNbRetry)) {
			clazz = clazz.getSuperclass();
			retriesCount++;
		}

		ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
		Type actualType = parameterizedType.getActualTypeArguments()[indexParametre];

		if (actualType instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) actualType).getRawType();
		} else if (actualType instanceof Class<?>) {
			return (Class<?>) actualType;
		} else {
			throw new IllegalArgumentException(
					MessageFormat.format("Impossible de déterminer le type générique de la classe {0}", classe));
		}
	}

}
