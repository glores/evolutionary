package aGeneticos.logica.abtractas;

import java.util.ArrayList;

import aGeneticos.logica.alumnos.PosById;
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
	public abstract Cromosoma[] muta(Cromosoma c, double probMutacion);
	
	protected void inicializa(ArrayList<PosById> hijo, int tam) {
		// Los rellenamos con datos inválidos
		for (int i = 0; i < tam; i++)
			hijo.add(new PosById(-2, -2));
	}
}
