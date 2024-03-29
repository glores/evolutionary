package aGeneticos.logica.mutadores;

import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.arbol.Nodo;
import aGeneticos.logica.arbol.Tipo;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

/**
 * Mutaci�n para �rboles
 * @author Gloria Pozuelo
 * @author Sergio Barja
 * 
 * Se selecciona un sub�rbol del individuo, igual que en el operador de recombinaci�n, se elimina totalmente el sub�rbol
 * seleccionado y en su lugar se incorpora un nuevo sub�rbol generado aleatoriamente
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
			int prof = nodo.getProfundidad();
			// Si el nodo est� a profundidad m�xima no puede mutar
			if (prof < profMax){
				nodo.eliminaHijos();
				c.getCadena().generarRamas(nodo, profMax, profMax - prof);
				c.getCadena().calculaProfundidad();
			}
			Cromosoma[] result = new Cromosoma[1];
			result[0] = c;
			return result;
		}
		else return null;

	}

}
