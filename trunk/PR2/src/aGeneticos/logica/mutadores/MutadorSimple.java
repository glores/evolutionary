package aGeneticos.logica.mutadores;

import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 * @deprecated para practica 2
 *
 * Clase encargada de la mutaci�n de la poblaci�n, mediante mutaci�n simple.
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
