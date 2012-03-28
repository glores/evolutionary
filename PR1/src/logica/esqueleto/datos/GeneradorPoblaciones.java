package logica.esqueleto.datos;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.logging.Logger;

import logica.esqueleto.abtractas.Evaluador;

import util.Aleatorio;


/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de generar la población inicial. El generador de poblaciones será siempre el mismo, siempre que 
 * los cromosomas sean cadenas de bits.
 */

public class GeneradorPoblaciones {
	private int tamCromosoma; 				/* Tamaño del cromosoma */
	private ArrayList<Integer> tamsGen; 	/* Array con los distintos tamaños de cada gen */
	private ConjuntoAlelos alelos;
	private Logger log;
	
	public GeneradorPoblaciones(ConjuntoAlelos alelos) {
		this.alelos=alelos;
		log=Logger.getLogger("CP");
		tamsGen = new ArrayList<Integer>();
		tamCromosoma = alelos.getLongitudCromosoma();
		Aleatorio.setLongCromosoma(tamCromosoma);
		for (int i = 0; i < alelos.getNumAlelos(); i++){
			tamsGen.add(alelos.getAleloN(i).getNumBits());
		}
		
	}

	public ArrayList<Cromosoma> generar(int tamPoblacion, Evaluador evaluador) {
		ArrayList<Cromosoma> nuevaPoblacion = new ArrayList<Cromosoma>(tamPoblacion);
		for (int j = 0; j < tamPoblacion; j++) {
			BitSet cadena = new BitSet(tamCromosoma);
			for (int i = 0; i < tamCromosoma; i++) {
				if (Aleatorio.bool()) {
					cadena.flip(i);
				}
			}
			Cromosoma individuo = new Cromosoma(cadena, tamCromosoma);			
			individuo.setTamsGen(tamsGen);
			evaluador.evaluar(individuo);
			//TODO: meterle la info de las longitudes de cada gen para ordenarlos
			log.info("Generado: "+alelos.imprime(individuo));
			nuevaPoblacion.add(individuo);
		}
		return nuevaPoblacion;
	}
}
