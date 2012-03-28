package logica.esqueleto.abtractas;

import logica.alelos.AleloDouble;
import logica.esqueleto.datos.ConjuntoAlelos;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa una funci�n parametrizada gen�rica para la evaluaci�n de la poblaci�n.
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
