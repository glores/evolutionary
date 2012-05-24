package aGeneticos.gui.parametros;

import java.util.logging.Logger;

import aGeneticos.logica.abtractas.Cruzador;
import aGeneticos.logica.abtractas.Funcion;
import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.abtractas.Seleccionador;
import aGeneticos.logica.cruzadores.CruzadorSimple;
import aGeneticos.logica.mutadores.MutadorDeArbol;
import aGeneticos.logica.mutadores.MutadorFuncionalSimple;
import aGeneticos.logica.mutadores.MutadorTerminalSimple;
import aGeneticos.logica.poblacion.GeneradorPoblaciones;
import aGeneticos.logica.problemas.FuncionHPenalizaGrandes;
import aGeneticos.logica.problemas.FuncionHormiga;
import aGeneticos.logica.seleccionadores.Ranking;
import aGeneticos.logica.seleccionadores.Ruleta;
import aGeneticos.logica.seleccionadores.SeleccionadorPropio;
import aGeneticos.logica.seleccionadores.TorneoDeterminista;
import aGeneticos.logica.seleccionadores.TorneoProbabilista;


/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 *
 * Clase que recoge los par�metros configurables del algoritmo. La vista se
 * encargar� de construir objetos de esta clase con los datos oportunos. El
 * controlador lo consultar�. Esta clase construye todo los objetos
 * configurables del algoritmo.
 * 
 * Importante: fijar tolerancia antes de problema y problema antes de generador
 */

public class ParametrosAlgoritmo {
	//Par�metros por defecto
	public final static int PARAMS_TAMPOBLACION = 100;
	public final static int PARAMS_LIMITERACIONES = 100;
	public final static double PARAMS_PROBCRUCE = 0.6;
	public final static double PARAMS_INT_PROBCRUCE_A = 0.0;
	public final static double PARAMS_INT_PROBCRUCE_B = 0.5;
	public final static double PARAMS_INT_PROBCRUCE_INC = 0.1;
	public final static double PARAMS_PROBMUTACION = 0.3;
	public final static boolean PARAMS_ELITISMO = Boolean.FALSE; 
	public final static int PARAMS_TAMELITE = 0;
	public final static int PARAMS_PROF_ARBOL = 3;
	public final static int PARAMS_PROF_MIN_ARBOL = 2;
	public final static ModoSeleccionador PARAMS_SELECCIONADOR = ModoSeleccionador.TORNEO_PROB;
	public final static ModoCruzador PARAMS_CRUZADOR = ModoCruzador.SIMPLE;
	public final static ModoMutador PARAMS_MUTADOR = ModoMutador.DE_ARBOL;
	public final static ModoGenerador PARAMS_GENERADOR = ModoGenerador.ALEATORIO;
	public final static Problema PARAMS_PROBLEMA = Problema.BOCADOS;
	public final static int PARAMS_N = 2;
	public final static int NUMPASOS = 400;
	

	/**
	 * Seleccionador
	 */
	private ModoSeleccionador modoSeleccionador;
	/**
	 * Cruzador
	 */
	private ModoCruzador modoCruzador;
	/**
	 * Mutador
	 */
	private ModoMutador modoMutador;

	// Parte obligatoria, par�metros del algoritmo
	/**
	 * Tama�o de la poblaci�n
	 */
	private int tamPoblacion;
	/**
	 * L�mite de iteraciones.
	 */
	private int limIteraciones;
	/**
	 * Profundidad del �rbol.
	 */
	private int profArbol;	
	/**
	 * Profundidad m�nima del �rbol
	 */
	private int profMinArbol;
	/**
	 * Probabilidad de cruce de dos indiv�duos.
	 */
	private double probabilidadCruce;
	/**
	 * Probabilidad de mutaci�n de un bit.
	 */
	private double probabilidadMutacion;

	// Parte opcional, par�metros del algoritmo
	/**
	 * Utilizar elitismo
	 */
	boolean elitismo;

