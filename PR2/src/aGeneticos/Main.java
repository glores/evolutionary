package aGeneticos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import aGeneticos.controlador.Controlador;
import aGeneticos.gui.GUI;
import aGeneticos.logica.AGenetico;
import aGeneticos.logica.alumnos.Alumno;
import aGeneticos.logica.alumnos.ListaAlumnos;


/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase principal que contiene el main y la creación del log.
 */

public class Main {
	private static final String  MAIN_LANDF="de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel";
	//private static final String  MAIN_LANDF="de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel";
	//private static final String  MAIN_LANDF="de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel";
	public static void main(String[] args) {
		
		// configuracion de formato de salida de los logs
		Logger log = Logger.getLogger("");
		for (Handler h : log.getHandlers())
			log.removeHandler(h);
		log = crearLog("CP");

		// Instanciación de modelo, vista y controlador
		Controlador controlador = Controlador.getInstance();
		final AGenetico algoritmo = new AGenetico();
		controlador.setAGenetico(algoritmo);
		ListaAlumnos listaAlumnos = cargarDatos("archivos/3.txt");
		
		try {
			UIManager.setLookAndFeel(MAIN_LANDF);
			GUI gui = new GUI();
			algoritmo.addObserver(gui);
		} catch (ClassNotFoundException e) {
			log.severe(e.getMessage());
		} catch (InstantiationException e) {
			log.severe(e.getMessage());
		} catch (IllegalAccessException e) {
			log.severe(e.getMessage());
		} catch (UnsupportedLookAndFeelException e) {
			log.severe(e.getMessage());
		}
	}

	private static Logger crearLog(String nombre) {
		Logger log;
		log = Logger.getLogger(nombre);
		log.addHandler(new ConsoleHandler());
		log.getHandlers()[0].setFormatter(new SimpleFormatter() {
			DecimalFormat fmt = new DecimalFormat("000");
			long previousMillis = System.currentTimeMillis();
			int stanza = 0;

			@Override
			public synchronized String format(LogRecord record) {
				String s = (record.getMillis() - previousMillis > 500) ? "\n *** "
						+ (++stanza) + " *** \n\n"
						: "";
				previousMillis = record.getMillis();
				return s + fmt.format(previousMillis % 1000) + " "
						+ record.getLevel() + " -- " + record.getMessage()
						+ "\n";
			}
		});
		/* configuracion del nivel de salida 'por defecto' de los logs
		   opciones: FINEST, FINER, FINE, INFO, CONFIG, WARNING, SEVERE
		   (es posible configurar los logs individuales a niveles distintos)*/
		log.getHandlers()[0].setLevel(Level.ALL);
		log.setLevel(Level.ALL);
		return log;
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
}
