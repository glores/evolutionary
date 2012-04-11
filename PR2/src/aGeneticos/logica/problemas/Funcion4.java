package aGeneticos.logica.problemas;

import aGeneticos.logica.abtractas.FuncionParametrizada;
import aGeneticos.logica.problemas.evaluadores.EvaluadorFunc4;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa la funci�n 4. Se encarga de crear el conjunto de alelos necesarios para esta funci�n.
 */

public class Funcion4 extends FuncionParametrizada {
	
	public Funcion4(){
		super();
		// Caso concreto de funci�n parametrizada
		n = 2;
	}
	
	public void preparar(double tolerancia){		
		super.prepararAlelosConN(tolerancia, -10, 10);
		evaluador = new EvaluadorFunc4(alelos);
	}
}
