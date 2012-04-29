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
				" Pos: " + pos;
	}
	
	@Override
	public boolean equals(Object otro){
		return this.id == ((PosById) otro).getId() && this.pos == ((PosById) otro).getPos();
	}
}
