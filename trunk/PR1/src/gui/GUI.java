package gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import logica.esqueleto.algoritmos.AGenetico;
import parametros.ModoGenerador;
import parametros.ModoSeleccionador;
import parametros.ParametrosAlgoritmo;
import parametros.Problema;
import controlador.Controlador;
import controlador.Pintor;

/**
 * Pr�ctica 1 de Programaci�n Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fern�ndez
 * @author Sergio Barja Vald�s
 * 
 *         Clase que implementa la interfaz gr�fica de usuario
 */

public class GUI extends JFrame implements ActionListener, Observer{

	private static final long serialVersionUID = 1L;

	private final static int INT = 0;
	private final static int DOUBLE = 1;

	private JSplitPane contentPane;
	private JPanel panelGraficos;
	private JTextField tolerancia;
	private JTextField tamPob;
	private JTextField maxIt;
	private JTextField probCruce;
	private JTextField probMut;
	private JButton btnOk, btnActualizar;
	private Pintor pintor;

	private ButtonGroup grupo;
	private JCheckBoxMenuItem chckbxmntmElitismo;

	private JRadioButtonMenuItem menuSelecciones[];
	private JRadioButtonMenuItem menuFunciones[];
	private JProgressBar barraProgreso;

	private Logger log;

	private int n;
	private int tamTorneo;
	private boolean ejecucion = false;

	private Task task;

	class Task extends SwingWorker<Void, Void> {
		/*
		 * Tarea principal. Ejecutada en un hilo en segundo plano.
		 */
		@Override
		public Void doInBackground() {
			log.info("[GUI] Ejecutar algoritmo");
			ParametrosAlgoritmo params = componerParametros();
			if (params != null) {
				log.info("[GUI] Parametros correctos");
				log.info(params.toString());
				Controlador.getInstance().inicia(params);

			}
			return null;
		}

		/*
		 * Una vez finalizado..
		 */
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			btnOk.setEnabled(true);
			setCursor(null);
		}
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		log = Logger.getLogger("CP");
		log.fine("[GUI] Inicializaci�n de GUI...");
		contentPane = new JSplitPane();
		contentPane.setDividerSize(1);
		setTitle("Algoritmo Gen\u00E9tico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		log.fine("[GUI] Creando menu...");
		// Menu
		JMenuBar menuBar = creaBarraMenu();
		setJMenuBar(menuBar);
		log.fine("[GUI] Creado");
		log.fine("[GUI] Creando panel de parametros...");
		// Panel de par�metros
		JPanel panelParams = crearPanelParams();
		parametroPoblacion(panelParams);
		parametroMaximoIteraciones(panelParams);
		parametroProbabilidadCruce(panelParams);
		parametroProbabilidadMutacion(panelParams);
		parametroTolerancia(panelParams);
		crearBoton(panelParams);
		log.fine("[GUI] Creado");
		log.fine("[GUI] Creando panel de graficos...");
		// Panel de gr�ficos
		panelGraficos = crearPanelGraficos();
		log.fine("[GUI] Creado");
		contentPane.setLeftComponent(panelParams);
		contentPane.setRightComponent(panelGraficos);
		setContentPane(contentPane);
		log.fine("[GUI] Inicializaci�n terminada");
		this.setVisible(true);
		this.setResizable(true);
		pintor = Controlador.getInstance().getPintor();
	}

	private JPanel crearPanelGraficos() {
		JPanel panelGraficos = new JPanel();
		return panelGraficos;

	}

	private void crearBoton(JPanel panelParams) {
		JSplitPane panelBoton = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		panelParams.add(panelBoton);

		JPanel panel = new JPanel();
		btnOk = new JButton("OK");
		btnOk.addActionListener(this);
		panel.add(btnOk);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(this);
		panel.add(btnActualizar);

		panelBoton.add(panel);
		barraProgreso = new JProgressBar();
		panelBoton.add(barraProgreso);
		panelBoton.setDividerSize(0);
	}

	private void parametroTolerancia(JPanel panelParams) {
		JPanel panelTol = new JPanel();
		panelParams.add(panelTol);
		panelTol.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblTamaoPoblacin = new JLabel("Tolerancia");
		panelTol.add(lblTamaoPoblacin);
		tolerancia = new JTextField();
		panelTol.add(tolerancia);
		tolerancia.setColumns(10);
		tolerancia.setText(Double
				.toString(ParametrosAlgoritmo.PARAMS_TOLERANCIA));
	}

