package aGeneticos.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import aGeneticos.controlador.Mapa;
import aGeneticos.logica.arbol.Nodo;
import aGeneticos.logica.arbol.Tipo;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.logica.problemas.evaluadores.EvaluadorHormiga;
import aGeneticos.util.Hormiga;
import aGeneticos.util.Hormiga.Direccion;

/**
 * Panel que contiene el mapa visual.
 * 
 */
public class PanelMapa extends JSplitPane {
	private static final long serialVersionUID = 1L;
	/**
	 * Mapa de casillas. Se construye a partir de un mapa binario.
	 */

	private Mapa mapa;
	private Cromosoma solucion;
	private Casilla[][] casillas;
	private int pasos;
	private int bocados;
	private Hormiga hormiga;

	private JPanel panelTablero;
	private JPanel panel;

	/**
	 * Casilla visual.
	 * 
	 */
	private class Casilla extends JTextArea {
		private static final long serialVersionUID = 1L;
		private boolean tieneComida;
		private boolean haPasado;

		public Casilla(boolean tieneComida) {
			setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			this.setEditable(false);
			setForeground(Color.BLACK);			
			setFont(new Font("Monospace",Font.BOLD,10));
			setAlignmentX(CENTER_ALIGNMENT);
			setAlignmentY(CENTER_ALIGNMENT);
			this.tieneComida = tieneComida;
			haPasado = false;
			cargarFondo();
		}

		private void cambiarColor(Color c) {
			setBackground(c);
			// Si el L&F lo tapa, usar esto
			/*
			 * putClientProperty("Synthetica.background", c);
			 * putClientProperty("Synthetica.background.alpha", 0.20f);
			 */
		}

		private void cargarFondo() {
			if (this.tieneComida) {
				if (this.haPasado) {
					cambiarColor(Color.GREEN);
				} else {
					cambiarColor(Color.BLUE);
				}
			} else {
				if (this.haPasado) {
					cambiarColor(Color.RED);
				} else {
					cambiarColor(Color.WHITE);
				}
			}
		}

		public void pasaLaHormiga(Hormiga hormiga) {
			this.haPasado = true;	
			cargarTexto(hormiga.getDir());			
			cargarFondo();
		}

		private void cargarTexto(Direccion dir) {
			switch(dir){
			case Norte:
				setText("\u2191");
				break;
			case Sur:
				setText("\u2193");
				break;
			case Este:
				setText("\u2192");
				break;
			case Oeste:				
				setText("\u2190");
				break;
			}
		}

	}

	/**
	 * Constructora de la clase.
	 */
	public PanelMapa() {
		super();
		setDividerSize(3);
		this.panel = new JPanel();
		JScrollPane p = new JScrollPane(panel);
		panelTablero = new JPanel();
		panel.setBackground(Color.white);
		setLeftComponent(p);
		setRightComponent(panelTablero);
	}

	public void cargarMapa(Mapa mapa) {
		this.mapa = mapa;
		casillas = new Casilla[mapa.getNumFilas()][mapa.getNumCols()];
		for (int i = 0; i < mapa.getNumFilas(); i++) {
			for (int j = 0; j < mapa.getNumCols(); j++) {
				casillas[i][j] = new Casilla(mapa.getCasilla(i, j));
			}
		}

	}

	public void dibujarTablero() {
		panelTablero.removeAll();
		panelTablero.setLayout(new java.awt.GridLayout(casillas.length,
				casillas[0].length));
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				panelTablero.add(casillas[i][j]);
			}
		}

	}

	public void cargarSolucion(Cromosoma solucion) {
		panel.removeAll();
		pasos = 0;
		this.solucion = solucion;
		bocados = 0;
		hormiga = new Hormiga();
		while (pasos < EvaluadorHormiga.getMaxPasos()) {
			ejecutarArbol(solucion.getCadena().getRaiz());
		}
		JTextArea textin = new JTextArea("Bocados: " + bocados + ""
				+ "\n Profundidad: " + solucion.getCadena().getProfundidad()
				+ "\n Núm nodos: " + solucion.getCadena().getNumeroNodos()
				+ "\n\n" + solucion.getCadena().toStringDato());

		textin.setEditable(false);
		panel.add(textin);

	}

	// Copiado del evaluador
	private void ejecutarArbol(Nodo<Tipo> nodo) {
		// mientras no se haya acabado el tiempo ni la comida
		if (pasos < EvaluadorHormiga.getMaxPasos()) {
			// Marcar el paso
			casillas[hormiga.getX()][hormiga.getY()].pasaLaHormiga(hormiga);
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

}
