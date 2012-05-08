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

    public void setDato(T data) {
        this.dato = data;
    }

    public String toString() {
        return getDato().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
           return true;
        }
        if (obj == null) {
           return false;
        }
        if (getClass() != obj.getClass()) {
           return false;
        }
        Nodo<?> otro = (Nodo<?>) obj;
        if (dato == null) {
           if (otro.dato != null) {
              return false;
           }
        } else if (!dato.equals(otro.dato)) {
           return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
       final int primo = 31;
       int result = 1;
       result = primo * result + ((dato == null) ? 0 : dato.hashCode());
       return result;
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

