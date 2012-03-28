package aGeneticos.util;

import java.util.Random;


/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de generar números aleatorios.
 */

public class Aleatorio {
	private static int tamCromosoma;
	
	private static Random r = new Random();

	public static void setSemilla(long semilla) {
		r = new Random(semilla);
	}

	public static double doble() {
		return r.nextDouble();
	}

	public static int entero(int max) {
		return r.nextInt(max);
	}

	/**
	 * Para generar un punto de cruce en el cromosoma
	 * @param tamCromosoma Longitud del cromosoma
	 */
	public static void setLongCromosoma(int tam) {
		tamCromosoma = tam;
	}
	
	public static int getPuntoCruce(){
		// Devuelve un punto de cruce
		return r.nextInt(tamCromosoma-1);
	}

	public static boolean bool() {
		return r.nextBoolean();
	}
}
