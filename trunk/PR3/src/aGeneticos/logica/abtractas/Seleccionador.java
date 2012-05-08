package aGeneticos.logica.abtractas;
import java.util.ArrayList;

import aGeneticos.logica.poblacion.Cromosoma;




/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de la selección de los individuos que posteriormente pasarán a reproducirse.
 */

public abstract class Seleccionador {
	
	// Devuelve la posición en el array de la población
	public abstract ArrayList<Integer> selecciona(ArrayList<Cromosoma> poblacion, int tamPoblacion);
}
