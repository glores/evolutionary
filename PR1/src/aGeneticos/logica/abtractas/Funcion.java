package aGeneticos.logica.abtractas;

import aGeneticos.logica.alelos.ConjuntoAlelos;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa una funci�n gen�rica para la evaluaci�n de la poblaci�n
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
