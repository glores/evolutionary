package aGeneticos.logica.problemas.evaluadores;

import aGeneticos.controlador.Controlador;
import aGeneticos.controlador.Mapa;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.util.Hormiga;

public class EvaluadorHPenalizaGrandes extends EvaluadorHormiga{
	private int comida,anchoProfundidad,minProf,maxProf;
	private double k;
	
	public EvaluadorHPenalizaGrandes(int minProf,int maxProf, int maxPasos, double k){
		super(maxPasos);
		anchoProfundidad=maxProf-minProf;		
		this.minProf=minProf;
		this.maxProf=maxProf;
		comida=Controlador.getInstance().getMapa().getNumComida();
		maximizar=true;
		this.k = k;	
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
		
		int prof=c.getCadena().getProfundidad();
		double bocadosEscalados=(100/comida)*bocados;
		double profundidadEscalada=(100/anchoProfundidad)*(prof-minProf);
		double aptitud=bocadosEscalados*(1-k)+(100-profundidadEscalada)*k;
		c.setAptitud(aptitud);
		if(aptitud<0){
			log.severe("negativa");
		}
		if(prof>maxProf){
			log.severe("prof:"+prof);
		}
		log.finest("FIN Evaluando nuevo cromosoma. APTITUD=" + aptitud);
	}
}
