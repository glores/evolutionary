package aGeneticos.logica.poblacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

import aGeneticos.logica.abtractas.Evaluador;
import aGeneticos.logica.alumnos.Alumno;
import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.alumnos.ListaAlumnos.PosById;


/**
 * Práctica 2 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase encargada de generar la población inicial de alumnos
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
			// Comprobamos si faltan miembros en algún grupo
			int resto = lista.getSize() % lista.getTamGrupo();
			if (resto != 0){
				// Añadimos alumnos dummy hasta que todos los grupos tengan el mismo número de alumnos
				for (int i = 0; i < resto; i++)
					lista.addAlumno(new Alumno(-1, 0.0));
			}
			lista.calcularMedia();
			evaluador.setListaAlumnos(lista);
			ArrayList<PosById> cadena = (ArrayList<PosById>) lista.getPosById().clone();
			// Desordenamos el array
			Collections.shuffle(cadena);
			/* Le metemos al cromosoma el desequilibrio de la colocación de alumnos
			y el número de incompatibilidades */
			Cromosoma individuo = new Cromosoma(cadena, lista.getDesequilibrio(cadena), lista.getIncompatibles(cadena));
			// Calculamos el fitness
			evaluador.evaluar(individuo);
			log.info("Generado: "+ individuo);
			nuevaPoblacion.add(individuo);
		}
		return nuevaPoblacion;
	}
}
