package aGeneticos.logica.problemas;

import aGeneticos.logica.abtractas.Funcion;
import aGeneticos.logica.problemas.evaluadores.EvaluadorAlumnos;

public class FuncionAlumnos extends Funcion {
	private double n;
	
	public FuncionAlumnos(double alpha){	
		this.n = alpha;
		evaluador = null;
	}
	
	public void preparar(){		
		evaluador = new EvaluadorAlumnos(n);
	}
}
