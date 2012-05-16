package aGeneticos.util;

import java.util.Random;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 * 
 *         Clase encargada de generar n�meros aleatorios.
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
