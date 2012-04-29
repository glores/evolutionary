package aGeneticos.logica.mutadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.alumnos.PosById;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

public class MutadorInsercion extends Mutador {

	
	@Override
	public Cromosoma muta(Cromosoma c, double probMut) {
		double prob;

		Cromosoma aux = null;
		prob = Aleatorio.doble();
		if (prob < probMut) {
			aux = mutar(c);

		}
		return aux;

	}

	private Cromosoma mutar(Cromosoma c) {		
		/*
		ArrayList<PosById> aux = new ArrayList<PosById>(c.getCadena()
				.size());
		inicializa(aux,c.getCadena().size());
		//TODO dejar como parametro el 5 que es el max num de inserciones
		int[] puntos=Aleatorio.getPuntosInsercion(5);
		// Se realizan las inserciones
		for(int i=0;i<puntos.length;i++){
			insertar(aux,puntos[i]);
		}
		aux.set(a,c.getCadena().get(b));
		aux.set(b,c.getCadena().get(a));		
		
		return new Cromosoma(aux, ListaAlumnos.getDesequilibrio(aux),
				ListaAlumnos.getIncompatibles(aux));
		*/
		return null;
		
	}
	
}
