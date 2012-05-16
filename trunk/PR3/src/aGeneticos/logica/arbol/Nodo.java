package aGeneticos.logica.arbol;

import java.util.ArrayList;
import java.util.List;

public class Nodo<T> {

    private T dato;
    private List<Nodo<T>> hijos;
    private Nodo<T> padre;
    private int indice;			/* Para realizar la búsqueda de los nodos */

    public Nodo() {
        super();
        hijos = new ArrayList<Nodo<T>>();
    }

    public Nodo(T dato) {
        this();
        setDato(dato);
    }

    public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public Nodo<T> getPadre() {
        return this.padre;
    }

    public void setPadre(Nodo<T> padre) {
		this.padre = padre;
	}

	public List<Nodo<T>> getHijos() {
        return this.hijos;
    }

    public int getNumeroHijos() {
        return getHijos().size();
    }

    public boolean tieneHijo() {
        return (getNumeroHijos() > 0);
    }

    public void setHijo(List<Nodo<T>> hijos) {
        for(Nodo<T> hijo : hijos) {
           hijo.padre = this;
        }

        this.hijos = hijos;
    }

    public void addHijo(Nodo<T> hijo) {
        hijo.padre = this;
        hijos.add(hijo);
    }

    public void addHijoAt(int index, Nodo<T> hijo) throws IndexOutOfBoundsException {
        hijo.padre = this;
        hijos.add(index, hijo);
    }

    public void eliminaHijos() {
    	this.hijos.clear();
    }

    public void eliminaHijoAt(int index) throws IndexOutOfBoundsException {
        hijos.remove(index);
    }
    
    public int eliminaHijoByIndex(int indice){
    	int i = 0; boolean encontrado = false;
    	while (!encontrado && i < hijos.size()){
    		encontrado = hijos.get(i).getIndice() == indice;
    		if (encontrado)
    			hijos.remove(i);
    		i++;
    	}
    	if (encontrado) return i-1;
    	else return -1;
    }

    public Nodo<T> getHijoAt(int index) throws IndexOutOfBoundsException {
        return hijos.get(index);
    }

    public T getDato() {
        return this.dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }
    
    public String toString(String ini){
    	return "\n" + ini + " Nodo " + indice + ":"
    			+ "\n" + ini + " Dato: " + this.dato + "\n";
//    			+ "\n Padre: " + this.padre + "\n";
//    			+ "\n -- Hijos: " + this.hijos;
    }
    
    public String toString(){
    	return "\n Nodo " + indice + ":"
    			+ "\n Dato: " + this.dato + "\n";
    }
    
    /**
     * Clona sólo el nodo actual, pero no los hijos ni el padre
     */
    public Nodo<T> clone(){
    	Nodo<T> clon = new Nodo<T>(this.getDato());
    	clon.setIndice(this.getIndice());
    	clon.setPadre(this.getPadre());
    	clon.setHijo(this.getHijos());
    	return clon;
    }
    
    @Override
    public boolean equals(Object otro) {
    	return ((Nodo<T>)otro).getIndice() == this.getIndice() && ((Nodo<T>) otro).getDato() == this.getDato();
    }

    /**
     * Clona todo el contenido de este nodo
     * @return
     */
	public Nodo<T> cloneRaiz() {    	
    	return cloneRaizAux(this);
	}

	private Nodo<T> cloneRaizAux(Nodo<T> nodo) {
    	Nodo<T> clon = new Nodo<T>(nodo.getDato());
    	clon.setIndice(nodo.getIndice());
    	// Clonamos los hijos
		for (Nodo<T> hijo: nodo.getHijos()){
			clon.addHijo(cloneRaizAux(hijo));
		}
		return clon;
	}
}

