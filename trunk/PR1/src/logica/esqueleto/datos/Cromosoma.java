package logica.esqueleto.datos;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa un cromosoma.
 */

public class Cromosoma {
	private BitSet cadena; 				/* Genotipo */
	private int numBits; 				/* Tamaño del cromosoma */
	private double aptitud; 			/* Función de aptitud */
	private double probabilidad; 		/* Probabilidad */
	private double probAcumulada; 		/* Probabilidad acumulada */
	private ArrayList<Integer> tamsGen; /* Array con los distintos tamaños de cada gen */

	public Cromosoma(BitSet cad, int bits) {
		cadena = cad;
		numBits = bits;
	}

	public Cromosoma(int num) {
		this.numBits = num;

	}

	public BitSet getBits(int ini, int fin) {
		return cadena.get(ini, fin);
	}

	/*------------------- GETTERS Y SETTERS -------------------------*/
	public double getProbabilidad() {
		return probabilidad;
	}

	public void setProbabilidad(double prob) {
		this.probabilidad = prob;
	}

	public BitSet getCadena() {
		return cadena;
	}

	public void setCadena(BitSet cadena) {
		this.cadena = cadena;
	}

	public int getNumBits() {
		return numBits;
	}

	public void setNumBits(int numBits) {
		this.numBits = numBits;
	}

	/**
	 * Para reemplazar un cromosoma por otro
	 * 
	 * @param sustituto
	 */
	public void setTodo(Cromosoma sustituto) {
		this.setAptitud(sustituto.getAptitud());
		this.setNumBits(sustituto.getNumBits());
		this.setTamsGen(sustituto.getTamsGen());
		this.setProbabilidad(sustituto.getProbabilidad());
		this.setProbAcumulada(sustituto.getProbAcumulada());
	}

	public ArrayList<Integer> getTamsGen() {
		return tamsGen;
	}

	public void setTamsGen(ArrayList<Integer> tamsGen) {
		this.tamsGen = tamsGen;
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

	public BitSet getGenotipo(int gen) {
		int desplazamiento = 0;

		for (int j = 0; j < gen; j++) {
			desplazamiento += tamsGen.get(j);
		}
		return cadena.get(desplazamiento, tamsGen.get(gen) + desplazamiento);
	}

	@Override
	public String toString() {
		String aux = "";
		for (int i = 0; i < numBits; i++)
			if (cadena.get(i))
				aux += "1";
			else
				aux += "0";

		return "\n\n Cromosoma [cadena = " + aux + ",\n  aptitud = " + aptitud
				+ ",\n  probabilidad = " + probabilidad + ",\n  probAcumulada = "
				+ probAcumulada + "]\n";
	}

	public Object clone() {
		Cromosoma clon = new Cromosoma(numBits);
		clon.aptitud = aptitud;
		clon.cadena = (BitSet) cadena.clone();
		clon.numBits = numBits;
		clon.probabilidad = probabilidad;
		clon.probAcumulada = probAcumulada;
		clon.tamsGen = new ArrayList<Integer>(tamsGen);
		return clon;
	}
}
