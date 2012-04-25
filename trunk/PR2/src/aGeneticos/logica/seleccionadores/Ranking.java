package aGeneticos.logica.seleccionadores;

import java.util.ArrayList;
import java.util.Collections;

import aGeneticos.logica.abtractas.Seleccionador;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.CromosomaDescendantSort;

/**
 * Práctica 2 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 * 
 *         Clase que implementa la selección por ranking. Se basa en el ranking
 *         según la ordenación de los individuos por fitness decreciente. El
 *         valor asignado a cada individuo depende sólo de su posición en el
 *         ranking y no de su valor objetivo.
 */

public class Ranking extends Seleccionador {
	private int tamPoblacion;
	private double beta;

	public Ranking(double b) {
		beta = b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Integer> selecciona(ArrayList<Cromosoma> poblacion,
			int tamPoblacion) {
		this.tamPoblacion = tamPoblacion;
		ArrayList<Cromosoma> poblacionOrdenada = (ArrayList<Cromosoma>) poblacion.clone();
		// Ordenamos la población por fitness decreciente
		Collections.sort(poblacionOrdenada, new CromosomaDescendantSort());

		Cromosoma[] futurosPadres = new Cromosoma[poblacionOrdenada.size()];
		futurosPadres[0] = poblacionOrdenada.get(0);
		futurosPadres[1] = poblacionOrdenada.get(1);
		int numPadres = 2;
		double[] segmentosFitness = rankingPopularidad();
		double segmentoEntero = segmentosFitness[segmentosFitness.length - 1];

		while (numPadres < futurosPadres.length) {
			double x = Math.random() * segmentoEntero;
			if (x <= segmentosFitness[0]) {
				// Se selecciona el primer individuo
				futurosPadres[numPadres] = poblacionOrdenada.get(0);
				numPadres++;
			} else {
				for (int i = 1; i < futurosPadres.length; i++) {
					if (x > segmentosFitness[i - 1] && x <= segmentosFitness[i]) {
						futurosPadres[numPadres] = poblacionOrdenada.get(i);
						numPadres++;
					}
				}
			}
		}
		ArrayList<Integer> selSupervivencia = new ArrayList<Integer>();
		// Buscamos los padres seleccionados para obtener su posición en la población
		for (int i = 0; i < futurosPadres.length; i++){
			selSupervivencia.add(encuentraPos(poblacion, futurosPadres[i]));
		}
		return selSupervivencia;
	}

	private Integer encuentraPos(ArrayList<Cromosoma> poblacion, Cromosoma padre) {
		int i = 0; boolean encontrado = false;
		while (!encontrado && i < poblacion.size()){
			encontrado = poblacion.get(i).equals(padre);
			i++;
		}
		return i-1;
	}

	private double[] rankingPopularidad() {
		double[] segmentosFitness = new double[tamPoblacion];
		double prob;
		for (int i = 0; i < tamPoblacion; i++) {
			prob = (double)i / tamPoblacion;
			prob = prob * 2 * (beta - 1);
			prob = beta - prob;
			prob = prob * ((double)1 / tamPoblacion);
			if (i != 0) {
				segmentosFitness[i] = segmentosFitness[i - 1] + prob;
			} else {
				segmentosFitness[i] = prob;
			}
		}
		return segmentosFitness;
	}

}
