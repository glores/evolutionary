package aGeneticos.logica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Observable;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

import aGeneticos.logica.abtractas.Cruzador;
import aGeneticos.logica.abtractas.Evaluador;
import aGeneticos.logica.abtractas.Mutador;
import aGeneticos.logica.abtractas.Seleccionador;
import aGeneticos.logica.alumnos.Alumno;
import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.poblacion.Cromosoma;
import aGeneticos.logica.poblacion.GeneradorPoblaciones;
import aGeneticos.util.Aleatorio;
import aGeneticos.util.CromosomaAscendantSort;
import aGeneticos.util.CromosomaDescendantSort;


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
	
	private ListaAlumnos listaAlumnos;

	private Logger log;

	public AGenetico() {
	}

	public AGenetico(Seleccionador selec, Mutador mut, Cruzador cruz,
			Evaluador eval) {
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
		evaluador.inicioEvaluacionGlobal();
		// Generamos la población inicial
		poblacion = generadorPoblaciones.generar(listaAlumnos, evaluador);

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
	
	
	/**
	 * Cargamos los datos de los alumnos desde un fichero
	 * @param file Nombre del fichero
	 * @return Devuelve la lista de alumnos
	 */
	private static ListaAlumnos cargarDatos(String file){
		Logger log = Logger.getLogger("CP");
		int numAlumnos, numRestricciones, id, odia;
		double nota;
		ListaAlumnos lista = new ListaAlumnos();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			// Leemos la primera linea que contiene el número de alumnos y el número de restricciones
			String line =  bf.readLine();
			numAlumnos = Integer.parseInt(line.split(" ")[0]);
			numRestricciones = Integer.parseInt(line.split(" ")[1]);

			// Leemos los alumnos y sus notas
			int i = 0; 
			while ((line = bf.readLine()) != null && i < numAlumnos) {
				id = Integer.parseInt(line.split(" ")[0]);
				nota = Double.parseDouble(line.split(" ")[1]);
				lista.addAlumno(new Alumno(id, nota));
				System.out.println(line);
				i++;
			}
			
			// Leemos las incompatibilidades de cada alumno
			i = 0; 
			while ((line = bf.readLine()) != null && i < numRestricciones) {
				id = Integer.parseInt(line.split(" ")[0]);
				odia = Integer.parseInt(line.split(" ")[1]);
				lista.addIncompatibleAlumno(id, odia);
				System.out.println(line);
				i++;
			}
			
			bf.close();
		}catch (FileNotFoundException e) {
			log.severe(e.getMessage());
		} catch (IOException e) {
			log.severe(e.getMessage());
		} 
		return lista;
	}


	/*------------------- GETTERS --------------------------------*/
	
	public ArrayList<Cromosoma> getPoblacion() {
		return poblacion;
	}

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

//	public String getSolucion() {
//		return this.evaluador.getAlelos().imprime(evaluador.getMejorGlobal());
//	}

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
	
	public void setListaAlumnos(String path, int tamGrupo){
		listaAlumnos = cargarDatos(path);
		listaAlumnos.setTamGrupo(tamGrupo);
	}

}
