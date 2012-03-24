package logica.mutadores;

import util.Aleatorio;
import logica.esqueleto.algoritmos.abtractas.Mutador;
import logica.esqueleto.datos.Cromosoma;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de la mutación de la población, mediante mutación simple.
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
