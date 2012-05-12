package aGeneticos.logica.poblacion;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Evaluador;
import aGeneticos.logica.arbol.Arbol;
import aGeneticos.logica.arbol.Funciones;
import aGeneticos.logica.arbol.Nodo;
import aGeneticos.logica.arbol.Tipo;
import aGeneticos.util.Aleatorio;

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
	
	public ArrayList<Cromosoma> generar(int tamPoblacion, int profArbol , Evaluador evaluador) {
		ArrayList<Cromosoma> nuevaPoblacion = new ArrayList<Cromosoma>();

		for (int i = 0; i < tamPoblacion; i++){
			// Creamos la raíz de tipo función para que el árbol no tenga un único nodo 
			Nodo<Tipo> nodo = new Nodo<Tipo>();
			Nodo<Tipo> raiz = generarRamas(nodo, profArbol, 2);
			Arbol<Tipo> arbol = new Arbol<Tipo>(raiz);
			arbol.calculaProfundidad();
			// TODO evaluar cromosoma
			nuevaPoblacion.add(new Cromosoma(arbol));
		}

		return nuevaPoblacion;
	}

	private Nodo<Tipo> generarRamas(Nodo<Tipo> nodo, int profMax, int profMin) {
		Nodo<Tipo> nuevo = null; Tipo tipo;
		if (profMin > 0){ //no puede ser hoja
			// Random entre 3 funciones + 3 por la situación de los terminales
			tipo = Tipo.values()[(Aleatorio.entero(3) + 3)];
			nodo.setDato(tipo);
			// Comprobamos la aridad del nodo
			if (Funciones.isFuncion(nodo.getDato())){
				int aridad = Funciones.getAridad(nodo.getDato());
				
				// Generamos los hijos del nodo
				for (int i = 0; i < aridad; i++){
					nuevo = new Nodo<Tipo>();
					nodo.addHijo(generarRamas(nuevo, profMax-1, profMin-1));
				}
			}
		}
		// ProfMin = 0
		if (profMax == 0){ // Sólo puede ser hoja
			// Terminal
			tipo = Tipo.values()[Aleatorio.entero(3)];
			nodo.setDato(tipo);
		}
		else{
			// se decide aleatoriamente terminal o función
			tipo = Tipo.values()[Aleatorio.entero(Tipo.values().length)];
			nodo.setDato(tipo);
			if (Funciones.isFuncion(tipo)){
				int aridad = Funciones.getAridad(nodo.getDato());
				
				// Generamos los hijos del nodo
				for (int i = 0; i < aridad; i++){
					nuevo = new Nodo<Tipo>();
					nodo.addHijo(generarRamas(nuevo, profMax-1, profMin-1));
				}
			}
		}
		return nodo;
	}
}
