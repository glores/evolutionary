package aGeneticos.logica.mutadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.alumnos.PosById;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

public class MutadorInversion extends Mutador {

	
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
		ArrayList<PosById> aux = new ArrayList<PosById>(c.getCadena()
				.size());
		inicializa(aux,c.getCadena().size());
		int puntosCruce[] = Aleatorio.get2PuntosCruceOrdenados();
		// Se invierten
		int contador=1;
		for (int i = puntosCruce[0]; i < puntosCruce[1]; i++) {
			aux.set(i, c.getCadena().get(puntosCruce[1]-contador));
			contador++;
		}
		//Resto igual
		//Izquierda
		for(int i=0;i<puntosCruce[0];i++){
			aux.set(i,c.getCadena().get(i));
		}
		//Derecha
		for(int i=puntosCruce[1];i<c.getCadena().size();i++){
			aux.set(i,c.getCadena().get(i));
		}
		
		
		
		return new Cromosoma(aux, ListaAlumnos.getDesequilibrio(aux),
				ListaAlumnos.getIncompatibles(aux));
		
		
	}
	
}
