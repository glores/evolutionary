package aGeneticos.logica.mutadores;

import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.arbol.Tipo;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

/**
 * Mutación para árboles
 * @author Gloria Pozuelo
 * @author Sergio Barja
 * 
 * Se selecciona al azar un símbolo terminal dentro del individuo y se sustituye por otro diferente
 */

public class MutadorTerminalSimple extends Mutador{

	@Override
	public Cromosoma[] muta(Cromosoma c, double probMutacion) {
		double prob = Aleatorio.doble();
		if (prob < probMutacion){
			int num = Aleatorio.entero(3);
			Tipo nuevoTerminal = Tipo.values()[num];
			c.getCadena().getTerminalAleatorio().setDato(nuevoTerminal);
			Cromosoma[] result = new Cromosoma[1];
			result[0] = c;
			return result;
		}
		else return null;
	}

}
