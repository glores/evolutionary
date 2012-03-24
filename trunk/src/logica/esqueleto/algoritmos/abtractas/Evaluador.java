package logica.esqueleto.algoritmos.abtractas;

import java.util.ArrayList;

import logica.esqueleto.datos.ConjuntoAlelos;
import logica.esqueleto.datos.Cromosoma;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * En la evaluación se revisan los contadores de aptitud relativa y puntuación
 * acumulada de los individuos de la población. Además se calcula la posición
 * del mejor individuo
 */

public abstract class Evaluador {
	/**
	 * Alelos del evaluador. Se construyen en las clases derivadas
	 */
	protected ConjuntoAlelos alelos;
	/**
	 * Indica si se maximiza o minimiza la función de aptitud. 
	 * Se inicializa en la clase derivada
	 */
	protected boolean maximizar = true;
	/**
	 * Mejor local (última evaluación)
	 */
	protected Cromosoma mejorLocal;
	/**
	 * Mejor global (Todas las evaluaciones)
	 */
	protected Cromosoma mejorGlobal;
	/**
	 * Suma de aptitud (local)
	 */
	protected double sumAptitud = 0;
	/**
	 * Puntuación acumulada (local)
	 */
	protected double probAcumulada = 0;
	/**
	 * Media de la población (local)
	 */
	protected double mediaPoblacion = 0;
	/**
	 * Posición del mejor (local)
	 */
	protected int posMejor = -1;
	
	/**
	 * Función de fitness. Se debe implementar en la clase derivada. 
	 * Debe asignar un valor a la aptitud del cromosoma.
	 * @param c
	 */
	public abstract void fitness(Cromosoma c);

	public Evaluador(ConjuntoAlelos al) {
		this.alelos = al;
	}

	/**
	 * Evalua la población y devuelve la posición del mejor Cromosoma
	 * 
	 * @param poblacion
	 *            Población
	 * @return Si se proclama como el cromosoma con mejor aptitud
	 */
	public int evaluar(ArrayList<Cromosoma> poblacion) {
		Cromosoma c;
		inicioEvaluacion();
		for (int i = 0; i < poblacion.size(); i++) {
			c = poblacion.get(i);
			fitness(c);
			double valor = c.getAptitud();
			this.sumAptitud += valor;
			if (maximizar)
				maximizarLocal(c, i);
			else
				minimizarLocal(c, i);
		}
		actualizarDatosGlobales(poblacion);

		return posMejor;
	}


	public double getMedia() {
		return mediaPoblacion;
	}

	public int getPosMejor() {
		return posMejor;
	}

	public ConjuntoAlelos getAlelos() {
		return alelos;
	}
	
	public Cromosoma getMejorLocal(){
		return mejorLocal;		
	}
	
	public Cromosoma getMejorGlobal(){
		return mejorGlobal;
	}

	private void maximizarLocal(Cromosoma c, int i) {
		if (c.getAptitud() > getMejorAptitudLocal()) {
			mejorLocal = (Cromosoma) c.clone();
			posMejor = i;
		}
	}

	private void minimizarLocal(Cromosoma c, int i) {
		if (c.getAptitud() < getMejorAptitudLocal()) {
			mejorLocal = (Cromosoma) c.clone();
			posMejor = i;
		}
	}
	private void maximizarGlobal() {
		if (getMejorAptitudLocal() > getMejorAptitudGlobal()) {
			mejorGlobal =  mejorLocal;

		}
	}

	private void minimizarGlobal() {
		if (getMejorAptitudLocal() < getMejorAptitudGlobal()) {
			mejorGlobal =  mejorLocal;
		}
	}
	
	private double getMejorAptitudLocal() {
		if (mejorLocal != null) {
			return mejorLocal.getAptitud();
		} else {
			if (maximizar) {
				return Double.NEGATIVE_INFINITY;
			} else {
				return Double.POSITIVE_INFINITY;
			}
		}
	}
	private double getMejorAptitudGlobal() {
		if (mejorGlobal != null) {
			return mejorGlobal.getAptitud();
		} else {
			if (maximizar) {
				return Double.NEGATIVE_INFINITY;
			} else {
				return Double.POSITIVE_INFINITY;
			}
		}
	}
	
	private void inicioEvaluacion() {
		mejorLocal=null;
		sumAptitud = 0;
		probAcumulada = 0;
		mediaPoblacion = 0;
		posMejor = -1;
	}

	private void actualizarDatosGlobales(ArrayList<Cromosoma> poblacion) {
		Cromosoma c;
		// Actualizamos las probabilidades y la probabilidad acumulada de cada
		// individuo
		for (int i = 0; i < poblacion.size(); i++) {
			c = poblacion.get(i);
			c.setProbabilidad(c.getAptitud() / sumAptitud);
			c.setProbAcumulada(c.getProbabilidad() + probAcumulada);
			probAcumulada += c.getProbabilidad();
		}
		mediaPoblacion = sumAptitud / poblacion.size();
		if(maximizar){
			maximizarGlobal();
		}else{
			minimizarGlobal();
		}
	}
	


}
