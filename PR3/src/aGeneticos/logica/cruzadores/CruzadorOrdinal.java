package aGeneticos.logica.cruzadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Cruzador;
import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.alumnos.PosById;
import aGeneticos.logica.poblacion.Cromosoma;

/**
 * Práctica 2 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 * 
 *         Clase que se encarga de la reproducción de los individuos mediante
 *         cruce simple. Para ello se hace la conversión de la lista de alumnos
 *         a su codificación ordinal.
 */

public class CruzadorOrdinal extends Cruzador {

	private class CodOrdinal {
		/**
		 * Clase para realizar la codificación ordinal de un cromosoma
		 */
		private boolean tapado = false;
		private int tachados = 0;

		private void addTachado() {
			tachados++;
		}

		private void setTapado(boolean b) {
			tapado = b;
		}

		public boolean isTapado() {
			return tapado;
		}

		public int getTachados() {
			return tachados;
		}

		public void reset() {
			tachados = 0;
			tapado = false;
		}

	}

	private CodOrdinal[] lista;

	/**
	 * Sólo utiliza un punto de cruce
	 */
	@Override
	public Cromosoma[] cruza(Cromosoma c1, Cromosoma c2, int[] puntoCruces) {	        
		int puntoCruce = puntoCruces[0];
		lista = new CodOrdinal[c1.getCadena().size()];
		for (int i = 0; i < c1.getCadena().size(); i++) {
			lista[i] = new CodOrdinal();
		}

		int[] cad1 = this.getCondificacionOrdinal(c1);
		int[] cad2 = this.getCondificacionOrdinal(c2);
		int[] aux1 = new int[cad1.length];
		int[] aux2 = new int[cad2.length];

		for (int i = 0; i < puntoCruce; i++) {
			aux1[i] = cad1[i];
			aux2[i] = cad2[i];
		}
		for (int i = puntoCruce; i < c1.getNumBits(); i++) {
			aux1[i] = cad2[i];
			aux2[i] = cad1[i];
		}
        Cromosoma[] nuevos = new Cromosoma[2];
        nuevos[0] = getNuevoCromosoma(c1, aux1);
        nuevos[1] = getNuevoCromosoma(c2, aux2);
		// Eliminamos los padres
		c1.delete(); c2.delete();
        return nuevos;

	}

	private Cromosoma getNuevoCromosoma(Cromosoma c, int[] aux) {
		inicializaListaOrdinal();
		ArrayList<PosById> listaAlumnos = c.getCadena();
		ArrayList<PosById> listaRes = new ArrayList<PosById>();		
		int pos = 0, j, k, cont;
		boolean encontrado, tapado;
		for (int i = 0; i < lista.length; i++) {
			cont = 0;
			pos = 0;
			if (lista[aux[i]].isTapado() || lista[aux[i]].getTachados() > 0) {
				j = 0;
				while (j != aux[i] + 1) {
					tapado = false;
					while (lista[cont + j].isTapado()) {
						cont++;
						tapado = true;
					}
					if (!tapado) {
						j++;
					}
				}
				pos = j + cont - 1;
			} else
				pos = aux[i];
			encontrado = false;
			k = 0;
			while (!encontrado && k < listaAlumnos.size()) {
				encontrado = pos == listaAlumnos.get(k).getPos();
				k++;
			}
			if (encontrado)
				listaRes.add(new PosById(listaAlumnos.get(k - 1).getId(), pos));

			lista[pos].setTapado(true);
			// Desde el que acabamos de tapar hasta el final añadimos un tachado
			// al contador
			for (k = pos + 1; k < lista.length; k++) {
				lista[k].addTachado();
			}
		}		
		return new Cromosoma(listaRes, ListaAlumnos.getDesequilibrio(listaRes),
				ListaAlumnos.getIncompatibles(listaRes));
	}

	private int[] getCondificacionOrdinal(Cromosoma c) {
		inicializaListaOrdinal();
		ArrayList<PosById> listaAlumnos = c.getCadena();

		int pos;
		int[] listaRes = new int[listaAlumnos.size()];
		for (int i = 0; i < lista.length; i++) {
			pos = listaAlumnos.get(i).getPos();
			listaRes[i] = pos - lista[pos].getTachados();
			lista[pos].setTapado(true);
			// Desde el que acabamos de tapar hasta el final añadimos un tachado
			// al contador
			for (int j = pos + 1; j < lista.length; j++) {
				lista[j].addTachado();
			}
		}

		return listaRes;
	}

	private void inicializaListaOrdinal() {
		for (int i = 0; i < lista.length; i++) {
			lista[i].reset();
		}
	}

}
