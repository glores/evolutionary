package logica.mutadores;

import util.Aleatorio;
import logica.esqueleto.algoritmos.abtractas.Mutador;
import logica.esqueleto.datos.Cromosoma;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase encargada de la mutaci�n de la poblaci�n, mediante mutaci�n simple.
 */

public class MutadorSimple extends Mutador{

	@Override
	public boolean muta(Cromosoma c, double probMut) {
		double prob;
		boolean mutado = false;
		
		for (int i = 0; i < c.getNumBits(); i++){
			prob = Aleatorio.doble();
			if (prob < probMut){
				c.getCadena().flip(i);
				mutado = true;
			}
		}
		return mutado;
	}

}
