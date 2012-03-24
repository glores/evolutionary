package parametros;

import java.util.logging.Logger;

import problemas.*;
import logica.cruzadores.*;
import logica.esqueleto.algoritmos.abtractas.*;
import logica.esqueleto.datos.*;
import logica.mutadores.*;
import logica.seleccionadores.*;

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
	public final static int PARAMS_LIMITERACIONES = 20;
	public final static double PARAMS_PROBCRUCE = 0.5;
	public final static double PARAMS_PROBMUTACION = 0.1;
	public final static double PARAMS_TOLERANCIA = 0.00001;
	public final static boolean PARAMS_ELITISMO = Boolean.FALSE; 
	public final static int PARAMS_TAMELITE = 0;
	public final static ModoSeleccionador PARAMS_SELECCIONADOR = ModoSeleccionador.RULETA;
	public final static ModoCruzador PARAMS_CRUZADOR = ModoCruzador.SIMPLE;
	public final static ModoMutador PARAMS_MUTADOR = ModoMutador.SIMPLE;
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
	/**
	 * Tolerancia.
	 */
	private double tolerancia;

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
	 * Parámetro para la función 5
	 */
	private int n;
	
	/**
	 * Parámetro para la selección por torneo
	 */
	private int tamTorneo;

	Logger log;

	// TODO variar un parámetro en un rango

	/**
	 * Constructora por defecto. Establece los parámetros por defecto.
	 */
	public ParametrosAlgoritmo() {
		log = Logger.getLogger("CP");
		setTamPoblacion(PARAMS_TAMPOBLACION);
		setLimIteraciones(PARAMS_LIMITERACIONES);
		setProbabilidadCruce(PARAMS_PROBCRUCE);
		setProbabilidadMutacion(PARAMS_PROBMUTACION);
		setTolerancia(PARAMS_TOLERANCIA);
		setElitismo(PARAMS_ELITISMO);
		setTamElite(PARAMS_TAMELITE);
		
		setSeleccionador(PARAMS_SELECCIONADOR);
		setCruzador(PARAMS_CRUZADOR);
		setMutador(PARAMS_MUTADOR);
		setGeneradorPoblaciones(PARAMS_GENERADOR);
		setProblema(PARAMS_PROBLEMA);
		setN(PARAMS_N);
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
		case TORNEO:
			resultado = "Torneo";
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
		case TORNEO:
			resultado = new Torneo(tamTorneo);
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
		case SIMPLE:
			resultado = "Simple";
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
		case SIMPLE:
			resultado = new MutadorSimple();
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
			resultado = "Simple";
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
			resultado = new CruzadorSimple();
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
		case FUNCION_2:
			resultado = "Función 2";
			break;
		case FUNCION_3:
			resultado = "Función 3";
			break;
		case FUNCION_4:
			resultado = "Función 4";
			break;
		case FUNCION_5:
			resultado = "Función 5";
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
			resultado = new Funcion1();
			break;
		case FUNCION_2:
			resultado = new Funcion2();
			break;
		case FUNCION_3:
			resultado = new Funcion3();
			break;
		case FUNCION_4:
			resultado = new Funcion4();
			break;
		case FUNCION_5:
			resultado = new Funcion5(n);
			break;
		default:
			resultado = null;
			break;
		}
		if(resultado!=null){
			resultado.preparar(tolerancia);
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
			resultado = new GeneradorPoblaciones(getProblema().getAlelos());
			break;
		default:
			resultado = null;
			break;
		}
		return resultado;
	}

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

	public double getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(double tolerancia) {
		this.tolerancia = tolerancia;
	}

	public boolean setTolerancia(String tolerancia) {
		try {
			this.tolerancia = Double.parseDouble(tolerancia);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + tolerancia + " no es un real.");
			return false;
		}
	}

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
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public boolean setN(String n) {
		try {
			this.n = Integer.parseInt(n);
			return true;
		} catch (NumberFormatException e) {
			log.warning("[PARAM] " + n + " no es un entero.");
			return false;
		}
	}
	
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
	
	
	public String toString(){
		String mensaje="";
		mensaje+="tamPoblacion: "+tamPoblacion+", ";
		mensaje+="limIteraciones: "+limIteraciones+", ";
		mensaje+="probabilidadCruce: "+probabilidadCruce+", ";
		mensaje+="probabilidadMutacion: "+probabilidadMutacion+", ";
		mensaje+="tolerancia: "+tolerancia+", ";
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
