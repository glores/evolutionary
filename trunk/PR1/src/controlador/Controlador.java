package controlador;

import java.awt.Graphics2D;
import java.util.logging.Logger;

import parametros.ParametrosAlgoritmo;
import logica.esqueleto.algoritmos.AGenetico;

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
	private static Pintor pintor;
	private boolean modoIterativo;

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
			modoIntervaloProbCruce(parametros);
		} else {
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

		// Bucle incrementando la probabilidad.
		double probCruceActual = parametros.getIntProbCruce_a();
		double incremento = parametros.getIntProbCruce_inc();
		Logger log = Logger.getLogger("CP");
		log.warning("Modo iterativo. Intervalo sobre Probabilidad de cruce: Desde "
				+ probCruceActual + " hasta "
				+ parametros.getIntProbCruce_b()
				+ " con un incremento de " + incremento);
		while (probCruceActual < parametros.getIntProbCruce_b()) {
			log.warning("Nueva iteracion. Probabilidad de cruce: "+probCruceActual);
			aGenetico.setProbCruce(probCruceActual);
			aGenetico.run();
			probCruceActual += incremento;
		}

	}

	public void dibuja(Graphics2D graphics) {
		pintor.dibujarGrafica(graphics);
	}

	public Pintor getPintor() {
		return pintor;
	}

}
