package aGeneticos.logica.alumnos;

import java.util.ArrayList;

/**
 * Clase que implementa un alumno
 * 
 * @author Gloria Pozuelo
 * @author Sergio Barja
 *
 */

public class Alumno {
	private int id;
	private double nota;
	private ArrayList<Integer> idImcompatibles;
	
	public Alumno(int id, double nota){
		this.id = id;
		this.nota = nota;
		idImcompatibles = new ArrayList<Integer>();
	}
	
	public void addAlumnoIncompatible(int id){
		if (!idImcompatibles.contains(id))
			idImcompatibles.add(id);
	}
	
	/*---------------- GETTERS Y SETTERS ------------------------*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public ArrayList<Integer> getIdImcompatibles() {
		return idImcompatibles;
	}
	
	public int getNumIncompatibles(){
		return idImcompatibles.size();
	}

	public void setIdImcompatibles(ArrayList<Integer> idImcompatibles) {
		this.idImcompatibles = idImcompatibles;
	}
	
	public String toString(){
		return "\n ID: " + id +
				"\n Nota: " + nota +
				"\n Incompatibles: " + idImcompatibles.toString();
	}
	
}
