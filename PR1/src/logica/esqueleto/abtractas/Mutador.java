package logica.esqueleto.abtractas;

import logica.esqueleto.datos.Cromosoma;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase encargada de la mutaci�n de los individuos de la poblaci�n.
 */

public abstract class Mutador {
	public abstract Cromosoma muta(Cromosoma c, double probMutacion);
}
