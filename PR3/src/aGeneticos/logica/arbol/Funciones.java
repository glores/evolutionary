package aGeneticos.logica.arbol;

import java.util.ArrayList;

/**
 * Clase encargada de gestionar todo lo referente a los nodos de funciones
 * 
 * @author Gloria Pozuelo
 * @author Sergio Barja
 * 
 */
public class Funciones {
	private Tipo funcion;
	private ArrayList<Tipo> compatibles;
	private static Tipo[] funciones = { Tipo.SIC, Tipo.PROGN2, Tipo.PROGN3 };

	public Funciones() {
	}

	public Funciones(Tipo t) {
		funcion = t;
		compatibles = new ArrayList<Tipo>();
	}

	/**
	 * Comprueba si el tipo del nodo dado por parámetro es una función
	 * 
	 * @param tipoNodo
	 *            Tipo del nodo
	 * @return
	 */
	public static boolean isFuncion(Tipo tipoNodo) {
		return tipoNodo == Tipo.SIC || tipoNodo == Tipo.PROGN2
				|| tipoNodo == Tipo.PROGN3;
	}

	/**
	 * Devuelve la aridad de una función pasasda por parámetro. En otro caso devuelve -1
	 * @param tipoNodo Tipo del nodo
	 * @return
	 */
	public static int getAridad(Tipo tipoNodo) {
		if (isFuncion(tipoNodo)) {
			switch (tipoNodo) {
			case SIC:
				return 2;
			case PROGN2:
				return 2;
			case PROGN3:
				return 3;
			default:
				return -1;
			}
		} else
			return -1;
	}
	
	/**
	 * Devuelve la aridad de esta función
	 * @return
	 */
	public int getAridad(){
		switch (funcion) {
		case SIC:
			return 2;
		case PROGN2:
			return 2;
		case PROGN3:
			return 3;
		default:
			return -1;
		}
	}

	public Tipo getCompatible() {
		int aridad = getAridad(this.funcion);
		// Buscamos un compatible que no sea él mismo
		int i = 0;
		boolean encontrado = false;
		while (!encontrado && i < funciones.length) {
			encontrado = funciones[i] != this.funcion
					&& aridad == getAridad(funciones[i]);
			i++;
		}
		return funciones[i - 1];
	}
	
	public static Tipo getCompatible(Tipo f) {
		int aridad = getAridad(f);
		// Buscamos un compatible que no sea él mismo
		int i = 0;
		boolean encontrado = false;
		while (!encontrado && i < funciones.length) {
			encontrado = funciones[i] != f
					&& aridad == getAridad(funciones[i]);
			i++;
		}
		return funciones[i - 1];
	}

	/**
	 * Rellena el array de compatibles
	 */
	public void getCompatibles() {
		int aridad = getAridad(this.funcion);
		// Rellenamos el array de compatibles
		boolean compatible = false;
		for (int i = 0; i < funciones.length; i++) {
			compatible = funciones[i] != this.funcion
					&& aridad == getAridad(funciones[i]);
			if (compatible) {
				compatibles.add(funciones[i]);
			}
			i++;
		}
	}
}
