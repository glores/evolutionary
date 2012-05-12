package aGeneticos.logica.poblacion;

import aGeneticos.logica.arbol.Arbol;
import aGeneticos.logica.arbol.Tipo;

/**
 * Pr�ctica 2 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa un cromosoma para alumnos.
 */

public class Cromosoma {
	private Arbol<Tipo> cadena; 	/* Genotipo */
	private double desequilibrio;
	private int incompatibles;
	private int tama�o; 				/* Tama�o del cromosoma */
	private double aptitud; 			/* Funci�n de aptitud */
	private double probabilidad; 		/* Probabilidad */
	private double probAcumulada; 		/* Probabilidad acumulada */
	
	public Cromosoma(){}

	public Cromosoma(Arbol<Tipo> cadena, double desequilibrio, int incompatibles) {
		this.cadena = cadena;
		this.tama�o = cadena.MAX_INDEX; // N�mero de nodos
		this.desequilibrio = desequilibrio;
		this.incompatibles = incompatibles;
	}


	/*------------------- GETTERS Y SETTERS -------------------------*/
	public int getTama�o() {
		return tama�o;
	}

	public void setTama�o(int tama�o) {
		this.tama�o = tama�o;
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
