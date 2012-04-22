package aGeneticos.logica.alumnos;

public class PosById{
	private int id;		/* Identificador de alumnos */
	private int pos; 	/* Posición dentro del array de alumnos */
	
	public PosById(int id, int pos){
		this.id = id;
		this.pos = pos;
	}
	
	public int getId() {
		return id;
	}
	public int getPos() {
		return pos;
	}
	
	public String toString(){
		return "\n ID: " + id +
				"\n Pos: " + pos;
	}
}
