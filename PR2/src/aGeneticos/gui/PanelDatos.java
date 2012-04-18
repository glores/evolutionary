package aGeneticos.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
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

public class PanelDatos extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT = "No hay archivo cargado";
	private JFileChooser fileChooser;
	private JButton botonCargar;
	private JTextField labelNombreFichero, tamGrupo, textFieldTamTorneo, alpha;
	private JRadioButton[] selecciones, cruzadores, mutadores, problemas;
	private JComboBox comboProbTorneo;

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

		JLabel m = new JLabel("Tama�o grupo: ");
		tamGrupo = new JTextField(10);
		tamGrupo.setText("2");

		paneles[1].add(m);
		paneles[1].add(tamGrupo);

		ButtonGroup grupo = new ButtonGroup();
		Problema[] problema = Problema.values();
		problemas = new JRadioButton[problema.length];
		alpha = new JTextField(10);
		alpha.setText("0.2");
		int i = 0;
		for (Problema p : problema) {
			problemas[i] = new JRadioButton(
					ParametrosAlgoritmo.getTextoProblema((p)));
			problemas[i].addActionListener(this);
			grupo.add(problemas[i]);
			paneles[2].add(problemas[i]);
			if (p == Problema.FUNCION_1){
				paneles[2].add(alpha);
			}
			if (p == ParametrosAlgoritmo.PARAMS_PROBLEMA) {
				problemas[i].setSelected(true);
			}
			i++;
		}

		return panelProblema;
	}

	private JPanel creaPanelMutadores() {
		JPanel panelMutadores = new JPanel(new GridLayout(1, 3));

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
		JPanel panelCruces = new JPanel(new GridLayout(1, 3));

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

		return panelCruces;
	}

	private JPanel creaPanelSeleccion() {
		JPanel panelSeleccionadores = new JPanel(new GridLayout(2, 3));

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

		// TextField para el tama�o de torneo
		textFieldTamTorneo = new JTextField(10);
		textFieldTamTorneo.setText("3");
		textFieldTamTorneo.setEnabled(false);
		JPanel panelTextField = new JPanel();
		panelTextField.add(new JLabel("Tama�o de torneo "));
		panelTextField.add(textFieldTamTorneo);
		panelSeleccionadores.add(panelTextField);

		return panelSeleccionadores;
	}
	
	/*------------- GETTERS Y SETTERS ------------------------------*/
	
	public boolean isTorneo(){
		return textFieldTamTorneo.isEditable();
	}
	
	public String getTamTorneo(){
		return textFieldTamTorneo.getText();
	}
	
	public String getTamGrupo(){
		return tamGrupo.getText();
	}
	

	public String getAlpha() {
		return alpha.getText();
	}
	
	public String getPath(){
		if (labelNombreFichero.getText().equals(DEFAULT)){
			return null;
		}
		else return labelNombreFichero.getText();
	}
	
	public String getProbTorneo(){
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
		} else if (e.getActionCommand().equals(
				ParametrosAlgoritmo
						.getTextoSeleccionador(ModoSeleccionador.TORNEO_PROB))) {
			textFieldTamTorneo.setEnabled(true);
			comboProbTorneo.setEnabled(true);
		} else if (e.getActionCommand().equals(
				ParametrosAlgoritmo
						.getTextoSeleccionador(ModoSeleccionador.RULETA))) {
			textFieldTamTorneo.setEnabled(false);
			comboProbTorneo.setEnabled(false);
		} else if (e.getActionCommand().equals(ParametrosAlgoritmo.getTextoProblema(Problema.FUNCION_1))){
			alpha.setEnabled(true);
		}
	}

}
