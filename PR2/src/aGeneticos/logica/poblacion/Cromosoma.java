package aGeneticos.logica.poblacion;

import java.util.ArrayList;
import java.util.List;

import aGeneticos.logica.alumnos.PosById;

/**
 * Práctica 2 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa un cromosoma para alumnos.
 */

public class Cromosoma {
	private ArrayList<PosById> cadena; 	/* Genotipo */
	private double desequilibrio;
	private int incompatibles;
	private int numAlumnos; 			/* Tamaño del cromosoma */
	private double aptitud; 			/* Función de aptitud */
	private double probabilidad; 		/* Probabilidad */
	private double probAcumulada; 		/* Probabilidad acumulada */

	public Cromosoma(ArrayList<PosById> cadena, double desequilibrio, int incompatibles) {
		this.cadena = cadena;
		numAlumnos = cadena.size();
		this.desequilibrio = desequilibrio;
		this.incompatibles = incompatibles;
	}

	public Cromosoma(int num) {
		this.numAlumnos = num;

	}

	public List<PosById> getBits(int ini, int fin) {
		return cadena.subList(ini, fin);
	}

	/*------------------- GETTERS Y SETTERS -------------------------*/
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

	public ArrayList<PosById> getCadena() {
		return cadena;
	}

	public void setCadena(ArrayList<PosById> cadena) {
		this.cadena = cadena;
	}

	public int getNumBits() {
		return numAlumnos;
	}

	public void setNumBits(int numBits) {
		this.numAlumnos = numBits;
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

//	public BitSet getGenotipo(int gen) {
//		int desplazamiento = 0;
//
//		for (int j = 0; j < gen; j++) {
//			desplazamiento += tamsGen.get(j);
//		}
//		return cadena.get(desplazamiento, tamsGen.get(gen) + desplazamiento);
//	}
	
	public void delete(){
		this.cadena.clear();
	}

	@Override
	public String toString() {
		String aux = "";
		/*
		for (int i = 0; i < numAlumnos; i++)
			aux = cadena.get(i).toString();
*/
		aux=cadena.toString();
		return "\n\n Cromosoma [cadena = " + aux + ",\n  aptitud = " + aptitud
				+ ",\n  probabilidad = " + probabilidad + ",\n  probAcumulada = "
				+ probAcumulada + "]\n";
	}

	@SuppressWarnings("unchecked")
	public Object clone() {
		Cromosoma clon = new Cromosoma(numAlumnos);
		clon.aptitud = aptitud;
		clon.cadena = (ArrayList<PosById>) cadena.clone();
		clon.numAlumnos = numAlumnos;
		clon.probabilidad = probabilidad;
		clon.probAcumulada = probAcumulada;
		return clon;
	}
}