	private void parametroProbabilidadMutacion(JPanel panelParams) {
		JPanel panelProbMut = new JPanel();
		panelParams.add(panelProbMut);
		panelProbMut.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblProbMutacin = new JLabel("Prob. Mutaci\u00F3n");
		panelProbMut.add(lblProbMutacin);

		probMut = new JTextField();
		probMut.setColumns(10);
		probMut.setText(Double
				.toString(ParametrosAlgoritmo.PARAMS_PROBMUTACION));
		panelProbMut.add(probMut);
	}

	private void parametroProbabilidadCruce(JPanel panelParams) {
		JPanel panelProbCruce = new JPanel();
		panelParams.add(panelProbCruce);
		panelProbCruce.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblProbCruce = new JLabel("Prob. Cruce");
		panelProbCruce.add(lblProbCruce);

		probCruce = new JTextField();
		probCruce.setColumns(10);
		probCruce.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		probCruce
				.setText(Double.toString(ParametrosAlgoritmo.PARAMS_PROBCRUCE));
		panelProbCruce.add(probCruce);
	}

	private void parametroMaximoIteraciones(JPanel panelParams) {
		JPanel panelMaxIt = new JPanel();
		panelParams.add(panelMaxIt);
		panelMaxIt.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblMxIteraciones = new JLabel("M\u00E1x. Iteraciones");
		panelMaxIt.add(lblMxIteraciones);

		maxIt = new JTextField();
		maxIt.setColumns(10);
		maxIt.setText(Integer
				.toString(ParametrosAlgoritmo.PARAMS_LIMITERACIONES));
		panelMaxIt.add(maxIt);
	}

	private void parametroPoblacion(JPanel panelParams) {
		JPanel panelTamPob = new JPanel();
		panelParams.add(panelTamPob);
		panelTamPob.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblTamPob = new JLabel("Tama\u00F1o poblaci\u00F3n");
		panelTamPob.add(lblTamPob);

		tamPob = new JTextField();
		tamPob.setColumns(10);
		tamPob.setText(Integer
				.toString(ParametrosAlgoritmo.PARAMS_TAMPOBLACION));
		panelTamPob.add(tamPob);
	}

	private JPanel crearPanelParams() {
		JPanel panelParams = new JPanel();
		panelParams.setPreferredSize(new Dimension(300, 500));
		GridLayout gl_panelParams = new GridLayout(6, 1);
		gl_panelParams.setVgap(10);
		gl_panelParams.setHgap(10);
		panelParams.setLayout(gl_panelParams);
		return panelParams;
	}

	private JMenuBar creaBarraMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(this);
		mnArchivo.add(mntmSalir);

		menuBar.add(creaMenuSeleccion());

		menuBar.add(creaMenuFunciones());

		menuBar.add(creaMenuOpciones());

