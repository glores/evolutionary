package logica.esqueleto.abtractas;

import java.util.BitSet;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa un alelo gen�rico.
 */

public abstract class Alelo<T> {
	/* El evaluador es el �nico que sabe de qu� tipo es */
	protected int pos;
	protected String nombre;
	
	public Alelo(int pos,String nombre){
		this.pos = pos;
		this.nombre=nombre;
	}
	
	public abstract T getFenotipo(BitSet genotipo, int numBits);
	public abstract int getNumBits();
	
	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getPos() {
		return pos;
	}
	public String getNombre(){
		return nombre;
	}
}
