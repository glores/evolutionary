package aGeneticos.util;

import java.util.Random;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 * 
 *         Clase encargada de generar números aleatorios.
 */

public class Aleatorio {

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

	public static int getPuntoCruce(int max) {
		// Devuelve un punto de cruce
		return r.nextInt(max - 1) + 1;
	}
	
	public static int[] get2PuntosCruce(int max1, int max2){
		int puntoCruce[] = new int[2];
		puntoCruce[0] = Aleatorio.getPuntoCruce(max1);
		puntoCruce[1] = Aleatorio.getPuntoCruce(max2);
		return puntoCruce;
	}

	public static boolean bool() {
		return r.nextBoolean();
	}
}
