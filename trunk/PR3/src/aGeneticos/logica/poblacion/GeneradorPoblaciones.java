package aGeneticos.logica.poblacion;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Evaluador;
import aGeneticos.logica.arbol.Arbol;
import aGeneticos.logica.arbol.Nodo;
import aGeneticos.logica.arbol.Tipo;

/**
 * Práctica 3 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de generar la población inicial 
 */

public class GeneradorPoblaciones {
	
	public GeneradorPoblaciones() {
		
	}
	
	public ArrayList<Cromosoma> generar(int tamPoblacion, int profArbol, int profMinArbol , Evaluador evaluador) {
		ArrayList<Cromosoma> nuevaPoblacion = new ArrayList<Cromosoma>();

		for (int i = 0; i < tamPoblacion; i++){
			// Creamos la raíz de tipo función para que el árbol no tenga un único nodo 
			Nodo<Tipo> nodo = new Nodo<Tipo>();
			Arbol<Tipo> arbol = new Arbol<Tipo>();
			Nodo<Tipo> raiz = arbol.generarRamas(nodo, profArbol, profMinArbol);
			arbol.setRaiz(raiz);

			arbol.calculaProfundidad();
			// TODO evaluar cromosoma
			nuevaPoblacion.add(new Cromosoma(arbol));
		}

		return nuevaPoblacion;
	}
}
