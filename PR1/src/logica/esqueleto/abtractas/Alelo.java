package logica.esqueleto.abtractas;

import java.util.BitSet;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa un alelo genérico.
 */

public abstract class Alelo<T> {
	/* El evaluador es el único que sabe de qué tipo es */
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
