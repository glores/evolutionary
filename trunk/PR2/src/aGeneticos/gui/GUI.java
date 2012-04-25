package aGeneticos.gui;

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
import javax.swing.JCheckBox;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import aGeneticos.controlador.Controlador;
import aGeneticos.controlador.PintorBase;
import aGeneticos.controlador.PintorIterativo;
import aGeneticos.gui.parametros.ModoCruzador;
import aGeneticos.gui.parametros.ModoGenerador;
import aGeneticos.gui.parametros.ModoMutador;
import aGeneticos.gui.parametros.ModoSeleccionador;
import aGeneticos.gui.parametros.ParametrosAlgoritmo;
import aGeneticos.gui.parametros.Problema;
import aGeneticos.logica.AGenetico;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 * 
 *         Clase que implementa la interfaz gráfica de usuario
 */

public class GUI extends JFrame implements ActionListener, Observer {

	private static final long serialVersionUID = 1L;

	private final static int DOUBLE = 1;

	private JSplitPane panelPrincipal;
	private JPanel panelGraficos;
	private PanelDatos panelDatos;
	private JTextField tamPob;
	private JTextField maxIt;
	private JTextField probCruce;
	private JTextField probMut;
	private JTextField probCruceIntInc;
	private JCheckBox intervaloProbCruce;
	private JTextField probCruceIntA;
	private JTextField probCruceIntB;
	private JButton btnOk, btnActualizar;
	private PintorBase pintor;

	private JCheckBoxMenuItem chckbxmntmElitismo;
	private JProgressBar barraProgreso;

	private Logger log;

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
				panelGraficos.removeAll();
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

