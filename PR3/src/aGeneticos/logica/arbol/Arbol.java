package aGeneticos.logica.arbol;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import aGeneticos.util.Aleatorio;

public class Arbol<T> {
	private int profundidad;
	public int MAX_INDEX = 1; /*
							 * Se utiliza para obtener un nodo aleatorio del
							 * árbol
							 */

	private Nodo<T> raiz;
	private List<Nodo<T>> terminales;

	public Arbol(Nodo<T> raiz) {
		super();
		profundidad = 0;
		this.raiz = raiz;
		terminales = new ArrayList<Nodo<T>>();
	}

	public Arbol() {
		super();
		profundidad = 0;
		terminales = new ArrayList<Nodo<T>>();
	}
	
	public List<Nodo<T>> getTerminales() {
		return terminales;
	}

	public void setTerminales(List<Nodo<T>> terminales) {
		this.terminales = terminales;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public Nodo<T> getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo<T> raiz) {
		this.raiz = raiz;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public int getNumeroNodos() {
		int num = 0;

		if (raiz != null) {
			num = getNumeroNodosAux(raiz) + 1; // 1 para la raíz
		}

		return num;
	}

	private int getNumeroNodosAux(Nodo<T> nodo) {
		int num = nodo.getNumeroHijos();

		for (Nodo<T> hijo : nodo.getHijos()) {
			num += getNumeroNodosAux(hijo);
		}

		return num;
	}

	public boolean isVacio() {
		return (raiz == null);
	}

	/**
	 * Calcula la profundidad del árbol y además establece los índices únicos
	 */
	public void calculaProfundidad() {
		// Se reinicia para recalcular la profundidad
		MAX_INDEX = 1;
		// Se reinicia para calcular los terminales
		terminales.clear();
		raiz.setIndice(MAX_INDEX);
		MAX_INDEX++;
		this.calculaProfundidad(raiz, 0);
	}

	private void calculaProfundidad(Nodo<T> nodo, int profundidad) {
		if (nodo.getNumeroHijos() == 0) {
			// Si no tiene hijos es terminal
			terminales.add(nodo);
		} else {
			for (int i = 0; i < nodo.getNumeroHijos(); i++) {
				nodo.getHijoAt(i).setIndice(MAX_INDEX);
				nodo.getHijoAt(i).setProfundidad(profundidad + 1);
				MAX_INDEX++;
				calculaProfundidad(nodo.getHijoAt(i), profundidad + 1);
			}
		}

		if (this.profundidad < profundidad) {
			this.profundidad = profundidad;
		}
	}

	public String toString() {
		String ini = "";
		String cadena = "";
		return "------------- ÁRBOL ----------------\n"
				+ this.imprimeNodo(raiz, ini, cadena);
	}

	private String imprimeNodo(Nodo<T> nodo, String ini, String cadena) {
		cadena += nodo.toString(ini);
		ini += "-- ";
		for (Nodo<T> hijo : nodo.getHijos()) {
			cadena = imprimeNodo(hijo, ini, cadena);
		}
		return cadena;
	}

	public Nodo<T> busca(int buscado) {
		return this.busca(raiz, buscado);
	}

	private Nodo<T> busca(Nodo<T> padre, int buscado) {
		// Comprobamos si es el propio padre
		if (padre.getIndice() == buscado) {
			return padre;
		} else {
			// Si el nodo tiene hijos
			if (padre.getNumeroHijos() > 0) {
				int cont = 0;
				while (cont < padre.getNumeroHijos()
						&& buscado >= padre.getHijoAt(cont).getIndice())
					cont++;
				// Buscamos por el hijo que corresponda
				return busca(padre.getHijoAt(cont - 1), buscado);
			}
			// Si no tiene hijos devolvemos null
			else
				return null;
		}
	}

	public void setNodo(Nodo<T> nuevoNodo, int buscado) {
		this.setNodo(raiz, nuevoNodo, buscado);
	}

	private void setNodo(Nodo<T> nodo, Nodo<T> nuevoNodo, int buscado) {
		Nodo<T> result = null;
		// Comprobamos si es el propio padre
		if (nodo.getIndice() == buscado) {
			result = nodo;
		} else {
			// Si el nodo tiene hijos
			if (nodo.getNumeroHijos() > 0) {
				int cont = 0;
				while (cont < nodo.getNumeroHijos()
						&& buscado >= nodo.getHijoAt(cont).getIndice())
					cont++;
				// Buscamos por el hijo que corresponda
				setNodo(nodo.getHijoAt(cont - 1), nuevoNodo, buscado);
			}
		}

		if (result != null) {
			Nodo<T> padre = result.getPadre();
			if (padre != null){
				int pos = padre.eliminaHijoByIndex(buscado);
				if (pos != -1)
					padre.addHijoAt(pos, nuevoNodo);
				else {
					Logger.getLogger("CP").warning("setNodo fallido");
				}
			}
			else{
				// Sustituimos la raíz
				raiz.eliminaHijos();
				this.raiz = nuevoNodo;
			}
		}

	}
	
	private boolean isTerminal(Nodo<T> nodo){
		return terminales.contains(nodo);
	}
	
	public Nodo<T> getNodoAleatorio(){
		int num = Aleatorio.entero(MAX_INDEX - 1) + 1;
		return this.busca(num);
	}
	
	public Nodo<T> getFuncionAleatoria(){
		int num = Aleatorio.entero(MAX_INDEX - 1) + 1;
		Nodo<T> buscado = this.busca(num);

		if (isTerminal(buscado)){
			// Si es un terminal devolvemos el padre
			return buscado.getPadre();
		}
		else return buscado;
	}
	
	public Nodo<T> getTerminalAleatorio(){
		int num = Aleatorio.entero(terminales.size());
		return terminales.get(num);
	}

	public Arbol<T> clone() {
		Arbol<T> clon = new Arbol<T>();
		clon.setRaiz(raiz.cloneRaiz());
		ArrayList<Nodo<T>> aux = new ArrayList<Nodo<T>>();
		aux.addAll(this.getTerminales());
		clon.setTerminales(aux);
		clon.setProfundidad(this.profundidad);
		clon.MAX_INDEX = this.MAX_INDEX;
		return clon;
	}

	public Nodo<Tipo> generarRamas(Nodo<Tipo> nodo, int profMax, int profMin) {
		Nodo<Tipo> nuevo = null; Tipo tipo;
		if (profMin > 0){ //no puede ser hoja
			// Random entre 3 funciones + 3 por la situación de los terminales
			tipo = Tipo.values()[(Aleatorio.entero(3) + 3)];
			nodo.setDato(tipo);
			// Comprobamos la aridad del nodo
			if (Funciones.isFuncion(nodo.getDato())){
				int aridad = Funciones.getAridad(nodo.getDato());
				
				// Generamos los hijos del nodo
				for (int i = 0; i < aridad; i++){
					nuevo = new Nodo<Tipo>();
					nodo.addHijo(generarRamas(nuevo, profMax-1, profMin-1));
				}
			}
		}
		// ProfMin = 0
		else if (profMax == 0){ // Sólo puede ser hoja
			// Terminal
			tipo = Tipo.values()[Aleatorio.entero(3)];
			nodo.setDato(tipo);
		}
		else{
			// se decide aleatoriamente terminal o función
			tipo = Tipo.values()[Aleatorio.entero(Tipo.values().length)];
			nodo.setDato(tipo);
			if (Funciones.isFuncion(tipo)){
				int aridad = Funciones.getAridad(nodo.getDato());
				
				// Generamos los hijos del nodo
				for (int i = 0; i < aridad; i++){
					nuevo = new Nodo<Tipo>();
					nodo.addHijo(generarRamas(nuevo, profMax-1, profMin-1));
				}
			}
		}
		return nodo;
	}

	/**
	 * Ejemplo de creación de árbol y cruce.
	 * 
	 * @param args
	 */
//	public static void main(String args[]) {
//		Arbol<Tipo> arbol = new Arbol<Tipo>();
//		Arbol<Tipo> arbol2 = new Arbol<Tipo>();
//
//		Nodo<Tipo> raiz = arbol.generarRamas(new Nodo<Tipo>(), 3, 2);
//		arbol.setRaiz(raiz);
//		arbol.calculaProfundidad();
//		System.out.print(arbol.toString());
//
//		Nodo<Tipo> raiz2 = new Nodo<Tipo>();
//		arbol2.setRaiz(raiz2);
//		arbol2.calculaProfundidad();
//		System.out.print(arbol2.toString());

//		int index1 = Aleatorio.entero(arbol.MAX_INDEX);
//		int index2 = Aleatorio.entero(arbol2.MAX_INDEX);
//
//		Nodo<Tipo> nodo1 = arbol.busca(index1).clone();
//		Nodo<Tipo> nodo2 = arbol2.busca(index2).clone();
//
//		arbol.setNodo(nodo2, index1);
//		arbol2.setNodo(nodo1, index2);
//		arbol.calculaProfundidad();
//		System.out.print(arbol.toString());
//		arbol2.calculaProfundidad();
//		System.out.print(arbol2.toString());
//	}
}
