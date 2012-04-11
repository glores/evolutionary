package aGeneticos.logica.abtractas;

import java.util.ArrayList;

import aGeneticos.logica.alelos.ConjuntoAlelos;
import aGeneticos.logica.poblacion.Cromosoma;


/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * En la evaluaci�n se revisan los contadores de aptitud relativa y puntuaci�n
 * acumulada de los individuos de la poblaci�n. Adem�s se calcula la posici�n
 * del mejor individuo
 */

public abstract class Evaluador {
	/**
	 * Alelos del evaluador. Se construyen en las clases derivadas
	 */
	protected ConjuntoAlelos alelos;
	/**
	 * Indica si se maximiza o minimiza la funci�n de aptitud. 
	 * Se inicializa en la clase derivada
	 */
	protected boolean maximizar = true;
	/**
	 * Mejor local (�ltima evaluaci�n)
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
	 * Puntuaci�n acumulada (local)
	 */
	protected double probAcumulada = 0;
	/**
	 * Media de la poblaci�n (local)
	 */
	protected double mediaPoblacion = 0;
	
	
	/**
	 * Funci�n de fitness. Se debe implementar en la clase derivada. 
	 * Debe asignar un valor a la aptitud del cromosoma.
	 * @param c
	 */
	public abstract void fitness(Cromosoma c);

	public Evaluador(ConjuntoAlelos al) {
		this.alelos = al;
	}
	
	/**
	 * Evalua el cromosoma dado por par�metro
	 * 
	 * @param cromosoma
	 */
	public void evaluar(Cromosoma c) {
		fitness(c);
	}

	public void inicioEvaluacionLocal() {
		sumAptitud = 0;
		probAcumulada = 0;
		mediaPoblacion = 0;
		mejorLocal = null;
	}

	public void inicioEvaluacionGlobal() {
		mejorGlobal=null;
	}
	
	/**
	 * Evalua la poblaci�n y devuelve la posici�n del mejor Cromosoma
	 * 
	 * @param poblacion
	 *            Poblaci�n
	 * @return Si se proclama como el cromosoma con mejor aptitud
	 */
	public void evaluar(ArrayList<Cromosoma> poblacion) {
		Cromosoma c;
		inicioEvaluacionLocal();
		for (int i = 0; i < poblacion.size(); i++) {
			c = poblacion.get(i);
			double valor = c.getAptitud();
			this.sumAptitud += valor;
			if (maximizar)
				maximizarLocal(c);
			else
				minimizarLocal(c);
		}		
		actualizarDatosGlobales(poblacion);
	}


	public double getMedia() {
		return mediaPoblacion;
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
	
	/**
	 * Devuelve cierto si es una funci�n de maximizaci�n
	 * Necesario para la �lite.
	 * @return
	 */
	public boolean isMaximizar() {
		return maximizar;
	}

	private void maximizarLocal(Cromosoma c) {
		if (c.getAptitud() > getMejorAptitudLocal()) {
			mejorLocal = (Cromosoma) c.clone();
		}
	}

	private void minimizarLocal(Cromosoma c) {
		if (c.getAptitud() < getMejorAptitudLocal()) {
			mejorLocal = (Cromosoma) c.clone();
		}
	}
	private void maximizarGlobal() {
		if (getMejorAptitudLocal() > getMejorAptitudGlobal()) {
			mejorGlobal =  (Cromosoma) mejorLocal.clone();

		}
	}

	private void minimizarGlobal() {
		if (getMejorAptitudLocal() < getMejorAptitudGlobal()) {
			mejorGlobal = (Cromosoma) mejorLocal.clone();
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
