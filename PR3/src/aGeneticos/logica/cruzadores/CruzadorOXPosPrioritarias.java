package aGeneticos.logica.cruzadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.CruzadorOX;
import aGeneticos.logica.alumnos.PosById;
import aGeneticos.util.Aleatorio;

/**
 * Cruce OX por orden con posiciones prioritarias. El único cambio es que en vez
 * de intercambiar el segmento entre los puntos de cruce se intercambian
 * posiciones aleatorias
 * 
 * @author Gloria Pozuelo
 * @author Sergio Barja
 * 
 */
public class CruzadorOXPosPrioritarias extends CruzadorOX {

	@Override
	protected void intercambio(ArrayList<PosById> hijo1,
			ArrayList<PosById> hijo2) {
		/* Generamos un número de posiciones a intercambiar, dejando siempre 1/3
		 de las posiciones sin cambiar y sumándole 1 para que no de 0*/
		int num = Aleatorio.entero(hijo1.size()	- (int) Math.round(hijo1.size() * 0.3) + 1);
		ArrayList<Integer> posiciones = new ArrayList<Integer>();
		int i = 0;
		int n;
		while (i < num) {
			n = Aleatorio.entero(hijo1.size());
			if (!posiciones.contains(n)) {
				posiciones.add(n);
				i++;
			}
		}

		// Intercambiamos las posiciones generadas anteriormente.
		for (int j = 0; j < posiciones.size(); j++) {
			hijo2.set(posiciones.get(j), padre1.getCadena().get(posiciones.get(j)));
			hijo1.set(posiciones.get(j), padre2.getCadena().get(posiciones.get(j)));
		}
	}

}
