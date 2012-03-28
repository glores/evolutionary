package aGeneticos.logica.seleccionadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Seleccionador;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;



/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de la selección de la población que posteriormente pasarán a reproducirse, mediante el método de ruleta.
 */

public class Ruleta extends Seleccionador {

	@Override
	public ArrayList<Integer> selecciona(ArrayList<Cromosoma> poblacion, int tamPoblacion) {
		ArrayList<Integer> selSupervivencia = new ArrayList<Integer>(tamPoblacion);
		int cont;
		double prob;
		
		for (int i = 0; i < poblacion.size(); i++){
			prob = Aleatorio.doble();
			cont = 0;
			
			while (cont < tamPoblacion && prob > poblacion.get(cont).getProbAcumulada()){
				cont++;
			}
			selSupervivencia.add(cont);
		}	
		
		return selSupervivencia;
	}	

}
