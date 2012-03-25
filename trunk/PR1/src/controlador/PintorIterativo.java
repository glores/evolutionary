package controlador;

import java.awt.BasicStroke;

import java.awt.Color;
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
import org.jCharts.properties.util.ChartStroke;
import org.jCharts.test.TestDataGenerator;
import org.jCharts.types.ChartType;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 * 
 *         Clase que se encarga de la representación de datos mediante gráficos
 */
public class PintorIterativo extends PintorBase {
	private double[][] mejores;
	private double[][] mejoresGlobales;
	private double[][] medias;
	private int tamGeneraciones;
	private int tamIteraciones;
	private int iteracionActual;

	public PintorIterativo() {

	}

	public void setTamGeneraciones(int tamGeneraciones) {
		this.tamGeneraciones = tamGeneraciones;
	}

	public void setTamIteraciones(int tamIteraciones) {
		this.tamIteraciones = tamIteraciones;
	}

	public void iniciar() {
		log = Logger.getLogger("CP");
		mejores = new double[tamIteraciones][tamGeneraciones];
		mejoresGlobales = new double[tamIteraciones][tamGeneraciones];

		medias = new double[tamIteraciones][tamGeneraciones];
		generacionActual = 0;
		iteracionActual = 0;
		titulo = "";
	}


	public void siguienteGeneracion() {
		generacionActual++;
	}

	public void siguienteIteracion() {
		generacionActual = 0;
		iteracionActual++;
	}

	public void addMejor(double valor) {
		mejores[iteracionActual][generacionActual] = valor;
	}

	public void addMedia(double valor) {
		medias[iteracionActual][generacionActual] = valor;
	}

	public void addMejorGlobal(double valor) {
		mejoresGlobales[iteracionActual][generacionActual] = valor;
	}

	

	protected AxisChart crearGraficoLineas() throws ChartDataException {
		String[] xAxisLabels = cargarEtiquetasX();
		double[][] data = cargarValoresY();

		String[] legendLabels = new String[data.length];
		Stroke[] strokes = new Stroke[data.length];
		Shape[] shapes = new Shape[data.length];
		Paint[] paints = TestDataGenerator.getRandomPaints(data.length);
		for (int i = 0; i < legendLabels.length; i++) {
			legendLabels[i] = "Iteracion " + i;
			strokes[i] = LineChartProperties.DEFAULT_LINE_STROKE;
			shapes[i] = null;
		}

		String xAxisTitle = "Generaciones";
		String yAxisTitle = "Puntos";

		dataSeries = new DataSeries(xAxisLabels, xAxisTitle, yAxisTitle, titulo);

		LineChartProperties lineChartProperties = new LineChartProperties(
				strokes, shapes);
		AxisChartDataSet axisChartDataSet = new AxisChartDataSet(data,
				legendLabels, paints, ChartType.LINE, lineChartProperties);
		dataSeries.addIAxisPlotDataSet(axisChartDataSet);

		chartProperties = new ChartProperties();
		axisProperties = new AxisProperties();
		ChartStroke xAxisGridLines = new ChartStroke(new BasicStroke(0.5f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 5f, new float[] {
						5f, 10f, 10f, 10f }, 4f), Color.red);

		axisProperties.getXAxisProperties().setGridLineChartStroke(
				xAxisGridLines);
		axisProperties.getXAxisProperties().setShowGridLines(
				AxisTypeProperties.GRID_LINES_ONLY_WITH_LABELS);

		ChartStroke yAxisGridLines = new ChartStroke(new BasicStroke(0.5f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 5f, new float[] {
						5f, 10f, 10f, 10f }, 4f), Color.blue);

		axisProperties.getYAxisProperties().setGridLineChartStroke(
				yAxisGridLines);
		axisProperties.getYAxisProperties().setShowGridLines(
				AxisTypeProperties.GRID_LINES_ONLY_WITH_LABELS);

		legendProperties = new LegendProperties();
		AxisChart axisChart = new AxisChart(dataSeries, chartProperties,
				axisProperties, legendProperties, 590, 430);

		return axisChart;
	}

	private double[][] cargarValoresY() {
		return mejores;
	}

	private String[] cargarEtiquetasX() {
		String[] generaciones = new String[mejores[0].length];
		for (int i = 0; i < generaciones.length; i++) {
			generaciones[i] = "" + i;
		}
		return generaciones;
	}


}
