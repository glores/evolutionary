package aGeneticos.logica.problemas;

import aGeneticos.logica.abtractas.Funcion;
import aGeneticos.logica.problemas.evaluadores.EvaluadorTarpeian;

public class FuncionTarpeian extends Funcion {
	private int maxPasos;
	private int k;
	
	public FuncionTarpeian(int maxPasos, int k){	
		this.maxPasos = maxPasos;
		this.k=k;
	}
	
	public void preparar(){		
		evaluador = new EvaluadorTarpeian(maxPasos,k);
		
	}
}
