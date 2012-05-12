package aGeneticos.logica.arbol;

/**
 * Clase encargada de gestionar todo lo referente a los nodos de terminales
 * @author Gloria Pozuelo
 * @author Sergio Barja
 *
 */
public class Terminales {
	private Tipo[] terminales = {Tipo.AVANZA, Tipo.GIRA_DERECHA, Tipo.GIRA_IZQUIERDA};
	
	/**
	 * Comprueba si el tipo del nodo dado por parámetro es un terminal
	 * @param tipoNodo Tipo del nodo
	 * @return
	 */
	public static boolean isTerminal(Tipo tipoNodo){
		return tipoNodo == Tipo.AVANZA || tipoNodo == Tipo.GIRA_DERECHA || tipoNodo == Tipo.GIRA_IZQUIERDA;
	}

}
