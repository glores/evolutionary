package logica.esqueleto.datos;

import java.util.ArrayList;

import logica.esqueleto.abtractas.Alelo;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de almacenar los alelos de una función concreta.
 */

public class ConjuntoAlelos {
	@SuppressWarnings("rawtypes")
	private ArrayList<Alelo> alelos;
	private int longCromosoma;
	
	@SuppressWarnings("rawtypes")
	public ConjuntoAlelos(){
		alelos = new ArrayList<Alelo>();
		longCromosoma = 0;
	}
	
	@SuppressWarnings("rawtypes")
	public void addAlelo(Alelo alelo){
		alelo.setPos(alelos.size());
		alelos.add(alelo);		
	}
	
	public int getLongitudCromosoma(){
		if (longCromosoma == 0){
			for (int i = 0; i < alelos.size(); i++)
				longCromosoma += alelos.get(i).getNumBits();
		}
		return longCromosoma;
	}
	
	@SuppressWarnings("rawtypes")
	public Alelo getAleloN(int n){
		return alelos.get(n);
	}
	
	public int getNumAlelos(){
		return alelos.size();
	}
	@SuppressWarnings("rawtypes")
	public String imprime(Cromosoma c){
		String cadena="";
		int i=0;
		for(Alelo a:alelos){
			cadena+=a.getNombre()+": "+a.getFenotipo(c.getGenotipo(i), a.getNumBits())+" ";
			i++;
		}
		cadena+=" Aptitud: "+c.getAptitud();
		return cadena;
	}
}
