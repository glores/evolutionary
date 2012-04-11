package aGeneticos.logica.problemas.evaluadores;

import java.util.logging.Logger;

import aGeneticos.logica.abtractas.Evaluador;
import aGeneticos.logica.alelos.AleloDouble;
import aGeneticos.logica.alelos.ConjuntoAlelos;
import aGeneticos.logica.poblacion.Cromosoma;



/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa el evaluador de la funci�n 3.
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
