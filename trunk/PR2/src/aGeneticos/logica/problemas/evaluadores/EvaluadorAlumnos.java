package aGeneticos.logica.problemas.evaluadores;

import java.util.logging.Logger;

import aGeneticos.logica.abtractas.Evaluador;
import aGeneticos.logica.poblacion.Cromosoma;

public class EvaluadorAlumnos extends Evaluador {
	private Logger log;
	private double n;

	
	public EvaluadorAlumnos(double n){
		log = Logger.getLogger("CP");
		this.maximizar = false;
		this.n = n;
	}

	@Override
	public void fitness(Cromosoma c) {
		double res = n* c.getDesequilibrio() + (1 - n)* c.getIncompatibles();
		c.setAptitud(res);
	}

}
