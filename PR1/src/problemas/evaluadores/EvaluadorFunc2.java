package problemas.evaluadores;

import java.util.logging.Logger;


import logica.alelos.AleloDouble;
import logica.esqueleto.abtractas.Evaluador;
import logica.esqueleto.datos.ConjuntoAlelos;
import logica.esqueleto.datos.Cromosoma;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa el evaluador de la funci�n 2.
 */

public class EvaluadorFunc2 extends Evaluador {
	private Logger log;
	private AleloDouble alelo;
	
		
	public EvaluadorFunc2(ConjuntoAlelos alelos) {		
		super(alelos);
		log=Logger.getLogger("CP");
	}

	public void fitness(Cromosoma c) {
		alelo = (AleloDouble)this.alelos.getAleloN(0);
		log.finest("[EV] Evaluando cromosoma: " + c.toString());
		// Recuperar los valores x e y		
		double x = alelo.getFenotipo(c.getGenotipo(alelo.getPos()),
				alelo.getNumBits());
		alelo = (AleloDouble) this.alelos.getAleloN(1);		
		double y = alelo.getFenotipo(c.getGenotipo(alelo.getPos()),
				alelo.getNumBits());
		/* f(x,y) = 21.5 + x.sen(4pi x)+y.sen(20pi y) */
		double valor = 21.5 + x * Math.sin(4 * Math.PI * x) + y	* Math.sin(20 * Math.PI * y);
		c.setAptitud(valor);
	}
	
	


	
}