	private int tamElite;
	private double porcentajeElite;
	/**
	 * Problema a resolver
	 */
	Problema problema;

	/**
	 * Generador de poblaciones
	 */
	ModoGenerador generador;
	/**
	 * Par�metro para la funci�n de evaluaci�n y para la selecci�n por ranking
	 */
	private double beta, paramPropio;
	
	/**
	 * Par�metro para la selecci�n por torneo
	 */
	private int tamTorneo;
	private double probTorneoProbabilista;

	Logger log;
	
	private double intProbCruce_a;
	private double intProbCruce_b;
	private double intProbCruce_inc;
	private boolean intProbCruce_habilitado;
	private String path;
	private int numRepeticiones = -1;
	private int maxPasos;
	// Par�metros para la funci�n de HP
	private double k;
	private int maxProfEst;
	

	/**
	 * Constructora por defecto. Establece los par�metros por defecto.
	 */
	public ParametrosAlgoritmo() {
		log = Logger.getLogger("CP");
		setTamPoblacion(PARAMS_TAMPOBLACION);
		setLimIteraciones(PARAMS_LIMITERACIONES);
		setProbabilidadCruce(PARAMS_PROBCRUCE);
		setProbabilidadMutacion(PARAMS_PROBMUTACION);
		setElitismo(PARAMS_ELITISMO);
		setTamElite(PARAMS_TAMELITE);
		setProfArbol(PARAMS_PROF_ARBOL);
		setProfMinArbol(PARAMS_PROF_MIN_ARBOL);
		
		setSeleccionador(PARAMS_SELECCIONADOR);
		setCruzador(PARAMS_CRUZADOR);
		setMutador(PARAMS_MUTADOR);
		setGeneradorPoblaciones(PARAMS_GENERADOR);
		setProblema(PARAMS_PROBLEMA);
	}
	


	public void setSeleccionador(ModoSeleccionador seleccionador) {
		modoSeleccionador = seleccionador;
	}

	/**
	 * Traduce el modo de seleccionador en su representaci�n en texto
	 * 
	 * @return
	 */
	public static String getTextoSeleccionador(ModoSeleccionador seleccionador) {
		String resultado;
		switch (seleccionador) {
		case RULETA:
			resultado = "Ruleta";
			break;
		case TORNEO_DET:
			resultado = "Torneo Determinista";
			break;
		case TORNEO_PROB:
			resultado = "Torneo Probabilista";
			break;
		case RANKING:
			resultado = "Ranking";
			break;
		case PROPIO:
			resultado = "Propio";
			break;			
		default:
			resultado = "";
			break;
		}
		return resultado;
	}

	/**
	 * Traduce el modo de seleccionador en un objeto Seleccionador
	 * 
	 * @return
	 */
	public Seleccionador getSeleccionador() {
		Seleccionador resultado;
		switch (this.modoSeleccionador) {
		case RULETA:
			resultado = new Ruleta();
			break;
		case TORNEO_DET:
			resultado = new TorneoDeterminista(tamTorneo);
			break;
		case TORNEO_PROB:
			resultado = new TorneoProbabilista(tamTorneo, probTorneoProbabilista);
			break;
		case RANKING:
			resultado = new Ranking(beta);
			break;
		case PROPIO:
			//TODO el maximizar cogerlo del evaluador de alguna manera...
			resultado = new SeleccionadorPropio(paramPropio,false);			
			break;
		default:
			resultado = null;
			break;
		}
		return resultado;
	}

	public void setMutador(ModoMutador mutador) {
		modoMutador = mutador;
	}

	public static String getTextoMutador(ModoMutador mutador) {
		String resultado;
		switch (mutador) {
		case TERMINAL_SIMPLE:
			resultado = "Terminal simple";
			break;		
		case FUNCIONAL_SIMPLE:
			resultado = "Funcional simple";
			break;
		case DE_ARBOL:
			resultado = "De �rbol";
			break;
		default:
			resultado = "";
			break;
		}
		return resultado;
	}

