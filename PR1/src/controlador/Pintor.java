package controlador;
import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.logging.Logger;

import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.DataSeries;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.AxisTypeProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.LineChartProperties;
import org.jCharts.properties.PointChartProperties;
import org.jCharts.properties.PropertyException;
import org.jCharts.properties.util.ChartStroke;
import org.jCharts.test.TestDataGenerator;
import org.jCharts.types.ChartType;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que se encarga de la representación de datos mediante gráficos
 */

public class Pintor {
	private AxisChart axisChart;
	private double[] mejores;
	private double[] mejoresGlobales;
	private double[] medias;
	private int generacionActual;
	private String titulo;
	private DataSeries dataSeries;
	private LegendProperties legendProperties;
	private ChartProperties chartProperties;
	private AxisProperties axisProperties;
	
	private Logger log;
	public Pintor(){
		
	}
	public void iniciar(int tamGeneraciones){
		log = Logger.getLogger("CP");
		mejores = new double[tamGeneraciones];
		mejoresGlobales = new double[tamGeneraciones];
		
		medias = new double[tamGeneraciones];
		generacionActual = 0;
		titulo = "";
	}
	

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

	public void addMejor(double valor) {
		mejores[generacionActual] = valor;
	}
	public void addMedia(double valor) {
		medias[generacionActual] = valor;
	}
	public void addMejorGlobal(double valor) {
		mejoresGlobales[generacionActual] = valor;
	}
	
	public void setTitulo(String titulo ) {
		this.titulo=titulo;
	}
	private AxisChart crearGraficoLineas() throws ChartDataException {
		String[] xAxisLabels = cargarEtiquetasX();
		double[][] data = cargarValoresY();
		String[] legendLabels = { "El mejor", "Media", "Mejor global" };
		String xAxisTitle = "Generaciones";
		String yAxisTitle = "Puntos";

		

		dataSeries = new DataSeries(xAxisLabels, xAxisTitle,
				yAxisTitle, titulo);
		
		Paint[] paints = TestDataGenerator.getRandomPaints(3);
		Stroke[] strokes = { 
				LineChartProperties.DEFAULT_LINE_STROKE ,
				new BasicStroke(3.5f, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_ROUND, 5f, new float[] { 5f, 5f, 10f, 5f }, 4f) ,
				LineChartProperties.DEFAULT_LINE_STROKE};
		Shape[] shapes = {null,
				PointChartProperties.SHAPE_CIRCLE,
				null};
		LineChartProperties lineChartProperties = new LineChartProperties(
				strokes, shapes);			
		AxisChartDataSet axisChartDataSet = new AxisChartDataSet(data,
				legendLabels, paints, ChartType.LINE, lineChartProperties);	
		dataSeries.addIAxisPlotDataSet(axisChartDataSet);
		
		
		chartProperties = new ChartProperties();
		axisProperties = new AxisProperties();
		ChartStroke xAxisGridLines= new ChartStroke(new BasicStroke(0.5f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 5f, new float[] { 5f, 10f, 10f, 10f }, 4f), Color.red );

		axisProperties.getXAxisProperties().setGridLineChartStroke( xAxisGridLines );
		axisProperties.getXAxisProperties().setShowGridLines( AxisTypeProperties.GRID_LINES_ONLY_WITH_LABELS );

		ChartStroke yAxisGridLines= new ChartStroke(new BasicStroke(0.5f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 5f, new float[] { 5f, 10f, 10f, 10f }, 4f), Color.blue );
		
		axisProperties.getYAxisProperties().setGridLineChartStroke( yAxisGridLines );
		axisProperties.getYAxisProperties().setShowGridLines( AxisTypeProperties.GRID_LINES_ONLY_WITH_LABELS );

		legendProperties = new LegendProperties();		
		AxisChart axisChart = new AxisChart(dataSeries, chartProperties,
				axisProperties, legendProperties, 590, 430);
		
		return axisChart;
	}

	private double[][] cargarValoresY() {
		double[][] data = new double[3][mejores.length];		
		data[0]=mejores;
		data[1]=medias;
		data[2]=mejoresGlobales;
		return data;
	}

	private String[] cargarEtiquetasX() {
		String[] generaciones = new String[mejores.length];
		for (int i = 0; i < generaciones.length; i++) {
			generaciones[i] = "" + i;
		}
		return generaciones;
	}
	
	public void actualizar(Graphics2D graphics){
		try{		
			axisChart.setGraphics2D(graphics);
		axisChart.render();
		
		}catch(PropertyException e){
			log.severe(e.getMessage());
		} catch (ChartDataException e) {
			log.severe(e.getMessage());
		}
	}
}
