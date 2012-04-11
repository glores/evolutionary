package aGeneticos.logica.seleccionadores;

import java.util.ArrayList;
import java.util.Collections;

import aGeneticos.logica.abtractas.Seleccionador;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;
import aGeneticos.util.CromosomaDescendantSort;


/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de la selección de la población que posteriormente pasarán a reproducirse, mediante el método de torneo.
 */

public class TorneoDeterminista extends Seleccionador{
	private int tamTorneo;
	
	public TorneoDeterminista(int tam) {
		this.tamTorneo = tam;
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
			Collections.sort(selTorneo, new CromosomaDescendantSort());
			
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
