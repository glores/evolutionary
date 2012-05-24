package aGeneticos.logica.problemas;

import aGeneticos.logica.abtractas.Funcion;
import aGeneticos.logica.problemas.evaluadores.EvaluadorHPenalizaGrandes;

public class FuncionHPenalizaGrandes extends Funcion {
	private int minProf;
	private int maxProf;
	private int maxPasos;
	private double k;
	
	public FuncionHPenalizaGrandes(int minProf, int maxProf, int maxPasos, double k){	
		evaluador = null;
		this.minProf=minProf;
		this.maxProf=maxProf;
		this.maxPasos = maxPasos;
		this.k = k;
	}
	
	public void preparar(){		
		evaluador = new EvaluadorHPenalizaGrandes(minProf,maxProf, maxPasos, k);
		
	}
}
