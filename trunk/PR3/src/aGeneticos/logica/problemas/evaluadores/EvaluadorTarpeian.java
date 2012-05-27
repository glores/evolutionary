package aGeneticos.logica.problemas.evaluadores;

import aGeneticos.controlador.Controlador;
import aGeneticos.controlador.Mapa;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Aleatorio;
import aGeneticos.util.Hormiga;

public class EvaluadorTarpeian extends EvaluadorHormiga {
	private int k;
	private static final double FITNESS_BAJO = 0.0001;

	public EvaluadorTarpeian(int max, int k) {
		super(max);
		this.k = k;
	}

	@Override
	public void fitness(Cromosoma c) {
		log.finest("Evaluando nuevo cromosoma");
		double aptitud;

		if ((numNodosMedioPob>0)&&(c.getCadena().getNumeroNodos() > numNodosMedioPob)
				&& (Aleatorio.entero(k) == 0)) {
			aptitud = FITNESS_BAJO;
		} else {
			bocados = 0;
			pasos = 0;
			mapa = (Mapa) Controlador.getInstance().getMapa().clone();
			hormiga = new Hormiga();
			while (pasos < maxPasos) {
				log.finest("Nueva ejecución del programa");
				try {
					ejecutarArbol(c.getCadena().getRaiz());
				} catch (Exception e) {
					this.log.severe("Cromosoma erróneo: \n"
							+ c.getCadena().toString());
					pasos++;
				}
			}
			aptitud = bocados;
		}
		
		c.setAptitud(aptitud);

		log.finest("FIN Evaluando nuevo cromosoma. APTITUD=");
	}

}
