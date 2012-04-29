package aGeneticos.logica.mutadores;

import java.util.ArrayList;

import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.alumnos.PosById;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;

public class MutadorHeuristica extends Mutador {

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
		ArrayList<PosById> aux = c.getCadena();

		// Como máximo hasta 5 dígitos
		String[] puntos = Aleatorio.getPuntos(5);
		String[] permutaciones = permuta(puntos);
		// Creamos los individuos a partir de las permutaciones
		Cromosoma[] nuevos = getIndividuos(puntos, permutaciones, aux);

		return nuevos;

	}

	private Cromosoma[] getIndividuos(String[] puntos, String[] permutaciones, ArrayList<PosById> padre) {
		Cromosoma[] resultado = new Cromosoma[permutaciones.length];
		ArrayList<PosById> aux; String[] posPermutacionI;
		int cont; // contador para la cantidad de números que se han insertado del array de permutaciones
		for (int i = 0; i < permutaciones.length; i++){
			aux = new ArrayList<PosById>();
			posPermutacionI = permutaciones[i].split(" ");
			cont = 0;
			for (int j = 0; j < padre.size(); j++){
				// Si la posición que vamos a añadir era una de las permutadas
				if (contiene(puntos, Integer.toString(j))){
					aux.add(padre.get(Integer.parseInt(posPermutacionI[cont])));
					cont++;
				} else{
					aux.add(padre.get(j));
				}
			}
			// Acabado un cromosoma
			resultado[i] = new Cromosoma(aux, ListaAlumnos.getDesequilibrio(aux), ListaAlumnos.getIncompatibles(aux));
		}
		return resultado;
	}
	
	private boolean contiene(String[] array, String elemento) {
		boolean resultado = false;
		int pos = 0;
		while (!resultado && pos < array.length) {
			resultado = array[pos].equals(elemento);
			pos++;
		}
		return resultado;
	}
	
	/*---------------------- Métodos auxiliares para realizar las permutaciones ---------------------------------*/

	/**
	 * Returns a list of permutations.
	 * @param s
	 * @return
	 */
	public String[] permuta(String[] s) { 
		if (s.length == 1)
			return s;
		String[] array = new String[factorial(s.length)];
		ArrayList<String> x = new ArrayList<String>();
		for (int i = 0; i < s.length; i++) {
			String[] y = permuta(remove(s, i));
			for (String z : y) {
				x.add(s[i] + " " + z);
			}
		}
		return (String[]) (x.toArray(array));
	}

	/**
	 * removes a String at the specified index from array s, and returns the result.
	 * @param s
	 * @param i
	 * @return
	 */
	public String[] remove(String[] s, int i) { 
		String[] x = new String[s.length - 1];
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int j = 0; j < s.length; j++) {
			if (j != i)
				arrayList.add(s[j]);
		}
		return (String[]) (arrayList.toArray(x));
	}

	/**
	 * Displays the String array, one element on each line.
	 * @param s
	 */
	public void display(String[] s) {
		for (String x : s) {
			System.out.println(x);
		}
	}

	/**
	 * Finds the factorial of an integer so we can know the size of the permutation array.
	 * @param i
	 * @return
	 */
	public int factorial(int i) {
		int result = 1;
		for (int y = 1; y <= i; y++) {
			result *= y;
		}
		return result;
	}
}
