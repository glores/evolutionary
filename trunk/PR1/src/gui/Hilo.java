package gui;

import logica.AGenetico;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa el hilo en el que se ejecuta la GUI
 */

public class Hilo implements Runnable {	
	private GUI frame;
	private AGenetico algoritmo;
	
	public Hilo (AGenetico algoritmo){
		super();
		this.algoritmo=algoritmo;
	}
	public void run(){
		run(algoritmo);
	}
	public void run(AGenetico algoritmo) {
		try {
			frame = new GUI();
			algoritmo.addObserver(frame);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public GUI getFrame(){
		return frame;
	}
}