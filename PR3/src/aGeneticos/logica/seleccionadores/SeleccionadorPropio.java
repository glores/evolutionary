package aGeneticos.logica.seleccionadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Seleccionador;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

/**
 * Pr�ctica 2 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 * 
 *         Selecci�n propia. Dados dos par�metros a y b entre 0 y 1, compone un
 *         conjunto de seleccionados de tama�o b*tamPob con los a*tamPob*b
 *         mejores y los (1-n)*tamPob*b peores
 */

public class SeleccionadorPropio extends Seleccionador {
	private double x;
	private boolean maximizar;

	public SeleccionadorPropio(double x, boolean maximizar) {
		this.x = x;
		this.maximizar = maximizar;
	}

	@Override
	public ArrayList<Integer> selecciona(ArrayList<Cromosoma> poblacion,
			int tamPoblacion) {
		//Metemos en un array los mejores
		ArrayList<Integer> mejores = new ArrayList<Integer>();
		for (int i = 0; i < poblacion.size(); i++) {
			if (maximizar) {
				//Mejores son con probabilidad > x
				if (poblacion.get(i).getProbAcumulada() > x) {
					mejores.add(i);
				}
			} else {
				//Mejores son con probabilidad < x
				if (poblacion.get(i).getProbAcumulada() < x) {
					mejores.add(i);
				}
			}
		}		
		//Ahora, con esos repetidos componemos la selecci�n
		ArrayList<Integer> selecc = new ArrayList<Integer>(poblacion.size());
		int aleatorio;
		for(int i=0;i<poblacion.size();i++){
			aleatorio=Aleatorio.entero(mejores.size());
			selecc.add(mejores.get(aleatorio));
		}

		return selecc;
	}

}
