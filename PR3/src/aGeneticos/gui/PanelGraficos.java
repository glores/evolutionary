package aGeneticos.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import aGeneticos.controlador.PintorBase;

public class PanelGraficos  extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private PintorBase pintor;	
	
	public PanelGraficos(){
		super();
		this.pintor=null;
	}
	
	public void paint(Graphics graphics){		
		if(pintor!=null){
			pintor.actualizar((Graphics2D)graphics);
		}		
	}
	
	public void dibujarGrafica(){
		pintor.dibujarGrafica((Graphics2D)this.getGraphics());
	}
	
	public void actualizarGrafica(){
		pintor.actualizar((Graphics2D)this.getGraphics());
	}
	
	public void setPintor(PintorBase p){
		pintor=p;
	}
}
