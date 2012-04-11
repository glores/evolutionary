package aGeneticos.logica.problemas;

import aGeneticos.logica.abtractas.Funcion;
import aGeneticos.logica.alelos.AleloDouble;
import aGeneticos.logica.alelos.ConjuntoAlelos;
import aGeneticos.logica.problemas.evaluadores.EvaluadorFunc1;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa la función 1. Se encarga de crear el conjunto de alelos necesarios para esta función.
 */

public class Funcion1 extends Funcion {
	
	public Funcion1(){
		super();		
	}
	
	public void preparar(double tolerancia){		
		alelos=new ConjuntoAlelos();
		AleloDouble alelo = new AleloDouble(0, 1, tolerancia, 0, "x");
		alelos.addAlelo(alelo);
		evaluador=new EvaluadorFunc1(alelos);
	}
}
