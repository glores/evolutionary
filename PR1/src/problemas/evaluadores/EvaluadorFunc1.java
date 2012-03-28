package problemas.evaluadores;

import java.util.logging.Logger;


import logica.alelos.AleloDouble;
import logica.esqueleto.abtractas.Evaluador;
import logica.esqueleto.datos.ConjuntoAlelos;
import logica.esqueleto.datos.Cromosoma;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa el evaluador de la función 1.
 */

public class EvaluadorFunc1 extends Evaluador {
	private Logger log;
	private AleloDouble alelo;
	
		
	public EvaluadorFunc1(ConjuntoAlelos alelos) {		
		super(alelos);
		log=Logger.getLogger("CP");
	}

	public void fitness(Cromosoma c){
		alelo = (AleloDouble)this.alelos.getAleloN(0);
		log.finest("[EV] Evaluando cromosoma: " + c.toString());
		/* f(x) = x + |sen(32pix)| : x €[0,1] */		
		double x = alelo.getFenotipo(c.getGenotipo(alelo.getPos()), c.getNumBits());
		double valor = x + Math.abs(Math.sin(32*Math.PI*x));
		c.setAptitud(valor);
	}
	
	
}
