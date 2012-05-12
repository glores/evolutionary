package aGeneticos.logica.poblacion;

import java.util.ArrayList;
import java.util.logging.Logger;

import aGeneticos.logica.abtractas.Evaluador;

/**
 * Pr�ctica 3 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase encargada de generar la poblaci�n inicial 
 */

public class GeneradorPoblaciones {
	private Logger log;
	
	public GeneradorPoblaciones() {
		log = Logger.getLogger("CP");		
	}
	
	public ArrayList<Cromosoma> generar(int tamPoblacion, Evaluador evaluador) {
		ArrayList<Cromosoma> nuevaPoblacion = new ArrayList<Cromosoma>();
		
		return nuevaPoblacion;
	}
}
