package problemas;

import logica.esqueleto.abtractas.FuncionParametrizada;
import problemas.evaluadores.EvaluadorFunc5;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa la función 5. Se encarga de crear el conjunto de alelos necesarios para esta función.
 */

public class Funcion5 extends FuncionParametrizada {
	
	public Funcion5(int n){
		super();	
		this.n = n;
	}
	
	public void preparar(double tolerancia){		
		super.prepararAlelosConN(tolerancia, 0, Math.PI);
		evaluador = new EvaluadorFunc5(alelos);
	}
}
