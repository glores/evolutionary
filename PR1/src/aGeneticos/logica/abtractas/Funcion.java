package aGeneticos.logica.abtractas;

import aGeneticos.logica.alelos.ConjuntoAlelos;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa una función genérica para la evaluación de la población
 */

public abstract class Funcion {
	protected ConjuntoAlelos alelos;
	protected Evaluador evaluador;	
	
	
	public Funcion(){
		alelos = null;
		evaluador = null;
	}
	
	
	public ConjuntoAlelos getAlelos(){
		return alelos;
	}
	public Evaluador getEvaluador(){
		return evaluador;
	}
		
	
	public abstract void preparar(double tolerancia);
}
