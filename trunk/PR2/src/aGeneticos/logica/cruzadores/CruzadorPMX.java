package aGeneticos.logica.cruzadores;

import java.util.ArrayList;
import java.util.Hashtable;

import aGeneticos.logica.abtractas.Cruzador;
import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.alumnos.PosById;
import aGeneticos.logica.poblacion.Cromosoma;

public class CruzadorPMX extends Cruzador {

	protected Cromosoma padre1, padre2;
	protected int[] puntoCruce;
	Hashtable<PosById, PosById> parejasDe1, parejasDe2;

	@Override
	public Cromosoma[] cruza(Cromosoma c1, Cromosoma c2, int[] puntoCruce) {
		padre1 = c1;
		padre2 = c2;
		this.puntoCruce = puntoCruce;
		parejasDe1 = new Hashtable<PosById, PosById>();
		parejasDe2 = new Hashtable<PosById, PosById>();
		ArrayList<PosById> hijo1 = new ArrayList<PosById>(padre1.getCadena()
				.size());
		ArrayList<PosById> hijo2 = new ArrayList<PosById>(padre1.getCadena()
				.size());
		inicializa(hijo1,padre1.getCadena().size());
		inicializa(hijo2,padre1.getCadena().size());
		
		
		// Se intercambian y se anotan las parejas
		for (int i = puntoCruce[0]; i < puntoCruce[1]; i++) {
			hijo2.set(i, padre1.getCadena().get(i));
			hijo1.set(i, padre2.getCadena().get(i));
			// id de c1 (clave), id de c2 valor
			parejasDe1.put(padre1.getCadena().get(i), padre2.getCadena().get(i));
			parejasDe2.put(padre2.getCadena().get(i), padre1.getCadena().get(i));

		}

		// Por la izquierda y por la derecha del segmento cruzado
		// Izquierda
		resolverConflictos(hijo1, hijo2, 0, puntoCruce[0]);
		// Derecha
		resolverConflictos(hijo1, hijo2, puntoCruce[1], padre1.getCadena()
				.size());

		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = new Cromosoma(hijo1, ListaAlumnos.getDesequilibrio(hijo1),
				ListaAlumnos.getIncompatibles(hijo1));
		hijos[1] = new Cromosoma(hijo2, ListaAlumnos.getDesequilibrio(hijo2),
				ListaAlumnos.getIncompatibles(hijo2));

		// Eliminamos los padres
		c1.delete();
		c2.delete();
		return hijos;
	}
	
	
	private PosById resolver1(int pos, ArrayList<PosById> hijo) {
		PosById actual = padre1.getCadena().get(pos);
		while (parejasDe2.containsKey(actual)) {
			actual = parejasDe2.get(actual);
		}
		return actual;
	}

	private PosById resolver2(int pos, ArrayList<PosById> hijo) {
		PosById actual = padre2.getCadena().get(pos);
		while (parejasDe1.containsKey(actual)) {
			actual = parejasDe1.get(actual);
		}
		return actual;
	}

	private void resolverConflictos(ArrayList<PosById> hijo1,
			ArrayList<PosById> hijo2, int a, int b) {
		// Si c1.getCadena(i).getId no está en la tabla de parejas como clave,
		// se pone en hijo1.
		// Si está, hay que resolverlo.
		// Si c2.getCadena(i).getId no está en la tabla de parejas como valor,
		// se pone en hijo2.
		// Si está, hay que resolverlo.
		for (int i = a; i < b; i++) {
			if (!parejasDe2.containsKey(padre1.getCadena().get(i))) {
				hijo1.set(i, padre1.getCadena().get(i));
			} else {
				hijo1.set(i, resolver1(i, hijo1));
			}
			if (!parejasDe1.containsKey(padre2.getCadena().get(i))) {
				hijo2.set(i, padre2.getCadena().get(i));
			} else {
				hijo2.set(i, resolver2(i, hijo2));
			}
		}
	}
}
