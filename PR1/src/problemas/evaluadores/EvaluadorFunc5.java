package problemas.evaluadores;

import java.util.ArrayList;
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
 * Clase que implementa el evaluador de la función 5.
 */

public class EvaluadorFunc5 extends Evaluador {
	private Logger log;
	private AleloDouble alelo;
	
		
	public EvaluadorFunc5(ConjuntoAlelos alelos) {		
		super(alelos);
		log = Logger.getLogger("CP");
		this.maximizar = false;
	}

	public void fitness(Cromosoma c) {
		log.finest("[EV] Evaluando cromosoma: " + c.toString());
		ArrayList<Double> x = new ArrayList<Double>();
		for (int i = 0; i < alelos.getNumAlelos(); i++){
			alelo = (AleloDouble)alelos.getAleloN(i);
			x.add(alelo.getFenotipo(c.getGenotipo(alelo.getPos()), alelo.getNumBits()));
		}

		double sum = 0;
		for(int i = 0; i < x.size(); i++){
			sum -= Math.sin(x.get(i))*Math.pow(Math.sin((i+2)*Math.pow(x.get(i), 2)/Math.PI), 20);
		}
	
		c.setAptitud(sum);
	}
	
	


	
}
