package aGeneticos.logica.abtractas;
import java.util.ArrayList;

import aGeneticos.logica.poblacion.Cromosoma;




/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase encargada de la selecci�n de los individuos que posteriormente pasar�n a reproducirse.
 */

public abstract class Seleccionador {
	
	// Devuelve la posici�n en el array de la poblaci�n
	public abstract ArrayList<Integer> selecciona(ArrayList<Cromosoma> poblacion, int tamPoblacion);
}
