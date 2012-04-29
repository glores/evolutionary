package aGeneticos.logica.abtractas;

import java.util.ArrayList;

import aGeneticos.logica.alumnos.PosById;
import aGeneticos.logica.poblacion.Cromosoma;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase encargada de la mutaci�n de los individuos de la poblaci�n.
 */

public abstract class Mutador {
	public abstract Cromosoma[] muta(Cromosoma c, double probMutacion);
	
	protected void inicializa(ArrayList<PosById> hijo, int tam) {
		// Los rellenamos con datos inv�lidos
		for (int i = 0; i < tam; i++)
			hijo.add(new PosById(-2, -2));
	}
}
