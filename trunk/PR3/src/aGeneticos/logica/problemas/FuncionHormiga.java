package aGeneticos.logica.problemas;

import aGeneticos.logica.abtractas.Funcion;
import aGeneticos.logica.problemas.evaluadores.EvaluadorHormiga;

public class FuncionHormiga extends Funcion {
	
	public FuncionHormiga(){	
		evaluador = null;
	}
	
	public void preparar(){		
		evaluador = new EvaluadorHormiga();
	}
}
