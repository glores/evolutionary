package aGeneticos.logica.problemas.evaluadores;

import java.util.logging.Logger;

import aGeneticos.controlador.Controlador;
import aGeneticos.controlador.Mapa;
import aGeneticos.logica.abtractas.Evaluador;
import aGeneticos.logica.arbol.Nodo;
import aGeneticos.logica.arbol.Tipo;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Hormiga;

public class EvaluadorHormiga extends Evaluador {
	protected int bocados;
	protected static int maxPasos;
	protected int pasos;
	protected Mapa mapa;
	protected Hormiga hormiga;
	protected Logger log;

	public EvaluadorHormiga(int max) {
		this.maximizar = true;
		log = Logger.getLogger("CP");
		maxPasos = max;
		Hormiga.setTamTablero(
				Controlador.getInstance().getMapa().getNumCols(), Controlador
						.getInstance().getMapa().getNumFilas());
	}

	@Override
	public void fitness(Cromosoma c) {
		log.finest("Evaluando nuevo cromosoma");
		bocados = 0;
		pasos = 0;
		mapa = (Mapa) Controlador.getInstance().getMapa().clone();
		hormiga = new Hormiga();
		while (pasos < maxPasos && bocados<=mapa.getNumComida()) {
			log.finest("Nueva ejecución del programa");
			try {
				ejecutarArbol(c.getCadena().getRaiz());
			} catch (Exception e) {
				this.log.severe("Cromosoma erróneo: \n"
						+ c.getCadena().toString());
				pasos++;
			}
		}

		c.setAptitud(bocados);
		log.finest("FIN Evaluando nuevo cromosoma. APTITUD=" + bocados);
	}

	protected void ejecutarArbol(Nodo<Tipo> nodo) {
		log.finest("Instrucción: " + nodo.getDato().toString());
		// mientras no se haya acabado el tiempo ni la comida
		if ((pasos < maxPasos)&&(bocados<mapa.getNumComida())) {
			// si estamos encima de comida comemos
			if (mapa.getCasilla(hormiga.getX(), hormiga.getY())) {
				mapa.comer(hormiga.getX(), hormiga.getY());
				bocados++;
			}
			if (nodo.getDato().equals(Tipo.PROGN3)) {
				ejecutarArbol(nodo.getHijoAt(0));
				ejecutarArbol(nodo.getHijoAt(1));
				ejecutarArbol(nodo.getHijoAt(2));
			} else if (nodo.getDato().equals(Tipo.PROGN2)) {
				ejecutarArbol(nodo.getHijoAt(0));
				ejecutarArbol(nodo.getHijoAt(1));
			} else if (nodo.getDato().equals(Tipo.SIC)) {
				int[] sigPos = hormiga.getSigPos();
				if (mapa.getCasilla(sigPos[0], sigPos[1])) {
					// Hay comida delante
					ejecutarArbol(nodo.getHijoAt(0));
				} else {
					// No hay comida delante
					ejecutarArbol(nodo.getHijoAt(1));
				}
			} else if (nodo.getDato().equals(Tipo.AVANZA)) {
				hormiga.avanzar();
				pasos++;
			} else if (nodo.getDato().equals(Tipo.GIRA_DERECHA)) {
				hormiga.girarDer();
				pasos++;
			} else if (nodo.getDato().equals(Tipo.GIRA_IZQUIERDA)) {
				hormiga.girarIzq();
				pasos++;
			}
		}
	}

	public static int getMaxPasos() {
		return maxPasos;
	}
}
