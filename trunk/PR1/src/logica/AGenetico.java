package logica;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Observable;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

import logica.esqueleto.abtractas.Cruzador;
import logica.esqueleto.abtractas.Evaluador;
import logica.esqueleto.abtractas.Mutador;
import logica.esqueleto.abtractas.Seleccionador;
import logica.esqueleto.datos.ConjuntoAlelos;
import logica.esqueleto.datos.Cromosoma;
import logica.esqueleto.datos.GeneradorPoblaciones;
import util.Aleatorio;
import util.CromosomaAscendantSort;
import util.CromosomaDescendantSort;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 * 
 *         Clase que implementa el algoritmo genético.
 */

public class AGenetico extends Observable {

	private ArrayList<Cromosoma> poblacion;
	private ArrayList<Integer> seleccionados;
	private SortedMap<Cromosoma, Integer> poblacionOrdenada;
	private SortedMap<Cromosoma, Integer> elite;
	private Cruzador cruzador;
	private Mutador mutador;
	private Seleccionador seleccionador;
	private int tamPoblacion;
	private int tamElite;
	private int numMaxGen;
	private double probCruce;
	private double probMutacion;
	private int numgeneracion;
	private GeneradorPoblaciones generadorPoblaciones;
	private Evaluador evaluador;

	private int numCruzados;
	private int numMutados;

	private Logger log;

	public AGenetico() {
	}

	public AGenetico(Seleccionador selec, Mutador mut, Cruzador cruz,
			Evaluador eval, ConjuntoAlelos alelos) {
		this.seleccionador = selec;
		this.mutador = mut;
		this.cruzador = cruz;
		this.evaluador = eval;
		numMutados = 0;
		numCruzados = 0;

	}

	public void run() {
		log = Logger.getLogger("CP");
		numgeneracion = 0;
		// Creamos la población inicial de cromosomas
		log.info("inicializando algoritmo...");
		inicializa();
		log.info("terminado");
		// Evaluamos los individuos y cogemos el mejor
		log.info("evaluando poblacion...");
		evaluarPoblacion();
		log.info("terminado");
		this.setChanged();
		this.notifyObservers("inicial");
		while (!terminado()) {
			numgeneracion++;
			log.info("generacion " + numgeneracion);
			if (tamElite > 0){
				log.info("Elite");
				seleccionaMejores();
			}
			log.info("seleccion");
			seleccion();
			log.finest("Seleción (" + seleccionados.size() + ") "
					+ seleccionados);
			log.info("reproduccion");
			reproduccion();
			log.info("mutacion");
			mutacion();
			log.info("evaluación");
			if (tamElite > 0) {
				log.info("Añadimos a la élite");
				this.incluirElite();
				// Volvemos a evaluar la población
			}
			evaluarPoblacion();
			log.finest("Poblacion (" + poblacion.size() + ") " + poblacion);
			this.setChanged();
			this.notifyObservers("generacion");
		}
		this.setChanged();
		this.notifyObservers("fin");
	}

	private void seleccionaMejores() {
		// Identificamos los mejores de la población
		Iterator<Cromosoma> iterator = poblacionOrdenada.keySet().iterator();
		Cromosoma mejorCromosoma;
		int i = 0;
		while (iterator.hasNext() && i < tamElite) {
			mejorCromosoma = iterator.next();
			// Guardamos copias para posteriormente reemplazar parte de la población por la élite
			elite.put((Cromosoma) mejorCromosoma, poblacionOrdenada.get(mejorCromosoma));
			i++;
		}
	}

	private void inicializa() {
		seleccionados = new ArrayList<Integer>();
		// Inicializamos los parámetros de evaluación
		evaluador.inicioEvaluacionLocal();
		// Generamos la población inicial
		poblacion = generadorPoblaciones.generar(tamPoblacion, evaluador);

		if (tamElite > 0)
			elite();

		numMutados = 0;
		numCruzados = 0;
	}

	private void elite() {
		// Si hay elitismo, insertamos la población de manera ordenada según
		// maximización o minimización
		Comparator<Cromosoma> comparator;
		if (evaluador.isMaximizar())
			comparator = new CromosomaDescendantSort();
		else
			comparator = new CromosomaAscendantSort();
		poblacionOrdenada = new TreeMap<Cromosoma, Integer>(comparator);
        elite = new TreeMap<Cromosoma, Integer>(comparator);

		// No guardamos clones
		for (int i = 0; i < poblacion.size(); i++) {
			poblacionOrdenada.put(poblacion.get(i), i);
		}
	}

	private void incluirElite() {
		Iterator<Cromosoma> iterator = elite.keySet().iterator();
		Cromosoma mejorCromosoma;
		int posPeorCromosoma;
		int i = 0;
		while (iterator.hasNext() && i < tamElite) {
			mejorCromosoma = iterator.next();
			posPeorCromosoma = poblacionOrdenada.get(poblacionOrdenada.lastKey());
			poblacion.set(posPeorCromosoma, (Cromosoma) mejorCromosoma.clone());			
			poblacionOrdenada.remove(poblacionOrdenada.lastKey());
			poblacionOrdenada.put(poblacion.get(posPeorCromosoma), posPeorCromosoma);
			i++;
		}
	}

