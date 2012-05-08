package aGeneticos.logica.arbol;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nodo<T> {

    private T dato;
    private List<Nodo<T>> hijos;
    private Nodo<T> padre;

    public Nodo() {
        super();
        hijos = new ArrayList<Nodo<T>>();
    }

    public Nodo(T dato) {
        this();
        setDato(dato);
    }

    public Nodo<T> getPadre() {
        return this.padre;
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
        this.hijos = new ArrayList<Nodo<T>>();
    }

    public void eliminaHijoAt(int index) throws IndexOutOfBoundsException {
        hijos.remove(index);
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

    public String toString() {
        return getDato().toString();
    }

    public String toStringVerbose() {
        String stringRepresentation = getDato().toString() + ":[";

        for (Nodo<T> node : getHijos()) {
            stringRepresentation += node.getDato().toString() + ", ";
        }

        //Pattern.DOTALL causes ^ and $ to match. Otherwise it won't. It's retarded.
        Pattern pattern = Pattern.compile(", $", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(stringRepresentation);

        stringRepresentation = matcher.replaceFirst("");
        stringRepresentation += "]";

        return stringRepresentation;
    }
}

