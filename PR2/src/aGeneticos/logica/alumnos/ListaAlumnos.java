package aGeneticos.logica.alumnos;

import java.util.ArrayList;

/**
 * Clase que implementa una lista de alumnos con un array de identificador de posiciones por id.
 * 
 * @author Gloria Pozuelo
 * @author Sergio Barja
 */
public class ListaAlumnos {
	private ArrayList<Alumno> alumnos;
	
	/*
	 * Array que contiene los ids de los alumnos,
	 * para obtener la posición en el array
	 * de alumnos
	 */
	private ArrayList<Integer> posById;
	
	public ListaAlumnos(){
		alumnos = new ArrayList<Alumno>();
		posById = new ArrayList<Integer>();
	}
	
	public void addAlumno(Alumno alumno){
		alumnos.add(alumno);
		posById.add(alumno.getId());
	}
	
	public void setNotaAlumno(int id, double nota){		
		alumnos.get(encontrarPosAlumno(id)).setNota(nota);
	}
	
	public double getNotaAlumno(int id){
		return alumnos.get(encontrarPosAlumno(id)).getNota();
	}
	
	public void addIncompatibleAlumno(int id, int odiado){
		alumnos.get(encontrarPosAlumno(id)).addAlumnoIncompatible(odiado);
	}
	
	public void setIncompatiblesAlumno(int id, ArrayList<Integer> incompatibles){
		alumnos.get(encontrarPosAlumno(id)).setIdImcompatibles(incompatibles);
	}
	
	public ArrayList<Integer> getIncompatiblesAlumno(int id){
		return alumnos.get(encontrarPosAlumno(id)).getIdImcompatibles();
	}

	private int encontrarPosAlumno(int id) {
		int i = 0; boolean encontrado = false;
		
		while (!encontrado && i < posById.size()){
			encontrado = id == posById.get(i);
			i++;
		}
		
		return i-1;
	}
}
