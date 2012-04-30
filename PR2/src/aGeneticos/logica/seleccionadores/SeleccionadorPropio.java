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
	private double a;
	private double b;

	public SeleccionadorPropio(double a, double b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public ArrayList<Integer> selecciona(ArrayList<Cromosoma> poblacion,int tamPoblacion) {
		
		//Suponiendo que poblacion est� ordenada de mejores a peores
		int tamSeleccionados=(int) Math.floor(tamPoblacion*b);		
		int tamMejores=(int)Math.floor(tamPoblacion*b*a);		
		ArrayList<Integer> selecc = new ArrayList<Integer>(tamSeleccionados);		
		/*
		for (int i = 0; i < tamMejores; i++) {
			selecc.add(i);
		}
		for(int i=tamMejores;i<tamSeleccionados;i++){
			selecc.add(i);
		}
		*/
		return selecc;
	}

}
