package aGeneticos.logica.alumnos;

import java.util.ArrayList;

/**
 * Clase que implementa una lista de alumnos con un array de identificador de posiciones por id.
 * 
 * @author Gloria Pozuelo
 * @author Sergio Barja
 */
public class ListaAlumnos {
	private static ArrayList<Alumno> alumnos;
	private static double media;

	
	/*
	 * Array que contiene los ids de los alumnos,
	 * para obtener la posición en el array
	 * de alumnos
	 */
	private ArrayList<PosById> posById;	
	private static int tamGrupo;
	
	public ListaAlumnos(){
		alumnos = new ArrayList<Alumno>();
		posById = new ArrayList<PosById>();
	}
	
	public void addAlumno(Alumno alumno){
		posById.add(new PosById(alumno.getId(), alumnos.size()));
		alumnos.add(alumno);
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
			encontrado = id == posById.get(i).getId();
			i++;
		}
		
		return posById.get(i-1).getPos();
	}
	
	/**
	 * Calcula la nota media de los alumnos
	 */
	public void calcularMedia(){
		double sum = 0;
		for (int i = 0; i < alumnos.size(); i++)
			sum += alumnos.get(i).getNota();
		media = sum / alumnos.size();
	}
	
	public void setTamGrupo(int tam){
		this.tamGrupo = tam;
	}
	
	public int getSize(){
		return alumnos.size();
	}

	/**
	 * Devuelve el número de incompatibilidades dado un array con ids de alumnos
	 * @param posById
	 * @return
	 */
	public static int getIncompatibles(ArrayList<PosById> posById){
		int sum = 0, id, i = 0, cont = 0;
		while (i < getNumGrupos()){
			// Para cada miembro del grupo se comprueba si son incompatibles entre sí
			id = posById.get(i*tamGrupo + cont).getId();
			for (int j = 0; j < tamGrupo; j++){
				if (alumnos.get(posById.get(i*tamGrupo + j).getPos()).getIdImcompatibles().contains(id))
					sum++;
			}
			if (cont == tamGrupo - 1){
				i++; cont = 0;
			}
			else {
				cont++;
			}
		}
		return sum;
	}
	
	public static int getNumGrupos(){
		return (int) Math.floor(alumnos.size() / tamGrupo);
	}
	
	public int getTamGrupo(){
		return tamGrupo;
	}

	public ArrayList<PosById> getPosById() {
		return posById;
	}
	
	/**
	 * Calcula el desequilibrio de los alumnos dado el array con los grupos
	 * @param posById
	 * @return
	 */
	public static double getDesequilibrio(ArrayList<PosById> posById){
		double desequilibrio = 0; double sumGrupo = 0;
		for (int i = 0; i < getNumGrupos(); i++){
			sumGrupo = 0;
			for (int j = 0; j < tamGrupo; j++){
				sumGrupo += alumnos.get(posById.get(i*tamGrupo + j).getPos()).getNota() - media;
			}
			desequilibrio += Math.pow(sumGrupo, 2);
		}		
		return desequilibrio;
	}
}
