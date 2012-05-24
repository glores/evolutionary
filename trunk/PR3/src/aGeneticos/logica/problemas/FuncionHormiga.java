package aGeneticos.logica.problemas;

import aGeneticos.logica.abtractas.Funcion;
import aGeneticos.logica.problemas.evaluadores.EvaluadorHormiga;

public class FuncionHormiga extends Funcion {
	private int maxPasos;
	
	public FuncionHormiga(int maxPasos){	
		evaluador = null;
		this.maxPasos = maxPasos;
	}
	
	public void preparar(){		
		evaluador = new EvaluadorHormiga(maxPasos);
	}
}
