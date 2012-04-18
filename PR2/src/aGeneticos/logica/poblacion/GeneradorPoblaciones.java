package aGeneticos.logica.poblacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

import aGeneticos.logica.abtractas.Evaluador;
import aGeneticos.logica.alumnos.Alumno;
import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.alumnos.ListaAlumnos.PosById;


/**
 * Pr�ctica 2 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase encargada de generar la poblaci�n inicial de alumnos
 */

public class GeneradorPoblaciones {
	private Logger log;
	
	public GeneradorPoblaciones() {
		log = Logger.getLogger("CP");		
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Cromosoma> generar(ListaAlumnos lista, Evaluador evaluador) {
		ArrayList<Cromosoma> nuevaPoblacion = new ArrayList<Cromosoma>(lista.getSize());
		for (int j = 0; j < lista.getSize(); j++) {
			// Comprobamos si faltan miembros en alg�n grupo
			int resto = lista.getSize() % lista.getTamGrupo();
			if (resto != 0){
				// A�adimos alumnos dummy hasta que todos los grupos tengan el mismo n�mero de alumnos
				for (int i = 0; i < resto; i++)
					lista.addAlumno(new Alumno(-1, 0.0));
			}
			lista.calcularMedia();
			evaluador.setListaAlumnos(lista);
			ArrayList<PosById> cadena = (ArrayList<PosById>) lista.getPosById().clone();
			// Desordenamos el array
			Collections.shuffle(cadena);
			/* Le metemos al cromosoma el desequilibrio de la colocaci�n de alumnos
			y el n�mero de incompatibilidades */
			Cromosoma individuo = new Cromosoma(cadena, lista.getDesequilibrio(cadena), lista.getIncompatibles(cadena));
			// Calculamos el fitness
			evaluador.evaluar(individuo);
			log.info("Generado: "+ individuo);
			nuevaPoblacion.add(individuo);
		}
		return nuevaPoblacion;
	}
}
