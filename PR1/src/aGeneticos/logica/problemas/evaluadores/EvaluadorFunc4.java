package aGeneticos.logica.problemas.evaluadores;

import java.util.logging.Logger;

import aGeneticos.logica.abtractas.Evaluador;
import aGeneticos.logica.alelos.AleloDouble;
import aGeneticos.logica.alelos.ConjuntoAlelos;
import aGeneticos.logica.poblacion.Cromosoma;



/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa el evaluador de la función 4.
 */

public class EvaluadorFunc4 extends Evaluador {
	private Logger log;
	private AleloDouble alelo;
	
		
	public EvaluadorFunc4(ConjuntoAlelos alelos) {		
		super(alelos);
		log=Logger.getLogger("CP");
		this.maximizar=false;
	}

	public void fitness(Cromosoma c) {
		
		log.finest("[EV] Evaluando cromosoma: " + c.toString());
		alelo = (AleloDouble)this.alelos.getAleloN(0);
		// Recuperar los valores x e y		
		double x1 = alelo.getFenotipo(c.getGenotipo(alelo.getPos()),
				alelo.getNumBits());
		alelo = (AleloDouble)this.alelos.getAleloN(1);
		// Recuperar los valores x e y		
		double x2 = alelo.getFenotipo(c.getGenotipo(alelo.getPos()),
				alelo.getNumBits());
		/* f(x) = sen(x)/(1+sqrt(x)+cos(x)/(1+x)*/
		double sum1,sum2;
		sum1=0;
		sum2=0;
		for(int k=1;k<=5;k++){
			sum1+=(k*Math.cos((k+1)*x1+k));
			sum2+=(k*Math.cos((k+1)*x2+k));
		}
		
		double valor = sum1*sum2;
		c.setAptitud(valor);
	}
	
	


	
}
