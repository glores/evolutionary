package problemas;

import problemas.evaluadores.EvaluadorFunc2;
import logica.alelos.AleloDouble;
import logica.esqueleto.abtractas.Funcion;
import logica.esqueleto.datos.ConjuntoAlelos;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa la funci�n 2. Se encarga de crear el conjunto de alelos necesarios para esta funci�n.
 */

public class Funcion2 extends Funcion {
	
	public Funcion2(){
		super();		
	}
	
	public void preparar(double tolerancia){		
		alelos=new ConjuntoAlelos();
		//x
		AleloDouble alelo = new AleloDouble(-3.0, 12.1, tolerancia, 0,"x");		
		alelos.addAlelo(alelo);
		//y
		alelo = new AleloDouble(4.1, 5.8, tolerancia, 1,"y");		
		alelos.addAlelo(alelo);
		evaluador=new EvaluadorFunc2(alelos);
	}
}
