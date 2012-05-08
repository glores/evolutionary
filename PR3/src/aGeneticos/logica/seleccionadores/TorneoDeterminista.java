package aGeneticos.logica.seleccionadores;

import java.util.ArrayList;
import java.util.Collections;

import aGeneticos.logica.abtractas.Seleccionador;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;
import aGeneticos.util.CromosomaDescendantSort;


/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase encargada de la selecci�n de la poblaci�n que posteriormente pasar�n a reproducirse, mediante el m�todo de torneo.
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
				// En la posici�n 0 siempre est� el mejor
				encontrado = selTorneo.get(0).equals(poblacion.get(indices[j]));
				j++;
			}
					
			selSupervivencia.add(indices[j-1]);
		}
		
		return selSupervivencia;
	}
}
