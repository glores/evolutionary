package aGeneticos.logica.problemas;

import aGeneticos.logica.abtractas.Funcion;
import aGeneticos.logica.problemas.evaluadores.EvaluadorHPenalizaGrandes;

public class FuncionHPenalizaGrandes extends Funcion {
	private int minProf;
	private int maxProf;
	public FuncionHPenalizaGrandes(int minProf, int maxProf){	
		evaluador = null;
		this.minProf=minProf;
		this.maxProf=maxProf;
	}
	
	public void preparar(){		
		evaluador = new EvaluadorHPenalizaGrandes(minProf,maxProf);
		
	}
}
