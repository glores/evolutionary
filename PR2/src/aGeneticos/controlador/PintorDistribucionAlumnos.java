package aGeneticos.controlador;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.DataSeries;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.PropertyException;
import org.jCharts.properties.StackedBarChartProperties;
import org.jCharts.test.TestDataGenerator;
import org.jCharts.types.ChartType;

import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.poblacion.Cromosoma;

public class PintorDistribucionAlumnos {

	// Atributos para pintar
	private AxisChart axisChart;
	private String titulo;
	private DataSeries dataSeries;
	private LegendProperties legendProperties;
	private ChartProperties chartProperties;
	private AxisProperties axisProperties;
	private Logger log;

	// Atributos con información del cromosoma
	private int tamGrupos;
	private int numGrupos;
	private Cromosoma solucion;
	private ListaAlumnos alumnos;

	public PintorDistribucionAlumnos() {

	}

	public void iniciar(int tamGrupos, Cromosoma c, ListaAlumnos alumnos) {
		log = Logger.getLogger("CP");
		titulo = "Distribución de alumnos (" + alumnos.getSize()
				+ " alumnos en grupos de " + tamGrupos + ")";
		this.tamGrupos = tamGrupos;
		this.solucion = c;
		this.alumnos = alumnos;
		numGrupos = (solucion.getCadena().size() / tamGrupos);
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

	protected AxisChart crearGraficoLineas() throws ChartDataException {

		ArrayList<ArrayList<String>> incompatibles = ListaAlumnos
				.resumenIncompatibles(solucion.getCadena());

		titulo = "Distribución con "
				+ ListaAlumnos.getIncompatibles(solucion.getCadena())
				+ " incompatibilidades.\n";
		for (int i = 0; i < incompatibles.size(); i++) {
			if (incompatibles.get(i).size() > 0) {
				titulo += "Grupo " + (i + 1)+": ";
				for (int j = 0; j < incompatibles.get(i).size(); j++) {
					titulo += incompatibles.get(i).get(j)+", ";
				}
				titulo+="; ";
			}
		}	
		
		String[] xAxisLabels = cargarEtiquetasX(incompatibles);
		double[][] data = cargarValoresY();
		String[] legendLabels = cargarLeyendas();
		String xAxisTitle = "Grupos";
		String yAxisTitle = "Notas";
		
		dataSeries = new DataSeries(xAxisLabels, xAxisTitle, yAxisTitle, titulo);

		Paint[] paints = TestDataGenerator.getRandomPaints(tamGrupos);

		StackedBarChartProperties stackedBarChartProperties = new StackedBarChartProperties();

		AxisChartDataSet axisChartDataSet = new AxisChartDataSet(data,
				legendLabels, paints, ChartType.BAR_STACKED,
				stackedBarChartProperties);
		dataSeries.addIAxisPlotDataSet(axisChartDataSet);

		chartProperties = new ChartProperties();
		axisProperties = new AxisProperties();

		legendProperties = new LegendProperties();
		AxisChart axisChart = new AxisChart(dataSeries, chartProperties,
				axisProperties, legendProperties, 590, 430);

		return axisChart;

	}

	/**
	 * Rellena data, que es una matriz(i,j) de reales. Cada celda representa la
	 * nota del alumno i del grupo j.
	 * 
	 * @return
	 */
	private double[][] cargarValoresY() {
		double[][] data = new double[tamGrupos][numGrupos];
		int posicion = 0;
		for (int i = 0; i < numGrupos; i++) {
			for (int j = 0; j < tamGrupos; j++) {
				int id = solucion.getCadena().get(posicion).getId();
				data[j][i] = alumnos.getNotaAlumno(id);
				posicion++;
			}
		}
		return data;
	}

	private String[] cargarEtiquetasX(ArrayList<ArrayList<String>> incompatibles) {
		String[] grupos = new String[numGrupos];
		for (int i = 1; i <= numGrupos; i++) {
			grupos[i - 1] = "" + i;
			if(incompatibles.get(i-1).size()>0){
				grupos[i - 1] +="*";
			}
		}
		return grupos;
	}

	private String[] cargarLeyendas() {
		String[] leyendas = new String[tamGrupos];
		for (int i = 1; i <= tamGrupos; i++) {
			leyendas[i - 1] = "Alumno " + i;
		}
		return leyendas;
	}

}
