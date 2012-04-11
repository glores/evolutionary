package aGeneticos.logica.seleccionadores;

import java.util.ArrayList;
import java.util.Collections;

import aGeneticos.logica.abtractas.Seleccionador;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;
import aGeneticos.util.CromosomaAscendantSort;
import aGeneticos.util.CromosomaDescendantSort;


/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de la selección de la población que posteriormente pasarán a reproducirse, mediante el método de torneo probabilista.
 */

public class TorneoProbabilista extends Seleccionador{
	private int tamTorneo;
	private double prob;
	
	public TorneoProbabilista(int tam, double probabilidad) {
		this.tamTorneo = tam;
		prob = probabilidad;
	}

	@Override
	public ArrayList<Integer> selecciona(ArrayList<Cromosoma> poblacion, int tamPoblacion) {
		ArrayList<Integer> selSupervivencia = new ArrayList<Integer>(tamPoblacion);		
		ArrayList<Cromosoma> selTorneo = new ArrayList<Cromosoma>(tamTorneo);
		Integer[] indices = new Integer[tamTorneo];
		
		while (selSupervivencia.size() < tamPoblacion){
			for (int i = 0;  i < tamTorneo; i++){
				indices[i] = new Integer(Aleatorio.entero(tamPoblacion));
				selTorneo.add(poblacion.get(indices[i]));
			}
			
			// Dependiendo de una probabilidad generada se coge al mayor o al menor del torneo
			double probabilidad = Aleatorio.doble();
			if (probabilidad > prob){
				Collections.sort(selTorneo, new CromosomaDescendantSort());
			}
			else Collections.sort(selTorneo, new CromosomaAscendantSort());
			
			int j = 0; boolean encontrado = false;
			while (!encontrado && j < indices.length){
				// En la posición 0 siempre está el mejor
				encontrado = selTorneo.get(0).equals(poblacion.get(indices[j]));
				j++;
			}
					
			selSupervivencia.add(indices[j-1]);
		}
		
		return selSupervivencia;
	}
}
