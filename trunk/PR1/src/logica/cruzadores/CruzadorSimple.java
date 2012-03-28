package logica.cruzadores;

import java.util.BitSet;

import logica.esqueleto.abtractas.Cruzador;
import logica.esqueleto.datos.Cromosoma;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que se encarga de la reproducci�n de los individuos mediante cruce simple.
 */

public class CruzadorSimple extends Cruzador{

	@Override
	public Cromosoma[] cruza(Cromosoma c1, Cromosoma c2, int puntoCruce) {
		BitSet cad1,cad2;
		cad1 = new BitSet(c1.getNumBits());
		cad2 = new BitSet(c1.getNumBits());
		
		for (int i = 0; i < puntoCruce; i++){
			if (c1.getCadena().get(i))
				cad1.set(i);
			if (c2.getCadena().get(i))
				cad2.set(i);
		}
		for (int i = puntoCruce; i < c1.getNumBits(); i++){
			if (c2.getCadena().get(i))
				cad1.set(i);
			if (c1.getCadena().get(i))
				cad2.set(i);
		}

		Cromosoma[] nuevos = new Cromosoma[2];
		nuevos[0] = new Cromosoma(cad1, c1.getNumBits());
		nuevos[0].setTodo(c1);
		nuevos[1] = new Cromosoma(cad2, c1.getNumBits());
		nuevos[1].setTodo(c2);
		return nuevos;
	}


}
