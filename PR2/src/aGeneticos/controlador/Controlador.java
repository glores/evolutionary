package aGeneticos.controlador;


import java.awt.Graphics2D;
import java.util.logging.Logger;

import aGeneticos.gui.parametros.ParametrosAlgoritmo;
import aGeneticos.logica.AGenetico;


/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 * 
 *         Clase que implementa el controlador del patrón MVC. Se encarga de la
 *         comunicación entre lógica y GUI.
 */

public class Controlador {
	private AGenetico aGenetico;
	private static Controlador controlador;
	private static PintorBase pintor;
	private boolean modoIterativo;
	private int numIteraciones;
	private int iteracionActual;
	

	public static Controlador getInstance() {
		if (controlador == null) {
			controlador = new Controlador();
			pintor = new Pintor();
		}
		return controlador;
	}

	public Controlador() {
		modoIterativo = false;
	}

	public Controlador(AGenetico algoritmo) {
		aGenetico = algoritmo;
		modoIterativo = false;
	}

	public void setAGenetico(AGenetico algoritmo) {
		aGenetico = algoritmo;
	}

	public void inicia(ParametrosAlgoritmo parametros) {
		
		if (parametros.getIntProbCruce_habilitado()) {
			modoIterativo = true;
			
			pintor=new PintorIterativo();			
			modoIntervaloProbCruce(parametros);
		} else {
			modoIterativo=false;
			pintor=new Pintor();
			((Pintor)pintor).setTamGeneraciones(parametros.getLimIteraciones());
			pintor.iniciar();
			aGenetico.setCruzador(parametros.getCruzador());
			aGenetico.setEvaluador(parametros.getProblema().getEvaluador());
			aGenetico.setGeneradorPoblaciones(parametros
					.getGeneradorPoblaciones());
			aGenetico.setMutador(parametros.getMutador());
			aGenetico.setNumMaxGen(parametros.getLimIteraciones());
			aGenetico.setTamPoblacion(parametros.getTamPoblacion());
			aGenetico.setProbCruce(parametros.getProbabilidadCruce());
			aGenetico.setProbMutacion(parametros.getProbabilidadMutacion());
			aGenetico.setSeleccionador(parametros.getSeleccionador());
			aGenetico.setTamElite(parametros.getTamElite());
			aGenetico.setListaAlumnos(parametros.getPath(), parametros.getTamGrupo());
			aGenetico.run();
		}
	}

	private void modoIntervaloProbCruce(ParametrosAlgoritmo parametros) {
		aGenetico.setCruzador(parametros.getCruzador());
		aGenetico.setEvaluador(parametros.getProblema().getEvaluador());
		aGenetico.setGeneradorPoblaciones(parametros.getGeneradorPoblaciones());
		aGenetico.setMutador(parametros.getMutador());
		aGenetico.setNumMaxGen(parametros.getLimIteraciones());
		aGenetico.setTamPoblacion(parametros.getTamPoblacion());
		aGenetico.setProbMutacion(parametros.getProbabilidadMutacion());
		aGenetico.setSeleccionador(parametros.getSeleccionador());
		aGenetico.setTamElite(parametros.getTamElite());
		aGenetico.setProbCruce(parametros.getProbabilidadCruce());
		aGenetico.setListaAlumnos(parametros.getPath(), parametros.getTamGrupo());

		// Bucle incrementando la probabilidad.
		double probCruceActual = parametros.getIntProbCruce_a();		
		double incremento = parametros.getIntProbCruce_inc();		
		numIteraciones=(int)((parametros.getIntProbCruce_b()-probCruceActual)/incremento);
		iteracionActual=0;
		
		((PintorIterativo)pintor).setTamIteraciones(numIteraciones);
		((PintorIterativo)pintor).setTamGeneraciones(parametros.getLimIteraciones());
		pintor.iniciar();
		
		Logger log = Logger.getLogger("CP");
		log.warning("Modo iterativo. Intervalo sobre Probabilidad de cruce: Desde "
				+ probCruceActual + " hasta "
				+ parametros.getIntProbCruce_b()
				+ " con un incremento de " + incremento);
		
		while(iteracionActual<numIteraciones){
			log.warning("Nueva iteracion.["+iteracionActual+"] Probabilidad de cruce: "+probCruceActual);
			aGenetico.setProbCruce(probCruceActual);
			aGenetico.run();
			probCruceActual+=incremento;
			iteracionActual++;
		}
		

	}

	public void dibuja(Graphics2D graphics) {
		pintor.dibujarGrafica(graphics);
	}

	public PintorBase getPintor() {
		return pintor;
	}

	public boolean esModoIterativo(){
		return modoIterativo;
	}
	
	public boolean esUltimaIteracion(){
		return iteracionActual==numIteraciones-1;
	}
	
	public int getNumIteraciones(){
		return numIteraciones;
	}
	
}
