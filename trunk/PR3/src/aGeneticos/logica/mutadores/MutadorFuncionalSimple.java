package aGeneticos.logica.mutadores;

import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.arbol.Funciones;
import aGeneticos.logica.arbol.Nodo;
import aGeneticos.logica.arbol.Tipo;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

/**
 * Mutación para árboles
 * 
 * @author Gloria Pozuelo
 * @author Sergio Barja
 * 
 *         Se selecciona al azar una función dentro del individuo y se sustituye
 *         por otra diferente del conjunto de funciones posibles con el mismo
 *         número de operandos
 */

public class MutadorFuncionalSimple extends Mutador {

	@Override
	public Cromosoma[] muta(Cromosoma c, double probMutacion) {
		double prob = Aleatorio.doble();
		// Si el árbol sólo tiene un nodo será terminal, por lo que no puede mutar
		if (prob < probMutacion && c.getCadena().getNumeroNodos() > 1) {
			Nodo<Tipo> nodo = c.getCadena().getFuncionAleatoria();
			Tipo tipo = Funciones.getCompatible(nodo.getDato());
			nodo.setDato(tipo);
			Cromosoma[] result = new Cromosoma[1];
			result[0] = c;
			return result;

		}
		else return null;
	}

}
