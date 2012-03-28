package aGeneticos.logica.abtractas;

import aGeneticos.logica.poblacion.Cromosoma;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de la mutación de los individuos de la población.
 */

public abstract class Mutador {
	public abstract Cromosoma muta(Cromosoma c, double probMutacion);
}
