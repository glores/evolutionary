package aGeneticos.logica.mutadores;

import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 * @deprecated para practica 2
 *
 * Clase encargada de la mutación de la población, mediante mutación simple.
 * 
 */

public class MutadorSimple extends Mutador{

	@Override
	public Cromosoma[] muta(Cromosoma c, double probMut) {
		double prob;
		boolean mutado = false;
		Cromosoma[] aux = new Cromosoma[1];
		aux[0] = (Cromosoma) c.clone();
		
		for (int i = 0; i < c.getNumBits(); i++){
			prob = Aleatorio.doble();
			if (prob < probMut){
				//aux.getCadena().flip(i);
				mutado = true;
			}
		}
		if (mutado) return aux;
		else return null;
	}

}
