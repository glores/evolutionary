package aGeneticos.logica.abtractas;

import java.util.ArrayList;

import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.alumnos.PosById;
import aGeneticos.logica.poblacion.Cromosoma;

/**
 * Clase que implementa el cruce OX. Consiste en copiar en cada uno de los hijos
 * una subcadena de uno de los padres mientras se mantiene el orden relativo de
 * las ciudades que aparecen en el otro padre.
 * 
 * @author Gloria Pozuelo
 * @author Sergio Barja
 * 
 */
public abstract class CruzadorOX extends Cruzador {
	private static PosById posInvalida = PosById.objetoInvalido();
	protected Cromosoma padre1, padre2;
	protected int[] puntoCruce;

	@Override
	public Cromosoma[] cruza(Cromosoma c1, Cromosoma c2, int[] puntoCruce) {
		padre1 = c1; padre2 = c2; this.puntoCruce = puntoCruce;
		ArrayList<PosById> hijo1 = new ArrayList<PosById>();
		ArrayList<PosById> hijo2 = new ArrayList<PosById>();
		
		inicializa(hijo1, padre1.getCadena().size()); inicializa(hijo2, padre2.getCadena().size());

		intercambio(hijo1, hijo2);

		rellenarHijo(padre1, hijo1);
		rellenarHijo(padre2, hijo2);

		Cromosoma[] hijos = new Cromosoma[2];
		hijos[0] = new  Cromosoma(hijo1, ListaAlumnos.getDesequilibrio(hijo1),
				ListaAlumnos.getIncompatibles(hijo1));
		hijos[1] = new  Cromosoma(hijo2, ListaAlumnos.getDesequilibrio(hijo2),
				ListaAlumnos.getIncompatibles(hijo2));
		// Eliminamos los padres
		c1.delete(); c2.delete();
		return hijos;
	}

	protected abstract void intercambio(ArrayList<PosById> hijo1, ArrayList<PosById> hijo2);

	private void rellenarHijo(Cromosoma padre, ArrayList<PosById> hijo) {
		int i = puntoCruce[1]; int contHijo = puntoCruce[1];
		while (!isCompleto(hijo)) {
			// Si llegamos al final del cromosoma padre volvemos al ppio hasta
			// completar el hijo
			if (i == padre.getCadena().size())
				i = 0;
			if (contHijo == hijo.size()) 
				contHijo = 0;
			if (!hijo.contains(padre.getCadena().get(i))) {
				hijo.set(contHijo, padre.getCadena().get(i));
				contHijo++;
			}
			i++;
		}
	}
	
	private boolean isCompleto(ArrayList<PosById> hijo){
		return !hijo.contains(posInvalida);
	}

}
