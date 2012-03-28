package problemas;

import logica.esqueleto.abtractas.FuncionParametrizada;
import problemas.evaluadores.EvaluadorFunc5;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa la funci�n 5. Se encarga de crear el conjunto de alelos necesarios para esta funci�n.
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
