package controlador;
import java.awt.Graphics2D;

import parametros.ParametrosAlgoritmo;
import logica.esqueleto.algoritmos.AGenetico;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que implementa el controlador del patr�n MVC. Se encarga de la comunicaci�n entre l�gica y GUI.
 */

public class Controlador {
	private AGenetico aGenetico;
	private static Controlador controlador;
	private static Pintor pintor;
	
	public static Controlador getInstance() {
		if (controlador == null) {
			controlador = new Controlador();
			pintor=new Pintor();
		}
		return controlador;
	}

	public Controlador() {	
	}
	
	public Controlador(AGenetico algoritmo) {
		aGenetico = algoritmo;
	}
	
	public void setAGenetico(AGenetico algoritmo) {
		aGenetico = algoritmo;
	}

	
	public void inicia(ParametrosAlgoritmo parametros) {	
		aGenetico.setCruzador(parametros.getCruzador());
		aGenetico.setEvaluador(parametros.getProblema().getEvaluador());
		aGenetico.setGeneradorPoblaciones(parametros.getGeneradorPoblaciones());
		aGenetico.setMutador(parametros.getMutador());
		aGenetico.setNumMaxGen(parametros.getLimIteraciones());
		aGenetico.setTamPoblacion(parametros.getTamPoblacion());
		aGenetico.setProbCruce(parametros.getProbabilidadCruce());
		aGenetico.setProbMutacion(parametros.getProbabilidadMutacion());
		aGenetico.setSeleccionador(parametros.getSeleccionador());
		aGenetico.setTamElite(parametros.getTamElite());
		aGenetico.setTamPoblacion(parametros.getTamPoblacion());	
		aGenetico.run();
	}
	
	public void dibuja(Graphics2D graphics){		
		pintor.dibujarGrafica(graphics);
	}
	
	public Pintor getPintor(){
		return pintor;
	}
	
}
