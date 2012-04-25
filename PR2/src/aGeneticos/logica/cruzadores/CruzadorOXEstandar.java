package aGeneticos.logica.cruzadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.CruzadorOX;
import aGeneticos.logica.alumnos.PosById;

public class CruzadorOXEstandar extends CruzadorOX{

	@Override
	protected void intercambio(ArrayList<PosById> hijo1,
			ArrayList<PosById> hijo2) {
		// Intercambiamos la cadena que se encuentra entre los puntos de cruces.
		for (int i = puntoCruce[0]; i < puntoCruce[1]; i++) {
			hijo2.set(i, padre1.getCadena().get(i));
			hijo1.set(i, padre2.getCadena().get(i));
		}
	}

}
