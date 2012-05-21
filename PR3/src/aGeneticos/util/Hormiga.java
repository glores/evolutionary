package aGeneticos.util;

public class Hormiga {
	public enum Direccion {
		Norte, Este, Sur, Oeste
	};

	private int posX, posY;

	private Direccion direccion;

	public Hormiga() {
		posX = posY = 0;
		direccion = Direccion.Oeste;
	}

	public void Avanzar() {
		switch (direccion) {
		case Norte:
			posX--;
			break;
		case Este:
			posY--;
			break;
		case Sur:
			posX++;
			break;
		case Oeste:
			posY++;
			break;
		}
	}

	public boolean puedeAvanzar(int tamX, int tamY) {
		boolean resultado;
		switch (direccion) {
		case Norte:
			resultado = (posX > 0);
			break;
		case Este:
			resultado = (posY > 0);
			break;
		case Sur:
			resultado = (posX < tamX - 1);
			break;
		case Oeste:
			resultado = (posY < tamY - 1);
			break;
		default:
			resultado = false;
			break;
		}

		return resultado;
	}

	public int[] getSigPos() {
		int x = posX;
		int y = posY;
		switch (direccion) {
		case Norte:
			x--;
			break;
		case Este:
			y--;
			break;
		case Sur:
			x++;
			break;
		case Oeste:
			y++;
			break;
		}
		return new int[] { x, y };
	}

	public void GirarIzq() {
		int aux = direccion.ordinal();
		if(aux==0){
			aux=3;
		}else{
			aux--;
		}
		direccion = Direccion.values()[aux];
	}

	public void GirarDer() {
		int aux = direccion.ordinal();
		if(aux==3){
			aux=0;
		}else{
			aux++;
		}
		direccion = Direccion.values()[aux];
	}

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}

	public int[] getPos() {
		return new int[] { posX, posY };
	}

}
