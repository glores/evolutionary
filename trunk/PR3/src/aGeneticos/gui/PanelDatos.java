package aGeneticos.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import aGeneticos.gui.parametros.ModoCruzador;
import aGeneticos.gui.parametros.ModoMutador;
import aGeneticos.gui.parametros.ModoSeleccionador;
import aGeneticos.gui.parametros.ParametrosAlgoritmo;
import aGeneticos.gui.parametros.Problema;

public class PanelDatos extends JPanel implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT = "archivos/mapa.txt";
	private JFileChooser fileChooser;
	private JButton botonCargar;
	private JTextField labelNombreFichero, textFieldTamTorneo, textFieldBeta,
			paramPropio, textFieldHeuristico;
	private JRadioButton[] selecciones, cruzadores, mutadores, problemas;
	private JComboBox comboProbTorneo;
	private JCheckBox checkHeuristico;
	private JTextField k;
	private JTextField profMaxEst;

	public PanelDatos() {
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(box);

		/* Panel de seleccionadores */
		add(creaPanelSeleccion());
		/* Panel de cruces */
		add(creaPanelCruces());
		/* Panel de mutadores */
		add(creaPanelMutadores());
		add(creaPanelProblema());

	}

	private Component creaPanelProblema() {
		JPanel panelProblema = new JPanel(new GridLayout(3, 1));

		// set border and layout
		Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
		Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border titleBorder = BorderFactory.createTitledBorder(lineBorder,
				"Problema");
		Border compoundBorder = BorderFactory.createCompoundBorder(titleBorder,
				emptyBorder);
		panelProblema.setBorder(compoundBorder);

		JPanel paneles[] = new JPanel[3];
		for (int i = 0; i < paneles.length; i++) {
			paneles[i] = new JPanel();
			panelProblema.add(paneles[i]);
		}

		botonCargar = new JButton("Cargar");
		botonCargar.addActionListener(this);
		paneles[0].add(botonCargar);

		labelNombreFichero = new JTextField(DEFAULT);
		labelNombreFichero.setHorizontalAlignment(JTextField.CENTER);
		paneles[0].add(labelNombreFichero);

		ButtonGroup grupo = new ButtonGroup();
		Problema[] problema = Problema.values();
		problemas = new JRadioButton[problema.length];

		int i = 0;
		for (Problema p : problema) {
			problemas[i] = new JRadioButton(
					ParametrosAlgoritmo.getTextoProblema((p)));
			problemas[i].addActionListener(this);
			grupo.add(problemas[i]);
			paneles[1].add(problemas[i]);
			if (p == ParametrosAlgoritmo.PARAMS_PROBLEMA) {
				problemas[i].setSelected(true);
			}
			i++;
		}

		JLabel labelk = new JLabel("Parámetro k ");
		JLabel labelMaxProfEst = new JLabel("Profundidad máx. estimada ");
		k = new JTextField(5);
		k.setText("0.2");
		profMaxEst = new JTextField(5);
		profMaxEst.setText("50");
		paneles[2].add(labelk);
		paneles[2].add(k);
		paneles[2].add(labelMaxProfEst);
		paneles[2].add(profMaxEst);
		profMaxEst.setEnabled(false);
		k.setEnabled(false);

		return panelProblema;
	}

	private JPanel creaPanelMutadores() {
		JPanel panelMutadores = new JPanel(new GridLayout(1, 2));

		// set border and layout
		Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
		Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border titleBorder = BorderFactory.createTitledBorder(lineBorder,
				"Mutadores");
		Border compoundBorder = BorderFactory.createCompoundBorder(titleBorder,
				emptyBorder);
		panelMutadores.setBorder(compoundBorder);

		ButtonGroup grupo = new ButtonGroup();
		ModoMutador[] mutador = ModoMutador.values();
		mutadores = new JRadioButton[mutador.length];
		int i = 0;
		for (ModoMutador p : mutador) {
			mutadores[i] = new JRadioButton(
					ParametrosAlgoritmo.getTextoMutador((p)));
			mutadores[i].addActionListener(this);
			grupo.add(mutadores[i]);
			panelMutadores.add(mutadores[i]);
			if (p == ParametrosAlgoritmo.PARAMS_MUTADOR) {
				mutadores[i].setSelected(true);
			}
			i++;
		}

		return panelMutadores;
	}

	private JPanel creaPanelCruces() {
		JPanel panelCruces = new JPanel(new GridLayout(1, 1));

		// set border and layout
		Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
		Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border titleBorder = BorderFactory.createTitledBorder(lineBorder,
				"Cruces");
		Border compoundBorder = BorderFactory.createCompoundBorder(titleBorder,
				emptyBorder);
		panelCruces.setBorder(compoundBorder);

		ButtonGroup grupo = new ButtonGroup();
		ModoCruzador[] cruces = ModoCruzador.values();
		cruzadores = new JRadioButton[cruces.length];
		int i = 0;
		for (ModoCruzador p : cruces) {
			cruzadores[i] = new JRadioButton(
					ParametrosAlgoritmo.getTextoCruzador((p)));
			cruzadores[i].addActionListener(this);
			grupo.add(cruzadores[i]);
			panelCruces.add(cruzadores[i]);
			if (p == ParametrosAlgoritmo.PARAMS_CRUZADOR) {
				cruzadores[i].setSelected(true);
			}
			i++;
		}

		// TextField para el tamaño de torneo
		checkHeuristico = new JCheckBox("Heurístico ");
		checkHeuristico.setSelected(false);
		checkHeuristico.addItemListener(this);
		textFieldHeuristico = new JTextField(10);
		textFieldHeuristico.setText("3");
		textFieldHeuristico.setEnabled(false);
		JPanel panelTextField = new JPanel();
		panelTextField.add(checkHeuristico);
		panelTextField.add(textFieldHeuristico);
		panelCruces.add(panelTextField);

		return panelCruces;
	}

	private JPanel creaPanelSeleccion() {
		JPanel panelSeleccionadores = new JPanel(new GridLayout(2, 5));

		// set border and layout
		Border emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
		Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border titleBorder = BorderFactory.createTitledBorder(lineBorder,
				"Seleccionadores");
		Border compoundBorder = BorderFactory.createCompoundBorder(titleBorder,
				emptyBorder);
		panelSeleccionadores.setBorder(compoundBorder);

		ButtonGroup grupo = new ButtonGroup();
		ModoSeleccionador[] seleccionadores = ModoSeleccionador.values();
		selecciones = new JRadioButton[seleccionadores.length];
		int i = 0;
		for (ModoSeleccionador p : seleccionadores) {
			selecciones[i] = new JRadioButton(
					ParametrosAlgoritmo.getTextoSeleccionador((p)));
			selecciones[i].addActionListener(this);
			grupo.add(selecciones[i]);
			panelSeleccionadores.add(selecciones[i]);
			if (p == ParametrosAlgoritmo.PARAMS_SELECCIONADOR) {
				selecciones[i].setSelected(true);
			}
			i++;
		}
		// Combo para el torneo probabilista
		String[] valores = { "0.5", "0.6", "0.7", "0.8", "0.9", "1.0" };
		comboProbTorneo = new JComboBox(valores);
		comboProbTorneo.setEnabled(false);
		JPanel panelCombo = new JPanel();
		panelCombo.add(new JLabel("Probabilidad Torneo Probabilista "));
		panelCombo.add(comboProbTorneo);
		panelSeleccionadores.add(panelCombo);

		// TextField para el tamaño de torneo
		textFieldTamTorneo = new JTextField(10);
		textFieldTamTorneo.setText("3");
		textFieldTamTorneo.setEnabled(false);
		JPanel panelTextField = new JPanel();
		panelTextField.add(new JLabel("Tamaño de torneo "));
		panelTextField.add(textFieldTamTorneo);
		panelSeleccionadores.add(panelTextField);

		// TextField para el parámetro beta del Ranking
		textFieldBeta = new JTextField(10);
		textFieldBeta.setText("1.5");
		textFieldBeta.setEnabled(false);
		JPanel panelBeta = new JPanel();
		panelBeta.add(new JLabel("Parámetro Beta (1 <= Beta <= 2) "));
		panelBeta.add(textFieldBeta);
		panelSeleccionadores.add(panelBeta);

		// TextField para el parámetro del seleccionador propio
		paramPropio = new JTextField(10);
		paramPropio.setText("0.5");
		paramPropio.setEnabled(false);
		JPanel panelParamPropio = new JPanel();
		panelParamPropio.add(new JLabel("Probabilidad "));
		panelParamPropio.add(paramPropio);
		panelSeleccionadores.add(panelParamPropio);

		return panelSeleccionadores;
	}

	/*------------- GETTERS Y SETTERS ------------------------------*/

	public boolean isTorneo() {
		return textFieldTamTorneo.isEditable();
	}

	public boolean isHeuristico() {
		return checkHeuristico.isSelected();
	}

	public String getTamHeuristico() {
		return textFieldHeuristico.getText();
	}

	public String getTamTorneo() {
		return textFieldTamTorneo.getText();
	}

	public String getBeta() {
		return textFieldBeta.getText();
	}

	public String getParamPropio() {
		return paramPropio.getText();
	}

	public String getProfMaxEst() {
		return profMaxEst.getText();
	}

	public String getK() {
		return k.getText();
	}

	public String getPath() {
		return labelNombreFichero.getText();
	}

	public String getProbTorneo() {
		return (String) comboProbTorneo.getSelectedItem();
	}

	public ModoSeleccionador getModoSeleccionador() {
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < selecciones.length) {
			encontrado = selecciones[i].isSelected();
			if (!encontrado) {
				i++;
			}
		}
		return ModoSeleccionador.values()[i];
	}

	public ModoCruzador getModoCruzador() {
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < cruzadores.length) {
			encontrado = cruzadores[i].isSelected();
			if (!encontrado) {
				i++;
			}
		}
		return ModoCruzador.values()[i];
	}

	public ModoMutador getModoMutador() {
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < mutadores.length) {
			encontrado = mutadores[i].isSelected();
			if (!encontrado) {
				i++;
			}
		}
		return ModoMutador.values()[i];
	}

	public Problema getProblema() {
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < problemas.length) {
			encontrado = problemas[i].isSelected();
			if (!encontrado) {
				i++;
			}
		}
		return Problema.values()[i];
	}

	/*--------------------------------------------------------------*/

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			textFieldHeuristico.setEnabled(false);
		} else
			textFieldHeuristico.setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonCargar) {
			if (fileChooser == null)
				fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter(
					"Archivo .txt", "txt"));

			int returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				labelNombreFichero.setText(fileChooser.getSelectedFile()
						.getAbsolutePath());
			}

		} else if (e.getActionCommand().equals(
				ParametrosAlgoritmo
						.getTextoSeleccionador(ModoSeleccionador.TORNEO_DET))) {
			textFieldTamTorneo.setEnabled(true);
			comboProbTorneo.setEnabled(false);
			textFieldBeta.setEnabled(false);
			paramPropio.setEnabled(false);
		} else if (e.getActionCommand().equals(
				ParametrosAlgoritmo
						.getTextoSeleccionador(ModoSeleccionador.TORNEO_PROB))) {
			textFieldTamTorneo.setEnabled(true);
			comboProbTorneo.setEnabled(true);
			textFieldBeta.setEnabled(false);
			paramPropio.setEnabled(false);
		} else if (e.getActionCommand().equals(
				ParametrosAlgoritmo
						.getTextoSeleccionador(ModoSeleccionador.RULETA))) {
			textFieldTamTorneo.setEnabled(false);
			comboProbTorneo.setEnabled(false);
			textFieldBeta.setEnabled(false);
			paramPropio.setEnabled(false);
		} else if (e.getActionCommand().equals(
				ParametrosAlgoritmo
						.getTextoSeleccionador(ModoSeleccionador.PROPIO))) {
			textFieldTamTorneo.setEnabled(false);
			comboProbTorneo.setEnabled(false);
			textFieldBeta.setEnabled(false);
			paramPropio.setEnabled(true);
		} else if (e.getActionCommand().equals(
				ParametrosAlgoritmo
						.getTextoSeleccionador(ModoSeleccionador.RANKING))) {
			textFieldTamTorneo.setEnabled(false);
			comboProbTorneo.setEnabled(false);
			textFieldBeta.setEnabled(true);
			paramPropio.setEnabled(false);
		} else if (e.getActionCommand()
				.equals(ParametrosAlgoritmo
						.getTextoProblema(Problema.PENALIZA_GRANDES))) {
			k.setEnabled(true);
			profMaxEst.setEnabled(true);
		} else if (e.getActionCommand().equals(
				ParametrosAlgoritmo.getTextoProblema(Problema.BOCADOS))) {
			k.setEnabled(false);
			profMaxEst.setEnabled(false);
		}
	}
}
