package aGeneticos.logica.seleccionadores;

import java.util.ArrayList;
import java.util.Collections;

import aGeneticos.logica.abtractas.Seleccionador;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;
import aGeneticos.util.CromosomaAscendantSort;
import aGeneticos.util.CromosomaDescendantSort;


/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase encargada de la selecci�n de la poblaci�n que posteriormente pasar�n a reproducirse, mediante el m�todo de torneo probabilista.
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
				// En la posici�n 0 siempre est� el mejor
				encontrado = selTorneo.get(0).equals(poblacion.get(indices[j]));
				j++;
			}
					
			selSupervivencia.add(indices[j-1]);
		}
		
		return selSupervivencia;
	}
}