	public Mutador getMutador() {
		Mutador resultado;
		switch (modoMutador) {
		case TERMINAL_SIMPLE:
			resultado = new MutadorTerminalSimple();
			break;	
		case FUNCIONAL_SIMPLE:
			resultado = new MutadorFuncionalSimple();
			break;
		case DE_ARBOL:
			resultado = new MutadorDeArbol(this.profArbol);
			break;
		default:
			resultado = null;
			break;
		}
		return resultado;
	}

	public void setCruzador(ModoCruzador cruzador) {
		modoCruzador = cruzador;
	}

	public static String getTextoCruzador(ModoCruzador cruzador) {
		String resultado = null;
		switch (cruzador) {
		case SIMPLE:
			resultado = "Simple";
			break;
		}
		return resultado;
	}

	public Cruzador getCruzador() {
		Cruzador resultado = null;
		switch (modoCruzador) {
		case SIMPLE:
			resultado = new CruzadorSimple();
			break;
		}
		return resultado;
	}

	public void setProblema(Problema problema) {
		this.problema = problema;		
	}

	public static String getTextoProblema(Problema problema) {
		String resultado;
		switch (problema) {
		case BOCADOS:
			resultado = "Bocados";
			break;
		case PENALIZA_GRANDES:
			resultado = "Penaliza grandes";
			break;
		default:
			resultado = "";
			break;
		}
		return resultado;
	}

	public Funcion getProblema() {
		Funcion resultado;
		switch (problema) {
		case BOCADOS:
			resultado = new FuncionHormiga(maxPasos);
			break;
		case PENALIZA_GRANDES:
			//resultado = new FuncionHPenalizaGrandes(this.profMinArbol,this.profArbol);
			resultado = new FuncionHPenalizaGrandes(profMinArbol,maxProfEst, maxPasos, k);
			break;
		default:
			resultado = null;
			break;
		}
		if(resultado!=null){
			resultado.preparar();
		}
		return resultado;
	}

	public void setGeneradorPoblaciones(ModoGenerador generador) {
		this.generador = generador;
	}

	public static String getTextoGeneradorPoblaciones(ModoGenerador generador) {
		String resultado;
		switch (generador) {
		case ALEATORIO:
			resultado = "Aleatorio";
			break;
		default:
			resultado = "";
			break;
		}
		return resultado;
	}

	/**
	 * Debe estar fijado antes el problema porque se crea con los alelos
	 * 
	 * @return
	 */
	public GeneradorPoblaciones getGeneradorPoblaciones() {
		GeneradorPoblaciones resultado;
		switch (generador) {
		case ALEATORIO:
			resultado = new GeneradorPoblaciones();
			break;
		default:
			resultado = null;
			break;
		}
		return resultado;
	}
	
	/*----------- Tama�o de la poblaci�n ------------------*/

	public int getTamPoblacion() {
		return tamPoblacion;
	}

	public void setTamPoblacion(int tamPoblacion) {
		this.tamPoblacion = tamPoblacion;
	}

