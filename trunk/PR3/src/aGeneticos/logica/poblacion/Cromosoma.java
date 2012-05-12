package aGeneticos.logica.poblacion;

import aGeneticos.logica.arbol.Arbol;
import aGeneticos.logica.arbol.Tipo;

/**
 * Práctica 2 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa un cromosoma para alumnos.
 */

public class Cromosoma {
	private Arbol<Tipo> cadena; 	/* Genotipo */
	private double desequilibrio;
	private int incompatibles;
	private int tamaño; 				/* Tamaño del cromosoma */
	private double aptitud; 			/* Función de aptitud */
	private double probabilidad; 		/* Probabilidad */
	private double probAcumulada; 		/* Probabilidad acumulada */
	
	public Cromosoma(){}

	public Cromosoma(Arbol<Tipo> cadena, double desequilibrio, int incompatibles) {
		this.cadena = cadena;
		this.tamaño = cadena.MAX_INDEX; // Número de nodos
		this.desequilibrio = desequilibrio;
		this.incompatibles = incompatibles;
	}


	/*------------------- GETTERS Y SETTERS -------------------------*/
	public int getTamaño() {
		return tamaño;
	}

	public void setTamaño(int tamaño) {
		this.tamaño = tamaño;
	}
	
	public double getDesequilibrio() {
		return desequilibrio;
	}
	
	public int getIncompatibles(){
		return incompatibles;
	}
	
	public double getProbabilidad() {
		return probabilidad;
	}

	public void setProbabilidad(double prob) {
		this.probabilidad = prob;
	}

	public Arbol<Tipo> getCadena() {
		return cadena;
	}

	public void setCadena(Arbol<Tipo> cadena) {
		this.cadena = cadena;
	}

	public double getAptitud() {
		return aptitud;
	}

	public void setAptitud(double aptitud) {
		this.aptitud = aptitud;
	}

	public double getProbAcumulada() {
		return probAcumulada;
	}

	public void setProbAcumulada(double probAcumulada) {
		this.probAcumulada = probAcumulada;
	}

	@Override
	public String toString() {
		String aux = "";
		aux = cadena.toString();
		return "\n\n Cromosoma [cadena = " + aux + ",\n  aptitud = " + aptitud
				+ ",\n  probabilidad = " + probabilidad + ",\n  probAcumulada = "
				+ probAcumulada + "]\n";
	}

	public Cromosoma clone() {
		Cromosoma clon = new Cromosoma();
		clon.aptitud = aptitud;
		clon.cadena = (Arbol<Tipo>) cadena.clone();
		clon.probabilidad = probabilidad;
		clon.probAcumulada = probAcumulada;
		return clon;
	}
}
