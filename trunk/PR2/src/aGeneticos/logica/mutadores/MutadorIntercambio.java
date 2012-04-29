package aGeneticos.logica.mutadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.alumnos.PosById;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

public class MutadorIntercambio extends Mutador {

	
	@Override
	public Cromosoma[] muta(Cromosoma c, double probMut) {
		double prob;

		Cromosoma[] aux = null;
		prob = Aleatorio.doble();
		if (prob < probMut) {
			aux = mutar(c);

		}
		return aux;

	}

	private Cromosoma[] mutar(Cromosoma c) {		
		ArrayList<PosById> aux = new ArrayList<PosById>(c.getCadena()
				.size());
		inicializa(aux,c.getCadena().size());
		int a,b;
		a=Aleatorio.getPuntoCruce();
		b=Aleatorio.getPuntoCruce();
		while(a==b){
			b=Aleatorio.getPuntoCruce();
		}
		// Se intercambian
		for(int i=0;i<c.getCadena().size();i++){
			aux.set(i, c.getCadena().get(i));
		}
		aux.set(a,c.getCadena().get(b));
		aux.set(b,c.getCadena().get(a));		
		
		Cromosoma[] res = new Cromosoma[1];
		res[0] = new Cromosoma(aux, ListaAlumnos.getDesequilibrio(aux),
				ListaAlumnos.getIncompatibles(aux));
		return res;		
		
	}
	
}
