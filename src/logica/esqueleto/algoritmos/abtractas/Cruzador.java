package logica.esqueleto.algoritmos.abtractas;

import logica.esqueleto.datos.Cromosoma;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase encargada de realizar la reproducci�n mediante un cruce definido en la clase que la extienda.
 */

public abstract class Cruzador {
	
	public abstract Cromosoma[] cruza(Cromosoma c1, Cromosoma c2, int puntoCruce);
}
