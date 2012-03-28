package aGeneticos.logica.abtractas;

import aGeneticos.logica.poblacion.Cromosoma;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de realizar la reproducción mediante un cruce definido en la clase que la extienda.
 */

public abstract class Cruzador {
	
	public abstract Cromosoma[] cruza(Cromosoma c1, Cromosoma c2, int puntoCruce);
}
