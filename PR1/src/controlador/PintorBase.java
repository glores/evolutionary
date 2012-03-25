package controlador;

import java.awt.Graphics2D;
import java.util.logging.Logger;

import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.DataSeries;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.PropertyException;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 * 
 *        
 */

public abstract class PintorBase {
	protected AxisChart axisChart;
	protected String titulo;
	protected DataSeries dataSeries;
	protected LegendProperties legendProperties;
	protected ChartProperties chartProperties;
	protected AxisProperties axisProperties;
	protected int generacionActual;

	protected Logger log;

	public abstract void iniciar();

	public void dibujarGrafica(Graphics2D graphics) {
		try {
			axisChart = crearGraficoLineas();
			axisChart.setGraphics2D(graphics);
			axisChart.render();
		} catch (ChartDataException e) {
			log.severe(e.getMessage());
		} catch (PropertyException e) {
			log.severe(e.getMessage());
		}

	}

	public void siguienteGeneracion() {
		generacionActual++;
	}

	public abstract void addMejor(double valor);

	public abstract void addMedia(double valor);

	public abstract void addMejorGlobal(double valor);

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	protected abstract AxisChart crearGraficoLineas() throws ChartDataException;

	public void actualizar(Graphics2D graphics) {
		try {
			axisChart.setGraphics2D(graphics);
			axisChart.render();
		} catch (PropertyException e) {
			log.severe(e.getMessage());
		} catch (ChartDataException e) {
			log.severe(e.getMessage());
		}
	}
	
	
}
