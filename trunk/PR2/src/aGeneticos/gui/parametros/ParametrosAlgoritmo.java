package aGeneticos.gui.parametros;

import java.util.logging.Logger;

import aGeneticos.logica.abtractas.Cruzador;
import aGeneticos.logica.abtractas.Funcion;
import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.abtractas.Seleccionador;
import aGeneticos.logica.cruzadores.CruzadorOXEstandar;
import aGeneticos.logica.cruzadores.CruzadorOXPosPrioritarias;
import aGeneticos.logica.cruzadores.CruzadorOrdinal;
import aGeneticos.logica.cruzadores.CruzadorPMX;
import aGeneticos.logica.mutadores.MutadorHeuristica;
import aGeneticos.logica.mutadores.MutadorInsercion;
import aGeneticos.logica.mutadores.MutadorIntercambio;
import aGeneticos.logica.mutadores.MutadorInversion;
import aGeneticos.logica.poblacion.GeneradorPoblaciones;
import aGeneticos.logica.problemas.FuncionAlumnos;
import aGeneticos.logica.seleccionadores.Ranking;
import aGeneticos.logica.seleccionadores.Ruleta;
import aGeneticos.logica.seleccionadores.TorneoDeterminista;
import aGeneticos.logica.seleccionadores.TorneoProbabilista;


/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que recoge los parámetros configurables del algoritmo. La vista se
 * encargará de construir objetos de esta clase con los datos oportunos. El
 * controlador lo consultará. Esta clase construye todo los objetos
 * configurables del algoritmo.
 * 
 * Importante: fijar tolerancia antes de problema y problema antes de generador
 */

public class ParametrosAlgoritmo {
	//Parámetros por defecto
	public final static int PARAMS_TAMPOBLACION = 100;
	public final static int PARAMS_LIMITERACIONES = 30;
	public final static double PARAMS_PROBCRUCE = 0.6;
	public final static double PARAMS_INT_PROBCRUCE_A = 0.0;
	public final static double PARAMS_INT_PROBCRUCE_B = 0.5;
	public final static double PARAMS_INT_PROBCRUCE_INC = 0.1;
	public final static double PARAMS_PROBMUTACION = 0.2;
	public final static boolean PARAMS_ELITISMO = Boolean.FALSE; 
	public final static int PARAMS_TAMELITE = 0;
	public final static ModoSeleccionador PARAMS_SELECCIONADOR = ModoSeleccionador.RULETA;
	public final static ModoCruzador PARAMS_CRUZADOR = ModoCruzador.SIMPLE;
	public final static ModoMutador PARAMS_MUTADOR = ModoMutador.INVERSION;
	public final static ModoGenerador PARAMS_GENERADOR = ModoGenerador.ALEATORIO;
	public final static Problema PARAMS_PROBLEMA = Problema.FUNCION_1;
	public final static int PARAMS_N = 2;

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

	// Parte obligatoria, parámetros del algoritmo
	/**
	 * Tamaño de la población
	 */
	private int tamPoblacion;
	/**
	 * Límite de iteraciones.
	 */
	private int limIteraciones;
	/**
	 * Probabilidad de cruce de dos indivíduos.
	 */
	private double probabilidadCruce;
	/**
	 * Probabilidad de mutación de un bit.
	 */
	private double probabilidadMutacion;

	// Parte opcional, parámetros del algoritmo
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
	 * Parámetro para la función de evaluación y para la selección por ranking
	 */
	private double alpha, beta;
	
	/**
	 * Parámetro para la selección por torneo
	 */
	private int tamTorneo;
	private double probTorneoProbabilista;
	private int tamGrupo;

	Logger log;
	
	private double intProbCruce_a;
	private double intProbCruce_b;
	private double intProbCruce_inc;
	private boolean intProbCruce_habilitado;
	private String path;
	

	/**
	 * Constructora por defecto. Establece los parámetros por defecto.
	 */
	public ParametrosAlgoritmo() {
		log = Logger.getLogger("CP");
		setTamPoblacion(PARAMS_TAMPOBLACION);
		setLimIteraciones(PARAMS_LIMITERACIONES);
		setProbabilidadCruce(PARAMS_PROBCRUCE);
		setProbabilidadMutacion(PARAMS_PROBMUTACION);
		setElitismo(PARAMS_ELITISMO);
		setTamElite(PARAMS_TAMELITE);
		
		setSeleccionador(PARAMS_SELECCIONADOR);
		setCruzador(PARAMS_CRUZADOR);
		setMutador(PARAMS_MUTADOR);
		setGeneradorPoblaciones(PARAMS_GENERADOR);
		setProblema(PARAMS_PROBLEMA);
		setAlpha(PARAMS_N);
		}
	


	public void setSeleccionador(ModoSeleccionador seleccionador) {
		modoSeleccionador = seleccionador;
	}

	/**
	 * Traduce el modo de seleccionador en su representación en texto
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
		case INVERSION:
			resultado = "Inversión";
			break;
		case INTERCAMBIO:
			resultado = "Intercambio";
			break;
		case INSERCION:
			resultado = "Inserción";
			break;
		case HEURISTICA:
			resultado = "Heurística";
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
		case INVERSION:
			resultado = new MutadorInversion();
			break;			
		case INTERCAMBIO:
			resultado = new MutadorIntercambio();
			break;
		case INSERCION:
			resultado = new MutadorInsercion();
			break;
		case HEURISTICA:
			resultado = new MutadorHeuristica();
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
		String resultado;
		switch (cruzador) {
		case SIMPLE:
			resultado = "Ordinal";
			break;
		case OX:
			resultado = "OX";
			break;
		case VARIANTE_OX:
			resultado = "OX posiciones prioritarias";
			break;
		case PMX:
			resultado = "PMX";
			break;
		default:
			resultado = "";
			break;
		}
		return resultado;
	}

	public Cruzador getCruzador() {
		Cruzador resultado;
		switch (modoCruzador) {
		case SIMPLE:
			resultado = new CruzadorOrdinal();
			break;
		case OX:
			resultado = new CruzadorOXEstandar();
			break;
		case VARIANTE_OX:
			resultado = new CruzadorOXPosPrioritarias();
			break;
		case PMX:
			resultado = new CruzadorPMX();
			break;
		default:
			resultado = null;
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
		case FUNCION_1:
			resultado = "Función 1";
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
		case FUNCION_1:
			resultado = new FuncionAlumnos(alpha);
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
	
	/*----------- Tamaño de la población ------------------*/

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
	
	/*----------- Generaciones de la población ------------------*/

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
	
	/*----------- Probabilidad de mutación ------------------*/

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
	

	
	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double n) {
		this.alpha = n;
	}

	public boolean setAlpha(String n) {
		try {
			this.alpha = Double.parseDouble(n);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + n + " no es un entero.");
			return false;
		}
	}
	
	/*----------- Tamaño para el torneo ------------------*/
	
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
	
	/*-------------- Tamaño grupo alumnos -----------------------------------*/
	
	public int getTamGrupo() {
		return tamGrupo;
	}

	public void setTamGrupo(int tam) {
		this.tamGrupo = tam;
	}

	public boolean setTamGrupo(String tam) {
		try {
			this.tamGrupo = Integer.parseInt(tam);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + tam + " no es un entero.");
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
	
	/*-------------- Parámetro beta para la selección por Ranking -------------------------*/
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
			log.warning("[PARAM] " + n + " no es un entero.");
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
		return mensaje;
	}
}
