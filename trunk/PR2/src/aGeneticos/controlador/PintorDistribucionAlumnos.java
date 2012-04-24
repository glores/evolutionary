
package aGeneticos.controlador;

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
import org.jCharts.properties.PropertyException;
import org.jCharts.properties.util.ChartStroke;
import org.jCharts.test.TestDataGenerator;
import org.jCharts.types.ChartType;

import aGeneticos.logica.alumnos.ListaAlumnos;
import aGeneticos.logica.poblacion.Cromosoma;

public class PintorDistribucionAlumnos {
   
    //Atributos para pintar
    private AxisChart axisChart;
    private String titulo;
    private DataSeries dataSeries;
    private LegendProperties legendProperties;
    private ChartProperties chartProperties;
    private AxisProperties axisProperties;
    private Logger log;

    //Atributos con información del cromosoma
    private int tamGrupos;
    private int numGrupos;
    private Cromosoma solucion;
    private ListaAlumnos alumnos;
   
   
   

    public PintorDistribucionAlumnos() {

    }
   
    public void iniciar(int tamGrupos, Cromosoma c, ListaAlumnos alumnos) {
        log = Logger.getLogger("CP");
        titulo = "Distribución de alumnos ("+alumnos.getSize()+" alumnos en grupos de "+tamGrupos+")";
        this.tamGrupos=tamGrupos;
        this.solucion=c;
        this.alumnos=alumnos;
        numGrupos=(solucion.getCadena().size()/tamGrupos);
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
       
       
        String[] xAxisLabels = cargarEtiquetasX();
        double[][] data = cargarValoresY();
        String[] legendLabels = { "El mejor", "Media", "Mejor global" };
        String xAxisTitle = "Generaciones";
        String yAxisTitle = "Puntos";

        dataSeries = new DataSeries(xAxisLabels, xAxisTitle, yAxisTitle, titulo);

        Paint[] paints = TestDataGenerator.getRandomPaints(3);
        Stroke[] strokes = {
                LineChartProperties.DEFAULT_LINE_STROKE,
                new BasicStroke(3.5f, BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND, 5f, new float[] { 5f, 5f, 10f,
                                5f }, 4f),
                LineChartProperties.DEFAULT_LINE_STROKE };
        Shape[] shapes = { null, null, null };
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

    /**
     * Rellena data, que es una matriz(i,j) de reales. Cada celda representa la nota del alumno i del grupo j.
     * @return
     */
    private double[][] cargarValoresY() {
        double[][] data = new double[tamGrupos][numGrupos];
        for(int i=0;i<tamGrupos;i++){           
            for(int j=0;j<numGrupos;j++){
                solucion.getCadena();
            }
        }
       
       
        return data;
    }

    private String[] cargarEtiquetasX() {
        String[] grupos = new String[numGrupos];
        for (int i = 0; i < numGrupos; i++) {
            grupos[i] = "" + i;
        }
        return grupos;
    }

}