	private void seleccion() {
		this.seleccionados = seleccionador.selecciona(poblacion, tamPoblacion);
	}

	private void reproduccion() {
		/*
		 * Seleccionamos los individuos que se van a cruzar mediante la
		 * probabilidad de cruce
		 */
		double prob;
		ArrayList<Integer> selec = new ArrayList<Integer>();
		for (int i = 0; i < tamPoblacion; i++) {
			prob = Aleatorio.doble();

			if (prob < probCruce) {
				selec.add(this.seleccionados.get(i));
			}
		}
		numCruzados += selec.size();
		// Si no es impar se elimina el último
		if (selec.size() % 2 == 1) {
			selec.remove(selec.size() - 1);
		}

		// Seleccionamos un punto al azar para el cruce
		Cromosoma[] cruzados;
		int puntoCruce = Aleatorio.getPuntoCruce() + 1; // Para que no empiece
														// en 0
		int i = 0;
		while (selec.size() >= 2 && i < selec.size()) {
			cruzados = cruzador.cruza(poblacion.get(selec.get(i)),
					poblacion.get(selec.get(i + 1)), puntoCruce);
			if (tamElite > 0){
				poblacionOrdenada.remove(poblacion.get(selec.get(i)));
				poblacionOrdenada.remove(poblacion.get(selec.get(i + 1)));
			}
			poblacion.set(selec.get(i), (Cromosoma)cruzados[0].clone());
			poblacion.set(selec.get(i + 1), (Cromosoma)cruzados[1].clone());
			// Hay que calcular las nuevas aptitudes
			evaluador.evaluar(poblacion.get(selec.get(i)));
			evaluador.evaluar(poblacion.get(selec.get(i + 1)));
			if (tamElite > 0){
				poblacionOrdenada.put(poblacion.get(selec.get(i)), selec.get(i));
				poblacionOrdenada.put(poblacion.get(selec.get(i+1)), selec.get(i+1));
			}

			i += 2;
		}
	}


	private void mutacion() {
		// Muta toda la población dependiendo de la probabilidad
		Cromosoma mutado = null;
		for (int i = 0; i < poblacion.size(); i++) {
			mutado = mutador.muta(poblacion.get(i), this.probMutacion);

			if (mutado != null) {
				numMutados++;
				if (tamElite > 0) poblacionOrdenada.remove(poblacion.get(i));
				poblacion.set(i, mutado);
				evaluador.evaluar(poblacion.get(i));
				if (tamElite > 0 ) poblacionOrdenada.put(mutado, i);
			}
		}

	}

	private boolean terminado() {
		return numgeneracion == numMaxGen - 1;
	}

	private void evaluarPoblacion() {
		// Guardamos la posición mejor en una auxiliar
		evaluador.evaluar(poblacion);

	}

	public ArrayList<Cromosoma> getPoblacion() {
		return poblacion;
	}

	/*------------------- GETTERS --------------------------------*/

	public int getGeneracion() {
		return numgeneracion;
	}

	public int getTamPoblacion() {
		return tamPoblacion;
	}

	public double getMejorAptitud() {
		return evaluador.getMejorGlobal().getAptitud();
	}

	public int getNumIteraciones() {
		return numMaxGen;
	}

	public double getMedia() {
		return evaluador.getMedia();
	}

	public double getMejorGeneracion() {
		return evaluador.getMejorLocal().getAptitud();
	}

	public String getSolucion() {
		return this.evaluador.getAlelos().imprime(evaluador.getMejorGlobal());
	}

	public int getNumMutados() {
		return numMutados;
	}

	public int getNumCruzados() {
		return numCruzados;
	}

	/*------------------- SETTERS --------------------------------*/

	public void setPoblacion(ArrayList<Cromosoma> poblacion) {
		this.poblacion = poblacion;
	}

	public void setCruzador(Cruzador cruzador) {
		this.cruzador = cruzador;
	}

	public void setMutador(Mutador mutador) {
		this.mutador = mutador;
	}

	public void setSeleccionador(Seleccionador seleccionador) {
		this.seleccionador = seleccionador;
	}

	public void setTamPoblacion(int tamPoblacion) {
		this.tamPoblacion = tamPoblacion;
	}

	public void setTamElite(int tamElite) {
		this.tamElite = tamElite;
	}

	public void setNumMaxGen(int numMaxGen) {
		this.numMaxGen = numMaxGen;
	}

	public void setProbCruce(double probCruce) {
		this.probCruce = probCruce;
	}

	public void setProbMutacion(double probMutacion) {
		this.probMutacion = probMutacion;
	}

	public void setGeneradorPoblaciones(
			GeneradorPoblaciones generadorPoblaciones) {
		this.generadorPoblaciones = generadorPoblaciones;
	}

	public void setEvaluador(Evaluador evaluador) {
		this.evaluador = evaluador;
	}

}
