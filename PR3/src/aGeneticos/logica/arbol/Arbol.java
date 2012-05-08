package aGeneticos.logica.arbol;

import java.util.*;

public class Arbol<T> {
	
	public enum Orden {
	    PRE_ORDEN,
	    POST_ORDEN
	}

    private Nodo<T> raiz;

    public Arbol() {
        super();
    }

    public Nodo<T> getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo<T> raiz) {
		this.raiz = raiz;
	}

	public int getNumeroNodos() {
        int num = 0;

        if(raiz != null) {
            num = getNumeroNodosAux(raiz) + 1; // 1 para la raíz
        }

        return num;
    }

    private int getNumeroNodosAux(Nodo<T> nodo) {
        int num = nodo.getNumeroHijos();

        for(Nodo<T> hijo : nodo.getHijos()) {
            num += getNumeroNodosAux(hijo);
        }

        return num;
    }

    public boolean isVacio() {
        return (raiz == null);
    }

    public List<Nodo<T>> construye(Orden ordenTrasversal) {
        List<Nodo<T>> listaResult = null;

        if(raiz != null) {
            listaResult = construye(raiz, ordenTrasversal);
        }

        return listaResult;
    }

    public List<Nodo<T>> construye(Nodo<T> nodo, Orden ordenTrasversal) {
        List<Nodo<T>> result = new ArrayList<Nodo<T>>();

        if(ordenTrasversal == Orden.PRE_ORDEN) {
            construyePreorden(nodo, result);
        }

        else if(ordenTrasversal == Orden.POST_ORDEN) {
            construyePostorden(nodo, result);
        }

        return result;
    }

    private void construyePreorden(Nodo<T> nodo, List<Nodo<T>> ordenResult) {
        ordenResult.add(nodo);

        for(Nodo<T> hijo : nodo.getHijos()) {
            construyePreorden(hijo, ordenResult);
        }
    }

    private void construyePostorden(Nodo<T> nodo, List<Nodo<T>> ordenResult) {
        for(Nodo<T> hijo : nodo.getHijos()) {
            construyePostorden(hijo, ordenResult);
        }

        ordenResult.add(nodo);
    }

    public Map<Nodo<T>, Integer> construyeConProfundidad(Orden orden) {
        Map<Nodo<T>, Integer> mapaResult = null;

        if(raiz != null) {
            mapaResult = construyeConProfundidad(raiz, orden);
        }

        return mapaResult;
    }

    public Map<Nodo<T>, Integer> construyeConProfundidad(Nodo<T> nodo, Orden orden) {
        Map<Nodo<T>, Integer> ordenResult = new LinkedHashMap<Nodo<T>, Integer>();

        if(orden == Orden.PRE_ORDEN) {
            construyePreordenConProfundidad(nodo, ordenResult, 0);
        }

        else if(orden == Orden.POST_ORDEN) {
            construyePostordenConProfundidad(nodo, ordenResult, 0);
        }

        return ordenResult;
    }

    private void construyePreordenConProfundidad(Nodo<T> nodo, Map<Nodo<T>, Integer> ordenResult, int profundidad) {
        ordenResult.put(nodo, profundidad);

        for(Nodo<T> hijo : nodo.getHijos()) {
            construyePreordenConProfundidad(hijo, ordenResult, profundidad + 1);
        }
    }

    private void construyePostordenConProfundidad(Nodo<T> nodo, Map<Nodo<T>, Integer> ordenResult, int profundidad) {
        for(Nodo<T> hijo : nodo.getHijos()) {
            construyePostordenConProfundidad(hijo, ordenResult, profundidad + 1);
        }

        ordenResult.put(nodo, profundidad);
    }

    public String toString() {
       // Por defecto preorden
        String result = "";

        if(raiz != null) {
            result = construye(Orden.PRE_ORDEN).toString();

        }

        return result;
    }

    public String toStringWithDepth() {
        // Por defecto preornden

        String result = "";

        if(raiz != null) {
            result = construyeConProfundidad(Orden.PRE_ORDEN).toString();
        }

        return result;
    }
    
    /**
     * Buscar el nodo viejo de este árbol, con el hascode del objeto
     * @param nodoNuevo El nuevo que viene de otro árbol
     * @param nodoViejo El de este árbol
     */
    public void intercambia(Nodo<T> nodoNuevo, Nodo<T> nodoViejo){
    	
    }
    
    public static void main(String args[]){
    	Arbol<Integer> arbol = new Arbol<Integer>();
    	Nodo<Integer> raiz = new Nodo<Integer>(1);
    	Nodo<Integer> hijo = new Nodo<Integer>(2);
    	hijo.addHijo(new Nodo<Integer>(70));
    	raiz.addHijo(hijo);
    	raiz.addHijo(new Nodo<Integer>(3));
    	raiz.addHijo(new Nodo<Integer>(4));
    	arbol.setRaiz(raiz);
    	System.out.print(arbol.getRaiz().getNumeroHijos());
    	System.out.print(arbol.toStringWithDepth());
    }
}
