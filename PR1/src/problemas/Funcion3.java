package problemas;

import problemas.evaluadores.EvaluadorFunc3;
import logica.alelos.AleloDouble;
import logica.esqueleto.algoritmos.abtractas.Funcion;
import logica.esqueleto.datos.ConjuntoAlelos;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa la funci�n 3. Se encarga de crear el conjunto de alelos necesarios para esta funci�n.
 */

public class Funcion3 extends Funcion {
	
	public Funcion3(){
		super();		
	}
	
	public void preparar(double tolerancia){		
		alelos=new ConjuntoAlelos();
		//x
		AleloDouble alelo = new AleloDouble(0, 25, tolerancia, 0,"x");		
		alelos.addAlelo(alelo);		
		evaluador=new EvaluadorFunc3(alelos);
	}
}