		log.fine("[GUI] Inicialización de GUI...");
		JTabbedPane contentPane = new JTabbedPane();
		panelPrincipal = new JSplitPane();
		panelPrincipal.setDividerSize(1);
		setTitle("Algoritmo Gen\u00E9tico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		log.fine("[GUI] Creando menu...");
		// Menu
		JMenuBar menuBar = creaBarraMenu();
		setJMenuBar(menuBar);
		log.fine("[GUI] Creado");
		log.fine("[GUI] Creando panel de parametros...");
		// Panel de parámetros
		JPanel panelParams = crearPanelParams();
		parametroPoblacion(panelParams);
		parametroMaximoIteraciones(panelParams);
		parametroProbabilidadCruce(panelParams);
		parametroIntervaloProbCruce(panelParams);
		parametroProbabilidadMutacion(panelParams);
		crearBoton(panelParams);
		log.fine("[GUI] Creado");
		log.fine("[GUI] Creando panel de graficos...");
		// Panel de gráficos
		panelGraficos = crearPanelGraficos();
		log.fine("[GUI] Creado");
		panelPrincipal.setLeftComponent(panelParams);
		panelPrincipal.setRightComponent(panelGraficos);

		panelDatos = new PanelDatos();
		contentPane.addTab("Principal", panelPrincipal);
		contentPane.addTab("Datos", panelDatos);
		this.setPreferredSize(new Dimension(950,600));
		int x = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2-this.getPreferredSize().width/2);
		int y = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2-this.getPreferredSize().height/2);
		setLocation(x, y);
		setContentPane(contentPane);
		log.fine("[GUI] Inicialización terminada");
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		pintor = Controlador.getInstance().getPintor();
	}

	private JPanel crearPanelGraficos() {
		JPanel panelGraficos = new JPanel();
		panelGraficos.setPreferredSize(new Dimension(590, 400));

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

	private void parametroIntervaloProbCruce(JPanel panelParams) {
		parametroIntervaloProbCruce_a(panelParams);
		parametroIntervaloProbCruce_b(panelParams);
	}

	private void parametroIntervaloProbCruce_a(JPanel panelParams) {
		JPanel panelIntervalo = new JPanel();
		panelParams.add(panelIntervalo);
		panelIntervalo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblIntervalo = new JLabel("Intervalo");
		panelIntervalo.add(lblIntervalo);
		intervaloProbCruce = new JCheckBox();
		panelIntervalo.add(intervaloProbCruce);
		JLabel lblIntervaloIncremento = new JLabel("Incremento");

		panelIntervalo.add(lblIntervaloIncremento);
		probCruceIntInc = new JTextField();
		probCruceIntInc.setColumns(4);
		probCruceIntInc.setText(Double
				.toString(ParametrosAlgoritmo.PARAMS_INT_PROBCRUCE_INC));
		panelIntervalo.add(probCruceIntInc);
	}

	private void parametroIntervaloProbCruce_b(JPanel panelParams) {
		JPanel panelIntervalo = new JPanel();
		panelParams.add(panelIntervalo);
		panelIntervalo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblIntervaloDesde = new JLabel("Desde");
		panelIntervalo.add(lblIntervaloDesde);
		probCruceIntA = new JTextField();
		probCruceIntA.setColumns(4);
		probCruceIntA.setText(Double
				.toString(ParametrosAlgoritmo.PARAMS_INT_PROBCRUCE_A));

		panelIntervalo.add(probCruceIntA);
		JLabel lblIntervaloHasta = new JLabel("Hasta");
		probCruceIntB = new JTextField();
		probCruceIntB.setColumns(4);
		probCruceIntB.setText(Double
				.toString(ParametrosAlgoritmo.PARAMS_INT_PROBCRUCE_B));
		panelIntervalo.add(lblIntervaloHasta);
		panelIntervalo.add(probCruceIntB);
	}

	private JPanel crearPanelParams() {
		JPanel panelParams = new JPanel();
		panelParams.setPreferredSize(new Dimension(300, 500));
		GridLayout gl_panelParams = new GridLayout(7, 1);
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
		item.setSelected(true);
		opcionesLog.add(item);

		menuLogger.add(item);
		return menuLogger;
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
//			log.info("[GUI] Ejecutar algoritmo");
//			ParametrosAlgoritmo params = componerParametros();
//			if (params != null) {
//				log.info("[GUI] Parametros correctos");
//				log.info(params.toString());
//				panelGraficos.removeAll();
//				Controlador.getInstance().inicia(params);
//
//			}

			 task = new Task();
			 task.execute();

		} else if (e.getSource() == btnActualizar) {
			actualizar();
		} else {
			actionEventLog(e);
		}

	}

	/**
	 * Función que realiza el evento correspondiente a la opción de log
	 * seleccionada
	 * 
	 * @param e
	 */
	private void actionEventLog(ActionEvent e) {
		if (e.getActionCommand().equals(Level.FINEST.toString())) {
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
		// Si ya se ha ejecutado alguna vez, actualiza el panel de gráficos
		if (ejecucion)
			// pintor.dibujarGrafica((Graphics2D) panelGraficos.getGraphics());
			pintor.actualizar((Graphics2D) panelGraficos.getGraphics());
	}

	private ParametrosAlgoritmo componerParametros() {
		String mensaje = "";
		ModoSeleccionador modoSelec = panelDatos.getModoSeleccionador();
		ModoCruzador modoCruzador = panelDatos.getModoCruzador();
		ModoMutador modoMutador = panelDatos.getModoMutador();
		Problema problema = panelDatos.getProblema();

		// Configurar los parámetros a utilizar
		ParametrosAlgoritmo params = new ParametrosAlgoritmo();

		params.setElitismo(chckbxmntmElitismo.isSelected());
		if (!params.setLimIteraciones(maxIt.getText())) {
			mensaje += "Número máximo de iteraciones no válido.\n";
		}

		if (!params.setProbabilidadCruce(probCruce.getText())) {
			mensaje += "Probabilidad de cruce no válida.\n";
		}

		if (!params.setProbabilidadMutacion(probMut.getText())) {
			mensaje += "Probabilidad de mutación no válida.\n";
		}

		if (!params.setProbabilidadMutacion(probMut.getText())) {
			mensaje += "Probabilidad de mutación no válida.\n";
		}
		if (chckbxmntmElitismo.isSelected()) {
			double porcentajeElite = setParametroExtra(
					"Introduzca un tamaño de élite (%): ", DOUBLE);
			params.setTamElite(porcentajeElite);
		}
		if (intervaloProbCruce.isSelected()) {

			if (!params.setIntProbCruce_a(probCruceIntA.getText())) {
				mensaje += "Estremo izquierdo del intervalo no válido.\n";
			}
			if (!params.setIntProbCruce_b(probCruceIntB.getText())) {
				mensaje += "Estremo derecho del intervalo no válido.\n";
			}
			if (!params.setIntProbCruce_inc(probCruceIntInc.getText())) {
				mensaje += "Incremento del intervalo no válido.\n";
			}
			if (!params.setIntProbCruce_habilitado(true)) {
				mensaje += "Revise los ajustes del intervalo. (¿ a<b ?)\n";
			}
		}

		if (modoSelec.equals(ModoSeleccionador.TORNEO_DET) || modoSelec.equals(ModoSeleccionador.TORNEO_PROB)) {
			if (!params.setTamTorneo(panelDatos.getTamTorneo())) {
				mensaje += "Tamaño de torneo no válido. \n";
			}
		}

		if (modoSelec.equals(ModoSeleccionador.TORNEO_PROB)) {
			if (!params.setProbTorneoProbabilista(panelDatos.getProbTorneo())){
				mensaje += "Probabilidad de torneo no válido. \n";
			}
		}
		
		if (modoSelec.equals(ModoSeleccionador.RANKING)){
			if (!params.setBeta(panelDatos.getBeta())){
				mensaje += "Parámetro Beta no válido. \n";
			}
		}

		if (!params.setTamGrupo(panelDatos.getTamGrupo())) {
			mensaje += "Tamaño de grupo no válido. \n";
		}

		if (panelDatos.getPath() == null) {
			mensaje += "No ha seleccionado archivo";
		} else
			params.setPath(panelDatos.getPath());

		if (!params.setAlpha(panelDatos.getAlpha())) {
			mensaje += "Alpha no válido. \n";
		}
		params.setGeneradorPoblaciones(ModoGenerador.ALEATORIO);
		params.setProblema(problema);

		params.setSeleccionador(modoSelec);
		if (!params.setTamPoblacion(tamPob.getText())) {
			mensaje += "Tamaño de población no válido.\n";
		}

		params.setCruzador(modoCruzador);
		params.setMutador(modoMutador);

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

	@Override
	public void update(Observable arg0, Object arg1) {
		pintor = Controlador.getInstance().getPintor();
		String evento = (String) arg1;
		AGenetico algoritmo = (AGenetico) arg0;
		if (evento.equals("inicial")) {
			barraProgreso.setMinimum(0);
			barraProgreso.setMaximum(algoritmo.getNumIteraciones());
			barraProgreso.setValue(0);
			barraProgreso.setStringPainted(true);
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
			barraProgreso.setValue(barraProgreso.getMaximum());
			if (Controlador.getInstance().esModoIterativo()) {
				if (!Controlador.getInstance().esUltimaIteracion()) {
					((PintorIterativo) pintor).siguienteIteracion();
				} else {
					pintor.setTitulo("Iterativo");
					pintor.dibujarGrafica((Graphics2D) panelGraficos
							.getGraphics());
					btnOk.setEnabled(true);
				}
			} else {
				// pintor.setTitulo(algoritmo.getSolucion() + " Cruces: "
				// + algoritmo.getNumCruzados() + " Mutaciones: "
				// + algoritmo.getNumMutados());
				// pintor.dibujarGrafica((Graphics2D)
				// panelGraficos.getGraphics());
				// btnOk.setEnabled(true);
			}

		}

	}

}
