package problemas;

import problemas.evaluadores.EvaluadorFunc3;
import logica.alelos.AleloDouble;
import logica.esqueleto.algoritmos.abtractas.Funcion;
import logica.esqueleto.datos.ConjuntoAlelos;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa la función 3. Se encarga de crear el conjunto de alelos necesarios para esta función.
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
