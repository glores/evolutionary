package aGeneticos.logica.cruzadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Cruzador;
import aGeneticos.logica.alumnos.PosById;
import aGeneticos.logica.poblacion.Cromosoma;

/**
 * Clase que implementa el cruce OX. Consiste en copiar en cada uno de los hijos
 * una subcadena de uno de los padres mientras se mantiene el orden relativo de
 * las ciudades que aparecen en el otro padre.
 * 
 * @author Gloria Pozuelo
 * @author Sergio Barja
 * 
 */
public class CruzadorOX extends Cruzador {

	@SuppressWarnings("unchecked")
	@Override
	public Cromosoma[] cruza(Cromosoma c1, Cromosoma c2, int[] puntoCruce) {
		ArrayList<PosById> hijo1 = (ArrayList<PosById>) c1.getCadena().clone();
		ArrayList<PosById> hijo2 = (ArrayList<PosById>) c1.getCadena().clone();

		// Intercambiamos la cadena que se encuentra entre los puntos de cruces.
		for (int i = puntoCruce[0]; i < puntoCruce[1]; i++) {
			hijo2.add(i, c1.getCadena().get(i));
			hijo1.add(i, c2.getCadena().get(i));
		}

		rellenarHijo(c1, hijo2 , puntoCruce[1]);
		rellenarHijo(c2, hijo1 , puntoCruce[1]);


		return null;
	}

	private void rellenarHijo(Cromosoma padre, ArrayList<PosById> hijo, int ini) {
		int i = ini;
		while (hijo.size() < padre.getCadena().size()) {
			// Si llegamos al final del cromosoma padre volvemos al ppio hasta
			// completar el hijo
			if (i == padre.getCadena().size())
				i = 0;
			if (!hijo.contains(padre.getCadena().get(i))) {
				hijo.add(i, padre.getCadena().get(i));
			}
			i++;
		}
	}

}