		return menuBar;
	}

	private JMenu creaMenuOpciones() {
		JMenu mnOpciones = new JMenu("Opciones");
		chckbxmntmElitismo = new JCheckBoxMenuItem("Elitismo");
		chckbxmntmElitismo.setSelected(ParametrosAlgoritmo.PARAMS_ELITISMO);
		mnOpciones.add(chckbxmntmElitismo);

		JMenu menuLogger = creaMenuLogger();
		mnOpciones.add(menuLogger);

		return mnOpciones;
	}

	private JMenu creaMenuLogger() {
		JMenu menuLogger = new JMenu("Nivel de log");
		ButtonGroup opcionesLog = new ButtonGroup();

		JRadioButtonMenuItem item;
		item = new JRadioButtonMenuItem(Level.FINEST.toString());
		item.addActionListener(this);
		opcionesLog.add(item);
		menuLogger.add(item);

		item = new JRadioButtonMenuItem(Level.FINER.toString());
		item.addActionListener(this);
		opcionesLog.add(item);
		menuLogger.add(item);

		item = new JRadioButtonMenuItem(Level.FINE.toString());
		item.addActionListener(this);
		opcionesLog.add(item);
		menuLogger.add(item);

		item = new JRadioButtonMenuItem(Level.INFO.toString());
		item.addActionListener(this);
		opcionesLog.add(item);
		menuLogger.add(item);

		item = new JRadioButtonMenuItem(Level.CONFIG.toString());
		item.addActionListener(this);
		opcionesLog.add(item);
		menuLogger.add(item);

		item = new JRadioButtonMenuItem(Level.WARNING.toString());
		item.addActionListener(this);
		opcionesLog.add(item);
		menuLogger.add(item);

		item = new JRadioButtonMenuItem(Level.SEVERE.toString());
		item.addActionListener(this);
		opcionesLog.add(item);

		menuLogger.add(item);
		menuLogger.getItem(0).setSelected(true);
		return menuLogger;
	}

	private JMenu creaMenuSeleccion() {
		JMenu mnSeleccion = new JMenu("Selecci\u00F3n");
		grupo = new ButtonGroup();
		ModoSeleccionador[] seleccionadores = ModoSeleccionador.values();
		menuSelecciones = new JRadioButtonMenuItem[seleccionadores.length];
		int i = 0;
		for (ModoSeleccionador p : seleccionadores) {
			menuSelecciones[i] = new JRadioButtonMenuItem(
					ParametrosAlgoritmo.getTextoSeleccionador((p)));
			grupo.add(menuSelecciones[i]);
			mnSeleccion.add(menuSelecciones[i]);
			if (p == ParametrosAlgoritmo.PARAMS_SELECCIONADOR) {
				menuSelecciones[i].setSelected(true);
			}
			i++;
		}
		menuSelecciones[ModoSeleccionador.TORNEO.ordinal()]
				.addActionListener(this);
		return mnSeleccion;

	}

	private JMenu creaMenuFunciones() {
		JMenu mnFunciones = new JMenu("Funciones");
		ButtonGroup grupoFunciones = new ButtonGroup();

		Problema[] funcionesDisponibles = Problema.values();

		menuFunciones = new JRadioButtonMenuItem[funcionesDisponibles.length];
		int i = 0;
		for (Problema p : funcionesDisponibles) {
			menuFunciones[i] = new JRadioButtonMenuItem(
					ParametrosAlgoritmo.getTextoProblema(p));
			grupoFunciones.add(menuFunciones[i]);
			mnFunciones.add(menuFunciones[i]);
			if (p == ParametrosAlgoritmo.PARAMS_PROBLEMA) {
				menuFunciones[i].setSelected(true);
			}
			i++;
		}
		menuFunciones[Problema.FUNCION_5.ordinal()].addActionListener(this);
		return mnFunciones;
	}

	private double setParametroExtra(String msg, int tipo) {
		boolean ok = false;
		double valor = -1;
		while (!ok) {
			try {
				switch (tipo) {
				case 0:
					valor = Integer.parseInt(JOptionPane.showInputDialog(msg));
					break;
				case 1:
					valor = Double
							.parseDouble(JOptionPane.showInputDialog(msg));
					break;
				}
				ok = true;
			} catch (NumberFormatException exception) {
				log.severe(exception.getMessage());
				ok = false;
			}
		}
		return valor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Salir")) {
			System.exit(0);
		} else if (e.getSource() == btnOk) {
			// accionEjecutar();
			task = new Task();
			task.execute();
		} else if (e.getSource() == btnActualizar) {
			actualizar();
		} else if (e.getActionCommand().equals(
				ParametrosAlgoritmo.getTextoProblema(Problema.FUNCION_5))) {
			n = (int) setParametroExtra("Introduzca un valor para n: ", INT);
		} else if (e.getActionCommand().equals(
				ParametrosAlgoritmo
						.getTextoSeleccionador(ModoSeleccionador.TORNEO))) {
			tamTorneo = (int) setParametroExtra(
					"Introduzca un tama�o de torneo: ", INT);
		} else if (e.getActionCommand().equals(Level.FINEST.toString())) {
			log.setLevel(Level.FINEST);
			System.out.println(log.getLevel());
		} else if (e.getActionCommand().equals(Level.FINER.toString())) {
			log.setLevel(Level.FINER);
			System.out.println(log.getLevel());
		} else if (e.getActionCommand().equals(Level.FINE.toString())) {
			log.setLevel(Level.FINE);
			System.out.println(log.getLevel());
		} else if (e.getActionCommand().equals(Level.INFO.toString())) {
			log.setLevel(Level.INFO);
			System.out.println(log.getLevel());
		} else if (e.getActionCommand().equals(Level.CONFIG.toString())) {
			log.setLevel(Level.CONFIG);
			System.out.println(log.getLevel());
		} else if (e.getActionCommand().equals(Level.WARNING.toString())) {
			log.setLevel(Level.WARNING);
			System.out.println(log.getLevel());
		} else if (e.getActionCommand().equals(Level.SEVERE.toString())) {
			log.setLevel(Level.SEVERE);
			System.out.println(log.getLevel());
		}

	}

	private void actualizar() {
		// Si ya se ha ejecutado alguna vez, actualiza el panel de gr�ficos
		if (ejecucion)
			pintor.dibujarGrafica((Graphics2D) panelGraficos.getGraphics());
	}

	private ParametrosAlgoritmo componerParametros() {
		String mensaje = "";
		ModoSeleccionador modoSelec = modoSeleccionador();
		Problema problema = problema();
		// Configurar los par�metros a utilizar
		ParametrosAlgoritmo params = new ParametrosAlgoritmo();

		if (!params.setTolerancia(tolerancia.getText())) {
			mensaje += "Tolerancia no v�lida.\n";
		}
		params.setElitismo(chckbxmntmElitismo.isSelected());
		if (!params.setLimIteraciones(maxIt.getText())) {
			mensaje += "N�mero m�ximo de iteraciones no v�lido.\n";
		} else {
			barraProgreso.setMinimum(0);
			barraProgreso.setMaximum(Integer.parseInt(maxIt.getText()));
			barraProgreso.setValue(0);
			barraProgreso.setStringPainted(true);
		}

		if (!params.setProbabilidadCruce(probCruce.getText())) {
			mensaje += "Probabilidad de cruce no v�lida.\n";
		}

		if (!params.setProbabilidadMutacion(probMut.getText())) {
			mensaje += "Probabilidad de mutaci�n no v�lida.\n";
		}

		if (!params.setProbabilidadMutacion(probMut.getText())) {
			mensaje += "Probabilidad de mutaci�n no v�lida.\n";
		}
		if (chckbxmntmElitismo.isSelected()) {
			double porcentajeElite = setParametroExtra(
					"Introduzca un tama�o de �lite (%): ", DOUBLE);
			params.setTamElite(porcentajeElite);
		}
		params.setTamTorneo(tamTorneo);
		params.setN(n);
		params.setGeneradorPoblaciones(ModoGenerador.ALEATORIO);
		params.setProblema(problema);

		params.setSeleccionador(modoSelec);
		if (!params.setTamPoblacion(tamPob.getText())) {
			mensaje += "Tama�o de poblaci�n no v�lido.\n";
		}

		// Estos no est�n en la GUI todav�a pero los dejo ya aqu�
		/*
		 * params.setCruzador(cruzador);
		 * 
		 * params.setMutador(mutador);
		 */

		if (!mensaje.equals("")) {
			JOptionPane.showMessageDialog(this,
					"Se han producido los siguientes errores: \n" + mensaje,
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		} else {
			btnOk.setEnabled(false);
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			return params;
		}
	}

	private ModoSeleccionador modoSeleccionador() {
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < menuSelecciones.length) {
			encontrado = menuSelecciones[i].isSelected();
			if (!encontrado) {
				i++;
			}
		}
		return ModoSeleccionador.values()[i];
	}

	private Problema problema() {
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < menuFunciones.length) {
			encontrado = menuFunciones[i].isSelected();
			if (!encontrado) {
				i++;
			}
		}
		return Problema.values()[i];
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String evento = (String) arg1;
		AGenetico algoritmo = (AGenetico) arg0;
		if (evento.equals("inicial")) {
			pintor.iniciar(algoritmo.getNumIteraciones());
			pintor.addMejorGlobal(algoritmo.getMejorAptitud());
			pintor.addMejor(algoritmo.getMejorGeneracion());
			pintor.addMedia(algoritmo.getMedia());
			pintor.siguienteGeneracion();
		} else if (evento.equals("generacion")) {
			pintor.addMejorGlobal(algoritmo.getMejorAptitud());
			pintor.addMejor(algoritmo.getMejorGeneracion());
			pintor.addMedia(algoritmo.getMedia());
			pintor.siguienteGeneracion();
			barraProgreso.setValue(barraProgreso.getValue() + 1);
		} else if (evento.equals("fin")) {
			ejecucion = true;
			pintor.setTitulo(algoritmo.getSolucion() + " Cruces: "
					+ algoritmo.getNumCruzados() + " Mutaciones: "
					+ algoritmo.getNumMutados());
			pintor.dibujarGrafica((Graphics2D) panelGraficos.getGraphics());
			btnOk.setEnabled(true);
			barraProgreso.setValue(barraProgreso.getMaximum());
		}

	}

}
