package aGeneticos.logica.cruzadores;

import java.util.ArrayList;
import java.util.Hashtable;

import aGeneticos.logica.abtractas.Cruzador;
import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.alumnos.PosById;
import aGeneticos.logica.poblacion.Cromosoma;

public class CruzadorPropio extends Cruzador {

	protected Cromosoma padre1, padre2;
	protected int[] puntoCruce;
	Hashtable<PosById, PosById> parejasDe1, parejasDe2;

	@Override
	public Cromosoma[] cruza(Cromosoma c1, Cromosoma c2, int[] puntoCruce) {
		padre1 = c1;
		padre2 = c2;
		this.puntoCruce = puntoCruce;
		//No se me ocurre!!
		return null;
	}
	
}
