package logica.esqueleto.abtractas;

import logica.alelos.AleloDouble;
import logica.esqueleto.datos.ConjuntoAlelos;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa una función parametrizada genérica para la evaluación de la población.
 */

public abstract class FuncionParametrizada extends Funcion {
	protected int n;
	
	public void prepararAlelosConN(double tolerancia, double min, double max){		
		alelos=new ConjuntoAlelos();

		AleloDouble alelo = null;
		for (int i = 0; i < n; i++){
			// Para cada n creamos un alelo double
			alelo = new AleloDouble(min, max, tolerancia, i, "x" + Integer.toString(i));
			alelos.addAlelo(alelo);
		}
	}
}
