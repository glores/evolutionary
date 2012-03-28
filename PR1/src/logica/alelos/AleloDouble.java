package logica.alelos;

import java.util.BitSet;

import logica.esqueleto.abtractas.Alelo;

/**
 * Práctica 1 de Programación Evolutiva
 * 
 * @author Gloria Esther Pozuelo Fernández
 * @author Sergio Barja Valdés
 *
 * Clase que implementa un alelo de tipo double.
 */

public class AleloDouble extends Alelo<Double> {
	private double x_min;
	private double x_max;
	private double tolerancia;

	private int longCromosoma;

	public AleloDouble(double x_min, double x_max, double tolerancia, int pos, String nombre) {
		super(pos,nombre);
		this.x_max = x_max;
		this.x_min = x_min;
		this.tolerancia = tolerancia;
		calcularLongitud();
	}

	@Override
	public Double getFenotipo(BitSet genotipo, int numBits) {
		double result = 0;

		for (int i = 0; i < numBits; i++) {
			if (genotipo.get(i))
				result += 1 * Math.pow(2, (numBits - i - 1));
		}

		return  x_min + (x_max - x_min) * result/ (Math.pow(2, longCromosoma) - 1);
	}


	private void calcularLongitud() {
		longCromosoma = (int) Math.ceil((Math.log(1 + (x_max - x_min)/ tolerancia) / Math.log(2)) + 0.5f);
	}

	@Override
	public int getNumBits() {
		return longCromosoma;
	}

}
