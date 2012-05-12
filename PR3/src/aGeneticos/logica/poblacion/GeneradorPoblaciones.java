package aGeneticos.logica.poblacion;

import java.util.ArrayList;
import java.util.logging.Logger;

import aGeneticos.logica.abtractas.Evaluador;

/**
 * Práctica 3 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de generar la población inicial 
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
