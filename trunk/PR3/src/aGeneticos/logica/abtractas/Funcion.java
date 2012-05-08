package aGeneticos.logica.abtractas;


/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa una funci�n gen�rica para la evaluaci�n de la poblaci�n
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
