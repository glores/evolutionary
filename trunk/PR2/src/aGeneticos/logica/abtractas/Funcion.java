package aGeneticos.logica.abtractas;


/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa una función genérica para la evaluación de la población
 */

public abstract class Funcion {
	protected Evaluador evaluador;	
	
	
	public Funcion(){
		evaluador = null;
	}
	
	public Evaluador getEvaluador(){
		return evaluador;
	}
		
	
	public abstract void preparar();
}
