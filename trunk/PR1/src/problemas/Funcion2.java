package problemas;

import problemas.evaluadores.EvaluadorFunc2;
import logica.alelos.AleloDouble;
import logica.esqueleto.abtractas.Funcion;
import logica.esqueleto.datos.ConjuntoAlelos;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa la función 2. Se encarga de crear el conjunto de alelos necesarios para esta función.
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