	public boolean setTamPoblacion(String tamPoblacion) {
		try {
			this.tamPoblacion = Integer.parseInt(tamPoblacion);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + tamPoblacion + " no es un entero.");
			return false;
		}
	}
	
	/*----------- Generaciones de la poblaci�n ------------------*/

	public int getLimIteraciones() {
		return limIteraciones;
	}

	public void setLimIteraciones(int limIteraciones) {
		this.limIteraciones = limIteraciones;
	}

	public boolean setLimIteraciones(String limIteraciones) {
		try {
			this.limIteraciones = Integer.parseInt(limIteraciones);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + limIteraciones + " no es un entero.");
			return false;
		}
	}
	
	/*----------- Probabilidad Cruce ------------------*/

	public double getProbabilidadCruce() {
		return probabilidadCruce;
	}

	public void setProbabilidadCruce(double probabilidadCruce) {
		this.probabilidadCruce = probabilidadCruce;
	}

	public boolean setProbabilidadCruce(String probabilidadCruce) {
		try {
			this.probabilidadCruce = Double.parseDouble(probabilidadCruce);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + probabilidadCruce + " no es un real.");
			return false;
		}
	}
	
	/*----------- Probabilidad de mutaci�n ------------------*/

	public double getProbabilidadMutacion() {
		return probabilidadMutacion;
	}

	public void setProbabilidadMutacion(double probabilidadMutacion) {
		this.probabilidadMutacion = probabilidadMutacion;
	}

	public boolean setProbabilidadMutacion(String probabilidadMutacion) {
		try {
			this.probabilidadMutacion = Double
					.parseDouble(probabilidadMutacion);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + probabilidadMutacion + " no es un real.");
			return false;
		}
	}
	
	/*----------- ELITISMO ------------------*/

	public boolean conElitismo() {
		return elitismo;
	}

	public void setElitismo(boolean elitismo) {
		this.elitismo = elitismo;
	}

	public int getTamElite() {
		return tamElite;
	}

	public void setTamElite(double porcentaje) {
		this.tamElite = Math.round((float)(porcentaje/100)*tamPoblacion);
	}
	

	public boolean setPorcentajeElite(String porcentaje) {
		try {
			this.porcentajeElite = Double.parseDouble(porcentaje);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + porcentajeElite + " no es un real.");
			return false;
		}
	}
	
	/*----------- Tama�o para el torneo ------------------*/
	
	public int getTamTorneo() {
		return tamTorneo;
	}

	public void setTamTorneo(int tam) {
		this.tamTorneo = tam;
	}

	public boolean setTamTorneo(String tam) {
		try {
			this.tamTorneo = Integer.parseInt(tam);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + tam + " no es un entero.");
			return false;
		}
	}

	
	public double getIntProbCruce_a() {
		return intProbCruce_a;
	}

	public void setIntProbCruce_a(double tam) {
		this.intProbCruce_a = tam;
	}

	public boolean setIntProbCruce_a(String tam) {
		try {
			this.intProbCruce_a = Double.parseDouble(tam);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + tam + " no es un real.");
			return false;
		}
	}
	public double getIntProbCruce_b() {
		return intProbCruce_b;
	}

	public void setIntProbCruce_b(double tam) {
		this.intProbCruce_b = tam;
	}

	public boolean setIntProbCruce_b(String tam) {
		try {
			this.intProbCruce_b = Double.parseDouble(tam);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + tam + " no es un real.");
			return false;
		}
	}
	public double getIntProbCruce_inc() {
		return intProbCruce_inc;
	}

	public void setIntProbCruce_inc(double tam) {
		this.intProbCruce_inc = tam;
	}

	public boolean setIntProbCruce_inc(String tam) {
		try {
			this.intProbCruce_inc = Double.parseDouble(tam);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + tam + " no es un real.");
			return false;
		}
	}
	public boolean getIntProbCruce_habilitado() {
		return intProbCruce_habilitado;
	}

	public boolean setIntProbCruce_habilitado(boolean hab) {
		if(hab){
			if(this.intProbCruce_a<this.intProbCruce_b){
				this.intProbCruce_habilitado=true;
				return true;
			}else{
				return false;
			}
		}else{
			this.intProbCruce_habilitado = false;
			return true;
		}
		
	}
	
	/*----------- Probabilidad para el torneo probabilista ------------------*/
	
	public double getProbTorneoProbabilista() {
		return probTorneoProbabilista;
	}

	public void setProbTorneoProbabilista(double prob) {
		this.probTorneoProbabilista = prob;
	}

	public boolean setProbTorneoProbabilista(String prob) {
		try {
			this.probTorneoProbabilista = Double.parseDouble(prob);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + prob + " no es un real.");
			return false;
		}
	}
	
	/*-------------- Ruta del archivo lista alumnos -----------------------------------*/
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	/*-------------- Par�metro beta para la selecci�n por Ranking -------------------------*/
	public double getBeta() {
		return beta;
	}

	public void setBeta(double n) {
		this.beta = n;
	}

	public boolean setBeta(String n) {
		try {
			this.beta = Double.parseDouble(n);
			if (beta >= 1 && beta <= 2)
				return true;
			else return false;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + n + " no es un real.");
			return false;
		}
	}
	
	/*-------------- Par�metro beta para la selecci�n propio -------------------------*/
	public double getParamPropio() {
		return paramPropio;
	}

	public void setParamPropio(double param) {
		this.paramPropio = param;
	}

	public boolean setParamPropio(String param) {
		try {
			this.paramPropio = Double.parseDouble(param);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + param + " no es un real.");
			return false;
		}
	}
	
	/*-------------- N�mero de repeticiones Cruce propio (heur�stico) -----------------------------------*/
	
	public int getNumRepeticiones() {
		return numRepeticiones;
	}

	public void setNumRepeticiones(int tam) {
		this.numRepeticiones = tam;
	}

	public boolean setNumRepeticiones(String tam) {
		try {
			this.numRepeticiones = Integer.parseInt(tam);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + tam + " no es un entero.");
			return false;
		}
	}

	
	/*----------- Profundidad del �rbol ------------------*/

	public int getProfArbol() {
		return profArbol;
	}

	public void setProfArbol(int prof) {
		this.profArbol = prof;
	}

	public boolean setProfArbol(String prof) {
		try {
			this.profArbol = Integer.parseInt(prof);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + prof + " no es un entero.");
			return false;
		}
	}
	
	/*----------- Profundidad m�nima del �rbol ------------------*/

	public int getProfMinArbol() {
		return profMinArbol;
	}

	public void setProfMinArbol(int prof) {
		this.profMinArbol = prof;
	}

	public boolean setProfMinArbol(String prof) {
		try {
			this.profMinArbol = Integer.parseInt(prof);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + prof + " no es un entero.");
			return false;
		}
	}
	
	/*----------- N�mero de pasos m�ximo ------------------*/
	public boolean setMaxPasos(String pasos) {
		try {
			this.maxPasos = Integer.parseInt(pasos);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + pasos + " no es un entero.");
			return false;
		} 
	}
	/*----------- Par�metro k para la funci�n HP ------------------*/
	public boolean setK(String k) {
		try {
			this.k = Double.parseDouble(k);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + k + " no es un real.");
			return false;
		} 
	}	
	/*----------- Par�metro profundidad m�x estimada ------------------*/
	public boolean setProfMaxEst(String prof) {
		try {
			this.maxProfEst = Integer.parseInt(prof);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + prof + " no es un entero.");
			return false;
		} 
	}
	

	
	
	public String toString(){
		String mensaje="";
		mensaje+="tamPoblacion: "+tamPoblacion+", ";
		mensaje+="limIteraciones: "+limIteraciones+", ";
		mensaje+="probabilidadCruce: "+probabilidadCruce+", ";
		mensaje+="probabilidadMutacion: "+probabilidadMutacion+", ";
		mensaje+="elitismo: "+elitismo+", ";
		mensaje+="tamElite: "+tamElite+", ";
		mensaje+="modoSeleccionador: "+modoSeleccionador+", ";
		mensaje+="modoCruzador: "+modoCruzador+", ";
		mensaje+="modoMutador: "+modoMutador+", ";
		mensaje+="generador: "+generador+", ";
		mensaje+="problema: "+problema+". ";
		mensaje+="profundidad: "+profArbol+". ";
		return mensaje;
	}
}
