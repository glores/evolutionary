package aGeneticos.logica.arbol;

import java.util.logging.Logger;

import aGeneticos.util.Aleatorio;

public class Arbol<T> {
	private int profundidad;
	public int MAX_INDEX = 0; /* Se utiliza para obtener un nodo aleatorio del árbol */

	private Nodo<T> raiz;
	
	public Arbol(Nodo<T> raiz){
		super();
		profundidad = 0;
		this.raiz = raiz;
	}

	public Arbol() {
		super();
		profundidad = 0;
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
	public void calculaProfundidad(){
		// Se reinicia para recalcular la profundidad
		MAX_INDEX = 0;
		raiz.setIndice(MAX_INDEX);
		MAX_INDEX++;
		this.calculaProfundidad(raiz, 0);
	}

	private void calculaProfundidad(Nodo<T> nodo, int profundidad) {
		for (int i = 0; i < nodo.getNumeroHijos(); i++) {
			nodo.getHijoAt(i).setIndice(MAX_INDEX);
			MAX_INDEX++;
			calculaProfundidad(nodo.getHijoAt(i), profundidad + 1);
		}

		if (this.profundidad < profundidad) {
			this.profundidad = profundidad;
		}
	}
	
	public String toString(){
		String ini = ""; String cadena = "";
		return "------------- ÁRBOL ----------------\n"
				+ this.imprimeNodo(raiz, ini, cadena);
	}
	
	private String imprimeNodo(Nodo<T> nodo, String ini, String cadena){
		cadena += nodo.toString(ini);
		ini += "-- ";
		for (Nodo<T> hijo: nodo.getHijos()){
			cadena = imprimeNodo(hijo, ini, cadena);
		}
		return cadena;
	}

	
	public Nodo<T> busca(int buscado){
		return this.busca(raiz, buscado);
	}


	private Nodo<T> busca(Nodo<T> padre, int buscado) {		
		// Comprobamos si es el propio padre
		if (padre.getIndice() == buscado){
			return padre;
		}
		else{
			// Si el nodo tiene hijos
			if (padre.getNumeroHijos() > 0){
				int cont = 0;
				while (cont < padre.getNumeroHijos() && buscado >= padre.getHijoAt(cont).getIndice()) cont++;
				// Buscamos por el hijo que corresponda
				return busca(padre.getHijoAt(cont-1), buscado);
			}
			// Si no tiene hijos devolvemos null
			else return null;
		}
	}
	
	public void setNodo(Nodo<T> nuevoNodo, int buscado){
		this.setNodo(raiz, nuevoNodo, buscado);
	}
	
	private void setNodo(Nodo<T> nodo, Nodo<T> nuevoNodo, int buscado) {	
		Nodo<T> result = null;
		// Comprobamos si es el propio padre
		if (nodo.getIndice() == buscado){
			result = nodo;
		}
		else{
			// Si el nodo tiene hijos
			if (nodo.getNumeroHijos() > 0){
				int cont = 0;
				while (cont < nodo.getNumeroHijos() && buscado >= nodo.getHijoAt(cont).getIndice()) cont++;
				// Buscamos por el hijo que corresponda
				setNodo(nodo.getHijoAt(cont-1), nuevoNodo, buscado);
			}
		}
		
		if (result != null){
			Nodo<T> padre = result.getPadre();
			int pos = padre.eliminaHijoByIndex(buscado);
			if (pos != -1)
				padre.addHijoAt(pos, nuevoNodo);
			else{
				Logger.getLogger("CP").warning("setNodo fallido");
			}
		}
		
	}
	
	public Arbol<T> clone(){
		Arbol<T> clon = new Arbol<T>();
		clon.setRaiz(raiz.cloneRaiz());
		return clon;
	}

	/**
	 * Ejemplo de creación de árbol y cruce.
	 * @param args
	 */
	public static void main(String args[]) {
		Arbol<Integer> arbol = new Arbol<Integer>();
		Arbol<Integer> arbol2 = new Arbol<Integer>();
		
		Nodo<Integer> raiz = new Nodo<Integer>(1);
		Nodo<Integer> hijo = new Nodo<Integer>(2);
		hijo.addHijo(new Nodo<Integer>(3));
		raiz.addHijo(hijo);
		raiz.addHijo(new Nodo<Integer>(4));
		raiz.addHijo(new Nodo<Integer>(5));
		arbol.setRaiz(raiz);
		arbol.calculaProfundidad();
		System.out.print(arbol.toString());
		
		Nodo<Integer> raiz2 = new Nodo<Integer>(6);
		Nodo<Integer> hijo2 = new Nodo<Integer>(7);
		hijo2.addHijo(new Nodo<Integer>(8));
		raiz2.addHijo(hijo2);
		raiz2.addHijo(new Nodo<Integer>(9));
		raiz2.addHijo(new Nodo<Integer>(10));
		arbol2.setRaiz(raiz2);
		arbol2.calculaProfundidad();
		System.out.print(arbol2.toString());
		
		int index1 = Aleatorio.entero(arbol.MAX_INDEX);
		int index2 = Aleatorio.entero(arbol2.MAX_INDEX);
		
		Nodo<Integer> nodo1 = arbol.busca(index1).clone();
		Nodo<Integer> nodo2 = arbol2.busca(index2).clone();
		
		arbol.setNodo(nodo2, index1);		
		arbol2.setNodo(nodo1, index2);	
		arbol.calculaProfundidad();
		System.out.print(arbol.toString());
		arbol2.calculaProfundidad();		
		System.out.print(arbol2.toString());
	}
}
