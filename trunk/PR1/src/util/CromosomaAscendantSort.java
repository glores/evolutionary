package util;

import java.util.Comparator;

import logica.esqueleto.datos.Cromosoma;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de la ordenación de los individuos de la población de menor a mayor aptitud.
 */

public class CromosomaAscendantSort implements Comparator<Cromosoma> {
	@Override
	public int compare(Cromosoma c1, Cromosoma c2) {
		// Ordenación descendente
        if (c1.getAptitud() < c2.getAptitud())
        	return -1;
        else if (c1.getAptitud() == c2.getAptitud())
        	return 0;
        else if (c1.getAptitud() > c2.getAptitud())
        	return 1;
        
		return 0;		
	}	
}
