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
	 * 
	 * @param tamCromosoma
	 *            Longitud del cromosoma
	 */
	public static void setLongCromosoma(int tam) {
		tamCromosoma = tam;
	}

	public static int getPuntoCruce() {
		// Devuelve un punto de cruce
		return r.nextInt(tamCromosoma - 1);
	}

	// Traido de algoritmo
	public static int[] get2PuntosCruceOrdenados() {
		int puntoCruce1 = Aleatorio.getPuntoCruce() + 1; // Para que no empiece
															// en 0
		int puntoCruce2 = Aleatorio.getPuntoCruce() + 1;
		int puntoCruce[] = new int[2];
		while (puntoCruce2 == puntoCruce1)
			puntoCruce2 = Aleatorio.getPuntoCruce() + 1;
		if (puntoCruce1 > puntoCruce2) {
			puntoCruce[0] = puntoCruce2;
			puntoCruce[1] = puntoCruce1;
		} else if (puntoCruce2 >= puntoCruce1) {
			puntoCruce[1] = puntoCruce2;
			puntoCruce[0] = puntoCruce1;
		}
		return puntoCruce;
	}
	
	public static int[] get2PuntosCruce(){
		int puntoCruce[] = new int[2];
		puntoCruce[0] = Aleatorio.getPuntoCruce();
		puntoCruce[1] = Aleatorio.getPuntoCruce();
		return puntoCruce;
	}

	/**
	 * Devuelve un array de longitud aleatoria(de 1 a num) de aleatorios (de 0 a tamCromosoma-1)
	 * en un array
	 * @param num Máximo de aleatorios
	 * @return Array de aleatorios que corresponden con posiciones del cromosoma
	 */
	public static int[] getXPuntosInsercion(int num) {
		int tam = r.nextInt(num - 1) + 1;
		return getNPuntosInsercion(tam);
	}

	/**
	 * Devuelve un array de longitud n de aleatorios (de 0 a tamCromosoma-1)
	 * @param n
	 * @return
	 */
	private static int[] getNPuntosInsercion(int n) {
		int[] puntos = new int[n];
		for (int i = 0; i < n; i++) {
			puntos[i] = getPuntoCruce();
			while (contiene(puntos, puntos[i], i)) {
				puntos[i] = getPuntoCruce();
			}
		}
		return puntos;
	}

	public static String[] getPuntos(int num) {
		int tam = r.nextInt(num - 1) + 1;

		String[] puntos = new String[tam];
		for (int i = 0; i < tam; i++) {
			puntos[i] = Integer.toString(getPuntoCruce());
			while (contiene(puntos, puntos[i], i)) {
				puntos[i] = Integer.toString(getPuntoCruce());
			}
		}

		return puntos;
	}

	private static boolean contiene(String[] array, String elemento, int extremo) {
		boolean resultado = false;
		int pos = 0;
		while (!resultado && pos < extremo) {
			resultado = array[pos].equals(elemento);
			pos++;
		}
		return resultado;
	}

	private static boolean contiene(int[] array, int elemento, int extremo) {
		boolean resultado = false;
		int pos = 0;
		while (!resultado && pos < extremo) {
			resultado = array[pos] == elemento;
			pos++;
		}
		return resultado;
	}

	public static boolean bool() {
		return r.nextBoolean();
	}
}
