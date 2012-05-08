package aGeneticos.logica.mutadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.alumnos.PosById;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

public class MutadorInsercion extends Mutador {

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

		ArrayList<PosById> aux = new ArrayList<PosById>(c.getCadena().size());
		inicializa(aux, c.getCadena().size());
		// TODO dejar como parametro el 5 que es el max num de inserciones
		int[] puntos = Aleatorio.getXPuntosInsercion(5);
		PosById[] elementos = Aleatorio.getNElementosInsercion(puntos.length,
				c.getCadena());
		
		//Primero pongo los que se insertan
		//EJ: A en 3, B en 6
		//___A__B_
		//01234567
		for(int i=0;i<puntos.length;i++){
			aux.set(puntos[i], elementos[i]);
		}
		int iAux=0;
		int iElems=0;
		boolean omitir;
		//Relleno el nuevo con los del original (si no están en la lista de elementos)
		//EJ: Original: 
		//ABCDEFGH
		//01234567
		//Hijo:
		//CDEAFGBH
		//01234567
		while(iAux<aux.size()){
			omitir=false;
			if(contiene(elementos,c.getCadena().get(iElems))){				
				omitir=true;
				iElems++;
			}
			if(!aux.get(iAux).isInvalido()){				
				omitir=true;
				iAux++;	
			}
			if(!omitir){
				aux.set(iAux, c.getCadena().get(iElems));
				iElems++;
				iAux++;				
			}
			
		}		

		Cromosoma[] res = new Cromosoma[1];
		res[0] = new Cromosoma(aux, ListaAlumnos.getDesequilibrio(aux),
				ListaAlumnos.getIncompatibles(aux));
		return res;	

	}

	private boolean contiene(PosById[] listado, PosById elemento) {
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < listado.length) {
			encontrado = listado[i].equals(elemento);
			i++;
		}
		return encontrado;
	}

}
