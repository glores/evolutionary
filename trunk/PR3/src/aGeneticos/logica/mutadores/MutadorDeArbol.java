package aGeneticos.logica.mutadores;

import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.arbol.Nodo;
import aGeneticos.logica.arbol.Tipo;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

/**
 * Mutación para árboles
 * @author Gloria Pozuelo
 * @author Sergio Barja
 * 
 * Se selecciona un subárbol del individuo, igual que en el operador de recombinación, se elimina totalmente el subárbol
 * seleccionado y en su lugar se incorpora un nuevo subárbol generado aleatoriamente
 */

public class MutadorDeArbol extends Mutador{
	private int profMax;
	
	public MutadorDeArbol(int prof){
		profMax = prof;
	}

	@Override
	public Cromosoma[] muta(Cromosoma c, double probMutacion) {
		double prob = Aleatorio.doble();
		if (prob < probMutacion){
			Nodo<Tipo> nodo = c.getCadena().getNodoAleatorio();
			nodo.eliminaHijos();
			Nodo<Tipo> subarbol = c.getCadena().generarRamas(nodo, profMax, 2);
			nodo.getPadre().addHijo(subarbol);
		}
		Cromosoma[] result = new Cromosoma[1];
		result[0] = c;
		return result;
	}

}
