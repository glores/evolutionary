package aGeneticos.logica.problemas.evaluadores;

import aGeneticos.logica.abtractas.Evaluador;
import aGeneticos.logica.poblacion.Cromosoma;

public class EvaluadorAlumnos extends Evaluador {
	private double n;

	
	public EvaluadorAlumnos(double n){
		this.maximizar = false;
		this.n = n;
	}

	@Override
	public void fitness(Cromosoma c) {
		double res = n;
		c.setAptitud(res);
	}

}
