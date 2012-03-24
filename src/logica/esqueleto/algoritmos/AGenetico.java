package logica.esqueleto.algoritmos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.logging.Logger;

import logica.esqueleto.algoritmos.abtractas.Cruzador;
import logica.esqueleto.algoritmos.abtractas.Evaluador;
import logica.esqueleto.algoritmos.abtractas.Mutador;
import logica.esqueleto.algoritmos.abtractas.Seleccionador;
import logica.esqueleto.datos.ConjuntoAlelos;
import logica.esqueleto.datos.Cromosoma;
import logica.esqueleto.datos.GeneradorPoblaciones;
import util.Aleatorio;
import util.CromosomaSort;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa el algoritmo genético.
 */

public class AGenetico extends Observable {

	private ArrayList<Cromosoma> poblacion;
	private ArrayList<Integer> seleccionados;
	private ArrayList<Cromosoma> elite;
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

	public AGenetico() {}

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
			if (tamElite > 0) {
				log.info("elite");
				elitismo();
			}
			log.info("seleccion");
			seleccion();
			log.finest("Seleción (" + seleccionados.size() + ") " + seleccionados);
			log.info("reproduccion");
			reproduccion();
			log.info("mutacion");
			mutacion();
			log.info("evaluación");
			evaluarPoblacion();
			if (tamElite > 0){
				log.info("Añadimos a la élite");
				this.incluirElite();
				// Volvemos a evaluar la población
				evaluarPoblacion();
			}
			log.finest("Poblacion (" + poblacion.size() + ") " + poblacion);
			this.setChanged();
			this.notifyObservers("generacion");
		}
		this.setChanged();
		this.notifyObservers("fin");
	}

	private void incluirElite() {
		// Para cada individuo de la élite se reemplaza por otro con peor aptitud
		for (int i = 0; i < elite.size(); i++){
			boolean ok = false; int j = 0;
			while (!ok && j < poblacion.size()){
				ok = elite.get(i).getAptitud() > poblacion.get(j).getAptitud();
				if (ok){
					this.reemplazar(elite.get(i), j);
				}
				j++;
			}
		}
	}

	private void elitismo() {
		// Seleccionar los tamElite mejores y guardar clones en el array
		seleccionarMejores();
	}

	private void seleccionarMejores() {
		elite = new ArrayList<Cromosoma>();

		for (int i = 0; i < tamElite; i++) {
			elite.add((Cromosoma) poblacion.get(i).clone());
			log.info("[ELITE] " + elite);
		}
		// Ordenamos la población según su aptitud de mayor a menor
		Collections.sort(elite, new CromosomaSort());
	}

	private void seleccion() {
		this.seleccionados = seleccionador.selecciona(poblacion, tamPoblacion);
	}

	private void reproduccion() {
		/* Seleccionamos los individuos que se van a cruzar mediante la
		 probabilidad de cruce */
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
		int puntoCruce = Aleatorio.getPuntoCruce() + 1; // Para que no empiece en 0
		int i = 0;
		while (selec.size() >= 2 && i < selec.size()) {
			cruzados = cruzador.cruza(poblacion.get(selec.get(i)), poblacion.get(selec.get(i + 1)), puntoCruce);
			reemplazar(cruzados[0], selec.get(i));
			reemplazar(cruzados[1], selec.get(i + 1));
			// Hay que calcular las nuevas aptitudes
			evaluador.evaluar(poblacion);
			i += 2;
		}
	}

	private void reemplazar(Cromosoma sustituto, int original) {
		// No es necesario hacer set de todos los campos, porque el numBits es
		// el mismo y las aptitudes y demás se calculan al evaluar
		log.info("[Reemplazo] " + poblacion.get(original) + " por " + sustituto);
		poblacion.get(original).setCadena(sustituto.getCadena());
	}

	private void mutacion() {
		// Muta toda la población dependiendo de la probabilidad
		boolean mutado = false;
		for (int i = 0; i < poblacion.size(); i++) {
			mutado = mutador.muta(poblacion.get(i), this.probMutacion) || mutado;
			
			if (mutado) {
				numMutados++;
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

	private void inicializa() {
		seleccionados = new ArrayList<Integer>();
		poblacion = generadorPoblaciones.generar(tamPoblacion);
		numMutados = 0;
		numCruzados = 0;
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

	public int getNumMutados(){
		return numMutados;
	}
	
	public int getNumCruzados(){
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
