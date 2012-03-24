package problemas.evaluadores;

import java.util.logging.Logger;


import logica.alelos.AleloDouble;
import logica.esqueleto.algoritmos.abtractas.Evaluador;
import logica.esqueleto.datos.ConjuntoAlelos;
import logica.esqueleto.datos.Cromosoma;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa el evaluador de la función 3.
 */

public class EvaluadorFunc3 extends Evaluador {
	private Logger log;
	private AleloDouble alelo;
	
		
	public EvaluadorFunc3(ConjuntoAlelos alelos) {		
		super(alelos);
		log=Logger.getLogger("CP");
		this.maximizar=false;
	}

	public void fitness(Cromosoma c) {
		alelo = (AleloDouble)this.alelos.getAleloN(0);
		log.finest("[EV] Evaluando cromosoma: " + c.toString());
		// Recuperar los valores x e y		
		double x = alelo.getFenotipo(c.getGenotipo(alelo.getPos()),
				alelo.getNumBits());
		
		/* f(x) = sen(x)/(1+sqrt(x)+cos(x)/(1+x)*/
		double valor = Math.sin(x)/(1+Math.sqrt(x)+(Math.cos(x)/(1+x)));
		c.setAptitud(valor);
	}
	
	


	
}
