package aGeneticos.logica.seleccionadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Seleccionador;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;



/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase encargada de la selecci�n de la poblaci�n que posteriormente pasar�n a reproducirse, mediante el m�todo de ruleta.
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
