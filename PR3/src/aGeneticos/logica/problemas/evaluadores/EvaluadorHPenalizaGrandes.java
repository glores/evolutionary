package aGeneticos.logica.problemas.evaluadores;

import aGeneticos.controlador.Controlador;
import aGeneticos.controlador.Mapa;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Hormiga;

public class EvaluadorHPenalizaGrandes extends EvaluadorHormiga{
	private int comida,anchoProfundidad,minProf;
	
	public EvaluadorHPenalizaGrandes(int minProf,int maxProf){
		super();
		anchoProfundidad=maxProf-minProf;		
		this.minProf=minProf;
		comida=Controlador.getInstance().getMapa().getNumComida();
		maximizar=true;
		
	}
	@Override
	public void fitness(Cromosoma c) {
		log.finest("Evaluando nuevo cromosoma");
		bocados = 0;
		pasos = 0;
		mapa = (Mapa) Controlador.getInstance().getMapa().clone();
		hormiga = new Hormiga();
		while (pasos < maxPasos) {
			log.finest("Nueva ejecución del programa");
			try {
				ejecutarArbol(c.getCadena().getRaiz());
			} catch (Exception e) {
				this.log.severe("Cromosoma erróneo: \n" + c.getCadena().toString());
				pasos++;
			}
		}
		double k=0.5;
		
		
		double bocadosEscalados=(100/comida)*bocados;
		double profundidadEscalada=(100/anchoProfundidad)*(c.getCadena().getNumeroNodos()-minProf);
		double aptitud=bocadosEscalados*(1-k)+(100-profundidadEscalada)*k;
		c.setAptitud(aptitud);
		if(aptitud<0){
			log.severe("negativa");
		}
		if(c.getCadena().getNumeroNodos()>50){
			log.severe("prof:"+c.getCadena().getNumeroNodos());
		}
		log.finest("FIN Evaluando nuevo cromosoma. APTITUD=" + bocados);
	}
}
