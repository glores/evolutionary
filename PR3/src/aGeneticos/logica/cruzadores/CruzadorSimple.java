package aGeneticos.logica.cruzadores;

import java.util.logging.Logger;

import aGeneticos.logica.abtractas.Cruzador;
import aGeneticos.logica.arbol.Nodo;
import aGeneticos.logica.arbol.Tipo;
import aGeneticos.logica.poblacion.Cromosoma;

/**
 * Método de cruce simple para árboles
 * @author Gloria Pozuelo
 * @author Sergio Barja
 *
 */

public class CruzadorSimple extends Cruzador{

	@Override
	public Cromosoma[] cruza(Cromosoma c1, Cromosoma c2, int[] puntoCruce) {
		Nodo<Tipo> nodo1 = c1.getCadena().busca(puntoCruce[0]).clone();
		Nodo<Tipo> nodo2 = c2.getCadena().busca(puntoCruce[1]).clone();
		
		c1.getCadena().setNodo(nodo2, puntoCruce[0]);		
		c2.getCadena().setNodo(nodo1, puntoCruce[1]);	
		c1.getCadena().calculaProfundidad();
		Logger.getLogger("CP").info("--- Cruzados ----");
		Logger.getLogger("CP").info(c1.getCadena().toString());
		c2.getCadena().calculaProfundidad();		
		Logger.getLogger("CP").info(c2.getCadena().toString());
		
		Cromosoma cruzados[] = new Cromosoma[2];
		cruzados[0] = c1;
		cruzados[1] = c2;
		
		return cruzados;
	}

}
