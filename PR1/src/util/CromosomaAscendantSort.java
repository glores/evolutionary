package util;

import java.util.Comparator;

import logica.esqueleto.datos.Cromosoma;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase encargada de la ordenaci�n de los individuos de la poblaci�n de menor a mayor aptitud.
 */

public class CromosomaAscendantSort implements Comparator<Cromosoma> {
	@Override
	public int compare(Cromosoma c1, Cromosoma c2) {
		// Ordenaci�n descendente
        if (c1.getAptitud() < c2.getAptitud())
        	return -1;
        else if (c1.getAptitud() == c2.getAptitud())
        	return 0;
        else if (c1.getAptitud() > c2.getAptitud())
        	return 1;
        
		return 0;		
	}	
}
