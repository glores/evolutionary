package aGeneticos.logica.problemas;

import aGeneticos.logica.abtractas.FuncionParametrizada;
import aGeneticos.logica.problemas.evaluadores.EvaluadorFunc4;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa la función 4. Se encarga de crear el conjunto de alelos necesarios para esta función.
 */

public class Funcion4 extends FuncionParametrizada {
	
	public Funcion4(){
		super();
		// Caso concreto de función parametrizada
		n = 2;
	}
	
	public void preparar(double tolerancia){		
		super.prepararAlelosConN(tolerancia, -10, 10);
		evaluador = new EvaluadorFunc4(alelos);
	}
}
